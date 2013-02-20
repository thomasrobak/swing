package at.robak.utils;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractSorter {
	protected int swaps[]=null;
    
    public int[] getSwaps(){
        return swaps;
    }
    
    public abstract void doSort(List list, Comparator cmp,int size);
    
    public void sort(List list, Comparator cmp,int size){
        swaps=new int[size];
        for(int i=0;i<swaps.length;++i){
            swaps[i]=i;
        }
        doSort(list,cmp,size);
    }
    
    public void swap(List list,int i,int j){
        if(list==null){
            throw new NullPointerException();
        }
        int size=list.size();
        if(i<0){
            throw new IndexOutOfBoundsException(i+"<0");
        }
        if(j<0){
            throw new IndexOutOfBoundsException(j+"<0");
        }
        if(i>=size){
            throw new IndexOutOfBoundsException(i+">="+size);
        }
        if(j>=size){
            throw new IndexOutOfBoundsException(j+">="+size);
        }
        Object h=list.get(i);
        list.set(i,list.get(j));
        list.set(j,h);
        int h2=swaps[j];
        swaps[j]=swaps[i];
        swaps[i]=h2;
    }
}
