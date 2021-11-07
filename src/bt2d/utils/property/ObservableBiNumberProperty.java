package bt2d.utils.property;

/**
 * Extension of the {@link ObservableBiProperty} for number types to allow further restriction of numeric types such as min and max values.
 *
 * @param <T1> the type parameter
 * @param <T2> the type parameter
 *
 * @author Lukas Hartwig
 * @since 07.11.2021
 */
public class ObservableBiNumberProperty<T1 extends Number, T2 extends Number> extends ObservableBiProperty<T1, T2>
{
    /**
     * The minimum value for the first value of this property.
     */
    protected T1 min1;

    /**
     * The maximum value for the first value of this property.
     */
    protected T1 max1;

    /**
     * The minimum value for the second value of this property.
     */
    protected T2 min2;

    /**
     * The maximum value for the second value of this property.
     */
    protected T2 max2;

    /**
     * Creates a new property instance and assigns the initial values.
     *
     * @param value1 the value 1
     * @param value2 the value 2
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public ObservableBiNumberProperty(T1 value1, T2 value2)
    {
        super(value1, value2);
    }

    /**
     * Creates a new property instance with an initial values of null.
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public ObservableBiNumberProperty()
    {
        super();
    }

    /**
     * Sets the values of this property.
     * <p>
     * If a change listener has been set then it will be notified.
     *
     * @param newValue1 the new value 1
     * @param newValue2 the new value 2
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    @Override
    public void set(T1 newValue1, T2 newValue2)
    {
        checkNonNull(newValue1, newValue2);
        checkMinBounds(newValue1, newValue2);
        checkMaxBounds(newValue1, newValue2);

        this.value1 = newValue1;
        this.value2 = newValue2;
        notifyListener(newValue1, newValue2);
    }

    /**
     * Defines a minimum required value (inclusive) for this properties first value.
     * <p>
     * If any set value is below the given value an exception will be thrown upon the next {@link #set(Number, Number)} call.
     * <p>
     * Note: The check is not performed for null values, meaning null is always within accepted bounds unless {@link #nonNull()} was called.
     *
     * @param min the min
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void min1(T1 min)
    {
        this.min1 = min;
    }

    /**
     * Defines a maximum value (inclusive) for this properties first value.
     * <p>
     * If any set value is above the given value an exception will be thrown upon the next {@link #set(Number, Number)} call.
     * <p>
     * Note: The check is not performed for null values, meaning null is always within accepted bounds unless {@link #nonNull()} was called.
     *
     * @param max the max
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void max1(T1 max)
    {
        this.max1 = max;
    }

    /**
     * Defines a minimum required value (inclusive) for this properties second value.
     * <p>
     * If any set value is below the given value an exception will be thrown upon the next {@link #set(Number, Number)} call.
     * <p>
     * Note: The check is not performed for null values, meaning null is always within accepted bounds unless {@link #nonNull()} was called.
     *
     * @param min the min
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void min2(T2 min)
    {
        this.min2 = min;
    }

    /**
     * Defines a maximum value (inclusive) for this properties second value.
     * <p>
     * If any set value is above the given value an exception will be thrown upon the next {@link #set(Number, Number)} call.
     * <p>
     * Note: The check is not performed for null values, meaning null is always within accepted bounds unless {@link #nonNull()} was called.
     *
     * @param max the max
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void max2(T2 max)
    {
        this.max2 = max;
    }

    /**
     * Defines minumum and maximum values (both inclusive) for this properties first value.
     * <p>
     * If any set value is below the given min or above the given max value an exception will be thrown upon the next {@link #set(Number, Number)} call.
     * <p>
     * Note: The check is not performed for null values, meaning null is always within accepted bounds unless {@link #nonNull()} was called.
     * <p>
     * This is just a convenience call for
     * <pre>
     *     min1(min);
     *     max1(max);
     * </pre>
     *
     * @param min the min
     * @param max the max
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void range1(T1 min, T1 max)
    {
        min1(min);
        max1(max);
    }

    /**
     * Defines minumum and maximum values (both inclusive) for this properties second value.
     * <p>
     * If any set value is below the given min or above the given max value an exception will be thrown upon the next {@link #set(Number, Number)} call.
     * <p>
     * Note: The check is not performed for null values, meaning null is always within accepted bounds unless {@link #nonNull()} was called.
     * <p>
     * This is just a convenience call for
     * <pre>
     *     min2(min);
     *     max2(max);
     * </pre>
     *
     * @param min the min
     * @param max the max
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void range2(T2 min, T2 max)
    {
        min2(min);
        max2(max);
    }

    /**
     * Check min bounds.
     *
     * @param newValue1 the new value 1
     * @param newValue2 the new value 2
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    protected void checkMinBounds(T1 newValue1, T2 newValue2)
    {
        if (newValue1 != null && this.min1 != null && newValue1.doubleValue() < this.min1.doubleValue())
        {
            throw new IllegalArgumentException("New property first value [" + newValue1 + "] is lower " +
                                                       "than the defined minimum value [" + this.min1 + "]");
        }

        if (newValue2 != null && this.min2 != null && newValue2.doubleValue() < this.min2.doubleValue())
        {
            throw new IllegalArgumentException("New property second value [" + newValue2 + "] is lower " +
                                                       "than the defined minimum value [" + this.min2 + "]");
        }
    }

    /**
     * Check max bounds.
     *
     * @param newValue1 the new value 1
     * @param newValue2 the new value 2
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    protected void checkMaxBounds(T1 newValue1, T2 newValue2)
    {
        if (newValue1 != null && this.max1 != null && newValue1.doubleValue() > this.max1.doubleValue())
        {
            throw new IllegalArgumentException("New property first value [" + newValue1 + "] is higher " +
                                                       "than the defined maximum value [" + this.max1 + "]");
        }

        if (newValue2 != null && this.max2 != null && newValue2.doubleValue() > this.max2.doubleValue())
        {
            throw new IllegalArgumentException("New property second value [" + newValue2 + "] is higher " +
                                                       "than the defined maximum value [" + this.max2 + "]");
        }
    }
}