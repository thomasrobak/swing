package at.robak.utils;

import java.util.Comparator;
import java.util.List;

public class QuickSorter extends AbstractSorter {
	public QuickSorter() {
    }

    public void doSort(List list, Comparator cmp, int size) {
        qsort(list,0,size-1,cmp);
    }
    
    protected int partition(List list, int begin, int end, Comparator cmp) {
        int index = begin;
        Object pivot = list.get(index);
        super.swap(list, index, end);        
        for (int i = index = begin; i < end; ++ i) {
            if (cmp.compare(list.get(i), pivot) <= 0) {
                super.swap(list, index++, i);
        }   }
        super.swap(list, index, end);        
        return index;
    }

    protected void qsort(List list, int begin, int end, Comparator cmp) {
        if (end > begin) {
            int index = partition(list, begin, end, cmp);
            qsort(list, begin, index - 1, cmp);
            qsort(list, index + 1,  end,  cmp);
        }
    }
}
