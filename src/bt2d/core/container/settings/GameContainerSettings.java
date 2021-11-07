package bt2d.core.container.settings;

import bt2d.utils.property.ObservableBiNumberProperty;
import bt2d.utils.property.ObservableBiProperty;
import bt2d.utils.property.ObservableNumberProperty;
import bt2d.utils.property.ObservableProperty;

/**
 * The settings of a game container.
 * <p>
 * This class offers observable properties to listen to value changes, so that they can be reflected by the container.
 *
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
public class GameContainerSettings
{
    /**
     * The pixel sizes of the window.
     * <p>
     * The first value is the width, the second value is the height.
     */
    private ObservableBiNumberProperty<Integer, Integer> windowSize;

    /**
     * The title of the window.
     */
    private ObservableProperty<String> title;

    /**
     * Indicates whether the window should be decorated with the OS default window border.
     */
    private ObservableProperty<Boolean> undecorated;

    /**
     * Indicates whether the window should be fullscreen.
     */
    private ObservableProperty<Boolean> fullscreen;

    /**
     * Indicates whether additional debug rendering should be done.
     */
    private ObservableProperty<Boolean> debugRendering;

    /**
     * The width of the game in game units.
     */
    private ObservableNumberProperty<Double> gameUnitWidth;

    /**
     * Instantiates a new Game container settings.
     * <p>
     * Default values will be set for the properties.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings()
    {
        this.windowSize = new ObservableBiNumberProperty<>(0, 0);
        this.windowSize.nonNull();
        this.windowSize.range1(1, Integer.MAX_VALUE);
        this.windowSize.range2(1, Integer.MAX_VALUE);

        this.title = new ObservableProperty<>("Title");
        this.title.nonNull();

        this.gameUnitWidth = new ObservableNumberProperty<>(100.0);
        this.gameUnitWidth.nonNull();
        this.gameUnitWidth.range(1.0, Double.MAX_VALUE);

        this.undecorated = new ObservableProperty<>(false);
        this.undecorated.nonNull();

        this.fullscreen = new ObservableProperty<>(false);
        this.fullscreen.nonNull();

        this.debugRendering = new ObservableProperty<>(false);
        this.debugRendering.nonNull();
    }

    /**
     * Gets window size in pixels.
     *
     * @return the window size property. The first value is the width, the second value is the height.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableBiProperty<Integer, Integer> getWindowSize()
    {
        return this.windowSize;
    }

    /**
     * Sets window size in pixels.
     * <p>
     * Even if the window should be started in fullscreen these values are required
     * to determine the desired aspect ratio of the game.
     *
     * @param width  the width of the window.
     * @param height the height of the window.
     *
     * @return This instance for chaining.
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
     * Gets the amount of game units this game is wide.
     *
     * @return the game unit width property.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableProperty<Double> getGameUnitWidth()
    {
        return this.gameUnitWidth;
    }

    /**
     * Sets the amount of game units this game is wide.
     *
     * @param gameUnitWidth
     *
     * @return This instance for chaining.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings setGameUnitWidth(double gameUnitWidth)
    {
        this.gameUnitWidth.set(gameUnitWidth);
        return this;
    }

    /**
     * Gets title of the window.
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
     * Sets title of the window.
     *
     * @param title the desired title. Cant be null.
     *
     * @return This instance for chaining.
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
     * Indicates whether the window should be decorated with the OS default window border.
     *
     * @return false if the default border should be used, true otherwise.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableProperty<Boolean> getUndecorated()
    {
        return this.undecorated;
    }

    /**
     * Sets whether the window should be decorated with the OS default window border.
     *
     * @param undecorated false if the default border should be used, true otherwise.
     *
     * @return This instance for chaining.
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
     * Indicates whether the window should be fullscreen.
     *
     * @return true if the window should be in fullscreen, false otherwise.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableProperty<Boolean> getFullscreen()
    {
        return this.fullscreen;
    }

    /**
     * Sets whether the window should be fullscreen.
     *
     * @param fullscreen true if the window should be in fullscreen, false otherwise.
     *
     * @return This instance for chaining.
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
     * Indicates whether additional debug rendering should be done.
     *
     * @return true if additional rendering should be done, false otherwise.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableProperty<Boolean> getDebugRendering()
    {
        return this.debugRendering;
    }

    /**
     * Sets whether additional debug rendering should be done.
     *
     * @param debugRendering true if additional rendering should be done, false otherwise.
     *
     * @return This instance for chaining.
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