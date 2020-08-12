package com.fplstats.common.dto.fplstats;

import com.fplstats.repositories.database.models.Position;

public class CalculatedPlayerStatisticsDto{

    private String playerName;

    private String teamName;

    private PositionDto position;

    private double minutesPlayed;

    private double nrOfGames;

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

        if (minutesPlayed == 0 || nrOfGames == 0){
            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
        }

        if (this.getPlayerName() == "Conor Coady"){
            this.setPlayerName("Conor Coady");
        }

        xP = xP/cost;
        xP = xP/nrOfGames;
        this.xPPoundGame += xP;
    }

    public void setxPPoundGame(double xP) {

        if (minutesPlayed == 0 || nrOfGames == 0){
            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
        }

        xP = xP/cost;
        xP = xP/nrOfGames;
        this.xPPoundGame = xP;
    }

    public double getxPAbs() {
        return xPAbs;
    }

    public void setxPAbs(double xPAbs) {

        if (minutesPlayed == 0 || nrOfGames == 0){
            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
        }

        this.xPAbs = xPAbs/nrOfGames;
    }

    public void incrementxPAbs(double pxAbs){
        if (minutesPlayed == 0 || nrOfGames == 0){
            throw new NullPointerException("Minutesplayed and nrOfGames cannot be 0");
        }

        this.xPAbs += xPAbs/nrOfGames;
    }

    public double getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(double minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public double getNrOfGames() {
        return nrOfGames;
    }

    public void setNrOfGames(double nrOfGames) {
        this.nrOfGames = nrOfGames;
    }

    public double getxPPoundGame() {
        return xPPoundGame;
    }
}
