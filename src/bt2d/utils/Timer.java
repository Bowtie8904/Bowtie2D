package bt2d.utils;

/**
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
public class Timer
{
    private Runnable action;
    private double delay;
    private double deltaSum;
    private boolean executed;

    public Timer(Runnable action, double delay)
    {
        this.action = action;
        this.delay = delay;
    }

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