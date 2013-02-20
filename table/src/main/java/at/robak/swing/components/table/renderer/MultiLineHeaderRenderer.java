/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.robak.swing.components.table.renderer;

import at.robak.swing.components.table.Format;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author schober
 */
public class MultiLineHeaderRenderer extends JList implements TableCellRenderer {
  public MultiLineHeaderRenderer() {
    setOpaque(true);
    setForeground(Format.COLOR_TBL_HEADERFONT);
    setBackground(Format.COLOR_TBL_HEADER);
    setBorder(new LineBorder(Color.GRAY, 1));
    ListCellRenderer renderer = getCellRenderer();
    ((JLabel) renderer).setHorizontalAlignment(JLabel.CENTER);
    setCellRenderer(renderer);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    setFont(table.getFont());
    String str = (value == null) ? "" : value.toString();
    BufferedReader br = new BufferedReader(new StringReader(str));
    String line;
    Vector v = new Vector();
    try {
      while ((line = br.readLine()) != null) {
        v.addElement(line);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    setListData(v);
    return this;
  }
}
