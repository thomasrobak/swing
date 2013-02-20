package at.robak.swing.components.table.renderer;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Thomas Robak
 */
public class CheckRenderer extends JCheckBox implements TableCellRenderer {

    public CheckRenderer() {
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focus, int row, int column) {
        selected |= table.getSelectedRow() == row;
        setBackground(SimpleTableCellRenderer.getBackground(table,value,selected, row));
        if(value != null){
            setSelected((Boolean)value);
        }

//        if(table.isCellEditable(row, column))
//            table.getCellEditor(row, column).getTableCellEditorComponent(table, value, focus, row, column).setBackground(getBackground());

        return this;
    }

}
