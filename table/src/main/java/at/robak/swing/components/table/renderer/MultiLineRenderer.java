/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.components.table.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Thomas Robak
 */
public class MultiLineRenderer extends JPanel implements TableCellRenderer{

    public MultiLineRenderer() {
        setLayout(new BorderLayout());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object arg1, boolean selected, boolean hasFocus, int row, int column) {
        Object[] values = new Object[0];
        if(arg1 instanceof Object[]){
            values = (Object[]) arg1;
        }

        removeAll();
        JPanel rowPanel = new JPanel(new GridLayout(0, 1, 5, 0));

        for (int i = 0; i < values.length; i++) {
            TableCellRenderer lineRenderer = createLineRenderer(table,values[i],row, column,i);

            Component lineComponent = lineRenderer.getTableCellRendererComponent(table, values[i], selected, hasFocus, row, column);
            rowPanel.add(lineComponent);
        }

        add(rowPanel,BorderLayout.PAGE_START);

        if(rowPanel.getPreferredSize().height > table.getRowHeight(row)){
            table.setRowHeight(row,rowPanel.getPreferredSize().height);
        }

        Color background = SimpleTableCellRenderer.getBackground(table,arg1,selected, row);
        rowPanel.setBackground(background);
        setBackground(background);
        return this;
    }

    public TableCellRenderer createLineRenderer(JTable table,Object value,int row,int column,int subrow){
        try {
            return table.getDefaultRenderer(value.getClass()).getClass().newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(MultiLineRenderer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MultiLineRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new SimpleTableCellRenderer();
    }
}
