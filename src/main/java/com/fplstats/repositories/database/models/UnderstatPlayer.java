package com.fplstats.repositories.database.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="understatPlayer")
public class UnderstatPlayer{

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
