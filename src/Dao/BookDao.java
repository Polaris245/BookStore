package Dao;

import pojo.Book;
import pojo.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao
{
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    /**
     *
     * @param book
     * @return 0表示失败，1表示成功
     */
    public int insert(Book book)
    {
        try
        {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement("insert into book(name,author,price,sales,stock,img_path) values(?,?,?,?,?,?)");
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setBigDecimal(3, book.getPrice());
            statement.setInt(4,book.getSales());
            statement.setInt(5,book.getStock());
            statement.setString(6,book.getImg_path());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection,statement,null);
        }
        return 0;
    }

    /**
     *
     * @param id
     * @return 0表示失败，1表示成功
     */
    public int delete(Integer id)
    {
        try
        {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement("delete from book where id = ?");
            statement.setInt(1,id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection,statement,null);
        }
        return 0;
    }

    public void update(Book book)
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
            statement = connection.prepareStatement("update book set name = ?,author = ?,price = ?,sales = ?,stock = ?,img_path = ? where id = ?");
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setBigDecimal(3,book.getPrice());
            statement.setInt(4,book.getSales());
            statement.setInt(5,book.getStock());
            statement.setString(6,book.getImg_path());
            statement.setInt(7,book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param id
     * @return null表示失败，成功则返回Book对象
     */
    public Book searchById(Integer id)
    {
        try
        {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement("select * from book where id = ?");
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                return new Book(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getBigDecimal(4),resultSet.getInt(5),resultSet.getInt(6),resultSet.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection,statement,null);
        }
        return null;
    }
    public List<Book> searchAll()
    {
        List<Book> books = new ArrayList<>();
        try
        {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement("select * from book order by id ASC");
            resultSet = statement.executeQuery();
            while (resultSet.next())
                books.add(new Book(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getBigDecimal(4),resultSet.getInt(5),resultSet.getInt(6),resultSet.getString(7)));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            DBUtils.close(connection,statement,resultSet);
        }
        return books;
    }
    public Page page(Page page,int begin)
    {
        List<Book> items = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement("select * from book order by id ASC offset ? limit ?");
            statement.setInt(1, begin);
            statement.setInt(2, page.getPageSize());
            resultSet = statement.executeQuery();
            while (resultSet.next())
                items.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7)));
            page.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection, statement, resultSet);
        }
        return page;
    }
    public Page pageByPrice(Page page,double min,double max,int begin)
    {
        List<Book> items = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement("select * from book where price between ? and ? order by price ASC offset ? limit ?");
            statement.setDouble(1, min);
            statement.setDouble(2, max);
            statement.setInt(3, begin);
            statement.setInt(4, page.getPageSize());
            resultSet = statement.executeQuery();
            while (resultSet.next())
                items.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7)));
            page.setItems(items);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBUtils.close(connection, statement, resultSet);
        }
        return page;
    }
    public int pageCount(double min,double max)
    {
        try
        {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement("select count(*) from book where price between ? and ?");
            statement.setDouble(1,min);
            statement.setDouble(2,max);
            resultSet = statement.executeQuery();
            resultSet.next();
             return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection,statement,resultSet);
        }
        return 0;
    }
}
