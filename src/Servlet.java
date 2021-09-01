import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public abstract class Servlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        doPost(req,resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/html; charset=UTF-8");//设置客户端与服务器的字符集，在获取流对象之前使用
        String action = req.getParameter("action");
        try {
            Method method = this.getClass().getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
