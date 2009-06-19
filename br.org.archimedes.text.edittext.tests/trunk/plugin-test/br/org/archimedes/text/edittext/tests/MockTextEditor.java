
package br.org.archimedes.text.edittext.tests;

import br.org.archimedes.text.edittext.TextEditor;

import org.eclipse.ui.PlatformUI;

public class MockTextEditor extends TextEditor {

    private final String answer;
    private int count = 0;

    public MockTextEditor (String before, String answer) {

        super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), before);
        this.answer = answer;
    }

    @Override
    public String open () {
        count++;
        return answer;
    }    
    
    public int countOpenCalls () {
        return count;
    }
}
