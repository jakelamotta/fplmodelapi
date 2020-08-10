package com.fplstats.repositories.database.models;

import javax.persistence.*;

@Entity
@Table(name = "Team")
public class Team{

    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    public int getId() {
        return Id;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "understatid")
    private int understatid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnderstatid() {
        return understatid;
    }

    public void setUnderstatid(int understatid) {
        this.understatid = understatid;
    }
}
