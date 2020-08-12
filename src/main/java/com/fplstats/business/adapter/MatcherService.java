package com.fplstats.business.adapter;

import com.fplstats.common.dto.fplstats.*;
import com.fplstats.common.exception.NonExistingGameException;
import com.fplstats.common.exception.NonExistingSeasonTeamPlayerException;
import com.fplstats.common.exception.NonExistingTeamException;
import com.fplstats.repositories.database.EntityReader;
import com.fplstats.repositories.database.EntityWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MatcherService {

    private EntityWriter entityWriter;
    private EntityReader entityReader;

    public MatcherService(){

        entityReader = new EntityReader();
        entityWriter = new EntityWriter();
    }

    public String matchGames() throws NonExistingTeamException, NonExistingSeasonTeamPlayerException, NonExistingGameException {

        Result<String> result = updateGames();

        if (!result.Success){
            return "Fail - could not update games";
        }

        //result = updateGameStatistics();

        if (!result.Success){
            return "Fail - could not update gamestatistics";
        }

        return "Success";
    }

    public String matchFplData(){

        Result<List<PlayerDto>> result = entityReader.getAllFplPlayers();

        if (!result.Success){
            return "Success - no data to adapt";
        }

        List<PlayerDto> newFplPlayers = result.Data;

        result = entityWriter.savePlayers(newFplPlayers);

        if (!result.Success) {
            return "Fail - could not save players";
        }

        return "Success";
    }

    public String matchUnderstatPlayer(){

        Result<List<PlayerDto>> result = entityReader.getAllPlayersInSystem();

        if (!result.Success){
            return "Fail - could not get players in system";
        }

        List<PlayerDto> systemPlayers = result.Data;

        Result<List<UnderstatPlayerDto>> understatPlayerResult = entityReader.getAllUnderstatPlayers();

        if (!understatPlayerResult.Success){
            return "Fail - could not get understatplayers";
        }

        Iterator it = understatPlayerResult.Data.iterator();
        UnderstatPlayerDto upDto;

        while(it.hasNext()){

            upDto = (UnderstatPlayerDto) it.next();

            Result<PlayerDto> playerDtoResult = matchStpToSystem(upDto, systemPlayers);

            if (playerDtoResult.Success){
                entityWriter.saveMatch(upDto, playerDtoResult.Data);
            }
        }

        return "Success";
    }

    public String adaptUnderstatPlayers() {

        Result<List<MatchUnderstatPlayerDto>> result = entityReader.getAllMatchedUnderstatPlayers();

        if (!result.Success){
            return "Fail - could not find matched understatplayers";
        }

        Iterator it = result.Data.iterator();
        MatchUnderstatPlayerDto matchUnderstatPlayerDto;
        SeasonTeamPlayerDto stp;
        List<String> teamNames;

        while (it.hasNext()){

            matchUnderstatPlayerDto = (MatchUnderstatPlayerDto) it.next();

            teamNames = extractTeams(matchUnderstatPlayerDto);

            Iterator teamNameIt = teamNames.iterator();

            while (teamNameIt.hasNext()){

                Iterator seasonIterator = entityReader.getSeasonsForUnderstatPlayer(matchUnderstatPlayerDto.getUnderstatPlayerDto().getId()).iterator();
                String teamName = (String) teamNameIt.next();

                while (seasonIterator.hasNext()){

                    int seasonId = (int) seasonIterator.next();

                    stp = new SeasonTeamPlayerDto();
                    stp.setPlayer(matchUnderstatPlayerDto.getPlayerDto());
                    stp.setSeasonTeam(new SeasonTeamDto());
                    stp.getSeasonTeam().setTeam(new TeamDto());
                    stp.getSeasonTeam().getTeam().setName(teamName);
                    stp.getSeasonTeam().setSeason(new SeasonDto());
                    stp.getSeasonTeam().getSeason().setId(seasonId);

                    entityWriter.savePlayer(stp);

                }

            }


        }

        return "Success";
    }

    public String matchTeams() {

        Result<String> result = updateTeams();

        if (!result.Success){
            return "Fail - could not update teams";
        }

        return "Success";
    }

    private Result<PlayerDto> matchStpToSystem(UnderstatPlayerDto upDto, List<PlayerDto> systemPlayers) {

        Iterator it = systemPlayers.iterator();
        PlayerDto playerDto;
        Result<PlayerDto> result = new Result<>();
        result.Success = false;
        result.ErrorMessage = "No match";

        while (it.hasNext()){

            playerDto = (PlayerDto) it.next();

            if (playerDto.getName().equals(upDto.getName())){
                result.Success = true;
                result.Data = playerDto;
            }

        }

        return result;
    }

    private List<String> extractTeams(MatchUnderstatPlayerDto matchUnderstatPlayerDto) {

        String teamName = matchUnderstatPlayerDto.getUnderstatPlayerDto().getTeamTitle();
        List<String> teamNames = new ArrayList<>();

        if (teamName.contains(",")){

            String[] teamNameList = teamName.split(",");
            return Arrays.asList(teamNameList);
        }

        teamNames.add(teamName);
        return teamNames;
    }

    private Result<String> updateGames() {

        Result<String> result = new Result<>();
        result.Success = true;

        Result<List<GameDto>> understatGameResult = entityReader.getAllUnderstatGames();

        if (!understatGameResult.Success){
            result.Success = false;
            result.ErrorMessage = "Could not fetch understat games";
            return result;
        }


        result = entityWriter.saveGames(understatGameResult.Data);

        return result;
    }

    private Result<String> updateGameStatistics() throws NonExistingTeamException, NonExistingSeasonTeamPlayerException, NonExistingGameException {

        Result<String> result = new Result<>();
        result.Success = true;

        List<GameDto> gameIds = entityReader.getAllGamesInSystem();
        Iterator it = gameIds.iterator();

        while (it.hasNext()){
            GameDto gameDto = (GameDto) it.next();

            Result<List<GameStatsDto>> understatGameStatsResult = entityReader.getAllUnderstatGameStats(gameDto);

            if (!understatGameStatsResult.Success){
                result.Success = false;
                result.ErrorMessage = "Could not fetch understat games";
                return result;
            }

            result = entityWriter.saveGameStats(understatGameStatsResult.Data);
        }



        return result;


    }

    private Result<String> updateTeams() {

        Result<String> result = new Result<>();
        result.Success = true;

        Result<List<SeasonTeamDto>> understatGameResult = entityReader.getAllUnderstatTeams();

        if (!understatGameResult.Success){
            result.Success = false;
            result.ErrorMessage = "Could not fetch understat games";
            return result;
        }

        result = entityWriter.saveTeams(understatGameResult.Data);

        return result;
    }
}
