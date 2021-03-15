package com.henry.tesis.expirapp.Expirapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.henry.tesis.expirapp.Expirapp.R;
import com.henry.tesis.expirapp.Expirapp.realm.model.Product;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tfb.fbtoast.FBToast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class NewProductActivity extends AppCompatActivity {

    private EditText productName, cantProduct, expireDate;
    private Realm myRealm;
    private RealmAsyncTask realmAsyncTask;
    private ImageButton cameraButton,expireButton;
    public String object;
    public String date;
    String reg0 = "^[0-3]?[0-9]..[0-3]?[0-9]..(?:[0-9]{2})?[0-9]{2}$";
    String reg1 ="^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
    String reg2= "[0-9]{1,2}/[a-zA-Z]{3}/[0-9]{2}";
    String format0= "dd.MM.yy";
    String format1= "dd/MM/yy";
    String format2= "dd/MMM/yy";
    Pattern pattern0 = Pattern.compile(reg0);
    Pattern pattern1 = Pattern.compile(reg1);
    Pattern pattern2=Pattern.compile(reg2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        object=getIntent().getExtras().getString("objectDetected");
        date=getIntent().getExtras().getString("date");
        cameraButton=findViewById(R.id.cameraButton);
        productName = findViewById(R.id.product_edit_text);
        cantProduct = findViewById(R.id.cant_edit_text);
        expireDate = findViewById(R.id.expire_edit_text);
        expireButton = findViewById(R.id.dateButton);
        myRealm = Realm.getDefaultInstance();
        productName.setText(object);
        SimpleDateFormat sdf = new SimpleDateFormat();

        if(!date.equals("")) {
            if (date.matches(reg1)) {
                sdf = new SimpleDateFormat(format1);
            } else {
                if (date.matches(reg0)) {
                    sdf = new SimpleDateFormat(format0);
                } else {
                    if (date.matches(reg2)) {
                        sdf = new SimpleDateFormat(format2);
                    }
                }
            }
            /*SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String strDate=formatter.format(date);
            System.out.println("fecha final"+strDate);*/
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
            String newDateString;
            Date d = new Date();
            try {
                d = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            newDateString = newFormat.format(d);
            expireDate.setText(newDateString);
        }
        else{
            expireDate.setText(date);
        }
        cameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent activity=new Intent(NewProductActivity.this,ClassifierActivity.class);
                activity.putExtra("date",date);
                startActivity(activity);
            }
        });
        expireButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent activity=new Intent(NewProductActivity.this,StillImageActivity.class);
                activity.putExtra("objectDetected",object);
                startActivityForResult(activity,1);
            }
        });

    }

    private void insertRecords() {

        final String productString = productName.getText().toString().trim();
        final String cantString = cantProduct.getText().toString().trim();
        final String expireString = expireDate.getText().toString().trim();


        if (productString.isEmpty()) {
            FBToast.successToast(NewProductActivity.this,
                    "Ingrese el nombre del producto"
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (cantString.isEmpty()) {
            FBToast.successToast(NewProductActivity.this,
                    "Ingrese la cantidad"
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (expireString.isEmpty()) {
            FBToast.successToast(NewProductActivity.this,
                    "Ingrese la fecha de expiracion"
                    , FBToast.LENGTH_SHORT);
            return;
        }

        realmAsyncTask = myRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Product product = realm.createObject(Product.class);
                        product.setName(productString);
                        product.setCant(Integer.parseInt(cantString));
                        try {
                            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(expireString);
                            product.setExpireDate(date);
                            Calendar calendar7Days = Calendar.getInstance();
                            Calendar calendar5Days = Calendar.getInstance();
                            Calendar calendar3Days = Calendar.getInstance();
                            calendar7Days.setTime(date);
                            calendar7Days.add(Calendar.DAY_OF_YEAR, -7);
                            Date sevenDays = calendar7Days.getTime();
                            calendar5Days.setTime(date);
                            calendar5Days.add(Calendar.DAY_OF_YEAR,-5);
                            Date fiveDays =calendar5Days.getTime();
                            calendar3Days.setTime(date);
                            calendar3Days.add(Calendar.DAY_OF_YEAR,-3);
                            Date threeDays =calendar3Days.getTime();
                            product.setThreeDaysBefore(threeDays);
                            product.setFiveDaysBefore(fiveDays);
                            product.setSevenDaysBefore(sevenDays);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                        FBToast.successToast(NewProductActivity.this,
                                "Insert Record Successfully ..."
                                , FBToast.LENGTH_SHORT);

                        productName.setText("");
                        cantProduct.setText("");
                        expireDate.setText("");
                        startActivity(new Intent(NewProductActivity.this, MainActivity.class));

                    }
                },

                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {

                        FBToast.errorToast(NewProductActivity.this,
                                "Error Is Insert Record ..."
                                , FBToast.LENGTH_SHORT);
                    }
                });


    }

    public void addBooks(View view) {
        insertRecords();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (realmAsyncTask != null && realmAsyncTask.isCancelled()) {
            realmAsyncTask.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}