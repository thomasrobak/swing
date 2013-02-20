/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.robak.swing.components.controller;

import at.robak.utils.SpringUtilities;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author Thomas Robak
 */
public class GenericEditController {


    private JPanel panel;

    public JPanel getPanel() {
        if (panel == null) {
            panel = new JPanel(new SpringLayout());
        }
        return panel;
    }

    public HashMap<String, JComponent> edit(Object o) {
        HashMap<String, JComponent> pairs = new HashMap<String, JComponent>();
        getPanel().removeAll();
        Method[] methods = o.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get")) {
                String name = m.getName().substring(3);
                String setter = "set"+name;

                boolean setterExists = setterExists(methods,setter);
                JTextField field = createField(o, m, setterExists);
                if(field == null){
                    continue;
                }

                if(setterExists){
                    pairs.put(setter, field);
                }

                panel.add(new JLabel(name));
                panel.add(field);

            }
        }
        SpringUtilities.makeCompactGrid(panel, pairs.size()/2, 4, 5, 5, 5, 5);
        return pairs;
    }

    private JTextField createField(Object o,Method m,boolean editable){
        try {
            JTextField field = new JTextField();
            field.setEditable(editable);
            Object result = m.invoke(o);
            field.setText(result == null ? "null" : result.toString());
            field.setPreferredSize(new Dimension(0, 25));
            return field;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GenericEditController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GenericEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private boolean setterExists(Method[] methods, String setter) {
        for(Method m : methods){
            if(setter.equals(m.getName())){
                return true;
            }
        }
        return false;
    }
}
