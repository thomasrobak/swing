/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.robak.swing.components.table.renderer;

import at.robak.swing.components.table.Format;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author schober
 */
public class SimpleTableHeaderCellRenderer extends DefaultTableCellRenderer {

    public SimpleTableHeaderCellRenderer() {
        setHorizontalAlignment(CENTER);
        setHorizontalTextPosition(LEFT);
        setVerticalAlignment(TOP);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        String html = value == null ? "" : "<html><p align=\"center\">" + value.toString() + "</p></html>";

        super.getTableCellRendererComponent(table, html, isSelected, hasFocus, row, column);
        setBackground(Format.COLOR_TBL_HEADER);
        setForeground(Format.COLOR_TBL_HEADERFONT);
        setBorder(new LineBorder(Color.GRAY, 1));
        return this;
    }
}
