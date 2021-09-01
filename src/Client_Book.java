import Service.Book_Service;
import Service.impl.Book_Service_impl;
import pojo.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Client_Book extends Servlet
{
    private Book_Service book_service = new Book_Service_impl();

    public void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = 1,pageSize = Page.PAGE_SIZE;
        String str = req.getParameter("pageNo");
        if (str != null)
            pageNo = Integer.parseInt(str);
        str = req.getParameter("pageSize");
        if (str != null)
            pageSize = Integer.parseInt(str);
        if (pageNo < 1)
            pageNo = 1;
        Page page = book_service.page(pageNo,pageSize);
        page.setUrl("client/book?action=page");
        req.setAttribute("page",page);
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }
    public void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double a = 0,b = Double.MAX_VALUE;
        Page page;
        int pageNo = 1,pageSize = Page.PAGE_SIZE;
        String str = req.getParameter("pageNo");
        if (str != null)
            pageNo = Integer.parseInt(str);
        str = req.getParameter("pageSize");
        if (str != null)
            pageSize = Integer.parseInt(str);
        if (pageNo < 1)
            pageNo = 1;
        str = req.getParameter("min");
        if (!str.equals(""))
            a = Double.parseDouble(str);
        str = req.getParameter("max");
        if (!str.equals(""))
            b = Double.parseDouble(str);
        page = book_service.pageByPrice(pageNo,pageSize,a,b);
        page.setUrl("client/book?action=pageByPrice&min="+a+"&max="+b);
        req.setAttribute("page",page);
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }
}
