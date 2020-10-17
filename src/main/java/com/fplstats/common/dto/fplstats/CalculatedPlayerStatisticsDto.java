package com.fplstats.common.dto.fplstats;

import com.fplstats.repositories.database.models.Position;

public class CalculatedPlayerStatisticsDto{

    private String playerName;

    private String teamName;

    private PositionDto position;

    private double weightedMinutesPlayed;

    private double weightedNrOfGames;

    private int minutesPlayed;

    private int nrOfGames;



    private double cost;

    //xP per pound and game. fraction of average playing time. (xP/pound)/(minutesplayed * nrOfGames*90)
    private double xPPoundGame;

    private double xPAbs;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public PositionDto getPosition() {
        return position;
    }

    public void setPosition(PositionDto position) {
        this.position = position;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }


//    public void setxPPound90(double xP) {
//
//        if (minutesPlayed == 0 || nrOfGames == 0){
//            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
//        }
//
//        xP = xP/cost;
//        xP = xP/(minutesPlayed/(90*nrOfGames));
//        this.xPPound90 = xP;
//    }
//
//    public void incrementXPPound90(double xP){
//
//        if (minutesPlayed == 0 || nrOfGames == 0){
//            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
//        }
//
//        xP = xP/cost;
//        xP = xP/(minutesPlayed/(90*nrOfGames));
//        this.xPPound90 += xP;
//    }

    public void incrementXPGame(double xP){

        if (weightedMinutesPlayed == 0 || weightedNrOfGames == 0){
            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
        }

        xP = xP/cost;
        xP = xP/weightedNrOfGames;
        this.xPPoundGame += xP;
    }

    public void setxPPoundGame(double xP) {

        if (weightedMinutesPlayed == 0 || weightedNrOfGames == 0){
            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
        }

        xP = xP/cost;
        xP = xP/weightedNrOfGames;
        this.xPPoundGame = xP;
    }

    public double getxPAbs() {
        return xPAbs;
    }

    public void setxPAbs(double xPAbs) {

        if (weightedMinutesPlayed == 0 || weightedNrOfGames == 0){
            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
        }

        this.xPAbs = xPAbs/weightedNrOfGames;
    }

    public void setxPAbs2(double xPAbs) {

        this.xPAbs = xPAbs;
    }

    public void incrementxPAbs(double pxAbs){
        if (weightedMinutesPlayed == 0 || weightedNrOfGames == 0){
            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
        }

        this.xPAbs += xPAbs/weightedNrOfGames;
    }

    public double getxPPoundGame() {
        return xPPoundGame;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public int getNrOfGames() {
        return nrOfGames;
    }

    public void setNrOfGames(int nrOfGames) {
        this.nrOfGames = nrOfGames;
    }

    public void setWeightedMinutesPlayed(double weightedMinutesPlayed) {
        this.weightedMinutesPlayed = weightedMinutesPlayed;
    }

    public void setWeightedNrOfGames(double weightedNrOfGames) {
        this.weightedNrOfGames = weightedNrOfGames;
    }
}
