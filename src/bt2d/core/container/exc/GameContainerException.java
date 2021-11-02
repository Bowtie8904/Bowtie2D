package bt2d.core.container.exc;

/**
 * An exception indicating an issue with the usage of the GameContainer class.
 *
 * @author Lukas Hartwig
 * @since 01.11.2021
 */
public class GameContainerException extends RuntimeException
{
    /**
     * Creates a new exception with the given message.
     *
     * @param message The message of the exception.
     */
    public GameContainerException(String message)
    {
        super(message);
    }

    /**
     * Created a new exception with the given message and the given cause.
     *
     * @param message The message of the exception.
     * @param cause   The cause of this exception.
     */
    public GameContainerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}