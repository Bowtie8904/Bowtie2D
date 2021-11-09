package bt2d.utils.timer;

import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Defines a set of timer based actions.
 *
 * @author Lukas Hartwig
 * @since 04.11.2021
 */
public class TimerActions
{
    /**
     * The list of timers that are currently managed by this instance.
     */
    private List<Timer> timers;

    /**
     * Instantiates a new TimerActions.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public TimerActions()
    {
        this.timers = new LinkedList<>();
    }

    /**
     * Adds the given timer to this instances list.
     * <p>
     * After this call the given timers tick method will be called everytime
     * this instances {@link #checkActions(double)} method is called.
     * <p>
     * The timer will be removed from this instances list as soon as
     * its {@link Timer#isExecuted()} method returns true.
     *
     * @param timer The timer to add.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public void add(Timer timer)
    {
        this.timers.add(timer);
    }

    /**
     * Creates a new timer and adds it to this instances list.
     * <p>
     * The created timer will be executed once.
     * <p>
     * After this call the created timers tick method will be called everytime
     * this instances {@link #checkActions(double)} method is called.
     * <p>
     * The timer will be removed from this instances list as soon as
     * its {@link Timer#isExecuted()} method returns true.
     *
     * @param action The action to execute after the given delay.
     * @param delay  The delay before executing the given action.
     *
     * @return The created timer instance.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public Timer once(Runnable action, Duration delay)
    {
        Timer timer = new Timer(action, delay);
        add(timer);
        return timer;
    }

    /**
     * Creates a new timer and adds it to this instances list.
     * <p>
     * The created timer will be executed numOfExecutions times.
     * <p>
     * After this call the created timers tick method will be called everytime
     * this instances {@link #checkActions(double)} method is called.
     * <p>
     * The timer will be removed from this instances list as soon as
     * its {@link Timer#isExecuted()} method returns true.
     *
     * @param action          The action to execute after the given delay.
     * @param delay           The delay before executing the given action and between repeated actions.
     * @param numOfExecutions The number of executions before this timer counts as executed.
     *
     * @return The created timer instance.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public Timer repeat(Runnable action, Duration delay, int numOfExecutions)
    {
        Timer timer = new Timer(action, delay).repeat(numOfExecutions);
        add(timer);
        return timer;
    }

    /**
     * Creates a new timer and adds it to this instances list.
     * <p>
     * The created timer will be executed infinite times.
     * <p>
     * After this call the created timers tick method will be called everytime
     * this instances {@link #checkActions(double)} method is called.
     * <p>
     * The timer will be removed from this instances list as soon as
     * its {@link Timer#isExecuted()} method returns true which will not happen naturally.
     *
     * @param action The action to execute after the given delay.
     * @param delay  The delay before executing the given action and between repeated actions.
     *
     * @return The created timer instance.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public Timer repeat(Runnable action, Duration delay)
    {
        Timer timer = new Timer(action, delay).repeat();
        add(timer);
        return timer;
    }

    /**
     * Check if any of the added timers need to be executed based on their configured delay.
     * <p>
     * This method should be called during every tick iteration.
     *
     * @param delta The delta between tick iterations in seconds.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public void checkActions(double delta)
    {
        Iterator<Timer> iterator = this.timers.iterator();

        while (iterator.hasNext())
        {
            Timer timer = iterator.next();
            timer.tick(delta);

            if (timer.isExecuted())
            {
                iterator.remove();
            }
        }
    }
}