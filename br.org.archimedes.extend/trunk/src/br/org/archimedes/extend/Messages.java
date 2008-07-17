package br.org.archimedes.extend;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.extend.messages"; //$NON-NLS-1$

    public static String SelectRefs;
    public static String Extended;
    public static String ExtendSelectElements;
    public static String ExtendCancel;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
