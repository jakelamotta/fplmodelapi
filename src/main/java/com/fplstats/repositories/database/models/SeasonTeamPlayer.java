package com.fplstats.repositories.database.models;

import javax.persistence.*;

@Entity
@Table(name = "SeasonTeamPlayer")
public class SeasonTeamPlayer{
    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "seasonteam_id")
    private SeasonTeam seasonTeam;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "isactive")
    private boolean isActive;

    public int getId() {
        return Id;
    }

    public SeasonTeam getSeasonTeam() {
        return seasonTeam;
    }

    public void setSeasonTeam(SeasonTeam seasonTeam) {
        this.seasonTeam = seasonTeam;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isCurrent() {
        return isActive;
    }

    public void setCurrent(boolean current) {
        isActive = current;
    }
}
