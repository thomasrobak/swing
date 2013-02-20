/*
 * BooleanSwitch.java
 *
 * Created on 13. März 2006, 14:00
 *
 */
/**
 *
 * @author Thomas Robak
 */

package at.robak.utils;

public class BooleanSwitch extends Switch {
    
    public BooleanSwitch(boolean b){
        this(new Boolean(b));
    }
    
    public BooleanSwitch(Boolean b) {
        super(b);
    }
    
    public void invert(){
        if(this.value instanceof Boolean){
            this.setValue(new Boolean(!((Boolean)this.value).booleanValue()));
        } else {
            this.value=new Boolean(false);
        }
    }
    
    public Object getValue(){
        if(this.value instanceof Boolean){
            return (Boolean)super.getValue();
        }
        return null;
    }
    
    public boolean booleanValue(){
        Boolean b=(Boolean)this.getValue();
        if(b!=null){
            return b.booleanValue();
        }
        return false;
    }
}
