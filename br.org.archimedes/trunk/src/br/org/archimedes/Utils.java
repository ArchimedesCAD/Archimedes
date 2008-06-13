/**
 * This file was created on 03/04/2006, 10:44:46, by cmsato. It is part of
 * br.org.archimedes on the archimedes project.
 */

package br.org.archimedes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectorFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.TrimManager;
import br.org.archimedes.intersections.NullIntersectionManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.trims.NullTrimManager;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author cmsato
 * @author andy
 */
public class Utils {

	private static final NullIntersectionManager NULL_INTERSECTION_MANAGER = new NullIntersectionManager();

	private static final NullTrimManager NULL_TRIM_MANAGER = new NullTrimManager();

	private final static Map<Class<? extends Element>, String> elementToIdMap;

	private final static Map<String, Class<? extends Element>> idToElementClassMap;

	private final static Map<Class<? extends Element>, Class<? extends SelectorFactory>> elementToDoubleClickHandler;

	private static IntersectionManager intersectorManager;

	private static TrimManager trimManager;

	static {
		elementToIdMap = new HashMap<Class<? extends Element>, String>();
		idToElementClassMap = new HashMap<String, Class<? extends Element>>();
		elementToDoubleClickHandler = new HashMap<Class<? extends Element>, Class<? extends SelectorFactory>>();

		fillExtensions();
	}

	private static void fillExtensions() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		if (registry != null) {
			fillElementVersusClassMaps(registry);
			fillDoubleClickHandlers(registry);

			intersectorManager = getIntersectionManager(registry);
			trimManager = getTrimManager(registry);
		}
	}

	/**
	 * @param registry
	 * @return The loaded trimManager or an instance of the NullTrimManager if
	 * 	none was loaded
	 */
	private static TrimManager getTrimManager(IExtensionRegistry registry) {
		IExtensionPoint extensionPoint = registry
				.getExtensionPoint("br.org.archimedes.trims"); //$NON-NLS-1$
		if (extensionPoint != null) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (IExtension extension : extensions) {
				IConfigurationElement[] configElements = extension
						.getConfigurationElements();
				for (IConfigurationElement elementTag : configElements) {
					try {
						TrimManager manager = (TrimManager) elementTag
								.createExecutableExtension("class");
						if (manager != NULL_TRIM_MANAGER) {
							return manager;
						}
					} catch (CoreException e) {
						// Then it cannot be loaded and something went really
						// wrong. Just printing for log reasons.
						e.printStackTrace();
					}
				}
			}
		}

		return NULL_TRIM_MANAGER;
	}

	/**
	 * @param registry
	 * @return The loaded intersectionManager or an instance of the
	 * 	NullIntersectionManager if none was loaded
	 */
	private static IntersectionManager getIntersectionManager(
			IExtensionRegistry registry) {

		IExtensionPoint extensionPoint = registry
				.getExtensionPoint("br.org.archimedes.intersections"); //$NON-NLS-1$
		if (extensionPoint != null) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (IExtension extension : extensions) {
				IConfigurationElement[] configElements = extension
						.getConfigurationElements();
				for (IConfigurationElement elementTag : configElements) {
					try {
						IntersectionManager manager = (IntersectionManager) elementTag
								.createExecutableExtension("class");
						if (manager != NULL_INTERSECTION_MANAGER) {
							return manager;
						}
					} catch (CoreException e) {
						// Then it cannot be loaded and something went really
						// wrong. Just printing for log reasons.
						e.printStackTrace();
					}
				}
			}
		}

		return NULL_INTERSECTION_MANAGER;
	}

	/**
	 * Finds a Factory that registered as a double click handler for elements of
	 * this type.
	 * 
	 * @param element
	 * 		Element
	 */
	public static SelectorFactory getDoubleClickFactoryFor(Element element) {

		Class<? extends SelectorFactory> factoryClass = elementToDoubleClickHandler
				.get(element.getClass());

		if (factoryClass != null) {
			try {
				SelectorFactory factoryInstance = factoryClass.newInstance();
				return factoryInstance;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static void fillDoubleClickHandlers(IExtensionRegistry registry) {

		IExtensionPoint extensionPoint = registry
				.getExtensionPoint("br.org.archimedes.factory"); //$NON-NLS-1$
		if (extensionPoint != null) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (IExtension extension : extensions) {
				IConfigurationElement[] configElements = extension
						.getConfigurationElements();
				for (IConfigurationElement factoryTag : configElements) {
					fillDoubleClickHandler(factoryTag);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void fillDoubleClickHandler(IConfigurationElement factoryTag) {

		String handlesDoubleClicks = factoryTag
				.getAttribute("handlesDoubleClicks");
		if (handlesDoubleClicks != null) {
			if (Boolean.valueOf(handlesDoubleClicks).booleanValue() == true) {

				String handledElementId = factoryTag
						.getAttribute("handledElementId");
				String factoryClass = factoryTag.getAttribute("class");

				if (handledElementId != null && factoryClass != null) {
					try {
						Class<? extends Element> target = getElementClass(handledElementId);
						Class<? extends SelectorFactory> factory = (Class<? extends SelectorFactory>) Class
								.forName(factoryClass);
						if (!SelectorFactory.class.isAssignableFrom(factory)) {
							throw new ClassCastException();
						}
						elementToDoubleClickHandler.put(target, factory);
					} catch (ClassNotFoundException e) {
						System.out.println("ClassNotFoundException: " + e);
					} catch (ClassCastException e) {
						// isso so vai acontecer se uma factory
						// se registrar para lidar com doubleclick mas
						// nao implementar a interface DoubleClickHandlerFactory
						System.out.println("ClassCastException: " + e);
					}

				}

			}
		}
	}

	private static void fillElementVersusClassMaps(IExtensionRegistry registry) {

		IExtensionPoint extensionPoint = registry
				.getExtensionPoint("br.org.archimedes.element"); //$NON-NLS-1$
		if (extensionPoint != null) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (IExtension extension : extensions) {
				IConfigurationElement[] configElements = extension
						.getConfigurationElements();
				for (IConfigurationElement elementTag : configElements) {
					fillMaps(elementTag);
				}
			}
		}

	}

	/**
	 * @param element
	 * 		The element from which we want the extension's id
	 * @return The element extension id string
	 */
	public static String getElementId(Element element) {

		return elementToIdMap.get(element);
	}

	/**
	 * @param elementId
	 * 		The element id from which we desire the class
	 * @return The class corresponding to that element or null if it was not
	 * 	found
	 */
	public static Class<? extends Element> getElementClass(String elementId) {

		return idToElementClassMap.get(elementId);
	}

	/**
	 * @return The loaded trim manager or NullTrimManager if none was loaded
	 */
	public static TrimManager getTrimManager() {
		return trimManager;
	}
	
	/**
	 * @return The loaded intersection manager or NullIntersectionManager if
	 * 	none was loaded
	 */
	public static IntersectionManager getIntersectionManager() {

		return intersectorManager;
	}

	/**
	 * Fills the maps with the infos found on this element tag. If there is an
	 * error, just does nothing.
	 * 
	 * @param elementTag
	 * 		The tag that contains infos
	 */
	@SuppressWarnings("unchecked")
	private static void fillMaps(IConfigurationElement elementTag) {

		String elementId = elementTag.getAttribute("id"); //$NON-NLS-1$
		String elementClass = elementTag.getAttribute("class"); //$NON-NLS-1$
		try {
			Class<? extends Element> eClass = (Class<? extends Element>) Class
					.forName(elementClass);
			elementToIdMap.put(eClass, elementId);
			idToElementClassMap.put(elementId, eClass);
		} catch (ClassNotFoundException e) {
			// If this happens, ignores the element and move on.
			// Printing the stack trace might help developers to
			// create their elements correctly.
			e.printStackTrace();
		}
	}

	/**
	 * @param text
	 * 		The text to verify.
	 * @return Returns true if the text is in the format of a point, false
	 * 	otherwise.
	 */
	public static boolean isPoint(String text) {

		boolean matches = text != null;
		if (matches) {
			String[] parts = text.split(";"); //$NON-NLS-1$
			matches = parts.length == 2 && isDouble(parts[0])
					&& isDouble(parts[1]);
		}
		return matches;
	}

	/**
	 * @param text
	 * 		The text to verify.
	 * @return Returns true if text is in the format of a double, false
	 * 	otherwise.
	 */
	public static boolean isDouble(String text) {

		return (text != null && !isReturn(text) && text
				.matches("\\s*[+-]?[0-9]*([.,][0-9]*)?\\s*")); //$NON-NLS-1$
	}

	/**
	 * Replaces commas by dots in the decimal representation.
	 * 
	 * @param stringWithComma
	 * 		The string to modify.
	 * @return Returns the new string.
	 */
	private static String withDot(String stringWithComma) {

		String returnValue;
		if (stringWithComma == null) {
			returnValue = null;
		} else {
			returnValue = stringWithComma.replace(',', '.');
		}
		return returnValue;
	}

	/**
	 * Parses the string into a point.
	 * 
	 * @param parameter
	 * 		The coordinates of the point.
	 * @return The equivalent Point or null if the parameter is not a point
	 * 	description.
	 */
	public static Point getPointCoordinates(String parameter) {

		Point point = null;
		if (parameter != null) {
			String[] doubles = parameter.split(";", 2); //$NON-NLS-1$

			if (doubles.length == 2) {
				double x = getDouble(doubles[0]);
				double y = getDouble(doubles[1]);
				point = new Point(x, y);
			}
		}
		return point;
	}

	/**
	 * @param parameter
	 * 		the text to verify
	 * @return Returns true if the text is in the format of a '\n', false
	 * 	otherwise.
	 */
	public static boolean isReturn(String text) {

		return (text != null && text.trim().matches("")); //$NON-NLS-1$
	}

	/**
	 * Get the double value from a string.
	 * 
	 * @param parameter
	 * 		Text to extract the double.
	 * @return The double value from the string.
	 */
	public static double getDouble(String parameter) {

		String withDots = withDot(parameter);
		Scanner stringScanner = new Scanner(withDots);
		stringScanner.useLocale(Locale.US);

		return stringScanner.nextDouble();
	}

	/**
	 * Verifies that the x values is in the interval [begin, end].
	 * 
	 * @param x
	 * 		the value to verify
	 * @param begin
	 * 		the begining of the interval
	 * @param end
	 * 		the end of the interval
	 * @return true if x is in the interval, false otherwise.
	 */
	public static boolean isBetween(double x, double begin, double end) {

		return (x >= (begin - Constant.EPSILON))
				&& (x <= (end + Constant.EPSILON));
	}

	/**
	 * This method receives a point and a delta then it returns a square with
	 * center in point and width = 2 * delta.
	 * 
	 * @param point
	 * 		The center of the rectangle
	 * @param delta
	 * 		Half the desired squarte width
	 * @return The square (in a Rectangle class)
	 */
	public static Rectangle getSquareFromDelta(Point point, double delta) {

		Rectangle rect = new Rectangle(point.getX() - delta, point.getY()
				+ delta, point.getX() + delta, point.getY() - delta);
		return rect;
	}

	/**
	 * @see br.org.archimedes.model.Point#compareTo(java.lang.Object)
	 * @param points
	 * 		The collection of points
	 * @return An array with the points of the collection sorted
	 */
	public static Point[] getSortedArrayOfPoints(Collection<Point> points) {

		Point[] returnValue = new Point[points.size()];
		int i = 0;

		// uses insertion sort
		for (Point point : points) {
			if (i == 0) {
				returnValue[0] = point;
			} else {
				int j = i - 1;
				Point auxPoint = returnValue[j];
				while (point.compareTo(auxPoint) == -1 && j >= 0) {
					returnValue[j + 1] = returnValue[j];
					j--;
					if (j >= 0) {
						auxPoint = returnValue[j];
					}
				}
				returnValue[j + 1] = point;
			}
			i++;
		}

		return returnValue;
	}

	/**
	 * @param o
	 * 		The object
	 * @param name
	 * 		The interface
	 * @return True if the passed object implements the passed interface
	 */
	@SuppressWarnings("unchecked")
	public static boolean isInterfaceOf(Object o, Class name) {

		boolean isInterface = (o.getClass() == name);
		for (Class interfaceName : o.getClass().getInterfaces()) {
			if (isInterface) {
				break;
			}
			isInterface = (interfaceName.equals(name));
		}
		return isInterface;
	}

	/**
	 * Tells us if the object o is a subclass of clazz.
	 * 
	 * @param o
	 * 		Object
	 * @param clazz
	 * 		Class
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean isSubclassOf(Object o, Class clazz) {

		return clazz.isAssignableFrom(o.getClass());
	}

	/**
	 * @param p1
	 * 		The first point (that should be used as a reference to ortogonalize)
	 * @param point
	 * 		The point that defines the vector
	 * @return If orto is on, the point that makes an ortogonal vector, the same
	 * 	point otherwise.
	 * @throws NullArgumentException
	 * 		Thrown if any argument is null.
	 */
	public static Point useOrto(Point p1, Point point)
			throws NullArgumentException {

		Workspace workspace = Workspace.getInstance();
		if (workspace.isOrtoOn() && workspace.getGripMousePosition() == null) {
			point = Geometrics.orthogonalize(p1, point);
		}
		return point;
	}

	/**
	 * Devolve o valor m�nimo encontrado no array. Poderia usar
	 * Collections.min(...) mas � O(n) de qq forma.
	 * 
	 * @param values
	 * 		double
	 * @return double
	 */
	public static double min(double... values) {

		if (values.length > 0) {
			double min = values[0];
			for (int i = 1; i < values.length; i++) {
				if (values[i] < min)
					min = values[i];
			}
			return min;
		}
		return Double.NaN;
	}

	/**
	 * Devolve o valor m�ximo encontrado no array. Poderia usar
	 * Collections.max(...) mas � O(n) de qq forma.
	 * 
	 * @param values
	 * 		double
	 * @return double
	 */
	public static double max(double... values) {

		if (values.length > 0) {
			double max = values[0];
			for (int i = 1; i < values.length; i++) {
				if (values[i] > max)
					max = values[i];
			}
			return max;
		}
		return Double.NaN;
	}
}
