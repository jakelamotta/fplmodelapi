package com.fplstats.repositories.database.models;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "match_understatteam")
public class MatchUnderstatTeam implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "understatteam_understatid")
    private UnderstatTeam understatTeam;

    @Id
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
