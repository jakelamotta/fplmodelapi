package com.fplstats.repositories.database.models;

import javax.persistence.*;

@Entity
@Table(name = "SeasonTeamPlayer")
public class SeasonTeamPlayer{
    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    public int getId() {
        return Id;
    }

    @ManyToOne
    @JoinColumn(name = "seasonteam_id")
    private SeasonTeam seasonTeam;

    public SeasonTeam getSeasonTeam() {
        return seasonTeam;
    }

    public void setSeasonTeam(SeasonTeam seasonTeam) {
        this.seasonTeam = seasonTeam;
    }

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "cost")
    private double cost;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
