
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author suihaojun
 */
public class WaitingList
{   
    public static ResultSet getHolidayWaiting(Connection c, String h) throws SQLException
    {
        PreparedStatement findHolidayWaiting = c.prepareStatement("SELECT CustomerName, Timestamp FROM WaitingList WHERE HolidayName = ? ORDER BY Timestamp ASC");
        findHolidayWaiting.setString(1, h);
        return findHolidayWaiting.executeQuery();
    }
    
    public static ResultSet getFirstCustomer(Connection c, String h) throws SQLException
    {
        PreparedStatement findFirstCustomer = c.prepareStatement("SELECT CustomerName, Timestamp FROM WaitingList WHERE HolidayName = ? ORDER BY Timestamp ASC");
        findFirstCustomer.setString(1, h);
        return findFirstCustomer.executeQuery();
    }
    
    public static void deleteCustomerWaiting(Connection connection, String c, String h) throws SQLException
    {
        PreparedStatement deleteCustomerWaiting = connection.prepareStatement("DELETE FROM WaitingList WHERE CustomerName = ? AND HolidayName = ?");
        deleteCustomerWaiting.setString(1, c);
        deleteCustomerWaiting.setString(2, h);
        deleteCustomerWaiting.executeUpdate();
    }
    
    public static void addWaiting(Connection connection, String c, String h, Timestamp t) throws SQLException
    {
        PreparedStatement addWaiting = connection.prepareStatement("INSERT INTO WaitingList (CustomerName, HolidayName, Timestamp) VALUES (?, ?, ?)");
        addWaiting.setString(1, c);
        addWaiting.setString(2, h);
        addWaiting.setTimestamp(3, t);
        addWaiting.executeUpdate();
    }
    
    public static ResultSet getCustomerWaiting(Connection connection, String c, String h) throws SQLException
    {
        PreparedStatement findCustomerWaiting = connection.prepareStatement("SELECT * FROM WaitingList WHERE CustomerName = ? AND HolidayName = ?");
        findCustomerWaiting.setString(1, c);
        findCustomerWaiting.setString(2, h);
        return findCustomerWaiting.executeQuery();
    }
    
    public static void setWaitingListTable(Statement s, JTable waitingListTable) throws SQLException
    {
        String[] tableColumnsName = {"Customer", "Holiday", "Time"};
        DefaultTableModel waitingListTableModel = (DefaultTableModel) waitingListTable.getModel();
        waitingListTableModel.setRowCount(0);
        waitingListTableModel.setColumnIdentifiers(tableColumnsName);
        
        ResultSet resultSet = s.executeQuery("SELECT CustomerName, HolidayName, Timestamp FROM WaitingList ORDER BY HolidayName ASC, CustomerName ASC, Timestamp ASC");
        
        // Loop through the ResultSet and transfer in the Model
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colNo = rsmd.getColumnCount();
        while (resultSet.next())
        {
            Object[] objects = new Object[colNo];
            // tanks to umit ozkan for the bug fix!
            for (int i = 0; i < colNo; i++)
                objects[i] = resultSet.getObject(i + 1);
            waitingListTableModel.addRow(objects);
        }
        waitingListTable.setModel(waitingListTableModel);
    }
}
