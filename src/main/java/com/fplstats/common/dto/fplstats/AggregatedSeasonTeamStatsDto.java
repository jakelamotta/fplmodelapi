package com.fplstats.common.dto.fplstats;

public class AggregatedSeasonTeamStatsDto{

    private SeasonTeamDto seasonTeamDto;
    private double xGAgainst;
    private int cleanSheets;
    private int nrOfGames;

    public AggregatedSeasonTeamStatsDto(){
        this.xGAgainst =0.0;
        this.cleanSheets = 0;
        this.nrOfGames = 0;
    }

    public void incrementXGAgainst(double xGAgainst){
        this.xGAgainst += xGAgainst;
    }

    public SeasonTeamDto getSeasonTeamDto() {
        return seasonTeamDto;
    }

    public void setSeasonTeamDto(SeasonTeamDto seasonTeamDto) {
        this.seasonTeamDto = seasonTeamDto;
    }

    public double getxGAgainst() {
        return xGAgainst;
    }

    public int getCleanSheets() {
        return cleanSheets;
    }

    public int getNrOfGames() {
        return nrOfGames;
    }

    public void incrementCleanSheets(int cleanSheets) {
        this.cleanSheets += cleanSheets;
    }

    public void incrementNrOfGames(int nrOfGames) {
        this.nrOfGames += nrOfGames;
    }
}
