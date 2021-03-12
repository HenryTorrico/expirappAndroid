package com.henry.tesis.expirapp.Expirapp.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfb.fbtoast.FBToast;

import com.henry.tesis.expirapp.Expirapp.R;
import com.henry.tesis.expirapp.Expirapp.activity.NewProductActivity;
import com.henry.tesis.expirapp.Expirapp.realm.model.Product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.RealmModule;

public class ProductRecyclerView extends RecyclerView.Adapter<ProductRecyclerView.Holders> {

    private Context context;
    private Realm realm;
    private RealmResults<Product> realmResults;
    private LayoutInflater inflater;


    public ProductRecyclerView(Context context ,Realm realm,RealmResults<Product> realmResults) {
        this.context = context;
        this.realm= realm;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.product_list_layout, parent, false);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull Holders holder, int position) {

        Product product = realmResults.get(position);
        holder.setProduct(product, position);
        holder.setListener();
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class Holders extends RecyclerView.ViewHolder {

        private int position;
        private TextView product_name, cant, expire;
        private ImageView editImage, deleteImage;

        public Holders(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_text_view);
            cant = itemView.findViewById(R.id.cant_text_view);
            expire = itemView.findViewById(R.id.expire_text_view);
            editImage = itemView.findViewById(R.id.edit_image_view);
            deleteImage = itemView.findViewById(R.id.delete_image_view);

        }

        public void setProduct(Product product, int position) {

            this.position = position;
            String name = product.getName();
            int cantidad =  product.getCant();
            Date expireDate = product.getExpireDate();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            product_name.setText(name);
            cant.setText(String.valueOf(cantidad));
            expire.setText(dateFormat.format(expireDate));


        }

        public void setListener() {

            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realmResults.deleteFromRealm(position);
                            FBToast.errorToast(context,
                                    "Producto: "+product_name.getText().toString()+" borrado correctamente"
                                    , FBToast.LENGTH_SHORT);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, realmResults.size());
                        }
                    });

                }
            });

        }

    }
}
