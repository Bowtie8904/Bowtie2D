package bt2d.utils.property;

import bt2d.utils.QuadConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * A simple two value property class which allows change observation via a single consumer.
 * <p>
 * Usage:
 * <pre>
 *     ObservableBiProperty< Boolean, Integer> myProp = new ObservableBiProperty<>(true, 1);
 *     myProp.addChangeListener((oldValue1, newValue1, oldValue2, newValue2) -> {
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
     * The list of change listeners that will be notified when the {@link #set(Object, Object) set} method is called.
     */
    protected List<QuadConsumer<T1, T1, T2, T2>> onChangeListeners;

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
        this();
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
        this.onChangeListeners = new ArrayList<>();
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

        T1 oldValue1 = this.value1;
        T2 oldValue2 = this.value2;

        this.value1 = newValue1;
        this.value2 = newValue2;
        notifyListeners(oldValue1, newValue1, oldValue2, newValue2);
    }

    /**
     * Notifies the set listeners (if there are any) and passes the new values.
     *
     * @param oldValue1 the old value 1
     * @param newValue1 the new value 1
     * @param oldValue2 the old value 2
     * @param newValue2 the new value 2
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    protected void notifyListeners(T1 oldValue1, T1 newValue1, T2 oldValue2, T2 newValue2)
    {
        if (this.onChangeListeners != null)
        {
            for (var listener : this.onChangeListeners)
            {
                listener.accept(oldValue1, newValue1, oldValue2, newValue2);
            }
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
     * Adds a listener which will be notified about changes of this properties values.
     *
     * @param onChange A BiConsumer whichs first argument will be the new first value and whichs second argument will be the new second value.
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void addChangeListener(BiConsumer<T1, T2> onChange)
    {
        addChangeListener((oldValue1, newValue1, oldValue2, newValue2) -> onChange.accept(newValue1, newValue2));
    }

    /**
     * Adds a listener which will be notified about changes of this properties values.
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
    public void addChangeListener(QuadConsumer<T1, T1, T2, T2> onChange)
    {
        if (this.onChangeListeners != null)
        {
            this.onChangeListeners.add(onChange);
        }
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