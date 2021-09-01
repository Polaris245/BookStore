package Service;

import pojo.Book;
import pojo.Page;

import java.util.List;

public interface Book_Service
{
    void add(Book book);

    void delete(Integer id);

    void update(Book book);

    Book searchById(Integer id);

    List<Book> searchAll();

    Page page(int pageNo, int pageSize);

    Page pageByPrice(int pageNo, int pageSize, double min, double max);
}
