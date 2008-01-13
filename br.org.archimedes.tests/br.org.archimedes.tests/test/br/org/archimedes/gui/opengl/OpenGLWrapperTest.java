/*
 * Created on 24/03/2006
 */

package br.org.archimedes.gui.opengl;

import org.junit.After;
import org.junit.Before;

public class OpenGLWrapperTest {

    OpenGLWrapper openGL;


    public OpenGLWrapperTest (String name) {

        // super(name);
        openGL = OpenGLWrapper.getInstance();
    }

    @Before
    protected void setUp () throws Exception {

        // super.setUp();
    }

    @After
    protected void tearDown () throws Exception {

        // super.tearDown();
    }

    // public void testAddDrawingContext () {
    //
    // Drawing drawing = new Drawing("Drawing");
    // GLCanvas canvas = new GLCanvas(new Shell(), SWT.NO_BACKGROUND,
    // new GLData());
    //
    // /* drawing and context null */
    // try {
    // openGL.addDrawingCanvas(null, null);
    // fail("Should not catch this exception.");
    // }
    // catch (NullArgumentException e) {
    // /* Should catch this exception */
    // assertTrue(true);
    // }
    //
    // /* just context null */
    // try {
    // openGL.addDrawingCanvas(drawing, null);
    // fail("Should not catch this exception.");
    // }
    // catch (NullArgumentException e) {
    // /* Should catch this exception */
    // assertTrue(true);
    // }
    //
    // /* just drawing null */
    // try {
    // openGL.addDrawingCanvas(null, canvas);
    // fail("Should not catch this exception.");
    // }
    // catch (NullArgumentException e) {
    // /* Should catch this exception */
    // assertTrue(true);
    // }
    //
    // /* adding a drawing associated with a context */
    // try {
    // openGL.addDrawingCanvas(drawing, canvas);
    // Map<Drawing, GLCanvas> map = openGL.getDrawingCanvas();
    // assertTrue("The drawing should be in the map!", map
    // .containsKey(drawing));
    // assertTrue("The drawing should be associated to the context.", map
    // .get(drawing).equals(canvas));
    //
    // }
    // catch (NullArgumentException e) {
    // fail("Should not catch this exception.");
    // }
    // }
}
