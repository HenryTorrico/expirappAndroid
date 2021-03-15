package com.henry.tesis.expirapp.Expirapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.henry.tesis.expirapp.Expirapp.R;
import com.henry.tesis.expirapp.Expirapp.realm.model.Product;
import com.henry.tesis.expirapp.Expirapp.utils.ProductRecyclerView;
import com.henry.tesis.expirapp.Expirapp.utils.ProxToExpireRecyclerView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private Realm myRealm;
    private RecyclerView recyclerView;
    private ProductRecyclerView productRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton addProductButton;

    public ProductFragment() {
    }

    public static ProductFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        myRealm = Realm.getDefaultInstance();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (productRecyclerView != null)
            productRecyclerView.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        final RealmResults<Product> realmResults = myRealm.where(Product.class).findAll();
        recyclerView =  (RecyclerView) view.findViewById(R.id.productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productRecyclerView = new ProductRecyclerView(this.getContext(),myRealm, realmResults);
        recyclerView.setAdapter(productRecyclerView);
        addProductButton=view.findViewById(R.id.addProductButton);
        addProductButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent activity=new Intent(getContext(),NewProductActivity.class);
                activity.putExtra("objectDetected","");
                activity.putExtra("date","");
                startActivity(activity);
            }
        });
        return view;
    }

}