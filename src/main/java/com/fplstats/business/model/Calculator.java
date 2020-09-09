package com.fplstats.business.model;

import com.fplstats.common.dto.fplstats.*;
import com.fplstats.common.exception.NonExistingGameException;
import com.fplstats.common.exception.NonExistingTeamException;
import com.fplstats.repositories.database.EntityReader;
import com.fplstats.repositories.database.EntityWriter;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

public class Calculator {

    private EntityWriter entityWriter;
    private EntityReader entityReader;

    private static final int DEF_GOALS_POINTS = 6;
    private static final int MID_GOALS_POINTS = 5;
    private static final int FOR_GOALS_POINTS = 4;
    private static final int ASSIST_POINTS = 3;
    private static final int DEF_CS_POINTS = 4;
    private static final int MID_CS_POINTS = 1;

    public Calculator(){

        entityWriter = new EntityWriter();
        entityReader = new EntityReader();
    }

    public String calculate() throws NonExistingGameException, NonExistingTeamException {

        LinearRegressionDto linearRegressionDto;
        Map<Integer, AggregatedPlayerStatsDto> playerDictionary = new Hashtable<>();
        Map<Integer, AggregatedTeamStatsDto> teamDictionary;
        Map<Integer, AggregatedSeasonTeamStatsDto> seasonTeamDictionary;
        GameStatsDto gameStats;

        List<GameStatsDto> gameStatsList = entityReader.getAllGamestatistics();
        List<GameDto> allGames = entityReader.getAllGamesInSystem();

        Iterator iterator = gameStatsList.iterator();

        while (iterator.hasNext()){

            gameStats = (GameStatsDto) iterator.next();
            PlayerDto playerDto = gameStats.getSeasonTeamPlayer().getPlayer();

            if (playerDictionary.get(playerDto.getId()) == null){
                AggregatedPlayerStatsDto agg = new AggregatedPlayerStatsDto();
                agg.setPlayer(playerDto);
                agg.setCurrentTeam(gameStats.getSeasonTeamPlayer().getSeasonTeam().getTeam());

                playerDictionary.put(playerDto.getId(), agg);

            }

            aggregatePlayerData(playerDictionary.get(playerDto.getId()), gameStats);
        }

        teamDictionary = createTeamDictionary(allGames);
        seasonTeamDictionary = createSeasonTeamDictionary(allGames);
        linearRegressionDto = getCsLinearRegressionModel(seasonTeamDictionary);
        List<CalculatedPlayerStatisticsDto> calcs = new ArrayList<>();
        Iterator it = playerDictionary.keySet().iterator();

        while(it.hasNext()){
            Integer element = (Integer) it.next();

            AggregatedPlayerStatsDto aggregatedPlayerStatsDto = playerDictionary.get(element);

            if (aggregatedPlayerStatsDto.getNrOfGames() > 0.0){
                //All calcs
                CalculatedPlayerStatisticsDto calculatedPlayerStatistics = addXgAndXsPoints(aggregatedPlayerStatsDto);
                addCsXp(teamDictionary.get(aggregatedPlayerStatsDto.getCurrentTeam().getId()), calculatedPlayerStatistics, linearRegressionDto);
                calcs.add(calculatedPlayerStatistics);
            }
        }

        entityWriter.saveCalculatedStatistics(calcs);

        return "Success";
    }

    private void addCsXp(AggregatedTeamStatsDto agg, CalculatedPlayerStatisticsDto calculatedPlayerStatistics, LinearRegressionDto linearRegressionDto) {

        double xCs = linearRegressionDto.getAlpha() + (agg.getxGAgainst()/agg.getNrOfGames())*linearRegressionDto.getBeta();

        if (xCs < 0.0){
            xCs = 0.0;
        }



        double xPCs = 0.0;

        switch (calculatedPlayerStatistics.getPosition().getId()){
            case 1:
                xPCs = DEF_CS_POINTS * xCs;
                break;
            case 2:
                xPCs = DEF_CS_POINTS * xCs;
                break;
            case 3:
                xPCs = MID_CS_POINTS * xCs;
                break;
            case 4:
                xPCs = 0.0 * xCs;
                break;
        }


        calculatedPlayerStatistics.incrementxPAbs(xPCs * calculatedPlayerStatistics.getNrOfGames());
        calculatedPlayerStatistics.incrementXPGame(xPCs * calculatedPlayerStatistics.getNrOfGames());
    }


    private CalculatedPlayerStatisticsDto addXgAndXsPoints(AggregatedPlayerStatsDto aggregatedPlayerStatsDto) {

        CalculatedPlayerStatisticsDto calculatedPlayerStatistics = new CalculatedPlayerStatisticsDto();

        calculatedPlayerStatistics.setMinutesPlayed(aggregatedPlayerStatsDto.getMinutesPlayed());
        calculatedPlayerStatistics.setNrOfGames(aggregatedPlayerStatsDto.getNrOfGames());
        calculatedPlayerStatistics.setCost(aggregatedPlayerStatsDto.getPlayer().getCost());
        calculatedPlayerStatistics.setPlayerName(aggregatedPlayerStatsDto.getPlayer().getName());
        calculatedPlayerStatistics.setPosition(aggregatedPlayerStatsDto.getPlayer().getPosition());

        double xPGame = 0.0;

        switch (aggregatedPlayerStatsDto.getPlayer().getPosition().getId()){

            case 1:
                xPGame = DEF_GOALS_POINTS * aggregatedPlayerStatsDto.getxG();
                break;
            case 2:
                xPGame = DEF_GOALS_POINTS * aggregatedPlayerStatsDto.getxG();
                break;
            case 3:
                xPGame = MID_GOALS_POINTS * aggregatedPlayerStatsDto.getxG();
                break;
            case 4:
                xPGame = FOR_GOALS_POINTS * aggregatedPlayerStatsDto.getxG();
                break;
        }

        xPGame += ASSIST_POINTS * aggregatedPlayerStatsDto.getxA();
        calculatedPlayerStatistics.setxPAbs(xPGame);
        calculatedPlayerStatistics.setxPPoundGame(xPGame);

        return calculatedPlayerStatistics;
    }

    private void aggregatePlayerData(AggregatedPlayerStatsDto aggregatedPlayerStatsDto, GameStatsDto gameStats) {
        aggregatedPlayerStatsDto.incrementMinutesPlayed(getPlayerGameWeight(gameStats.getGame().getDate()) * gameStats.getMinutesPlayed());
        aggregatedPlayerStatsDto.incrementxA(getPlayerGameWeight(gameStats.getGame().getDate()) * gameStats.getxA());
        aggregatedPlayerStatsDto.incrementxG(getPlayerGameWeight(gameStats.getGame().getDate()) * gameStats.getxG());
        aggregatedPlayerStatsDto.incrementNrOfGames(getPlayerGameWeight(gameStats.getGame().getDate()));
    }



    private void aggregateSeasonTeamData(AggregatedSeasonTeamStatsDto aggregatedSeasonTeamStatsDto, GameDto game) {

        if(aggregatedSeasonTeamStatsDto.getSeasonTeamDto().getId() == game.getHomeTeam().getId()){

            aggregatedSeasonTeamStatsDto.incrementXGAgainst(game.getAwayXG());

            if (game.getAwayGoals() == 0){
                aggregatedSeasonTeamStatsDto.incrementCleanSheets(1);
            }

        }
        else if (aggregatedSeasonTeamStatsDto.getSeasonTeamDto().getId() == game.getAwayTeam().getId()){

            aggregatedSeasonTeamStatsDto.incrementXGAgainst(game.getHomeXG());

            if (game.getHomeGoals() == 0){
                aggregatedSeasonTeamStatsDto.incrementCleanSheets(1);
            }

        }
        else{
            throw new IllegalArgumentException("aggregateSeasonTeamData: Team not in game!");
        }

        aggregatedSeasonTeamStatsDto.incrementNrOfGames(1);

    }

    private void aggregateTeamData(AggregatedTeamStatsDto aggregatedTeamStatsDto, GameDto game) {

        if(aggregatedTeamStatsDto.getTeamDto().getId() == game.getHomeTeam().getTeam().getId()){

            aggregatedTeamStatsDto.incrementxGAgainst(game.getAwayXG());

            if (game.getAwayGoals() == 0){
                aggregatedTeamStatsDto.incrementCleanSheets();
            }

        }
        else if (aggregatedTeamStatsDto.getTeamDto().getId() == game.getAwayTeam().getTeam().getId()){

            aggregatedTeamStatsDto.incrementxGAgainst(game.getHomeXG());

            if (game.getHomeGoals() == 0){
                aggregatedTeamStatsDto.incrementCleanSheets();
            }

        }
        else{
            throw new IllegalArgumentException("aggregateTeamData: Team not in game!");
        }

        aggregatedTeamStatsDto.incrementNrOfGames();
    }

    private double getPlayerGameWeight(Date date){

        LocalDate when = LocalDate.now().minusDays(300);

        if (date.after(Date.from(when.atStartOfDay().toInstant(ZoneOffset.UTC)))){
            return 1;
        }

        return 0;
    }

    private Map<Integer, AggregatedSeasonTeamStatsDto> createSeasonTeamDictionary(List<GameDto> games) {
        Map<Integer, AggregatedSeasonTeamStatsDto> seasonTeamMap = new Hashtable<>();

        Iterator iterator = games.iterator();

        while (iterator.hasNext()){

            GameDto game = (GameDto) iterator.next();
            SeasonTeamDto seasonTeamDto = game.getHomeTeam();

            if (seasonTeamMap.get(seasonTeamDto.getId()) == null){
                AggregatedSeasonTeamStatsDto agg = new AggregatedSeasonTeamStatsDto();
                agg.setSeasonTeamDto(seasonTeamDto);

                seasonTeamMap.put(seasonTeamDto.getId(), agg);
            }

            aggregateSeasonTeamData(seasonTeamMap.get(seasonTeamDto.getId()), game);

            seasonTeamDto = game.getAwayTeam();

            if (seasonTeamMap.get(seasonTeamDto.getId()) == null){
                AggregatedSeasonTeamStatsDto agg = new AggregatedSeasonTeamStatsDto();
                agg.setSeasonTeamDto(seasonTeamDto);
                seasonTeamMap.put(seasonTeamDto.getId(), agg);
            }

            aggregateSeasonTeamData(seasonTeamMap.get(seasonTeamDto.getId()), game);
        }

        return seasonTeamMap;
    }

    private Map<Integer, AggregatedTeamStatsDto> createTeamDictionary(List<GameDto> games) throws NonExistingGameException {

        Map<Integer, AggregatedTeamStatsDto> teamDictionary = new Hashtable<>();

        Iterator iterator = games.iterator();
        while (iterator.hasNext()){

            GameDto game = (GameDto) iterator.next();
            TeamDto teamDto = game.getHomeTeam().getTeam();

            if (teamDictionary.get(teamDto.getId()) == null){
                AggregatedTeamStatsDto agg = new AggregatedTeamStatsDto();
                agg.setTeamDto(teamDto);
                agg.setLast25Games(getLastXGames(teamDto.getId(),25));
                teamDictionary.put(teamDto.getId(), agg);
            }

            aggregateTeamData(teamDictionary.get(teamDto.getId()), game);

            teamDto = game.getAwayTeam().getTeam();

            if (teamDictionary.get(teamDto.getId()) == null){
                AggregatedTeamStatsDto agg = new AggregatedTeamStatsDto();
                agg.setTeamDto(teamDto);
                teamDictionary.put(teamDto.getId(), agg);
            }

            aggregateTeamData(teamDictionary.get(teamDto.getId()), game);
        }

        return teamDictionary;
    }

    private List<GameDto> getLastXGames(int teamId, int x) throws NonExistingGameException {
        return entityReader.getLastXGamesByTeam(teamId, x);
    }

    private LinearRegressionDto getCsLinearRegressionModel(Map<Integer, AggregatedSeasonTeamStatsDto> teamDictionary) {


        //TODO: Ta fram xCs
        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();

        Set<Integer> keys = teamDictionary.keySet();
        Iterator itr = keys.iterator();

        while(itr.hasNext()){
            int element = (Integer) itr.next();

            AggregatedSeasonTeamStatsDto agg = teamDictionary.get(element);

            xValues.add(agg.getxGAgainst()/agg.getNrOfGames());
            yValues.add(((double)agg.getCleanSheets()/agg.getNrOfGames()));
        }

        return Utility.getLinearRegressionCoefficents(xValues, yValues);
    }

}
