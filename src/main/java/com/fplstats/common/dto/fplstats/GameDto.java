package com.fplstats.common.dto.fplstats;

import com.fplstats.repositories.database.models.Season;
import com.fplstats.repositories.database.models.SeasonTeam;

import javax.persistence.*;
import java.util.Date;

public class GameDto{

    private int id;
    private int understatid;
    private SeasonTeamDto awayTeam;
    private SeasonTeamDto homeTeam;
    private Date date;
    private SeasonDto season;
    private int homeGoals;
    private double homeXG;
    private int awayGoals;
    private double awayXG;

    public int getUnderstatid() {
        return understatid;
    }

    public void setUnderstatid(int understatid) {
        this.understatid = understatid;
    }

    public SeasonTeamDto getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(SeasonTeamDto awayTeam) {
        this.awayTeam = awayTeam;
    }

    public SeasonTeamDto getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(SeasonTeamDto homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SeasonDto getSeason() {
        return season;
    }

    public void setSeason(SeasonDto season) {
        this.season = season;
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
