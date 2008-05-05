/*
 * Created on May 5, 2008 for br.org.archimedes.intersections
 */

package br.org.archimedes.intersections;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import br.org.archimedes.Utils;

/**
 * Belongs to package br.org.archimedes.intersections.
 * 
 * @author night
 */
public class Activator extends Plugin {

    private IntersectorsManager manager;


    /**
     * Default constructor.
     */
    public Activator () {

        manager = new IntersectorsManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start (BundleContext context) throws Exception {

        super.start(context);
        Utils.setIntersectionManager(manager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop (BundleContext context) throws Exception {

        super.stop(context);
        Utils.setIntersectionManager(null);
    }
}
