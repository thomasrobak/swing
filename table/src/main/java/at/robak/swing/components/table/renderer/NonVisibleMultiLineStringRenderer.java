/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.components.table.renderer;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Thomas Robak
 */
public class NonVisibleMultiLineStringRenderer extends MultiLineRenderer{

    @Override
    public TableCellRenderer createLineRenderer(JTable table, Object value, int row, int column, int subrow) {
        if(subrow == 1){
            return new NonVisibleStringRenderer();
        }
        return super.createLineRenderer(table, value, row, column, subrow);
    }
}
