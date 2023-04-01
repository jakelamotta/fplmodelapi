package com.fplstats.business.datacollection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fplstats.common.dto.adapter.*;
import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.database.EntityWriter;
import com.fplstats.repositories.database.FileProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DataImporterService {

    private EntityWriter entityWriter;

    public DataImporterService(){
        entityWriter = new EntityWriter();
    }

    public void importFplData() throws JsonProcessingException {

        String data = FileProvider.ReadFileContent("FPL" + File.separator + "data.txt");

        String jsonString = data.split("\"elements\":")[1];

        ObjectMapper objectMapper = new ObjectMapper();
        List<FplJsonObject> fplJsonObject = objectMapper.readValue(jsonString, new TypeReference<List<FplJsonObject>>(){});

        entityWriter.saveFplPlayers(fplJsonObject);
    }

    public void importUnderstatPlayers(String leagueName, int year) throws Exception {

        String filename = "understat" + File.separator + leagueName + File.separator + year + File.separator + "players.txt";

        String data = FileProvider.ReadFileContent(filename);
        List<UnderstatPlayerJsonObject> understatPlayerJsonObject;

        if (data != null){
            ObjectMapper objectMapper = new ObjectMapper();
            understatPlayerJsonObject = objectMapper.readValue(data, new TypeReference<List<UnderstatPlayerJsonObject>>(){});
            entityWriter.saveUnderstatPlayers(leagueName, year, understatPlayerJsonObject);
        }
        else{
            throw new Exception("Could not import understatPlayers");
        }
    }

    public void importUnderstatTeams(String leagueName, int year) throws Exception {

        String filename = "understat" + File.separator + leagueName + File.separator + year + File.separator + "teams.txt";

        String data =  FileProvider.ReadFileContent(filename);
        List<UnderstatTeamJsonObject> understatTeamJsonObjectList;

        if (data != null){
            ObjectMapper objectMapper = new ObjectMapper();
            understatTeamJsonObjectList = objectMapper.readValue(data, new TypeReference<List<UnderstatTeamJsonObject>>(){});
            entityWriter.saveUnderstatTeams(leagueName, year, understatTeamJsonObjectList);
        }
        else{
            throw new Exception("Could not import understatPlayers");
        }
    }

    public void importUnderstatGames(String leagueName, int year) throws Exception {

        String filename = "understat" + File.separator + leagueName + File.separator + year + File.separator + "results.txt";

        String data = FileProvider.ReadFileContent(filename);
        List<UnderstatGameJsonObject> understatGameJsonObjectList;

        if (data != null){
            ObjectMapper objectMapper = new ObjectMapper();
            understatGameJsonObjectList = objectMapper.readValue(data, new TypeReference<List<UnderstatGameJsonObject>>(){});
            entityWriter.saveUnderstatGames(leagueName, year, understatGameJsonObjectList);
        }
        else{
            throw new Exception("Could not import understatPlayers");
        }
    }

    public void importUnderstatGamePlayers(String leagueName, int year) throws IOException {

        String path = "data" + File.separator + "understat" + File.separator + leagueName + File.separator + year;

        List<String> paths = Files.walk(Paths.get(path)).filter(Files::isDirectory).map(s -> s.toString()).collect(Collectors.toList());
        Iterator it = paths.iterator();
        String temp = it.next().toString();

        List<UnderstatGamePlayerJsonObject> understatGamePlayerJsonObjectList = new ArrayList<>();

        JsonNode jsonNode;
        int gameId;

        while(it.hasNext()){
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                temp = it.next().toString();

                String[] arr = temp.split("/");
                String gameIdAsString = arr[arr.length-1];
                gameId = Integer.valueOf(gameIdAsString);

                String data = FileProvider.ReadFileContent( temp + File.separator + "games.txt", true);
                jsonNode = objectMapper.readTree(data);

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

                entityWriter.saveUnderstatGamePlayers(leagueName, year, understatGamePlayerJsonObjectList, gameId);
                understatGamePlayerJsonObjectList.clear();
                FileProvider.DeleteItem(temp);
            }
            catch (Exception e){
                var t = e;
            }

        }
    }

    public String importFplHistory(int year) {



        return "Success";
    }
}
