/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.components.controller;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 *
 * @author Thomas Robak
 */
public class RequestFocusWhenComponentShownListener implements ComponentListener{
    private Component c;

    public RequestFocusWhenComponentShownListener(Component c) {
        this.c = c;
    }

    @Override
    public void componentResized(ComponentEvent arg0) {
    }

    @Override
    public void componentMoved(ComponentEvent arg0) {
    }

    @Override
    public void componentShown(ComponentEvent arg0) {
        c.requestFocus();
    }

    @Override
    public void componentHidden(ComponentEvent arg0) {
    }
}
