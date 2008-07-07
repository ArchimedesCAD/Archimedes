package br.org.archimedes.trim.line.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trim.line.LineTrimmer;
import br.org.archimedes.trims.interfaces.Trimmer;

public class LineTrimTest extends Tester {
	Trimmer trimmer = new LineTrimmer();
	Collection<Element> references = new ArrayList<Element>();
	
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		Line line1 = new Line(new Point(1.0, 4.0), new Point(-1.0, 0.0));
		Line line2 = new Line(new Point(2.0, 4.0), new Point(2.0, 0.0));
		references.add(line1);
		references.add(line2);
	}
	
	@Test
	public void testNullLineArgument(){
		try {
			trimmer.trim(null, references, new Point(0.0, 0.0));
		} catch (NullArgumentException e) {
			Assert.assertTrue("Should throw null argument exception", true);
			return;
		}
		
		Assert.assertFalse("Should throw null argument exception", true);
	}
	
	@Test
	public void testNullReferencesArgument() throws InvalidArgumentException, NullArgumentException{
		Line line3 = new Line(new Point(-1.0, 2.0), new Point(3.0, 2.0));
		try {
			trimmer.trim(line3, null, new Point(0.0, 0.0));
		} catch (NullArgumentException e) {
			Assert.assertTrue("Should throw null argument exception", true);
			return;
		}
		
		Assert.assertFalse("Should throw null argument exception", true);
	}
	
	@Test
	public void lineTrimsCenter() throws NullArgumentException, InvalidArgumentException{
		Line line3 = new Line(new Point(-1.0, 2.0), new Point(3.0, 2.0));
		Collection<Element> collection = trimmer.trim(line3, references, new Point(1.0, 2.0));
		
		assertCollectionContains(collection, new Line(new Point(-1.0, 2.0), new Point(0.0, 2.0)));
		assertCollectionContains(collection, new Line(new Point(2.0, 2.0), new Point(3.0, 2.0)));
		Assert.assertTrue(collection.size() == 2);
	}
	
	@Test
	public void lineTrimsEndingPortionOfLine() throws NullArgumentException, InvalidArgumentException{
		Line line3 = new Line(new Point(-1.0, 2.0), new Point(3.0, 2.0));
		Collection<Element> collection = trimmer.trim(line3, references, new Point(-0.5, 2.0));
		
		assertCollectionContains(collection, new Line(new Point(0.0, 2.0), new Point(3.0, 2.0)));
		Assert.assertTrue(collection.size() == 1);
	}
	
	@Test
	public void lineTrimsExtremePoint() throws NullArgumentException, InvalidArgumentException{
		Line line3 = new Line(new Point(-1.0, 2.0), new Point(2.0, 2.0));
		Collection<Element> collection = trimmer.trim(line3, references, new Point(1.0, 2.0));
		
		assertCollectionContains(collection, new Line(new Point(-1.0, 2.0), new Point(0.0, 2.0)));
		Assert.assertTrue(collection.size() == 1);
	}
	
	@Test
	public void lineDoesntTrimWhenHasNoIntersections() throws NullArgumentException, InvalidArgumentException{
		Line line3 = new Line(new Point(0.5, 2.0), new Point(1.5, 2.0));
		Collection<Element> collection = trimmer.trim(line3, references, new Point(1.0, 2.0));
		
		assertCollectionTheSame(Collections.EMPTY_LIST, collection);
	}
	
	@Test
	public void lineDoesntTrimWhenClickingExactlyOnIntersectionPoint() throws NullArgumentException, InvalidArgumentException{
		Line line3 = new Line(new Point(-1.0, 2.0), new Point(3.0, 2.0));
		Collection<Element> collection = trimmer.trim(line3, references, new Point(2.0, 2.0));
		
		assertCollectionContains(collection, new Line(new Point(-1.0, 2.0), new Point(0.0, 2.0)));
		assertCollectionContains(collection, new Line(new Point(2.0, 2.0), new Point(3.0, 2.0)));
		Assert.assertTrue(collection.size() == 2);
	}
}
