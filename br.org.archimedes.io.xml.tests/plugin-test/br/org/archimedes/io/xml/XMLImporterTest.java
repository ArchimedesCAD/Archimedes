/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Marcos P. Moreti - later contributions<br>
 * <br>
 * This file was created on 2007/06/14, 08:50:09, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml on the br.org.archimedes.io.xml.tests project.<br>
 */

package br.org.archimedes.io.xml;

import br.org.archimedes.Constant;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.text.Text;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

/**
 * Belongs to package br.org.archimedes.io.xml.
 * 
 * @author nitao
 */
public class XMLImporterTest {

    /**
     * Test method for
     * {@link br.org.archimedes.io.xml.XMLImporter#importDrawing(java.io.InputStream)}.
     * 
     * @throws IOException
     *             Thrown in case something goes wrong reading the file
     * @throws InvalidFileFormatException
     *             Thrown if the file is invalid
     * @throws InvalidArgumentException
     *             Thrown if arguments are invalid
     * @throws IllegalActionException
     *             Thrown if trying to do something illegal
     * @throws NullArgumentException
     *             Thrown if something is null
     */
    @Test
    public void testImportDrawing () throws InvalidFileFormatException, IOException,
            NullArgumentException, IllegalActionException, InvalidArgumentException {

        String fileName = "../br.org.archimedes.io.xml.tests/ExampleFile.xml";

        InputStream input = readFile(fileName);
        XMLImporter importer = new XMLImporter();
        Drawing drawing = importer.importDrawing(input);
        drawing.setFile(new File(fileName));

        Map<String, Layer> layers = new HashMap<String, Layer>(5);
        Layer layer = new Layer(new Color(247, 173, 40), "MeuPrimeiroLayer", LineStyle.values()[1],
                1.05);
        layer.putElement(new Circle(new Point( -191, 155), 93.193));
        layer.putElement(new Line(new Point( -64, 198), new Point( -173, 88)));
        layer.putElement(new InfiniteLine(new Point( -328, 8), new Point( -315, -8)));
        layers.put(layer.getName(), layer);

        layer = new Layer(new Color(102, 198, 193), "UmSegundoLayer", LineStyle.values()[1], 2);
        layers.put(layer.getName(), layer);
        layer.putElement(new Arc(new Point( -278, 43), new Point(3, 199), new Point( -72, 236)));

        layer = new Layer(new Color(207, 42, 148), "LayerDoAnderson", LineStyle.values()[0], 2.4);
        layer.setPrintColor(new Color(255, 0, 233));
        layers.put(layer.getName(), layer);

        List<Point> points = new ArrayList<Point>();
        points.add(new Point( -174, 210));
        points.add(new Point( -50, 159));
        points.add(new Point( -174, 64));
        points.add(new Point(4, 62));
        points.add(new Point( -170, 8));
        layer.putElement(new Polyline(points));

        layer = new Layer(Constant.WHITE, "Layer 3", LineStyle.values()[1], 1);
        layers.put(layer.getName(), layer);
        layer.putElement(new Arc(new Point(143, 146), new Point(41, -135), new Point(268, -44)));
        layer.putElement(new Line(new Point(164, 8), new Point( -83, 127)));

        layer = new Layer(new Color(235, 24, 24), "LayerdoFabricio", LineStyle.values()[0], 1);
        layer.setPrintColor(Constant.BLACK);
        layers.put(layer.getName(), layer);
        layer.putElement(new Text("Archimedes", new Point(143.403, 209.828), 63.828));
        layer.putElement(new InfiniteLine(new Point(268, -44), new Point(267.002, -44.063)));
        layer.putElement(new Line(new Point( -1083.033, 453.48), new Point(957.756, -355.968)));

        Drawing expected = new Drawing("ExampleFile", layers);
        expected.setFile(new File(fileName));

        Assert.assertEquals(expected, drawing);
    }

    /**
     * @param fileName The file name to load
     * @return An input stream to the start of the file or null if no file could be found
     * @throws FileNotFoundException Thrown if there was a problem loading the file
     */
    private InputStream readFile (String fileName) throws FileNotFoundException {

        InputStream input;
        if (TestActivator.getDefault() == null) { // Non plugin test
            input = new FileInputStream(fileName);
        } else {
            input = TestActivator.locateFile(fileName);
        }
        return input;
    }
}
