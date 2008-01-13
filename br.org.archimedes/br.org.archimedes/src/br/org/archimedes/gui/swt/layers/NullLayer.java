/**
 * This file was created on 2007/04/07, 21:09:55, by nitao. It is part of
 * br.org.archimedes.gui.swt on the br.org.archimedes project.
 */

package br.org.archimedes.gui.swt.layers;

import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 * 
 * @author nitao
 */
public class NullLayer extends Layer {

    /**
     * Default constructor
     */
    public NullLayer () {

        super(null, "", LineStyle.CONTINUOUS, 0); //$NON-NLS-1$
    }
}
