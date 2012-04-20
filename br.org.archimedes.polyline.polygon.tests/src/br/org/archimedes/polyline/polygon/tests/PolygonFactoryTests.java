package br.org.archimedes.polyline.polygon.tests;


import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.polygon.PolygonFactory;
import br.org.archimedes.helper.FactoryTester;



public class PolygonFactoryTests extends FactoryTester {
    
    private CommandFactory factory;
    
    private Drawing drawing;


    @Before
    public void setUp () throws Exception {
        
        factory = new PolygonFactory();
        drawing = new Drawing("Drawing");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }
    
    @After
    public void tearDown () throws Exception {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }
    
    @Test
    public void canCreatePolygonByDefault () {
        int sides = 3;
        Point center = new Point(0.0, 0.0);
        Point initialPoint = new Point(0.0, 1.0);
        
        assertBegin(factory, false);
        
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, center);
        assertInvalidNext(factory, "f");
        
        assertSafeNext(factory, "i", false);
        
        assertInvalidNext(factory, "i");
        assertInvalidNext(factory, center);
        
        assertSafeNext(factory, sides, false);
        
        assertInvalidNext(factory, "i");
        assertInvalidNext(factory, sides);
        
        assertSafeNext(factory, center, false);
        
        assertInvalidNext(factory, "i");
        assertInvalidNext(factory, sides);
        
        assertSafeNext(factory, initialPoint, true);
        
        //Again
        assertBegin(factory, false);
        assertSafeNext(factory, "c", false);
        assertSafeNext(factory, sides, false);
        assertSafeNext(factory, center, false);
        assertSafeNext(factory, initialPoint, true);
        
        //Again
        assertBegin(factory, false);
        assertSafeNext(factory, sides, false);
        assertSafeNext(factory, center, false);
        assertSafeNext(factory, initialPoint, true);
    }
    
    @Test
    public void testCancel () {

        int sides = 3;
        Point center = new Point(0.0, 0.0);

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, sides, false);
        assertCancel(factory, false);
        
        assertBegin(factory, false);
        assertSafeNext(factory, sides, false);
        assertSafeNext(factory, center, false);
        assertCancel(factory, false);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
