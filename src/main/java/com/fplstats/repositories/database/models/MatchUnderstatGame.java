package com.fplstats.repositories.database.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "match_understatgame")
public class MatchUnderstatGame implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "understatgame_understatid")
    private UnderstatGame understatGame;

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
