package com.fplstats.common.dto.fplstats;


public class GameStatsDto{

    private int Id;

    private int understatid;

    private GameDto game;

    private SeasonTeamPlayerDto seasonTeamPlayer;

    private int minutesPlayed;

    private int goals;

    private int assists;

    private int saves;

    private int shots;

    private int yellowCards;

    private int redCards;

    private double xG;

    private double xA;

    private double xGChain;

    private double xGBuildup;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public SeasonTeamPlayerDto getSeasonTeamPlayer() {
        return seasonTeamPlayer;
    }

    public void setSeasonTeamPlayer(SeasonTeamPlayerDto seasonTeamPlayer) {
        this.seasonTeamPlayer = seasonTeamPlayer;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public double getxG() {
        return xG;
    }

    public void setxG(double xG) {
        this.xG = xG;
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

    public int getUnderstatid() {
        return understatid;
    }

    public void setUnderstatid(int understatid) {
        this.understatid = understatid;
    }
}
