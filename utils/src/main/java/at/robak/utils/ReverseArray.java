/*
 * ReverseArray.java
 *
 * Created on 15. Mai 2006, 16:50
 *
 */
/**
 *
 * @author Thomas Robak
 */

package at.robak.utils;

public class ReverseArray {
    
    public ReverseArray() {
    }
    
    public static byte[] reverse(byte[] b){
        if(b==null){
            return null;
        }
        byte[] r=new byte[b.length];
        for(int i=b.length-1;i>=0;--i){
            r[Math.abs(b.length-i)-1]=b[i];
        }
        return r;
    }
    
    public static Object[] reverse(Object[] a){
        if(a==null){
            return a;
        }
        int i=0;
        int j=a.length-1;
        for(;i<j;++i,--j){
            swap(a,i,j);
        }
        return a;
    }

    public static void swap(Object[] a,int i,int j){
        if(a==null){
            return;
        }
        if(i<0||i>=a.length||j<0||j>=a.length){
            return;
        }
        Object h=a[i];
        a[i]=a[j];
        a[j]=h;
    }
}
