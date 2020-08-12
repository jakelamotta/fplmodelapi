package com.fplstats.repositories.database;

import com.fplstats.common.dto.fplstats.*;
import com.fplstats.repositories.database.models.*;

public class Mapper{

    public static GameStatsDto mapGameStatsDto(GameStats gameStats) {
        GameStatsDto gameStatsDto = new GameStatsDto();

        gameStatsDto.setYellowCards(gameStats.getYellowCards());
        gameStatsDto.setxG(gameStats.getxG());
        gameStatsDto.setxA(gameStats.getxA());
        gameStatsDto.setShots(gameStats.getShots());
        gameStatsDto.setRedCards(gameStats.getRedCards());
        gameStatsDto.setMinutesPlayed(gameStats.getMinutesPlayed());
        gameStatsDto.setGoals(gameStats.getGoals());
        gameStatsDto.setGame(mapGameDto(gameStats.getGame()));
        gameStatsDto.setSeasonTeamPlayer(mapSeasonTeamPlayerDto(gameStats.getSeasonTeamPlayer()));

        return gameStatsDto;
    }

    public static SeasonTeamPlayerDto mapSeasonTeamPlayerDto(SeasonTeamPlayer seasonTeamPlayer) {

        SeasonTeamPlayerDto seasonTeamPlayerDto = new SeasonTeamPlayerDto();
        seasonTeamPlayerDto.setSeasonTeam(mapSeasonTeam(seasonTeamPlayer.getSeasonTeam()));
        seasonTeamPlayerDto.setPlayer(mapPlayer(seasonTeamPlayer.getPlayer()));

        return seasonTeamPlayerDto;
    }

    public static PlayerDto mapPlayer(Player player) {

        PlayerDto playerDto = new PlayerDto();
        playerDto.setPosition(mapPosition(player.getPosition()));
        playerDto.setId(player.getId());
        playerDto.setName(player.getName());
        playerDto.setCost(player.getCost());
        playerDto.setFplId(player.getFplId());

        return playerDto;
    }

    public static PositionDto mapPosition(Position position) {
        PositionDto positionDto = new PositionDto();
        positionDto.setId(position.getId());
        positionDto.setName(position.getName());

        return positionDto;
    }

    public static SeasonTeamDto mapSeasonTeam(SeasonTeam seasonTeam) {

        SeasonTeamDto seasonTeamDto = new SeasonTeamDto();
        seasonTeamDto.setSeason(mapSeason(seasonTeam.getSeason()));
        seasonTeamDto.setTeam(mapTeam(seasonTeam.getTeam()));
        seasonTeamDto.setId(seasonTeam.getId());
        return seasonTeamDto;
    }

    private static TeamDto mapTeam(Team team) {

        TeamDto teamDto = new TeamDto();
        teamDto.setUnderstatId(team.getUnderstatid());
        teamDto.setName(team.getName());
        teamDto.setId(team.getId());

        return teamDto;
    }

    private static SeasonDto mapSeason(Season season) {
        SeasonDto seasonDto = new SeasonDto();

        seasonDto.setId(season.getId());
        seasonDto.setStartYear(season.getStartYear());
        seasonDto.setCurrent(season.isActive());

        return seasonDto;
    }

    public static GameDto mapGameDto(Game game) {

        GameDto gameDto = new GameDto();

        gameDto.setHomeXG(game.getHomeXG());
        gameDto.setAwayXG(game.getAwayXG());
        gameDto.setAwayGoals(game.getAwayGoals());
        gameDto.setHomeGoals(game.getHomeGoals());
        gameDto.setId(game.getId());
        gameDto.setHomeTeam(mapSeasonTeam(game.getHomeTeam()));
        gameDto.setAwayTeam(mapSeasonTeam(game.getAwayTeam()));
        gameDto.setDate(game.getGameDay());
        gameDto.setSeason(mapSeason(game.getSeason()));
        gameDto.setUnderstatid(game.getUnderstatId());

        return gameDto;
    }

}
