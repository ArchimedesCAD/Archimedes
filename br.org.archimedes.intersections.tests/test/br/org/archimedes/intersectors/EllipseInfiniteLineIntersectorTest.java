package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Point;

public class EllipseInfiniteLineIntersectorTest extends Tester {

    private Ellipse ellipse;
    private EllipseInfiniteLineIntersector intersector;
    
    
    @Before
    public void SetUp() throws NullArgumentException, InvalidArgumentException
    {
        ellipse = new Ellipse(new Point(0, 0), new Point(1, 0), new Point(0,1));
        intersector = new EllipseInfiniteLineIntersector();
    }

    @Test
    public void shouldNotReturnIntersectionPoints() throws InvalidArgumentException, NullArgumentException
    {
    	InfiniteLine verticalLine = new InfiniteLine(3.0, 0.0, 3.0, 1.0);
        assertCollectionTheSame(new ArrayList<Point>(), intersector.getIntersections(ellipse, verticalLine));
    }
    
    @Test
    public void shouldReturnOneTangencyPoint() throws InvalidArgumentException, NullArgumentException
    {
    	InfiniteLine verticalLine = new InfiniteLine(1.0, 0.0, 1.0, 1.0);
    	ArrayList<Point> interPoints = new ArrayList<Point>();
    	interPoints.add(new Point(1.0, 0.0));
    	
    	assertCollectionTheSame(interPoints, intersector.getIntersections(ellipse, verticalLine));
    }
    
    @Test
    public void shouldReturnOneTangencyPointRotated() throws InvalidArgumentException, NullArgumentException
    {
    	this.infiniteLine = new InfiniteLine(1,1, 2,0);
    	//InfiniteLine expected = new InfiniteLine(0, Math.sqrt(2), Math.sqrt(2), Math.sqrt(2));//p1y = -1.1102230246251565E-16
        //Assert.assertEquals("point should be equal.", expected.getInitialPoint().getX(), infiniteLineClone.getInitialPoint().getX(),0.01);
        //Assert.assertEquals("point should be equal.", expected.getInitialPoint(), infiniteLineClone.getInitialPoint());
        //Assert.assertEquals("point should be equal.", expected.getEndingPoint(), infiniteLineClone.getEndingPoint());
        
    	List<Point> intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(1, 1));
        assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse2, infiniteLine));        
        
        this.infiniteLine = new InfiniteLine(1,-1, 2,0);
    	intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(1, -1));
        assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse3, infiniteLine));        
        
        this.infiniteLine = new InfiniteLine(6, 5, 7, 6);
    	intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(6, 5));
        assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse4, infiniteLine));        
        
        
    }
    
    
    
}
