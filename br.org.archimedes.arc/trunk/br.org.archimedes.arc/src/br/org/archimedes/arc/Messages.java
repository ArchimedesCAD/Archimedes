package br.org.archimedes.arc;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "br.org.archimedes.arc.messages"; //$NON-NLS-1$

	public static String ArcCancel;

	public static String ArcCenterPoint;

	public static String ArcCreated;

	public static String ArcDirection;

	public static String ArcEndingPoint;

	public static String ArcInitialPoint;

	public static String ArcInitialPointorCenter;

	public static String ArcNotCreated;

	public static String ArcPassingPoint;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
