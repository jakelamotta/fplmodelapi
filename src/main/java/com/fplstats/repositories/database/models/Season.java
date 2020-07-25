package com.fplstats.repositories.database.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "season")
public class Season {

    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "startYear")
    private int startYear;

    @Column(name = "isactive")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    public int getId() {
        return Id;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }


    public League getLeague() {
        return league;
    }

    public void setLeagueId(League league) {
        this.league = league;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
