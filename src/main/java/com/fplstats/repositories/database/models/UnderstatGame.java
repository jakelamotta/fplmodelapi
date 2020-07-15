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

    @Column(name = "homeTeamId")
    private boolean homeTeamId;

    @Column(name = "awayTeamId")
    private boolean awayTeamId;

    @Column(name = "datetime")
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

    public boolean isHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(boolean homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public boolean isAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(boolean awayTeamId) {
        this.awayTeamId = awayTeamId;
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
}
