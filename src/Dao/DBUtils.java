package Dao;

import java.sql.*;

public class DBUtils
{
    public static ThreadLocal<Connection> conn = new ThreadLocal<Connection>();
    public static Connection getConnection() throws SQLException
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "xxxxxx");
    }
    public static void commitAndClose()
    {
        Connection connection = conn.get();
        if (connection != null)
        {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        conn.remove();
    }
    public static void rollbackAndClose()
    {
        Connection connection = conn.get();
        if (connection != null)
        {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        conn.remove();
    }
    public static void close(Connection connection, Statement statement, ResultSet resultSet)
    {
        if (connection != null)
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
        if (statement != null)
        {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }
        if (resultSet != null)
        {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resultSet = null;
        }
    }
}
