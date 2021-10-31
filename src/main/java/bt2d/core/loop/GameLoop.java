package bt2d.core.loop;

import bt.runtime.InstanceKiller;
import bt.types.Killable;
import bt.utils.Exceptions;
import bt.utils.Null;
import bt.utils.ThrowRunnable;

import java.util.function.Consumer;

/**
 * A simple game loop that will try to call tick and render methods at set rates.
 * <p>
 * Usage:
 * <pre>
 *     GameLoop loop = new GameLoop(this::myTick,
 *                                  this::myRender);
 *     loop.setFrameRate(60);
 *     loop.setTickRate(100);
 *     loop.setRateChecksPerSecond(10);
 *
 *     Threads.get().execute(loop, "GAME_LOOP");
 *
 *     loop.kill();
 * </pre>
 *
 * @author Lukas Hartwig
 * @since 28.10.2021
 */
public class GameLoop implements Killable, Runnable
{
    /**
     * Conversion constant from nano seconds to seconds.
     */
    protected static final double NANO_TO_BASE = 1.0e9;

    /**
     * Indicates whether this loop is currently running. Setting this to false is the easiest way to terminate the loop.
     */
    protected volatile boolean running;

    /**
     * The estimated current frames per second.
     */
    protected int currentFramesPerSecond = -1;

    /**
     * The estimated current ticks per second.
     */
    protected int currentTicksPerSecond = -1;

    /**
     * The interval (in nano seconds) at which the current frame and tick rate are calculated and the intervals between tick and render calls are adjusted.
     */
    protected long rateCheckInterval = 0;

    /**
     * Nano second interval between render calls.
     */
    protected long renderInterval = 0;

    /**
     * The number of frames per second that this loop should try to maintain.
     */
    protected int desiredFramesPerSecond;

    /**
     * Nano second interval between tick calls.
     */
    protected long tickInterval = 0;

    /**
     * The number of ticks per second that this loop should try to maintain.
     */
    protected int desiredTicksPerSecond;

    /**
     * The last delta value that was passed to the tick method.
     */
    protected double delta = 0;

    /**
     * The amount of nano seconds that will be added or subtracted from the tick/render interval for each
     * frame/tick per second difference to the desired value.
     */
    protected long intervalCorrection = 10000;

    /**
     * The set tick consumer that receives the delta seconds since the last tick.
     */
    protected Consumer<Double> tick;

    /**
     * The set render runnable that is called {@link #currentFramesPerSecond n} times per second.
     */
    protected Runnable render;

    /**
     * A callback to initialize stuff before the rendering starts. Can be used to setup the window on the same thread as the rendering.
     */
    protected Runnable init;

    /**
     * The set consumer that is executed whenever the frame and tick rate are recalculated. See {@link #rateCheckInterval}.
     */
    protected Consumer<Integer> onFpsUpdate;

    /**
     * Creates a new instance and sets the runnables for tick and render methods.
     *
     * <p>
     * This constructor registers the loop to the {@link InstanceKiller} with a priority of 1.
     * </p>
     *
     * @param tick   Callback for tick calls.
     * @param render Callback for render calls.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    public GameLoop(Consumer<Double> tick, Runnable render)
    {
        this.tick = tick;
        this.render = render;
        setFrameRate(60);
        setTickRate(60);
        setRateChecksPerSecond(10);
        InstanceKiller.killOnShutdown(this, 1);
    }

    /**
     * Creates a new instance and sets the runnables for tick, render and init methods.
     *
     * <p>
     * This constructor registers the loop to the {@link InstanceKiller} with a priority of 1.
     * </p>
     *
     * @param tick   Callback for tick calls.
     * @param render Callback for render calls.
     * @param init   Callback for initialization before the first run.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    public GameLoop(Consumer<Double> tick, Runnable render, Runnable init)
    {
        this(tick, render);
        this.init = init;
    }

    /**
     * Kills the loop.
     *
     * <p>
     * This will not cancel the current iteration. The tick and render methods might still be called once before the
     * loop truly terminates.
     * </p>
     *
     * @author Lukas Hartwig
     * @see bt.runtime.Killable#kill()
     * @since 28.10.2021
     */
    @Override
    public void kill()
    {
        System.out.println("Killing game loop.");
        this.running = false;
    }

    /**
     * Starts the loop if it is not running yet.
     * <p>
     * If an init runnable has been set then it will be called before the {@link #loop()} call.
     *
     * @author Lukas Hartwig
     * @since 30.10.2021
     */
    @Override
    public void run()
    {
        if (!this.running)
        {
            this.running = true;

            Null.checkRun(this.init);

            loop();
        }
    }

    /**
     * Sets the target frame rate that this loop will try to maintain.
     *
     * @param desiredFramesPerSecond The target number of renders per second.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    public void setFrameRate(int desiredFramesPerSecond)
    {
        this.desiredFramesPerSecond = desiredFramesPerSecond;

        // calculating nano second interval
        this.renderInterval = (long)(GameLoop.NANO_TO_BASE / desiredFramesPerSecond);
    }

    /**
     * Sets the target tick rate that this loop will try to maintain.
     *
     * @param desiredTicksPerSecond The target number of ticks per second.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    public void setTickRate(int desiredTicksPerSecond)
    {
        this.desiredTicksPerSecond = desiredTicksPerSecond;

        // calculating nano second interval
        this.tickInterval = (long)(GameLoop.NANO_TO_BASE / desiredTicksPerSecond);
    }

    /**
     * Defines an action that is executed when the frame and tick rate are recalculated.
     * <p>
     * See {@link #setRateChecksPerSecond(int)} to set the interval at which this consumer will be called.
     *
     * @param onUpdate A consumer which will receive the new frame rate.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    public void onFpsUpdate(Consumer<Integer> onUpdate)
    {
        this.onFpsUpdate = onUpdate;
    }

    /**
     * Sets how many times the current frame and tick rate are calculated and the intervals between tick and render calls are adjusted.
     *
     * @param checksPerSecond The amount of checks per second. 1 = 1 check per second, 10 = 10 checks per second.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    public void setRateChecksPerSecond(int checksPerSecond)
    {
        // calculating nano second interval
        this.rateCheckInterval = (long)(GameLoop.NANO_TO_BASE / checksPerSecond);
    }

    /**
     * Gets the current frame rate. This value is updated {@link #setRateChecksPerSecond(int) n} times per second.
     * <p>
     * This value is only an estimation. It is most accurate at 1 check per second and becomes less accurate the more checks are performed.
     *
     * @return The current estimated frame rate.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    public int getCurrentFramesPerSecond()
    {
        return this.currentFramesPerSecond;
    }

    /**
     * Gets the current tick rate. This value is updated {@link #setRateChecksPerSecond(int) n} times per second.
     * <p>
     * This value is only an estimation. It is most accurate at 1 check per second and becomes less accurate the more checks are performed.
     *
     * @return The current estimated tick rate.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    public int getCurrentTicksPerSecond()
    {
        return this.currentFramesPerSecond;
    }

    /**
     * Gets the last delta value (in seconds) that was given to the tick call.
     * <p>
     * The delta value is the time between two consecutive tick calls.
     *
     * @return The delta value in seconds.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    public double getLastDelta()
    {
        return this.delta;
    }

    /**
     * Gets the amount of nano seconds that will be added or subtracted from the tick/render interval for each
     * frame/tick per second difference to the desired value.
     *
     * @return The correction amount in nano seconds.
     *
     * @author Lukas Hartwig
     * @since 31.10.2021
     */
    public long getIntervalCorrection()
    {
        return intervalCorrection;
    }

    /**
     * Sets the amount of nano seconds that will be added or subtracted from the tick/render interval for each
     * frame/tick per second difference to the desired value.
     *
     * @param intervalCorrection The correction amount in nano seconds.
     *
     * @author Lukas Hartwig
     * @since 31.10.2021
     */
    public void setIntervalCorrection(long intervalCorrection)
    {
        this.intervalCorrection = intervalCorrection;
    }

    /**
     * Runs the render runnable if it is not null.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    protected void runRender()
    {
        Null.checkRun(this.render);
    }

    /**
     * Runs the tick runnable if it is not null.
     *
     * @author Lukas Hartwig
     * @since 28.10.2021
     */
    protected void runTick(double delta)
    {
        Null.checkConsume(this.tick, delta);
    }

    /**
     * The core of the GameLoop class containing the actual loop which calls tick and render methods.
     * <p>
     * This method will loop until {@link #running} is set to false (i.e. via a call to {@link #kill()}.
     *
     * @author Lukas Hartwig
     * @since 30.10.2021
     */
    protected void loop()
    {
        // the current JVM nano time
        long currentNanoTime = System.nanoTime();

        // the JVM nano time of the previous iteration
        long lastNanoTime = currentNanoTime;

        // the scheduled JVM nano time of the next render call
        long nextRenderCall = currentNanoTime;

        // the scheduled JVM nano time of the next tick call
        long nextTickCall = currentNanoTime;

        // accumulated delta between tick calls
        long tickDeltaSum = 0;

        // delta of current iteration
        long nanoDelta = 0;

        // accumulated number of render calls between rate updates
        int frames = 0;

        // accumulated number of tick calls between rate updates
        int ticks = 0;

        // accumulated delta between frame and tick rate checks
        long rateCheckDeltaSum = 0;

        while (this.running)
        {
            // wait until the next action
            // either tick or render
            currentNanoTime = sync(Math.min(nextRenderCall, nextTickCall));

            // calculate delta to last iteration
            nanoDelta = currentNanoTime - lastNanoTime;

            lastNanoTime = currentNanoTime;
            tickDeltaSum += nanoDelta;
            rateCheckDeltaSum += nanoDelta;

            // check if tick call has to be executed
            if (currentNanoTime >= nextTickCall)
            {
                // convert nanoDelta to seconds
                this.delta = (double)tickDeltaSum / GameLoop.NANO_TO_BASE;
                tickDeltaSum = 0;

                // call tick method and schedule next tick call
                runTick(this.delta);
                nextTickCall = currentNanoTime + this.tickInterval;

                // increment ticks for a later check if ticks per second are met
                ticks++;
            }

            // check if render call has to be executed
            if (currentNanoTime >= nextRenderCall)
            {
                // call render method and schedule next render call
                runRender();
                nextRenderCall = currentNanoTime + this.renderInterval;

                // increment frames for a later check if frames per second are met
                frames++;
            }

            // check if frame and tick rate need to be recalculated and adjusted
            if (rateCheckDeltaSum >= this.rateCheckInterval)
            {
                // estimate current frames per second
                this.currentFramesPerSecond = (int)(frames / (rateCheckDeltaSum / GameLoop.NANO_TO_BASE));
                frames = 0;
                adjustRenderInterval();

                // estimate current ticks per second
                this.currentTicksPerSecond = (int)(ticks / (rateCheckDeltaSum / GameLoop.NANO_TO_BASE));
                ticks = 0;
                adjustTickInterval();

                Null.checkConsume(this.onFpsUpdate, this.currentFramesPerSecond);

                rateCheckDeltaSum = 0;
            }
        }
    }

    /**
     * Calculates the difference between the current ticks per second and the desired ticks per second.
     * If there is a difference then the tick interval is adjusted by ({@link #intervalCorrection} * tick difference) in an attempt to remove the gap.
     *
     * @author Lukas Hartwig
     * @since 30.10.2021
     */
    protected void adjustTickInterval()
    {
        long tickDiff = this.currentTicksPerSecond - this.desiredTicksPerSecond;
        this.tickInterval += tickDiff * this.intervalCorrection;
    }

    /**
     * Calculates the difference between the current frames per second and the desired frames per second.
     * If there is a difference then the render interval is adjusted by ({@link #intervalCorrection} * frame difference) in an attempt to remove the gap.
     *
     * @author Lukas Hartwig
     * @since 30.10.2021
     */
    protected void adjustRenderInterval()
    {
        long frameDiff = this.currentFramesPerSecond - this.desiredFramesPerSecond;
        this.renderInterval += frameDiff * this.intervalCorrection;
    }

    /**
     * Sleeps until the target JVM nano time has been reached.
     *
     * @param target The JVM nano time that should be reached.
     *
     * @return The current nano time after the target has been reached.
     *
     * @author Lukas Hartwig
     * @since 30.10.2021
     */
    protected long sync(long target)
    {
        long current = 0;
        ThrowRunnable sleep = () -> Thread.sleep(1);

        while ((current = System.nanoTime()) < target)
        {
            Exceptions.ignoreThrow(sleep);
        }

        return current;
    }
}