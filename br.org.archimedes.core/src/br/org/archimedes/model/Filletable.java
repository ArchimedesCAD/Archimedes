package br.org.archimedes.model;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;

public interface Filletable {
	
	public Collection<UndoableCommand> getFilletCommands(Point arcCenter, Point arcIntersectionWithThisElement, Point arcIntersectionWithThatElement, Point force)  throws NullArgumentException;
	public Point getTangencyLinePoint(Point intersection, Point click);
	

}
