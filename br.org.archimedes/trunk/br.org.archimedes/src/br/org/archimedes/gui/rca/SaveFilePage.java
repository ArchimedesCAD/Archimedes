package br.org.archimedes.gui.rca;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import br.org.archimedes.interfaces.FileModel;

public class SaveFilePage extends AbstractFilePage {
	
	public SaveFilePage(String pageName, String[] extensions, FileModel model) {
		super(pageName, extensions, model);
	}

	@Override
	protected FileDialog getDialog(Shell shell) {
		return new FileDialog(shell, SWT.SAVE);
	}

	@Override
	protected boolean canFlipToNextPage(File file) {
        return !file.exists() || (file.isFile() && file.canWrite());
	}

}
