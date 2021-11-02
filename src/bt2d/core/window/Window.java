package bt2d.core.window;

import bt.types.Killable;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window implements Killable
{

    protected long window;

    protected boolean shouldClose;

    protected boolean fullScreenMode;

    protected boolean successFullyCreatedWindow = false;

    protected int width;

    protected int height;

    protected int xPos;

    protected int yPos;

    protected long monitor;

    protected int refreshRate;

    protected String windowTitle;

    protected GLFWVidMode videoMode;

    public Window(int width, int height, String title, boolean fullScreen, boolean undecorated, int refreshRate)
    {
        this.height = height;
        this.width = width;
        this.windowTitle = title;
        this.fullScreenMode = fullScreen;
        this.refreshRate = refreshRate;
        this.shouldClose = false;

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, undecorated ? GLFW_FALSE : GLFW_TRUE);

        this.monitor = glfwGetPrimaryMonitor();

        this.window = glfwCreateWindow(width, height, title, fullScreenMode ? monitor : 0, 0);

        if (window == 0)
        {
            shouldClose = true;
            throw new IllegalStateException("Unable to create Window");
        }

        glfwMakeContextCurrent(this.window);

        successFullyCreatedWindow = true;
        videoMode = glfwGetVideoMode(monitor);
        if (videoMode != null)
        {
            xPos = (videoMode.width() - width) / 2;
            yPos = (videoMode.height() - height) / 2;
            glfwSetWindowPos(window, xPos, yPos);
        }

    }

    public long getWindow()
    {
        return window;
    }

    public void setWindow(long window)
    {
        this.window = window;
    }

    public boolean isShouldClose()
    {
        return shouldClose || glfwWindowShouldClose(this.window);
    }

    public void setShouldClose(boolean shouldClose)
    {
        this.shouldClose = shouldClose;
        glfwSetWindowShouldClose(window, shouldClose);
    }

    public boolean isFullScreenMode()
    {
        return fullScreenMode;
    }

    public void setFullScreenMode(boolean fullScreenMode)
    {
        // TODO I think we need to set width and height for fullscreen as well to be able to retrieve this information in the game container
        // this is relevant for calculating the pixels to unit ratio when switching to fullscreen or back
        if (this.fullScreenMode != fullScreenMode)
        {
            boolean priorState = this.fullScreenMode;
            this.fullScreenMode = fullScreenMode;

            if (!priorState)
            {
                xPos = 0;
                yPos = 0;
                glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), xPos, yPos, videoMode.width(), videoMode.width(), refreshRate);
            }
            else
            {
                xPos = (videoMode.width() - width) / 2;
                yPos = (videoMode.height() - height) / 2;
                glfwSetWindowMonitor(window, 0, xPos, yPos, width, height, 0);
            }
        }
    }

    public boolean isSuccessFullyCreatedWindow()
    {
        return successFullyCreatedWindow;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getWindowTitle()
    {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle)
    {
        this.windowTitle = windowTitle;
        glfwSetWindowTitle(window, windowTitle);
    }

    public void updateWindowSize(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.xPos = (videoMode.width() - width) / 2;
        this.yPos = (videoMode.height() - height) / 2;
        glfwSetWindowSize(window, width, height);
        glfwSetWindowPos(window, xPos, yPos);
    }

    public void showWindow()
    {
        glfwShowWindow(window);
    }

    public void beforeRender()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void afterRender()
    {
        glfwSwapBuffers(this.window);
    }

    @Override
    public void kill()
    {
        glfwFreeCallbacks(this.window);
        glfwDestroyWindow(this.window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
