package com.fplstats.repositories.database;

import com.fplstats.common.dto.adapter.*;
import com.fplstats.common.dto.fplstats.*;
import com.fplstats.common.exception.NonExistingGameException;
import com.fplstats.common.exception.NonExistingSeasonTeamPlayerException;
import com.fplstats.common.exception.NonExistingTeamException;
import com.fplstats.repositories.database.models.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.sql.Date;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

@Repository
public class EntityWriter implements IEntityWriter{

    @Bean
    public EntityWriter getEntityWriter(){
        return new EntityWriter();
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
        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return result;
    }

    public Result<String> saveUnderstatPlayers(String leagueName, int year, List<UnderstatPlayerJsonObject> understatPlayerJsonObjectList) {

        Result<String> result = new Result<>();
        result.Success = true;

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Season season = (Season) manager.createQuery("from Season As S where league.name = ?1"
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
            player.setTeamTitle(next.getTeam_title());

            manager.persist(player);

            UnderstatPlayerSeason understatPlayerSeason;

            try{
                understatPlayerSeason = (UnderstatPlayerSeason) manager.createQuery("from UnderstatPlayerSeason As U where understatPlayer.understatId = ?1"
                        + " and season.id = ?2")
                        .setParameter(1, player.getUnderstatId())
                        .setParameter(2, season.getId())
                        .getSingleResult();
            }
            catch (NoResultException e){
                understatPlayerSeason = null;
            }


            if (understatPlayerSeason == null){

                understatPlayerSeason = new UnderstatPlayerSeason();

                understatPlayerSeason.setSeason(season);
                understatPlayerSeason.setUnderstatPlayer(player);

                manager.persist(understatPlayerSeason);

            }
            manager.getTransaction().commit();
        }

        if (manager.getTransaction().isActive()){
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
            game.setDatetime(next.getDatetime());

            game.setAwayGoals(next.getAwayGoals());
            game.setHomeGoals(next.getHomeGoals());
            game.setAwayXG(next.getAwayXG());
            game.setHomeXG(next.getHomeXG());

            manager.persist(game);
            manager.getTransaction().commit();
        }

        if (manager.getTransaction().isActive()){
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
            gamePlayer.setUnderstatTeamId(next.getTeam_id());

            gamePlayer.setSeasonId(seasonId);

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

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return result;
    }

    public Result<List<PlayerDto>> savePlayers(List<PlayerDto> newFplPlayers) {

        Result<List<PlayerDto>> result = new Result<>();
        result.Success = true;

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Iterator it = newFplPlayers.iterator();
        PlayerDto next;
        Player player;
        boolean costChange = false;

        manager.getTransaction().begin();

        List<Position> positions = getPositions();

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

                costChange = player.getCost() != next.getCost();

                if (player.getFplId() == next.getFplId() &&
                    !costChange &&
                    player.getPosition().getId() == next.getPosition().getId()){
                    continue;
                }
            }
            else{
                player = new Player();
                player.setName(next.getName());
            }

            player.setPosition(getPositionById(positions, next.getPosition().getId()));
            player.setCost(next.getCost());
            player.setFplId(next.getFplId());

            manager.persist(player);

            if (costChange){
                CostHistory costHistory = new CostHistory();
                costHistory.setDate(Date.from(Instant.now()));
                costHistory.setPlayer(player);
                costHistory.setNewCost(player.getCost());
                manager.persist(costHistory);
            }
        }

        manager.getTransaction().commit();

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return result;
    }

    public void savePlayer(SeasonTeamPlayerDto stpDto) throws NoResultException {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        TeamDto teamDto = saveTeam(stpDto.getSeasonTeam().getTeam());
        SeasonTeamPlayer stp;

        manager.getTransaction().begin();

        try {
            stp = (SeasonTeamPlayer) manager
                    .createQuery("from SeasonTeamPlayer As P where player.Id = ?1 AND seasonTeam.season.id = ?2 AND seasonTeam.team.id = ?3")
                    .setParameter(1, stpDto.getPlayer().getId())
                    .setParameter(2, stpDto.getSeasonTeam().getSeason().getId())
                    .setParameter(3, teamDto.getId())
                    .getSingleResult();
        }
        catch (NoResultException e){
            stp = null;
        }

        if (stp == null){
            stp = new SeasonTeamPlayer();

            Player player = manager.find(Player.class,stpDto.getPlayer().getId());

            if (player == null){
                throw new NoResultException("Player should not be null");
            }

            Team team = manager.find(Team.class, teamDto.getId());

            if (team == null){
                throw new NoResultException("Team should not be null");
            }

            Season season = manager.find(Season.class, stpDto.getSeasonTeam().getSeason().getId());

            if (season == null){
                throw new NoResultException("Season should not be null");
            }

            SeasonTeam seasonTeam;

            try{
                seasonTeam = (SeasonTeam) manager
                        .createQuery("from SeasonTeam As S where season.Id = ?1 AND team.id = ?2")
                        .setParameter(1, season.getId())
                        .setParameter(2, team.getId())
                        .getSingleResult();
            }
            catch (NoResultException ex){
                seasonTeam = null;
            }

            if (seasonTeam == null){
                seasonTeam = new SeasonTeam();
                seasonTeam.setSeason(season);
                seasonTeam.setTeam(team);
                manager.persist(seasonTeam);
            }

            stp.setSeasonTeam(seasonTeam);
            stp.setPlayer(player);

            manager.persist(stp);
            manager.getTransaction().commit();
        }

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }
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

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }
    }

    public Result<String> saveGames(List<GameDto> data) {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        GameDto gameDto;
        Game game;
        Result<String> result = new Result<>();
        result.Success = true;

        Iterator it = data.iterator();

        manager.getTransaction().begin();

        while (it.hasNext()){

            gameDto = (GameDto) it.next();

            try {
                game = (Game) manager
                        .createQuery("from Game As G where understatid = ?1")
                        .setParameter(1, gameDto.getUnderstatid())
                        .getSingleResult();
            }
            catch (NoResultException e){
                game = null;
            }

            if (game == null){

                game = new Game();

            }

            game.setAwayTeam(DatabaseUtility.getSeasonTeamBySeasonAndName(gameDto.getAwayTeam().getTeam().getName(), gameDto.getSeason().getId()));
            game.setHomeTeam(DatabaseUtility.getSeasonTeamBySeasonAndName(gameDto.getHomeTeam().getTeam().getName(), gameDto.getSeason().getId()));
            game.setUnderstatId(gameDto.getUnderstatid());
            game.setGameDay(gameDto.getDate());
            game.setSeason(game.getHomeTeam().getSeason());

            game.setAwayGoals(gameDto.getAwayGoals());
            game.setAwayXG(gameDto.getAwayXG());
            game.setHomeGoals(gameDto.getHomeGoals());
            game.setHomeXG(gameDto.getHomeXG());

            manager.persist(game);
        }

        manager.getTransaction().commit();

        return result;
    }

    public Result<String> saveUnderstatTeams(String leagueName, int year, List<UnderstatTeamJsonObject> understatTeamJsonObjectList) {

        Result<String> result = new Result<>();
        result.Success = true;

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        int seasonId = (Integer) manager.createQuery("select S.id from Season As S where league.name = ?1"
                + " and startYear = ?2")
                .setParameter(1, leagueName)
                .setParameter(2, year).getSingleResult();

        Iterator it = understatTeamJsonObjectList.iterator();
        UnderstatTeamJsonObject next;
        UnderstatTeam team;

        while (it.hasNext()){

            next = (UnderstatTeamJsonObject) it.next();

            manager.getTransaction().begin();

            try{
                team = (UnderstatTeam) manager.createQuery("from UnderstatTeam AS T where name = ?1 AND seasonId = ?2")
                        .setParameter(1, next.getTitle())
                        .setParameter(2, seasonId).getSingleResult();

            }
            catch (NoResultException e){
                team = null;
            }

            if (team == null){
                team = new UnderstatTeam();
            }

            team.setUnderstatId(next.getId());
            team.setName(next.getTitle());
            team.setSeasonId(seasonId);

            manager.persist(team);
            manager.getTransaction().commit();
        }

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return result;

    }

    public Result<String> saveTeams(List<SeasonTeamDto> seasonTeamDtos) {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        Result<String> result = new Result<>();
        result.Success = true;

        Iterator it = seasonTeamDtos.iterator();
        SeasonTeamDto seasonTeamDto;

        while (it.hasNext()){

            seasonTeamDto = (SeasonTeamDto) it.next();

            TeamDto teamDto = saveTeam(seasonTeamDto.getTeam());

            SeasonTeam seasonTeam;

            try{
                seasonTeam = (SeasonTeam) manager
                        .createQuery("from SeasonTeam As S where season.Id = ?1 AND team.id = ?2")
                        .setParameter(1, seasonTeamDto.getSeason().getId())
                        .setParameter(2, seasonTeamDto.getTeam().getId())
                        .getSingleResult();
            }
            catch (NoResultException ex){
                seasonTeam = null;
            }

            if (seasonTeam == null){
                manager.getTransaction().begin();

                seasonTeam = new SeasonTeam();

                Season season = manager.find(Season.class, seasonTeamDto.getSeason().getId());
                Team team = manager.find(Team.class, teamDto.getId());

                seasonTeam.setSeason(season);
                seasonTeam.setTeam(team);
                manager.persist(seasonTeam);

                manager.getTransaction().commit();
            }
        }

        return result;
    }

    public Result<String> saveGameStats(List<GameStatsDto> data) throws NonExistingSeasonTeamPlayerException, NonExistingGameException, NonExistingTeamException {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        Iterator it = data.iterator();
        GameStatsDto gameStatsDto;
        GameStats gameStats;

        Result<String> result = new Result<>();
        result.Success = true;
        manager.getTransaction().begin();

        while (it.hasNext()){

            gameStatsDto = (GameStatsDto) it.next();

            SeasonTeamPlayer stp = getSeasonTeamPlayerByParams(gameStatsDto.getSeasonTeamPlayer().getPlayer().getId(),
                    gameStatsDto.getSeasonTeamPlayer().getSeasonTeam().getSeason().getId(),
                    gameStatsDto.getSeasonTeamPlayer().getSeasonTeam().getTeam().getId());

            //TODO: Kolla om stats finns
            try{
                gameStats =  (GameStats) manager.createQuery("from GameStats as Gs where game.id = ?1 AND seasonTeamPlayer.id = ?2")
                        .setParameter(1, gameStatsDto.getGame().getId())
                        .setParameter(2, stp.getId())
                        .getSingleResult();
            }
            catch (NoResultException e){
                gameStats = null;
            }

            if (gameStats == null){
                gameStats = new GameStats();
                gameStats.setSeasonTeamPlayer(stp);
                gameStats.setGame(getGameById(gameStatsDto.getGame().getId()));
            }

            gameStats.setAssists(gameStatsDto.getAssists());
            gameStats.setGoals(gameStatsDto.getGoals());
            gameStats.setMinutesPlayed(gameStatsDto.getMinutesPlayed());
            gameStats.setRedCards(gameStatsDto.getRedCards());
            gameStats.setSaves(gameStatsDto.getSaves());
            gameStats.setShots(gameStatsDto.getShots());
            gameStats.setxA(gameStatsDto.getxA());
            gameStats.setxG(gameStatsDto.getxG());
            gameStats.setxGBuildup(gameStatsDto.getxGBuildup());
            gameStats.setxGChain(gameStatsDto.getxGChain());
            gameStats.setYellowCards(gameStatsDto.getYellowCards());
            manager.persist(gameStats);
        }

        manager.getTransaction().commit();

        return result;
    }

    private TeamDto saveTeam(TeamDto teamDto) {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        Team team;

        try {
            team = (Team) manager
                    .createQuery("from Team As T where name = ?1")
                    .setParameter(1, teamDto.getName())
                    .getSingleResult();
        }
        catch (NoResultException e){
            team = null;
        }

        manager.getTransaction().begin();

        if (team == null){
            team = new Team();
            team.setName(teamDto.getName());
        }

        team.setUnderstatid(teamDto.getUnderstatId());

        manager.persist(team);
        manager.getTransaction().commit();

        teamDto.setId(team.getId());

        if (manager.getTransaction().isActive()){
            manager.getTransaction().commit();
        }

        return teamDto;
    }

    private Game getGameById(int id) throws NonExistingGameException {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        Game game;

        try{
            game =  (Game) manager.createQuery("from Game as G where id = ?1")
                    .setParameter(1, id)
                    .getSingleResult();
        }
        catch (NoResultException e){
            throw new NonExistingGameException("Could not find game with id " + id);
        }

        return game;

    }

    private SeasonTeamPlayer getSeasonTeamPlayerByParams(int playerId, int seasonId, int teamId) throws NonExistingTeamException {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        SeasonTeamPlayer seasonTeamPlayer;

        try{
            seasonTeamPlayer =  (SeasonTeamPlayer) manager.createQuery("from SeasonTeamPlayer as stp where player.id = ?1 AND seasonTeam.team.id = ?2 AND seasonTeam.season.id = ?3")
                    .setParameter(1, playerId)
                    .setParameter(2, teamId)
                    .setParameter(3, seasonId)
                    .getSingleResult();
        }
        catch (NoResultException e){
            seasonTeamPlayer = null;
        }

        if (seasonTeamPlayer == null){
            seasonTeamPlayer = saveSeasonTeamPlayer(playerId, teamId, seasonId);
        }

        return seasonTeamPlayer;

    }

    private SeasonTeamPlayer saveSeasonTeamPlayer(int playerId, int teamId, int seasonId) throws NonExistingTeamException {
        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();

        SeasonTeamPlayer seasonTeamPlayer = new SeasonTeamPlayer();

        Player player = manager.find(Player.class, playerId);

        seasonTeamPlayer.setPlayer(player);

        SeasonTeam seasonTeam;

        try{
            seasonTeam = (SeasonTeam) manager.createQuery("from SeasonTeam as stp where team.id = ?1 AND season.id = ?2")
                    .setParameter(1, teamId)
                    .setParameter(2, seasonId)
                    .getSingleResult();
        }
        catch (NoResultException e){
            seasonTeam = null;
        }

        if (seasonTeam == null){
            throw new NonExistingTeamException("Seasonteam doesnt exist. Teamid: " +teamId + " seasonId: " + seasonId);
        }

        seasonTeamPlayer.setSeasonTeam(seasonTeam);
        manager.persist(seasonTeamPlayer);

        manager.getTransaction().commit();

        return seasonTeamPlayer;
    }

    private Position getPositionById(List<Position> positions, int id) {

        Iterator it = positions.iterator();
        Position position;

        while (it.hasNext()){

            position = (Position) it.next();

            if (position.getId() == id){
                return position;
            }
        }

        throw new NullPointerException("Position can't be null");
    }

    private List<Position> getPositions() {

        EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();

        List<Position> positions  =
                manager.createQuery("from Position"
                ).getResultList();

        return positions;
    }
}
