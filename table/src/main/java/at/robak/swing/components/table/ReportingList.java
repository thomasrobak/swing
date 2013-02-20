/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.robak.swing.components.table;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Thomas Robak
 */
public abstract class ReportingList<ListDataType,ModelListenerType> extends LinkedList<ListDataType> {
    protected abstract void tableModelChanged();
    private LinkedList<ModelListenerType> modelListener = new LinkedList<ModelListenerType>();

    public ReportingList() {
    }

    public ReportingList(Collection<? extends ListDataType> elements) {
        super(elements);
    }

    protected LinkedList<ModelListenerType> getModelListener() {
        return modelListener;
    }

    protected void setModelListener(LinkedList<ModelListenerType> modelListener) {
        this.modelListener = modelListener;
    }

    public void addTableModelListener(ModelListenerType arg0) {
        this.getModelListener().add(arg0);
    }

    public void removeTableModelListener(ModelListenerType arg0) {
        this.modelListener.remove(arg0);
    }

    @Override
    public boolean add(ListDataType arg0) {
        boolean result = super.add(arg0);
        tableModelChanged();
        return result;
    }

    public boolean init(Collection<? extends ListDataType> arg0) {
        return super.addAll(arg0);
    }

    @Override
    public void clear() {
        super.clear();
        tableModelChanged();
    }

    @Override
    public boolean remove(Object arg0) {
        boolean result = super.remove(arg0);
        tableModelChanged();
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        boolean result = super.removeAll(arg0);
        tableModelChanged();
        return result;
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends ListDataType> arg1) {
        boolean result = super.addAll(arg0,arg1);
        tableModelChanged();
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        boolean result = super.retainAll(arg0);
        tableModelChanged();
        return result;
    }


    @Override
    public ListDataType set(int arg0, ListDataType arg1) {
        ListDataType result = super.set(arg0,arg1);
        tableModelChanged();
        return result;
    }

    @Override
    public ListDataType remove(int arg0) {
        ListDataType result = super.remove(arg0);
        tableModelChanged();
        return result;
    }
}
