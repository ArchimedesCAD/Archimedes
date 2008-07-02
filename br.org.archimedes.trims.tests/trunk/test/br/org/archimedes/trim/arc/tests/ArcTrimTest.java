package br.org.archimedes.trim.arc.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trim.arc.ArcTrimmer;
import br.org.archimedes.trims.interfaces.Trimmer;

public class ArcTrimTest extends Tester {
	private Arc testArc;
	private Trimmer trimmer;
	
	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		testArc = new Arc(new Point(1.0, 0.0), new Point(0.0, 1.0), new Point(-1.0, 0.0));
		trimmer = new ArcTrimmer();
	}
	
	@Test
	public void tangentLineTrimArcResultsHalfArc() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(-1.0, 1.0), new Point(1.0, 1.0));
		Collection<Element> trimmed = trimmer.trim(testArc, Collections.singleton(line), new Point(1.0, 0.0));
		assertCollectionTheSame(Collections.singleton(new Arc(new Point(0.0, 1.0), new Point(-1.0, 0.0), new Point(0.0, 0.0), true)), trimmed);
	}
	
	@Test
	public void lineIntersectsArcOnceReturnsQuarterCircle() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(0.0, 2.0), new Point(0.0, 0.0));
		Collection<Element> trimmed = trimmer.trim(testArc, Collections.singleton(line), new Point(1.0, 0.0));
		assertCollectionTheSame(Collections.singleton(new Arc(new Point(0.0, 1.0), new Point(-1.0, 0.0), new Point(0.0, 0.0), true)), trimmed);
	}
	
	@Test
	public void lineIntersectsExtremePointsOfArcReturnsSameArc() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(-1.0, 0.0), new Point(1.0, 0.0));
		Collection<Element> trimmed = trimmer.trim(testArc, Collections.singleton(line), new Point(1.0, 0.0));
		assertCollectionTheSame(Collections.singleton(testArc), trimmed);
	}
	
	@Test
	public void lineIntersectsArcTwiceReturnsTwoArcsWhenClickedOverLine() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(-2.0, 0.5), new Point(2.0, 0.5));
		Collection<Element> trimmed = trimmer.trim(testArc, Collections.singleton(line), new Point(0.0, 1.0));
		Collection<Element> expected = new ArrayList<Element>();
		expected.add(new Arc(new Point(1.0, 0.0), new Point(Math.sqrt(3.0) / 2.0, 0.5), new Point(0.0, 0.0), true));
		expected.add(new Arc(new Point(-Math.sqrt(3.0) / 2.0, 0.5), new Point(-1.0, 0.0), new Point(0.0, 0.0), true));
		assertCollectionTheSame(expected, trimmed);
		// Should have the same behavior if click is not on the arc
		trimmed = trimmer.trim(testArc, Collections.singleton(line), new Point(0.0, 3.0));
		assertCollectionTheSame(expected, trimmed);
	}
	
	@Test
	public void lineDoesntIntersectArcWouldIfArcWasCircleReturnsSameArc() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(-1.0, -0.5), new Point(1.0, -0.5));
		Collection<Element> trimmed = trimmer.trim(testArc, Collections.singleton(line), new Point(1.0, 0.0));
		assertCollectionTheSame(Collections.singleton(testArc), trimmed);
	}
	
	@Test
	public void twoLinesIntersectArcReturnsThreeQuartersOfArc() throws NullArgumentException, InvalidArgumentException {
		Collection<Element> references = new ArrayList<Element>();
		references.add(new Line(new Point(-2.0, -2.0), new Point(2.0, 2.0)));
		references.add(new Line(new Point(-2.0, 2.0), new Point(2.0, -2.0)));
		Collection<Element> trimmed = trimmer.trim(testArc, references, new Point(1.0, 0.0));
		double cos45 = Math.sqrt(2.0) / 2.0;
		assertCollectionTheSame(Collections.singleton(new Arc(new Point(cos45, cos45), new Point(0.0, 1.0), new Point(-1.0, 0.0))), trimmed);
	}
}
