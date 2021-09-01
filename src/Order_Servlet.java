import Dao.DBUtils;
import Service.Order_Service;
import Service.impl.Order_Service_impl;
import pojo.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Order_Servlet extends Servlet
{
    private Order_Service order_service = new Order_Service_impl();
    public void createOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        User user = (User) req.getSession().getAttribute("user");
        String orderId = null;
        if (user == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            return;
        }
        String userId = user.getUsername();
       try {
           orderId = order_service.createOrder(cart, userId);
           if (orderId == null)
               DBUtils.rollbackAndClose();
           else
               DBUtils.commitAndClose();
       } catch (Exception e)
       {
           DBUtils.rollbackAndClose();
           e.printStackTrace();
           throw new RuntimeException(e);
       }
        req.getSession().setAttribute("orderId", orderId);
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
    }
}
