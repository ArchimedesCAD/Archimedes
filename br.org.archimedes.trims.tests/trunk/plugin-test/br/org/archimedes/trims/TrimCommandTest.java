/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Wesley Seidel, Bruno da Hora - initial API and implementation<br>
 * <br>
 * This file was created on 28/05/2009, 08:24:08.<br>
 * It is part of br.org.archimedes.trims on the br.org.archimedes.trims.tests project.<br>
 */

package br.org.archimedes.trims;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.semiline.Semiline;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Wesley Seidel, Bruno da Hora
 */
public class TrimCommandTest extends Tester {

    private TrimCommand trimCommand;

    private Collection<Element> references;

    Drawing drawing;

    private MockIntersectionManager mockIntersectionManager;

    private ArrayList<Point> clicks;

    private InfiniteLine line1;

    private InfiniteLine line2;

    private InfiniteLine line3;

    private MockTrimManager mockTrimManager;

    private Polyline polyline;


    @Before
    public void setUp () throws Exception {

        line1 = new InfiniteLine(0.0, 0.0, 0.0, 100.0);
        line2 = new InfiniteLine(50.0, 0.0, 50.0, 100.0);
        line3 = new InfiniteLine(0.0, 50.0, 100.0, 50.0);
        ArrayList<Point> polylinePoints = new ArrayList<Point>();
        polylinePoints.add(new Point(0.0, 0.0));
        polylinePoints.add(new Point(25.0, 100.0));
        polylinePoints.add(new Point(50.0, 0.0));
        polylinePoints.add(new Point(75.0, 100.0));
        polylinePoints.add(new Point(100.0, 0.0));
        polyline = new Polyline(polylinePoints);
        references = new ArrayList<Element>();
        clicks = new ArrayList<Point>();
        trimCommand = new TrimCommand(references, clicks);
        mockIntersectionManager = new MockIntersectionManager();
        mockTrimManager = new MockTrimManager();
        trimCommand.setIntersectionManager(mockIntersectionManager);
        trimCommand.setTrimManager(mockTrimManager);
        drawing = new Drawing("Test");
        drawing.putElement(line1);
        drawing.putElement(line2);
        drawing.putElement(line3);
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @Test(expected = NullArgumentException.class)
    public void doItWithDrawingNullThrowsNullArgumentException () throws Exception {

        trimCommand.doIt(null);
    }

    @Test(expected = NullArgumentException.class)
    public void undoItWithDrawingNullThrowsNullArgumentException () throws Exception {

        trimCommand.undoIt(null);
    }

    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentExceptionIfClicksIsNull () throws Exception {

        trimCommand = new TrimCommand(references, null);
    }
    
    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentExceptionIfReferencesIsNull () throws Exception {

        trimCommand = new TrimCommand(null, clicks);
    }
    
    @Test(expected = IllegalActionException.class)
    public void throwsIllegalActionExceptionIfClickIsNotOnAElement () throws Exception {

        clicks.add(new Point(75.0, 75.0));
        trimCommand.doIt(drawing);
    }
    
    @Test
    public void usesAllElementsAsReferencesIfNoReferenceIsGiven () throws Exception {

        clicks.add(new Point(75.0, 50.0));
        ArrayList<Point> intersections = new ArrayList<Point>();
        intersections.add(new Point(0.0, 50.0));
        intersections.add(new Point(50.0, 50.0));
        mockIntersectionManager.setResult(intersections);
        ArrayList<Element> trimResult = new ArrayList<Element>();
        trimResult.add(new Semiline(new Point(50.0, 50.0), new Point(0.0, 50.0)));
        mockTrimManager.setResult(trimResult);
        trimCommand.doIt(drawing);
        assertCollectionTheSame(drawing.getUnlockedContents(), mockIntersectionManager.getReferences());
        assertEquals(new Point(75.0, 50.0), mockTrimManager.getClick());
    }
    
    @Test
    @Ignore // Not ready
    public void generatedCommandPutsOnlyTheUniqueResultsOfTrim () throws Exception {

        drawing.putElement(polyline);
        clicks.add(new Point(0.0, 0.0));
        clicks.add(new Point(50.0, 0.0));
        clicks.add(new Point(100.0, 0.0));
        references.add(line3);
        ArrayList<Point> intersections = new ArrayList<Point>();
        intersections.add(new Point(12.5, 50.0));
        intersections.add(new Point(37.5, 50.0));
        intersections.add(new Point(62.5, 50.0));
        intersections.add(new Point(87.5, 50.0));
        mockIntersectionManager.setResult(intersections);
        ArrayList<Element> trimResult = new ArrayList<Element>();
        trimResult.add(new Semiline(new Point(50.0, 50.0), new Point(0.0, 50.0)));
    }

}