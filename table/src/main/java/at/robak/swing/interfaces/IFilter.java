/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.interfaces;

/**
 *
 * @author Thomas Robak
 */
public interface IFilter {
    public boolean include(Object object);
    public boolean include(Object[] lines);
}
