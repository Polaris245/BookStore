package Service.impl;

import Dao.BookDao;
import Dao.DBUtils;
import Service.Book_Service;
import pojo.Book;
import pojo.Page;

import java.util.List;

public class Book_Service_impl implements Book_Service
{
    private BookDao bookDao = new BookDao();
    @Override
    public void add(Book book)
    {
        bookDao.insert(book);
    }

    @Override
    public void delete(Integer id)
    {
        bookDao.delete(id);
    }

    @Override
    public void update(Book book)
    {
        bookDao.update(book);
        DBUtils.commitAndClose();
    }

    @Override
    public Book searchById(Integer id)
    {
        return bookDao.searchById(id);
    }

    @Override
    public List<Book> searchAll()
    {
        return bookDao.searchAll();
    }

    @Override
    public Page page(int pageNo, int pageSize)
    {
        Integer pageTotalCount, pageTotal, begin;
        Page page = new Page();
        page.setPageSize(pageSize);
        pageTotalCount = bookDao.pageCount(0, Double.MAX_VALUE);
        page.setPageTotalCount(pageTotalCount);
        pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0)
            pageTotal += 1;
        page.setPageTotal(pageTotal);
        if (pageNo > pageTotal)
            pageNo = pageTotal;
        page.setPageNo(pageNo);
        begin = (pageNo - 1) * pageSize;
        return bookDao.page(page, begin);
    }

    @Override
    public Page pageByPrice(int pageNo, int pageSize, double min, double max)
    {
        Integer pageTotalCount, pageTotal, begin;
        Page page = new Page();
        page.setPageSize(pageSize);
        pageTotalCount = bookDao.pageCount(min, max);
        page.setPageTotalCount(pageTotalCount);
        pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0)
            pageTotal += 1;
        page.setPageTotal(pageTotal);
        if (pageNo > pageTotal)
            pageNo = pageTotal;
        page.setPageNo(pageNo);
        begin = (pageNo - 1) * pageSize;
        return bookDao.pageByPrice(page,min,max,begin);
    }
}
