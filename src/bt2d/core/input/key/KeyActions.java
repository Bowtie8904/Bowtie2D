package bt2d.core.input.key;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Key actions.
 *
 * @author Lukas Hartwig
 * @since 03.11.2021
 */
public class KeyActions
{
    private Map<Key, Runnable> actions;

    /**
     * Instantiates a new Key actions.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public KeyActions()
    {
        this.actions = new HashMap<>();
    }

    /**
     * On key down.
     *
     * @param key    the key
     * @param action the action
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyDown(int key, Runnable action)
    {
        onKeyDown(key, 0, action);
    }

    /**
     * On key down.
     *
     * @param key    the key
     * @param mods   the mods
     * @param action the action
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyDown(int key, int mods, Runnable action)
    {
        this.actions.put(new Key(key, Key.KEY_DOWN, mods), action);
    }

    /**
     * On key just down.
     *
     * @param key    the key
     * @param action the action
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyJustDown(int key, Runnable action)
    {
        onKeyJustDown(key, 0, action);
    }

    /**
     * On key just down.
     *
     * @param key    the key
     * @param mods   the mods
     * @param action the action
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyJustDown(int key, int mods, Runnable action)
    {
        this.actions.put(new Key(key, Key.KEY_JUST_DOWN, mods), action);
    }

    /**
     * On keyreleased.
     *
     * @param key    the key
     * @param action the action
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyreleased(int key, Runnable action)
    {
        onKeyreleased(key, 0, action);
    }

    /**
     * On keyreleased.
     *
     * @param key    the key
     * @param mods   the mods
     * @param action the action
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyreleased(int key, int mods, Runnable action)
    {
        this.actions.put(new Key(key, Key.KEY_RELEASED, mods), action);
    }

    /**
     * Check actions.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void checkActions()
    {
        for (Key key : this.actions.keySet())
        {
            if (key.getStatus() == Key.KEY_DOWN && KeyInput.get().isKeyDown(key.getKeycode(), key.getMods()))
            {
                this.actions.get(key).run();
            }
            else if (key.getStatus() == Key.KEY_JUST_DOWN && KeyInput.get().isKeyJustDown(key.getKeycode(), key.getMods()))
            {
                this.actions.get(key).run();
            }
            else if (key.getStatus() == Key.KEY_RELEASED && KeyInput.get().isKeyReleased(key.getKeycode(), key.getMods()))
            {
                this.actions.get(key).run();
            }
        }
    }
}