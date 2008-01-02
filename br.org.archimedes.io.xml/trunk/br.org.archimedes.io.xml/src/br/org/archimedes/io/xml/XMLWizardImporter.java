package br.org.archimedes.io.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.gui.rca.LoadFilePage;
import br.org.archimedes.interfaces.DrawingImporter;
import br.org.archimedes.interfaces.FileModel;
import br.org.archimedes.interfaces.FileModelImpl;
import br.org.archimedes.model.Drawing;

public class XMLWizardImporter extends Wizard implements IExportWizard, DrawingImporter {

	private FileModel fileModel = new FileModelImpl();
	
	
	@Override
	public boolean performFinish() {
		XMLImporter importer = new XMLImporter();
		File file = new File(fileModel.getFilePath());
		if(file.exists() && file.isFile() && file.canRead()) {
			try {
				Drawing drawing = importer.importDrawing(new FileInputStream(file));
				System.out.println(drawing);
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (InvalidFileFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return false;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	public String getName() {
		return "Importação do XML do Archimedes";
	}

	@Override
	public void addPages() {
		super.addPages();
		this.addPage(new LoadFilePage("Load file", new String[] {"*.arc", "*.xml"}, fileModel));
	}
	
	
	
}
