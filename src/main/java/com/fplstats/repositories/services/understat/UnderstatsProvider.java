package com.fplstats.repositories.services.understat;

import com.fplstats.common.dto.fplstats.Result;
import org.python.util.PythonInterpreter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.List;

public class UnderstatsProvider {

    public Result<String> getTeams(String leagueName, int startYear) throws IOException, InterruptedException {


        String basePath = UnderstatsProvider.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String path = "C:\\Users\\krijoh3\\IdeaProjects\\fplstats\\src\\main\\java\\com\\fplstats\\repositories\\services\\understat\\getTeams.py";


        String[] args = new String[]{leagueName, String.valueOf(startYear)};

        Result<String> result = PythonUtility.ExecutePythonScript(path,args);

        return result;
    }

    @Bean
    public Result<String> getPlayers(String leagueName, int startYear) throws IOException, InterruptedException {


        String[] args = new String[]{leagueName, String.valueOf(startYear)};
        String basePath = UnderstatsProvider.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String path = "C:\\Users\\krijoh3\\IdeaProjects\\fplstats\\src\\main\\java\\com\\fplstats\\repositories\\services\\understat\\getPlayers.py";

        Result<String> result = PythonUtility.ExecutePythonScript(path,args);

        return result;
    }

    @Bean
    public Result<String> getLeagueResults(String leagueName, int startYear) throws IOException, InterruptedException {


        String[] args = new String[]{leagueName, String.valueOf(startYear)};

        String path = "C:\\Users\\krijoh3\\IdeaProjects\\fplstats\\src\\main\\java\\com\\fplstats\\repositories\\services\\understat\\getLeagueResults.py";

        Result<String> result = PythonUtility.ExecutePythonScript(path, args);

        return result;
    }

    @Bean
    public Result<String> getMatchPlayersByMatch(int next) {
        throw new NotImplementedException();
    }
}
