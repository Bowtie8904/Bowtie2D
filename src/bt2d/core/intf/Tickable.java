package bt2d.core.intf;

/**
 * Defines a class that wants to be notified during the game tick.
 *
 * @author Lukas Hartwig
 * @since 10.11.2021
 */
public interface Tickable
{
    /**
     * Performs actions for this game tick.
     *
     * @param delta The elapsed time in seconds since the last tick call.
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public void tick(double delta);
}