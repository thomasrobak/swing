/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.components.table.renderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author Thomas Robak
 */
public class NonVisibleStringRenderer extends SimpleTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        super.getTableCellRendererComponent(table, value, hasFocus, hasFocus, row, row);
        if(value != null){
            //setForeground(Color.BLUE);
             setVisible(false);
        }
        return this;
    }
}
