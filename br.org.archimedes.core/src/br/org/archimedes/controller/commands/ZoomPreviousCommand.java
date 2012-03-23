/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * César Seragiotto - initial API and implementation<br>
 * Victor D. Lopes, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/21, 13:18:00, by César Seragiotto.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.controller.commands;

import java.util.Stack;

import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;

public class ZoomPreviousCommand extends ZoomCommand {

	private ZoomCommand zoomcmd;

	@Override
	protected double calculateZoom(Drawing drawing) {
		if (zoomcmd == null)
			zoomcmd = findPreviousZoom(drawing);
	
		if (zoomcmd != null)
			return zoomcmd.getPreviousZoom();
		
		return 1.0;
	}

	private ZoomCommand findPreviousZoom(Drawing drawing) {
		boolean isDone = false;
				
		Stack<UndoableCommand> undoHistory = drawing.getUndoHistory();
		Stack<UndoableCommand> tempStack = new Stack<UndoableCommand>();
		
		ZoomCommand zc = null;
		
		if (undoHistory.size() > 0) {
			while (undoHistory.size() > 0 && !isDone) {
				UndoableCommand command = undoHistory.pop();
				if (command instanceof ZoomCommand) {
					zc = (ZoomCommand) command;
					while (undoHistory.size() > 0 && !isDone) {
						UndoableCommand commandAux = undoHistory.pop();
						if (commandAux instanceof ZoomCommand)
							zc = (ZoomCommand) commandAux;
						else {
							isDone = true;
							undoHistory.push(commandAux);
						}
					}
				} else {
					tempStack.push(command);
				}
			}
			for (UndoableCommand command : tempStack) {
				undoHistory.push(command);
			}
		}
		return zc;
	}

	@Override
	protected Point getNewViewport(Drawing drawing) {
		if (zoomcmd == null)
			zoomcmd = findPreviousZoom(drawing);
	
		if (zoomcmd != null) {
			return zoomcmd.getPreviousViewport();
		}
		
		return null;
	}

	/*@Override
	protected Point getPreviousViewport(Drawing drawing) {
		if (zoomcmd == null)
			zoomcmd = findPreviousZoom(drawing);
	
		if (zoomcmd != null) {
			previousViewPort = zoomcmd.getPreviousViewport(drawing);
		}
		
		return previousViewPort;
		return drawing.getViewportPosition();
	}*/

	
	
}
