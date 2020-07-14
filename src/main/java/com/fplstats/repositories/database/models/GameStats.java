package com.fplstats.repositories.database.models;

import javax.persistence.*;

@Entity
@Table(name = "GameStats")
public class GameStats{
    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    public int getId() {
        return Id;
    }
}
