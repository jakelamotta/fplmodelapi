package com.fplstats.common.dto.fplstats;

public class SeasonTeamPlayerDto{

    private int Id;

    private SeasonTeamDto seasonTeam;

    private PlayerDto player;

    private boolean isActive;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public SeasonTeamDto getSeasonTeam() {
        return seasonTeam;
    }

    public void setSeasonTeam(SeasonTeamDto seasonTeam) {
        this.seasonTeam = seasonTeam;
    }

    public PlayerDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDto player) {
        this.player = player;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
