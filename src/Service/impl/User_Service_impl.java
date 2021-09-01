package Service.impl;

import Dao.UserDao;
import Service.User_Service;
import pojo.User;

public class User_Service_impl implements User_Service
{
    private UserDao userDao = new UserDao();
    @Override
    public void regist(String username,String password,String email)
    {
        userDao.insert(username,password,email);
    }

    @Override
    public User login(String username,String password)
    {
        return userDao.login(username,password);
    }

    @Override
    public boolean existUser(String username)
    {
        return userDao.existUser(username);
    }
}
