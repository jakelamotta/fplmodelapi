package com.fplstats.common.dto.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FplJsonObject {

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
        this.now_cost = now_cost/10;
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
