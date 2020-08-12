package com.fplstats.common.dto.fplstats;

import java.util.List;

public class AggregatedTeamStatsDto{

    private TeamDto teamDto;
    private double xGAgainst;
    private int cleanSheets;
    private List<GameDto> last25Games;
    private int nrOfGames;

    public AggregatedTeamStatsDto(){
        xGAgainst = 0.0;
        cleanSheets = 0;
    }

    public TeamDto getTeamDto() {
        return teamDto;
    }

    public void setTeamDto(TeamDto teamDto) {
        this.teamDto = teamDto;
    }

    public double getxGAgainst() {
        return xGAgainst;
    }

    public void incrementxGAgainst(double xGAgainst) {
        this.xGAgainst += xGAgainst;
    }

    public int getCleanSheets() {
        return cleanSheets;
    }

    public void incrementCleanSheets() {
        this.cleanSheets += 1;
    }

    public List<GameDto> getLast25Games() {
        return last25Games;
    }

    public void setLast25Games(List<GameDto> last25Games) {
        this.last25Games = last25Games;
    }

    public int getNrOfGames() {
        return nrOfGames;
    }

    public void incrementNrOfGames() {
        this.nrOfGames += 1;
    }
}
