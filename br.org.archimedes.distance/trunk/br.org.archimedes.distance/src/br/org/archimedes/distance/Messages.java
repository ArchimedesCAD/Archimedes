/**
 * 
 */
package br.org.archimedes.distance;

import org.eclipse.osgi.util.NLS;

/**
 * @author pafhuaman
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "br.org.archimedes.distance.messages"; //$NON-NLS-1$

	public static String distanceError;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
