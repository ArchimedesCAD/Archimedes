/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/07, 18:41:59, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
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
