package com.fplstats.common.dto.fplstats;

import com.fplstats.repositories.database.models.Season;
import com.fplstats.repositories.database.models.SeasonTeam;

import java.util.Date;

public class UnderstatPlayerDto{

    private int id;

    private String name;

    private String teamTitle;

    private UnderstatPlayerSeasonDto understatPlayerSeasonDto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamTitle() {
        return teamTitle;
    }

    public void setTeamTitle(String teamTitle) {
        this.teamTitle = teamTitle;
    }

    public UnderstatPlayerSeasonDto getUnderstatPlayerSeasonDto() {
        return understatPlayerSeasonDto;
    }

    public void setUnderstatPlayerSeasonDto(UnderstatPlayerSeasonDto understatPlayerSeasonDto) {
        this.understatPlayerSeasonDto = understatPlayerSeasonDto;
    }
}
