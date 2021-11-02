package bt2d.core.container.settings;

import bt2d.utils.ObservableBiProperty;
import bt2d.utils.ObservableProperty;

/**
 * @author Lukas Hartwig
 * @since 01.11.2021
 */
public class GameContainerSettings
{
    private ObservableBiProperty<Integer, Integer> windowSize;
    private ObservableProperty<String> title;
    private ObservableProperty<Boolean> undecorated;
    private ObservableProperty<Boolean> fullscreen;
    private ObservableProperty<Boolean> debugRendering;
    private ObservableProperty<Double> pixelsPerUnit;

    public GameContainerSettings()
    {
        this.windowSize = new ObservableBiProperty<>();
        this.title = new ObservableProperty<>();
        this.pixelsPerUnit = new ObservableProperty<>(1.0);
        this.undecorated = new ObservableProperty<>(false);
        this.fullscreen = new ObservableProperty<>(false);
        this.debugRendering = new ObservableProperty<>(false);
    }

    public ObservableBiProperty<Integer, Integer> getWindowSize()
    {
        return this.windowSize;
    }

    public GameContainerSettings setWindowSize(int width, int height)
    {
        this.windowSize.set(width, height);
        return this;
    }

    public ObservableProperty<Double> getPixelsPerUnit()
    {
        return this.pixelsPerUnit;
    }

    public GameContainerSettings setPixelsPerUnit(double pixelsPerUnit)
    {
        this.pixelsPerUnit.set(pixelsPerUnit);
        return this;
    }

    public ObservableProperty<String> getTitle()
    {
        return this.title;
    }

    public GameContainerSettings setTitle(String title)
    {
        this.title.set(title);
        return this;
    }

    public ObservableProperty<Boolean> getUndecorated()
    {
        return this.undecorated;
    }

    public GameContainerSettings setUndecorated(boolean undecorated)
    {
        this.undecorated.set(undecorated);
        return this;
    }

    public ObservableProperty<Boolean> getFullscreen()
    {
        return this.fullscreen;
    }

    public GameContainerSettings setFullscreen(boolean fullscreen)
    {
        this.fullscreen.set(fullscreen);
        return this;
    }

    public ObservableProperty<Boolean> getDebugRendering()
    {
        return this.debugRendering;
    }

    public GameContainerSettings setDebugRendering(boolean debugRendering)
    {
        this.debugRendering.set(debugRendering);
        return this;
    }
}