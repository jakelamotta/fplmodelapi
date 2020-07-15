package com.fplstats.repositories.database.models;

import javax.persistence.*;

@Entity
@Table(name = "GameStats")
public class GameStats{

    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "seasonteamplayer_id")
    private SeasonTeamPlayer seasonTeamPlayer;

    @Column(name = "minutesplayed")
    private int minutesPlayed;

    @Column(name = "goals")
    private int goals;

    @Column(name = "assists")
    private int assists;

    @Column(name = "saves")
    private int saves;

    @Column(name = "shots")
    private int shots;

    @Column(name = "yellowcards")
    private int yellowCards;

    @Column(name = "redcards")
    private int redCards;

    @Column(name = "xg")
    private double xG;

    @Column(name = "xA")
    private double xA;

    @Column(name = "xGChain")
    private double xGChain;

    @Column(name = "xGBuildup")
    private double xGBuildup;


    public void setId(int id) {
        Id = id;
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

    public int getId() {
        return Id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public SeasonTeamPlayer getSeasonTeamPlayer() {
        return seasonTeamPlayer;
    }

    public void setSeasonTeamPlayer(SeasonTeamPlayer seasonTeamPlayer) {
        this.seasonTeamPlayer = seasonTeamPlayer;
    }
}
