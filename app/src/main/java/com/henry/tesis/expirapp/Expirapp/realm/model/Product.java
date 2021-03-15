package com.henry.tesis.expirapp.Expirapp.realm.model;

import androidx.annotation.NonNull;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Product extends RealmObject {

    @Required
    private String name;

    private int cant;
    private Date expireDate;
    private Date sevenDaysBefore;
    private Date fiveDaysBefore;
    private Date threeDaysBefore;

    public Product(String name, int cant, Date expireDate, Date sevenDaysBefore, Date fiveDaysBefore, Date threeDaysBefore) {
        this.cant=cant;
        this.name = name;
        this.expireDate = expireDate;
        this.sevenDaysBefore = sevenDaysBefore;
        this.fiveDaysBefore = fiveDaysBefore;
        this.threeDaysBefore = threeDaysBefore;
    }

    public Product(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }


    public Date getSevenDaysBefore() {
        return sevenDaysBefore;
    }

    public void setSevenDaysBefore(Date sevenDaysBefore) {
        this.sevenDaysBefore = sevenDaysBefore;
    }

    public Date getFiveDaysBefore() {
        return fiveDaysBefore;
    }

    public void setFiveDaysBefore(Date fiveDaysBefore) {
        this.fiveDaysBefore = fiveDaysBefore;
    }

    public Date getThreeDaysBefore() {
        return threeDaysBefore;
    }

    public void setThreeDaysBefore(Date threeDaysBefore) {
        this.threeDaysBefore = threeDaysBefore;
    }
}
