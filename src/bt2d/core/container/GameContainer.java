package bt2d.core.container;

import bt.types.Killable;
import bt2d.core.container.exc.GameContainerException;
import bt2d.core.container.settings.GameContainerSettings;
import bt2d.core.loop.GameLoop;
import bt2d.core.window.Window;
import bt2d.utils.Timer;
import bt2d.utils.Unit;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

/**
 * The type Game container.
 *
 * @author Lukas Hartwig
 * @since 01.11.2021
 */
public class GameContainer implements Runnable, Killable
{
    protected GameLoop loop;
    protected GameContainerSettings settings;
    protected Window window;
    protected Unit width;
    protected Unit height;
    private Timer testTimer;

    /**
     * Instantiates a new Game container.
     *
     * @param settings the settings
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainer(GameContainerSettings settings)
    {
        this.settings = settings;
        createWindow();
        configureSettings();
    }

    /**
     * Configure settings.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    protected void configureSettings()
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
     * Gets settings.
     *
     * @return the settings
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainerSettings getSettings()
    {
        return this.settings;
    }

    /**
     * Sets game loop.
     *
     * @param loop the loop
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void setGameLoop(GameLoop loop)
    {
        if (this.loop != null && this.loop.isRunning())
        {
            throw new GameContainerException("Previously set loop is already active");
        }

        this.loop = loop;
    }

    /**
     * Create window.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    protected void createWindow()
    {
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFWErrorCallback.createPrint(System.err).set();

        this.window = new Window(this.settings.getWindowSize().getFirst(),
                                 this.settings.getWindowSize().getSecond(),
                                 this.settings.getTitle().get(),
                                 this.settings.getFullscreen().get(),
                                 0);

        Unit.setRatio(this.settings.getPixelsPerUnit().get());
        this.width = Unit.forPixels(this.window.getWidth());
        this.height = Unit.forPixels(this.window.getHeight());

        System.out.println(this.width);

        this.window.showWindow();

        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glOrtho(0.f,
                getWidth().pixels(),
                getHeight().pixels(),
                0.f,
                0.f,
                1.f);

        this.testTimer = new Timer(() -> this.settings.setWindowSize(854, 480), 4);
    }

    /**
     * Tick.
     *
     * @param delta the delta
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void tick(double delta)
    {
        // TODO forward tick call to scene

        this.testTimer.tick(delta);

        glfwPollEvents();

        if (this.window.isShouldClose())
        {
            kill();
        }
    }

    /**
     * Render.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void render()
    {
        // TODO forward render call to scene

        this.window.beforeRender();

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
     * Kill.
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
     * Run.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    @Override
    public void run()
    {
        if (this.loop == null)
        {
            this.loop = createDefaultGameLoop();
        }

        this.loop.run();
    }

    /**
     * Create default game loop game loop.
     *
     * @return the game loop
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
     * Gets width.
     *
     * @return the width
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit getWidth()
    {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit getHeight()
    {
        return height;
    }
}