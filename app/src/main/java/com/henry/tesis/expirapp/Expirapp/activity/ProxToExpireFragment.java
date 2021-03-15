package com.henry.tesis.expirapp.Expirapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.henry.tesis.expirapp.Expirapp.R;
import com.henry.tesis.expirapp.Expirapp.realm.model.Product;
import com.henry.tesis.expirapp.Expirapp.utils.ProductRecyclerView;
import com.henry.tesis.expirapp.Expirapp.utils.ProxToExpireRecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProxToExpireFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private Realm myRealm;
    private RecyclerView recyclerView;
    private ProxToExpireRecyclerView proxToExpireRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    public ProxToExpireFragment() {
    }

    public static ProxToExpireFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ProxToExpireFragment fragment = new ProxToExpireFragment();
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
        if (proxToExpireRecyclerView != null)
            proxToExpireRecyclerView.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prox_to_expire, container, false);
        final RealmResults<Product> realmResults = myRealm.where(Product.class).findAll();
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todayString=date.format(formatter);
        Date today= null;
        try {
            today = new SimpleDateFormat("dd/MM/yyyy").parse(todayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RealmResults<Product> finalResults = myRealm.where(Product.class).lessThanOrEqualTo("sevenDaysBefore",today).findAll();
        System.out.println("resultado final"+finalResults);
       /* for (Product p:realmResults){
            System.out.println("fecha producto"+p.getSevenDaysBefore());
            System.out.println("fecha hoy"+today);
            long difference = p.getSevenDaysBefore().getTime() - today.getTime();
            System.out.println("diferencia"+ difference);
            float daysBetween = (difference / (1000*60*60*24));
            System.out.println("dias en medio"+daysBetween);
            if(daysBetween<1){
                /*Product realm=myRealm.createObject(Product.class);
                realm.setThreeDaysBefore(p.getThreeDaysBefore());
                realm.setFiveDaysBefore(p.getFiveDaysBefore());
                realm.setSevenDaysBefore(p.getSevenDaysBefore());
                realm.setCant(p.getCant());
                realm.setExpireDate(p.getExpireDate());
                realm.setName(p.getName());

            }
        }*/
        recyclerView =  (RecyclerView) view.findViewById(R.id.productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        proxToExpireRecyclerView = new ProxToExpireRecyclerView(this.getContext(),myRealm, finalResults);
        recyclerView.setAdapter(proxToExpireRecyclerView);
        return view;
    }
}
