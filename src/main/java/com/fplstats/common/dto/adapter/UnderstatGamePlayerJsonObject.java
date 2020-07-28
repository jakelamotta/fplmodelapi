package com.fplstats.common.dto.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnderstatGamePlayerJsonObject{

    private int gameId;
    private int goals;
    private int own_goals;
    private int shots;
    private double xG;
    private int time;
    private int team_id;
    private int player_id;
    private int yellow_card;
    private int red_card;
    private int key_passes;
    private int assists;
    private double xA;
    private double xGChain;

    private double xGBuildup;

    private String h_a;
    private int id;
    private int seasonId;

    public int getSeasonId() {
            return seasonId;
    }

    public void setSeasonId(int seasonId) {
            this.seasonId = seasonId;
    }

    public String getH_a() {
            return h_a;
    }

    public void setH_a(String h_a) {
        this.h_a = h_a;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getOwn_goals() {
        return own_goals;
    }

    public void setOwn_goals(int own_goals) {
        this.own_goals = own_goals;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public double getxG() {
        return xG;
    }

    public void setxG(double xG) {
        this.xG = xG;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getYellow_card() {
        return yellow_card;
    }

    public void setYellow_card(int yellow_card) {
        this.yellow_card = yellow_card;
    }

    public int getRed_card() {
        return red_card;
    }

    public void setRed_card(int red_card) {
        this.red_card = red_card;
    }

    public int getKey_passes() {
        return key_passes;
    }

    public void setKey_passes(int key_passes) {
        this.key_passes = key_passes;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public double getxA() {
        return xA;
    }

    public void setxA(double xA) {
        this.xA = xA;
    }

    public double getxGChain() {
        return xGChain;
    }

    public void setxGChain(double xGChain) {
        this.xGChain = xGChain;
    }

    public double getxGBuildup() {
        return xGBuildup;
    }

    public void setxGBuildup(double xGBuildup) {
        this.xGBuildup = xGBuildup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }
}
