package com.fplstats.repositories.database;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseUtility {

    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory(){


        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.fpldata.jpa");
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("REzuc5vyirNrT9nQpqyu");
        dataSource.setUrl("jdbc:mysql://localhost:3306/fpldata");


        return entityManagerFactory;
    }
}
