
package br.org.archimedes.trims;

import br.org.archimedes.model.Element;

import org.hamcrest.Factory;
import org.mockito.ArgumentMatcher;

import java.util.Collection;

public class IsACollectionEquivalentTo<T extends Collection<Element>> extends ArgumentMatcher<Collection<Element>> {

    private final Collection<?> expected;


    public IsACollectionEquivalentTo (Collection<?> expected) {

        this.expected = expected;
    }

    @Override
    public boolean matches (Object argument) {

        try {
            Collection<?> collection = (Collection<?>) argument;
            return collection.containsAll(expected) && expected.containsAll(collection);
        }
        catch (ClassCastException e) {
            return false;
        }
    }

    @Factory
    public static <T extends Collection<Element>> IsACollectionEquivalentTo<T> isACollectionEquivalentTo (Collection<Element> expected) {

        return new IsACollectionEquivalentTo<T>(expected);
    }

}
