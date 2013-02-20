/*
 * TrackCollections.java
 *
 * Created on 14. März 2006, 10:43
 *
 */
/**
 *
 * @author Thomas Robak
 */

package at.robak.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TrackCollections {
	 protected void track(Object[] list,int[] swaps){
	        if(list==null){
	            return;
	        }
	        if(list.length!=swaps.length){
	            return;
	        }
	        Object[] h=new Object[list.length];
	        for(int i=0;i<swaps.length;++i){
	            h[i]=list[swaps[i]];
	        }
	        for(int i=0;i<h.length;++i){
	            list[i]=h[i];
	        }
	    }
	    
	    public void sort(List list, Comparator c,List tracklist,Class classSorter) {
	        if(list==null||c==null){
	            throw new NullPointerException();
	        }
	        AbstractSorter sorter=null;
	        try {
	            sorter = (AbstractSorter)classSorter.newInstance();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return;
	        } 
	        
	        if(list.isEmpty()){
	            return;
	        }

	        int size=list.size();
	        sorter.sort(list,c,size);
	        
	        if(tracklist!=null){
	            for(Iterator it=tracklist.iterator();it.hasNext();){
	                track((Object[])it.next(),sorter.getSwaps());
	            }
	        }
	    }
	    
}
