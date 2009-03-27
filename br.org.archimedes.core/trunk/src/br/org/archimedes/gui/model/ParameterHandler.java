/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Cristiane M. Sato, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/03/30, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.model;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.gui.model.
 * 
 * @author mari
 * @author cris
 */
public class ParameterHandler implements Observer, SelectionListener {

	private Text inputText;

	/**
	 * Constructor.
	 * 
	 * @param inputText
	 *            The Text widget to be the parameter input.
	 * @param outputText
	 *            The Text widget to be the parameter output.
	 */
	public ParameterHandler(Text inputText, Text outputText) {

		MouseClickHandler.getInstance().addObserver(this);
		this.inputText = inputText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	/**
	 * Update method of the Observer interface.
	 * 
	 * @param mouseHandler
	 *            The MouseClickHandler being observed.
	 * @param point
	 *            The point clicked, if the left button was clicked. Null if the
	 *            right button was clicked.
	 */
	public void update(Observable mouseHandler, Object point) {

		if (point != null) {
			Point p = (Point) point;
			handleParameter(p.getX() + ";" + p.getY()); //$NON-NLS-1$
		} else {
			getParameterFromInput();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent arg0) {

		// Does nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent event) {

		getParameterFromInput();
	}

	/**
	 * Performs the "enter" on the input, in case the enter key was pressed or
	 * the right mouse button was clicked.
	 */
	private void getParameterFromInput() {

		String text = inputText.getText();
		inputText.setText(""); //$NON-NLS-1$
		handleParameter(text);
	}

	/**
	 * Receives a text input and sends it to the interpreter.
	 * 
	 * @param text
	 *            The text to send.
	 */
	public void handleParameter(String text) {

		br.org.archimedes.Utils.getInputController().receiveText(text);
	}
}
