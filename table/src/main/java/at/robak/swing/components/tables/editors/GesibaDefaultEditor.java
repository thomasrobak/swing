/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.robak.swing.components.tables.editors;

import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Thomas Robak
 */
public class GesibaDefaultEditor extends DefaultCellEditor{

    public GesibaDefaultEditor(JCheckBox checkbox) {
        super(checkbox);
    }

    public GesibaDefaultEditor(JTextField textField) {
        super(textField);
    }

    public GesibaDefaultEditor(JComboBox arg0) {
        super(arg0);
    }

    @Override
    public boolean shouldSelectCell(EventObject arg0) {
        return true;
    }
}
