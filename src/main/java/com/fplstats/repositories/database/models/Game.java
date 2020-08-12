package com.fplstats.repositories.database.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "game")
public class Game{

    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    @Column(name = "game_day")
    private Date gameDay;

    @ManyToOne
    @JoinColumn(name = "hometeam_id")
    private SeasonTeam homeTeam;

    @ManyToOne
    @JoinColumn(name = "awayteam_id")
    private SeasonTeam awayTeam;

    @Column(name = "homegoals")
    private int homeGoals;

    @Column(name = "homexG")
    private double homeXG;

    @Column(name = "awaygoals")
    private int awayGoals;

    @Column(name = "awayxG")
    private double awayXG;

    @Column(name = "hasbeenplayed")
    private boolean HasBeenPlayed;

    @Column(name = "understatid")
    private int understatId;


    public int getId() {
        return Id;
    }

    public SeasonTeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(SeasonTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    public SeasonTeam getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(SeasonTeam awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Date getGameDay() {
        return gameDay;
    }

    public void setGameDay(Date gameDay) {
        this.gameDay = gameDay;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public int getUnderstatId() {
        return understatId;
    }

    public void setUnderstatId(int understatId) {
        this.understatId = understatId;
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
