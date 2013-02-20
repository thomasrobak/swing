/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.robak.swing.components.table.renderer;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author schober
 */
public class NonVisibleCheckboxRenderer extends CheckRenderer{

        @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(!table.getModel().isCellEditable(row, column)){
            JPanel panel = new JPanel();
            panel.setBackground(SimpleTableCellRenderer.getBackground(table,value,isSelected, row));
            return panel;
        }
        return this;
    }

}
