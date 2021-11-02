package bt2d.utils;

/**
 * The type Timer which allows a task to be executed on the tick thread after a set delay.
 *
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
public class Timer
{
    private Runnable action;
    private double delay;
    private double deltaSum;
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
     *
     * @param delta the delta of the tick loop
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
}