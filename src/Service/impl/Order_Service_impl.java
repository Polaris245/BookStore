package Service.impl;

import Dao.OrderDao;
import Dao.OrderItemDao;
import Service.Book_Service;
import Service.Order_Service;
import pojo.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class Order_Service_impl implements Order_Service
{
    private Book_Service book_service = new Book_Service_impl();
    private OrderDao orderDao = new OrderDao();
    private OrderItemDao orderItemDao = new OrderItemDao();
    @Override
    public String createOrder(Cart cart, String userId)
    {
        String orderId = System.currentTimeMillis() + "" + userId;
        Timestamp date = new Timestamp(Calendar.getInstance().getTime().getTime());
        Order order = new Order(orderId, date, cart.getTotalPrice(), 0, userId);
        orderDao.insert(order);
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet())
        {
            CartItem cartItem = entry.getValue();
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            Book book = book_service.searchById(cartItem.getId());
            int num = book.getStock() - cartItem.getCount();
            if (num >= 0)
            {
                book.setSales(book.getSales() + cartItem.getCount());
                book.setStock(num);
                book_service.update(book);
                orderItemDao.insert(orderItem);
            }
            else
                return null;
        }
        cart.clear();
        return orderId;
    }
}
