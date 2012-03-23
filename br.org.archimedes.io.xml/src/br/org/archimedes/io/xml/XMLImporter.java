/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/02/01, 23:19:00, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.interfaces.Importer;
import br.org.archimedes.io.xml.parsers.XMLParser;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.io.xml.
 * 
 * @author night
 */
public class XMLImporter implements Importer {

    private XMLParser parser;


    /**
     * Empty constructor. Tries to load "FileXMLSchema.xsd" as the validation
     * schema.
     */
    public XMLImporter () {

        Validator validator = null;
        try {
            InputStream validatorInput = Activator
                    .locateFile(Messages.XMLImporter_SchemaFileName);
            validator = buildValidator(validatorInput);
        }
        catch (IOException e) {
            // Could load the validator
            e.printStackTrace();
        }
        setXMLParser(validator);
    }

    /**
     * @param inputStream
     *            The input stream to load the schema file.
     */
    public XMLImporter (InputStream inputStream) {

        setXMLParser(buildValidator(inputStream));
    }

    /**
     * @param validator
     *            The validator to use on the parser
     */
    private void setXMLParser (Validator validator) {

        this.parser = new XMLParser(validator);
    }

    /**
     * @param inputStream
     *            The input stream from which the schema should be read
     * @return A validator for the XML files or null if none could be loaded
     */
    private Validator buildValidator (InputStream inputStream) {

        Schema schema = obtainSchema(inputStream);
        if(schema == null) {
            return null;
        }
        return schema.newValidator();
    }

    /**
     * @param inputStream
     *            The input stream to load the schema file.
     * @return A schema related to this input stream or null if the inputStream
     *         was null or there was a parse error
     */
    private static Schema obtainSchema (InputStream inputStream) {

        if (inputStream == null) {
            return null;
        }

        SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Source schemaFile = new StreamSource(inputStream);
        try {
            return factory.newSchema(schemaFile);
        }
        catch (SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.Importer#importDrawing(java.io.InputStream)
     */
    public Drawing importDrawing (InputStream input)
            throws InvalidFileFormatException, IOException {

        return parser.parse(input);
    }
}
