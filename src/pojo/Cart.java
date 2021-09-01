package pojo;

import java.math.BigDecimal;
import java.util.*;

public class Cart
{
    private BigDecimal totalPrice;
    private Map<Integer,CartItem> items = new LinkedHashMap<Integer,CartItem>();

    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer,CartItem> entry : items.entrySet())
            totalCount += entry.getValue().getCount();
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Integer,CartItem> entry : items.entrySet())
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        return totalPrice;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public Cart( Map<Integer, CartItem> items) {
//        this.totalCount = totalCount;
//        this.totalPrice = totalPrice;
        this.items = items;
    }
    public Cart() {
    }

    public void add(CartItem cartItem)
    {
        CartItem item = items.get(cartItem.getId());
        if (item == null)
            items.put(cartItem.getId(),cartItem);
        else {
            item.setCount(item.getCount()+1);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }
    public void delete(Integer id)
    {
        items.remove(id);
    }
    public void clear()
    {
        items.clear();
    }
    public void updateCount(Integer id, Integer count)
    {
        CartItem cartItem = items.get(id);
        cartItem.setCount(count);
        cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
    }
}
