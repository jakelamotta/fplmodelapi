package com.fplstats.repositories.database;

import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseUtility {

    private static EntityManagerFactory entityManagerFactory;
    private static SessionFactory sessionFactory;

    public static EntityManagerFactory getEntityManagerFactory(){


        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.fpldata.jpa");
        }


        return entityManagerFactory;
    }
}
