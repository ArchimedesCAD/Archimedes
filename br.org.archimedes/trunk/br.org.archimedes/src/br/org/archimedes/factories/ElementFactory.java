
package br.org.archimedes.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.model.Element;

/**
 * @author vidlopes
 */
public class ElementFactory {

    /**
     * @param elementId
     *            Element's id
     * @param args
     *            The arguments (in the correct order) to create the element
     *            (the constructor with those arguments in this order MUST
     *            exist).
     * @return The element created
     * @throws ElementCreationException
     *             Thrown if there is some problem during the method invoke
     */
    public Element createElement (String elementId, Object... args)
            throws ElementCreationException {

        Element element = null;
        Class<? extends Element> elementClass = Utils
                .getElementClass(elementId);
        if (elementClass != null) {
            Class<?>[] parameterTypes = new Class[args.length];
            int i = 0;
            for (Object object : args) {
                parameterTypes[i++] = object.getClass();
            }
            try {
                Constructor<? extends Element> constructor = elementClass
                        .getConstructor(parameterTypes);
                element = constructor.newInstance(args);
            }
            catch (SecurityException e) {
                throw new ElementCreationException(
                        "Impossible to create the element "
                                + "("
                                + elementId
                                + "). "
                                + "Check that you have all the required plugins.",
                        e);
            }
            catch (IllegalArgumentException e) {
                throw new ElementCreationException(
                        "Impossible to create the element " + "(" + elementId
                                + "). " + "The arguments are wrong.", e);
            }
            catch (NoSuchMethodException e) {
                throw new ElementCreationException(
                        "Impossible to create the element " + "(" + elementId
                                + "). " + "No constructor with such arguments.",
                        e);
            }
            catch (InstantiationException e) {
                throw new ElementCreationException(
                        "Impossible to create the element "
                                + "("
                                + elementId
                                + "). "
                                + "The class could not be instantiated (thrown an exception on instacing or is not a concrete class).",
                        e);
            }
            catch (IllegalAccessException e) {
                throw new ElementCreationException(
                        "Impossible to create the element "
                                + "("
                                + elementId
                                + "). "
                                + "Check that you have all the required plugins.",
                        e);
            }
            catch (InvocationTargetException e) {
                throw new ElementCreationException(
                        "Impossible to create the element " + "(" + elementId
                                + "). " + "Cannot invoke the constructor.", e);
            }
        }
        return element;
    }
}
