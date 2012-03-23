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
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.TrimManager;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.semiline.Semiline;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.progress.NewOngoingStubbing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static br.org.archimedes.trims.IsACollectionEquivalentTo.isACollectionEquivalentTo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Wesley Seidel, Bruno da Hora
 */
public class TrimCommandTest extends Tester {

    private TrimCommand trimCommand;

    private Collection<Element> references;

    Drawing drawing;

    private IntersectionManager intersectionManager;

    private ArrayList<Point> clicks;

    private InfiniteLine line1;

    private InfiniteLine line2;

    private InfiniteLine line3;

    private TrimManager trimManager;

    private Set<Element> removed;

    private Set<Element> added;


    @Before
    public void setUp () throws Exception {

        removed = null;
        added = null;
        line1 = new InfiniteLine(0.0, 0.0, 0.0, 100.0);
        line2 = new InfiniteLine(50.0, 0.0, 50.0, 100.0);
        line3 = new InfiniteLine(0.0, 50.0, 100.0, 50.0);
        references = new ArrayList<Element>();
        clicks = new ArrayList<Point>();
        trimCommand = new TrimCommand(references, clicks) {

            @Override
            protected void buildMacro (Set<Element> toRemove, Set<Element> toAdd) {

                removed = toRemove;
                added = toAdd;
            }
        };
        intersectionManager = mock(IntersectionManager.class);
        trimManager = mock(TrimManager.class);
        trimCommand.setIntersectionManager(intersectionManager);
        trimCommand.setTrimManager(trimManager);
        drawing = new Drawing("Test");
        drawing.putElement(line1, drawing.getCurrentLayer());
        drawing.putElement(line2, drawing.getCurrentLayer());
        drawing.putElement(line3, drawing.getCurrentLayer());
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

    @Test(expected = IllegalActionException.class)
    public void throwsIllegalActionExceptionIfTrimCantBeDone () throws Exception {

        Point click = new Point(75.0, 50.0);
        clicks.add(click);

        Collection<Element> expectedReferences = drawing.getUnlockedContents();

        ArrayList<Point> intersections = new ArrayList<Point>();
        intersections.add(new Point(0.0, 50.0));
        intersections.add(new Point(50.0, 50.0));
        when(
                intersectionManager.getIntersectionsBetween(eq(line3),
                        argThat(isACollectionEquivalentTo(expectedReferences)))).thenReturn(
                intersections);

        // Pretends that the trim can't be done
        Collection<Element> trimResult = Collections.emptyList();
        when(trimManager.getTrimOf(line3, intersections, click)).thenReturn(trimResult);

        trimCommand.doIt(drawing);
    }

    @Test
    public void usesAllElementsAsReferencesIfNoReferenceIsGiven () throws Exception {

        Point click = new Point(75.0, 50.0);
        clicks.add(click);

        Collection<Element> expectedReferences = drawing.getUnlockedContents();

        ArrayList<Point> intersections = new ArrayList<Point>();
        intersections.add(new Point(0.0, 50.0));
        intersections.add(new Point(50.0, 50.0));
        when(
                intersectionManager.getIntersectionsBetween(eq(line3),
                        argThat(isACollectionEquivalentTo(expectedReferences)))).thenReturn(
                intersections);

        ArrayList<Element> trimResult = new ArrayList<Element>();
        trimResult.add(new Semiline(new Point(50.0, 50.0), new Point(0.0, 50.0)));
        when(trimManager.getTrimOf(line3, intersections, click)).thenReturn(trimResult);

        trimCommand.doIt(drawing);
    }

    @Test
    public void generatedCommandPutsOnlyTheUniqueResultsOfTrim () throws Exception {

        ArrayList<Point> polylinePoints = new ArrayList<Point>();
        polylinePoints.add(new Point(0.0, 0.0));
        polylinePoints.add(new Point(25.0, 100.0));
        polylinePoints.add(new Point(50.0, 0.0));
        polylinePoints.add(new Point(75.0, 100.0));
        polylinePoints.add(new Point(100.0, 0.0));
        Polyline polyline = new Polyline(polylinePoints);
        drawing.putElement(polyline, drawing.getCurrentLayer());

        Point firstClick = new Point(6.25, 25.0);
        Point secondClick = new Point(50.0, 0.0);
        Point thirdClick = new Point(93.75, 25.0);
        clicks.add(firstClick);
        clicks.add(secondClick);
        clicks.add(thirdClick);

        references.add(line3);

        ArrayList<Point> intersections = new ArrayList<Point>();
        intersections.add(new Point(12.5, 50.0));
        intersections.add(new Point(37.5, 50.0));
        intersections.add(new Point(62.5, 50.0));
        intersections.add(new Point(87.5, 50.0));
        
        Polyline poly = eq(polyline);
        IsACollectionEquivalentTo<Collection<Element>> points = isACollectionEquivalentTo(references);
        Collection<Element> args = argThat(points);
        Collection<Point> result = intersectionManager.getIntersectionsBetween(poly, args);
        NewOngoingStubbing<Collection<Point>> call = when(result);
        call.thenReturn(intersections);

        ArrayList<Element> trimResult = new ArrayList<Element>();
        polylinePoints.set(0, new Point(12.5, 50.0));
        Polyline firstResult = new Polyline(polylinePoints);
        trimResult.add(firstResult);
        when(trimManager.getTrimOf(polyline, intersections, firstClick)).thenReturn(trimResult);

        ArrayList<Point> firstPolylinePoints = new ArrayList<Point>();
        ArrayList<Point> secondPolylinePoints = new ArrayList<Point>();
        firstPolylinePoints.add(new Point(12.5, 50.0));
        firstPolylinePoints.add(new Point(25.0, 100.0));
        firstPolylinePoints.add(new Point(37.5, 50.0));
        Polyline firstPolyline = new Polyline(firstPolylinePoints);
        secondPolylinePoints.add(new Point(62.5, 50.0));
        secondPolylinePoints.add(new Point(75.0, 100.0));
        secondPolylinePoints.add(new Point(100.0, 0.0));
        Polyline secondResult = new Polyline(secondPolylinePoints);
        Collection<Element> secondTrimResult = new ArrayList<Element>();
        secondTrimResult.add(firstPolyline);
        secondTrimResult.add(secondResult);
        when(trimManager.getTrimOf(firstResult, intersections, secondClick)).thenReturn(
                secondTrimResult);

        secondPolylinePoints.set(2, new Point(87.5, 50.0));
        Polyline secondPolyline = new Polyline(secondPolylinePoints);
        ArrayList<Element> thirdTrimResult = new ArrayList<Element>();
        thirdTrimResult.add(firstPolyline);
        thirdTrimResult.add(secondPolyline);
        when(trimManager.getTrimOf(secondResult, intersections, thirdClick)).thenReturn(
                thirdTrimResult);

        trimCommand.doIt(drawing);
        assertCollectionTheSame(Collections.singleton(polyline), removed);
        assertCollectionTheSame(thirdTrimResult, added);
    }

    @Test
    public void testEquals () throws Exception {

        references.add(line1);

        TrimCommand allElementsWithClickInOrigin = new TrimCommand(drawing.getUnlockedContents(),
                Collections.singletonList(new Point(0.0, 0.0)));
        TrimCommand allElementsWithClickOutOfOrigin = new TrimCommand(
                drawing.getUnlockedContents(), Collections.singletonList(new Point(1.0, 1.0)));
        TrimCommand line1WithClickInOrigin = new TrimCommand(references, Collections
                .singletonList(new Point(0.0, 0.0)));
        TrimCommand line1WithClickOutOfOrigin = new TrimCommand(references, Collections
                .singletonList(new Point(1.0, 1.0)));
        TrimCommand otherAllElementsWithClickInOrigin = new TrimCommand(drawing
                .getUnlockedContents(), Collections.singletonList(new Point(0.0, 0.0)));

        assertFalse(allElementsWithClickInOrigin.equals(allElementsWithClickOutOfOrigin));
        assertFalse(allElementsWithClickInOrigin.equals(line1WithClickInOrigin));
        assertFalse(allElementsWithClickInOrigin.equals(line1WithClickOutOfOrigin));
        assertFalse(allElementsWithClickOutOfOrigin.equals(line1WithClickInOrigin));
        assertFalse(allElementsWithClickOutOfOrigin.equals(line1WithClickOutOfOrigin));
        assertFalse(line1WithClickInOrigin.equals(line1WithClickOutOfOrigin));
        assertEquals(allElementsWithClickInOrigin, otherAllElementsWithClickInOrigin);
        assertEquals(otherAllElementsWithClickInOrigin, allElementsWithClickInOrigin);
        assertEquals(allElementsWithClickOutOfOrigin, allElementsWithClickOutOfOrigin);
    }

    // TODO implement hashCode in TrimCommand
    @Test
    public void hashCodesAreEqualWhenCommandsAreEqual () throws Exception {

        TrimCommand allElementsWithClickInOrigin = new TrimCommand(drawing.getUnlockedContents(),
                Collections.singletonList(new Point(0.0, 0.0)));
        TrimCommand otherAllElementsWithClickInOrigin = new TrimCommand(drawing
                .getUnlockedContents(), Collections.singletonList(new Point(0.0, 0.0)));
        assertEquals(allElementsWithClickInOrigin.hashCode(), otherAllElementsWithClickInOrigin
                .hashCode());
    }

}
