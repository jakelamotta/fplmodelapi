package com.fplstats.repositories.database.models;

import javax.persistence.*;


@Entity
@Table(name = "understatTeam")
public class UnderstatTeam{

    @javax.persistence.Id
    @Column(name = "understatid")
    private int understatId;

    @Column(name = "seasonid")
    private int seasonId;

    @Column(name = "title")
    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
