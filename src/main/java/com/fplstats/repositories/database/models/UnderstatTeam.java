package com.fplstats.repositories.database.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "understatTeam")
public class UnderstatTeam{

    @javax.persistence.Id
    @Column(name = "understatid")
    private int understatId;

    public int getUnderstatId() {
        return understatId;
    }

    public void setUnderstatId(int understatId) {
        this.understatId = understatId;
    }
}
