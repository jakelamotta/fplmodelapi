package com.fplstats.business.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fplstats.common.dto.adapter.*;
import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.database.EntityProvider;
import com.fplstats.repositories.database.FileProvider;
import com.fplstats.repositories.database.models.UnderstatTeam;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DataImporterService {

    public String importFplData() throws JsonProcessingException {

        Result<String> result = FileProvider.ReadFileContent("FPL\\data.txt");

        String jsonString = result.Data.split("\"elements\":")[1];

        ObjectMapper objectMapper = new ObjectMapper();
        List<FplJsonObject> fplJsonObject = objectMapper.readValue(jsonString, new TypeReference<List<FplJsonObject>>(){});

        EntityProvider provider = new EntityProvider();
        result = provider.saveFplPlayers(fplJsonObject);

        if (result.Success){
            return "Successfull import!";
        }

        return result.ErrorMessage;
    }

    public String importUnderstatPlayers(String leagueName, int year) throws IOException {


        EntityProvider provider = new EntityProvider();

        String filename = "Understat\\" + leagueName + "\\" + year + "\\" + "players.txt";

        Result<String> result = FileProvider.ReadFileContent(filename);
        List<UnderstatPlayerJsonObject> understatPlayerJsonObject;

        if (result.Success){
            ObjectMapper objectMapper = new ObjectMapper();
            understatPlayerJsonObject = objectMapper.readValue(result.Data, new TypeReference<List<UnderstatPlayerJsonObject>>(){});
            provider.saveUnderstatPlayers(leagueName, year, understatPlayerJsonObject);
        }

        if (result.Success){
            return "Success";
        }

        return result.ErrorMessage;
    }

    public String importUnderstatTeams(String leagueName, int year) throws IOException {
        EntityProvider provider = new EntityProvider();

        String filename = "Understat\\" + leagueName + "\\" + year + "\\" + "teams.txt";

        Result<String> result = FileProvider.ReadFileContent(filename);
        List<UnderstatTeamJsonObject> understatTeamJsonObjectList;

        if (result.Success){
            ObjectMapper objectMapper = new ObjectMapper();
            understatTeamJsonObjectList = objectMapper.readValue(result.Data, new TypeReference<List<UnderstatTeamJsonObject>>(){});
            provider.saveUnderstatTeams(leagueName, year, understatTeamJsonObjectList);
        }

        if (result.Success){
            return "Success";
        }

        return result.ErrorMessage;
    }

    public String importUnderstatGames(String leagueName, int year) throws IOException {
        EntityProvider provider = new EntityProvider();

        String filename = "Understat\\" + leagueName + "\\" + year + "\\" + "results.txt";

        Result<String> result = FileProvider.ReadFileContent(filename);
        List<UnderstatGameJsonObject> understatGameJsonObjectList;

        if (result.Success){
            ObjectMapper objectMapper = new ObjectMapper();
            understatGameJsonObjectList = objectMapper.readValue(result.Data, new TypeReference<List<UnderstatGameJsonObject>>(){});
            provider.saveUnderstatGames(leagueName, year, understatGameJsonObjectList);
        }

        if (result.Success){
            return "Success";
        }

        return result.ErrorMessage;
    }

    public String importUnderstatGamePlayers(String leagueName, int year) throws IOException {

        String basePath = "C:\\Users\\krijoh3\\IdeaProjects\\fplstats\\src\\main\\resources\\data\\";
        String path = basePath + "\\Understat\\" + leagueName + "\\" + year;

        EntityProvider provider = new EntityProvider();

        List<String> paths = Files.walk(Paths.get(path)).filter(Files::isDirectory).map(s -> s.toString()).collect(Collectors.toList());
        Iterator it = paths.iterator();
        String temp = it.next().toString();

        List<UnderstatGamePlayerJsonObject> understatGamePlayerJsonObjectList = new ArrayList<>();

        JsonNode jsonNode;
        Result<String> result;
        int gameId;

        while(it.hasNext()){
            ObjectMapper objectMapper = new ObjectMapper();
            temp = it.next().toString();

            String gameIdAsString = temp.substring(path.length());
            gameId = Integer.valueOf(gameIdAsString);

            result = FileProvider.ReadFileContent( temp + "\\games.txt", true);
            jsonNode = objectMapper.readTree(result.Data);

            JsonNode homeNode = jsonNode.get("h");
            homeNode.fieldNames()
                    .forEachRemaining(f -> {
                        try {
                            String t = homeNode.get(f).toString();
                            UnderstatGamePlayerJsonObject obj = objectMapper.readValue(homeNode.get(f).toString(), UnderstatGamePlayerJsonObject.class);
                            understatGamePlayerJsonObjectList.add(obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });

            JsonNode awayNode = jsonNode.get("a");
            homeNode.fieldNames()
                    .forEachRemaining(f -> {
                        try {
                            String t = homeNode.get(f).toString();
                            UnderstatGamePlayerJsonObject obj = objectMapper.readValue(homeNode.get(f).toString(), UnderstatGamePlayerJsonObject.class);
                            understatGamePlayerJsonObjectList.add(obj);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    });

            result = provider.saveUnderstatGamePlayers(leagueName, year, understatGamePlayerJsonObjectList, gameId);

            if (result.Success){
                FileProvider.DeleteItem(temp);
            }
        }

        return "Success";
    }
}
