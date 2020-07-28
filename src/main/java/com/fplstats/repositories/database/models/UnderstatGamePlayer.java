package com.fplstats.repositories.database.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "understatGamePlayer")
public class UnderstatGamePlayer{

    @Id
    @Column(name = "understatid")
    private int understatId;

    @Column(name = "understatgameid")
    private int understatGameId;

    @Column(name = "understatplayerid")
    private int understatPlayerId;

    @Column(name = "understatteamid")
    private int understatTeamId;

    @Column(name = "goals")
    private int goals;

    @Column(name = "shots")
    private int shots;

    @Column(name = "xG")
    private double xG;

    @Column(name = "time")
    private int time;

    @Column(name = "h_a")
    private String hA;

    @Column(name = "yellow_card")
    private int yellowCard;

    @Column(name = "red_card")
    private int redCard;

    @Column(name = "key_passes")
    private int keyPasses;

    @Column(name = "assists")
    private int assists;

    @Column(name = "xA")
    private double xA;

    @Column(name = "xGChain")
    private double xGChain;

    @Column(name = "xGBuildup")
    private double xGBuildup;

    @Column(name = "seasonId")
    private int seasonId;

    public int getUnderstatGameId() {
        return understatGameId;
    }

    public void setUnderstatGameId(int understatGameId) {
        this.understatGameId = understatGameId;
    }


    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
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

    public String gethA() {
        return hA;
    }

    public void sethA(String hA) {
        this.hA = hA;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(int yellowCard) {
        this.yellowCard = yellowCard;
    }

    public int getRedCard() {
        return redCard;
    }

    public void setRedCard(int redCard) {
        this.redCard = redCard;
    }

    public int getKeyPasses() {
        return keyPasses;
    }

    public void setKeyPasses(int keyPasses) {
        this.keyPasses = keyPasses;
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

    public int getUnderstatPlayerId() {
        return understatPlayerId;
    }

    public void setUnderstatPlayerId(int understatPlayerId) {
        this.understatPlayerId = understatPlayerId;
    }

    public int getUnderstatTeamId() {
        return understatTeamId;
    }

    public void setUnderstatTeamId(int understatTeamId) {
        this.understatTeamId = understatTeamId;
    }

    public int getUnderstatId() {
        return understatId;
    }

    public void setUnderstatId(int understatId) {
        this.understatId = understatId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }
}
