package com.fplstats.common.dto.fplstats;

public class AggregatedPlayerStatsDto{

    private PlayerDto player;
    private TeamDto currentTeam;

    private double minutesPlayed;
    private double nrOfGames;
    private double xG;
    private double xA;
    private double xYc;
    private double xRc;

    public PlayerDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDto player) {
        this.player = player;
    }

    public double getMinutesPlayed() {
        return minutesPlayed;
    }

    public void incrementMinutesPlayed(double minutesPlayed) {
        this.minutesPlayed += minutesPlayed;
    }

    public double getxG() {
        return xG;
    }

    public void incrementxG(double xG) {
        this.xG +=  xG;
    }

    public double getxA() {
        return xA;
    }

    public void incrementxA(double xA) {
        this.xA += xA;
    }

    public double getNrOfGames() {
        return nrOfGames;
    }

    public void incrementNrOfGames(double increment) {
        this.nrOfGames += increment;
    }

    public TeamDto getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(TeamDto currentTeam) {
        this.currentTeam = currentTeam;
    }
}
