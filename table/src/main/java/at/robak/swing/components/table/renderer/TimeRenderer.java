/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.robak.swing.components.table.renderer;

import at.robak.utils.GUIHelper;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 *
 * @author Thomas Robak
 */
public class TimeRenderer extends SimpleTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean arg2, boolean arg3, int arg4, int arg5) {
        super.getTableCellRendererComponent(table, value, arg2, arg3, arg4, arg5);
                if(value != null){
            setText(GUIHelper.formatTime(getText()));
        }
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }
}
