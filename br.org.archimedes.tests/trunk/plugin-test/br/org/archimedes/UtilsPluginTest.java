/*
 * Created on May 5, 2008 for br.org.archimedes.tests
 */

package br.org.archimedes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.intersections.NullIntersectionManager;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author night
 */
public class UtilsPluginTest {

    @Test
    public void loadsNullIntersectionManagerIfNoManagerHasBeenInjected ()
            throws Exception {

        IntersectionManager manager = Utils.getIntersectionManager();
        assertEquals("The loaded intersection manager was not a NullIntersectionManager",
                NullIntersectionManager.class, manager.getClass());
    }
}
