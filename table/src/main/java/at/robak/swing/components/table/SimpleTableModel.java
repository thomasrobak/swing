/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.robak.swing.components.table;

import java.awt.EventQueue;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Thomas Robak
 */
public abstract class SimpleTableModel<T> extends ReportingList<T, TableModelListener> implements TableModel {

    protected abstract String[] getColumnNames();

    protected abstract String[] getGetter();

    public SimpleTableModel() {
    }

    public SimpleTableModel(Collection<? extends T> elements) {
        super();
    }

    protected String[] getSetter() {
        return new String[0];
    }

    @Override
    public int getRowCount() {
        return this.size();
    }

    @Override
    public int getColumnCount() {
        return getColumnNames().length;
    }

    @Override
    public String getColumnName(int index) {
        return getColumnNames()[index];
    }

    @Override
    public Class getColumnClass(int c) {
        Object value = getValueAt(0, c);
        if (value == null) {
            return String.class;
        }
        return value.getClass();
    }

    protected boolean hasAnnotation(Class<? extends Annotation> annotation,int row,int column){
        Object value = get(row);
        if(value == null){
            return false;
        }
        try{
            String getter = getGetter()[column];
            if(getter.startsWith("get") == false){
                return false;
            }
            return value.getClass().getDeclaredField(getFieldName(getter)).isAnnotationPresent(annotation);
        } catch(Exception ex){
            return false;
        }
    }

    private String getFieldName(String getter){
        getter = getter.substring(3);
        String first = getter.substring(0, 1);
        String remaining = getter.substring(1);

        return first.toLowerCase() + remaining;
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
//        if (column >= getSetter().length || getSetter()[column] == null) {
//            throw new UnsupportedOperationException("Not supported yet.");
//        }
        int setterColumn = Math.min(column, getSetter().length -1);
        String prop = getSetter()[setterColumn];
        T obj = get(row);
        try {
            try {
                obj.getClass().getMethod(prop, value.getClass()).invoke(obj, value);
            } catch (NoSuchMethodException ex) {
                try {
                    getClass().getMethod(prop, obj.getClass(), value.getClass(), int.class, int.class).invoke(this, obj, value, row, column);
                } catch (NoSuchMethodException ex1) {
                    Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } catch (SecurityException ex) {
            Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableModelChanged(new TableModelEvent(this, row));
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (row >= size()) {
            return null;
        }
        T obj = get(row);
        String prop = getGetter()[Math.min(column, getGetter().length -1)];

        try {
            try {
                return obj.getClass().getMethod(prop, (Class[]) null).invoke(obj);
            } catch (NoSuchMethodException ex) {
                try {
                    return getClass().getMethod(prop, obj.getClass(), int.class, int.class).invoke(this, obj, row, column);
                } catch (NoSuchMethodException ex1) {
                    Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } catch (SecurityException ex) {
            Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SimpleTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void tableModelChanged() {
        final TableModelEvent evt = new TableModelEvent(this);
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                for (TableModelListener l : getModelListener()) {
                    l.tableChanged(evt);
                }
            }
        });
    }

    public void tableModelChanged(TableModelEvent evt) {
        for (TableModelListener l : getModelListener()) {
            l.tableChanged(evt);
        }
    }

    public List<T> findByValue(int column, Object value) {
        List<T> result = new LinkedList<T>();
        for (int i = 0; i < size(); ++i) {
            if (value.equals(getValueAt(i, column))) {
                result.add(get(i));
            }
        }
        return result;
    }
}
