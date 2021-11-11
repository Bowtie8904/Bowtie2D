package bt2d.resource.render.intf;

/**
 * Defines a class that can be rendered in any way.
 *
 * @author Lukas Hartwig
 * @since 10.11.2021
 */
public interface Renderable
{
    /**
     * Renders the contents of this class.
     *
     * @param debugRendering true if additional debug rendering is enabled and expected, false otherwise.
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public void render(boolean debugRendering);
}