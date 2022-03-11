package bt2d.utils.render;

/**
 * A simple class to store RGBA values for a color. This class is basically a simpler copy of {@link java.awt.Color}
 * and is used to convert from ranges 0-255 to openGL ranges 0-1.
 *
 * @author Lukas Hartwig
 * @since 09.01.2022
 */
public class Color
{
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color LIGHT_GRAY = new Color(192, 192, 192);
    public static final Color GRAY = new Color(128, 128, 128);
    public static final Color DARK_GRAY = new Color(64, 64, 64);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color PINK = new Color(255, 175, 175);
    public static final Color ORANGE = new Color(255, 200, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color MAGENTA = new Color(255, 0, 255);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color BLUE = new Color(0, 0, 255);

    private float r;
    private float g;
    private float b;
    private float a;

    /**
     * Instantiates a new Color.
     *
     * @param r the red component within a range of 0-255.
     * @param g the green component within a range of 0-255.
     * @param b the blue component within a range of 0-255.
     * @param a the alphacomponent within a range of 0-255.
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public Color(int r, int g, int b, int a)
    {
        // convert from range 0-255 to range 0-1
        this.r = r / 255.0f;
        this.g = g / 255.0f;
        this.b = b / 255.0f;
        this.a = a / 255.0f;

    }

    /**
     * Instantiates a new Color.
     *
     * @param r the red component within a range of 0-255.
     * @param g the green component within a range of 0-255.
     * @param b the blue component within a range of 0-255.
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public Color(int r, int g, int b)
    {
        this(r, g, b, 255);
    }

    /**
     * Instantiates a new Color.
     *
     * @param hexString the hex string in range #000000-#ffffff.
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public Color(String hexString)
    {
        Integer intval = Integer.decode(hexString);
        int i = intval.intValue();

        // get parts of the hex value and convert from range 0-255 to range 0-1
        this.r = ((i >> 16) & 0xFF) / 255.0f;
        this.g = ((i >> 8) & 0xFF) / 255.0f;
        this.b = (i & 0xFF) / 255.0f;
        this.a = 1.0f;
    }

    /**
     * Gets red component in a range of 0-1.
     *
     * @return the red
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public float getRed()
    {
        return r;
    }

    /**
     * Gets green component in a range of 0-1.
     *
     * @return the green
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public float getGreen()
    {
        return g;
    }

    /**
     * Gets blue component in a range of 0-1.
     *
     * @return the blue
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public float getBlue()
    {
        return b;
    }

    /**
     * Gets alpha component in a range of 0-1.
     *
     * @return the alpha
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public float getAlpha()
    {
        return a;
    }
}