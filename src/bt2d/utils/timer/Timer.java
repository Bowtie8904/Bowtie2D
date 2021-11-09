package bt2d.utils.timer;

import java.time.Duration;

/**
 * The type Timer which allows a task to be executed on the tick thread after a set delay either once or repeatetly.
 *
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
public class Timer
{
    /**
     * Conversion constant from nano seconds to seconds.
     */
    protected static final double NANO_TO_BASE = 1.0e9;
    /**
     * The action that will be executed after the delay.
     */
    protected Runnable action;

    /**
     * The time to wat until the execution of the set action in seconds.
     */
    protected double delay;

    /**
     * The accumulated delta in seconds which is used to determine if the set delay has passed.
     */
    protected double deltaSum;

    /**
     * Indicates whether this timers action has been executed.
     */
    protected boolean executed;

    /**
     * The current number of executions that this timer has already done.
     */
    protected int currentNumOfExecutions = 0;

    /**
     * The desired number of times this timers action should be executed. A negative value will mean that the timer will run indefinetely.
     */
    protected int desiredNmOfExecutions = 1;

    /**
     * Instantiates a new Timer.
     * <p>
     * By default this timer would be executed once. See {@link #repeat(int)} to change this behavior.
     *
     * @param action the action to execute after the dealy
     * @param delay  the delay duration before the execution and between repeated executions.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Timer(Runnable action, Duration delay)
    {
        this.action = action;
        this.delay = delay.getSeconds() + (delay.getNano() / NANO_TO_BASE);
    }

    /**
     * Sets the timer to repeat indefinetely.
     * <p>
     * This means that after each execution the timer will wait the configured delay again and then execute again.
     *
     * @return This instance, no new timer is created.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public Timer repeat()
    {
        return repeat(-1);
    }

    /**
     * Sets the timer to repeat n times.
     * <p>
     * This means that after each execution the timer will wait the configured delay again
     * and then execute again until it executed the given number of times.
     *
     * @param numOfExceutions The number of executions before this timer is done.
     *
     * @return This instance, no new timer is created.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public Timer repeat(int numOfExceutions)
    {
        this.desiredNmOfExecutions = numOfExceutions;
        return this;
    }

    /**
     * Checks if the desired delay has passed and if so executed the set action.
     * <p>
     * If the action has been executed either once or {@link #repeat n times} this becomes a no-op. (see {@link #isExecuted()})
     *
     * @param delta the delta of the tick loop in seconds.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void tick(double delta)
    {
        if (!this.executed)
        {
            this.deltaSum += delta;

            if (this.deltaSum >= this.delay)
            {
                this.action.run();
                this.deltaSum = 0;
                this.currentNumOfExecutions++;

                if (this.currentNumOfExecutions == this.desiredNmOfExecutions)
                {
                    this.executed = true;
                }
            }
        }
    }

    /**
     * Indicates whether this timers action has been executed.
     *
     * @return true if the action has been executed, false otherwise.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public boolean isExecuted()
    {
        return this.executed;
    }

    /**
     * Sets the status of this timer. A timer that is set to executed will no longer check its delay and wont execute its action anymore.
     * <p>
     * Essentially {@link #tick(double)} will become a no-op.
     *
     * @param executed The new value of executed. true to set this timer to executed, false otherwise.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public void setExecuted(boolean executed)
    {
        this.executed = executed;
    }
}