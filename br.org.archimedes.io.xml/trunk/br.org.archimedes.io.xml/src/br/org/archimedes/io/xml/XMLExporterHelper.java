
package br.org.archimedes.io.xml;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Classe utilitária com métodos para converter elementos básicos para xml como:
 * Point Vector Entra no lugar dos métodos write(...) do projeto antigo para
 * elementos exportáveis que não são sub-classe de Element. Deve escrever o XML
 * conforme especificado em FileXMLSchema.xsd
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
