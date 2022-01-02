package bt2d.core.container;

import bt.log.Log;
import bt.scheduler.Threads;
import bt.types.Killable;
import bt2d.core.container.exc.GameContainerException;
import bt2d.core.container.settings.GameContainerSettings;
import bt2d.core.container.settings.exc.SettingsChangeException;
import bt2d.core.input.key.KeyActions;
import bt2d.core.input.key.KeyInput;
import bt2d.core.loop.GameLoop;
import bt2d.core.scene.Scene;
import bt2d.core.scene.obj.ScenePair;
import bt2d.core.window.Window;
import bt2d.resource.load.exc.LoadException;
import bt2d.utils.Unit;
import bt2d.utils.timer.TimerActions;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
     * A set of key actions that can be freely configured to setup global triggers.
     */
    protected KeyActions keyActions;

    /**
     * This containers key input instacne which is used to check pressed keys.
     */
    protected KeyInput keyInput;

    /**
     * A set of timer actions that can be freely configured to setup global delay based triggers.
     */
    protected TimerActions timerActions;

    /**
     * The currently active scene.
     */
    protected Scene currentScene;

    /**
     * The name of the requested scene if there is any.
     */
    protected String requestedSceneName;

    /**
     * Indicates whether a new scene was requested and should be loaded.
     */
    protected boolean sceneRequested;

    /**
     * A mapping of scenes to unique names. The entries will hold the main scene and an
     * optional (may be null) loading scene.
     */
    protected Map<String, ScenePair> scenes;

    /**
     * Instantiates a new Game container.
     *
     * @param settings the settings that will be bound by this container. Changes to the properties                 of this settings instance will be reflected by the container.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public GameContainer(GameContainerSettings settings)
    {
        Objects.requireNonNull(settings, "settings cant be null");
        this.settings = settings;
        this.keyActions = new KeyActions();
        this.timerActions = new TimerActions();
        this.scenes = new HashMap<>();
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
        this.settings.getTitle().addChangeListener(title -> this.window.setWindowTitle(title));

        this.settings.getUndecorated().addChangeListener(undecorated -> {
            throw new SettingsChangeException("Cant change undecorated property after the window was created");
        });

        this.settings.getWindowSize().addChangeListener((width, height) -> {
            this.window.updateWindowSize(width, height);
        });

        this.settings.getFullscreen().addChangeListener(fullscreen -> {
            this.window.setFullScreenMode(fullscreen);
        });

        this.settings.getMaximized().addChangeListener(maximized -> {
            this.window.setMaximized(maximized);
        });

        this.settings.getStrictAspectRatio().addChangeListener(strictAspectRatio -> {
            this.window.setStrictAspectRatio(strictAspectRatio);
        });

        this.settings.getGameUnitWidth().addChangeListener(gameUnits -> Unit.setRatio(this.window.getWidth() / gameUnits));
    }

    /**
     * Gets the settings instance that is bound by this container.
     * <p>
     * Changes to this settings instances properties will be reflected by this container.
     * I.e. changing the properties for the window size will resize the window of the container.
     *
     * @return the settings instance that was given to this container and whichs properties are reflected by this instance.
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
        Objects.requireNonNull(loop, "loop cant be null");

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
                                 this.settings.getUndecorated().get(),
                                 this.settings.getStrictAspectRatio().get(),
                                 60);

        // set ratio based on settings and calculate unit size for this container
        Unit.setRatio(this.window.getWidth() / this.settings.getGameUnitWidth().get());
        this.width = Unit.forGlUnits(this.window.getWidth());
        this.height = Unit.forGlUnits(this.window.getHeight());

        this.window.setMaximized(this.settings.getMaximized().get());

        this.window.showWindow();

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glOrtho(0.f,
                getWidth().glUnits(),
                getHeight().glUnits(),
                0.f,
                0.f,
                1.f);
    }

    /**
     * Creates the {@link KeyInput} instance of this container.
     * <p>
     * The setup instance will be available via {@link #getKeyInput()}.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    protected void setupKeyInput()
    {
        this.keyInput = new KeyInput(this.window.getWindow());
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
        // if a new scene was requested switch now
        // to avoid complications during the current process and the killing of the old scene at the same time
        if (this.sceneRequested)
        {
            try
            {
                loadScene(this.requestedSceneName);
            }
            catch (LoadException e)
            {
                Log.error("Error during loading of requested scene", e);
                kill();
                return;
            }

            this.sceneRequested = false;
        }

        glfwPollEvents();

        this.keyInput.checkKeyChanges();
        this.keyActions.checkActions(this.keyInput);
        this.timerActions.checkActions(delta);

        if (this.currentScene != null)
        {
            this.currentScene.tick(delta);
        }

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
        this.window.beforeRender();

        if (this.currentScene != null)
        {
            this.currentScene.render(this.settings.getDebugRendering().get());
        }

        // TODO remove test rendering
        glColor4f(0, 0, 1, 0);

        glBegin(GL_LINES);
        glVertex3f(0, 0, 0);
        glVertex3f((float)getWidth().divideBy(2).glUnits(), (float)getHeight().divideBy(2).glUnits(), 0);
        glEnd();

        glBegin(GL_LINES);
        glVertex3f((float)getWidth().glUnits(), 0, 0);
        glVertex3f((float)getWidth().divideBy(2).glUnits(), (float)getHeight().divideBy(2).glUnits(), 0);
        glEnd();

        glBegin(GL_LINES);
        glVertex3f((float)getWidth().divideBy(2).glUnits(), (float)getHeight().divideBy(2).glUnits(), 0);
        glVertex3f((float)getWidth().divideBy(2).glUnits(), (float)getHeight().glUnits(), 0);
        glEnd();

        glColor4f(1, 0, 0, 0);

        glBegin(GL_QUADS);
        glVertex3d(getWidth().divideBy(3).glUnits(), getHeight().divideBy(2).glUnits(), 0);
        glVertex3d(getWidth().divideBy(3).glUnits(), getHeight().divideBy(3).glUnits(), 0);
        glVertex3d(getWidth().divideBy(2).glUnits(), getHeight().divideBy(3).glUnits(), 0);
        glVertex3d(getWidth().divideBy(2).glUnits(), getHeight().divideBy(2).glUnits(), 0);
        glEnd();

        glColor4f(0, 1, 0, 0);

        glBegin(GL_QUADS);
        glVertex3d(Unit.forGameUnits(10).glUnits(), Unit.forGameUnits(10).glUnits(), 0);
        glVertex3d(Unit.forGameUnits(15).glUnits(), Unit.forGameUnits(10).glUnits(), 0);
        glVertex3d(Unit.forGameUnits(10).glUnits(), Unit.forGameUnits(15).glUnits(), 0);
        glVertex3d(Unit.forGameUnits(15).glUnits(), Unit.forGameUnits(15).glUnits(), 0);
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
        Log.debug("Killing GameContainer");
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
        Log.entry();
        createWindow();
        bindSettings();
        setupKeyInput();

        if (this.loop == null)
        {
            this.loop = createDefaultGameLoop();
        }

        this.loop.run();
        Log.exit();
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

    /**
     * Returns a set of key actions that can be extended.
     * <p>
     * This can be used to add global key actions to this container.
     *
     * @return The key actions which were setup for this container.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public KeyActions getKeyActions()
    {
        return this.keyActions;
    }

    /**
     * Gets {@link KeyInput} instance that was setup for this container.
     * <p>
     * The returned instance can be used to check if certain keys were pressed.
     *
     * @return The key input instance setup for this container.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public KeyInput getKeyInput()
    {
        return this.keyInput;
    }

    /**
     * Returns a set of timer actions that can be extended.
     * <p>
     * This can be used to add global delay based actions to this container which will be executed on the main thread.
     *
     * @return The timer actions which were setup for this container.
     *
     * @author Lukas Hartwig
     * @since 04.11.2021
     */
    public TimerActions getTimerActions()
    {
        return this.timerActions;
    }

    /**
     * Requests a new scene to be loaded after the current render iteration.
     *
     * <p>
     * This will cause the container to properly {@link Scene#kill() kill} the current scene. The new main scene will be
     * loaded in a different thread. During the loading of the main scene the set loading scene is played (if it
     * exists).
     * </p>
     *
     * @param sceneName The name of the scene that it was mapped by.
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public void requestScene(String sceneName)
    {
        Log.entry(sceneName);
        Objects.requireNonNull(sceneName, "sceneName cant be null");

        this.requestedSceneName = sceneName;
        this.sceneRequested = true;
        Log.exit();
    }

    /**
     * Loads the {@link Scene} to be displayed. This will properly {@link Scene#kill() kill} the current scene. The new
     * main scene will be loaded in a different thread. During the loading of the main scene the set loading scene is
     * played (if it exists).
     *
     * @param name The name of the scene that should be played.
     *
     * @throws LoadException If there is no mapping for the scene or anything goes wrong during the loading process.
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    protected void loadScene(String name) throws LoadException
    {
        Log.entry(name);
        Objects.requireNonNull(name, "name cant be null");

        ScenePair pair = this.scenes.get(name);

        if (pair == null)
        {
            // no mapping for given name
            throw new LoadException("Cant find mapped scene for name '" + name + "'");
        }

        Scene mainScene = pair.getMainScene();
        Scene loadingScene = pair.getLoadingScene();

        if (loadingScene != null)
        {
            loadScene(loadingScene, name);
        }

        Threads.get().executeCached(() -> {
            try
            {
                loadScene(mainScene, name);
            }
            catch (LoadException e)
            {
                Log.error("Error during loading of main scene", e);
                kill();
            }
        });

        Log.exit();
    }

    /**
     * Loads the given scene, sets it asctive and starts it.
     *
     * @param scene       the scene
     * @param contextName the context name that is passed to the scenes load method.
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    protected void loadScene(Scene scene, String contextName) throws LoadException
    {
        Log.entry(scene, contextName);
        scene.load(contextName);
        scene.onStart();
        setScene(scene);
        Log.exit();
    }

    /**
     * Sets the given scene. This kills the current scene if it does not equal the given one.
     *
     * @param scene the scene
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    protected void setScene(Scene scene)
    {
        Log.entry(scene);
        Objects.requireNonNull(scene, "scene cant be null");

        if (this.currentScene != null && !this.currentScene.equals(scene))
        {
            this.currentScene.kill();
        }

        this.currentScene = scene;

        Log.exit();
    }

    /**
     * Adds the given main scene and loading scene and maps them to the given name.
     *
     * <p>
     * The loading scene may be null. If it is not null it will be displayed while the main scene is loading.
     * </p>
     * The name needs to be used to request the scene via {@link #requestScene(String)}.
     *
     * @param name         the name that this scene will be mapped by.
     * @param mainScene    the main scene
     * @param loadingScene the loading scene that is displayed while the main scene is loaded.
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public void addScene(String name, Scene mainScene, Scene loadingScene)
    {
        Log.entry(name, mainScene, loadingScene);
        Objects.requireNonNull(name, "name cant be null");
        Objects.requireNonNull(mainScene, "mainScene cant be null");

        mainScene.setGameContainer(this);

        if (loadingScene != null)
        {
            loadingScene.setGameContainer(this);
        }

        this.scenes.put(name, new ScenePair(mainScene,
                                            loadingScene));

        Log.exit();
    }

    /**
     * Adds the given scene and maps it to the given name.
     * <p>
     * The name needs to be used to request the scene via {@link #requestScene(String)}.
     *
     * <p>
     * This is a convinience method for
     *
     * <pre>
     * {@link #addScene(String, Scene, Scene) addScene(name, scene, null);}
     * </pre>
     * </p>
     *
     * @param name  the name that this scene will be mapped by.
     * @param scene the scene
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public void addScene(String name, Scene scene)
    {
        addScene(name, scene, null);
    }

    /**
     * Gets currently active scene.
     *
     * @return the current scene
     *
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public Scene getCurrentScene()
    {
        return this.currentScene;
    }
}