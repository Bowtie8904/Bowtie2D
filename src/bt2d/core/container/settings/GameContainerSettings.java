package bt2d.core.container.settings;

import bt2d.utils.ObservableBiProperty;
import bt2d.utils.ObservableProperty;

/**
 * The type Game container settings.
 *
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
public class GameContainerSettings
{
    private ObservableBiProperty<Integer, Integer> windowSize;
    private ObservableProperty<String> title;
    private ObservableProperty<Boolean> undecorated;
    private ObservableProperty<Boolean> fullscreen;
    private ObservableProperty<Boolean> debugRendering;
    private ObservableProperty<Double> pixelsPerUnit;

    /**
     * Instantiates a new Game container settings.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings()
    {
        this.windowSize = new ObservableBiProperty<>();
        this.title = new ObservableProperty<>();
        this.pixelsPerUnit = new ObservableProperty<>(1.0);
        this.undecorated = new ObservableProperty<>(false);
        this.fullscreen = new ObservableProperty<>(false);
        this.debugRendering = new ObservableProperty<>(false);
    }

    /**
     * Gets window size.
     *
     * @return the window size
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableBiProperty<Integer, Integer> getWindowSize()
    {
        return this.windowSize;
    }

    /**
     * Sets window size.
     *
     * @param width  the width
     * @param height the height
     *
     * @return the window size
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings setWindowSize(int width, int height)
    {
        this.windowSize.set(width, height);
        return this;
    }

    /**
     * Gets pixels per unit.
     *
     * @return the pixels per unit
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableProperty<Double> getPixelsPerUnit()
    {
        return this.pixelsPerUnit;
    }

    /**
     * Sets pixels per unit.
     *
     * @param pixelsPerUnit the pixels per unit
     *
     * @return the pixels per unit
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings setPixelsPerUnit(double pixelsPerUnit)
    {
        this.pixelsPerUnit.set(pixelsPerUnit);
        return this;
    }

    /**
     * Gets title.
     *
     * @return the title
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableProperty<String> getTitle()
    {
        return this.title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     *
     * @return the title
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings setTitle(String title)
    {
        this.title.set(title);
        return this;
    }

    /**
     * Gets undecorated.
     *
     * @return the undecorated
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableProperty<Boolean> getUndecorated()
    {
        return this.undecorated;
    }

    /**
     * Sets undecorated.
     *
     * @param undecorated the undecorated
     *
     * @return the undecorated
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings setUndecorated(boolean undecorated)
    {
        this.undecorated.set(undecorated);
        return this;
    }

    /**
     * Gets fullscreen.
     *
     * @return the fullscreen
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableProperty<Boolean> getFullscreen()
    {
        return this.fullscreen;
    }

    /**
     * Sets fullscreen.
     *
     * @param fullscreen the fullscreen
     *
     * @return the fullscreen
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings setFullscreen(boolean fullscreen)
    {
        this.fullscreen.set(fullscreen);
        return this;
    }

    /**
     * Gets debug rendering.
     *
     * @return the debug rendering
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableProperty<Boolean> getDebugRendering()
    {
        return this.debugRendering;
    }

    /**
     * Sets debug rendering.
     *
     * @param debugRendering the debug rendering
     *
     * @return the debug rendering
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings setDebugRendering(boolean debugRendering)
    {
        this.debugRendering.set(debugRendering);
        return this;
    }
}