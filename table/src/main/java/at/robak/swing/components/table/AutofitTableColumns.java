/*
 * AutofitTableColumns.java
 *
 * Created on 22. August 2006, 11:38
 *
 */
/**
 *
 * @author Thomas Robak
 */

package at.robak.swing.components.table;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Component;

import javax.swing.text.JTextComponent;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;

import at.robak.utils.StatisticCalculator;
import java.awt.Dimension;


public class AutofitTableColumns {

    private static final int DEFAULT_COLUMN_PADDING = 5;
    private static final int MIN_COLUMN_WIDTH=50;

    public static int[] autoResizeTable(JTable aTable) {
        return autoResizeTable(aTable,DEFAULT_COLUMN_PADDING);
    }

    public static int[] autoResizeTable(JTable aTable,int columnPadding ) {
        int rWidth=aTable.getWidth();
        int rHeight=aTable.getHeight();
        int columnCount = aTable.getColumnCount();
        int tableWidth  = 0;

        Dimension cellSpacing = aTable.getIntercellSpacing();

        if(columnCount<=1){
            return new int[] {0};
        }

        int columnWidth[] = new int[columnCount];
        double columnWidth2[] = new double[columnCount];

        int minWidth=0;
        int maxWidth=0;

        for ( int i=0; i<columnCount; i++ ) {
            int width = getMaxColumnWidth( aTable, i, columnPadding );
            columnWidth[i]=width;
            columnWidth2[i]=width;
            tableWidth += columnWidth[i];
        }

        if(columnCount>2){
            StatisticCalculator sc=new StatisticCalculator(columnWidth2);
            minWidth=(int)sc.min;
            maxWidth=(int)sc.quartile[3];
        }

        if(tableWidth<rWidth){
            int slice=1+(rWidth-tableWidth)/columnCount;
            for(int i=0;i<columnCount;++i){
                columnWidth[i]+=slice;
            }
        }

        TableColumnModel tableColumnModel = aTable.getColumnModel();
        TableColumn tableColumn;

        for ( int i=0; i<columnCount; i++ ) {

            tableColumn = tableColumnModel.getColumn( i );
            tableColumn.setMinWidth(minWidth);
//            tableColumn.setMaxWidth(maxWidth);
//            tableColumn.setPreferredWidth( columnWidth[i] );
            tableColumn.setMaxWidth(columnWidth[i]);
            tableColumn.setPreferredWidth(maxWidth);

        }

        aTable.doLayout();
        return columnWidth;
    }

    private static int getMaxColumnWidth( JTable aTable, int columnNo,int columnPadding ) {
        TableColumn column = aTable.getColumnModel().getColumn( columnNo );
        Component comp = null;
        int maxWidth = 0;
        TableCellRenderer headerRenderer = column.getHeaderRenderer();
        if ( headerRenderer != null ) {
            comp = headerRenderer.getTableCellRendererComponent( aTable, column.getHeaderValue(), false, false, 0, columnNo );

            if ( comp instanceof JTextComponent ) {
                JTextComponent jtextComp = (JTextComponent)comp;

                String text = jtextComp.getText();
                Font font = jtextComp.getFont();
                FontMetrics fontMetrics = jtextComp.getFontMetrics( font );

                maxWidth = SwingUtilities.computeStringWidth( fontMetrics, text );
            } else {
                maxWidth = comp.getPreferredSize().width;
            }
        } else {
            try {
                String headerText = (String)column.getHeaderValue();
                JLabel defaultLabel = new JLabel( headerText );

                Font font = defaultLabel.getFont();
                FontMetrics fontMetrics = defaultLabel.getFontMetrics( font );

                maxWidth = SwingUtilities.computeStringWidth( fontMetrics, headerText );
            } catch ( ClassCastException ce ) {
                ce.printStackTrace();
                maxWidth = 0;
            }
        }
        TableCellRenderer tableCellRenderer;

        int cellWidth=0;

        for (int i = 0; i < aTable.getRowCount(); i++) {
            tableCellRenderer = aTable.getCellRenderer( i, columnNo );

            Object o=aTable.getValueAt(i,columnNo);
            comp = tableCellRenderer.getTableCellRendererComponent( aTable, o, false, false, i, columnNo );

            if ( comp instanceof JTextComponent ||o instanceof String) {
                String text = ((o instanceof String)?(String)o:((JTextComponent)comp).getText());

                Font font = comp.getFont();
                FontMetrics fontMetrics = comp.getFontMetrics( font );

                int textWidth = SwingUtilities.computeStringWidth( fontMetrics, text );

                maxWidth = Math.max( maxWidth, textWidth );
            } else {
                cellWidth = comp.getPreferredSize().width;
                maxWidth = Math.max( maxWidth, cellWidth );
            }
        }

        return maxWidth + columnPadding;
    }
}