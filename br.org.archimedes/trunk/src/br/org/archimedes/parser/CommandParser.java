/*
 * Created on 23/03/2006
 */

package br.org.archimedes.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import br.org.archimedes.factories.CommandFactory;

/**
 * This class must have all the verifications for all the commands.<br>
 * Belongs to package br.org.archimedes.interpreter.
 * 
 * @author gigante
 */
public class CommandParser {

	private Map<String, CommandFactory> commands;

	/**
	 * Conctructor.
	 */
	public CommandParser() {

		commands = new HashMap<String, CommandFactory>();

		loadElementFactories();
		loadFactories();
	}

	/**
	 * Loads the factories associated with elements
	 */
	private void loadElementFactories() {

		IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
				.getExtensionPoint("br.org.archimedes.element"); //$NON-NLS-1$
		if (extensionPoint != null) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (IExtension extension : extensions) {
				IConfigurationElement[] configElements = extension
						.getConfigurationElements();
				for (IConfigurationElement element : configElements) {
					CommandFactory factory = parseFactory(element,
							"element", "factory"); //$NON-NLS-1$ //$NON-NLS-2$ $NON-NLS-2$
					if (factory != null) {
						addFactory(element, factory);
					}
				}
			}
		}
	}

	/**
	 * Loads the factories defined by a factory extension point
	 */
	private void loadFactories() {

		IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
				.getExtensionPoint("br.org.archimedes.factory"); //$NON-NLS-1$
		if (extensionPoint != null) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (IExtension extension : extensions) {
				IConfigurationElement[] configElements = extension
						.getConfigurationElements();
				for (IConfigurationElement element : configElements) {
					CommandFactory factory = parseFactory(element,
							"factory", "class"); //$NON-NLS-1$ //$NON-NLS-2$ $NON-NLS-2$
					if (factory != null) {
						addFactory(element, factory);
					}
				}
			}
		}
	}

	/**
	 * Creates a factory from a configuration element.
	 * 
	 * @param element
	 *            The configuration element.
	 * @param elementName
	 *            The name of the element.
	 * @param attribute
	 *            The attribute that has the class name.
	 * @return The created factory or null if there was none.
	 */
	private CommandFactory parseFactory(IConfigurationElement element,
			String elementName, String attribute) {

		CommandFactory factory = null;
		if (element.getName().equals(elementName)) {
			try {
				factory = (CommandFactory) element
						.createExecutableExtension(attribute);
			} catch (CoreException e) {
				System.err
						.println("Extension did not defined the correct factory"); //$NON-NLS-1$
				e.printStackTrace();
			}
		}
		return factory;
	}

	/**
	 * Adds a factory to the map using its id, its name and any shortcut
	 * provided.
	 * 
	 * @param element
	 *            The factories' configuration element.
	 * @param factory
	 *            The factory to be added.
	 */
	private void addFactory(IConfigurationElement element,
			CommandFactory factory) {

		// Name is not unique. Factories with repeated names will be lost.
		// When this becomes a problem, we must do a "Shortcut Editing Window"
		// so that this may be a bit more independent of the plugin writer.
		addCommand(element.getAttribute("id"), factory); //$NON-NLS-1$
		String factoryName = factory.getName();
		if (factoryName != null) {
			addCommand(factoryName, factory);
		}

		// We add shortcuts for element factory...
		String shortcutName = element.getAttribute("shortcut"); //$NON-NLS-1$
		if (shortcutName != null) {
			addCommand(shortcutName, factory);
		}

		// ... and for simple factory
		IConfigurationElement[] shortcuts = element.getChildren("shortcut"); //$NON-NLS-1$
		if (shortcuts != null) {
			for (IConfigurationElement shortcut : shortcuts) {
				String name = shortcut.getAttribute("id"); //$NON-NLS-1$
				addCommand(name, factory);
			}
		}
	}

	/**
	 * Adds a command factory to the map of command factories with key as its
	 * command representation.
	 * 
	 * @param key
	 *            The key to access the command factory.
	 * @param commandFactory
	 *            The command factory to be added.
	 */
	private void addCommand(String key, CommandFactory commandFactory) {

		if (commands.get(key.toLowerCase()) == null) {
			commands.put(key.toLowerCase(), commandFactory);
		}
	}

	/**
	 * Verifies if a command exists.
	 * 
	 * @param command
	 *            The command to verify. Returns true if the command exists,
	 *            false otherwise.
	 */
	public boolean existsCommand(String command) {

		return commands.containsKey(command.toLowerCase());
	}

	/**
	 * This method identificates what command factory will be executed.
	 * 
	 * @param commandString
	 *            The identifier of a command factory.
	 * @return The command factory associated to the identifier.
	 */
	public CommandFactory getCommand(String commandString) {

		return commands.get(commandString.toLowerCase());
	}

	public List<String> getCommandList() {

		List<String> factoryNames = new ArrayList<String>();
		for (CommandFactory cmdFact : commands.values()) {
			if (!factoryNames.contains(cmdFact.toString())) {
				factoryNames.add(cmdFact.toString());
			}
		}
		return factoryNames;
	}
}
