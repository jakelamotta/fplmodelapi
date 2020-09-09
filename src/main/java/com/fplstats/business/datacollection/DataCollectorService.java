package com.fplstats.business.datacollection;

import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.common.dto.fplstats.SeasonDto;
import com.fplstats.repositories.database.EntityReader;
import com.fplstats.repositories.database.FileProvider;
import com.fplstats.repositories.services.fpl.FplApiProvider;
import com.fplstats.repositories.services.understat.UnderstatsProvider;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Configuration
public class DataCollectorService {

    private EntityReader entityReader;

    public DataCollectorService(){
        entityReader = new EntityReader();
    }

    public String collectFplData() throws IOException {

        FplApiProvider provider = new FplApiProvider();

        Result<String> result = provider.getFplPlayerData();
        result.ErrorMessage =  "General error collectFplData";

        if (result.Success){

            result = FileProvider.WriteFileContent(result.Data, "FPL\\data.txt");
        }


        if (result.Success){
            return "Success";
        }

        return result.ErrorMessage;
    }

    public String collectUnderstatNestedData(String leagueName, int startYear) throws IOException, InterruptedException {

        Result<List<Integer>> result = entityReader.getGameIds(leagueName,startYear);
        Result<String> understatResult;

        UnderstatsProvider understatsProvider = new UnderstatsProvider();

        if (result.Success){

            Iterator it = result.Data.iterator();

            while(it.hasNext()){

                Integer matchId = (Integer)it.next();

                understatResult = understatsProvider.getMatchPlayersByMatch(matchId);

                if (understatResult.Success){
                    understatResult = FileProvider.WriteFileContent(understatResult.Data, "understat" + "\\" + leagueName + "\\" + startYear + "\\" + String.valueOf(matchId) + "\\"  +"games.txt");
                }

                if (!understatResult.Success){
                    return "Error in collectUnderstatNestedData";
                }
            }
        }

        return "Success";
    }

    public String collectUnderstatBaseData(String leagueName, int startYear) throws IOException, InterruptedException {

        UnderstatsProvider understatsProvider = new UnderstatsProvider();
        Result<String> result = understatsProvider.getTeams(leagueName,startYear);

        if (result.Success){
            result = FileProvider.WriteFileContent(result.Data, "understat" + "\\" + leagueName + "\\" + startYear + "\\" + "teams.txt");
        }

        result = understatsProvider.getPlayers(leagueName,startYear);

        if (result.Success){
            result = FileProvider.WriteFileContent(result.Data, "understat" + "\\" + leagueName + "\\" + startYear + "\\" + "players.txt");
        }

        result = understatsProvider.getLeagueResults(leagueName,startYear);

        if (result.Success){
            result = FileProvider.WriteFileContent(result.Data, "understat" + "\\" + leagueName + "\\" + startYear + "\\" + "results.txt");
        }

        if (result.Success){
            return "Success";
        }

        return "Error in data-collection";
    }

    public SeasonDto getActiveSeason() {

        Result<SeasonDto> result = entityReader.getActiveSeason();

        if (result.Success){
            return result.Data;
        }

        throw new NullPointerException("No active season");
    }
}
