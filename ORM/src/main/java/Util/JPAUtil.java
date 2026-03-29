package Util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JPAUtil {
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory(){
        if (emf == null){
            emf = Persistence.createEntityManagerFactory("orm");
        }
        return emf;
    }


    public static void closeEntityManagerFactory(){
        if (emf != null && emf.isOpen()){
            emf.close();
        }
    }
}

