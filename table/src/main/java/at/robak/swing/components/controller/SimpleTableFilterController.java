/*
 *  GIBA DEVELOPMENT
 */
package at.robak.swing.components.controller;

import at.robak.swing.interfaces.IFilteredComponent;
import at.robak.swing.components.table.Format;
import at.robak.swing.components.table.SimpleTableFilterPanel;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Thomas Robak
 */
public class SimpleTableFilterController implements DocumentListener, KeyListener {
    private boolean alwaysVisible = false;
    private SimpleTableFilterPanel filterPanel;
    private JPanel visibilityPanel;

    private List<IFilteredComponent> filterListener = new LinkedList<IFilteredComponent>();

    // <editor-fold defaultstate="collapsed" desc="Events">

    public void addFilterListener(IFilteredComponent listener) {
        alwaysVisible |= listener.isSearchPanelAlwaysVisible();
        getVisibilityPanel().setVisible(filterPanel.isShowing() || alwaysVisible);
        filterListener.add(listener);
        listener.addKeyListener(this);
    }

    public void fireRestFilter(){
        resetColors();
        for (IFilteredComponent listener : filterListener) {
            listener.resetFilter();
        }
    }

    public void fireSetFilter() {
        String text = filterPanel.getFilterText().trim();
        if(text.length() < 1){
            fireRestFilter();
            return;
        }
        highlightColors();
        for (IFilteredComponent listener : filterListener) {
            listener.setFilter(text);
        }
    }

    public boolean getIsHideEvent(KeyEvent evt) {
        for (IFilteredComponent listener : filterListener) {
            if (listener.isSearchPanelHideEvent(evt)) {
                return true;
            }
        }
        return false;
    }

    public boolean getIsShowEvent(KeyEvent evt) {
        for (IFilteredComponent listener : filterListener) {
            if (listener.isSearchPanelShowEvent(evt)) {
                return true;
            }
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Highlighting">
    private void resetColors(){
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(new EtchedBorder());
    }

    private void highlightColors(){
        filterPanel.setBackground(Format.COLOR_TBL_SEARCHMATCH);
        filterPanel.setBorder(new LineBorder(Format.COLOR_BORDER_SEARCHMATCH));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DocumentListener overrides">
    @Override
    public void insertUpdate(DocumentEvent arg0) {
        fireSetFilter();
    }

    @Override
    public void removeUpdate(DocumentEvent arg0) {
        fireSetFilter();
    }

    @Override
    public void changedUpdate(DocumentEvent arg0) {
        fireSetFilter();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="KeyListener overrides">
    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (getIsShowEvent(evt) && filterPanel.isShowing() == false) {
            showFilterPanel();
        } else if (getIsHideEvent(evt) && filterPanel.isShowing() == true) {
            hideFilterPanel();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * @return the filterPanel
     */
    public SimpleTableFilterPanel getFilterPanel() {
        return filterPanel;
    }

    /**
     * @param filterPanel the filterPanel to set
     */
    public void setFilterPanel(SimpleTableFilterPanel filterPanel) {
        this.filterPanel = filterPanel;
        this.filterPanel.getTextSearch().getDocument().addDocumentListener(this);
        this.filterPanel.getTextSearch().addKeyListener(this);
    }

    /**
     * @return the visibilityPanel
     */
    public JPanel getVisibilityPanel() {
        return visibilityPanel;
    }

    /**
     * @param visibilityPanel the visibilityPanel to set
     */
    public void setVisibilityPanel(JPanel visibilityPanel) {
        this.visibilityPanel = visibilityPanel;
        this.visibilityPanel.addComponentListener(new RequestFocusWhenComponentShownListener(filterPanel.getTextSearch()));
    }

    // </editor-fold>

    public void showFilterPanel(){
        getVisibilityPanel().setVisible(true);

    }

    public void hideFilterPanel(){
        getFilterPanel().getTextSearch().setText("");
        getVisibilityPanel().setVisible(false);
        fireRestFilter();
    }


}
