package com.fplstats.repositories.database;

import com.fplstats.common.dto.adapter.*;
import com.fplstats.common.dto.fplstats.*;
import com.fplstats.common.exception.NonExistingGameException;
import com.fplstats.common.exception.NonExistingSeasonTeamPlayerException;
import com.fplstats.common.exception.NonExistingTeamException;
import com.fplstats.repositories.database.models.*;

import java.util.List;



public interface IEntityWriter{

    Result<String> saveFplPlayers(List<FplJsonObject> fplJsonObject);
    Result<String> saveUnderstatPlayers(String leagueName, int year, List<UnderstatPlayerJsonObject> understatPlayerJsonObjectList);
    Result<String> saveUnderstatGames(String leagueName, int year, List<UnderstatGameJsonObject> understatGameJsonObjectList);
    Result<String> saveUnderstatGamePlayers(String leagueName, int year, List<UnderstatGamePlayerJsonObject> understatGamePlayerJsonObjectList, int gameId);
    Result<List<PlayerDto>> savePlayers(List<PlayerDto> newFplPlayers);
    void savePlayer(SeasonTeamPlayerDto stpDto);
    void saveMatch(UnderstatPlayerDto upDto, PlayerDto data);
    Result<String> saveGames(List<GameDto> data);
    Result<String> saveUnderstatTeams(String leagueName, int year, List<UnderstatTeamJsonObject> understatTeamJsonObjectList);
    Result<String> saveTeams(List<SeasonTeamDto> seasonTeamDtos);
    Result<String> saveGameStats(List<GameStatsDto> data) throws NonExistingSeasonTeamPlayerException, NonExistingGameException, NonExistingTeamException;
}
