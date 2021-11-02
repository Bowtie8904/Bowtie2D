package bt2d.utils;

/**
 * A measurement conversion class between pixels and custom game units. This class uses a set ratio of pixels per unit
 * to do its calculations.
 *
 * @author Lukas Hartwig
 * @since 01.11.2021
 */
public record Unit(double units)
{
    /**
     * A constant for zero units since its expected to be used frequently.
     */
    private static final Unit ZERO = Unit.forUnits(0);

    /**
     * The ratio of pixels to unit. Describes how many pixels are in one unit.
     */
    private static double ratio = 1;

    /**
     * Gets the pixel per unit ratio. The returned value is the amount of pixels one unit consist of.
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
     * Sets the pixel per unit ratio. The given value is the amount of pixels one unit should consist of. If this method
     * is not called, the default ratio of 1 will be used.
     *
     * @param pixelsPerUnit the pixels per unit
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public static void setRatio(double pixelsPerUnit)
    {
        Unit.ratio = pixelsPerUnit;
    }

    /**
     * Returns a constant instance with a unit and pixel value of zero.
     *
     * @return unit
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public static Unit zero()
    {
        return ZERO;
    }

    /**
     * Creates a new instance where the given value is treated as units.
     *
     * @param units the units
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public static Unit forUnits(double units)
    {
        return new Unit(units);
    }

    /**
     * Creates a new instance where the given value is treated as pixels.
     *
     * @param pixels the pixels
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public static Unit forPixels(double pixels)
    {
        return new Unit(pixels / Unit.ratio);
    }

    /**
     * Adds the given amount of units to the value that this instance holds. A new Unit instance with the adjusted value
     * is created and returned. The original instance is not modified.
     *
     * @param units the units
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit addUnits(double units)
    {
        return forUnits(this.units + units);
    }

    /**
     * Subtracts the given amount of units from the value that this instance holds. A new Unit instance with the
     * adjusted value is created and returned. The original instance is not modified.
     *
     * @param unit the unit
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit subtractUnits(Unit unit)
    {
        return forUnits(this.units - unit.units());
    }

    /**
     * Adds the given amount of units to the value that this instance holds. A new Unit instance with the adjusted value
     * is created and returned. The original instance is not modified.
     *
     * @param unit the unit
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit addUnits(Unit unit)
    {
        return forUnits(this.units + unit.units());
    }

    /**
     * Subtracts the given amount of units from the value that this instance holds. A new Unit instance with the
     * adjusted value is created and returned. The original instance is not modified.
     *
     * @param units the units
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit subtractUnits(double units)
    {
        return forUnits(this.units - units);
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
        return forUnits(this.units / amount);
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
        return forUnits(this.units * amount);
    }

    /**
     * Adds the given amount of pixels to the value that this instance holds. A new Unit instance with the adjusted
     * value is created and returned. The original instance is not modified.
     *
     * @param pixels the pixels
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit addPixels(double pixels)
    {
        return forPixels(pixels() + pixels);
    }

    /**
     * Subtracts the given amount of pixels from the value that this instance holds. A new Unit instance with the
     * adjusted value is created and returned. The original instance is not modified.
     *
     * @param pixels the pixels
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit subtractPixels(double pixels)
    {
        return forPixels(pixels() - pixels);
    }

    /**
     * Adds the amount of pixels held by the given unit instance to the value that this instance holds. A new Unit
     * instance with the adjusted value is created and returned. The original instance is not modified.
     *
     * @param unit the unit
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit addPixels(Unit unit)
    {
        return forPixels(pixels() + unit.pixels());
    }

    /**
     * Subtracts the amount of pixels held by the given unit instance to the value that this instance holds. A new Unit
     * instance with the adjusted value is created and returned. The original instance is not modified.
     *
     * @param unit the unit
     *
     * @return The created instance.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public Unit subtractPixels(Unit unit)
    {
        return forPixels(pixels() - unit.pixels());
    }

    /**
     * Gets the value for pixels. This value is calculated every call to ensure accuracy in case the ratio is changed.
     *
     * @return the number of pixels this instance describes. This equals to units * ratio.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public double pixels()
    {
        return this.units * Unit.ratio;
    }
}