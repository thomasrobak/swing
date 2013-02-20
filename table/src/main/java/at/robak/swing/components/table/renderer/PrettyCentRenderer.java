/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.robak.swing.components.table.renderer;

import at.robak.utils.GUIHelper;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author Thomas Robak
 */
public class PrettyCentRenderer extends NumericRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        super.getTableCellRendererComponent(table, value, hasFocus, hasFocus, row, row);
        if(value != null && !"0".equals(value)){
            setText(GUIHelper.prettyCent(getText()));
        } else {
            setText("");
        }
        return this;
    }

}
