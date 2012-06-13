package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;

public class EllipseLineIntersectorTest extends Tester {

    private Ellipse ellipse;
    private Line line;
    private EllipseLineIntersector intersector;
    
    private Ellipse ellipse2x3;
    
    
    @Before
    public void SetUp() throws NullArgumentException, InvalidArgumentException
    {
    	this.intersector = new EllipseLineIntersector();
    	
        this.ellipse = new Ellipse(new Point(0, 0), new Point(2, 0), new Point(0,1));
        this.ellipse2x3 = new Ellipse(new Point(-4, 2), new Point(
				-1.401923788646684, 3.5), new Point(-5, 3.732050807568877));
    }

    @Test
    public void shouldNotReturnIntersectionPoints() throws InvalidArgumentException, NullArgumentException
    {
         this.line = new Line(2.1, 0, 2.1, 10);
         assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(ellipse, line));
         
         this.line = new Line(0, 2, 10, 2);
         assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(ellipse, line));
         
         this.line = new Line(3, 0, 3, 10);
         assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(ellipse, line));
         
         
    }
    
    @Test
    public void shouldReturnOneTangencyPoint() throws InvalidArgumentException, NullArgumentException
    {
        this.line = new Line(2, 0, 2, 10);
        List<Point> intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(2, 0));
        assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse, line));
        
        this.line = new Line(-10, 1, 10, 1);
        intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(0, 1));
        assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse, line));
        
    }
    
    @Test
    public void shouldReturnOneIntersectionPointRotated() throws InvalidArgumentException, NullArgumentException
    {
        this.line = new Line(-1.0, 0.0, -4.7069360559627, -0.28210643262392987);
    	Collection<Point> result = intersector.getIntersections(ellipse2x3, line);
        System.out.println(result);
        
        List<Point> intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(-4.7069360559627, -0.28210643262392987));
        assertCollectionTheSame(intersectionPoints, result);
    }
        
    
}
