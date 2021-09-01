package Dao;

import pojo.Order;

import java.sql.*;

public class OrderDao
{
    public int insert(Order order)
    {
        try
        {
            Connection connection = DBUtils.conn.get();
            if (connection == null)
            {
                connection = DBUtils.getConnection();
                DBUtils.conn.set(connection);
                connection.setAutoCommit(false);
            }
            PreparedStatement statement = connection.prepareStatement("insert into order_info values (?,?,?,?,?)");
            statement.setString(1,order.getOrderId());
            statement.setTimestamp(2, (Timestamp) order.getCreateTime());
            statement.setBigDecimal(3,order.getPrice());
            statement.setInt(4,order.getStatus());
            statement.setString(5,order.getUserId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
