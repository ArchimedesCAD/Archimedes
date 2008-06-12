
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
            try {
                Constructor<? extends Element> constructor = getMatchableConstructor(
                        elementClass, args);
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

    /**
     * Ignores generic unchecked cast because of Class backwards compatibility
     * issues.
     * 
     * @param clazz
     *            The class in which we want to search for a constructor
     * @param args
     *            The arguments that should be matched
     * @return The first constructor that matches the arguments type
     * @throws NoSuchMethodException
     *             Thrown if no matchable Constructor is found
     */
    @SuppressWarnings("unchecked")
    private Constructor<? extends Element> getMatchableConstructor (
            Class<? extends Element> clazz, Object... args)
            throws NoSuchMethodException {

        Class<?>[] parameterTypes = extractTypes(args);

        Constructor<? extends Element>[] constructors = clazz.getConstructors();

        for (Constructor<? extends Element> constructor : constructors) {
            if (matches(constructor, parameterTypes)) {
                return constructor;
            }
        }

        throw new NoSuchMethodException(clazz.getCanonicalName() + ".<init>("
                + joinArrayWith(parameterTypes, ", ") + ")");
    }

    /**
     * @param array
     *            An array of object
     * @param separator
     *            The string that should be inserted between elements
     * @return A string that joins transform element to strings with toString()
     *         and join them with the separator.
     */
    private String joinArrayWith (Object[] array, String separator) {

        String parameterListStr = "";
        boolean first = true;
        for (Object object : array) {
            if (first) {
                first = false;
            }
            else {
                parameterListStr += separator;
            }

            parameterListStr += object.toString();
        }
        return parameterListStr;
    }

    /**
     * @param args
     *            Arguments to which we want the types
     * @return An array of classes that represent the types of the arguments
     */
    private Class<?>[] extractTypes (Object... args) {

        Class<?>[] parameterTypes = new Class[args.length];
        int i = 0;
        for (Object object : args) {
            parameterTypes[i++] = object.getClass();
        }
        return parameterTypes;
    }

    /**
     * @param constructor
     *            The constructor to test
     * @param parameterTypes
     *            The types of the arguments it must be able to receive
     * @return true if this constructor accepts the specified types
     */
    private boolean matches (Constructor<? extends Element> constructor,
            Class<?>[] parameterTypes) {

        boolean matches = true;

        Class<?>[] constructorTypes = constructor.getParameterTypes();
        matches = (constructorTypes.length == parameterTypes.length);

        for (int i = 0; matches && i < parameterTypes.length; i++) {
            if ( !constructorTypes[i].isAssignableFrom(parameterTypes[i])) {
                matches = false;
            }
        }

        return matches;
    }
}
