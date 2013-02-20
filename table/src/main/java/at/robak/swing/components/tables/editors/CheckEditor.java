/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.robak.swing.components.tables.editors;

import at.robak.swing.components.table.renderer.SimpleTableCellRenderer;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Thomas Robak
 */
public class CheckEditor extends JCheckBox implements TableCellEditor, ItemListener {

    List<CellEditorListener> listener = new LinkedList<CellEditorListener>();

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean selected, int row, int column) {
        selected |= table.getSelectedRow() == row;
        setBackground(SimpleTableCellRenderer.getBackground(table, value, selected, row));
        setSelected((Boolean) value);
        return this;
    }

    @Override
    public Object getCellEditorValue() {
        return isSelected();
    }

    @Override
    public boolean isCellEditable(EventObject arg0) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject arg0) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        return true;
    }

    @Override
    public void cancelCellEditing() {
    }

    @Override
    public void addCellEditorListener(CellEditorListener arg0) {
        listener.add(arg0);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener arg0) {
        listener.remove(arg0);
    }

    public CheckEditor() {
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addItemListener(this);
    }

    public void fireEditingStopped() {
        for (int i = 0; i < listener.size(); ++i) {
            listener.get(i).editingStopped(new ChangeEvent(this));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {
        fireEditingStopped();
    }
}
