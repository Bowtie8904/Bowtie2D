package bt2d.core.input.key;

import java.util.Objects;

/**
 * A simple entity describing a key + mods combination.
 *
 * @author Lukas Hartwig
 * @since 03.11.2021
 */
public class Key
{
    /**
     * Status for a key that is currently not pressed.
     */
    public static final int KEY_NOT_DOWN = 0;

    /**
     * Status for a key that is currently pressed.
     */
    public static final int KEY_DOWN = 1;

    /**
     * Status for a key that was just pressed during the last tick.
     */
    public static final int KEY_JUST_DOWN = 2;

    /**
     * Status for a key that was just released during the last tick.
     */
    public static final int KEY_RELEASED = 3;

    /**
     * The keycode of this key.
     */
    private int keycode;

    /**
     * The status of this key, i.e. {@link KeyInput#KEY_DOWN}.
     */
    private int status;

    /**
     * Bitmask for mods that are pressed with this key, i.e. shift or alt.
     */
    private int mods;

    /**
     * Instantiates a new Key.
     *
     * @param keycode the keycode
     * @param status  the status
     * @param mods    the mods
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public Key(int keycode, int status, int mods)
    {
        this.keycode = keycode;
        this.status = status;
        this.mods = mods;
    }

    /**
     * Gets keycode.
     *
     * @return the keycode
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public int getKeycode()
    {
        return keycode;
    }

    /**
     * Sets keycode.
     *
     * @param keycode the keycode
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void setKeycode(int keycode)
    {
        this.keycode = keycode;
    }

    /**
     * Gets status.
     *
     * @return the status
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * Gets mods.
     *
     * @return the mods
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public int getMods()
    {
        return mods;
    }

    /**
     * Sets mods.
     *
     * @param mods the mods
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void setMods(int mods)
    {
        this.mods = mods;
    }

    /**
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Key key = (Key)o;
        return keycode == key.keycode && status == key.status && mods == key.mods;
    }

    /**
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(keycode, status, mods);
    }

    /**
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    @Override
    public String toString()
    {
        return "Key{" +
                "keycode=" + keycode +
                ", status=" + status +
                ", mods=" + mods +
                '}';
    }
}