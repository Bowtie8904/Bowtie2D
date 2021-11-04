package bt2d.core.input.key;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a set of actions mapped to specific keys.
 *
 * @author Lukas Hartwig
 * @since 03.11.2021
 */
public class KeyActions
{
    /**
     * The actions mapped by the key + mods combination.
     */
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
     * Defines an action that is executed if the given key is pressed.
     * <p>
     * If the key remains pressed then this action will be executed every tick.
     * <p>
     * For one time actions see {@link #onKeyJustDown(int, Runnable)}.
     *
     * @param key    the key code of the trigger key.
     * @param action the action to execute.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyDown(int key, Runnable action)
    {
        onKeyDown(key, 0, action);
    }

    /**
     * Defines an action that is executed if the given key + mods combination is pressed.
     * <p>
     * If the key remains pressed then this action will be executed every tick.
     * <p>
     * For one time actions see {@link #onKeyJustDown(int, int, Runnable)}.
     *
     * @param key    the key code of the trigger key.
     * @param mods   the mods of this key press, i.e. shift or alt.
     * @param action the action to execute.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyDown(int key, int mods, Runnable action)
    {
        this.actions.put(new Key(key, Key.KEY_DOWN, mods), action);
    }

    /**
     * Defines an action that is executed if the given key is pressed.
     * <p>
     * This is a one time per key press action. If the key remains pressed this action will not be repeated.
     *
     * @param key    the key code of the trigger key.
     * @param action the action to execute.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyJustDown(int key, Runnable action)
    {
        onKeyJustDown(key, 0, action);
    }

    /**
     * Defines an action that is executed if the given key + mods combination is pressed.
     * <p>
     * This is a one time per key press action. If the key remains pressed this action will not be repeated.
     *
     * @param key    the key code of the trigger key.
     * @param mods   the mods of this key press, i.e. shift or alt.
     * @param action the action to execute.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyJustDown(int key, int mods, Runnable action)
    {
        this.actions.put(new Key(key, Key.KEY_JUST_DOWN, mods), action);
    }

    /**
     * Defines an action that is executed if the given key is released.
     * <p>
     * This is a one time per release action which will be executed in the same tick as the releasing is detected.
     *
     * @param key    the key code of the trigger key.
     * @param action the action to execute.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyreleased(int key, Runnable action)
    {
        onKeyreleased(key, 0, action);
    }

    /**
     * Defines an action that is executed if the given key + mods combination is released.
     * <p>
     * This is a one time per release action which will be executed in the same tick as the releasing is detected.
     *
     * @param key    the key code of the trigger key.
     * @param mods   the mods of this key press, i.e. shift or alt.
     * @param action the action to execute.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void onKeyreleased(int key, int mods, Runnable action)
    {
        this.actions.put(new Key(key, Key.KEY_RELEASED, mods), action);
    }

    /**
     * Check if any of the configured actions need to be executed based on the given KeyInput instance.
     * <p>
     * This method should be called during every tick iteration after the
     * KeyInputs {@link KeyInput#checkKeyChanges()} method was called.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void checkActions(KeyInput input)
    {
        for (Key key : this.actions.keySet())
        {
            if (key.getStatus() == Key.KEY_DOWN && input.isKeyDown(key.getKeycode(), key.getMods()))
            {
                this.actions.get(key).run();
            }
            else if (key.getStatus() == Key.KEY_JUST_DOWN && input.isKeyJustDown(key.getKeycode(), key.getMods()))
            {
                this.actions.get(key).run();
            }
            else if (key.getStatus() == Key.KEY_RELEASED && input.isKeyReleased(key.getKeycode(), key.getMods()))
            {
                this.actions.get(key).run();
            }
        }
    }
}