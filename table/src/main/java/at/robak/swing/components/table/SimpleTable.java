/*
 *  GIBA DEVELOPMENT
 */
package at.robak.swing.components.table;

import at.robak.swing.components.controller.RequestFocusWhenComponentShownListener;
import at.robak.swing.interfaces.IFilteredComponent;
import at.robak.swing.interfaces.IFilter;
import at.robak.swing.components.table.renderer.MultiLineRenderer;
import at.robak.swing.components.table.renderer.DateRenderer;
import at.robak.swing.components.table.renderer.NumericRenderer;
import at.robak.swing.components.table.renderer.SimpleTableCellRenderer;
import at.robak.swing.components.table.renderer.CheckRenderer;
import at.robak.swing.components.tables.editors.CheckEditor;
import at.robak.swing.components.table.filter.TextFilter;
import at.robak.swing.components.table.renderer.SimpleTableHeaderCellRenderer;
import at.robak.swing.interfaces.ColumnsFitListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Base class to use for all tables across the Giba-Platform
 *
 * Supports:
 * Sorting
 * Autoresizing
 * Filtering
 *
 * @author Thomas Robak
 */
public class SimpleTable<Model extends TableModel> extends JTable implements IFilteredComponent {

    public static final int DEFAULT_ACTION = 0;
    private SimpleTableKeyAdapter keyAdapter;
    private List<ColumnsFitListener> fitListener = new LinkedList<ColumnsFitListener>();
    boolean doAutoFit = true;

    public boolean isDoAutoFit() {
        return doAutoFit;
    }

    public void setDoAutoFit(boolean doAutoFit) {
        this.doAutoFit = doAutoFit;
    }

    public void setFixedColumnWidth(int width){
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setDoAutoFit(false);
        Enumeration<TableColumn> columns = getColumnModel().getColumns();
        while(columns.hasMoreElements()) {
            columns.nextElement().setPreferredWidth(width);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Actions">
    private List<CellEditorListener> editorListeners = new LinkedList<CellEditorListener>();

    public void addCellEditorListener(CellEditorListener listener) {
        editorListeners.add(listener);
    }

    public void removeCellEditorListener(CellEditorListener listener) {
        editorListeners.remove(listener);
    }

    private void fireCellEditingCanceled(ChangeEvent ev){
        for (CellEditorListener listener : editorListeners) {
            listener.editingCanceled(ev);
        }
    }

    private void fireCellEditingStopped(ChangeEvent ev){
        for (CellEditorListener listener : editorListeners) {
            listener.editingStopped(ev);
        }
    }

    public void addDefaultActionListener(ActionListener listener) {
        keyAdapter.addKeyAction(DEFAULT_ACTION, listener);
    }

    private void fireDefaultActionPerformed() {
        keyAdapter.fireActionPerformed(this, DEFAULT_ACTION, "DEFAULT");
    }

    public void addKeyAction(Integer key, ActionListener action) {
        keyAdapter.addKeyAction(key, action);
    }

    public void addKeyAction(Integer[] keys, ActionListener action) {
        keyAdapter.addKeyAction(keys, action);
    }

    public void addColumnsFitListener(ColumnsFitListener listener) {
        fitListener.add(listener);
    }

    private void fireColumnsFit(int[] columnMaxWidths) {
        if (getColumnCount() < 1) {
            return;
        }
        int[] columnWidths = new int[columnMaxWidths.length];
        for (int i = 0; i < columnMaxWidths.length; ++i) {
            columnWidths[i] = getColumnModel().getColumn(i).getWidth();
        }
        for (ColumnsFitListener listener : fitListener) {
            listener.columnsFit(columnWidths);
        }
    }
    //</editor-fold>

    private static final int DEFAULT_COLUMN_PADDING = 20;

    public SimpleTable() {
        this(null);
    }

    public SimpleTable(Model model) {
        super();
        if (model != null) {
            setModel(model);
        }
        setDefaultRenderer();
        setBackground(Color.WHITE);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new SimpleTableHeaderCellRenderer());

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initKeyAdapter();
        addKeyListener(this.keyAdapter);
        addComponentListener(new RequestFocusWhenComponentShownListener(this));
        setFocusable(false);
        setFocusable(false);
    }

    public String getPrintoutName() {

        String name = getClass().getSimpleName();
        if (name.endsWith("Table")) {
            name = name.substring(0, name.length() - "Table".length());
        }
        return name;
    }

    private void initKeyAdapter() {
        this.keyAdapter = new SimpleTableKeyAdapter();
        this.keyAdapter.addKeyAction(KeyEvent.VK_ENTER, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                fireDefaultActionPerformed();
            }
        });
    }

    @Override
    protected void processMouseEvent(MouseEvent arg0) {
        if (arg0.getID() == MouseEvent.MOUSE_CLICKED && arg0.getButton() == MouseEvent.BUTTON1 && arg0.getClickCount() == 2) {
            fireDefaultActionPerformed();
            return;
        }
        super.processMouseEvent(arg0);
    }

    @Override
    public void setModel(TableModel arg0) {
        super.setModel(arg0);
        setRowSorter(new TableRowSorter<Model>((Model) getModel()));
    }

    /**
     * @return getRowSorter casted
     */
    private TableRowSorter<Model> getTableRowSorter() {
        return ((TableRowSorter<Model>) getRowSorter());
    }

    public boolean searchOn() {
        return getRowSorter() != null && getTableRowSorter().getRowFilter() != null;
    }

    /**
     *
     * @return
     */
    public IFilter getRowFilter() {
        if (getTableRowSorter().getRowFilter() == null) {
            return null;
        }
        if (getTableRowSorter().getRowFilter() instanceof IFilter) {
            return (IFilter) getTableRowSorter().getRowFilter();
        }
        return null;
    }

    /**
     * Sets custom default-renderer for various classes
     */
    private void setDefaultRenderer() {
        setDefaultRenderer(Object.class, new SimpleTableCellRenderer());
        setDefaultRenderer(Boolean.class, new CheckRenderer());
        setDefaultRenderer(Integer.class, new NumericRenderer());
        setDefaultRenderer(Float.class, new NumericRenderer());
        setDefaultRenderer(Double.class, new NumericRenderer());
        setDefaultRenderer(Long.class, new NumericRenderer());
        setDefaultRenderer(Date.class, new DateRenderer());
        setDefaultRenderer(Object[].class, new MultiLineRenderer());
        setDefaultRenderer(String[].class, new MultiLineRenderer());
        setDefaultRenderer(Integer[].class, new MultiLineRenderer());
        setDefaultRenderer(Date[].class, new MultiLineRenderer());
        setDefaultRenderer(Boolean[].class, new MultiLineRenderer());
    }

    @Override
    public void tableChanged(TableModelEvent arg0) {
        super.tableChanged(arg0);
        if (getRowCount() > 0 && isDoAutoFit()) {
            fireColumnsFit(AutofitTableColumns.autoResizeTable(this, DEFAULT_COLUMN_PADDING));
        }
    }

    /**
     * Resets the filter over the table
     */
    @Override
    public void resetFilter() {
        getTableRowSorter().setRowFilter(null);
    }

    @Override
    public void setFilter(String filterText) {
        getTableRowSorter().setRowFilter(new TextFilter(filterText));
    }

    @Override
    public boolean isSearchPanelHideEvent(KeyEvent evt) {
        return evt.getKeyCode() == KeyEvent.VK_ESCAPE;
    }

    @Override
    public boolean isSearchPanelShowEvent(KeyEvent evt) {
        return evt.getKeyCode() == KeyEvent.VK_F;
    }

    @Override
    public boolean isSearchPanelAlwaysVisible() {
        return false;
    }

    private boolean hasAnnotation(Class<? extends Annotation> annotation, int row, int column) {
        if (getModel() instanceof SimpleTableModel) {
            return ((SimpleTableModel) getModel()).hasAnnotation(annotation, row, column);
        }
        return false;
    }

    @Override
    public void editingCanceled(ChangeEvent ev) {
        super.editingCanceled(ev);
        fireCellEditingCanceled(ev);
    }

    @Override
    public void editingStopped(ChangeEvent ev) {
        super.editingStopped(ev);
        fireCellEditingStopped(ev);
    }
}
