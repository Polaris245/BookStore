import Service.Book_Service;
import Service.impl.Book_Service_impl;
import org.apache.commons.beanutils.BeanUtils;
import pojo.Book;
import pojo.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Book_Servlet extends Servlet
{
    private Book_Service book_service = new Book_Service_impl();
    public void add(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException, IOException {
        Book book = new Book();
        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
        BeanUtils.populate(book,req.getParameterMap());
        book_service.add(book);
        resp.sendRedirect(req.getContextPath()+"/manager/book?action=page&pageNo="+pageNo+1);
    }
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        book_service.delete(id);
        resp.sendRedirect(req.getContextPath()+"/manager/book?action=page&pageNo="+req.getParameter("pageNo"));
    }
    public void update(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException, IOException {
        Book book = new Book();
        BeanUtils.populate(book,req.getParameterMap());
        book_service.update(book);
        resp.sendRedirect(req.getContextPath()+"/manager/book?action=page&pageNo="+req.getParameter("pageNo"));
    }
    public void search(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        List<Book> books = book_service.searchAll();
        req.setAttribute("books",books);
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }
    public  void searchById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = book_service.searchById(id);
        req.setAttribute("book",book);
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }
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
        page.setUrl("manager/book?action=page");
        req.setAttribute("page",page);
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }
}
