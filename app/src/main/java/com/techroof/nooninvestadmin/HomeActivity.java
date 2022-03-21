package com.techroof.nooninvestadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Short4;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity  {

    GridLayout gridLayout;
    CardView cardViewAddproduct, cardViewAddInvestment, CardViewAddInvestmentRate, cardViewRequests,cardViewPayments,cardViewRefferdPayments;
    private FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    double getTotalProfitAmount;
    double getDailyAmount;
    double addTotalProfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridLayout = (GridLayout) findViewById(R.id.mainGrid);
        cardViewAddproduct = findViewById(R.id.add_products);
        cardViewAddInvestment = findViewById(R.id.add_investement);
        CardViewAddInvestmentRate = findViewById(R.id.cardview_Investment_Rate);
        cardViewRequests=findViewById(R.id.cardview_update);
        cardViewPayments=findViewById(R.id.paymenthistory);
        cardViewRefferdPayments=findViewById(R.id.crd_reffered_amount_payments);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        onTimeSet(19,54);

        cardViewAddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ProductCategoryActivity.class
                );
                startActivity(intent);
            }
        });

        cardViewAddInvestment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowInvestementItemsActivity.class
                );
                startActivity(intent);
            }
        });

        CardViewAddInvestmentRate.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View view) {
                                                             Intent intent = new Intent(getApplicationContext(), AddInvestmentDetails.class
                                                             );
                                                             startActivity(intent);
                                                         }
                                                     }
        );


//setSingleEvent(gridLayout);
        cardViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Bundle bundle = new Bundle();
                bundle.putString("Requestscategory", "SentRequestsWallets");*/

                Intent intentt = new Intent(getApplicationContext(), ShowWithdrawalRequests.class
                );
                intentt.putExtra("Requestscategory","SentRequestsWallets");
                SharedPreferences shrd=getSharedPreferences("categorys",MODE_PRIVATE);
                SharedPreferences.Editor editor= shrd.edit();
                editor.putString("str","SentRequestsWallets");
                editor.apply();
               /* RequestAFragment requestAFragment=new RequestAFragment();
                requestAFragment.setArguments(bundle);*/
                startActivity(intentt);
            }
        });
        cardViewRefferdPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentt = new Intent(getApplicationContext(), ShowWithdrawalRequests.class
                );
                intentt.putExtra("Requestscategory","SentRequestsWallets");
                SharedPreferences shrd=getSharedPreferences("categorys",MODE_PRIVATE);
                SharedPreferences.Editor editor= shrd.edit();
                editor.putString("str","SentRequestsRefferals");
                editor.apply();
               /* RequestAFragment requestAFragment=new RequestAFragment();
                requestAFragment.setArguments(bundle);*/
                startActivity(intentt);

            }
        });

        cardViewPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intentt = new Intent(getApplicationContext(), Approvedpayments.class);
                startActivity(intentt);

            }
        });
    }


    /*private void setSingleEvent(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeActivity.this, "Clicked at index " + finalI,
                            Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),ProductCategoryActivity.class
                    );
                    startActivity(intent);
                }
            });
        }
    }*/

    void updateFirebase() {

        firestore.collection("wallets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    ArrayList<String> listt = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        // listt.add(document.getId());

                        //Toast.makeText(getContext(), ""+document.get("id"), Toast.LENGTH_SHORT).show();
                        String walletId=document.get("id").toString();

                        getDailyAmount=document.getDouble("dailyAmount").doubleValue();
                        getTotalProfitAmount = document.getDouble("totalProfit").doubleValue();
                        addTotalProfit=getDailyAmount+getTotalProfitAmount;
                        DocumentReference documentReference = firestore.collection("wallets").document(walletId);
                        HashMap updateAmountt = new HashMap<>();
                        updateAmountt.put("totalProfit", addTotalProfit);
                        documentReference.update(updateAmountt).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                Toast.makeText(getApplicationContext(), "dataupdated", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "NO!", Toast.LENGTH_LONG).show();
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
    //event

    public void onTimeSet(int hourofDay, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourofDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        startAlarm(c);


    }
    //calender
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Services.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),1000*60*60*24,pendingIntent);


    }



  }


