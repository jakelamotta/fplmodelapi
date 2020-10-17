package com.fplstats.repositories.database.models;

import javax.persistence.*;


@Entity
@Table(name = "fplplayer")
public class FplPlayer{

    @Id
    @Column(name = "fplid")
    private int FplId;

    public FplPlayer() {
    }

    public int getFplId() {
        return FplId;
    }

    public void setFplId(int fplId) {
        FplId = fplId;
    }

    @Column(name = "element_type")
    private int ElementType;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String SecondName;

    @Column(name = "now_cost")
    private double nowCost;

    @Column(name = "selected_by_percent")
    private double selectedByPercent;

    @Column(name = "saves")
    private int saves;

    @Column(name = "bonus")
    private int bonus;

    @Column(name= "bps")
    private int Bps;

    public int getElementType() {
        return ElementType;
    }

    public void setElementType(int elementType) {
        ElementType = elementType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public double getNowCost() {
        return nowCost;
    }

    public void setNowCost(double nowCost) {
        this.nowCost = nowCost;
    }

    public double getSelectedByPercent() {
        return selectedByPercent;
    }

    public void setSelectedByPercent(double selectedByPercent) {
        this.selectedByPercent = selectedByPercent;
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
        return Bps;
    }

    public void setBps(int bps) {
        Bps = bps;
    }

}
