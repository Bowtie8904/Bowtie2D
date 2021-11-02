package bt2d.core.window;

import bt.types.Killable;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Class used to monitor and change the properties of the displayed window
 *
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
     * Width of the window (resolution)
     */
    protected int width;

    /**
     * Height of the window (resolution)
     */
    protected int height;

    /**
     * Width of the window with which it was created
     */
    protected int originalWidth;

    /**
     * Height of the window with which it was created
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
     * Basic constructor for the Window class
     *
     * @param width width of the window
     * @param height height of the window
     * @param title displayed title of the window
     * @param fullScreen indicates whether the window starts in full screen
     * @param refreshRate refresh rate of the window in hertz
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public Window(int width, int height, String title, boolean fullScreen, int refreshRate) {

        this.height = height;
        this.width = width;
        this.windowTitle = title;
        this.fullScreenMode = fullScreen;
        this.refreshRate = refreshRate;
        this.shouldClose = false;
        this.originalWidth = width;
        this.originalHeight = height;

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        this.monitor = glfwGetPrimaryMonitor();

        this.window = glfwCreateWindow(width, height, title, fullScreenMode ? monitor : 0, 0);

        if (window == 0)
        {
            shouldClose = true;
            throw new IllegalStateException("Unable to create Window");
        }

        glfwMakeContextCurrent(this.window);

        videoMode = glfwGetVideoMode(monitor);
        if (videoMode != null)
        {
            xPos = (videoMode.width() - width) / 2;
            yPos = (videoMode.height() - height) / 2;
            glfwSetWindowPos(window, xPos, yPos);
        }

    }


    /**
     * Returns the ID of this window
     *
     * @return ID of the window
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public long getWindow() {
        return window;
    }

    /**
     * Sets the monitored window to a different one
     *
     * @param window the ID of the window
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public void setWindow(long window) {
        this.window = window;
    }

    /**
     * Returns true if the window should be closed
     *
     * @return true if window is or should be closed
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public boolean isShouldClose() {
        return shouldClose;
    }

    /**
     * Sets the shouldClose variable indicating whether the window should be closed
     *
     * @param shouldClose the boolean value
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public void setShouldClose(boolean shouldClose) {
        this.shouldClose = shouldClose;
        glfwSetWindowShouldClose(window, shouldClose);
    }

    /**
     * Returns true if the window is currently in full screen mode
     *
     * @return true if in full screen mode
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public boolean isFullScreenMode() {
        return fullScreenMode;
    }

    /**
     * This method sets the fullScreenMode variable for the window class and immediately applies the change.
     *
     * @param fullScreenMode true will change to full screen mode, false will apply windowed state
     * @author Marc Hermes
     * @since 01-11-2021
     */
    public void setFullScreenMode(boolean fullScreenMode) {
        // TODO I think we need to set width and height for fullscreen as well to be able to retrieve this information in the game container
        // this is relevant for calculating the pixels to unit ratio when switching to fullscreen or back
        if (this.fullScreenMode != fullScreenMode) {
            boolean priorState = this.fullScreenMode;
            this.fullScreenMode = fullScreenMode;

            if (!priorState) {
                xPos = 0;
                yPos = 0;
                glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), xPos, yPos, videoMode.width(), videoMode.height(), refreshRate);
                height = videoMode.height();
                width = videoMode.width();
            } else {
                xPos = (videoMode.width() - originalWidth) / 2;
                yPos = (videoMode.height() - originalHeight) / 2;
                glfwSetWindowMonitor(window, 0, xPos, yPos, originalWidth, originalHeight, 0);
                height = originalHeight;
                width = originalWidth;
            }
        }
    }

    /**
     *
     * @return the width of the window
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @param width the width of the window, doesn't update the displayed window size yet ( use updateWindowSize() instead )
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     *
     * @return the height of the window
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public int getHeight()
    {
        return height;
    }

    /**
     *
     * @param height the height of the window, doesn't update the displayed window size yet ( call updateWindowSize() instead )
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     *
     * @return width of the window with which it was created
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public int getOriginalWidth() {
        return originalWidth;
    }

    /**
     *
     * @return height of the window with which it was created
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public int getOriginalHeight() {
        return originalHeight;
    }

    /**
     *
     * @return the current title of the window
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public String getWindowTitle()
    {
        return windowTitle;
    }

    /**
     *
     * @param windowTitle the new title for the window
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void setWindowTitle(String windowTitle)
    {
        this.windowTitle = windowTitle;
        glfwSetWindowTitle(window, windowTitle);
    }

    /**
     * This method updates the window size with a new height and width value. The changes will immediately be reflected on the display.
     *
     * @param width the new width for the window
     * @param height the new height for the window
     * @author Marc Hermes
     * @since 02-11-2021
     */
    public void updateWindowSize(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.originalWidth = width;
        this.originalHeight = height;
        this.xPos = (videoMode.width() - width) / 2;
        this.yPos = (videoMode.height() - height) / 2;
        glfwSetWindowSize(window, width, height);
        glfwSetWindowPos(window, xPos, yPos);
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
}
