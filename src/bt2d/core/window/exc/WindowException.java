package bt2d.core.window.exc;

/**
 * An exception indicating an issue with the usage of the Window class.
 *
 * @author Lukas Hartwig
 * @since 09.01.2021
 */
public class WindowException extends RuntimeException
{
    /**
     * Creates a new exception with the given message.
     *
     * @param message The message of the exception.
     *
     * @author Lukas Hartwig
     * @since 09.01.2021
     */
    public WindowException(String message)
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
     * @since 09.01.2021
     */
    public WindowException(String message, Throwable cause)
    {
        super(message, cause);
    }
}