/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.components.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thomas Robak
 */
public class SimpleTableKeyAdapter extends HashMap<Integer,List<ActionListener>> implements KeyListener{
    public static final String KEY_PRESSED = "KEYPRESSED";

    public void addKeyAction(Integer[] keys,ActionListener action){
        for (Integer key : keys) {
            addKeyAction(key, action);
        }
    }

    public void addKeyAction(Integer key,ActionListener action){
        if(containsKey(key) == false){
            put(key, new LinkedList<ActionListener>());
        }
        get(key).add(action);
    }

    public void fireActionPerformed(Object c,Integer key,String text){
        if(containsKey(key) == false){
            return;
        }

        ActionEvent arg = new ActionEvent(c, key, text);

        for(ActionListener listener : get(key)){
            listener.actionPerformed(arg);
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        fireActionPerformed(arg0.getSource(), arg0.getKeyCode(), KEY_PRESSED);
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }
}
