package bt2d.utils.property;

/**
 * Extension of the {@link ObservableProperty} for number types to allow further restriction of numeric types such as min and max values.
 *
 * @param <T> the type parameter
 *
 * @author Lukas Hartwig
 * @since 07.11.2021
 */
public class ObservableNumberProperty<T extends Number> extends ObservableProperty<T>
{
    /**
     * The minimum value for this property.
     */
    protected T min;

    /**
     * The maximum value for this property.
     */
    protected T max;

    /**
     * Creates a new property instance and assigns the initial value.
     *
     * @param value The initial value.
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public ObservableNumberProperty(T value)
    {
        super(value);
    }

    /**
     * Creates a new property instance with an initial value of null.
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public ObservableNumberProperty()
    {
        super();
    }

    /**
     * Defines a minimum required value (inclusive) for this property.
     * <p>
     * If any set value is below the given value an exception will be thrown upon the next {@link #set(Number)} call.
     * <p>
     * Note: The check is not performed for null values, meaning null is always within accepted bounds unless {@link #nonNull()} was called.
     *
     * @param min the min
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void min(T min)
    {
        this.min = min;
    }

    /**
     * Defines a maximum value (inclusive) for this property.
     * <p>
     * If any set value is above the given value an exception will be thrown upon the next {@link #set(Number)} call.
     * <p>
     * Note: The check is not performed for null values, meaning null is always within accepted bounds unless {@link #nonNull()} was called.
     *
     * @param max the max
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void max(T max)
    {
        this.max = max;
    }

    /**
     * Defines minumum and maximum values (both inclusive) for this property.
     * <p>
     * If any set value is below the given min or above the given max value an exception will be thrown upon the next {@link #set(Number)} call.
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
    public void range(T min, T max)
    {
        min(min);
        max(max);
    }

    /**
     * Sets the value of this property.
     * <p>
     * If a change listener has been set then it will be notified.
     *
     * @param newValue the new value
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    @Override
    public void set(T newValue)
    {
        checkNonNull(newValue);
        checkMinBounds(newValue);
        checkMaxBounds(newValue);

        this.value = newValue;
        notifyListeners(newValue);
    }

    /**
     * Check min bounds.
     *
     * @param newValue the new value
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    protected void checkMinBounds(T newValue)
    {
        if (newValue != null && this.min != null && newValue.doubleValue() < this.min.doubleValue())
        {
            throw new IllegalArgumentException("New property value [" + newValue + "] is lower " +
                                                       "than the defined minimum value [" + this.min + "]");
        }
    }

    /**
     * Check max bounds.
     *
     * @param newValue the new value
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    protected void checkMaxBounds(T newValue)
    {
        if (newValue != null && this.max != null && newValue.doubleValue() > this.max.doubleValue())
        {
            throw new IllegalArgumentException("New property value [" + newValue + "] is higher " +
                                                       "than the defined maximum value [" + this.max + "]");
        }
    }
}