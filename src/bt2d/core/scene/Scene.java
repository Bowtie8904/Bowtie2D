package bt2d.core.scene;

import bt.types.Killable;
import bt2d.core.container.GameContainer;
import bt2d.core.intf.Tickable;
import bt2d.resource.load.intf.Loadable;

/**
 * A scene describes an isolated part of a game (i. e. a level, a city or a mainmenu).
 * <p>
 * The scene is the core of that isolated part. It is supposed to take care of forwarding
 * render and tick calls, loading resources, releasing resources, storing game entities and so on.
 * <p>
 * The point of a scene is to only load and keep the resources it actually needs to lower memory usage.
 * <p>
 * Scenes are added to the game container via {@link GameContainer#addScene(String, Scene) addScene}.
 * <p>
 * For most cases your classes should just extend {@link bt2d.core.scene.impl.BaseScene} as it already
 * has some basic implementation.
 *
 * @author Lukas Hartwig
 * @since 10.11.2021
 */
public interface Scene extends Tickable, Loadable, Killable
{
    /**
     * Called by the game container once this scene is fully loaded and righjt before
     * it will be set as the active scene to receive tick and render calls.
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public void onStart();

    /**
     * Gets the game container this scene was loaded by.
     *
     * @return the game container
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public GameContainer getGameContainer();

    /**
     * Sets game container.
     * <p>
     * This will be called by the game container when adding this scene to it.
     *
     * @param gameContainer the game container
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public void setGameContainer(GameContainer gameContainer);

    /**
     * Renders the contents of the scene by forwarding the render call to the concerned entities.
     *
     * @param debugRendering true if additional debug rendering, such as drawing hitboxes, is enabled and expected, false otherwise.
     *
     * @author Lukas Hartwig
     * @since 11.11.2021
     */
    public void render(boolean debugRendering);
}