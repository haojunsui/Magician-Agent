import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.swing.JComboBox;

/**
 *
 * @author suihaojun
 */
public class Booking
{
    public static void deleteCustomerBooking(Connection connection, String c, String h) throws SQLException
    {
        PreparedStatement deleteCustomerBooking = connection.prepareStatement("DELETE FROM Booking WHERE CustomerName = ? AND HolidayName = ?");
        deleteCustomerBooking.setString(1, c);
        deleteCustomerBooking.setString(2, h);
        deleteCustomerBooking.executeUpdate();
    }
    
    public static void deleteMagicianBooking(Connection c, String m) throws SQLException
    {
        PreparedStatement deleteMagicianBooking = c.prepareStatement("DELETE FROM Booking WHERE MagicianName = ?");
        deleteMagicianBooking.setString(1, m);
        deleteMagicianBooking.executeUpdate();
    }
    
    public static void getCustomerHoliday(Connection connection, String c, JComboBox m) throws SQLException
    {
        PreparedStatement findCustomerHoliday = connection.prepareStatement("SELECT HolidayName FROM Booking WHERE CustomerName = ? UNION SELECT HolidayName FROM WaitingList WHERE CustomerName = ?");
        findCustomerHoliday.setString(1, c);
        findCustomerHoliday.setString(2, c);
        ResultSet resultSet = findCustomerHoliday.executeQuery();
        m.removeAllItems();
        while (resultSet.next())
            m.addItem(resultSet.getString("HolidayName"));
    }
    
    public static void getAllCustomer(Statement s, JComboBox m) throws SQLException
    {
        String SELECT_CUSTOMER = "SELECT DISTINCT CustomerName FROM Booking UNION SELECT DISTINCT CustomerName FROM WaitingList";
        ResultSet resultSet = s.executeQuery(SELECT_CUSTOMER);
        m.removeAllItems();
        while (resultSet.next())
            m.addItem(resultSet.getString("CustomerName"));
    }
    
    public static ResultSet getHolidayBooking(Connection c, String h) throws SQLException
    {
        PreparedStatement findHolidayBooking = c.prepareStatement("SELECT CustomerName, MagicianName, Timestamp FROM Booking WHERE HolidayName = ?");
        findHolidayBooking.setString(1, h);
        return findHolidayBooking.executeQuery();
    }
    
    public static ResultSet getMagicianBooking(Connection c, String m) throws SQLException
    {
        PreparedStatement findMagicianBooking = c.prepareStatement("SELECT HolidayName, CustomerName, Timestamp FROM Booking WHERE MagicianName = ?");
        findMagicianBooking.setString(1, m);
        return findMagicianBooking.executeQuery();
    }
    
    public static ResultSet getCustomerBooking(Connection connection, String c, String h) throws SQLException
    {
        PreparedStatement findCustomerBooking = connection.prepareStatement("SELECT MagicianName FROM Booking WHERE CustomerName = ? AND HolidayName = ?");
        findCustomerBooking.setString(1, c);
        findCustomerBooking.setString(2, h);
        return findCustomerBooking.executeQuery();
    }
    
    public static void addBooking(Connection connection, String c, String h, String m, Timestamp t) throws SQLException
    {
        PreparedStatement addBooking = connection.prepareStatement("INSERT INTO Booking (CustomerName, HolidayName, MagicianName, Timestamp) VALUES (?, ?, ?, ?)");
        addBooking.setString(1, c);
        addBooking.setString(2, h);
        addBooking.setString(3, m);
        addBooking.setTimestamp(4, t);
        addBooking.executeUpdate();
    }
}
