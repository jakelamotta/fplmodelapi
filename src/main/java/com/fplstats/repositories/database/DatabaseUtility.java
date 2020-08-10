package com.fplstats.repositories.database;

import com.fplstats.repositories.database.models.SeasonTeam;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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

    public static SeasonTeam getSeasonTeamBySeasonAndName(String homeTeamName, int seasonId) {
        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        try{
            return (SeasonTeam) manager.createQuery("from SeasonTeam AS st where st.season.id = ?1 AND st.team.name = ?2")
                    .setParameter(1, seasonId)
                    .setParameter(2,homeTeamName)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
