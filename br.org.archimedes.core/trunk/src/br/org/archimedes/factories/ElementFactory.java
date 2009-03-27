/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Victor D. Lopes - initial API and implementation<br>
 * Hugo Corbucci, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2007/04/09, 12:49:06, by Victor D. Lopes.<br>
 * It is part of package br.org.archimedes.factories on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.model.Element;
import br.org.archimedes.rcp.extensionpoints.ElementEPLoader;

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

        ElementEPLoader loader = new ElementEPLoader();

        Element element = null;
        Class<? extends Element> elementClass = loader
                .getElementClass(elementId);
        if (elementClass != null) {
            try {
                Constructor<? extends Element> constructor = getMatchableConstructor(
                        elementClass, args);
                element = constructor.newInstance(args);
            }
            catch (IllegalArgumentException e) {
                throw new ElementCreationException(Messages
                        .bind(Messages.ElementFactory_WrongNumberArguments,
                                elementId), e);
            }
            catch (NoSuchMethodException e) {
                throw new ElementCreationException(Messages.bind(
                        Messages.ElementFactory_WrongArgument, elementId), e);
            }
            catch (InstantiationException e) {
                throw new ElementCreationException(Messages.bind(
                        Messages.ElementFactory_NonBuildableObject, elementId),
                        e);
            }
            catch (SecurityException e) {
                throw new ElementCreationException(Messages.bind(
                        Messages.ElementFactory_MissingPlugins, elementId), e);
            }
            catch (IllegalAccessException e) {
                throw new ElementCreationException(Messages.bind(
                        Messages.ElementFactory_MissingPlugins, elementId), e);
            }
            catch (InvocationTargetException e) {
                throw new ElementCreationException(Messages.bind(
                        Messages.ElementFactory_InvalidConstructor, elementId),
                        e);
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

        // Had to insert the class cast because Java 6 considers this a compile error
        Constructor<? extends Element>[] constructors = (Constructor<? extends Element>[]) clazz
                .getConstructors();

        for (Constructor<? extends Element> constructor : constructors) {
            if (matches(constructor, parameterTypes)) {
                return constructor;
            }
        }

        throw new NoSuchMethodException(Messages.bind(
                Messages.ElementFactory_NoConstructor,
                clazz.getCanonicalName(), joinArrayWith(parameterTypes, ", "))); //$NON-NLS-1$
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

        String parameterListStr = ""; //$NON-NLS-1$
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
