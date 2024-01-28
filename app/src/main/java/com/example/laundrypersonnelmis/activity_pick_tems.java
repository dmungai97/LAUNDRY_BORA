package com.example.laundrypersonnelmis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class activity_pick_tems extends AppCompatActivity {
    Integer Trouser=0,Jacket=0,Shirt=0,Tshirt=0,Dress=0,Skirt=0,Shoes=0,Sweater=0,total=0;
    ImageView imgtrouser,imgjacket,imgshirt,imgtshirt,imgdress,imgskirt,imgshoes,imgsweater;
    TextView txtTrouser,txtJacket,txtShirt,txtTshirt,txtDress,txtSkirt,txtShoes,txtSweater,txttotal;
    String phone1,name1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_tems);

        txtTrouser=(TextView)findViewById(R.id.textViewTrouser);
        txtJacket=(TextView)findViewById(R.id.textViewJacket);
        txtShirt=(TextView)findViewById(R.id.textViewShirt);
        txtTshirt=(TextView)findViewById(R.id.textViewTshirt);
        txtDress=(TextView)findViewById(R.id.textViewDress);
        txtSkirt=(TextView)findViewById(R.id.textViewSkirt);
        txtShoes=(TextView)findViewById(R.id.textViewShoes);
        txtSweater=(TextView)findViewById(R.id.textViewSweater);
        txttotal=(TextView)findViewById(R.id.total);
        imgtrouser=(ImageView)findViewById(R.id.imgtrouser);
        imgjacket=(ImageView)findViewById(R.id.imgjacket);
        imgshirt=findViewById(R.id.imgshirt);
        imgtshirt=findViewById(R.id.imgtshirt);
        imgdress=findViewById(R.id.imgdress);
        imgskirt=findViewById(R.id.imgskirt);
        imgshoes=findViewById(R.id.imgshoes);
        imgsweater=findViewById(R.id.imgsweater);
        Glide.with(this).load(R.drawable.trouser).circleCrop().into(imgtrouser);
        Glide.with(this).load(R.drawable.jacket).circleCrop().into(imgjacket);
        Glide.with(this).load(R.drawable.shirt).circleCrop().into(imgshirt);
        Glide.with(this).load(R.drawable.tshirt).circleCrop().into(imgtshirt);
        Glide.with(this).load(R.drawable.dress).circleCrop().into(imgdress);
        Glide.with(this).load(R.drawable.skirt).circleCrop().into(imgskirt);
        Glide.with(this).load(R.drawable.shoes).circleCrop().into(imgshoes);
        Glide.with(this).load(R.drawable.sweater).circleCrop().into(imgsweater);
        Intent getphone = getIntent();
        phone1 = getphone.getStringExtra("email" );
        name1=getphone.getStringExtra("name");

    }
    @SuppressLint("SetTextI18n")
    public void addTrouser(View view) {
        Trouser=Trouser+1;
        txtTrouser.setText(Integer.toString(Trouser));

        total();
    }
    @SuppressLint("SetTextI18n")
    public void subTrouser(View view) {
        if (Trouser == 0) {
            // Add any specific logic to be executed when Trouser is already 0
        } else {
            Trouser -= 1;
            txtTrouser.setText(Integer.toString(Trouser));
            total();
        }
    }

    public void addJacket(View view) {
        Jacket=Jacket+1;
        txtJacket.setText(Integer.toString(Jacket));
        total();
    }
    public void subJacket(View view) {
        if(Jacket==0){}else{
            Jacket -=1;
            txtJacket.setText(Integer.toString(Jacket));
            total();}
    }

    public void addShirt(View view) {
        Shirt=Shirt+1;
        txtShirt.setText(Integer.toString(Shirt));
        total();
    }
    public void subShirt(View view) {
        if(Shirt==0){}else{
            Shirt -=1;
            txtShirt.setText(Integer.toString(Shirt));
            total();}
    }


    public void addDress(View view) {
        Dress=Dress+1;
        txtDress.setText(Integer.toString(Dress));
        total();
    }
    public void subDress(View view) {
        if(Dress==0){}else{
            Dress -=1;
            txtDress.setText(Integer.toString(Dress));
            total();}
    }



    public void addTshirt(View view) {
        Tshirt=Tshirt+1;
        txtTshirt.setText(Integer.toString(Tshirt));
        total();
    }
    public void subTshirt(View view) {
        if(Tshirt==0){}else{
            Tshirt-=1;
            txtTshirt.setText(Integer.toString(Tshirt));
            total();}
    }


    public void addSkirt(View view) {
        Skirt=Skirt+1;
        txtSkirt.setText(Integer.toString(Skirt));
        total();
    }
    public void subSkirt(View view) {
        if(Skirt==0){}else{
            Skirt-=1;
            txtSkirt.setText(Integer.toString(Skirt));
            total();}
    }

    public void addShoes(View view) {
        Shoes=Shoes+1;
        txtShoes.setText(Integer.toString(Shoes));
        total();
    }
    public void subShoes(View view) {
        if(Shoes==0){}else{
            Shoes-=1;
            txtShoes.setText(Integer.toString(Shoes));
            total();}
    }


    public void addSweater(View view) {
        Sweater +=1;
        txtSweater.setText(Integer.toString(Sweater));
        total();

    }
    public void subSweater(View view) {
        if(Sweater==0){}else{
            Sweater-=1;
            txtSweater.setText(Integer.toString(Sweater));
            total();}
    }


    public void total () {
        total=(Trouser*50)+(Jacket*70)+(Shirt*30)+(Dress*50)+(Tshirt*30)+(Skirt*40)+(Shoes*50)+(Sweater*60);
        txttotal.setText(String.format(Locale.getDefault(), "Ksh %d", total));
    }


    public void makeorder(View view) {
        if(Trouser==0&&Jacket==0&&Shirt==0&&Tshirt==0&&Skirt==0&&Shoes==0&&Sweater==0){
            Toast.makeText(activity_pick_tems.this, "pick an Item", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(getApplicationContext(), servicemenlist.class);
            total = (Trouser * 50) + (Jacket * 70) + (Shirt * 30) + (Dress * 50) + (Tshirt * 30) + (Skirt * 40) + (Shoes * 50) + (Sweater * 60);
            intent.putExtra("phone", phone1);
            intent.putExtra("name", name1);
            intent.putExtra("Trouser", Trouser);
            intent.putExtra("Jacket", Jacket);
            intent.putExtra("Shirt", Shirt);
            intent.putExtra("Dress", Dress);
            intent.putExtra("Tshirt", Tshirt);
            intent.putExtra("Skirt", Skirt);
            intent.putExtra("Shoes", Shoes);
            intent.putExtra("Sweater", Sweater);
            intent.putExtra("Total", total);
            startActivity(intent);

            this.finish();
        }
    }
    public void BackHome(View view) {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        this.finish();
    }
}