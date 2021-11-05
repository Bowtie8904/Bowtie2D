package bt2d.utils;

/**
 * A measurement conversion class between OpenGL units and custom game units. This class uses a set ratio of OpenGl units per game unit
 * to do its calculations.
 * <p>
 * Goal of this class is to allow rendring objects with the same relative size no matter what size the window has.
 *
 * @author Lukas Hartwig
 * @since 01.11.2021
 */
public record Unit(double gameUnits)
{
    /**
     * A constant for zero units since its expected to be used frequently.
     */
    private static final Unit ZERO = Unit.forGameUnits(0);

    /**
     * The ratio of OpenGL units to game units. Describes how many OpenGL units are in one game unit.
     */
    private static double ratio = 1;

    /**
     * Gets the OpenGL units per game unit ratio. The returned value is the amount of OpenGL units one game unit consist of.
     *
     * @return ratio
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public static double getRatio()
    {
        return ratio;
    }

    /**
     * Sets the OpenGL unit per game unit ratio. The given value is the amount of OpenGL units one game unit should consist of. If this method
     * is not called, the default ratio of 1 will be used.
     *
     * @param glUnitsPerGameUnit the OpenGL units per game unit
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public static void setRatio(double glUnitsPerGameUnit)
    {
        Unit.ratio = glUnitsPerGameUnit;
    }

    /**
     * Returns a constant instance with a game unit and openGl unit value of zero.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public static Unit zero()
    {
        return ZERO;
    }

    /**
     * Creates a new instance where the given value is treated as game units.
     *
     * @param units the game units
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public static Unit forGameUnits(double units)
    {
        return new Unit(units);
    }

    /**
     * Creates a new instance where the given value is treated as OpenGL units.
     *
     * @param glUnits the OpenGL units
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public static Unit forGlUnits(double glUnits)
    {
        return new Unit(glUnits / Unit.ratio);
    }

    /**
     * Gets the value for OpenGL units. This value is calculated every call to ensure accuracy in case the ratio is changed.
     *
     * @return the number of OpenGL units this instance describes. This equals to game units * ratio.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public double glUnits()
    {
        return this.gameUnits * Unit.ratio;
    }

    /**
     * Adds the given amount of game units to the value that this instance holds. A new Unit instance with the adjusted value
     * is created and returned. The original instance is not modified.
     *
     * @param units the units
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit addGameUnits(double units)
    {
        return forGameUnits(this.gameUnits + units);
    }

    /**
     * Subtracts the given amount of game units from the value that this instance holds. A new Unit instance with the
     * adjusted value is created and returned. The original instance is not modified.
     *
     * @param unit the unit
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit subtractGameUnits(Unit unit)
    {
        return forGameUnits(this.gameUnits - unit.gameUnits());
    }

    /**
     * Adds the given amount of game units to the value that this instance holds. A new Unit instance with the adjusted value
     * is created and returned. The original instance is not modified.
     *
     * @param unit the unit
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit addGameUnits(Unit unit)
    {
        return forGameUnits(this.gameUnits + unit.gameUnits());
    }

    /**
     * Subtracts the given amount of game units from the value that this instance holds. A new Unit instance with the
     * adjusted value is created and returned. The original instance is not modified.
     *
     * @param units the units
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit subtractGameUnits(double units)
    {
        return forGameUnits(this.gameUnits - units);
    }

    /**
     * Divides the value that this instance holds by the given amount. A new Unit instance with the adjusted value is
     * created and returned. The original instance is not modified.
     *
     * @param amount the amount
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit divideBy(double amount)
    {
        return forGameUnits(this.gameUnits / amount);
    }

    /**
     * Multiplies the value that this instance holds with the given amount. A new Unit instance with the adjusted value
     * is created and returned. The original instance is not modified.
     *
     * @param amount the amount
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit multiplyWith(double amount)
    {
        return forGameUnits(this.gameUnits * amount);
    }

    /**
     * Adds the given amount of OpenGL units to the value that this instance holds. A new Unit instance with the adjusted
     * value is created and returned. The original instance is not modified.
     *
     * @param glUnits the OpenGL units
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit addGlUnits(double glUnits)
    {
        return forGlUnits(glUnits() + glUnits);
    }

    /**
     * Subtracts the given amount of OpenGL units from the value that this instance holds. A new Unit instance with the
     * adjusted value is created and returned. The original instance is not modified.
     *
     * @param glUnits the OpenGL units
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit subtractGlUnits(double glUnits)
    {
        return forGlUnits(glUnits() - glUnits);
    }

    /**
     * Adds the amount of OpenGL units held by the given unit instance to the value that this instance holds. A new Unit
     * instance with the adjusted value is created and returned. The original instance is not modified.
     *
     * @param unit the unit
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit addGlUnits(Unit unit)
    {
        return forGlUnits(glUnits() + unit.glUnits());
    }

    /**
     * Subtracts the amount of OpenGL units held by the given unit instance to the value that this instance holds. A new Unit
     * instance with the adjusted value is created and returned. The original instance is not modified.
     *
     * @param unit the unit
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit subtractGlUnits(Unit unit)
    {
        return forGlUnits(glUnits() - unit.glUnits());
    }
}