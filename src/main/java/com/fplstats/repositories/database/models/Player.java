package com.fplstats.repositories.database.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Player")
public class Player{

    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private double cost;

    @Column(name = "fplid")
    private int fplId;

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

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
