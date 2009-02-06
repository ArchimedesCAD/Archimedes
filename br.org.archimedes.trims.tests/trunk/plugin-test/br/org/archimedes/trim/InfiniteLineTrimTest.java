
package br.org.archimedes.trim;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.Semiline;
import br.org.archimedes.trims.interfaces.Trimmer;

public class InfiniteLineTrimTest extends Tester {

    Trimmer trimmer = new InfiniteLineTrimmer();

    Collection<Element> references = new ArrayList<Element>();


    public void setUp () throws NullArgumentException, InvalidArgumentException {

        InfiniteLine biasedLine = new InfiniteLine(new Point(1.0, 4.0),
                new Point( -1.0, 0.0));
        InfiniteLine verticalXLine = new InfiniteLine(new Point(2.0, 4.0),
                new Point(2.0, 0.0));
        references.add(biasedLine);
        references.add(verticalXLine);
    }

    @Test(expected = NullArgumentException.class)
    public void testNullLineArgument () throws NullArgumentException {

        trimmer.trim(null, references, new Point(0.0, 0.0));
    }

    @Test(expected = NullArgumentException.class)
    public void testNullReferencesArgument () throws NullArgumentException,
            InvalidArgumentException {

        InfiniteLine xline3 = new InfiniteLine(new Point( -1.0, 2.0),
                new Point(3.0, 2.0));
        trimmer.trim(xline3, null, new Point(0.0, 0.0));
    }

    @Test
    public void infiniteLineTrimsCenter () throws NullArgumentException,
            InvalidArgumentException {

        InfiniteLine horizontalXLine = new InfiniteLine(new Point( -1.0, 2.0),
                new Point(3.0, 2.0));
        Collection<Element> collection = trimmer.trim(horizontalXLine,
                references, new Point(1.0, 2.0));

        assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
                new Point( -1.0, 2.0)));
        assertCollectionContains(collection, new Semiline(new Point(2.0, 2.0),
                new Point(3.0, 2.0)));
        Assert
                .assertEquals(
                        "A trim between references should produce exactly 2 semilines.",
                        2, collection.size());
    }

    @Test
    @Ignore
    // FIXME: make it work even if initial and ending points are very close.
    public void infiniteLineDefinedVerySmallTrimsCenter ()
            throws NullArgumentException, InvalidArgumentException {

        InfiniteLine horizontalXLine = new InfiniteLine(new Point(0.5, 2.0),
                new Point(0.6, 2.0));
        Collection<Element> collection = trimmer.trim(horizontalXLine,
                references, new Point(1.0, 2.0));

        assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
                new Point( -1.0, 2.0)));
        assertCollectionContains(collection, new Semiline(new Point(2.0, 2.0),
                new Point(3.0, 2.0)));
        Assert
                .assertEquals(
                        "A trim between references should produce exactly 2 semilines.",
                        2, collection.size());
    }

    @Test
    public void infiniteLineTrimsEndingPortionOfLine ()
            throws NullArgumentException, InvalidArgumentException {

        InfiniteLine horizontalXLine = new InfiniteLine(new Point( -1.0, 2.0),
                new Point(3.0, 2.0));
        Collection<Element> collection = trimmer.trim(horizontalXLine,
                references, new Point( -0.5, 2.0));

        assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
                new Point(3.0, 2.0)));
        Assert.assertEquals(
                "A trim at the begging should produce exactly 1 semiline.", 1,
                collection.size());
    }

    @Test
    public void infiniteLineTrimsLefterPartWhenClickingExactlyOnIntersectionPoint ()
            throws NullArgumentException, InvalidArgumentException {

        InfiniteLine horizontalXLine = new InfiniteLine(new Point( -1.0, 2.0),
                new Point(3.0, 2.0));
        Collection<Element> collection = trimmer.trim(horizontalXLine,
                references, new Point(2.0, 2.0));

        assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
                new Point( -1.0, 2.0)));
        assertCollectionContains(collection, new Semiline(new Point(2.0, 2.0),
                new Point(3.0, 2.0)));
        Assert.assertEquals(
                "A trim at an intersection point removes the lefter part.", 2,
                collection.size());
    }
}
