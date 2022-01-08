package bt2d.core.window;

import bt.types.Killable;
import bt.utils.Null;
import bt2d.core.container.exc.GameContainerException;
import bt2d.utils.log.glfw.DefaultGLFWErrorCallback;
import bt2d.utils.log.lwjgl.DefaultLWJGLDebugOutputStream;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryStack;

import java.io.PrintStream;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Class used to monitor and change the properties of the displayed window
 * <p>
 * Any and all changes done to the windows properties should be done through this class.
 *
 * @author Marc Hermes
 * @since 01-11-2021
 */
public class Window implements Killable
{

    /**
     * ID of this window
     */
    protected long window;

    /**
     * Indicates whether this window should be closed
     */
    protected boolean shouldClose;

    /**
     * Indicates if this window is in full screen mode
     */
    protected boolean fullScreenMode;

    /**
     * Indicates if the window is currently maximized
     */
    protected boolean maximized;

    /**
     * Width of the window (resolution)
     */
    protected int width;

    /**
     * Height of the window (resolution)
     */
    protected int height;

    /**
     * Original width of the window before maximization
     */
    protected int originalWidth;

    /**
     * Original height of the window before maximization
     */
    protected int originalHeight;

    /**
     * Absolute x-position on the screen
     */
    protected int xPos;

    /**
     * Absolute y-position on the screen
     */
    protected int yPos;

    /**
     * ID of the monitor of the window
     */
    protected long monitor;

    /**
     * Refresh rate of the window in hertz
     */
    protected int refreshRate;

    /**
     * The displayed title of the window
     */
    protected String windowTitle;

    /**
     * The video mode of the monitor of the window
     */
    protected GLFWVidMode videoMode;

    /**
     * Aspect ratio of this window calculated when initially giving size.
     */
    protected double aspectRatio;

    /**
     * Indicates that the window should keep its aspect ratio no matter the frame size.
     */
    protected boolean strictAspectRatio;

    /**
     * Basic constructor for the Window class
     *
     * @param width             width of the window
     * @param height            height of the window
     * @param title             displayed title of the window
     * @param fullScreen        indicates whether the window starts in full screen
     * @param strictAspectRatio indicates whether the aspect ratio of the initial window size should be kept
     * @param refreshRate       refresh rate of the window in hertz
     *
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public Window(int width, int height, String title, boolean fullScreen, boolean undecorated, boolean strictAspectRatio, int refreshRate)
    {
        // set logging callbacks
        Configuration.DEBUG_STREAM.set(createLWJGLDebugPrintStream());
        GLFWErrorCallback.create(createGLFWErrorCallback()).set();

        if (!glfwInit())
        {
            throw new GameContainerException("Unable to initialize GLFW");
        }

        if (width <= 0)
        {
            throw new IllegalArgumentException("Width has to be above 0");
        }

        if (height <= 0)
        {
            throw new IllegalArgumentException("Height has to be above 0");
        }

        this.strictAspectRatio = strictAspectRatio;

        this.height = height;
        this.width = width;

        this.aspectRatio = (double)width / height;

        this.originalHeight = height;
        this.originalWidth = width;
        this.windowTitle = Null.nullValue(title, "");
        this.fullScreenMode = fullScreen;
        this.refreshRate = refreshRate;
        this.shouldClose = false;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, undecorated ? GLFW_FALSE : GLFW_TRUE);

        this.monitor = glfwGetPrimaryMonitor();

        this.window = glfwCreateWindow(width, height, windowTitle, fullScreenMode ? monitor : 0, 0);

        if (window == 0)
        {
            shouldClose = true;
            throw new IllegalStateException("Unable to create Window");
        }

        glfwMakeContextCurrent(this.window);
        GL.createCapabilities();
        glfwSetFramebufferSizeCallback(window, this::framebufferSizeCallback);

        videoMode = glfwGetVideoMode(monitor);
        if (videoMode != null)
        {
            xPos = (videoMode.width() - width) / 2;
            yPos = (videoMode.height() - height) / 2;
            glfwSetWindowPos(window, xPos, yPos);
        }
    }

    /**
     * Creates a suitable {@link GLFWErrorCallback} instance.
     * <p>
     * This method is called during the constructor of this class and set as the active GLFW error callback.
     *
     * @return the glfw error callback. By default this is an instance of {@link DefaultGLFWErrorCallback}.
     *
     * @author Lukas Hartwig
     * @since 06.01.2022
     */
    protected GLFWErrorCallback createGLFWErrorCallback()
    {
        return new DefaultGLFWErrorCallback();
    }

    /**
     * Creates a suitable {@link PrintStream} instance.
     * <p>
     * This method is called during the constructor of this class and set as the active LWJGL debug printstream.
     *
     * @return the print stream. By default this is an instance of {@link PrintStream} with an outputstream of type {@link DefaultLWJGLDebugOutputStream}.
     *
     * @author Lukas Hartwig
     * @since 06.01.2022
     */
    protected PrintStream createLWJGLDebugPrintStream()
    {
        return new PrintStream(new DefaultLWJGLDebugOutputStream());
    }

    /**
     * Returns the ID of this window
     *
     * @return ID of the window
     *
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public long getWindow()
    {
        return window;
    }

    /**
     * Sets the monitored window to a different one
     *
     * @param window the ID of the window
     *
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public void setWindow(long window)
    {
        this.window = window;
    }

    /**
     * Returns true if the window should be closed
     *
     * @return true if window is or should be closed
     *
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public boolean isShouldClose()
    {
        return shouldClose || glfwWindowShouldClose(this.window);
    }

    /**
     * Sets the shouldClose variable indicating whether the window should be closed
     *
     * @param shouldClose the boolean value
     *
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public void setShouldClose(boolean shouldClose)
    {
        this.shouldClose = shouldClose;
        glfwSetWindowShouldClose(window, shouldClose);
    }

    /**
     * Returns true if the window is currently in full screen mode
     *
     * @return true if in full screen mode
     *
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public boolean isFullScreenMode()
    {
        return fullScreenMode;
    }

    /**
     * This method sets the fullScreenMode variable for the window class and immediately applies the visual change.
     * <p>
     * When leaving the full screen mode, the window will be properly centered on the monitor.
     *
     * @param fullScreenMode true will change to full screen mode, false will apply windowed state
     *
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public void setFullScreenMode(boolean fullScreenMode)
    {

        if (this.fullScreenMode != fullScreenMode)
        {
            boolean priorState = this.fullScreenMode;
            this.fullScreenMode = fullScreenMode;

            if (!priorState)
            {
                xPos = 0;
                yPos = 0;
                glfwSetWindowMonitor(window, monitor, xPos, yPos, width, height, refreshRate);
            }
            else
            {

                try (MemoryStack stack = stackPush())
                {
                    IntBuffer pLeft = stack.mallocInt(1); // int*
                    IntBuffer pTop = stack.mallocInt(1); // int*
                    IntBuffer pRight = stack.mallocInt(1); // int*
                    IntBuffer pBottom = stack.mallocInt(1); // int*
                    IntBuffer pX = stack.mallocInt(1); // int*
                    IntBuffer pY = stack.mallocInt(1); // int*
                    IntBuffer pWidth = stack.mallocInt(1); // int*
                    IntBuffer pHeight = stack.mallocInt(1); // int*

                    glfwSetWindowMonitor(window, 0, xPos, yPos, width, height, refreshRate);
                    glfwGetWindowFrameSize(window, pLeft, pTop, pRight, pBottom);
                    glfwGetMonitorWorkarea(monitor, pX, pY, pWidth, pHeight);

                    xPos = ((pWidth.get(0) - width) / 2) + pX.get(0);
                    yPos = ((pHeight.get(0) - height + pTop.get(0)) / 2) + pY.get(0);
                    glfwSetWindowPos(window, xPos, yPos);
                }
            }
        }
    }

    /**
     * @return the width of the window
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @param width the width of the window, doesn't update the displayed window size yet ( use updateWindowSize() instead )
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * @return the height of the window
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @param height the height of the window, doesn't update the displayed window size yet ( call updateWindowSize() instead )
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * @return the current title of the window
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public String getWindowTitle()
    {
        return windowTitle;
    }

    /**
     * @param windowTitle the new title for the window
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void setWindowTitle(String windowTitle)
    {
        this.windowTitle = windowTitle;
        glfwSetWindowTitle(window, windowTitle);
    }

    /**
     * Method used for maximizing/minimizing a window.
     * <p>
     * Calling this method will result in the maximization/minimization of the window. If the window is already
     * maximized/minimized nothing will happen.
     * Depending on the position of the taskbar (if there is one) and the available monitor work area the position
     * and size of the window is calculated and applied when maximizing. Minimizing returns the window to its original
     * size before it was maximized.
     * <p>
     * If this method is called while the window is in full screen mode, nothing will happen.
     *
     * @param maximized true implies the window should be maximized, false minimizes the window
     *
     * @author Marc Hermes
     * @since 09-11-2021
     */
    public void setMaximized(boolean maximized)
    {

        if (maximized != this.maximized && !fullScreenMode)
        {

            if (maximized)
            {

                originalWidth = this.width;
                originalHeight = this.height;

                try (MemoryStack stack = stackPush())
                {
                    IntBuffer pLeft = stack.mallocInt(1); // int*
                    IntBuffer pTop = stack.mallocInt(1); // int*
                    IntBuffer pRight = stack.mallocInt(1); // int*
                    IntBuffer pBottom = stack.mallocInt(1); // int*
                    IntBuffer pX = stack.mallocInt(1); // int*
                    IntBuffer pY = stack.mallocInt(1); // int*
                    IntBuffer pWidth = stack.mallocInt(1); // int*
                    IntBuffer pHeight = stack.mallocInt(1); // int*

                    glfwGetWindowFrameSize(this.window, pLeft, pTop, pRight, pBottom);
                    glfwGetMonitorWorkarea(monitor, pX, pY, pWidth, pHeight);

                    this.xPos = pX.get(0);
                    this.yPos = pTop.get(0) + pY.get(0);
                    this.width = pWidth.get(0);
                    this.height = pHeight.get(0) - pTop.get(0);

                    glfwSetWindowSize(this.window, width, height);
                    glfwSetWindowPos(this.window, xPos, yPos);
                    this.maximized = true;

                }
            }
            else
            {
                updateWindowSize(originalWidth, originalHeight);
            }

        }

    }

    /**
     * This method updates the window size with a new height and width value. The changes will immediately be reflected on the display.
     *
     * @param width  the new width for the window
     * @param height the new height for the window
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void updateWindowSize(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.xPos = (videoMode.width() - width) / 2;
        this.yPos = (videoMode.height() - height) / 2;
        glfwSetWindowSize(window, width, height);
        glfwSetWindowPos(window, xPos, yPos);
        this.maximized = false;
    }

    /**
     * Shows this window by calling glfwShowWindow()
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void showWindow()
    {
        glfwShowWindow(window);
    }

    /**
     * Method used before the actual render call.
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void beforeRender()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Method used after the actual render call.
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void afterRender()
    {
        glfwSwapBuffers(this.window);
    }

    /**
     * This method can be called to kill this window. This window will be destroyed. CAUTION: NOT ONLY WILL THE WINDOW
     * BE DESTROYED BUT ALSO glfwTerminate() is called, resulting in FREEING ALL ALLOCATED RESOURCES BY GLFW.
     *
     * @author Marc Hermes
     * @since 02-11-2021
     */
    @Override
    public void kill()
    {
        glfwFreeCallbacks(this.window);
        glfwDestroyWindow(this.window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * Indicates that the window should keep its aspect ratio no matter the frame size.
     *
     * @return true if the aspect ratio will be kept, false otherwise.
     *
     * @author Lukas Hartwig
     * @since 09.11.2021
     */
    public boolean isStrictAspectRatio()
    {
        return strictAspectRatio;
    }

    /**
     * Sets strict aspect ratio.
     * <p>
     * This will only take an effect after the next window resize.
     *
     * @param strictAspectRatio the strict aspect ratio
     *
     * @author Lukas Hartwig
     * @since 09.11.2021
     */
    public void setStrictAspectRatio(boolean strictAspectRatio)
    {
        this.strictAspectRatio = strictAspectRatio;
    }

    /**
     * Method called when the framebuffer size is changed, thus requiring a change of the glViewport values
     *
     * @param window the reference for the window
     * @param width  the new width
     * @param height the new height
     *
     * @author Marc Hermes
     * @since 09-11-2021
     */
    void framebufferSizeCallback(long window, int width, int height)
    {
        if (this.strictAspectRatio)
        {
            // keep original aspect ratio to avoid stretching when going into maximized for example
            glViewport(0, 0, width, (int)(width / this.aspectRatio));
        }
        else
        {
            glViewport(0, 0, width, height);
        }
    }
}