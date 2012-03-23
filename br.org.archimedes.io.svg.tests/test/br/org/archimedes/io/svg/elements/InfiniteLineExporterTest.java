/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Bruno da Hora and Bruno Klava - initial API and implementation<br>
 * <br>
 * This file was created on 31/03/2009, 17:51:56.<br>
 * It is part of br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg.tests project.<br>
 */

package br.org.archimedes.io.svg.elements;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.infiniteline.InfiniteLine;

/**
 * @author Bruno da Hora and Bruno Klava
 */
public class InfiniteLineExporterTest extends Tester {

	private InfiniteLineExporter exporter;

	private ByteArrayOutputStream stream;

	private InfiniteLine infiniteline;

	@Before
	public void setUp() throws Exception {

		exporter = new InfiniteLineExporter();
		stream = new ByteArrayOutputStream();
		infiniteline = new InfiniteLine(0.0, -1.0, 1.0, 0.0);

	}

	@Test(expected = NotSupportedException.class)
	public void exportInfiniteLineOnlyWithoutBoundary() throws Exception {
		exporter.exportElement(infiniteline, stream);
	}

	@Test
	public void exportInfiniteLineOnly() throws Exception {

		exporter.exportElement(infiniteline, stream, null);
		String expected = "<line x1=\"0\" y1=\"1\" x2=\"1\" y2=\"0\" />\n"
				.replaceAll("\\s", "");
		String result = stream.toString().replaceAll("\\s", "");
		assertEquals(expected, result);
	}

}
