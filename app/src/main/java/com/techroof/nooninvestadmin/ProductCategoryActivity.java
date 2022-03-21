package com.techroof.nooninvestadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProductCategoryActivity extends AppCompatActivity {

    CardView cardViewClothing,cardViewTechnology,cardViewHomeAppliances,cardViewAutomotives;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_activity);

        cardViewClothing= findViewById(R.id.cardviewClothing);
        cardViewTechnology= findViewById(R.id.cardviewTechProducts);
        cardViewHomeAppliances= findViewById(R.id.cardview_Home_Appliances);
        cardViewAutomotives=(CardView) findViewById(R.id.cardview_Automotives);

        img=findViewById(R.id.img_back_arrow);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cardViewClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("category","Clothing");
                startActivity(intent);
            }
        });

        cardViewTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("category","Tech");
                startActivity(intent);
            }
        });

        cardViewHomeAppliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("category","HomeAppliances");
                startActivity(intent);
            }
        });



        cardViewAutomotives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("category","AutoMotives");
                startActivity(intent);
            }
        });


    }
}