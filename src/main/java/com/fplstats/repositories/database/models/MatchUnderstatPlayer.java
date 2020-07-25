package com.fplstats.repositories.database.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "match_understatplayer")
public class MatchUnderstatPlayer implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "understatplayer_understatid")
    private UnderstatPlayer understatPlayer;

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public UnderstatPlayer getUnderstatPlayer() {
        return understatPlayer;
    }

    public void setUnderstatPlayer(UnderstatPlayer understatPlayer) {
        this.understatPlayer = understatPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
