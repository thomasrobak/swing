/*
 * Switch.java
 *
 * Created on 13. März 2006, 13:55
 *
 */
/**
 *
 * @author Thomas Robak
 */

package at.robak.utils;

public class Switch {
    protected Object value=null;
    
    public Switch(Object value) {
        this.setValue(value);
    }
    public void setValue(Object value){
        this.value=value;
    }
    public Object getValue(){
        return this.value;
    }
}
