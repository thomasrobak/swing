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
public class SaldoRenderer extends PrettyCentMultiLineRenderer{

    @Override
    public TableCellRenderer createLineRenderer(JTable table, Object value, int row, int column, int subrow) {
        if(subrow == 1){
            return new InverseColorPrettyCentRenderer();
        }
        return super.createLineRenderer(table, value, row, column, subrow);
    }
}
