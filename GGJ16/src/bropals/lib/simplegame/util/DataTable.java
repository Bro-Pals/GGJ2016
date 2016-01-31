/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Jonathon Prehn and Kevin Prehn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package bropals.lib.simplegame.util;

import java.util.ArrayList;

/**
 * Represents an immutable table of data.
 * @author Jonathon
 */
public class DataTable {
    
    private final Class[] columnTypes;
    private final Object[][] data;
    private final String[] columnNames;
    
    /**
     * Creates a DataTable. Use {@link bropals.lib.simplegame.util.AssetLoader#loadDataTable()} 
     * instead. 
     * @param columnTypes the data type of each column
     * @param columnNames the name of each column
     * @param rows the row data
     */
    public DataTable(Class[] columnTypes, String[] columnNames, ArrayList<Object[]> rows) {
        this.columnTypes = columnTypes;
        this.columnNames = columnNames;
        this.data = (Object[][])rows.toArray(new Object[rows.size()][0]);
    }
    
    /**
     * Gets the number of rows in the data table.
     * @return the number of rows in the data table.
     */
    public int getRowCount() {
        return data.length;
    }
    
    /**
     * Gets a row of data.
     * @param row the row to get
     * @return the row data
     */
    public Object[] getRow(int row) {
        return data[row];
    }
    
    /**
     * Gets the element at the specified row and column.
     * @param <T> the data type of the element
     * @param row the row the element is on
     * @param column the column the element is on
     * @return the data at that spot
     */
    public <T> T getElement(int row, int column) {
        return (T) getRow(row)[column];
    }
    
    /**
     * Get the data type of the specified column.
     * @param column the column to get the type of.
     * @return the column's type
     */
    public Class getColumnType(int column) {
        return columnTypes[column].getClass();
    }
    
    /**
     * Get the name of the specified column.
     * @param column the column to get the name of.
     * @return the column's name
     */
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
