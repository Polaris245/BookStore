import Service.User_Service;
import Service.impl.User_Service_impl;
import com.google.gson.Gson;
import pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class User_Servlet extends Servlet
{
    private User_Service user_service = new User_Service_impl();

    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = user_service.login(username,password);
        if (user != null)
        {
            req.getSession().setAttribute("user",user);
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }
        else
        {
            req.setAttribute("msg","用户名或密码错误");
            req.setAttribute("username",username);
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }
    }
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY); //谷歌验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        if (token != null && token.equals(code))
            if (user_service.existUser(username))
            {
                req.setAttribute("msg","用户名已存在");
                req.setAttribute("username",username);
                req.setAttribute("email",email);
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }
            else
            {
                    user_service.regist(username,password,email);
                    User user = user_service.login(username,password);
                    req.getSession().setAttribute("user",user);
                    req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
            }
    }
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath());
    }
    public void existUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        boolean exist = user_service.existUser(username);
        Map<String,Object> result = new HashMap<>();
        result.put("exist",exist);
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(result));
    }
}
