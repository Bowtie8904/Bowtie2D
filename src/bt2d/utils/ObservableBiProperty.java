package bt2d.utils;

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
        if (this.onChange != null)
        {
            this.onChange.accept(this.value1, newValue1, this.value2, newValue2);
        }

        this.value1 = newValue1;
        this.value2 = newValue2;
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
}