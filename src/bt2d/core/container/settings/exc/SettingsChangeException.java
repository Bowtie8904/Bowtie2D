package bt2d.core.container.settings.exc;

/**
 * An exception indicating that an unchangeable value was attempted to be changed.
 *
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
public class SettingsChangeException extends RuntimeException
{
    /**
     * Creates a new exception with the given message.
     *
     * @param message The message of the exception.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public SettingsChangeException(String message)
    {
        super(message);
    }

    /**
     * Created a new exception with the given message and the given cause.
     *
     * @param message The message of the exception.
     * @param cause   The cause of this exception.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public SettingsChangeException(String message, Throwable cause)
    {
        super(message, cause);
    }
}