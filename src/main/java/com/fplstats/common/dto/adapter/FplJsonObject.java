package com.fplstats.common.dto.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FplJsonObject {

    //{"chance_of_playing_next_round":100,"chance_of_playing_this_round":100,"code":154043,"cost_change_event":0,"cost_change_event_fall":0,"cost_change_start":-5,"cost_change_start_fall":5,"dreamteam_count":1,"element_type":2,"ep_next":"0.9","ep_this":"1.4","event_points":0,"first_name":"Ainsley","form":"0.4","id":4,"in_dreamteam":false,"news":"","news_added":"2019-09-22T18:00:10.824841Z","now_cost":45,"photo":"154043.jpg","points_per_game":"2.1","second_name":"Maitland-Niles","selected_by_percent":"2.2","special":false,"squad_number":null,"status":"a","team":1,"team_code":3,"total_points":40,"transfers_in":609637,"transfers_in_event":398,"transfers_out":652487,"transfers_out_event":154,"value_form":"0.1","value_season":"8.9","web_name":"Maitland-Niles","minutes":1292,"goals_scored":0,"assists":2,"clean_sheets":3,"goals_conceded":20,"own_goals":0,"penalties_saved":0,"penalties_missed":0,"yellow_cards":4,"red_cards":1,"saves":0,"bonus":3,"bps":229,"influence":"282.6","creativity":"159.5","threat":"37.0","ict_index":"47.3","influence_rank":243,"influence_rank_type":97,"creativity_rank":227,"creativity_rank_type":57,"threat_rank":364,"threat_rank_type":133,"ict_index_rank":308,"ict_index_rank_type":103}
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getElement_type() {
        return element_type;
    }

    public void setElement_type(int element_type) {
        this.element_type = element_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public double getNow_cost() {
        return now_cost;
    }

    public void setNow_cost(double now_cost) {
        this.now_cost = now_cost;
    }

    public double getSelected_by_percent() {
        return selected_by_percent;
    }

    public void setSelected_by_percent(double selected_by_percent) {
        this.selected_by_percent = selected_by_percent;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getBps() {
        return bps;
    }

    public void setBps(int bps) {
        this.bps = bps;
    }

    private int element_type;

    private String first_name;

    private String second_name;

    private double now_cost;

    private double selected_by_percent;

    private int saves;

    private int bonus;

    private int bps;
}
