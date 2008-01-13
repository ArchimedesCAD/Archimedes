/**
 * This file was created on 2007/05/24, 07:50:05, by nitao.
 * It is part of br.org.archimedes.text.edittext on the br.org.archimedes.text.edittext project.
 *
 */

package br.org.archimedes.text.edittext;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.text.edittext.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.text.edittext.messages"; //$NON-NLS-1$

    public static String Factory_Cancel;

    public static String Factory_Edited;

    public static String Factory_InvalidSelection;

    public static String TextEditor_CancelButton;

    public static String TextEditor_OkButton;

    public static String TextEditor_WindowName;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
