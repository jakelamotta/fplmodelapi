package com.fplstats.repositories.database.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "understatGame")
public class UnderstatGame{

    @javax.persistence.Id
    @Column(name = "understatid")
    private int understatId;

    @Column(name = "seasonid")
    private int seasonId;

    @Column(name = "isResult")
    private boolean isResult;

    @Column(name = "homeTeamName")
    private String homeTeamName;

    @Column(name = "awayTeamName")
    private String awayTeamName;

    @Column(name = "homegoals")
    private int homeGoals;

    @Column(name = "homexG")
    private double homeXG;

    @Column(name = "awaygoals")
    private int awayGoals;

    @Column(name = "awayxG")
    private double awayXG;

    @Column(name = "datetime")
    @Temporal(TemporalType.DATE)
    private Date datetime;

    public int getUnderstatId() {
        return understatId;
    }

    public void setUnderstatId(int understatId) {
        this.understatId = understatId;
    }

    public boolean isResult() {
        return isResult;
    }

    public void setResult(boolean result) {
        isResult = result;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public double getHomeXG() {
        return homeXG;
    }

    public void setHomeXG(double homeXG) {
        this.homeXG = homeXG;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public double getAwayXG() {
        return awayXG;
    }

    public void setAwayXG(double awayXG) {
        this.awayXG = awayXG;
    }
}
