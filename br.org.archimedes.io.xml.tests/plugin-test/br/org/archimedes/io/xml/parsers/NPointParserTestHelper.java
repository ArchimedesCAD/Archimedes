/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Victor D. Lopes - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2008/06/12, 17:11:05, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml.parsers on the br.org.archimedes.io.xml.tests project.<br>
 */
package br.org.archimedes.io.xml.parsers;

import static junit.framework.Assert.assertNull;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.model.Element;

/**
 * @author vidlopes
 */
public class NPointParserTestHelper {

    protected ElementParser parser;


    protected Node getNodeLine (String xmlSample) throws Exception {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = null;

        docBuilder = docBuilderFactory.newDocumentBuilder();

        StringReader ir = new StringReader(xmlSample);
        InputSource is = new InputSource(ir);

        Document doc = docBuilder.parse(is);
        doc.normalize();

        return doc.getFirstChild();
    }

    /**
     * @param xml_code
     *            The xml to be parsed
     * @throws Exception
     *             Thrown if something unexpected happens
     */
    protected void testFail (final String xml_code) throws Exception {

        Node nodeLine = this.getNodeLine(xml_code);
        Element element = null;
        try {
            element = parser.parse(nodeLine);
        }
        catch (ElementCreationException e) {
            // OK
        }

        assertNull(element);
    }
}
