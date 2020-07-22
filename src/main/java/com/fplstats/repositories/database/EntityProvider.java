package com.fplstats.repositories.database;

import com.fplstats.common.dto.adapter.FplJsonObject;
import com.fplstats.common.dto.adapter.UnderstatGameJsonObject;
import com.fplstats.common.dto.adapter.UnderstatPlayerJsonObject;
import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.database.models.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.Iterator;
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
                manager.createQuery("SELECT g.id from UnderstatGame g where seasonid = ?1")
                .setParameter(1, seasonId)
                .getResultList();

        result.Success = true;

        return  result;
    }

    public Result<String> saveFplPlayers(List<FplJsonObject> fplJsonObject) {

        Iterator it = fplJsonObject.iterator();
        FplJsonObject next;
        FplPlayer player;

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Result<String> result = new Result<>();
        result.Success = true;

        while (it.hasNext()){
            next = (FplJsonObject) it.next();

            manager.getTransaction().begin();

            player = manager.find(FplPlayer.class,next.getId());

            if (player == null){
                player = new FplPlayer();
                player.setFplId(next.getId());
            }

            player.setBonus(next.getBonus());
            player.setBps(next.getBps());
            player.setElementType(next.getElement_type());
            player.setFirstName(next.getFirst_name());
            player.setNowCost(next.getNow_cost());
            player.setSaves(next.getSaves());
            player.setSecondName(next.getSecond_name());
            player.setSelectedByPercent(next.getSelected_by_percent());


            manager.persist(player);
            manager.getTransaction().commit();

        }
        manager.close();

        return result;
    }

    public Result<String> saveUnderstatPlayers(String leagueName, int year, List<UnderstatPlayerJsonObject> understatPlayerJsonObjectList) {

        Result<String> result = new Result<>();
        result.Success = true;

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        int seasonId = (Integer) manager.createQuery("select S.id from Season As S where league.name = ?1"
                + " and startYear = ?2")
                .setParameter(1, leagueName)
                .setParameter(2, year).getSingleResult();

        Iterator it = understatPlayerJsonObjectList.iterator();
        UnderstatPlayerJsonObject next;
        UnderstatPlayer player;

        while (it.hasNext()){

            next = (UnderstatPlayerJsonObject) it.next();

            manager.getTransaction().begin();

            player = manager.find(UnderstatPlayer.class, next.getId());

            if (player == null){
                player = new UnderstatPlayer();
                player.setUnderstatId(next.getId());
            }

            player.setPlayerName(next.getPlayer_name());
            player.setSeasonId(seasonId);
            player.setTeamTitle(next.getTeam_title());

            manager.persist(player);
            manager.getTransaction().commit();
        }

        return result;
    }

    public Result<String> saveUnderstatGames(String leagueName, int year, List<UnderstatGameJsonObject> understatGameJsonObjectList) {
        Result<String> result = new Result<>();
        result.Success = true;

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        int seasonId = (Integer) manager.createQuery("select S.id from Season As S where league.name = ?1"
                + " and startYear = ?2")
                .setParameter(1, leagueName)
                .setParameter(2, year).getSingleResult();

        Iterator it = understatGameJsonObjectList.iterator();
        UnderstatGameJsonObject next;
        UnderstatGame game;

        while (it.hasNext()){

            next = (UnderstatGameJsonObject) it.next();

            manager.getTransaction().begin();

            game = manager.find(UnderstatGame.class, next.getId());

            if (game == null){
                game = new UnderstatGame();
                game.setUnderstatId(next.getId());
            }

            game.setHomeTeamName(next.getHome_team());
            game.setAwayTeamName(next.getAway_team());
            game.setSeasonId(seasonId);
            game.setResult(next.isResult());

            manager.persist(game);
            manager.getTransaction().commit();
        }

        return result;
    }
}
