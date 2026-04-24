package Util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JPAUtilPoolConnection {
    private static EntityManagerFactory emf;
    private static HikariDataSource dataSource;

    public static EntityManagerFactory getEntityManagerFactory(){
        if(emf == null){
            Properties properties = Props.getProperties();

            HikariConfig config = new HikariConfig();
            assert properties != null;
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));
            config.setDriverClassName(properties.getProperty("db.driver"));
            config.setConnectionTimeout(1000);
            config.setMaxLifetime(10000);
            config.setMaximumPoolSize(10);              // ((core_count * 2) + effective_spindle_count)
                                                        // (2 * core_count) <- ensures that all cores are used and that the CPU
                                                        //                      is not overwhelmed with a lot of context switches.
                                                        // effective_spindle_count <- how many I/O requests can the disk handle concurrently.
                                                        //                  these threads are blocked waiting for the disk response.
            config.setMinimumIdle(5);
            config.setConnectionTestQuery("SELECT 1");

            dataSource = new HikariDataSource(config);

            Map<String, Object> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", properties.getProperty("db.url"));

            props.put("hibernate.connection.datasource", dataSource);

            emf = Persistence.createEntityManagerFactory("orm", props);

        }
        return emf;
    }

    public static HikariDataSource getDataSource() {
        if (dataSource == null) {
            getEntityManagerFactory();
        }
        return dataSource;
    }

    public static void shutdown() {
        if (emf != null && emf.isOpen()) emf.close();
        if (dataSource != null) dataSource.close();
    }
}
