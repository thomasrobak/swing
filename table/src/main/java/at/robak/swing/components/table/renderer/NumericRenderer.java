/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.robak.swing.components.table.renderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 *
 * @author Thomas Robak
 */
public class NumericRenderer extends SimpleTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
        super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        try {
            applyValueFormat(transformValue(arg1), arg0.getSelectedRow() == arg4);
        } catch (NumberFormatException e) {
//            Logger.getLogger(getClass().getName()).log(Level.INFO, "", e);
        }
        return this;
    }

    protected double transformValue(Object value) {
        if (value == null) {
            throw new NumberFormatException("value is null ");
        }
        if (value instanceof String) {
            return Double.parseDouble((String) value);
        }
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        }
        if (value instanceof Float) {
            return ((Float) value).doubleValue();
        }
        if (value instanceof Long) {
            return ((Long) value).doubleValue();
        }
        if (value instanceof Double) {
            return ((Double) value);
        }
        throw new NumberFormatException("Type "+value.getClass()+" not supported by this renderer");
    }

    protected void applyValueFormat(double value, boolean selected) {
        if (value < 0) {
            setForeground(Color.RED);
        } else {
            setForeground(selected ? Color.WHITE : Color.BLACK);
        }


    }
}
