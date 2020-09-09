package com.fplstats.business.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fplstats.common.dto.adapter.*;
import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.database.EntityReader;
import com.fplstats.repositories.database.EntityWriter;
import com.fplstats.repositories.database.FileProvider;
import com.fplstats.repositories.database.IEntityWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DataImporterService {

    private EntityWriter entityWriter;

    public DataImporterService(){
        entityWriter = new EntityWriter();
    }

    public String importFplData() throws JsonProcessingException {

        Result<String> result = FileProvider.ReadFileContent("FPL\\data.txt");

        String jsonString = result.Data.split("\"elements\":")[1];

        ObjectMapper objectMapper = new ObjectMapper();
        List<FplJsonObject> fplJsonObject = objectMapper.readValue(jsonString, new TypeReference<List<FplJsonObject>>(){});

        result = entityWriter.saveFplPlayers(fplJsonObject);

        if (result.Success){
            return "Success";
        }

        return result.ErrorMessage;
    }

    public String importUnderstatPlayers(String leagueName, int year) throws IOException {

        String filename = "Understat\\" + leagueName + "\\" + year + "\\" + "players.txt";

        Result<String> result = FileProvider.ReadFileContent(filename);
        List<UnderstatPlayerJsonObject> understatPlayerJsonObject;

        if (result.Success){
            ObjectMapper objectMapper = new ObjectMapper();
            understatPlayerJsonObject = objectMapper.readValue(result.Data, new TypeReference<List<UnderstatPlayerJsonObject>>(){});
            entityWriter.saveUnderstatPlayers(leagueName, year, understatPlayerJsonObject);
        }

        if (result.Success){
            return "Success";
        }

        return result.ErrorMessage;
    }

    public String importUnderstatTeams(String leagueName, int year) throws IOException {

        String filename = "Understat\\" + leagueName + "\\" + year + "\\" + "teams.txt";

        Result<String> result = FileProvider.ReadFileContent(filename);
        List<UnderstatTeamJsonObject> understatTeamJsonObjectList;

        if (result.Success){
            ObjectMapper objectMapper = new ObjectMapper();
            understatTeamJsonObjectList = objectMapper.readValue(result.Data, new TypeReference<List<UnderstatTeamJsonObject>>(){});
            entityWriter.saveUnderstatTeams(leagueName, year, understatTeamJsonObjectList);
        }

        if (result.Success){
            return "Success";
        }

        return result.ErrorMessage;
    }

    public String importUnderstatGames(String leagueName, int year) throws IOException {

        String filename = "Understat\\" + leagueName + "\\" + year + "\\" + "results.txt";

        Result<String> result = FileProvider.ReadFileContent(filename);
        List<UnderstatGameJsonObject> understatGameJsonObjectList;

        if (result.Success){
            ObjectMapper objectMapper = new ObjectMapper();
            understatGameJsonObjectList = objectMapper.readValue(result.Data, new TypeReference<List<UnderstatGameJsonObject>>(){});
            entityWriter.saveUnderstatGames(leagueName, year, understatGameJsonObjectList);
        }

        if (result.Success){
            return "Success";
        }

        return result.ErrorMessage;
    }

    public String importUnderstatGamePlayers(String leagueName, int year) throws IOException {

        String basePath = "C:\\Users\\krist\\IdeaProjects\\fplmodelapi\\src\\main\\resources\\data\\";
        String path = basePath + "\\Understat\\" + leagueName + "\\" + year;

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
                            UnderstatGamePlayerJsonObject obj = objectMapper.readValue(homeNode.get(f).toString(), UnderstatGamePlayerJsonObject.class);
                            understatGamePlayerJsonObjectList.add(obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });

            JsonNode awayNode = jsonNode.get("a");
            awayNode.fieldNames()
                    .forEachRemaining(f -> {
                        try {

                            UnderstatGamePlayerJsonObject obj = objectMapper.readValue(awayNode.get(f).toString(), UnderstatGamePlayerJsonObject.class);
                            understatGamePlayerJsonObjectList.add(obj);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    });

            result = entityWriter.saveUnderstatGamePlayers(leagueName, year, understatGamePlayerJsonObjectList, gameId);
            understatGamePlayerJsonObjectList.clear();
            if (result.Success){
                FileProvider.DeleteItem(temp);
            }
        }

        return "Success";
    }
}
