package Repo;

import Domain.Customer;
import Domain.Order;
import Util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoApp {
    private final EntityManagerFactory emf;
    private static final Logger logger = LogManager.getLogger();

    public RepoApp(){
        emf = JPAUtil.getEntityManagerFactory();
    }

    public List<Customer> GetCustomers() throws Exception {
        logger.traceEntry();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        logger.info("Transaction created");
        try{
            tx.begin();
            List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
            tx.commit();

            return customers;
        }catch(Exception e){
            if(tx != null &&tx.isActive()){
                tx.rollback();
            }
            logger.error("Transaction failed: {}", e.getMessage());
            e.printStackTrace();
        }finally{
            em.close();
        }
        return null;
    }

//
//
//    public List<Order> GetOrders() throws Exception{
//        String sql = "SELECT * FROM Orders";
//        List<Order> orders = new ArrayList<>();
//        try (Connection conn = DbEntityManager.getConnection()) {
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//            while(rs.next()){
//                Long order_id = rs.getLong("order_id");
//                Long customer_id = rs.getLong("customer_id");
//                Timestamp purchase_date = rs.getTimestamp("purchase_date");
//                Order order = new Order();
//                orders.add(order);
//            }
//        } catch (SQLException e) {
//            throw new Exception("Nu s-au putut extrage orderele");
//        }
//
//        return orders;
//    }
//
//
//
//    public void addOrder(Long selected_customer_id, Date purchase_date) throws Exception{
//        String sql = "INSERT INTO Orders (customer_id, purchase_date) VALUES ( ?, ?)";
//
//        try (Connection conn = DbEntityManager.getConnection()) {
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setLong(1, selected_customer_id);
//            pstmt.setDate(2, new java.sql.Date(purchase_date.getTime()));
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new Exception("Nu s-a putut adauga orderul");
//        }
//    }
//
//
//
//    public void editOrder(Long order_id, Date new_purchase_date) throws Exception{
//        String sql = "UPDATE Orders SET purchase_date = ? WHERE order_id = ?";
//        try (Connection conn = DbEntityManager.getConnection()) {
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setDate(1, new java.sql.Date(new_purchase_date.getTime()));
//            pstmt.setLong(2, order_id);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new Exception("Nu s-a putut edita orderul");
//        }
//    }
//
//
//    public void deleteOrder(Long order_id) throws Exception{
//        String sql = "DELETE FROM Orders WHERE order_id = ?";
//
//        try (Connection conn = DbEntityManager.getConnection()) {
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setLong(1, order_id);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new Exception("Nu s-a putut sterge orderul");
//        }
//    }
}
