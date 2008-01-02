/*
 * Created on 11/07/2006
 */

package br.org.archimedes.gui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.gui.actions.
 * 
 * @author fernandorb
 */
public class LoadCommand {

	private Shell parent;

	private MessageBox error;

	/**
	 * Constructor
	 * 
	 * @param shell
	 *            DialogBox's parent
	 */
	public LoadCommand(Shell shell) {

		this.parent = shell;

		error = new MessageBox(parent, SWT.OK | SWT.ICON_ERROR);
		error.setMessage(Messages.Load_InvalidFileTitle);
		error.setText(Messages.Load_InvalidFileText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.gui.actions.Command#execute()
	 */
	public Drawing execute() {

		FileDialog dialog = new FileDialog(parent, SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.arc", "*.xml", "*" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		dialog.setText(Messages.Load_OpenDialog);

		Workspace workspace = Workspace.getInstance();
		String lastDirectory = workspace.getLastUsedDirectory()
				.getAbsolutePath();

		Drawing drawing = null;
		String filePath;
		do {
			dialog.setFilterPath(lastDirectory);
			filePath = dialog.open();

			if (filePath != null) {
				File file = new File(filePath);
				lastDirectory = file.getParent();
				if (file.exists() && file.canRead()) {
                    // TODO Load
					if (drawing == null) {
						error.open();
					}
				}

			}
		} while (drawing == null && filePath != null);

		return drawing;
	}
}
