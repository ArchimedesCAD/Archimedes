package br.org.archimedes.gui.rca.exporter;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class DrawingExporterTestPage extends WizardPage {


	private String nome;

	protected DrawingExporterTestPage(String pageName) {
		super(pageName);
		this.nome = pageName;
	}

	public void createControl(Composite parent) {
        Font font = parent.getFont();
        
        // create composite for page.
        Composite outerContainer = new Composite(parent, SWT.NONE);
        outerContainer.setLayout(new GridLayout());
        outerContainer.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        outerContainer.setFont(font);

        Label messageLabel = new Label(outerContainer, SWT.NONE);
        messageLabel.setText(nome);
        messageLabel.setFont(font);

        setControl(outerContainer);		
	}


}
