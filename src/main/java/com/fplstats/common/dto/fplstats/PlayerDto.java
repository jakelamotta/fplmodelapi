package com.fplstats.common.dto.fplstats;

import javax.persistence.Column;

public class PlayerDto{
    private int Id;

    private String name;

    private double cost;

    private int fplId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstname, String secondname) {
        this.name = firstname + " " + secondname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getFplId() {
        return fplId;
    }

    public void setFplId(int fplId) {
        this.fplId = fplId;
    }
}
