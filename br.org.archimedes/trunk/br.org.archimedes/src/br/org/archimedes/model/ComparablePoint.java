/*
 * Created on 05/06/2006
 *
 */


package br.org.archimedes.model;

import br.org.archimedes.exceptions.NullArgumentException;



/**
 * Belongs to package br.org.archimedes.model.
 *
 */
public class ComparablePoint implements Comparable<ComparablePoint> {

    private Point point;
    
    private Key key;
        
    public ComparablePoint(Point point, Key key) throws NullArgumentException{
        if (point == null) {
            throw new NullArgumentException();
        }
        this.point = point;
        this.key = key;
    }

    /**
     * @param point The comparable point to be compared with this point
     * @return -1, 0 or 1 if the point key is less than, equal or greater than this point key 
     */
    public int compareTo(ComparablePoint point) {

        return this.key.compareTo(point.getKey());
    }
    
    /**
     * @return The key associated to this comparable point
     */
    public Key getKey() {
        return key;
    }
    
    /**
     * @return The point associated to this comparable point
     */
    public Point getPoint() {
        return point;
    }
    
}
