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

    @Column(name = "hasbeenplayed")
    private boolean HasBeenPlayed;

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
}
