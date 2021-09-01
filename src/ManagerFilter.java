import pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ManagerFilter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user == null)
        {
            httpServletRequest.getRequestDispatcher("/pages/user/login.jsp").forward(servletRequest,servletResponse);
        }
        else
        {
            try {
                filterChain.doFilter(servletRequest,servletResponse);
            } catch (Exception e)
            {
                throw new RuntimeException(e); //将异常抛给TomCat处理
            }
        }
    }

    @Override
    public void destroy() {

    }
}
