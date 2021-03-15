package com.henry.tesis.expirapp.Expirapp.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.henry.tesis.expirapp.Expirapp.R;
import com.henry.tesis.expirapp.Expirapp.realm.model.Product;
import com.tfb.fbtoast.FBToast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProxToExpireRecyclerView extends RecyclerView.Adapter<ProxToExpireRecyclerView.Holders>{

    private Context context;
    private Realm realm;
    private RealmResults<Product> realmResults;
    private LayoutInflater inflater;

    public ProxToExpireRecyclerView(Context context ,Realm realm,RealmResults<Product> realmResults) {
        this.context = context;
        this.realm= realm;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ProxToExpireRecyclerView.Holders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.prox_to_expire_list, parent, false);
        ProxToExpireRecyclerView.Holders holders = new ProxToExpireRecyclerView.Holders(view);
        return holders;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ProxToExpireRecyclerView.Holders holder, int position) {

        Product product = realmResults.get(position);
        holder.setProduct(product, position);
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class Holders extends RecyclerView.ViewHolder {

        private int position;
        private TextView product_name, cant, expire,restDays;

        public Holders(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_text_view);
            cant = itemView.findViewById(R.id.cant_text_view);
            expire = itemView.findViewById(R.id.expire_text_view);
            restDays = itemView.findViewById(R.id.rest_days);

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void setProduct(Product product, int position) {

            this.position = position;
            String name = product.getName();
            int cantidad =  product.getCant();
            Date expireDate = product.getExpireDate();
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String todayString=date.format(formatter);
            Date today= null;
            try {
                today = new SimpleDateFormat("dd/MM/yyyy").parse(todayString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long difference = product.getExpireDate().getTime() - today.getTime();
            long daysBetween = (difference / (1000*60*60*24));
            int days=Integer.valueOf((int) daysBetween);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            product_name.setText(name);
            cant.setText(String.valueOf(cantidad));
            expire.setText(dateFormat.format(expireDate));
            if(days<5){
                restDays.setBackgroundColor(Color.parseColor("#e4df3f"));
            }
            restDays.setText("Quedan: "+String.valueOf(days)+"dias");
        }


    }
}
