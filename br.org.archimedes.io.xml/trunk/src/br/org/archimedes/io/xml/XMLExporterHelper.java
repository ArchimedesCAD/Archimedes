/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/31, 12:37:35, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.io.xml on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Utility class with methods to convert basic elements to XML such as:
 * Point and Vector<br>
 * Replaces the write(...) methods of the old version to export pieces
 * of the drawing that are not Elements. Should write the XML according
 * to the specification FileSchema.xsd
 * 
 * @author julien
 */
public final class XMLExporterHelper {

    private static final String POINT_TAG = "point"; //$NON-NLS-1$

    private static final String VECTOR_TAG = "vector"; //$NON-NLS-1$

    private static final String POINT_FORMAT = "<%s x=\"%s\" y=\"%s\" />"; //$NON-NLS-1$

    private static final String VECTOR_FORMAT = "<%s>\n\t%s\n</%s>"; //$NON-NLS-1$


    /**
     * C'tor privado.
     */
    private XMLExporterHelper () {

    }

    /**
     * Escreve a representação XML deste ponto neste OutputStream.
     * 
     * @param point
     *            o Point
     * @param output
     *            o OutputStream
     * @throws IOException
     *             Se não conseguir escrever no OutputStream
     */
    public static void write (final Point point, final OutputStream output)
            throws IOException {

        write(point, POINT_TAG, output);
    }

    /**
     * Escreve a representação XML deste ponto neste OutputStream usando este
     * tag name.
     * 
     * @param point
     *            o Point
     * @param tag
     *            o nome do Tag xml
     * @param output
     *            o OutputStream
     * @throws IOException
     *             Se não conseguir escrever no OutputStream
     */
    public static void write (final Point point, final String tag,
            final OutputStream output) throws IOException {

        output.write(xmlFor(tag, point).getBytes());
    }

    /**
     * Escreve a representação XML deste Vector neste OutputStream.
     * 
     * @param vector
     *            o Vector
     * @param output
     *            o OutputStream
     * @throws IOException
     *             Se não conseguir escrever no OutputStream
     */
    public static void write (final Vector vector, final OutputStream output)
            throws IOException {

        write(vector, VECTOR_TAG, output);
    }

    /**
     * Escreve a representação XML deste Vector neste OutputStream usando este
     * tag name.
     * 
     * @param vector
     *            o Vector
     * @param tag
     *            o nome do Tag xml
     * @param output
     *            o OutputStream
     * @throws IOException
     *             Se não conseguir escrever no OutputStream
     */
    public static void write (final Vector vector, final String tag,
            final OutputStream output) throws IOException {

        output.write(xmlFor(tag, vector).getBytes());
    }

    public static String xmlFor (final Point point) {

        return String.format(POINT_FORMAT, POINT_TAG, point.getX(), point
                .getY());
    }

    public static String xmlFor (final String tag, final Point point) {

        return String.format(POINT_FORMAT, tag, point.getX(), point.getY());
    }

    public static String xmlFor (final Vector vector) {

        return String.format(VECTOR_FORMAT, VECTOR_TAG, xmlFor(POINT_TAG,
                vector.getPoint()), VECTOR_TAG);
    }

    public static String xmlFor (final String tag, final Vector vector) {

        return String.format(VECTOR_FORMAT, tag, xmlFor(POINT_TAG, vector
                .getPoint()), tag);
    }

}
