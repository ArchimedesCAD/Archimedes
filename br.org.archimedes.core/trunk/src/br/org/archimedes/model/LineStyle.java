/**
 * This file was created on 2007/04/07, 18:41:59, by nitao. It is part of
 * br.org.archimedes.model on the br.org.archimedes.core project.
 */

package br.org.archimedes.model;

import br.org.archimedes.gui.opengl.OpenGLWrapper;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author nitao
 */
public enum LineStyle {

    CONTINUOUS {

        public String getName () {

            return Messages.LineStyle_0;
        }

        public int getOpenGLStyle () {

            return OpenGLWrapper.CONTINUOUS_LINE;
        }
    },
    STIPPED {

        public String getName () {

            return Messages.LineStyle_1;
        }

        public int getOpenGLStyle () {

            return OpenGLWrapper.STIPPLED_LINE;
        }
    };

    /**
     * @return The name of this line style
     */
    public abstract String getName ();

    /**
     * @return The openGL style of this line.
     */
    public abstract int getOpenGLStyle ();
}
