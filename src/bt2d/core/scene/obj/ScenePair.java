package bt2d.core.scene.obj;

import bt2d.core.scene.Scene;

/**
 * The type Scene pair.
 *
 * @author Lukas Hartwig
 * @since 10.11.2021
 */
public class ScenePair
{
    private Scene mainScene;
    private Scene loadingScene;

    /**
     * Instantiates a new Scene pair.
     *
     * @param mainScene    the main scene
     * @param loadingScene the loading scene
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public ScenePair(Scene mainScene, Scene loadingScene)
    {
        this.mainScene = mainScene;
        this.loadingScene = loadingScene;
    }

    /**
     * Instantiates a new Scene pair.
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
     * Gets main scene.
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
     * Gets loading scene.
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