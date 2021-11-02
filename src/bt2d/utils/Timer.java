package bt2d.utils;

/**
 * The type Timer which allows a task to be executed on the tick thread after a set delay.
 *
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
public class Timer
{
    /**
     * The action that will be executed after the delay.
     */
    private Runnable action;

    /**
     * The time to wat until the execution of the set action in seconds.
     */
    private double delay;

    /**
     * The accumulated delta in seconds which is used to determine if the set delay has passed.
     */
    private double deltaSum;

    /**
     * Indicates whether this timers action has been executed.
     */
    private boolean executed;

    /**
     * Instantiates a new Timer.
     *
     * @param action the action to execute after the dealy
     * @param delay  the delay in seconds.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Timer(Runnable action, double delay)
    {
        this.action = action;
        this.delay = delay;
    }

    /**
     * Checks if the desired delay has passed and if so executed the set action.
     * <p>
     * If the action has been executed once (see {@link #isExecuted()}) this becomes a no-op.
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
                this.executed = true;
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
}