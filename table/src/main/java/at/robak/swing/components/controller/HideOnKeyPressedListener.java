/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.components.controller;

import at.robak.swing.interfaces.HideRequestListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Thomas Robak
 */
public class HideOnKeyPressedListener implements KeyListener{
    private HideRequestListener c;
    private int key;

    public HideOnKeyPressedListener(HideRequestListener c, int key) {
        this.c = c;
        this.key = key;
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if(arg0.getKeyCode() == key){
            c.requestHide();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

}
