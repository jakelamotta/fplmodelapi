package com.fplstats.repositories.services.understat;

import com.fplstats.common.dto.fplstats.Result;
import org.springframework.context.annotation.Bean;

import java.io.IOException;


public class UnderstatsProvider {

    public Result<String> getTeams(String leagueName, int startYear) throws IOException, InterruptedException {


        String basePath = "C:\\Users\\krist\\IdeaProjects\\fplmodelapi\\";//UnderstatsProvider.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String path = basePath + "src\\main\\java\\com\\fplstats\\repositories\\services\\understat\\getTeams.py";


        String[] args = new String[]{leagueName, String.valueOf(startYear)};

        Result<String> result = PythonUtility.ExecutePythonScript(path,args);

        return result;
    }

    @Bean
    public Result<String> getPlayers(String leagueName, int startYear) throws IOException, InterruptedException {


        String[] args = new String[]{leagueName, String.valueOf(startYear)};
        String basePath = "C:\\Users\\krist\\IdeaProjects\\fplmodelapi\\";//UnderstatsProvider.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String path = basePath + "src\\main\\java\\com\\fplstats\\repositories\\services\\understat\\getPlayers.py";

        Result<String> result = PythonUtility.ExecutePythonScript(path,args);

        return result;
    }

    @Bean
    public Result<String> getLeagueResults(String leagueName, int startYear) throws IOException, InterruptedException {


        String[] args = new String[]{leagueName, String.valueOf(startYear)};

        String basePath = "C:\\Users\\krist\\IdeaProjects\\fplmodelapi\\";//UnderstatsProvider.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String path = basePath + "src\\main\\java\\com\\fplstats\\repositories\\services\\understat\\getLeagueResults.py";

        Result<String> result = PythonUtility.ExecutePythonScript(path, args);

        return result;
    }

    @Bean
    public Result<String> getMatchPlayersByMatch(int gameId) throws IOException, InterruptedException {
        String[] args = new String[]{String.valueOf(gameId), ""};

        String basePath = "C:\\Users\\krist\\IdeaProjects\\fplmodelapi\\";//UnderstatsProvider.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String path = basePath + "src\\main\\java\\com\\fplstats\\repositories\\services\\understat\\getGamePlayers.py";

        Result<String> result = PythonUtility.ExecutePythonScript(path, args);

        return result;
    }
}
