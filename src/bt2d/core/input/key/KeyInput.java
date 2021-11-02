package bt2d.core.input.key;

import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

/**
 * A central place that will listen for key callbacks from an GLFW window.
 * <p>
 * This class offers methods to check if a key is currently pressed or or released.
 *
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
public class KeyInput
{
    /**
     * The singleton instance.
     */
    private volatile static KeyInput instance;

    /**
     * The keys mapped by their keycode keeping track of their status.
     */
    private Map<Integer, Key> keyValues;

    /**
     * Staged changes to the main keyValues map, will be merged during the next call of {@link #checkKeyChanges()}.
     */
    private Map<Integer, Key> keyChanges;

    /**
     * Instantiates a new Key input.
     *
     * @param windowRef the window ref
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public KeyInput(long windowRef)
    {
        instance = this;
        this.keyValues = new HashMap<>();
        this.keyChanges = new HashMap<>();
        GLFW.glfwSetKeyCallback(windowRef, this::onKeyAction);
    }

    /**
     * Get key input.
     *
     * @return the key input
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public static KeyInput get()
    {
        return instance;
    }

    /**
     * Indicates whether a key is just now being pressed. This state does not last longer than one tick.
     *
     * @param key The key code (constant from {@link GLFW}) of the key to check.
     *
     * @return true if the key was just pressed.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public boolean isKeyJustDown(int key)
    {
        return isKeyJustDown(key, 0);
    }

    /**
     * Indicates whether a key + mods combination is just now being pressed. This state does not last longer than one tick.
     *
     * @param key  The key code (constant from {@link GLFW}) of the key to check.
     * @param mods Bitmask of the mods that are pressed with the key, i.e shift and alt.
     *
     * @return true if the key + mods combination was just pressed.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public boolean isKeyJustDown(int key, int mods)
    {
        var entry = this.keyValues.get(key);
        return entry != null && entry.getStatus() == Key.KEY_JUST_DOWN
                && entry.getMods() == mods;
    }

    /**
     * Indicates whether a key is currently being pressed.
     *
     * @param key The key code (constant from {@link GLFW}) of the key to check.
     *
     * @return true if the key is being pressed.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public boolean isKeyDown(int key)
    {
        return isKeyDown(key, 0);
    }

    /**
     * Indicates whether a key + mods combination is currently being pressed.
     *
     * @param key  The key code (constant from {@link GLFW}) of the key to check.
     * @param mods Bitmask of the mods that are pressed with the key, i.e shift and alt.
     *
     * @return true if the key is being pressed.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public boolean isKeyDown(int key, int mods)
    {
        var entry = this.keyValues.get(key);
        return entry != null
                && (entry.getStatus() == Key.KEY_DOWN || entry.getStatus() == Key.KEY_JUST_DOWN)
                && entry.getMods() == mods;
    }

    /**
     * Indicates whether a key was recently pressed and is now released.
     *
     * @param key The key code (constant from {@link GLFW}) of the key to check.
     *
     * @return true if the key has been pressed somewhere in the past and was released during the last tick.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public boolean isKeyReleased(int key)
    {
        return isKeyReleased(key, 0);
    }

    /**
     * Indicates whether a key + mods combination was recently pressed and is now released.
     *
     * @param key  The key code (constant from {@link GLFW}) of the key to check.
     * @param mods Bitmask of the mods that are pressed with the key, i.e shift and alt.
     *
     * @return true if the key has been pressed somewhere in the past and was released during the last tick.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public boolean isKeyReleased(int key, int mods)
    {
        var entry = this.keyValues.get(key);
        return entry != null
                && entry.getStatus() == Key.KEY_RELEASED
                && entry.getMods() == mods;
    }

    /**
     * Callback for GLFW.
     *
     * @param window   The window reference.
     * @param key      The keycode of the pressed key.
     * @param scancode The system-specific scancode of the key
     * @param action   The action of this event, i.e release.
     * @param mods     The the modifications of this action, i.e. shift or alt.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    protected void onKeyAction(long window, int key, int scancode, int action, int mods)
    {
        if (action == GLFW.GLFW_RELEASE)
        {
            keyReleased(key, mods);
        }
        else
        {
            keyPressed(key, mods);
        }
    }

    /**
     * Marks the given key + mods combination as pressed if it isnt already.
     * <p>
     * This is only a staged change, it will come into effect after the next {@link #checkKeyChanges()} call.
     *
     * @param key  the keycode
     * @param mods the mods, i.e. shift ro alt
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    protected void keyPressed(int key, int mods)
    {
        synchronized (this.keyChanges)
        {
            if (!isKeyDown(key, mods))
            {
                this.keyChanges.put(key, new Key(key, Key.KEY_JUST_DOWN, mods));
            }
        }
    }

    /**
     * Marks the given key + mods combination as released if it isnt already.
     * <p>
     * This is only a staged change, it will come into effect after the next {@link #checkKeyChanges()} call.
     *
     * @param key  the keycode
     * @param mods the mods, i.e. shift ro alt
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    protected void keyReleased(int key, int mods)
    {
        synchronized (this.keyChanges)
        {
            this.keyChanges.put(key, new Key(key, Key.KEY_RELEASED, mods));
        }
    }

    /**
     * Checks for new staged key changes.
     * <p>
     * First this method will update the states of already known keys.
     * It changes 'recently released' to 'not down' and 'just down' to 'down'.
     * <p>
     * After that the staged changes will be added to the main key map and are
     * then available to calls like {@link #isKeyDown(int, int) isKeyDown}.
     *
     * @author Lukas Hartwig
     * @since 03.11.2021
     */
    public void checkKeyChanges()
    {
        // changing 'recently released' to 'not down' and 'just down' to 'down'
        this.keyValues.replaceAll((k, v) ->
                                  {
                                      if (v.getStatus() == Key.KEY_RELEASED)
                                      {
                                          v.setStatus(Key.KEY_NOT_DOWN);
                                      }
                                      else if (v.getStatus() == Key.KEY_JUST_DOWN)
                                      {
                                          v.setStatus(Key.KEY_DOWN);
                                      }

                                      return v;
                                  });

        // merge staged changes into main map and clear changes afterwards
        synchronized (this.keyChanges)
        {
            for (var key : this.keyChanges.keySet())
            {
                this.keyValues.put(key, this.keyChanges.get(key));
            }

            this.keyChanges.clear();
        }
    }
}