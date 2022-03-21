package com.techroof.nooninvestadmin;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Services extends BroadcastReceiver {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    double getTotalProfitAmount;
    double getDailyAmount;
    double addTotalProfit;

    double RgetTotalProfitAmount;
    double RgetDailyAmount;
    double RaddTotalProfit;


    @Override
    public void onReceive(Context context, Intent intent) {
        updateFirebase();
        updateFirebaseReferals();

    }

    void updateFirebase() {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firestore.collection("wallets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    ArrayList<String> listt = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        // listt.add(document.getId());

                        //Toast.makeText(getContext(), ""+document.get("id"), Toast.LENGTH_SHORT).show();
                        String walletId = document.get("id").toString();

                        getDailyAmount = document.getDouble("dailyAmount").doubleValue();
                        getTotalProfitAmount = document.getDouble("totalProfit").doubleValue();
                        addTotalProfit = getDailyAmount + getTotalProfitAmount;
                        DocumentReference documentReference = firestore.collection("wallets").document(walletId);
                        HashMap updateAmountt = new HashMap<>();
                        updateAmountt.put("totalProfit", addTotalProfit);
                        documentReference.update(updateAmountt).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });


                    }
                    // Toast.makeText(getContext(), "" + listt, Toast.LENGTH_SHORT).show();
                    //updateData(listt); // *** new ***
                } else {
                    Log.d("TAGGG", "Error getting documents: ", task.getException());
                }
            }
        });

    }

    //refferals


    void updateFirebaseReferals() {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firestore.collection("refferals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    ArrayList<String> listt = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        // listt.add(document.getId());

                        //Toast.makeText(getContext(), ""+document.get("id"), Toast.LENGTH_SHORT).show();
                        String refferalId = document.get("id").toString();

                        RgetDailyAmount = document.getDouble("dailyAmount").doubleValue();
                        RgetTotalProfitAmount = document.getDouble("totalProfit").doubleValue();
                        RaddTotalProfit = RgetDailyAmount + RgetTotalProfitAmount;
                        DocumentReference documentReference = firestore.collection("refferals").document(refferalId);
                        HashMap updateAmountt = new HashMap<>();
                        updateAmountt.put("totalProfit", RaddTotalProfit);
                        documentReference.update(updateAmountt).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });


                    }
                    // Toast.makeText(getContext(), "" + listt, Toast.LENGTH_SHORT).show();
                    //updateData(listt); // *** new ***
                } else {
                    Log.d("TAGGG", "Error getting documents: ", task.getException());
                }
            }
        });

    }

}