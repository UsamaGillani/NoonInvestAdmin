package com.techroof.nooninvestadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.nooninvestadmin.Adapters.HomeFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.ModelsClass.ProductsData;

import java.util.ArrayList;

public class ShowInvestementItemsActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private ArrayList<ProductsData> productClothingArrayList, productTechArrayList, productAutoMotiveArrayList, productHomeAppliances;
    private HomeFragmentRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView clothingRv, techRv, homeApliancesRv, AutomotivesRv;
    private FirebaseFirestore firestore;
    private HomeFragmentRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManagerclothing, layoutManagertech, layoutManagerhomeAppliances,
            layoutManagerautoMotive;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    private ImageView imageView,img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_investement_items);

        clothingRv = findViewById(R.id.clothing_rv);
        homeApliancesRv = findViewById(R.id.home_appliancesrv);
        techRv = findViewById(R.id.techrv);
        AutomotivesRv = findViewById(R.id.automotiverv);
        firestore = FirebaseFirestore.getInstance();
        imageView=findViewById(R.id.imageview_recyclerview);
        img=findViewById(R.id.img_back_arrow);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        //arraylist decleration
        productClothingArrayList = new ArrayList<>();
        productTechArrayList = new ArrayList<>();
        productHomeAppliances = new ArrayList<>();
        productAutoMotiveArrayList = new ArrayList<>();

        //methods
        getClothingProduct();
        getTechProduct();
        getHomeAppliances();
        getAutoMotives();
/*
        firebaseAuth=FirebaseAuth.getInstance();
         authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                }
            }
        };*/


    }


    private void getAutoMotives() {

        firestore.collection("products")
                .whereEqualTo("category", "AutoMotives")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    ProductsData listData = documentSnapshot.toObject(ProductsData.class);
                    productAutoMotiveArrayList.add(listData);

                }

                layoutManagerautoMotive = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                AutomotivesRv.setLayoutManager(layoutManagerautoMotive);
                adapter = new HomeFragmentRecyclerViewAdapter(productAutoMotiveArrayList,
                        getApplicationContext());
                AutomotivesRv.setAdapter(adapter);


            }
        });


    }


    private void getHomeAppliances() {

        firestore.collection("products")
                .whereEqualTo("category", "HomeAppliances")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    ProductsData listData = documentSnapshot.toObject(ProductsData.class);
                    productHomeAppliances.add(listData);


                }

                layoutManagerhomeAppliances = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                homeApliancesRv.setLayoutManager(layoutManagerhomeAppliances);
                adapter = new HomeFragmentRecyclerViewAdapter(productHomeAppliances, getApplicationContext());
                homeApliancesRv.setAdapter(adapter);


            }
        });


    }


    private void getTechProduct() {

        firestore.collection("products")
                .whereEqualTo("category", "Tech")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    ProductsData listData = documentSnapshot.toObject(ProductsData.class);
                    productTechArrayList.add(listData);


                }

                layoutManagertech = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                techRv.setLayoutManager(layoutManagertech);
                adapter = new HomeFragmentRecyclerViewAdapter(productTechArrayList, getApplicationContext());
                techRv.setAdapter(adapter);


            }
        });


    }


    private void getClothingProduct() {

        firestore.collection("products")
                .whereEqualTo("category", "Clothing")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    ProductsData listData = documentSnapshot.toObject(ProductsData.class);
                    productClothingArrayList.add(listData);


                }

                layoutManagerclothing = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                clothingRv.setLayoutManager(layoutManagerclothing);
                adapter = new HomeFragmentRecyclerViewAdapter(productClothingArrayList, getApplicationContext());
                clothingRv.setAdapter(adapter);


            }
        });


    }
}
