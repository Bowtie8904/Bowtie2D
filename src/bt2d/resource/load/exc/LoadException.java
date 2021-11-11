package bt2d.resource.load.exc;

/**
 * An exception that will be thrown during the process of loading resources.
 *
 * @author Lukas Hartwig
 * @since 10.11.2021
 */
public class LoadException extends Exception
{
    /**
     * Instantiates a new LoadException.
     *
     * @param message the message
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public LoadException(String message)
    {
        super(message);
    }

    /**
     * Instantiates a new LoadException.
     *
     * @param message the message
     * @param cause   the cause
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public LoadException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
