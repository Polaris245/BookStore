import Dao.BookDao;
import com.google.gson.Gson;
import pojo.Book;
import pojo.Cart;
import pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Cart_Servlet extends Servlet
{
    private BookDao bookDao = new BookDao();

    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = bookDao.searchById(id);
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null)
        {
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.add(cartItem);
        req.getSession().setAttribute("lastName",cartItem.getName());
//        resp.sendRedirect(req.getHeader("Referer"));
        Map<String,Object> result = new HashMap<>();
        result.put("lastName",cartItem.getName());
        result.put("totalCount",cart.getTotalCount());
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(result));
    }
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null)
        {
            cart.delete(id);
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }
    public void clear(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null)
        {
            cart.clear();
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int count = Integer.parseInt(req.getParameter("count"));
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null)
        {
            Gson gson = new Gson();
            Map<String, Object> result = new HashMap<>();
            PrintWriter writer = resp.getWriter();
            Book book = bookDao.searchById(id);
            if (book.getStock() >= count)
            {
                cart.updateCount(id,count);
                result.put("stockIsEnough",true);
            }
            else
            {
                result.put("stockIsEnough",false);
            }
            writer.write(gson.toJson(result));
            writer.close();
        }
    }
}
