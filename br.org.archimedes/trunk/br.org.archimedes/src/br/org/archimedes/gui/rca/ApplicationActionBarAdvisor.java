
package br.org.archimedes.gui.rca;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IWorkbenchAction exitAction;

    private IWorkbenchAction aboutAction;

    private IWorkbenchAction helpAction;

    private IWorkbenchWindow window;


    /**
     * @param configurer
     *            Default constructor.
     */
    public ApplicationActionBarAdvisor (IActionBarConfigurer configurer) {

        super(configurer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.application.ActionBarAdvisor#makeActions(org.eclipse.ui.IWorkbenchWindow)
     */
    protected void makeActions (final IWorkbenchWindow window) {
        this.window = window;
        
        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);

        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);

        helpAction = ActionFactory.HELP_CONTENTS.create(window);
        register(helpAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.application.ActionBarAdvisor#fillMenuBar(org.eclipse.jface.action.IMenuManager)
     */
    protected void fillMenuBar (IMenuManager menuBar) {

        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createCreateMenu());
        menuBar.add(createTransformMenu());
        menuBar.add(createZoomMenu());
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(createHelpMenu());
    }

    /**
     * @return
     */
    private MenuManager createHelpMenu () {

        MenuManager helpMenu = new MenuManager(
                Messages.br_org_archimedes_menu_help,
                IWorkbenchActionConstants.M_HELP);
        helpMenu.add(new GroupMarker("begin")); //$NON-NLS-1$
        helpMenu.add(new Separator());
        helpMenu.add(helpAction);
        helpMenu.add(new Separator());
        helpMenu.add(aboutAction);
        return helpMenu;
    }

    /**
     * @return
     */
    private MenuManager createZoomMenu () {

        MenuManager zoomMenu = new MenuManager(
                Messages.br_org_archimedes_menu_zoom, "zoom"); //$NON-NLS-1$
        createSeparatedSlots(zoomMenu, 10, "absolute"); //$NON-NLS-1$
        zoomMenu.add(new Separator());
        createSeparatedSlots(zoomMenu, 10, "relative"); //$NON-NLS-1$
        zoomMenu.add(new Separator());
        createSeparatedSlots(zoomMenu, 10, "move"); //$NON-NLS-1$
        zoomMenu.add(new Separator());
        createSeparatedSlots(zoomMenu, 10, "complex"); //$NON-NLS-1$
        return zoomMenu;
    }

    /**
     * @return
     */
    private MenuManager createTransformMenu () {

        MenuManager transformMenu = new MenuManager(
                Messages.br_org_archimedes_menu_transform, "transform"); //$NON-NLS-1$
        createSeparatedSlots(transformMenu, 10, "simple"); //$NON-NLS-1$
        transformMenu.add(new Separator());
        createSeparatedSlots(transformMenu, 10, "complex"); //$NON-NLS-1$
        transformMenu.add(new Separator());
        createSeparatedSlots(transformMenu, 10, "composed"); //$NON-NLS-1$
        return transformMenu;
    }

    /**
     * @return
     */
    private MenuManager createCreateMenu () {

        MenuManager createMenu = new MenuManager(
                Messages.br_org_archimedes_menu_create, "create"); //$NON-NLS-1$
        createSeparatedSlots(createMenu, 10, "simple"); //$NON-NLS-1$
        createMenu.add(new Separator());
        createSeparatedSlots(createMenu, 10, "complex"); //$NON-NLS-1$
        createMenu.add(new Separator());
        createSeparatedSlots(createMenu, 10, "structured"); //$NON-NLS-1$
        return createMenu;
    }

    /**
     * @return
     */
    private MenuManager createEditMenu () {

        MenuManager editMenu = new MenuManager(
                Messages.br_org_archimedes_menu_edit,
                IWorkbenchActionConstants.M_EDIT);
        editMenu.add(new GroupMarker("begin")); //$NON-NLS-1$
        editMenu.add(new Separator());
        createSeparatedSlots(editMenu, 10, "slot"); //$NON-NLS-1$
        editMenu.add(new Separator());
        editMenu.add(new GroupMarker("end")); //$NON-NLS-1$
        return editMenu;
    }

    /**
     * @return
     */
    private MenuManager createFileMenu () {

        MenuManager fileMenu = new MenuManager(
                Messages.br_org_archimedes_menu_file,
                IWorkbenchActionConstants.M_FILE);
        fileMenu.add(new GroupMarker("begin")); //$NON-NLS-1$
        fileMenu.add(new Separator());
        createSeparatedSlots(fileMenu, 10, "slot"); //$NON-NLS-1$
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);
        return fileMenu;
    }

    /**
     * @param menu
     *            The menu in which the slots must be created
     * @param slotNumber
     *            The number of slots that must be created
     * @param string
     *            The string to which the numbers [0-9] must be appended
     */
    private void createSeparatedSlots (MenuManager menu, int slotNumber,
            String string) {

        for (int i = 0; i < slotNumber; i++) {
            menu.add(new GroupMarker(string + i)); //$NON-NLS-1$
            if (i < 9) {
                menu.add(new Separator());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.application.ActionBarAdvisor#fillCoolBar(org.eclipse.jface.action.ICoolBarManager)
     */
    protected void fillCoolBar (ICoolBarManager coolBar) {

        IToolBarManager fileBar = new ToolBarManager(coolBar.getStyle());
        IToolBarManager editBar = new ToolBarManager(coolBar.getStyle());
        IToolBarManager createBar = new ToolBarManager(coolBar.getStyle());
        IToolBarManager transformBar = new ToolBarManager(coolBar.getStyle());
        IToolBarManager additionsBar = new ToolBarManager(coolBar.getStyle());
        IToolBarManager zoomBar = new ToolBarManager(coolBar.getStyle());
        IToolBarManager helpBar = new ToolBarManager(coolBar.getStyle());

        coolBar.add(new ToolBarContributionItem(fileBar, "file")); //$NON-NLS-1$
        coolBar.add(new ToolBarContributionItem(editBar, "edit")); //$NON-NLS-1$
        coolBar.add(new LayerComboContributionItem(window)); //$NON-NLS-1$
        coolBar.add(new ToolBarContributionItem(createBar, "create")); //$NON-NLS-1$
        coolBar.add(new ToolBarContributionItem(transformBar, "transform")); //$NON-NLS-1$
        coolBar.add(new ToolBarContributionItem(additionsBar,
                IWorkbenchActionConstants.MB_ADDITIONS));
        coolBar.add(new ToolBarContributionItem(zoomBar, "zoom")); //$NON-NLS-1$
        coolBar.add(new ToolBarContributionItem(helpBar, "help")); //$NON-NLS-1$
    }
}
