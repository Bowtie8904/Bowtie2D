package bt2d.core.container.settings;

import bt2d.utils.ObservableProperty;

/**
 * @author Lukas Hartwig
 * @since 01.11.2021
 */
public class GameContainerSettings
{
    private ObservableProperty<Integer> windowWidth;
    private ObservableProperty<Integer> windowHeight;
    private ObservableProperty<String> title;
    private ObservableProperty<Boolean> undecorated;
    private ObservableProperty<Boolean> fullscreen;
    private ObservableProperty<Boolean> debugRendering;

    public GameContainerSettings()
    {
        this.windowWidth = new ObservableProperty<>();
        this.windowHeight = new ObservableProperty<>();
        this.title = new ObservableProperty<>();
        this.undecorated = new ObservableProperty<>(false);
        this.fullscreen = new ObservableProperty<>(false);
        this.debugRendering = new ObservableProperty<>(false);
    }

    public ObservableProperty<Integer> getWindowWidth()
    {
        return this.windowWidth;
    }

    public GameContainerSettings setWindowWidth(int width)
    {
        this.windowWidth.set(width);
        return this;
    }

    public ObservableProperty<Integer> getWindowHeight()
    {
        return this.windowHeight;
    }

    public GameContainerSettings setWindowHeight(int height)
    {
        this.windowHeight.set(height);
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