package com.fplstats.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fplstats.common.dto.adapter.FplJsonObject;
import com.fplstats.common.dto.adapter.UnderstatGameJsonObject;
import com.fplstats.common.dto.adapter.UnderstatGamePlayerJsonObject;
import com.fplstats.common.dto.adapter.UnderstatPlayerJsonObject;
import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.database.EntityProvider;
import com.fplstats.repositories.database.FileProvider;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
            return "Successfull import!";
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
            return "Successfull import!";
        }

        return result.ErrorMessage;
    }

    public String importUnderstatGamePlayers(String leagueName, int year) throws IOException {

        String basePath = "C:\\Users\\krijoh3\\IdeaProjects\\fplstats\\src\\main\\resources\\data\\";
        String path = basePath + "\\Understat\\" + leagueName + "\\" + year;

        List<String> paths = Files.walk(Paths.get(path)).filter(Files::isDirectory).map(s -> s.toString()).collect(Collectors.toList());
        Iterator it = paths.iterator();
        String temp = it.next().toString();
        List<UnderstatGamePlayerJsonObject> understatGamePlayerJsonObjectList;
        Result<String> result = new Result<>();

        while(it.hasNext()){
            ObjectMapper objectMapper = new ObjectMapper();
            understatGamePlayerJsonObjectList = objectMapper.readValue(result.Data, new TypeReference<List<UnderstatGamePlayerJsonObject>>(){});


            FileProvider.DeleteItem(it.next().toString());
        }

        return "Successfull delete";
    }
}
