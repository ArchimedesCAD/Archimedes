package br.org.archimedes.trim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;

public class CircleTrimTest extends Tester {

	private Circle testCircle;
	private Trimmer trimmer;
	
	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		testCircle = new Circle(new Point(0.0, 0.0), 1.0);
		trimmer = new CircleTrimmer();
	}
	
	@Test
	public void tangentLineCannotTrimCircle() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(1.0, 1.0), new Point(1.0, -1.0));
		Collection<Element> trimmed = trimmer.trim(testCircle, Collections.singleton(line), new Point(0.0, 1.0));
		assertCollectionTheSame(Collections.singleton(testCircle), trimmed);
	}
	
	@Test
	public void lineIntersectsCircleOnceCannotTrimCircle() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(0.0, 2.0), new Point(0.0, 0.5));
		Collection<Element> trimmed = trimmer.trim(testCircle, Collections.singleton(line), new Point(1.0, 0.0));
		assertCollectionTheSame(Collections.singleton(testCircle), trimmed);
	}
	
	@Test
	public void secantLineTrimsCircleResultsHalfCircle() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(0.0, 2.0), new Point(0.0, -2.0));
		Collection<Element> trimmed = trimmer.trim(testCircle, Collections.singleton(line), new Point(1.0, 0.0));
		assertCollectionTheSame(Collections.singleton(new Arc(new Point(0.0, 1.0), new Point(-1.0, 0.0), new Point(0.0, -1.0))), trimmed);
	}
	
	@Test
	public void twoSecantLinesTrimsCircleResultsThreeQuartersOfCircle() throws NullArgumentException, InvalidArgumentException {
		Element line1 = new Line(new Point(0.0, 2.0), new Point(0.0, -2.0));
		Element line2 = new Line(new Point(-2.0, 0.0), new Point(2.0, 0.0));
		Collection<Element> references = new ArrayList<Element>();
		references.add(line1);
		references.add(line2);
		Collection<Element> trimmed = trimmer.trim(testCircle, references, new Point(Math.sqrt(2.0) / 2.0, Math.sqrt(2.0) / 2.0));
		assertCollectionTheSame(Collections.singleton(new Arc(new Point(0.0, 1.0), new Point(-1.0, 0.0), new Point(1.0, 0.0))), trimmed);
	}
	
	@Test
	public void secantLineTrimCircleResultsLeftHalfOfCircleWhenClickedInIntersection() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(0.0, 2.0), new Point(0.0, -2.0));
		Collection<Element> trimmed = trimmer.trim(testCircle, Collections.singleton(line), new Point(0.0, 1.0));
		assertCollectionTheSame(Collections.singleton(new Arc(new Point(0.0, 1.0), new Point(-1.0, 0.0), new Point(0.0, -1.0))), trimmed);
	}
	
	@Test
	public void referencesDontIntersectCircleDontTrim() throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(2.0, 2.0), new Point(2.0, -2.0));
		Collection<Element> trimmed = trimmer.trim(testCircle, Collections.singleton(line), new Point(1.0, 0.0));
		assertCollectionTheSame(Collections.singleton(testCircle), trimmed);
	}
}
