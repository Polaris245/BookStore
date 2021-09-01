package Dao;

import pojo.User;

import java.sql.*;

public class UserDao
{
    private Connection connection;
    private CallableStatement callableStatement;
    private PreparedStatement statement;
    private ResultSet resultSet;
    /**
     *
     * @return true表示登录成功，false表示登陆失败
     */
    public User login(String username, String pwd)
    {
        User user = null;
        try
        {
            connection = DBUtils.getConnection();
            callableStatement = connection.prepareCall("{? = call verify_user(?,?)}");
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            callableStatement.setString(2,username);
            callableStatement.setString(3,pwd);
            callableStatement.executeUpdate();
            if (callableStatement.getBoolean(1))
            {
                user = new User();
                statement = connection.prepareStatement("select email from userinfo where username = ?");
                statement.setString(1,username);
                resultSet = statement.executeQuery();
                resultSet.next();
                user.setUsername(username);user.setPassword(pwd);user.setEmail(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection,callableStatement,null);
        }
        return user;
    }

    /**
     *
     * @return 1表示注册成功，0表示注册失败
     */
    public int insert(String username, String pwd, String email)
    {
        try
        {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement("insert into userinfo values (?,hmac(?,?,'md5'),?)");
            statement.setString(1,username);
            statement.setString(2,pwd);
            statement.setString(3,username);
            statement.setString(4,email);
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
     * @return true表示用户名已存在
     */
    public boolean existUser(String username)
    {
        try
        {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement("select username from userinfo where username = ?");
            statement.setString(1,username);
            resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection,statement,resultSet);
        }
        return false;
    }
}
