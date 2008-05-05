
package br.org.archimedes.gui.rca.editor;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.ui.part.EditorActionBarContributor;

import br.org.archimedes.gui.model.MouseMoveHandler;
import br.org.archimedes.model.Point;

/**
 * @author night
 */
public class Contributor extends EditorActionBarContributor implements Observer {

    private StatusLineContributionItem positionManager;


    /**
     * Default construtor. Make this a mouse move observer.
     */
    public Contributor () {

        MouseMoveHandler.getInstance().addObserver(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToStatusLine(org.eclipse.jface.action.IStatusLineManager)
     */
    @Override
    public void contributeToStatusLine (IStatusLineManager statusLineManager) {

        positionManager = new StatusLineContributionItem(
                "br.org.archimedes.statusbar.position");
        statusLineManager.add(positionManager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update (Observable o, Object point) {

        Point mousePosition = (Point) point;
        String position = String.format("X: %.2f Y:%.2f", new Object[] { //$NON-NLS-1$
                mousePosition.getX(), mousePosition.getY()});
        positionManager.setText(position);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.part.EditorActionBarContributor#dispose()
     */
    @Override
    public void dispose () {
    
        MouseMoveHandler.getInstance().deleteObserver(this);
        super.dispose();
    }
}
