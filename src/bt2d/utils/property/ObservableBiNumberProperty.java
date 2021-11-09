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
     * The minimum value for the value of this property.
     */
    protected Number min;

    /**
     * The maximum value for the value of this property.
     */
    protected Number max;

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
     * Defines a minimum required value (inclusive) for this properties values.
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
    public void min(Number min)
    {
        this.min = min;
    }

    /**
     * Defines a maximum value (inclusive) for this properties values.
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
    public void max(Number max)
    {
        this.max = max;
    }

    /**
     * Defines minumum and maximum values (both inclusive) for this properties values.
     * <p>
     * If any set value is below the given min or above the given max value an exception will be thrown upon the next {@link #set(Number, Number)} call.
     * <p>
     * Note: The check is not performed for null values, meaning null is always within accepted bounds unless {@link #nonNull()} was called.
     * <p>
     * This is just a convenience call for
     * <pre>
     *     min(min);
     *     max(max);
     * </pre>
     *
     * @param min the min
     * @param max the max
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void range(Number min, Number max)
    {
        min(min);
        max(max);
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
        if (newValue1 != null && this.min != null)
        {
            if (newValue1.doubleValue() < this.min.doubleValue())
            {
                throw new IllegalArgumentException("New property first value [" + newValue1 + "] is lower " +
                                                           "than the defined minimum value [" + this.min + "]");
            }
            else if (newValue2.doubleValue() < this.min.doubleValue())
            {
                throw new IllegalArgumentException("New property second value [" + newValue2 + "] is lower " +
                                                           "than the defined minimum value [" + this.min + "]");
            }
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
        if (newValue1 != null && this.max != null)
        {
            if (newValue1.doubleValue() > this.max.doubleValue())
            {
                throw new IllegalArgumentException("New property first value [" + newValue1 + "] is higher " +
                                                           "than the defined maximum value [" + this.max + "]");
            }
            else if (newValue2.doubleValue() > this.max.doubleValue())
            {
                throw new IllegalArgumentException("New property second value [" + newValue2 + "] is higher " +
                                                           "than the defined maximum value [" + this.max + "]");
            }
        }
    }
}