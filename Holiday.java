
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author suihaojun
 */
public class Holiday
{
    public static void addHoliday(Connection c, String h) throws SQLException
    {
        PreparedStatement addHoliday = c.prepareStatement("INSERT INTO Holiday (HolidayName) VALUES (?)");
        addHoliday.setString(1, h);
        addHoliday.executeUpdate();
    }
    
    public static ResultSet getHoliday(Connection c, String h) throws SQLException
    {
        PreparedStatement getHoliday = c.prepareStatement("SELECT HolidayName FROM Holiday WHERE HolidayName = ?");
        getHoliday.setString(1, h);
        return getHoliday.executeQuery();
    }
    
    public static ResultSet getAllHolidayResultSet(Statement s) throws SQLException
    {
        String SELECT_HOLIDAY = "SELECT HolidayName FROM Holiday";
        return s.executeQuery(SELECT_HOLIDAY);
    }
    
    public static void getAllHoliday(Statement s, JComboBox h) throws SQLException
    {
        String SELECT_HOLIDAY = "SELECT HolidayName FROM Holiday";
        ResultSet resultSet = s.executeQuery(SELECT_HOLIDAY);
        h.removeAllItems();
        while (resultSet.next())
            h.addItem(resultSet.getString("HolidayName"));
    }
    
    public static void setHolidayTable(Connection connection, String h, JTable holidayTable) throws SQLException
    {
        String[] tableColumnsName = {"Customer", "Magician", "Time"};
        DefaultTableModel holidayTableModel = (DefaultTableModel) holidayTable.getModel();
        holidayTableModel.setRowCount(0);
        holidayTableModel.setColumnIdentifiers(tableColumnsName);
        
        ResultSet resultSet = Booking.getHolidayBooking(connection, h);
        
        // Loop through the ResultSet and transfer in the Model
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colNo = rsmd.getColumnCount();
        while (resultSet.next())
        {
            Object[] objects = new Object[colNo];
            for (int i = 0; i < colNo; i++)
                objects[i] = resultSet.getObject(i + 1);
            holidayTableModel.addRow(objects);
        }
        holidayTable.setModel(holidayTableModel);
    }
}
