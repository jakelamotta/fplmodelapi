package com.fplstats.common.dto.fplstats;

import com.fplstats.repositories.database.models.Season;

public class SeasonTeamDto{

    private SeasonDto seasonDto;

    private TeamDto team;

    public SeasonDto getSeason() {
        return seasonDto;
    }

    public void setSeason(SeasonDto seasonDto) {
        this.seasonDto = seasonDto;
    }

    public TeamDto getTeam() {
        return team;
    }

    public void setTeam(TeamDto team) {
        this.team = team;
    }
}
