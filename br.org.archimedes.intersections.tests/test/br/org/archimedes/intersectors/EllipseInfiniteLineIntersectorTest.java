package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private InfiniteLine infiniteLine2;
    private EllipseInfiniteLineIntersector intersector;
    
    private Ellipse ellipse2;
    
    
    @Before
    public void SetUp() throws NullArgumentException, InvalidArgumentException
    {
        this.ellipse = new Ellipse(new Point(0, 0), new Point(2, 0), new Point(0,1));
        this.intersector = new EllipseInfiniteLineIntersector();
        
        this.ellipse2 = new Ellipse(new Point(0, 0), new Point(2, -2), new Point(1,1));
        
        
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
        assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse, infiniteLine));
        
        this.infiniteLine = new InfiniteLine(0, 1, 10, 1);
        intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(0, 1));
        assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse, infiniteLine));
        
    }
    
    @Test
    public void shouldReturnOneTangencyPoint2() throws InvalidArgumentException, NullArgumentException
    {
        this.infiniteLine2 = new InfiniteLine(1, 1, 2, 0);
        List<Point> intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(1, 1));
        assertCollectionTheSame(intersectionPoints, intersector.getIntersections(ellipse2, infiniteLine2));        
        
        
    }
}
