package Dao;

import pojo.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderItemDao
{
    public int insert(OrderItem orderItem)
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
            PreparedStatement statement = connection.prepareStatement("insert into order_item(name,count,price,total_price,order_id) values (?,?,?,?,?)");
            statement.setString(1, orderItem.getName());
            statement.setInt(2,orderItem.getCount());
            statement.setBigDecimal(3,orderItem.getPrice());
            statement.setBigDecimal(4,orderItem.getTotalPrice());
            statement.setString(5, orderItem.getOrderId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
