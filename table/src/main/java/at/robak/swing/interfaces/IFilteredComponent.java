/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.interfaces;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Thomas Robak
 */
public interface IFilteredComponent {
    public void setFilter(String filterText);
    public void resetFilter();
    public boolean isSearchPanelHideEvent(KeyEvent evt);
    public boolean isSearchPanelShowEvent(KeyEvent evt);
    public boolean isSearchPanelAlwaysVisible();
    public void addKeyListener(KeyListener listener);
}
