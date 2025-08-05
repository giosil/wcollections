package org.dew.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryResultSet implements ResultSet {

  protected Statement stm;
  protected ResultSetMetaData rsmd;
  protected Map<String, Integer> fields;
  protected List<List<Object>> data;
  protected int index;
  protected boolean closed;
  
  public InMemoryResultSet(ResultSet rs)
  {
    index = -1;
    try {
      stm = rs.getStatement();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    try {
      rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      fields = new HashMap<String, Integer>(columnCount);
      for (int i = 1; i <= columnCount; i++){
        String columnName = rsmd.getColumnName(i);
        fields.put(columnName.toUpperCase(), i);
      }
      data = new ArrayList<List<Object>>();
      while(rs.next()) {
        List<Object> record = new ArrayList<Object>(columnCount);
        for (int i = 1; i <= columnCount; i++) {
          record.add(rs.getObject(i));
        }
        data.add(record);
      }
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    finally {
      if(rs != null) try { rs.close(); } catch(Exception ex) {}
    }
    if(fields == null) fields = new HashMap<String, Integer>();
    if(data   == null) data   = new ArrayList<List<Object>>();
  }
  
  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }

  @Override
  public boolean next() throws SQLException {
    if(data == null || data.size() == 0) return false;
    if(index >= 0 && index >= data.size() - 1) {
      return false;
    }
    index++;
    return true;
  }

  @Override
  public void close() throws SQLException {
    closed = true;
  }

  @Override
  public boolean wasNull() throws SQLException {
    return false;
  }

  @Override
  public String getString(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toString(record.get(i), null);
  }

  @Override
  public boolean getBoolean(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toBoolean(record.get(i), false);
  }

  @Override
  public byte getByte(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return (byte) WUtil.toInt(record.get(i), 0);
  }

  @Override
  public short getShort(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return (short) WUtil.toInt(record.get(i), 0);
  }

  @Override
  public int getInt(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toInt(record.get(i), 0);
  }

  @Override
  public long getLong(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toLong(record.get(i), 0);
  }

  @Override
  public float getFloat(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return (float) WUtil.toDouble(record.get(i), 0.0d);
  }

  @Override
  public double getDouble(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toDouble(record.get(i), 0.0d);
  }

  @Override
  public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toBigDecimal(record.get(i), null);
  }

  @Override
  public byte[] getBytes(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toArrayOfByte(record.get(i), false);
  }

  @Override
  public Date getDate(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toSQLDate(record.get(i), null);
  }

  @Override
  public Time getTime(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toSQLTime(record.get(i), null);
  }

  @Override
  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toSQLTimestamp(record.get(i), null);
  }

  @Override
  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    byte[] bytes = WUtil.toArrayOfByte(record.get(i), true);
    return new ByteArrayInputStream(bytes);
  }

  @Override
  public InputStream getUnicodeStream(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    byte[] bytes = WUtil.toArrayOfByte(record.get(i), true);
    return new ByteArrayInputStream(bytes);
  }

  @Override
  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    byte[] bytes = WUtil.toArrayOfByte(record.get(i), true);
    return new ByteArrayInputStream(bytes);
  }

  @Override
  public String getString(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getString(colIdx.intValue());
  }

  @Override
  public boolean getBoolean(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getBoolean(colIdx.intValue());
  }

  @Override
  public byte getByte(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getByte(colIdx.intValue());
  }

  @Override
  public short getShort(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getShort(colIdx.intValue());
  }

  @Override
  public int getInt(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getInt(colIdx.intValue());
  }

  @Override
  public long getLong(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getLong(colIdx.intValue());
  }

  @Override
  public float getFloat(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getFloat(colIdx.intValue());
  }

  @Override
  public double getDouble(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getDouble(colIdx.intValue());
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getBigDecimal(colIdx.intValue());
  }

  @Override
  public byte[] getBytes(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getBytes(colIdx.intValue());
  }

  @Override
  public Date getDate(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getDate(colIdx.intValue());
  }

  @Override
  public Time getTime(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getTime(colIdx.intValue());
  }

  @Override
  public Timestamp getTimestamp(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getTimestamp(colIdx.intValue());
  }

  @Override
  public InputStream getAsciiStream(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getAsciiStream(colIdx.intValue());
  }

  @Override
  public InputStream getUnicodeStream(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getUnicodeStream(colIdx.intValue());
  }

  @Override
  public InputStream getBinaryStream(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getBinaryStream(colIdx.intValue());
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return null;
  }

  @Override
  public void clearWarnings() throws SQLException {
  }

  @Override
  public String getCursorName() throws SQLException {
    return "ResultSetCached";
  }

  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    return rsmd;
  }

  @Override
  public Object getObject(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return record.get(i);
  }

  @Override
  public Object getObject(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getObject(colIdx.intValue());
  }

  @Override
  public int findColumn(String columnLabel) throws SQLException {
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) return 0;
    return colIdx.intValue();
  }

  @Override
  public Reader getCharacterStream(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    byte[] bytes = WUtil.toArrayOfByte(record.get(i), true);
    return new InputStreamReader(new ByteArrayInputStream(bytes));
  }

  @Override
  public Reader getCharacterStream(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getCharacterStream(colIdx.intValue());
  }

  @Override
  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return WUtil.toBigDecimal(record.get(i), null);
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getBigDecimal(colIdx.intValue());
  }

  @Override
  public boolean isBeforeFirst() throws SQLException {
    return index == -1;
  }

  @Override
  public boolean isAfterLast() throws SQLException {
    return index >= data.size();
  }

  @Override
  public boolean isFirst() throws SQLException {
    return index == 0;
  }

  @Override
  public boolean isLast() throws SQLException {
    return index >= 0 && index == data.size() - 1;
  }

  @Override
  public void beforeFirst() throws SQLException {
    index = -1;
  }

  @Override
  public void afterLast() throws SQLException {
    index = data.size();
  }

  @Override
  public boolean first() throws SQLException {
    if(data == null || data.size() == 0) return false;
    index = 0;
    return true;
  }

  @Override
  public boolean last() throws SQLException {
    if(data == null || data.size() == 0) return false;
    index = data.size() - 1;
    if(index >= 0) return true;
    return false;
  }

  @Override
  public int getRow() throws SQLException {
    return index + 1;
  }

  @Override
  public boolean absolute(int row) throws SQLException {
    return false;
  }

  @Override
  public boolean relative(int rows) throws SQLException {
    return false;
  }

  @Override
  public boolean previous() throws SQLException {
    if(data == null || data.size() == 0) return false;
    if(index <= 0) return false;
    index--;
    return true;
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return 0;
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {
  }

  @Override
  public int getFetchSize() throws SQLException {
    return 0;
  }

  @Override
  public int getType() throws SQLException {
    return 0;
  }

  @Override
  public int getConcurrency() throws SQLException {
    return 0;
  }

  @Override
  public boolean rowUpdated() throws SQLException {
    return false;
  }

  @Override
  public boolean rowInserted() throws SQLException {
    return false;
  }

  @Override
  public boolean rowDeleted() throws SQLException {
    return false;
  }

  @Override
  public void updateNull(int columnIndex) throws SQLException {
  }

  @Override
  public void updateBoolean(int columnIndex, boolean x) throws SQLException {
  }

  @Override
  public void updateByte(int columnIndex, byte x) throws SQLException {
  }

  @Override
  public void updateShort(int columnIndex, short x) throws SQLException {
  }

  @Override
  public void updateInt(int columnIndex, int x) throws SQLException {
  }

  @Override
  public void updateLong(int columnIndex, long x) throws SQLException {
  }

  @Override
  public void updateFloat(int columnIndex, float x) throws SQLException {
  }

  @Override
  public void updateDouble(int columnIndex, double x) throws SQLException {
  }

  @Override
  public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
  }

  @Override
  public void updateString(int columnIndex, String x) throws SQLException {
  }

  @Override
  public void updateBytes(int columnIndex, byte[] x) throws SQLException {
  }

  @Override
  public void updateDate(int columnIndex, Date x) throws SQLException {
  }

  @Override
  public void updateTime(int columnIndex, Time x) throws SQLException {
  }

  @Override
  public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
  }

  @Override
  public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
  }

  @Override
  public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
  }

  @Override
  public void updateObject(int columnIndex, Object x) throws SQLException {
  }

  @Override
  public void updateNull(String columnLabel) throws SQLException {
  }

  @Override
  public void updateBoolean(String columnLabel, boolean x) throws SQLException {
  }

  @Override
  public void updateByte(String columnLabel, byte x) throws SQLException {
  }

  @Override
  public void updateShort(String columnLabel, short x) throws SQLException {
  }

  @Override
  public void updateInt(String columnLabel, int x) throws SQLException {
  }

  @Override
  public void updateLong(String columnLabel, long x) throws SQLException {
  }

  @Override
  public void updateFloat(String columnLabel, float x) throws SQLException {
  }

  @Override
  public void updateDouble(String columnLabel, double x) throws SQLException {
  }

  @Override
  public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
  }

  @Override
  public void updateString(String columnLabel, String x) throws SQLException {
  }

  @Override
  public void updateBytes(String columnLabel, byte[] x) throws SQLException {
  }

  @Override
  public void updateDate(String columnLabel, Date x) throws SQLException {
  }

  @Override
  public void updateTime(String columnLabel, Time x) throws SQLException {
  }

  @Override
  public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
  }

  @Override
  public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
  }

  @Override
  public void updateObject(String columnLabel, Object x) throws SQLException {
  }

  @Override
  public void insertRow() throws SQLException {
  }

  @Override
  public void updateRow() throws SQLException {
  }

  @Override
  public void deleteRow() throws SQLException {
  }

  @Override
  public void refreshRow() throws SQLException {
  }

  @Override
  public void cancelRowUpdates() throws SQLException {
  }

  @Override
  public void moveToInsertRow() throws SQLException {
  }

  @Override
  public void moveToCurrentRow() throws SQLException {
  }

  @Override
  public Statement getStatement() throws SQLException {
    return stm;
  }

  @Override
  public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    return record.get(i);
  }

  @Override
  public Ref getRef(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    Object value = record.get(i);
    if(value instanceof Ref) {
      return (Ref) value;
    }
    return null;
  }

  @Override
  public Blob getBlob(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    Object value = record.get(i);
    if(value instanceof Blob) {
      return (Blob) value;
    }
    return null;
  }

  @Override
  public Clob getClob(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    Object value = record.get(i);
    if(value instanceof Clob) {
      return (Clob) value;
    }
    return null;
  }

  @Override
  public Array getArray(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    Object value = record.get(i);
    if(value instanceof Array) {
      return (Array) value;
    }
    return null;
  }

  @Override
  public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getObject(colIdx.intValue());
  }

  @Override
  public Ref getRef(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getRef(colIdx.intValue());
  }

  @Override
  public Blob getBlob(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getBlob(colIdx.intValue());
  }

  @Override
  public Clob getClob(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getClob(colIdx.intValue());
  }

  @Override
  public Array getArray(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getArray(colIdx.intValue());
  }

  @Override
  public Date getDate(int columnIndex, Calendar cal) throws SQLException {
    return getDate(columnIndex);
  }

  @Override
  public Date getDate(String columnLabel, Calendar cal) throws SQLException {
    return getDate(columnLabel);
  }

  @Override
  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    return getTime(columnIndex);
  }

  @Override
  public Time getTime(String columnLabel, Calendar cal) throws SQLException {
    return getTime(columnLabel);
  }

  @Override
  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
    return getTimestamp(columnIndex);
  }

  @Override
  public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
    return getTimestamp(columnLabel);
  }

  @Override
  public URL getURL(int columnIndex) throws SQLException {
    String value = getString(columnIndex);
    if(value == null || value.length() == 0) {
      return null;
    }
    try {
      return new URL(value);
    }
    catch(Exception ex) {
      return null;
    }
  }

  @Override
  public URL getURL(String columnLabel) throws SQLException {
    String value = getString(columnLabel);
    if(value == null || value.length() == 0) {
      return null;
    }
    try {
      return new URL(value);
    }
    catch(Exception ex) {
      return null;
    }
  }

  @Override
  public void updateRef(int columnIndex, Ref x) throws SQLException {
  }

  @Override
  public void updateRef(String columnLabel, Ref x) throws SQLException {
  }

  @Override
  public void updateBlob(int columnIndex, Blob x) throws SQLException {
  }

  @Override
  public void updateBlob(String columnLabel, Blob x) throws SQLException {
  }

  @Override
  public void updateClob(int columnIndex, Clob x) throws SQLException {
  }

  @Override
  public void updateClob(String columnLabel, Clob x) throws SQLException {
  }

  @Override
  public void updateArray(int columnIndex, Array x) throws SQLException {
  }

  @Override
  public void updateArray(String columnLabel, Array x) throws SQLException {
  }

  @Override
  public RowId getRowId(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    Object value = record.get(i);
    if(value instanceof RowId) {
      return (RowId) value;
    }
    return null;
  }

  @Override
  public RowId getRowId(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getRowId(colIdx.intValue());
  }

  @Override
  public void updateRowId(int columnIndex, RowId x) throws SQLException {
  }

  @Override
  public void updateRowId(String columnLabel, RowId x) throws SQLException {
  }

  @Override
  public int getHoldability() throws SQLException {
    return 0;
  }

  @Override
  public boolean isClosed() throws SQLException {
    return closed;
  }

  @Override
  public void updateNString(int columnIndex, String nString) throws SQLException {
  }

  @Override
  public void updateNString(String columnLabel, String nString) throws SQLException {
  }

  @Override
  public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
  }

  @Override
  public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
  }

  @Override
  public NClob getNClob(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    Object value = record.get(i);
    if(value instanceof NClob) {
      return (NClob) value;
    }
    return null;
  }

  @Override
  public NClob getNClob(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getNClob(colIdx.intValue());
  }

  @Override
  public SQLXML getSQLXML(int columnIndex) throws SQLException {
    if(index < 0 || index >= data.size()) {
      throw new SQLException("Res index=" + index + ",data.size=" + data.size());
    }
    List<Object> record = data.get(index);
    int i = columnIndex - 1;
    if(i < 0 || i >= record.size()) {
      throw new SQLException("Col index=" + i + ",record.size=" + record.size());
    }
    Object value = record.get(i);
    if(value instanceof SQLXML) {
      return (SQLXML) value;
    }
    return null;
  }

  @Override
  public SQLXML getSQLXML(String columnLabel) throws SQLException {
    if(columnLabel == null) throw new SQLException("columnLabel is null");
    Integer colIdx = fields.get(columnLabel.toUpperCase());
    if(colIdx == null) throw new SQLException("column " + columnLabel + " not found");
    return getSQLXML(colIdx.intValue());
  }

  @Override
  public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
  }

  @Override
  public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
  }

  @Override
  public String getNString(int columnIndex) throws SQLException {
    return getString(columnIndex);
  }

  @Override
  public String getNString(String columnLabel) throws SQLException {
    return getString(columnLabel);
  }

  @Override
  public Reader getNCharacterStream(int columnIndex) throws SQLException {
    return getCharacterStream(columnIndex);
  }

  @Override
  public Reader getNCharacterStream(String columnLabel) throws SQLException {
    return getCharacterStream(columnLabel);
  }

  @Override
  public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
  }

  @Override
  public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
  }

  @Override
  public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
  }

  @Override
  public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
  }

  @Override
  public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
  }

  @Override
  public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
  }

  @Override
  public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
  }

  @Override
  public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
  }

  @Override
  public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
  }

  @Override
  public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
  }

  @Override
  public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
  }

  @Override
  public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
  }

  @Override
  public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
  }

  @Override
  public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
  }

  @Override
  public void updateClob(int columnIndex, Reader reader) throws SQLException {
  }

  @Override
  public void updateClob(String columnLabel, Reader reader) throws SQLException {
  }

  @Override
  public void updateNClob(int columnIndex, Reader reader) throws SQLException {
  }

  @Override
  public void updateNClob(String columnLabel, Reader reader) throws SQLException {
  }

  @Override
  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    Object value = getObject(columnIndex);
    if (type.isInstance(value)) {
      return type.cast(value);
    }
    return null;
  }

  @Override
  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    Object value = getObject(columnLabel);
    if (type.isInstance(value)) {
      return type.cast(value);
    }
    return null;
  }
}
