/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.components.table.renderer;

import java.awt.Color;

/**
 *
 * @author Thomas Robak
 */
public class InverseColorPrettyCentRenderer extends PrettyCentRenderer {

    @Override
    protected void applyValueFormat(double value, boolean selected) {
        if(value != 0){
            setForeground(Color.RED);
        } else {
            setForeground(selected ? Color.WHITE : Color.BLACK);
        }
    }
}
