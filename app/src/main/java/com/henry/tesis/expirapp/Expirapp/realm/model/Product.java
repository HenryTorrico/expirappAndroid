package com.henry.tesis.expirapp.Expirapp.realm.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Product extends RealmObject {

    @Required
    private String name;

    private int cant;
    private Date expireDate;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", cant=" + cant +
                ", expireDate=" + expireDate +
                '}';
    }

    public Product(String name, int cant, Date expireDate) {
        this.cant=cant;
        this.name = name;
        this.expireDate = expireDate;
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


}
