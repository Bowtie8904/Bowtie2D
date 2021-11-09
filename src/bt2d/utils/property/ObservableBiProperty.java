package bt2d.utils.property;

import bt2d.utils.QuadConsumer;

import java.util.function.BiConsumer;

/**
 * A simple two value property class which allows change observation via a single consumer.
 * <p>
 * Usage:
 * <pre>
 *     ObservableBiProperty< Boolean, Integer> myProp = new ObservableBiProperty<>(true, 1);
 *     myProp.onChange((oldValue1, newValue1, oldValue2, newValue2) -> {
 *                              System.out.println("Old value1: " + oldValue1 + "  new value1: " + newValue1);
 *                              System.out.println("Old value2: " + oldValue2 + "  new value2: " + newValue2);
 *                          });
 *     myProp.set(false, 5);
 * </pre>
 *
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
public class ObservableBiProperty<T1, T2>
{
    /**
     * The change listener that will be notified when the {@link #set(Object, Object) set} method is called.
     */
    protected QuadConsumer<T1, T1, T2, T2> onChange;

    /**
     * The first value of this property.
     */
    protected T1 value1;

    /**
     * Indicates whether the values of this property have to be non-null.
     */
    protected boolean requireNonNull;

    /**
     * The second value of this property.
     */
    protected T2 value2;

    /**
     * Creates a new property instance and assigns the initial values.
     *
     * @param value1 The initial first value.
     * @param value2 The initial second value.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public ObservableBiProperty(T1 value1, T2 value2)
    {
        set(value1, value2);
    }

    /**
     * Creates a new property instance with an initial values of null.
     *
     * @author Lukas Hartwig
     * @since 01.11.2021
     */
    public ObservableBiProperty()
    {
    }

    /**
     * Sets the values of this property.
     * <p>
     * If a change listener has been set then it will be notified.
     *
     * @param newValue1 The new first value of this property.
     * @param newValue2 The new second value of this property.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void set(T1 newValue1, T2 newValue2)
    {
        checkNonNull(newValue1, newValue2);

        this.value1 = newValue1;
        this.value2 = newValue2;
        notifyListener(newValue1, newValue2);
    }

    /**
     * Notifies the set listener (if there is any) and passes the new values.
     *
     * @param newValue1 the new value 1
     * @param newValue2 the new value 2
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    protected void notifyListener(T1 newValue1, T2 newValue2)
    {
        if (this.onChange != null)
        {
            this.onChange.accept(this.value1, newValue1, this.value2, newValue2);
        }
    }

    /**
     * Checks if this property requires non-null values. If so checks if the given values are non-null.
     *
     * @param newValue1 the new value 1
     * @param newValue2 the new value 2
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    protected void checkNonNull(T1 newValue1, T2 newValue2)
    {
        if (this.requireNonNull && (newValue1 == null || newValue2 == null))
        {
            throw new IllegalArgumentException("Property requires non-null values");
        }
    }

    /**
     * Gets the first value of this property.
     *
     * @return The first value.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public T1 getFirst()
    {
        return this.value1;
    }

    /**
     * Gets the second value of this property.
     *
     * @return The second value.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public T2 getSecond()
    {
        return this.value2;
    }

    /**
     * Sets a listener which will be notified about changes of this properties values.
     *
     * @param onChange A BiConsumer whichs first argument will be the new first value and whichs second argument will be the new second value.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void onChange(BiConsumer<T1, T2> onChange)
    {
        this.onChange = (oldValue1, newValue1, oldValue2, newValue2) -> onChange.accept(newValue1, newValue2);
    }

    /**
     * Sets a listener which will be notified about changes of this properties values.
     *
     * @param onChange A QuadConsumer whichs arguments will be:
     *                 - old first value
     *                 - new first value
     *                 - old second value
     *                 - new second value
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void onChange(QuadConsumer<T1, T1, T2, T2> onChange)
    {
        this.onChange = (oldValue1, newValue1, oldValue2, newValue2) -> onChange.accept(oldValue1, newValue1, oldValue2, newValue2);
    }

    /**
     * Sets that this property requires its values to be non-null.
     * <p>
     * Any values passed to {@link #set(Object, Object)} after this call will be checked.
     * If a null value is encountered then an {@link IllegalArgumentException} will be thrown.
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    public void nonNull()
    {
        this.requireNonNull = true;
    }
}