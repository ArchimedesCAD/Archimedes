
package br.org.archimedes.text.edittext.tests;

import br.org.archimedes.text.edittext.Activator;
import br.org.archimedes.text.edittext.TextEditor;

public class MockTextEditor extends TextEditor {

    private final String answer;
    private int count = 0;

    public MockTextEditor (String before, String answer) {

        super(Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(), before);
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
