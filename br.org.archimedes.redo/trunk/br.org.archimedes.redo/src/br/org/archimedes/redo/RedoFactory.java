/*
 * Created on 08/05/2006
 */

package br.org.archimedes.redo;

import br.org.archimedes.undo.UndoFactory;

public class RedoFactory extends UndoFactory {

    public String begin () {

        setCommand(new RedoCommand());
        return Messages.RedoPerformed;
    }

    public String getName () {

        return "redo"; //$NON-NLS-1$
    }
}
