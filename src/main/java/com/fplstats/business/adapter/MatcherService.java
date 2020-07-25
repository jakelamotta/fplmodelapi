package com.fplstats.business.adapter;

import com.fplstats.common.dto.fplstats.PlayerDto;
import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.common.dto.fplstats.SeasonTeamPlayerDto;
import com.fplstats.common.dto.fplstats.UnderstatPlayerDto;
import com.fplstats.repositories.database.EntityProvider;
import com.fplstats.repositories.database.models.Player;
import com.fplstats.repositories.database.models.SeasonTeamPlayer;
import com.fplstats.repositories.database.models.UnderstatGamePlayer;

import java.util.Iterator;
import java.util.List;

public class MatcherService {

    private EntityProvider entityProvider;

    public MatcherService(){
        entityProvider = new EntityProvider();
    }

    public String matchFplData(){

        Result<List<PlayerDto>> result = entityProvider.getAllFplPlayers();

        if (!result.Success){
            return "Success - no data to adapt";
        }

        List<PlayerDto> newFplPlayers = result.Data;

        //result = entityProvider.getAllPlayersInSystem();

        //if (!result.Success){
        //    return "Fail - could not fetch players in system";
        //}

        //List<PlayerDto> existingPlayers = result.Data;

        result = entityProvider.savePlayers(newFplPlayers);

        if (!result.Success) {
            return "Fail - could not save players";
        }

        return "Success";
    }

    public String matchUnderstatPlayer(){

        Result<List<PlayerDto>> result = entityProvider.getAllPlayersInSystem();

        if (!result.Success){
            return "Fail - could not get players in system";
        }

        List<PlayerDto> systemPlayers = result.Data;

        Result<List<UnderstatPlayerDto>> understatPlayerResult = entityProvider.getAllUnderstatPlayers();

        if (!understatPlayerResult.Success){
            return "Fail - could not get understatplayers";
        }

        Iterator it = understatPlayerResult.Data.iterator();
        UnderstatPlayerDto upDto;

        while(it.hasNext()){

            upDto = (UnderstatPlayerDto) it.next();

            Result<PlayerDto> playerDtoResult = matchStpToSystem(upDto, systemPlayers);

            if (playerDtoResult.Success){
                entityProvider.saveMatch(upDto, playerDtoResult.Data);
            }
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
}
