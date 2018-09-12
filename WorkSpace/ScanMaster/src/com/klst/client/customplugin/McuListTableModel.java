package com.klst.client.customplugin;

import javax.swing.table.AbstractTableModel;

public class McuListTableModel extends AbstractTableModel{

    private String[] columnNames = {};

    private Object[][] rowData = {};

    private Class[] typeArray = {};

    @Override
    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column].toString();
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return typeArray[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public Object[][] getRowData() {
        return rowData;
    }

    public void setRowData(Object[][] rowData) {
        this.rowData = rowData;
    }

    public Class[] getTypeArray() {
        return typeArray;
    }

    public void setTypeArray(Class[] typeArray) {
        this.typeArray = typeArray;
    }
}
