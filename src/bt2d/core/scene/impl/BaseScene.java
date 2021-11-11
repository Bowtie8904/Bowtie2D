package bt2d.core.scene.impl;

import bt2d.core.container.GameContainer;
import bt2d.core.scene.Scene;
import bt2d.resource.load.exc.LoadException;

/**
 * The type Base scene.
 *
 * @author Lukas Hartwig
 * @since 10.11.2021
 */
public class BaseScene implements Scene
{
    protected GameContainer gameContainer;

    /**
     * On start.
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    @Override
    public void onStart()
    {

    }

    @Override
    public GameContainer getGameContainer()
    {
        return this.gameContainer;
    }

    @Override
    public void setGameContainer(GameContainer gameContainer)
    {
        this.gameContainer = gameContainer;
    }

    /**
     * Kill.
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    @Override
    public void kill()
    {

    }

    /**
     * Render.
     *
     * @param debugRendering the debug rendering
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    @Override
    public void render(boolean debugRendering)
    {

    }

    /**
     * Tick.
     *
     * @param delta the delta
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    @Override
    public void tick(double delta)
    {

    }

    /**
     * Load.
     *
     * @param sceneContextName the scene context name
     *
     * @throws LoadException the load exception
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    @Override
    public void load(String sceneContextName) throws LoadException
    {

    }
}