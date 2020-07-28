package com.fplstats.repositories.database.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "understatplayerseason")
public class UnderstatPlayerSeason implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "understatplayerid")
    private UnderstatPlayer understatPlayer;

    @Id
    @ManyToOne
    @JoinColumn(name = "seasonId")
    private Season season;

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public UnderstatPlayer getUnderstatPlayer() {
        return understatPlayer;
    }

    public void setUnderstatPlayer(UnderstatPlayer understatPlayer) {
        this.understatPlayer = understatPlayer;
    }
}
