/*
 * Created on 26/08/2006
 */

package br.org.archimedes.controller.commands;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;


/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author Nitao
 */
public class PanCommand extends ZoomCommand {

	private Point originalViewport;

	private Point viewport;

	/**
	 * @param originalViewport
	 *            The previous viewport position
	 * @param viewport
	 *            The new viewport position
	 * @throws NullArgumentException
	 *             In case some argument is null
	 * @throws IllegalActionException
	 *             In case the points are the same
	 */
	public PanCommand(Point originalViewport, Point viewport)
			throws NullArgumentException, IllegalActionException {

		if (originalViewport == null || viewport == null) {
			throw new NullArgumentException();
		}
		if (originalViewport.equals(viewport)) {
			throw new IllegalActionException();
		}
		this.originalViewport = originalViewport;
		this.viewport = viewport;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.model.commands.TheZoomCommand#getNewViewport(br.org.archimedes.model.Drawing)
	 */
	@Override
	protected Point getNewViewport(Drawing drawing) {

		return viewport;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.model.commands.TheZoomCommand#calculateZoom(br.org.archimedes.model.Drawing)
	 */
	@Override
	protected double calculateZoom(Drawing drawing) {

		return drawing.getZoom();
	}

	@Override
	protected Point getPreviousViewport() {

		return originalViewport;
	}
}
