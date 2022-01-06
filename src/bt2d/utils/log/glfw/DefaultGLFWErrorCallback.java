package bt2d.utils.log.glfw;

import bt.log.Log;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.APIUtil;

import java.util.Map;

/**
 * @author Lukas Hartwig
 * @since 06.01.2022
 */
public class DefaultGLFWErrorCallback extends GLFWErrorCallback
{
    private Map<Integer, String> ERROR_CODES = APIUtil.apiClassTokens((field, value) -> 0x10000 < value && value < 0x20000, null, GLFW.class);

    @Override
    public void invoke(int error, long description)
    {
        String msg = getDescription(description);

        Log.error("[LWJGL] {} error", ERROR_CODES.get(error));
        Log.error("\tDescription: " + msg);
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        for (int i = 4; i < stack.length; i++)
        {
            Log.error("\t\t" + stack[i].toString());
        }
    }
}
