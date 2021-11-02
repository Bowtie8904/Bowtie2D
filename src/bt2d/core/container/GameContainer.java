package bt2d.core.container;

import bt.types.Killable;
import bt2d.core.container.exc.GameContainerException;
import bt2d.core.container.settings.GameContainerSettings;
import bt2d.core.loop.GameLoop;
import bt2d.core.window.Window;
import bt2d.utils.Unit;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

/**
 * The core of a game.
 * <p>
 * Contains the central tick and render methods, holds a reference to the game window and manages scenes.
 *
 * @author Lukas Hartwig
 * @since 01.11.2021
 */
public class GameContainer implements Runnable, Killable
{
    /**
     * The loop that calls this containers tick and render methods.
     */
    protected GameLoop loop;

    /**
     * The settings whichs properties will be bound by this container.
     * Changes in this settings instance will be reflected by the container.
     */
    protected GameContainerSettings settings;

    /**
     * The window of this container.
     */
    protected Window window;

    /**
     * The width of this container in units.
     */
    protected Unit width;

    /**
     * The height of this container in units.
     */
    protected Unit height;

    /**
     * Instantiates a new Game container.
     *
     * @param settings the settings that will be bound by this container. Changes to the properties
     *                 of this settings instance will be reflected by the container.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainer(GameContainerSettings settings)
    {
        this.settings = settings;
    }

    /**
     * Binds specific actions to the properties of the settings instance.
     * <p>
     * After this call changes to the settings, i.e. to the window size, will be reflected by the window.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    protected void bindSettings()
    {
        this.settings.getTitle().onChange(title -> this.window.setWindowTitle(title));
        this.settings.getWindowSize().onChange((width, height) -> {
            this.window.updateWindowSize(width, height);
            this.settings.setPixelsPerUnit(width / this.width.units());
        });
        this.settings.getFullscreen().onChange(fullscreen -> this.window.setFullScreenMode(fullscreen));
        this.settings.getPixelsPerUnit().onChange(ratio -> Unit.setRatio(ratio));
    }

    /**
     * Gets the settings instance that is bound by this container.
     * <p>
     * Changes to this settings instances properties will be reflected by this container.
     * I.e. changing the properties for the window size will resize the window of the container.
     *
     * @return the settings instance that was given to this container and
     * whichs properties are reflected by this instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings getSettings()
    {
        return this.settings;
    }

    /**
     * Sets game loop that will be started when this container is started.
     *
     * @param loop the loop instance which is already fully configured.
     *
     * @throws GameContainerException If there is already a loop set which is currently running.
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void setGameLoop(GameLoop loop)
    {
        // already set loop is currently running, should not just replace
        if (this.loop != null && this.loop.isRunning())
        {
            throw new GameContainerException("Previously set loop is already active");
        }

        this.loop = loop;
    }

    /**
     * Create the window based on the given settings.
     *
     * @throws GameContainerException if GLFW could not be initialized.
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    protected void createWindow()
    {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
        {
            throw new GameContainerException("Unable to initialize GLFW");
        }

        this.window = new Window(this.settings.getWindowSize().getFirst(),
                                 this.settings.getWindowSize().getSecond(),
                                 this.settings.getTitle().get(),
                                 this.settings.getFullscreen().get(),
                                 0);

        // set ratio based on settings and calculate unit size for this container
        Unit.setRatio(this.settings.getPixelsPerUnit().get());
        this.width = Unit.forPixels(this.window.getWidth());
        this.height = Unit.forPixels(this.window.getHeight());

        this.window.showWindow();

        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glOrtho(0.f,
                getWidth().pixels(),
                getHeight().pixels(),
                0.f,
                0.f,
                1.f);
    }

    /**
     * The tick method of this container.
     * <p>
     * This will check if the window needs to be closed and will forward tick calls scenes.
     *
     * @param delta the delta since the last tick call in seconds.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void tick(double delta)
    {
        // TODO forward tick call to scene

        glfwPollEvents();

        if (this.window.isShouldClose())
        {
            kill();
        }
    }

    /**
     * The render method of this container.
     * <p>
     * This will call {@link Window#beforeRender()} before forwarding the render call and {@link Window#afterRender()} afterwards.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void render()
    {
        // TODO forward render call to scene

        this.window.beforeRender();

        // TODO remove test rendering
        glColor4f(1, 0, 0, 0);

        glBegin(GL_LINES);
        glVertex3f(0, 0, 0);
        glVertex3f((float)getWidth().divideBy(2).pixels(), (float)getHeight().divideBy(2).pixels(), 0);
        glEnd();

        glBegin(GL_LINES);
        glVertex3f((float)getWidth().units(), 0, 0);
        glVertex3f((float)getWidth().divideBy(2).pixels(), (float)getHeight().divideBy(2).pixels(), 0);
        glEnd();

        glBegin(GL_LINES);
        glVertex3f((float)getWidth().divideBy(2).pixels(), (float)getHeight().divideBy(2).pixels(), 0);
        glVertex3f((float)getWidth().divideBy(2).pixels(), (float)getHeight().pixels(), 0);
        glEnd();

        this.window.afterRender();
    }

    /**
     * Terminates this container by closing the window and stopping the gameloop.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    @Override
    public void kill()
    {
        this.loop.kill();
        this.window.kill();
    }

    /**
     * Starts the game by creating the window, binding the settings and starting the gameloop.
     * <p>
     * If no gameloop was set previously a defualt one will be created and used.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    @Override
    public void run()
    {
        createWindow();
        bindSettings();

        if (this.loop == null)
        {
            this.loop = createDefaultGameLoop();
        }

        this.loop.run();
    }

    /**
     * Creates a default game loop.
     * <p>
     * This method is called automatically if no gameloop instance was set
     * via {@link #setGameLoop(GameLoop)} when the container is started via {@link #run()}.
     *
     * @return the created loop.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    protected GameLoop createDefaultGameLoop()
    {
        GameLoop defaultLoop = new GameLoop(this::tick, this::render);
        defaultLoop.setFrameRate(60);
        defaultLoop.setTickRate(60);
        defaultLoop.setRateChecksPerSecond(2);
        return defaultLoop;
    }

    /**
     * Gets the width of this container in units.
     * <p>
     * This method returns null prior to the start of the container via {@link #run()}.
     *
     * @return the width of this container or null if the container was not started yet.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit getWidth()
    {
        return width;
    }

    /**
     * Gets the height of this container in units.
     * <p>
     * This method returns null prior to the start of the container via {@link #run()}.
     *
     * @return the height of this container or null if the container was not started yet.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit getHeight()
    {
        return height;
    }
}