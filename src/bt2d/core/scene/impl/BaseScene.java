package bt2d.core.scene.impl;

import bt.log.Log;
import bt2d.core.container.GameContainer;
import bt2d.core.scene.Scene;
import bt2d.resource.load.exc.LoadException;

/**
 * A basic implementation of the {@link Scene} interface.
 * <p>
 * This implementation should be a sufficient base for most cases.
 *
 * @author Lukas Hartwig
 * @since 10.11.2021
 */
public class BaseScene implements Scene
{
    protected GameContainer gameContainer;

    /**
     * @see Scene#onStart()
     */
    @Override
    public void onStart()
    {
        Log.debug("Starting scene");
    }

    /**
     * @see Scene#getGameContainer()
     */
    @Override
    public GameContainer getGameContainer()
    {
        return this.gameContainer;
    }

    /**
     * @see Scene#setGameContainer(GameContainer)
     */
    @Override
    public void setGameContainer(GameContainer gameContainer)
    {
        this.gameContainer = gameContainer;
    }

    /**
     * @see Scene#kill()
     */
    @Override
    public void kill()
    {
        Log.debug("Killing scene");
    }

    /**
     * @see Scene#render(boolean)
     */
    @Override
    public void render(boolean debugRendering)
    {

    }

    /**
     * @see Scene#tick(double)
     */
    @Override
    public void tick(double delta)
    {

    }

    /**
     * Loads all relevant resources for this scene. The call will be forwarded to all concerned entities and handlers.
     *
     * @see Scene#load(String)
     */
    @Override
    public void load(String sceneContextName) throws LoadException
    {
        Log.entry(sceneContextName);
        Log.exit();
    }
}