package com.fplstats.repositories.database.models;

import javax.persistence.*;

@Entity
@Table(name="understatPlayer")
public class UnderstatPlayer{

    @javax.persistence.Id
    @Column(name = "understatid")
    private int understatId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "team_title")
    private String teamTitle;

    public int getUnderstatId() {
        return understatId;
    }

    public void setUnderstatId(int understatId) {
        this.understatId = understatId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamTitle() {
        return teamTitle;
    }

    public void setTeamTitle(String teamTitle) {
        this.teamTitle = teamTitle;
    }
}
