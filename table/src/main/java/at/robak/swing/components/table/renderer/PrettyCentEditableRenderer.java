/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.robak.swing.components.table.renderer;

import at.robak.utils.GUIHelper;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Thomas Robak
 */
public class PrettyCentEditableRenderer extends NumericRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        super.getTableCellRendererComponent(table, value, hasFocus, hasFocus, row, row);
          if(!table.getModel().isCellEditable(row, column)){
            JPanel panel = new JPanel();
            panel.setBackground(SimpleTableCellRenderer.getBackground(table,value,isSelected, row));
            return panel;
        }
        if(value != null){
            setText(GUIHelper.prettyCent(getText()));
        }
        return this;
    }

}
