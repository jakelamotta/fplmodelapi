package com.fplstats.repositories.database.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "costhistory")
public class CostHistory{

    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "changedate")
    private Date date;

    @Column(name = "newcost")
    private double newCost;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getNewCost() {
        return newCost;
    }

    public void setNewCost(double newCost) {
        this.newCost = newCost;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
