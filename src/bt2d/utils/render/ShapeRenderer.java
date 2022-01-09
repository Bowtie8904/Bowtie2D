package bt2d.utils.render;

import bt2d.utils.Unit;

import static org.lwjgl.opengl.GL11.*;

/**
 * A utility class to render basic shapes primarely for debugging purposes.
 *
 * @author Lukas Hartwig
 * @since 09.01.2022
 */
public final class ShapeRenderer
{
    private static Color defaultColor = Color.WHITE;

    /**
     * Sets the default color that is used by methods of this class if no other color was given.
     *
     * @param color the color
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void setDefaultColor(Color color)
    {
        defaultColor = color;
    }

    /**
     * Fills a straight rectangle. The upper left corner of the rectangle will be at gameUnitX|gameUnitY and
     * the bottom right corner will be at gameUnitX + gameUnitWidth|gameUnitY + gameUnitHeight.
     * <p>
     * The rectangle will be filled with the default color.
     *
     * @param gameUnitX      the game unit x
     * @param gameUnitY      the game unit y
     * @param gameUnitWidth  the game unit width
     * @param gameUnitHeight the game unit height
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void fillRectangle(double gameUnitX, double gameUnitY, double gameUnitWidth, double gameUnitHeight)
    {
        fillRectangle(gameUnitX, gameUnitY, gameUnitWidth, gameUnitHeight, defaultColor);
    }

    /**
     * Fills a straight rectangle. The upper left corner of the rectangle will be at gameUnitX|gameUnitY and
     * the bottom right corner will be at gameUnitX + gameUnitWidth|gameUnitY + gameUnitHeight.
     * <p>
     * The rectangle will be filled with the given color.
     *
     * @param gameUnitX      the game unit x
     * @param gameUnitY      the game unit y
     * @param gameUnitWidth  the game unit width
     * @param gameUnitHeight the game unit height
     * @param color          the color
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void fillRectangle(double gameUnitX, double gameUnitY, double gameUnitWidth, double gameUnitHeight, Color color)
    {
        fillRectangle(Unit.forGameUnits(gameUnitX),
                      Unit.forGameUnits(gameUnitY),
                      Unit.forGameUnits(gameUnitWidth),
                      Unit.forGameUnits(gameUnitHeight),
                      color);
    }

    /**
     * Fills a straight rectangle. The upper left corner of the rectangle will be at x|y and
     * the bottom right corner will be at x + width|y + height.
     * <p>
     * The rectangle will be filled with the default color.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void fillRectangle(Unit x, Unit y, Unit width, Unit height)
    {
        fillRectangle(x, y, width, height, defaultColor);
    }

    /**
     * Fills a straight rectangle. The upper left corner of the rectangle will be at x|y and
     * the bottom right corner will be at x + width|y + height.
     * <p>
     * The rectangle will be filled with the given color.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param color  the color
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void fillRectangle(Unit x, Unit y, Unit width, Unit height, Color color)
    {
        glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        glBegin(GL_QUADS);
        glVertex3d(x.glUnits(), y.glUnits(), 0);
        glVertex3d(x.addGameUnits(width).glUnits(), y.glUnits(), 0);
        glVertex3d(x.addGameUnits(width).glUnits(), y.addGameUnits(height).glUnits(), 0);
        glVertex3d(x.glUnits(), y.addGameUnits(height).glUnits(), 0);
        glEnd();
    }

    /**
     * Draws a straight rectangle. The upper left corner of the rectangle will be at gameUnitX|gameUnitY and
     * the bottom right corner will be at gameUnitX + gameUnitWidth|gameUnitY + gameUnitHeight.
     * <p>
     * The rectangle will be outlined with the default color.
     * <p>
     * This is just a convenience method that calls {@link #line(Unit, Unit, Unit, Unit, Color)} for the four borders.
     *
     * @param gameUnitX      the game unit x
     * @param gameUnitY      the game unit y
     * @param gameUnitWidth  the game unit width
     * @param gameUnitHeight the game unit height
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void drawRectangle(double gameUnitX, double gameUnitY, double gameUnitWidth, double gameUnitHeight)
    {
        drawRectangle(gameUnitX, gameUnitY, gameUnitWidth, gameUnitHeight, defaultColor);
    }

    /**
     * Draws a straight rectangle. The upper left corner of the rectangle will be at gameUnitX|gameUnitY and
     * the bottom right corner will be at gameUnitX + gameUnitWidth|gameUnitY + gameUnitHeight.
     * <p>
     * The rectangle will be outlined with the given color.
     * <p>
     * This is just a convenience method that calls {@link #line(Unit, Unit, Unit, Unit, Color)} for the four borders.
     *
     * @param gameUnitX      the game unit x
     * @param gameUnitY      the game unit y
     * @param gameUnitWidth  the game unit width
     * @param gameUnitHeight the game unit height
     * @param color          the color
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void drawRectangle(double gameUnitX, double gameUnitY, double gameUnitWidth, double gameUnitHeight, Color color)
    {
        drawRectangle(Unit.forGameUnits(gameUnitX),
                      Unit.forGameUnits(gameUnitY),
                      Unit.forGameUnits(gameUnitWidth),
                      Unit.forGameUnits(gameUnitHeight),
                      color);
    }

    /**
     * Draws a straight rectangle. The upper left corner of the rectangle will be at x|y and
     * the bottom right corner will be at x + width|y + height.
     * <p>
     * The rectangle will be outlined with the default color.
     * <p>
     * This is just a convenience method that calls {@link #line(Unit, Unit, Unit, Unit, Color)} for the four borders.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void drawRectangle(Unit x, Unit y, Unit width, Unit height)
    {
        drawRectangle(x, y, width, height, defaultColor);
    }

    /**
     * Draws a straight rectangle. The upper left corner of the rectangle will be at x|y and
     * the bottom right corner will be at x + width|y + height.
     * <p>
     * The rectangle will be outlined with the given color.
     * <p>
     * This is just a convenience method that calls {@link #line(Unit, Unit, Unit, Unit, Color)} for the four borders.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param color  the color
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void drawRectangle(Unit x, Unit y, Unit width, Unit height, Color color)
    {
        line(x, y, x.addGameUnits(width), y, color);
        line(x, y, x, y.addGameUnits(height), color);
        line(x, y.addGameUnits(height), x.addGameUnits(width), y.addGameUnits(height), color);
        line(x.addGameUnits(width), y, x.addGameUnits(width), y.addGameUnits(height), color);
    }

    /**
     * Draws a line from gameUnitX1|gameUnitY1 to gameUnitX2|gameUnitY2.
     * <p>
     * The line will have the default color.
     *
     * @param gameUnitX1 the game unit x 1
     * @param gameUnitY1 the game unit y 1
     * @param gameUnitX2 the game unit x 2
     * @param gameUnitY2 the game unit y 2
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void line(double gameUnitX1, double gameUnitY1, double gameUnitX2, double gameUnitY2)
    {
        line(gameUnitX1,
             gameUnitY1,
             gameUnitX2,
             gameUnitY2,
             defaultColor);
    }

    /**
     * Draws a line from gameUnitX1|gameUnitY1 to gameUnitX2|gameUnitY2.
     * <p>
     * The line will have the given color.
     *
     * @param gameUnitX1 the game unit x 1
     * @param gameUnitY1 the game unit y 1
     * @param gameUnitX2 the game unit x 2
     * @param gameUnitY2 the game unit y 2
     * @param color      the color
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void line(double gameUnitX1, double gameUnitY1, double gameUnitX2, double gameUnitY2, Color color)
    {
        line(Unit.forGameUnits(gameUnitX1),
             Unit.forGameUnits(gameUnitY1),
             Unit.forGameUnits(gameUnitX2),
             Unit.forGameUnits(gameUnitY2),
             color);
    }

    /**
     * Draws a line from x1|y1 to x2|y2.
     * <p>
     * The line will have the default color.
     *
     * @param x1 the x 1
     * @param y1 the y 1
     * @param x2 the x 2
     * @param y2 the y 2
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void line(Unit x1, Unit y1, Unit x2, Unit y2)
    {
        line(x1, y1, x2, y2, defaultColor);
    }

    /**
     * Draws a line from x1|y1 to x2|y2.
     * <p>
     * The line will have the given color.
     *
     * @param x1    the x 1
     * @param y1    the y 1
     * @param x2    the x 2
     * @param y2    the y 2
     * @param color the color
     *
     * @author Lukas Hartwig
     * @since 09.01.2022
     */
    public static void line(Unit x1, Unit y1, Unit x2, Unit y2, Color color)
    {
        glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        glBegin(GL_LINES);
        glVertex3d(x1.glUnits(), y1.glUnits(), 0);
        glVertex3d(x2.glUnits(), y2.glUnits(), 0);
        glEnd();
    }
}