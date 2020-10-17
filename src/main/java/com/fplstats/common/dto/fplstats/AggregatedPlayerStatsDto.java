package com.fplstats.common.dto.fplstats;

public class AggregatedPlayerStatsDto{

    private PlayerDto player;
    private TeamDto currentTeam;

    private double weightedNrOfMinutesPlayed;
    private double weightedNrOfGames;
    private double xG;
    private double xA;
    private double xYc;
    private double xRc;

    private int actualMinutesPlayed;
    private int actualNrOfGames;

    public PlayerDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDto player) {
        this.player = player;
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

    public TeamDto getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(TeamDto currentTeam) {
        this.currentTeam = currentTeam;
    }

    public double getWeightedNrOfMinutesPlayed() {
        return weightedNrOfMinutesPlayed;
    }

    public void incrementWeightedNrOfMinutesPlayed(double weightedNrOfMinutesPlayed) {
        this.weightedNrOfMinutesPlayed += weightedNrOfMinutesPlayed;
    }

    public double getWeightedNrOfGames() {
        return weightedNrOfGames;
    }

    public void incrementWeightedNrOfGames(double weightedNrOfGames) {
        this.weightedNrOfGames += weightedNrOfGames;
    }

    public int getActualMinutesPlayed() {
        return actualMinutesPlayed;
    }

    public void incrementActualMinutesPlayed(int actualMinutesPlayed) {
        this.actualMinutesPlayed += actualMinutesPlayed;
    }

    public int getActualNrOfGames() {
        return actualNrOfGames;
    }

    public void incrementActualNrOfGames(int actualNrOfGames) {
        this.actualNrOfGames += actualNrOfGames;
    }
}
