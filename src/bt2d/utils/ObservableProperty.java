package bt2d.utils;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A simple property class which allows change observation via a single consumer.
 * <p>
 * Usage:
 * <pre>
 *     ObservableProperty< Boolean> myBool = new ObservableProperty<>(true);
 *     myBool.onChange((oldValue, newValue) -> System.out.println("Old value: " + oldValue + "  new value: " + newValue));
 *     myBool.set(false);
 * </pre>
 *
 * @author Lukas Hartwig
 * @since 01.11.2021
 */
public class ObservableProperty<T>
{
    /**
     * The value of this property
     */
    protected T value;

    /**
     * The change listener that will be notified when the {@link #set(Object) set} method is called.
     */
    protected BiConsumer<T, T> onChange;

    /**
     * Creates a new property instance and assigns the initial value.
     *
     * @param value The initial value.
     *
     * @author Lukas Hartwig
     * @since 01.11.2021
     */
    public ObservableProperty(T value)
    {
        set(value);
    }

    /**
     * Creates a new property instance with an initial value of null.
     *
     * @author Lukas Hartwig
     * @since 01.11.2021
     */
    public ObservableProperty()
    {
    }

    /**
     * Sets the value of this property.
     * <p>
     * If a change listener has been set then it will be notified.
     *
     * @param newValue The new value of this property.
     *
     * @author Lukas Hartwig
     * @since 01.11.2021
     */
    public void set(T newValue)
    {
        if (this.onChange != null)
        {
            this.onChange.accept(this.value, newValue);
        }

        this.value = newValue;
    }

    /**
     * Gets the value of this property.
     *
     * @return The value.
     *
     * @author Lukas Hartwig
     * @since 01.11.2021
     */
    public T get()
    {
        return this.value;
    }

    /**
     * Sets a listener which will be notified about changes of this properties value.
     *
     * @param onChange A BiConsumer whichs first argument will be the old value and whichs second argument will be the new value.
     *
     * @author Lukas Hartwig
     * @since 01.11.2021
     */
    public void onChange(BiConsumer<T, T> onChange)
    {
        this.onChange = onChange;
    }

    /**
     * Sets a listener which will be notified about changes of this properties value.
     *
     * @param onChange A Consumer whichs argument will be the new value.
     *
     * @author Lukas Hartwig
     * @since 01.11.2021
     */
    public void onChange(Consumer<T> onChange)
    {
        onChange((oldValue, newValue) -> onChange.accept(newValue));
    }
}