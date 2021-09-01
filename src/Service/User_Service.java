package Service;

import pojo.User;

public interface User_Service
{
    void regist(String username,String password,String email);

    User login(String username,String password);

    boolean existUser(String username);
}
