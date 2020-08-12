package com.fplstats.common.dto.fplstats;

import com.fplstats.repositories.database.models.SeasonTeamPlayer;

public class AggregatedGamesDto{

    private SeasonTeamDto seasonTeam;

    private int goals;
    private int goalsAgainst;
    private double xG;
    private double xGAgainst;

    public SeasonTeamDto getSeasonTeam() {
        return seasonTeam;
    }

    public void setSeasonTeam(SeasonTeamDto seasonTeam) {
        this.seasonTeam = seasonTeam;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public double getxG() {
        return xG;
    }

    public void setxG(double xG) {
        this.xG = xG;
    }

    public double getxGAgainst() {
        return xGAgainst;
    }

    public void setxGAgainst(double xGAgainst) {
        this.xGAgainst = xGAgainst;
    }
}
