package at.robak.swing.components.table.renderer;

import at.robak.swing.components.table.SimpleTable;
import at.robak.swing.components.table.Format;
import at.robak.swing.interfaces.IFilter;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Thomas Robak
 */
public class SimpleTableCellRenderer extends DefaultTableCellRenderer{

    static Border gap = BorderFactory.createEmptyBorder(2, 4, 0, 4);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean hasFocus, int row, int column) {
        selected |= table.getSelectedRow() == row;

        super.getTableCellRendererComponent(table,value,selected,hasFocus,row,column);
        setText(value == null?"":value.toString());
        this.setFont(Format.DEFAULT_FONT);

        setBackground(getBackground(table,value,selected, row));
        setVerticalAlignment(TOP);
        setBorder(getBorder(table,value));

        return this;
    }

    public static boolean searchOn(JTable table){
        if(table instanceof SimpleTable){
            return ((SimpleTable) table).searchOn();
        }
        return false;
    }

    public static boolean isMatch(JTable table,Object value){
        if(table instanceof SimpleTable == false){
            return false;
        }
        IFilter filter = ((SimpleTable) table).getRowFilter();
        if(filter == null){
            return false;
        }
        return filter.include(value);
    }

    public static Border getBorder(JTable table,Object value){
        return searchOn(table)  ? isMatch(table, value)
                                    ? new LineBorder(Format.COLOR_BORDER_SEARCHMATCH)
                                    : gap
                                : gap;
    }

    public static Color getBackground(JTable table,Object value,boolean selected, int row){
        return searchOn(table)  ? getSearchBackground(table,value, selected, row)
                                : getDefaultBackground(selected, row);
    }

    private static Color getSearchBackground(JTable table,Object value,boolean selected,int row){
        return selected || isMatch(table, value)
                                        ? Format.COLOR_TBL_SEARCHMATCH
                                        : row % 2 == 0
                                            ? Format.COLOR_TBL_SEARCHROW2
                                            :  Format.COLOR_TBL_SEARCHROW1;
    }

    private static Color getDefaultBackground(boolean selected,int row){
        return selected ? Format.COLOR_TBL_SEL
                        : row % 2 == 0
                            ? Format.COLOR_TBL_ROW2
                            : Format.COLOR_TBL_ROW1;

    }

}
