package com.fplstats.repositories.database.models;

import javax.persistence.*;

@Entity
@Table(name = "understatTeam")
public class UnderstatTeam{

    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "understatid")
    private int understatId;

    @Column(name = "seasonid")
    private int seasonId;

    @Column(name = "name")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
