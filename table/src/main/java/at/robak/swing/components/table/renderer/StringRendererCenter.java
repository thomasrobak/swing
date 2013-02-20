/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.robak.swing.components.table.renderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author grabmuell
 */
public class StringRendererCenter extends SimpleTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        super.getTableCellRendererComponent(table, value, hasFocus, hasFocus, row, row);
        if(value != null){
            setHorizontalAlignment(CENTER);
        }
        return this;
    }
}
