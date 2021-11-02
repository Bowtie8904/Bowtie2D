package bt2d.core.container;

import bt.scheduler.Threads;
import bt.types.Killable;
import bt2d.core.container.exc.GameContainerException;
import bt2d.core.container.settings.GameContainerSettings;
import bt2d.core.loop.GameLoop;
import bt2d.core.window.Window;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author Lukas Hartwig
 * @since 01.11.2021
 */
public class GameContainer implements Runnable, Killable
{
    protected GameLoop loop;
    protected GameContainerSettings settings;
    protected Window window;

    public GameContainer(GameContainerSettings settings)
    {
        this.settings = settings;
        createWindow();
        configureSettings();
    }

    protected void configureSettings()
    {
        // TODO add calls to window methods
        this.settings.getTitle().onChange(value -> System.out.println(value));
        this.settings.getUndecorated().onChange(value -> System.out.println(value));
        this.settings.getWindowHeight().onChange(value -> System.out.println(value));
        this.settings.getWindowWidth().onChange(value -> System.out.println(value));
        this.settings.getFullscreen().onChange(value -> System.out.println(value));
    }

    public GameContainerSettings getSettings()
    {
        return this.settings;
    }

    public void setGameLoop(GameLoop loop)
    {
        if (this.loop != null && this.loop.isRunning())
        {
            throw new GameContainerException("Previously set loop is already active");
        }

        this.loop = loop;
    }

    protected void createWindow()
    {
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFWErrorCallback.createPrint(System.err).set();

        this.window = new Window(this.settings.getWindowWidth().get(),
                                 this.settings.getWindowHeight().get(),
                                 this.settings.getTitle().get(),
                                 this.settings.getFullscreen().get(),
                                 0);

        this.window.showWindow();

        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glOrtho(0.f, this.settings.getWindowWidth().get(), this.settings.getWindowHeight().get(), 0.f, 0.f, 1.f);

        Threads.get().schedule(() -> this.window.updateWindowSize(1024, 860), 4, TimeUnit.SECONDS);
    }

    public void tick(double delta)
    {
        // TODO forward tick call to scene

        glfwPollEvents();

        if (this.window.isShouldClose())
        {
            kill();
        }
    }

    public void render()
    {
        // TODO forward render call to scene

        this.window.beforeRender();

        glColor4f(1, 0, 0, 0);

        glBegin(GL_LINES);
        glVertex3f(0, 0, 0);
        glVertex3f(this.settings.getWindowWidth().get() / 2, this.settings.getWindowHeight().get() / 2, 0);
        glEnd();

        glBegin(GL_LINES);
        glVertex3f(this.settings.getWindowWidth().get(), 0, 0);
        glVertex3f(this.settings.getWindowWidth().get() / 2, this.settings.getWindowHeight().get() / 2, 0);
        glEnd();

        glBegin(GL_LINES);
        glVertex3f(this.settings.getWindowWidth().get() / 2, this.settings.getWindowHeight().get() / 2, 0);
        glVertex3f(this.settings.getWindowWidth().get() / 2, this.settings.getWindowHeight().get(), 0);
        glEnd();

        this.window.afterRender();
    }

    @Override
    public void kill()
    {
        // TODO kill window

        this.loop.kill();
        this.window.kill();
    }

    @Override
    public void run()
    {
        if (this.loop == null)
        {
            this.loop = createDefaultGameLoop();
        }

        this.loop.run();
    }

    protected GameLoop createDefaultGameLoop()
    {
        GameLoop defaultLoop = new GameLoop(this::tick, this::render);
        defaultLoop.setFrameRate(60);
        defaultLoop.setTickRate(60);
        defaultLoop.setRateChecksPerSecond(2);
        return defaultLoop;
    }
}