
package br.org.archimedes.leader;

import br.org.archimedes.interfaces.FactoryAction;

public class NewLeaderAction extends FactoryAction {

    private static final String PLUGIN_ID = "br.org.archimedes.leader";


    /**
     * Default constructor. Creates the factory action with this plugin's id.
     */
    public NewLeaderAction () {

        super(PLUGIN_ID);
    }
}
