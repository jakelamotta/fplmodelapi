package com.fplstats.repositories.database;

import com.fplstats.common.dto.adapter.FplJsonObject;
import com.fplstats.common.dto.adapter.UnderstatGameJsonObject;
import com.fplstats.common.dto.adapter.UnderstatGamePlayerJsonObject;
import com.fplstats.common.dto.adapter.UnderstatPlayerJsonObject;
import com.fplstats.common.dto.fplstats.*;
import com.fplstats.repositories.database.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
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
                manager.createQuery("SELECT g.id from UnderstatGame g where seasonid = ?1"
                )
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

    public Result<String> saveUnderstatGamePlayers(String leagueName, int year, List<UnderstatGamePlayerJsonObject> understatGamePlayerJsonObjectList, int gameId) {

        Result<String> result = new Result<>();
        result.Success = true;

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        int seasonId = (Integer) manager.createQuery("select S.id from Season As S where league.name = ?1"
                + " and startYear = ?2")
                .setParameter(1, leagueName)
                .setParameter(2, year).getSingleResult();

        Iterator it = understatGamePlayerJsonObjectList.iterator();
        UnderstatGamePlayerJsonObject next;
        UnderstatGamePlayer gamePlayer;

        manager.getTransaction().begin();

        while (it.hasNext()){

            next = (UnderstatGamePlayerJsonObject) it.next();


            gamePlayer = manager.find(UnderstatGamePlayer.class, next.getId());

            if (gamePlayer != null){

                continue;
            }

            gamePlayer = new UnderstatGamePlayer();
            gamePlayer.setUnderstatId(next.getId());
            gamePlayer.setUnderstatGameId(gameId);
            gamePlayer.setAssists(next.getAssists());
            gamePlayer.setGoals(next.getGoals());
            gamePlayer.sethA(next.getH_a());
            gamePlayer.setKeyPasses(next.getKey_passes());
            gamePlayer.setRedCard(next.getRed_card());
            gamePlayer.setShots(next.getShots());
            gamePlayer.setTime(next.getTime());
            gamePlayer.setUnderstatPlayerId(next.getPlayer_id());
            gamePlayer.setxA(next.getxA());
            gamePlayer.setxG(next.getxG());
            gamePlayer.setxGBuildup(next.getxGBuildup());
            gamePlayer.setxGChain(next.getxGChain());
            gamePlayer.setYellowCard(next.getYellow_card());

            manager.persist(gamePlayer);
        }

        manager.getTransaction().commit();

        return result;
    }

    public Result<List<PlayerDto>> getAllFplPlayers() {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Result<List<PlayerDto>> result = new Result<>();

        List<FplPlayer> players  =
                manager.createQuery("from FplPlayer"
                ).getResultList();

        Iterator it = players.iterator();
        FplPlayer player;
        result.Data = new ArrayList<>();

        while (it.hasNext()){

            player = (FplPlayer) it.next();

            PlayerDto playerDto = new PlayerDto();

            playerDto.setFplId(player.getFplId());
            playerDto.setCost(player.getNowCost());
            playerDto.setName(player.getFirstName(), player.getSecondName());

            result.Data.add(playerDto);
        }

        result.Success = result.Data.size() > 0;

        return  result;

    }

    public Result<List<PlayerDto>> savePlayers(List<PlayerDto> newFplPlayers) {

        Result<List<PlayerDto>> result = new Result<>();
        result.Success = true;

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Iterator it = newFplPlayers.iterator();
        PlayerDto next;
        Player player;

        manager.getTransaction().begin();

        while (it.hasNext()){

            next = (PlayerDto) it.next();

            try {
                player = (Player) manager
                        .createQuery("from Player As P where name = ?1")
                        .setParameter(1, next.getName())
                        .getSingleResult();
            }
            catch (NoResultException e){
                player = null;
            }

            if (player != null){

                if (player.getFplId() == next.getFplId() &&
                    player.getCost() == next.getCost()){
                    continue;
                }
            }
            else{
                player = new Player();
                player.setName(next.getName());
            }
            player.setCost(next.getCost());
            player.setFplId(next.getFplId());

            manager.persist(player);

            CostHistory costHistory = new CostHistory();
            costHistory.setDate(Date.from(Instant.now()));
            costHistory.setPlayer(player);
            costHistory.setNewCost(player.getCost());
            manager.persist(costHistory);
        }

        manager.getTransaction().commit();

        return result;
    }

    public Result<List<PlayerDto>> getAllPlayersInSystem() {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Result<List<PlayerDto>> result = new Result<>();

        List<Player> players  =
                manager.createQuery("from Player"
                ).getResultList();

        Iterator it = players.iterator();
        Player player;
        result.Data = new ArrayList<>();

        while (it.hasNext()){

            player = (Player) it.next();

            PlayerDto playerDto = new PlayerDto();

            playerDto.setFplId(player.getFplId());
            playerDto.setCost(player.getCost());
            playerDto.setName(player.getName());
            playerDto.setId(player.getId());

            result.Data.add(playerDto);
        }

        result.Success = result.Data.size() > 0;

        return  result;

    }

    public Result<List<UnderstatPlayerDto>> getAllUnderstatPlayers() {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Result<List<UnderstatPlayerDto>> result = new Result<>();

        List<UnderstatPlayer> players  =
                manager.createQuery("from UnderstatPlayer"
                ).getResultList();

        Iterator it = players.iterator();
        UnderstatPlayer player;
        result.Data = new ArrayList<>();

        while (it.hasNext()){

            player = (UnderstatPlayer) it.next();

            UnderstatPlayerDto understatPlayerDto = new UnderstatPlayerDto();
            understatPlayerDto.setId(player.getUnderstatId());
            understatPlayerDto.setName(player.getPlayerName());

            result.Data.add(understatPlayerDto);
        }

        result.Success = result.Data.size() > 0;

        return  result;
    }

    public void savePlayer(SeasonTeamPlayerDto stpDto, PlayerDto data) throws InvalidParameterException {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        TeamDto teamDto = saveTeam(stpDto.getSeasonTeam().getTeam());
        SeasonTeamPlayer stp;

        try {
            stp = (SeasonTeamPlayer) manager
                    .createQuery("from SeasonTeamPlayer As P where player.Id = ?1 AND seasonTeam.season.id = ?2 AND seasonTeam.team.id = ?3")
                    .setParameter(1, data.getId())
                    .setParameter(2, stpDto.getSeasonTeam().getSeason().getId())
                    .setParameter(3, teamDto.getId())
                    .getSingleResult();
        }
        catch (NoResultException e){

            stp = new SeasonTeamPlayer();

            Player player = manager.find(Player.class,data.getId());

            if (player == null){
                throw new InvalidParameterException("Player should not be null");
            }

            Team team = manager.find(Team.class, teamDto.getId());

            if (team == null){
                throw new InvalidParameterException("Team should not be null");
            }

            Season season = manager.find(Season.class, stp.getSeasonTeam().getSeason().getId());

            if (season == null){
                throw new InvalidParameterException("Season should not be null");
            }

            SeasonTeam seasonTeam = (SeasonTeam) manager
                    .createQuery("from SeasonTeam As S where season.Id = ?1 AND team.id = ?2")
                    .setParameter(1, stpDto.getSeasonTeam().getSeason().getId())
                    .setParameter(2, teamDto.getId())
                    .getSingleResult();

            if (seasonTeam == null){

                seasonTeam = new SeasonTeam();
                seasonTeam.setSeason(season);
                seasonTeam.setTeam(team);

            }

            stp.setSeasonTeam(seasonTeam);
            stp.setPlayer(player);

            manager.getTransaction().begin();
            manager.persist(stp);
            manager.getTransaction().commit();
        }

    }

    private TeamDto saveTeam(TeamDto team) {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        try {
            Team stp = (Team) manager
                    .createQuery("from Team As T where name = ?1")
                    .setParameter(1, team.getName())
                    .getSingleResult();
        }
        catch (NoResultException e){
            team = null;
        }

        team.setId(team.getId());

        return team;
    }

    public void saveMatch(UnderstatPlayerDto upDto, PlayerDto data) {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        MatchUnderstatPlayer matchUnderstatPlayer;

        try {
            matchUnderstatPlayer = (MatchUnderstatPlayer) manager
                    .createQuery("from MatchUnderstatPlayer As M where understatPlayer.id = ?1 AND player.id = ?2")
                    .setParameter(1, upDto.getId())
                    .setParameter(2, data.getId())
                    .getSingleResult();
        }
        catch (NoResultException e){
            matchUnderstatPlayer = null;
        }

        if (matchUnderstatPlayer == null){

            Player player = manager.find(Player.class, data.getId());
            UnderstatPlayer understatPlayer = manager.find(UnderstatPlayer.class, upDto.getId());

            matchUnderstatPlayer = new MatchUnderstatPlayer();
            matchUnderstatPlayer.setPlayer(player);
            matchUnderstatPlayer.setUnderstatPlayer(understatPlayer);

            manager.getTransaction().begin();
            manager.persist(matchUnderstatPlayer);
            manager.getTransaction().commit();
        }
    }
}
