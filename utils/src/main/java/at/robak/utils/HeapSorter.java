package at.robak.utils;

import java.util.Comparator;
import java.util.List;

public class HeapSorter extends AbstractSorter {
	public HeapSorter() {
    }

    public void doSort(List list, Comparator cmp, int size) {
        heapsort(list,cmp,size);
    }
    
    public void heapsort(List a, Comparator cmp,int size) {
        buildheap(a,cmp,size);
        extract(a,cmp,size);
    }

    private void buildheap(List a, Comparator cmp,int size) {
        for(int i=size/2-1; i>=0; i--) {
            heapify(a,cmp,i,a.size());
        }
    }

    private void extract(List a, Comparator cmp,int size) {
        int n = size; 

        while (n>1) {
            n--;
            super.swap(a,0,n);
            heapify(a,cmp,0,n);
        }
    }

    private void heapify(List a, Comparator cmp, int i, int n) {
        int k=2*i+1; 
        while (k<n) {
            if (k+1<n && (cmp.compare(a.get(k+1),a.get(k))>0)){
                k++;
            }
            if(cmp.compare(a.get(i),a.get(k))>=0){
                return; 
            } else {
                super.swap(a, i, k);
                i=k;
                k=2*i+1;
            }
        }
    }
}
