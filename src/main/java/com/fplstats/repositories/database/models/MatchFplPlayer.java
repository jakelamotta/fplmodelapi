package com.fplstats.repositories.database.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "match_FplPlayer")
public class MatchFplPlayer implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "fplplayer_fplid")
    private FplPlayer fplPlayer;

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;


    public FplPlayer getFplPlayer() {
        return fplPlayer;
    }

    public void setFplPlayer(FplPlayer fplPlayer) {
        this.fplPlayer = fplPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
