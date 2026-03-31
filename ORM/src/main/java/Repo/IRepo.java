package Repo;

import Domain.Customer;
import Domain.Order;

import java.sql.Date;
import java.util.List;

public interface IRepo {
    public List<Customer> GetCustomers() throws Exception;
    public List<Order> GetOrders() throws Exception;
    public void addOrder(Long selected_customer_id, Date purchase_date) throws Exception;
    public void editOrder(Long order_id, Date new_purchase_date) throws Exception;
    public void deleteOrder(Long order_id) throws Exception;
}
