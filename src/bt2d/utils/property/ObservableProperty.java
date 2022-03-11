package bt2d.utils.property;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A simple property class which allows change observation via a single consumer.
 * <p>
 * Usage:
 * <pre>
 *     ObservableProperty< Boolean> myBool = new ObservableProperty<>(true);
 *     myBool.addChangeListener((oldValue, newValue) -> System.out.println("Old value: " + oldValue + "  new value: " + newValue));
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
     * The list of change listeners that will be notified when the {@link #set(Object) set} method is called.
     */
    protected List<BiConsumer<T, T>> onChangeListeners;

    /**
     * Indicates whether the value of this property have to be non-null.
     */
    protected boolean requireNonNull;

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
        this();
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
        this.onChangeListeners = new ArrayList<>();
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
        checkNonNull(newValue);

        T oldValue = this.value;

        this.value = newValue;
        notifyListeners(oldValue, newValue);
    }

    /**
     * Checks if this property requires non-null values. If so checks if the given value is non-null.
     *
     * @param newValue the new value
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    protected void checkNonNull(T newValue)
    {
        if (this.requireNonNull && newValue == null)
        {
            throw new IllegalArgumentException("Property requires non-null value");
        }
    }

    /**
     * Notifies the set listeners (if there are any) and passes the new value.
     *
     * @param oldValue the old value
     * @param newValue the new value
     *
     * @author Lukas Hartwig
     * @since 07.11.2021
     */
    protected void notifyListeners(T oldValue, T newValue)
    {
        if (this.onChangeListeners != null)
        {
            for (var listener : this.onChangeListeners)
            {
                listener.accept(oldValue, newValue);
            }
        }
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
     * Adds a listener which will be notified about changes of this properties value.
     *
     * @param onChange A BiConsumer whichs first argument will be the old value and whichs second argument will be the new value.
     *
     * @author Lukas Hartwig
     * @since 01.11.2021
     */
    public void addChangeListener(BiConsumer<T, T> onChange)
    {
        if (this.onChangeListeners != null)
        {
            this.onChangeListeners.add(onChange);
        }
    }

    /**
     * Adds a listener which will be notified about changes of this properties value.
     *
     * @param onChange A Consumer whichs argument will be the new value.
     *
     * @author Lukas Hartwig
     * @since 01.11.2021
     */
    public void addChangeListener(Consumer<T> onChange)
    {
        addChangeListener((oldValue, newValue) -> onChange.accept(newValue));
    }

    /**
     * Sets that this property requires its values to be non-null.
     * <p>
     * Any values passed to {@link #set(Object)} after this call will be checked.
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