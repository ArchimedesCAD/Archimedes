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
    private InfiniteLine infiniteLine;
    private EllipseInfiniteLineIntersector intersector;
    
    private Ellipse ellipse2;
    private Ellipse ellipse3;
    private Ellipse ellipse4;
    
    
    @Before
    public void SetUp() throws NullArgumentException, InvalidArgumentException
    {
        this.ellipse = new Ellipse(new Point(0, 0), new Point(2, 0), new Point(0,1));
        this.intersector = new EllipseInfiniteLineIntersector();
        this.ellipse2 = new Ellipse(new Point(0, 0), new Point(2, -2), new Point(1,1));
        this.ellipse3 = new Ellipse(new Point(0, 0), new Point(-2, -2), new Point(1,-1));
        this.ellipse4 = new Ellipse(new Point(5, 6), new Point(3, 4), new Point(6, 5));
        
    }

    @Test
    public void shouldNotReturnIntersectionPoints() throws InvalidArgumentException, NullArgumentException
    {
         this.infiniteLine = new InfiniteLine(2.1, 0, 2.1, 10);
         assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(ellipse, infiniteLine));
         
         this.infiniteLine = new InfiniteLine(0, 2, 10, 2);
         assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(ellipse, infiniteLine));
         
         this.infiniteLine = new InfiniteLine(3, 0, 3, 10);
         assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(ellipse, infiniteLine));
         
         
    }
    
    @Test
    public void shouldReturnOneTangencyPoint() throws InvalidArgumentException, NullArgumentException
    {
        this.infiniteLine = new InfiniteLine(2, 0, 2, 10);
        List<Point> intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(2, 0));
        //assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse, infiniteLine));
        
        this.infiniteLine = new InfiniteLine(0, 1, 10, 1);
        intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(0, 1));
        assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse, infiniteLine));
        
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
