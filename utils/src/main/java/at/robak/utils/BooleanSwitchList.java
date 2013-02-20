/*
 * BooleanSwitchList.java
 *
 * Created on 22. August 2006, 10:56
 *
 */
/**
 *
 * @author Thomas Robak
 */

package at.robak.utils;

import java.util.ArrayList;

public class BooleanSwitchList extends ArrayList{
    
    protected int last_inverted=-1;
    
    public BooleanSwitchList() {
        super();
    }
    
    public BooleanSwitchList(int n){
        super(n);
    }
    
    public void invert(int i){
        if(i>=this.size()){
            return;
        }
        ((BooleanSwitch)this.get(i)).invert();
        this.last_inverted=i;
    }
    
    public void redoLastInvert(){
        if(this.last_inverted<0){
            return;
        }
        this.invert(this.last_inverted);
    }
}
