package com.techroof.nooninvestadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.nooninvestadmin.Adapters.ApprovedFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.Adapters.PaymentFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.ModelsClass.RequestData;

import java.util.ArrayList;

public class Payments extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<RequestData> requestDataArrayList;
    private ApprovedFragmentRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView showRv;
    private FirebaseFirestore firestore;
    private PaymentFragmentRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManagershowrequests;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ImageView imgg;
    private String requestsCategory,getDetails,checkRequesttype,withDrawalid,displayapproved;
    private double detailswithdrawalAmount;
    private TextView textViewAccountnumberapproved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            getDetails = extras.getString("movepending");
            checkRequesttype= extras.getString("displaypending");
            withDrawalid=extras.getString("withdrawlid");
            displayapproved=extras.getString("displayapproved");
            //Toast.makeText(getApplicationContext(), ""+getDetails, Toast.LENGTH_SHORT).show();
        }
        showRv= findViewById(R.id.show_payments_rv);
        textViewAccountnumberapproved=findViewById(R.id.tv_accountnumber_payments);
        //textViewAccountnumberapproved.setText();
        firestore = FirebaseFirestore.getInstance();
        requestDataArrayList = new ArrayList<>();
        getRequests();
        getDetailss();
    }
    private void getRequests() {

        firestore.collection("SentRequestsWallets").whereEqualTo("Uid",getDetails)
                .whereEqualTo("Status", "Approved")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    RequestData listData = documentSnapshot.toObject(RequestData.class);
                    requestDataArrayList.add(listData);


                }

                layoutManagershowrequests = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL, false);
                showRv.setLayoutManager(layoutManagershowrequests);
                adapter = new PaymentFragmentRecyclerViewAdapter(requestDataArrayList, getApplicationContext());
                showRv.setAdapter(adapter);


            }
        });

    }

   private void getDetailss() {

        //uId = FirebaseAuth.getInstance().getUid();
        firestore.collection("SentRequestsWallets")
                .whereEqualTo("Uid", getDetails).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String AccountNumber = document.getString("AccountNumber");
                        //Toast.makeText(getApplicationContext(), " id" +detailswithdrawalAmount, Toast.LENGTH_SHORT).show();
                        textViewAccountnumberapproved.setText(String.valueOf(AccountNumber));

                    }
                } else {
                    Log.d("d", "Error getting documents: ", task.getException());

                }
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });

    }

}