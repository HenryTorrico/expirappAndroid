package com.henry.tesis.expirapp.Expirapp.realm.repository;

import com.henry.tesis.expirapp.Expirapp.Expirapp;

import io.realm.RealmQuery;
import io.realm.RealmResults;
import com.henry.tesis.expirapp.Expirapp.realm.model.Product;

import java.util.Date;

import io.realm.Realm;
import com.henry.tesis.expirapp.Expirapp.realm.table.RealmTable;

public class ProductRepository extends IProductRepository {
    Realm realm = Realm.getDefaultInstance();

    @Override
    public void addProduct(Product university, OnAddProductCallback callback) {
        realm.beginTransaction();
        Product u = realm.createObject(Product.class);
        u.setName("Manzana Muy roja");
        u.setCant(5);
        u.setExpireDate(new Date());
        realm.commitTransaction();
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteProductById(String Id, OnDeleteProductCallback callback) {
        realm.beginTransaction();
        Product product = realm.where(Product.class).equalTo(RealmTable.Product.name, Id).findFirst();
        product.deleteFromRealm();
        realm.commitTransaction();
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteProductByPosition(int position, OnDeleteProductCallback callback) {
        realm.beginTransaction();
        RealmQuery<Product> query = realm.where(Product.class);
        RealmResults<Product> results = query.findAll();
        results.remove(position);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getProductById(String id, OnGetProductByIdCallback callback) {
        Product result = realm.where(Product.class).equalTo(RealmTable.Product.name, id).findFirst();

        if (callback != null)
            callback.onSuccess(result);
    }

    @Override
    public void getAllProducts(OnGetAllProductCallback callback) {
        RealmQuery<Product> query = realm.where(Product.class);
        RealmResults<Product> results = query.findAll();

        if (callback != null)
            callback.onSuccess(results);
    }

}

