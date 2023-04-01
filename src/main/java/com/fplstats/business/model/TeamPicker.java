package com.fplstats.business.model;

import com.fplstats.common.dto.fplstats.CalculatedPlayerStatisticsDto;
import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.database.EntityReader;
import com.fplstats.repositories.database.models.CalculatedPlayerStatistics;
import com.fplstats.repositories.database.models.Team;

import java.util.*;

public class TeamPicker {

    private EntityReader entityReader;

    public TeamPicker(){

        entityReader = new EntityReader();
    }

    public Map<String, CalculatedPlayerStatisticsDto> pickTeam(long iterations) throws Exception {

        Result<List<CalculatedPlayerStatisticsDto>> calculatedPlayerStatisticsDtoResult = entityReader.getAllCalculatedPlayerStatistics();
        List<CalculatedPlayerStatisticsDto> allPlayers = calculatedPlayerStatisticsDtoResult.Data;
        Map<String, CalculatedPlayerStatisticsDto> players = new HashMap<>();
        Map<String, CalculatedPlayerStatisticsDto> finalTeam = new HashMap<>();

        double finalPoints = 0.0;
        double finalSum = 0.0;

        for (int i=0; i < iterations;i++){

            double sum = 0.0;
            double totalPoints = 0.0;

            try{
                players = pickTeam(10000, allPlayers);
            }
            catch (Exception e){
                var t = 1;
            }

            for (var entry: players.values()) {
                if (entry != null){
                    sum += entry.getCost();
                    totalPoints += entry.getxPAbs();
                }
            }

            if (finalPoints < totalPoints){
                finalTeam = players;
                finalSum = sum;
                finalPoints = totalPoints;
            }
        }

        return finalTeam;
    }

    public Map<String, CalculatedPlayerStatisticsDto> pickTeam(long iterations, List<CalculatedPlayerStatisticsDto> allPlayers) throws Exception {

        Map<String, CalculatedPlayerStatisticsDto> team = new HashMap<>();
        team.put("G", null);

        team.put("D1", null);
        team.put("D2", null);
        team.put("D3", null);

        team.put("M1", null);
        team.put("M2", null);
        team.put("M3", null);
        team.put("M4", null);

        team.put("F1", null);
        team.put("F2", null);
        team.put("F3", null);

        for (int i = 0; i < iterations; i++){
        //while (!allPlayers.isEmpty()){
            CalculatedPlayerStatisticsDto player = pickRandom(allPlayers);

            if (player.getPosition().getName().equals("Goalkeeper")){
                tryAddKeeper(team, player);
            }
            else if (player.getPosition().getName().equals("Defender")){
                tryAddDef(team, player);
            }
            else if (player.getPosition().getName().equals("Midfielder")){
                tryAddMidfielder(team, player);
            }
            else if (player.getPosition().getName().equals("Forward")){
                tryAddForward(team, player);
            }
            else {
                throw new Exception();
            }

        }

        return team;
    }

    private void tryAddKeeper(Map<String, CalculatedPlayerStatisticsDto> team, CalculatedPlayerStatisticsDto player) {

        List<String> keys = new ArrayList<String>();
        keys.add("G");

        tryAdd(keys, team, player);
    }

    private void tryAddDef(Map<String, CalculatedPlayerStatisticsDto> team, CalculatedPlayerStatisticsDto player) {
        List<String> keys = new ArrayList<String>();
        keys.add("D1");
        keys.add("D2");
        keys.add("D3");

        tryAdd(keys, team, player);
    }

    private void tryAddMidfielder(Map<String, CalculatedPlayerStatisticsDto> team, CalculatedPlayerStatisticsDto player) {
        List<String> keys = new ArrayList<String>();
        keys.add("M1");
        keys.add("M2");
        keys.add("M3");
        keys.add("M4");

        tryAdd(keys, team, player);

    }

    private void tryAddForward(Map<String, CalculatedPlayerStatisticsDto> team, CalculatedPlayerStatisticsDto player) {
        List<String> keys = new ArrayList<String>();
        keys.add("F1");
        keys.add("F2");
        keys.add("F3");

        tryAdd(keys, team, player);
    }

    private void tryAdd(List<String> keys, Map<String, CalculatedPlayerStatisticsDto> team, CalculatedPlayerStatisticsDto player){
        Map<String, CalculatedPlayerStatisticsDto> suggestedTeam = new HashMap<>();

        for (String key : team.keySet()){
            suggestedTeam.put(key, team.get(key));
        }

        for (String key : keys){
            if (suggestedTeam.get(key) == null){
                suggestedTeam.put(key, player);

                if (isTeamViable(suggestedTeam)){
                    team.put(key, player);
                }

                return;
            }
            else if (suggestedTeam.get(key).getPlayerName() == player.getPlayerName()){
                return;
            }
        }

        String key = getRandomKey(keys);

        CalculatedPlayerStatisticsDto existingPlayer = suggestedTeam.get(key);

        if (player.getxPAbs() > existingPlayer.getxPAbs()){
            suggestedTeam.put(key,player);

            if (isTeamViable(suggestedTeam)){
                team.put(key, player);
            }
        }
    }

    private CalculatedPlayerStatisticsDto pickRandom(List<CalculatedPlayerStatisticsDto> allPlayers) {
        Random rand = new Random();
        CalculatedPlayerStatisticsDto player =  allPlayers.get(rand.nextInt(allPlayers.size()));

        //allPlayers.remove(player);
        return player;
    }

    private String getRandomKey(List<String> keys){

        Random rand = new Random();
        return keys.get(rand.nextInt(keys.size()));
    }

    private boolean isTeamViable(Map<String, CalculatedPlayerStatisticsDto> team){

        Map<String, Integer> teams = new HashMap<>();
        double sum = 0.0;

        for (String key:team.keySet()){

            CalculatedPlayerStatisticsDto player = team.get(key);

            if (player != null) {
                sum += player.getCost();

                if (teams.containsKey(player.getTeamName())){
                    int current = teams.get(player.getTeamName());
                    teams.put(player.getTeamName(), current+1);
                }
                else{
                    teams.put(player.getTeamName(), 1);
                }
            }
        }

        if (sum > 83.0){
            return false;
        }

        for (String key:teams.keySet()){

            if (teams.get(key) > 3){
                return false;
            }
        }

        return true;
    }

}
