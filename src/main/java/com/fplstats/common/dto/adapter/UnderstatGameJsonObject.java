package com.fplstats.common.dto.adapter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnderstatGameJsonObject{

    private int id;

    private String home_team;

    private String away_team;

    private boolean isResult;

    private int homeGoals;

    private int awayGoals;

    private double homeXG;

    private double awayXG;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date datetime;

    @JsonProperty("h")
    private void unpackHomeTeam(Map<String,Object> h) {
        this.home_team = (String)h.get("title");
    }

    @JsonProperty("a")
    private void unpackAwayTeam(Map<String,Object> a) {
        this.away_team = (String)a.get("title");
    }

    @JsonProperty("goals")
    private void unpackHomeGoals(Map<String,Object> h) {
        this.homeGoals = Integer.parseInt(h.get("h").toString());
        this.awayGoals = Integer.parseInt(h.get("a").toString());
    }

    @JsonProperty("xG")
    private void unpackHomeXG(Map<String,Object> h) {
        this.homeXG = Double.parseDouble(h.get("h").toString());
        this.awayXG = Double.parseDouble(h.get("a").toString());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHome_team() {
        return home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public boolean isResult() {
        return isResult;
    }

    public void setResult(boolean result) {
        isResult = result;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public double getHomeXG() {
        return homeXG;
    }

    public double getAwayXG() {
        return awayXG;
    }
}
