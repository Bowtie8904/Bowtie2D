package bt2d.core.scene.obj;

import bt2d.core.scene.Scene;

import java.util.Objects;

/**
 * Defines a collection of one or two scenes.
 * <p>
 * This class will hold a main scene and an optional loading scene.
 *
 * @author Lukas Hartwig
 * @since 10.11.2021
 */
public class ScenePair
{
    private Scene mainScene;
    private Scene loadingScene;

    /**
     * Instantiates a new ScenePair.
     *
     * @param mainScene    the main scene
     * @param loadingScene the loading scene
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public ScenePair(Scene mainScene, Scene loadingScene)
    {
        Objects.requireNonNull(mainScene, "mainScene may not be null");
        this.mainScene = mainScene;
        this.loadingScene = loadingScene;
    }

    /**
     * Instantiates a new ScenePair without a loading scene.
     *
     * @param mainScene the main scene
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public ScenePair(Scene mainScene)
    {
        this.mainScene = mainScene;
    }

    /**
     * Gets the main scene.
     *
     * @return the main scene
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public Scene getMainScene()
    {
        return this.mainScene;
    }

    /**
     * Gets the loading scene.
     *
     * @return the loading scene
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public Scene getLoadingScene()
    {
        return this.loadingScene;
    }
}