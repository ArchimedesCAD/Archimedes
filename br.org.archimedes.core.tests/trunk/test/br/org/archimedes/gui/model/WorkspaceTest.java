/*
 * Created on 10/04/2006
 */

package br.org.archimedes.gui.model;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;

public class WorkspaceTest {

    private Workspace workspace;


    public WorkspaceTest () {

        workspace = br.org.archimedes.Utils.getWorkspace();
    }

    @Test
    public void testModelToScreen () {

        /* Tests converting a null point */
        try {
            workspace.modelToScreen(null);
            Assert.fail("Should throw a null argument exception.");
        }
        catch (NullArgumentException e) {
            Assert.assertTrue("Should catch this exception.", true);
        }

        Point viewport = workspace.getViewport();

        /* Minimum x and y */
        testModelToScreenCase(0.0, 0.0, 0.0, 0.0);
        /* Minimum x and maximum y */
        testModelToScreenCase(0.0, viewport.getY(), 0.0, viewport.getY());
        /* Minimum y and maximum x */
        testModelToScreenCase(viewport.getX(), 0.0, viewport.getX(), 0.0);
        /* Maximum x and y */
        testModelToScreenCase(viewport.getX(), viewport.getY(), 1.0, 1.0);
        /* Center of the model viewport */
        testModelToScreenCase((viewport.getX() / 2.0), (viewport.getY() / 2.0),
                0.5, 0.5);
    }

    public void testModelToScreenCase (double x, double y, double expectedX,
            double expectedY) {

        Point m, s;

        m = new Point(x, y);
        try {
            s = workspace.modelToScreen(m);

            Assert.assertTrue("Expected: (" + expectedX + ", " + expectedY
                    + ")  Returned: (" + s.getX() + ", " + s.getY() + ")", Math
                    .abs(s.getX() - expectedX) <= Constant.EPSILON
                    && Math.abs(s.getY() - expectedY) <= Constant.EPSILON);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not catch this exception.");
        }
    }

    @Test
    public void testScreenToModel () {

        /* Tests converting a null point */
        try {
            workspace.screenToModel((Point) null);
            Assert.fail("Should throw a null argument exception.");
        }
        catch (NullArgumentException e) {
            Assert.assertTrue("Should catch this exception.", true);
        }

        Point viewport = workspace.getViewport();

        /* Minimum x and y */
        testScreenToModelCase(0.0, 0.0, 0.0, 0.0);
        /* Minimum x and maximum y */
        testScreenToModelCase(0.0, 1.0, 0.0, viewport.getY());
        /* Minimum y and maximum x */
        testScreenToModelCase(1.0, 0.0, viewport.getX(), 0.0);
        /* Maximum x and y */
        testScreenToModelCase(1.0, 1.0, viewport.getX(), viewport.getY());
        /* Center of the model viewport */
        testScreenToModelCase(0.5, 0.5, (viewport.getX() / 2.0), (viewport
                .getY() / 2.0));
    }

    public void testScreenToModelCase (double x, double y, double expectedX,
            double expectedY) {

        Point m, s;

        s = new Point(x, y);

        try {
            m = workspace.screenToModel(s);

            Assert.assertTrue("Expected: (" + expectedX + ", " + expectedY
                    + ")  Returned: (" + m.getX() + ", " + m.getY() + ")", Math
                    .abs(m.getX() - expectedX) <= Constant.EPSILON
                    && Math.abs(m.getY() - expectedY) <= Constant.EPSILON);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not catch this exception.");
        }
    }
}
