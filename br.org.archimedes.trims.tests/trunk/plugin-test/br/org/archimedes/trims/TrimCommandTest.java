/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Wesley Seidel, Bruno da Hora - initial API and implementation<br>
 * <br>
 * This file was created on 28/05/2009, 08:24:08.<br>
 * It is part of br.org.archimedes.trims on the br.org.archimedes.trims.tests project.<br>
 */

package br.org.archimedes.trims;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * @author Wesley Seidel, Bruno da Hora
 * 
 */
public class TrimCommandTest {

	private TrimCommand trimCommand;

	private Collection<Element> references;

	Drawing drawing;

	@Before
	public void setUp() throws Exception {
		InfiniteLine line1 = new InfiniteLine(0.0, 0.0, 0.0, 100.0);
		InfiniteLine line2 = new InfiniteLine(50.0, 0.0, 50.0, 100.0);
		references = new ArrayList<Element>();
		references.add(line1);
		references.add(line2);
		trimCommand = new TrimCommand(references, Collections
				.singletonList(new Point(-2.0, 2.0)));
	}

	@Test(expected = NullArgumentException.class)
	public void doItWithDrawingNullThrowsNullArgumentException()
			throws Exception {
		trimCommand.doIt(null);
	}

	@Test(expected = NullArgumentException.class)
	public void undoItWithDrawingNullThrowsNullArgumentException()
			throws Exception {
		trimCommand.undoIt(null);
	}

	@Test(expected = IllegalActionException.class)
	public void throwsIllegalActionExceptionIfClicksIsNull() throws Exception {
		drawing = new Drawing("Testes");

		trimCommand = new TrimCommand(references, Collections
				.singletonList(new Point(-2.0, 2.0))){

			@Override
			protected void computeTrim(Drawing drawing, Point click)
					throws IllegalActionException,
					NullArgumentException {
			}
		};

		trimCommand.doIt(drawing);
	}
	

}
