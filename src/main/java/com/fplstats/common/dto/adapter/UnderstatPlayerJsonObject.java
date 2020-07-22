package com.fplstats.common.dto.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnderstatPlayerJsonObject{

    private int id;

    private String player_name;

    private String team_title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getTeam_title() {
        return team_title;
    }

    public void setTeam_title(String team_title) {
        this.team_title = team_title;
    }
}
