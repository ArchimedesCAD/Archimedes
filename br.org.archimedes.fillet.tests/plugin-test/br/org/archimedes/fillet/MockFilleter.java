/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real, Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 05/05/2009, 15:23:34.<br>
 * It is part of br.org.archimedes.fillet on the br.org.archimedes.fillet.tests project.<br>
 */

package br.org.archimedes.fillet;

import br.org.archimedes.interfaces.Filleter;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luiz Real, Ricardo Sider
 */
public class MockFilleter implements Filleter {

    private boolean called = false;

    private Element receivedE1;

    private Point receivedClick1;

    private Element receivedE2;

    private Point receivedClick2;

    private MockMacroCommand macroCommand;


    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.Filleter#fillet(br.org.archimedes.model.Element,
     * br.org.archimedes.model.Point, br.org.archimedes.model.Element,
     * br.org.archimedes.model.Point)
     */
    public List<? extends UndoableCommand> fillet (Element e1, Point click1, Element e2,
            Point click2) {

        this.receivedE1 = e1;
        this.receivedClick1 = click1;
        this.receivedE2 = e2;
        this.receivedClick2 = click2;
        called = true;
        try {
            macroCommand = new MockMacroCommand();
        }
        catch (Exception e) {
            // Should not happen
            e.printStackTrace();
        }

        return new ArrayList<MockMacroCommand>();
    }

    public boolean calledFillet () {

        return called;
    }

    /**
     * @return the receivedE1
     */
    public Element getReceivedE1 () {

        return receivedE1;
    }

    /**
     * @return the receivedClick1
     */
    public Point getReceivedClick1 () {

        return receivedClick1;
    }

    /**
     * @return the receivedE2
     */
    public Element getReceivedE2 () {

        return receivedE2;
    }

    /**
     * @return the receivedClick2
     */
    public Point getReceivedClick2 () {

        return receivedClick2;
    }

    public MockMacroCommand getGeneratedMacroCommand () {

        return macroCommand;
    }

}
