package br.org.archimedes.gui.rca;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		
		layout.addStandaloneView(InterpreterView.ID,  false, IPageLayout.BOTTOM, 0.85f, editorArea);
		layout.getViewLayout(InterpreterView.ID).setCloseable(false);
	}
}
