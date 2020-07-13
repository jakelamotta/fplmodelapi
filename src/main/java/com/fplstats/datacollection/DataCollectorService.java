package com.fplstats.datacollection;

import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.database.EntityProvider;
import com.fplstats.repositories.database.FileProvider;
import com.fplstats.repositories.services.understat.UnderstatsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Configuration
public class DataCollectorService {


    public String collectUnderstatNestedData(String leagueName, int startYear){

        EntityProvider entityProvider = new EntityProvider();
        Result<List<Integer>> result = entityProvider.getMatchIds(leagueName,startYear);
        Result<String> understatResult;

        UnderstatsProvider understatsProvider = new UnderstatsProvider();

        if (result.Success){

            Iterator it = result.Data.iterator();

            while(it.hasNext()){

                Integer matchId = (Integer)it.next();

                understatResult = understatsProvider.getMatchPlayersByMatch(matchId);

                if (understatResult.Success){
                    understatResult = FileProvider.WriteFileContent(understatResult.Data, "understat" + "\\" + leagueName + "\\" + startYear + "\\" + String.valueOf(matchId) + "\\"  +"teams.txt");
                }

                if (!understatResult.Success){
                    return "Error in collectUnderstatNestedData";
                }
            }
        }

        return "Successfull fetch";
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
            return "Succssfull fetch";
        }

        return "Error in data-collection";
    }

}
