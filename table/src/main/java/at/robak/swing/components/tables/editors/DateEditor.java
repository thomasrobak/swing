/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.robak.swing.components.tables.editors;

import at.robak.utils.GUIHelper;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author schober
 */
public class DateEditor extends GesibaDefaultEditor {

    public DateEditor() {
        super(new JTextField());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (StringUtils.isBlank((String) value)) {
            value = GUIHelper.getSysDateWithPoints();
        }

        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

}
