package com.fplstats.repositories.database.models;

import javax.persistence.*;

@Entity
@Table(name = "Game")
public class Game{

    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    public int getId() {
        return Id;
    }

    @ManyToOne
    @JoinColumn(name = "hometeam_id")
    private SeasonTeam homeTeam;

    @ManyToOne
    @JoinColumn(name = "awayteam_id")
    private SeasonTeam awayTeam;
}
