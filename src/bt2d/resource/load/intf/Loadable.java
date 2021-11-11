package bt2d.resource.load.intf;

import bt2d.resource.load.exc.LoadException;

/**
 * Defines a class that can be loaded in any way.
 *
 * @author Lukas Hartwig
 * @since 10.11.2021
 */
public interface Loadable
{
    /**
     * Loads this classes resources.
     *
     * @param sceneContextName The context of this load call. This will usually be the name of the scene that is being loaded.
     *
     * @throws LoadException If anything goes wrong while loading.
     * @author Lukas Hartwig
     * @since 10.11.2021
     */
    public void load(String sceneContextName) throws LoadException;
}