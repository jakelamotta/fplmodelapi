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

    public int getId() {
        return Id;
    }

    @Column(name = "startYear")
    private int startYear;

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    public League getLeague() {
        return league;
    }

    public void setLeagueId(League league) {
        this.league = league;
    }
}
