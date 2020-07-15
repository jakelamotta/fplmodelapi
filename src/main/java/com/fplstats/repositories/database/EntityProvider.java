package com.fplstats.repositories.database;

import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.database.models.Game;
import com.fplstats.repositories.database.models.League;
import com.fplstats.repositories.database.models.Season;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;


public class EntityProvider {

    public Result<List<Integer>> getGameIds(String leagueName, int year){
        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Result<List<Integer>> result = new Result<>();

        int seasonId = (Integer) manager.createQuery("select S.id from Season As S where league.name = ?1"
            + " and startYear = ?2")
                .setParameter(1, leagueName)
                .setParameter(2, year).getSingleResult();

        result.Data =
                manager.createQuery("SELECT g.id from Game g where season.id = ?1")
                .setParameter(1, seasonId)
                .getResultList();

        result.Success = true;

        factory.close();

        return  result;
    }

}
