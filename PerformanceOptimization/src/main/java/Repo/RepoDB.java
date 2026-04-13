package Repo;

import Domain.Customer;
import Domain.Employee;
import Domain.Order;
import Util.JPAUtilPoolConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class RepoDB {
    private static EntityManagerFactory emf;
    private static final Logger logger = LogManager.getLogger();

    public RepoDB(){
        emf = JPAUtilPoolConnection.getEntityManagerFactory();
    }

    public void Nplus1Lazy(){
        logger.traceEntry();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();
            List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
            for (Customer customer : customers) {
                List<Order> orders = customer.getOrders();      // N queries
                System.out.println("Customer: " + customer.getCustomer_id() + " has " + orders.size() + " orders" );
            }
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void Nplus1Eager(){
        logger.traceEntry();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();
            List<Customer> customers = em.createQuery("SELECT c FROM Customer c JOIN FETCH c.orders", Customer.class).getResultList();
            for (Customer customer : customers) {
                List<Order> orders = customer.getOrders();              // fetched in the above query
                System.out.println("Customer: " + customer.getCustomer_id() + " has " + orders.size() + " orders" );
            }
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    public static void create10000EmployeesUtil(){
        emf = JPAUtilPoolConnection.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        int batchSize = 50;
        try{
            tx.begin();
            for(int i = 1;i <= 10_000; i++){
                Employee empl = new Employee();
                empl.setDepartmentId((int)(Math.random() * 10 + 1));
                empl.setEmail("emp" + i + "@gmail.com");
                empl.setSalary((long)(100_000 - Math.random() * 80_000 + 1));

                em.persist(empl);

                if(i % batchSize == 0){
                    em.flush();
                    em.clear();
                    logger.info("Processed batch: {}", i);
                }
            }
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private void solveIndexProblems(Runnable runnable){
        long start =  System.nanoTime();

        try {
            for(int i = 1; i <= 100; i++ ){
                // simulating 100 iterations
                runnable.run();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        long end = System.nanoTime();
        double time_in_millis = (end - start) / 1_000_000.0;
        System.out.println("Time took: " + time_in_millis + " ms");
    }



    public void BenchmarkWithoutIndex(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        System.out.println("----- Benchmark without index -----\n");
        // ----------------------------------- //
        System.out.println("Search based on email");
        solveIndexProblems(() -> {
            tx.begin();
            List<Employee> employees = em.createQuery("select e from Employee e where e.email like 'emp2573@gmail.com'", Employee.class).getResultList();
            tx.commit();
        });


        // ----------------------------------- //
        System.out.println("Search based on department id");
        solveIndexProblems(() -> {
            tx.begin();
            List<Employee> employees = em.createQuery("select e from Employee e where e.department_id = 5", Employee.class).getResultList();
            tx.commit();
        });


        // ----------------------------------- //
        System.out.println("Search based on salary interval");
        solveIndexProblems(() -> {
            tx.begin();
            List<Employee> employees = em.createQuery("select e from Employee e where e.salary between 50000 and 80000", Employee.class).getResultList();
            tx.commit();
        });



        // ----------------------------------- //
        System.out.println("Search based on 2 columns (department_id and salary)");
        solveIndexProblems(() -> {
            tx.begin();
            List<Employee> employees = em.createQuery("select e from Employee e where e.department_id = 5 and salary > 60000", Employee.class).getResultList();
            tx.commit();
        });

    }

    public void BenchmarkIndex(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
    }

}
