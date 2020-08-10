package com.fplstats.repositories.database;

import com.fplstats.common.dto.fplstats.*;
import com.fplstats.common.exception.NonExistingGameException;
import com.fplstats.common.exception.NonExistingTeamException;
import com.fplstats.repositories.database.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityReader{
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

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return  result;
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

            PositionDto positionDto = new PositionDto();
            positionDto.setId(player.getElementType());
            playerDto.setPosition(positionDto);

            result.Data.add(playerDto);
        }

        result.Success = result.Data.size() > 0;

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return  result;

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

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

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

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return  result;
    }

    public Result<List<MatchUnderstatPlayerDto>> getAllMatchedUnderstatPlayers() {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Result<List<MatchUnderstatPlayerDto>> result = new Result<>();

        List<UnderstatPlayer> players  =
                manager.createQuery("from MatchUnderstatPlayer"
                ).getResultList();

        Iterator it = players.iterator();
        MatchUnderstatPlayer matchedPlayer;
        result.Data = new ArrayList<>();

        while (it.hasNext()){

            matchedPlayer = (MatchUnderstatPlayer) it.next();

            MatchUnderstatPlayerDto matchUnderstatPlayerDto = new MatchUnderstatPlayerDto();

            matchUnderstatPlayerDto.setPlayerDto(new PlayerDto());
            matchUnderstatPlayerDto.getPlayerDto().setId(matchedPlayer.getPlayer().getId());

            matchUnderstatPlayerDto.setUnderstatPlayerDto(new UnderstatPlayerDto());
            matchUnderstatPlayerDto.getUnderstatPlayerDto().setId(matchedPlayer.getUnderstatPlayer().getUnderstatId());
            matchUnderstatPlayerDto.getUnderstatPlayerDto().setTeamTitle(matchedPlayer.getUnderstatPlayer().getTeamTitle());

            result.Data.add(matchUnderstatPlayerDto);
        }

        result.Success = result.Data.size() > 0;

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return  result;

    }

    public List<Integer> getSeasonsForUnderstatPlayer(int id) {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        List<UnderstatPlayerSeason> players  =
                manager.createQuery("from UnderstatPlayerSeason as U where U.understatPlayer.understatId = ?1")
                        .setParameter(1, id)
                        .getResultList();

        List<Integer> playerIds = new ArrayList<>();
        Iterator it = players.iterator();
        UnderstatPlayerSeason player;

        while (it.hasNext()){

            player = (UnderstatPlayerSeason) it.next();
            playerIds.add(player.getSeason().getId());
        }

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return playerIds;
    }

    public Result<List<GameDto>> getAllUnderstatGames() {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Result<List<GameDto>> result = new Result<>();

        List<UnderstatGame> understatGames  =
                manager.createQuery("from UnderstatGame"
                ).getResultList();

        Iterator it = understatGames.iterator();
        UnderstatGame understatGame;
        result.Data = new ArrayList<>();

        while (it.hasNext()){

            understatGame = (UnderstatGame) it.next();

            GameDto gameDto = new GameDto();

            SeasonTeam homeTeam = DatabaseUtility.getSeasonTeamBySeasonAndName(understatGame.getHomeTeamName(), understatGame.getSeasonId());

            SeasonTeam awayTeam = DatabaseUtility.getSeasonTeamBySeasonAndName(understatGame.getAwayTeamName(), understatGame.getSeasonId());
            SeasonDto seasonDto = new SeasonDto();
            seasonDto.setId(homeTeam.getSeason().getId());

            SeasonTeamDto homeTeamDto = new SeasonTeamDto();
            homeTeamDto.setSeason(seasonDto);
            homeTeamDto.setTeam(new TeamDto());
            homeTeamDto.getTeam().setName(homeTeam.getTeam().getName());
            homeTeamDto.getTeam().setId(homeTeam.getTeam().getId());

            SeasonTeamDto awayTeamDto = new SeasonTeamDto();
            awayTeamDto.setSeason(seasonDto);
            awayTeamDto.setTeam(new TeamDto());
            awayTeamDto.getTeam().setName(awayTeam.getTeam().getName());
            awayTeamDto.getTeam().setId(awayTeam.getTeam().getId());

            gameDto.setUnderstatid(understatGame.getUnderstatId());
            gameDto.setHomeTeam(homeTeamDto);
            gameDto.setAwayTeam(awayTeamDto);
            gameDto.setDate(understatGame.getDatetime());
            gameDto.setSeason(seasonDto);

            result.Data.add(gameDto);
        }

        result.Success = result.Data.size() > 0;

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return  result;
    }

    public List<GameDto> getAllGamesInSystem() {
        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        List<Game> games  = manager.createQuery("from Game AS g"
        ).getResultList();

        List<GameDto> gameDtos = new ArrayList<>();
        Game next;
        GameDto gameDto;

        Iterator it = games.iterator();

        while (it.hasNext()){

            next = (Game) it.next();

            gameDto = new GameDto();
            gameDto.setUnderstatid(next.getUnderstatId());
            gameDto.setSeason(new SeasonDto());
            gameDto.getSeason().setId(next.getSeason().getId());

            gameDto.setDate(next.getGameDay());

            gameDto.setAwayTeam(new SeasonTeamDto());
            gameDto.getAwayTeam().setTeam(new TeamDto());
            gameDto.getAwayTeam().getTeam().setName(next.getAwayTeam().getTeam().getName());

            gameDto.setHomeTeam(new SeasonTeamDto());
            gameDto.getHomeTeam().setTeam(new TeamDto());
            gameDto.getHomeTeam().getTeam().setName(next.getHomeTeam().getTeam().getName());

            gameDtos.add(gameDto);
        }

        return  gameDtos;
    }

    public Result<List<GameStatsDto>> getAllUnderstatGameStats(GameDto gameDto) throws NonExistingTeamException, NonExistingGameException {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Result<List<GameStatsDto>> result = new Result<>();

        List<GameStatsDto> understatGameStats  =
                manager.createQuery("from UnderstatGamePlayer where understatgameid = ?1"
                ).setParameter(1, gameDto.getUnderstatid())
                        .getResultList();

        Iterator it = understatGameStats.iterator();
        UnderstatGamePlayer understatGamePlayer;
        GameStatsDto gameStatsDto;

        result.Data = new ArrayList<>();

        while (it.hasNext()){

            understatGamePlayer = (UnderstatGamePlayer) it.next();
            int playerId = getPlayerIdFromUnderstatid(understatGamePlayer.getUnderstatPlayerId());
            TeamDto teamDto = getTeamFromUnderstatid(understatGamePlayer.getUnderstatTeamId());

            if (playerId == 0){
                continue;
            }

            if (teamDto == null){
                throw new NonExistingTeamException("Could not find team with id " + teamDto.getUnderstatId());
            }

            gameStatsDto = new GameStatsDto();

            gameStatsDto.setAssists(understatGamePlayer.getAssists());
            gameStatsDto.setGame(new GameDto());
            gameStatsDto.getGame().setUnderstatid(understatGamePlayer.getUnderstatGameId());
            gameStatsDto.getGame().setId(getGameByUnderstatId(understatGamePlayer.getUnderstatGameId()).getId());

            gameStatsDto.setGoals(understatGamePlayer.getGoals());
            gameStatsDto.setMinutesPlayed(understatGamePlayer.getTime());
            gameStatsDto.setRedCards(understatGamePlayer.getRedCard());
            gameStatsDto.setShots(understatGamePlayer.getShots());

            gameStatsDto.setSeasonTeamPlayer(new SeasonTeamPlayerDto());
            gameStatsDto.getSeasonTeamPlayer().setPlayer(new PlayerDto());
            gameStatsDto.getSeasonTeamPlayer().getPlayer().setId(playerId);

            gameStatsDto.getSeasonTeamPlayer().setSeasonTeam(new SeasonTeamDto());
            gameStatsDto.getSeasonTeamPlayer().getSeasonTeam().setTeam(new TeamDto());
            gameStatsDto.getSeasonTeamPlayer().getSeasonTeam().getTeam().setName(teamDto.getName());
            gameStatsDto.getSeasonTeamPlayer().getSeasonTeam().getTeam().setId(teamDto.getId());

            gameStatsDto.getSeasonTeamPlayer().getSeasonTeam().setSeason(new SeasonDto());
            gameStatsDto.getSeasonTeamPlayer().getSeasonTeam().getSeason().setId(understatGamePlayer.getSeasonId());

            gameStatsDto.setxA(understatGamePlayer.getxA());
            gameStatsDto.setxG(understatGamePlayer.getxG());
            gameStatsDto.setxGBuildup(understatGamePlayer.getxGBuildup());
            gameStatsDto.setxGChain(understatGamePlayer.getxGChain());
            gameStatsDto.setYellowCards(understatGamePlayer.getYellowCard());

            result.Data.add(gameStatsDto);
        }

        result.Success = result.Data.size() > 0;

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return  result;

    }

    public Result<List<SeasonTeamDto>> getAllUnderstatTeams() {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Result<List<SeasonTeamDto>> result = new Result<>();

        List<UnderstatTeam> understatTeams  =
                manager.createQuery("from UnderstatTeam"
                ).getResultList();

        Iterator it = understatTeams.iterator();
        UnderstatTeam team;
        result.Data = new ArrayList<>();

        while (it.hasNext()){

            team = (UnderstatTeam) it.next();

            SeasonTeamDto seasonTeamDto = new SeasonTeamDto();
            TeamDto teamDto = new TeamDto();
            teamDto.setName(team.getName());
            teamDto.setUnderstatId(team.getUnderstatId());

            seasonTeamDto.setTeam(teamDto);

            SeasonDto seasonDto = new SeasonDto();
            seasonDto.setId(team.getSeasonId());
            seasonTeamDto.setSeason(seasonDto);

            result.Data.add(seasonTeamDto);
        }

        result.Success = result.Data.size() > 0;

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return result;

    }

    private TeamDto getTeamFromUnderstatid(int understatTeamId) {
        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Team team;

        try{
            team = (Team) manager.createQuery("from Team where understatid = ?1")
                    .setParameter(1, understatTeamId)
                    .getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }

        TeamDto teamDto = new TeamDto();

        teamDto.setName(team.getName());
        teamDto.setId(team.getId());

        return teamDto;
    }

    private int getPlayerIdFromUnderstatid(int understatPlayerId) {
        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        MatchUnderstatPlayer player;

        try{
            player =  (MatchUnderstatPlayer) manager.createQuery("from MatchUnderstatPlayer as M where understatplayer_understatid = ?1")
                    .setParameter(1, understatPlayerId)
                    .getSingleResult();
        }
        catch (NoResultException e){
            player = null;
        }

        if (player == null){
            return 0;
        }

        return player.getPlayer().getId();
    }

    private Game getGameByUnderstatId(int understatId) throws NonExistingGameException {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        Game game;

        try{
            game =  (Game) manager.createQuery("from Game as G where understatId = ?1")
                    .setParameter(1, understatId)
                    .getSingleResult();
        }
        catch (NoResultException e){
            throw new NonExistingGameException("Could not find game with understatid " + understatId);
        }

        return game;

    }
}
