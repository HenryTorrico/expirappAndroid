package com.henry.tesis.expirapp.Expirapp.realm.repository;

import com.henry.tesis.expirapp.Expirapp.realm.model.Product;

import io.realm.RealmResults;

public abstract class IProductRepository  {
    interface OnAddProductCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetAllProductCallback {
        void onSuccess(RealmResults<Product> universities);
        void onError(String message);
    }

    interface OnGetProductByIdCallback {
        void onSuccess(Product university);
        void onError(String message);
    }

    interface OnDeleteProductCallback{
        void onSuccess();
        void onError(String message);
    }

    abstract void addProduct(Product university, OnAddProductCallback callback);

    abstract void deleteProductById(String Id, OnDeleteProductCallback callback);

    abstract void deleteProductByPosition(int position, OnDeleteProductCallback callback);

    abstract void getAllProducts(OnGetAllProductCallback callback);

    abstract void getProductById(String id, OnGetProductByIdCallback callback);
}
