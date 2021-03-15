package com.henry.tesis.expirapp.Expirapp.realm.table;

public interface RealmTable {
    interface Product{
        String name="name";
        String cant="cant";
        String expirationDate="expirationDate";
        String threeDaysBefore = "threeDaysBefore";
        String sevenDaysBefore = "sevenDaysBefore";
        String fiveDaysBefore = "fiveDaysBefore";
    }
}
