package com.fplstats.datacollection;

import com.fplstats.adapter.AdapterUtility;
import com.fplstats.common.dto.adapter.FplJsonObject;
import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.database.EntityProvider;
import com.fplstats.repositories.database.FileProvider;
import com.fplstats.repositories.services.fpl.FplApiProvider;
import com.fplstats.repositories.services.understat.UnderstatsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Configuration
public class DataCollectorService {

    public String collectFplData() throws IOException {

        FplApiProvider provider = new FplApiProvider();

        Result<String> result = provider.getFplPlayerData();
        result.ErrorMessage =  "General error collectFplData";

        if (result.Success){

            result = FileProvider.WriteFileContent(result.Data, "FPL\\data.txt");
        }

        FplJsonObject fplJsonObject = AdapterUtility.ParseJsonString("{\"chance_of_playing_next_round\":100,\"chance_of_playing_this_round\":100,\"code\":154043,\"cost_change_event\":0,\"cost_change_event_fall\":0,\"cost_change_start\":-5,\"cost_change_start_fall\":5,\"dreamteam_count\":1,\"element_type\":2,\"ep_next\":\"0.9\",\"ep_this\":\"1.4\",\"event_points\":0,\"first_name\":\"Ainsley\",\"form\":\"0.4\",\"id\":4,\"in_dreamteam\":false,\"news\":\"A\",\"news_added\":\"2019-09-22T18:00:10.824841Z\",\"now_cost\":45,\"photo\":\"154043.jpg\",\"points_per_game\":\"2.1\",\"second_name\":\"Maitland-Niles\",\"selected_by_percent\":\"2.2\",\"special\":false,\"squad_number\":null,\"status\":\"a\",\"team\":1,\"team_code\":3,\"total_points\":40,\"transfers_in\":609637,\"transfers_in_event\":398,\"transfers_out\":652487,\"transfers_out_event\":154,\"value_form\":\"0.1\",\"value_season\":\"8.9\",\"web_name\":\"Maitland-Niles\",\"minutes\":1292,\"goals_scored\":0,\"assists\":2,\"clean_sheets\":3,\"goals_conceded\":20,\"own_goals\":0,\"penalties_saved\":0,\"penalties_missed\":0,\"yellow_cards\":4,\"red_cards\":1,\"saves\":0,\"bonus\":3,\"bps\":229,\"influence\":\"282.6\",\"creativity\":\"159.5\",\"threat\":\"37.0\",\"ict_index\":\"47.3\",\"influence_rank\":243,\"influence_rank_type\":97,\"creativity_rank\":227,\"creativity_rank_type\":57,\"threat_rank\":364,\"threat_rank_type\":133,\"ict_index_rank\":308,\"ict_index_rank_type\":103}");

        if (result.Success){
            return "Successfull fetch!";
        }

        return result.ErrorMessage;
    }

    public String collectUnderstatNestedData(String leagueName, int startYear){

        EntityProvider entityProvider = new EntityProvider();
        Result<List<Integer>> result = entityProvider.getGameIds(leagueName,startYear);
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
