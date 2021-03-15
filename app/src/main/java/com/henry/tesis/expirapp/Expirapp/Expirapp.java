package com.henry.tesis.expirapp.Expirapp;


import android.app.Application;

import com.henry.tesis.expirapp.Expirapp.realm.module.ProductRealmModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Expirapp extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);RealmConfiguration configuration = new RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .name("product.realm")
                .build();
        Realm.setDefaultConfiguration(configuration);

    }

}
