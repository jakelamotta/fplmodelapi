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
}
