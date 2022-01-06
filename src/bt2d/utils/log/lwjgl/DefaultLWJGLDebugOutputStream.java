package bt2d.utils.log.lwjgl;

import bt.log.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Lukas Hartwig
 * @since 06.01.2022
 */
public class DefaultLWJGLDebugOutputStream extends OutputStream
{
    private StringBuffer buffer;

    public DefaultLWJGLDebugOutputStream()
    {
        this.buffer = new StringBuffer();
    }

    @Override
    public void write(int b) throws IOException
    {
        if ((char)b == '\n')
        {
            flush();
            return;
        }

        this.buffer.append((char)b);
    }

    @Override
    public void flush()
    {
        Log.debug(this.buffer.toString());
        this.buffer = new StringBuffer();
    }
}