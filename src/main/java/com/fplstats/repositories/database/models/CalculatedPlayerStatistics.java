package com.fplstats.repositories.database.models;

import javax.persistence.*;

@Entity
@Table(name = "calculatedplayerstatistics")
public class CalculatedPlayerStatistics{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="player")
    private String playerName;

    @Column(name="team")
    private String teamName;

    @Column(name = "position")
    private String position;

    @Column(name = "cost")
    private double cost;

    @Column(name = "xppoundgame")
    private double xPPoundGame;

    @Column(name = "xpabs")
    private double xPAbs;

    @Column(name = "minutesplayed")
    private int minutesplayed;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }


    public double getxPPoundGame() {
        return xPPoundGame;
    }

    public void setxPPoundGame(double xPPoundGame) {
        this.xPPoundGame = xPPoundGame;
    }

    public double getxPAbs() {
        return xPAbs;
    }

    public void setxPAbs(double xPAbs) {
        this.xPAbs = xPAbs;
    }

    public int getMinutesplayed() {
        return minutesplayed;
    }

    public void setMinutesplayed(int minutesplayed) {
        this.minutesplayed = minutesplayed;
    }

    public int getId() {
        return id;
    }
}
