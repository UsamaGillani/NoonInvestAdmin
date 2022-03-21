package com.techroof.nooninvestadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.nooninvestadmin.Adapters.PendingFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.Adapters.RequestFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.ModelsClass.RequestData;

import java.util.ArrayList;
import java.util.HashMap;

public class ChangeRequestStatusActivity extends AppCompatActivity {

    String getDetails,checkRequesttype,withDrawalid,displayapproved;
    TextView tvAcountnumber;
    TextView tvAmountRequested;
    Button btnPending, btnAproved;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    private  String Uidd,Statuss,uid;
    double withdrawalAmount;
    double detailswithdrawalAmount;
    double updateProfit;
    RequestFragmentRecyclerViewAdapter adapterRequest;
    PendingFragmentRecyclerViewAdapter adapterPending;
    private ProgressDialog progress;
    //ArrayList<RequestData> requestDataArrayList;
    //ArrayList<RequestData> pendingarraylist;

    private RequestAFragment requestAFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_request_status);
        tvAcountnumber = findViewById(R.id.name_account_number);
        tvAmountRequested = findViewById(R.id.user_amount_requested);
        btnPending=findViewById(R.id.btn_pending);
        btnAproved=findViewById(R.id.btn_approved);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getUid();

        requestAFragment=new RequestAFragment();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            getDetails = extras.getString("movepending");
            checkRequesttype= extras.getString("displaypending");
            withDrawalid=extras.getString("withdrawlid");
            displayapproved=extras.getString("displayapproved");
            //Toast.makeText(getApplicationContext(), ""+displayapproved, Toast.LENGTH_SHORT).show();
        }

        if(checkRequesttype!=null){

            btnPending.setVisibility(View.INVISIBLE);
        }else{
            Log.d("checktype", "onCreate: ");
        }

        if(displayapproved!=null){

            btnPending.setVisibility(View.INVISIBLE);
            btnAproved.setVisibility(View.INVISIBLE);
        }else{
            Log.d("checktype", "onCreate: ");
        }
        //requestDataArrayList=new ArrayList<RequestData>();
        //pendingarraylist=new ArrayList<RequestData>();
        //adapterRequest=new RequestFragmentRecyclerViewAdapter(requestDataArrayList,getApplicationContext());
        //adapterPending=new PendingFragmentRecyclerViewAdapter(requestDataArrayList,getApplicationContext());
        getDetailss();

        getTotalProfit();


        btnPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Updatestatuspending();

              /*  Toast.makeText(getApplicationContext(),""+getDetails,Toast.LENGTH_LONG).show();
                adapterRequest.notifyDataSetChanged();
                adapterPending.notifyDataSetChanged();
                Intent intent=new Intent(getApplicationContext(),ShowWithdrawalRequests.class);
                startActivity(intent);*/

               // adapterRequest.notifyDataSetChanged();
                //adapterPending.notifyDataSetChanged();

            }
        });

        btnAproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Updatestatusapproved();
                UpdateWalletTotalProfit();
                adapterRequest.notifyDataSetChanged();
                adapterPending.notifyDataSetChanged();
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);

            }
        });

    }

   void getDetailss() {

        //uId = FirebaseAuth.getInstance().getUid();
        firestore.collection("SentRequestsWallets")
                .whereEqualTo("Uid", getDetails).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String AccountNumber = document.getString("AccountNumber");
                        detailswithdrawalAmount = document.getDouble("withdrawalAmount").doubleValue();

                         Toast.makeText(getApplicationContext(), " id" +detailswithdrawalAmount, Toast.LENGTH_SHORT).show();
                        tvAcountnumber.setText(AccountNumber);
                        tvAmountRequested.setText(String.valueOf(detailswithdrawalAmount));

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

    private void Updatestatuspending() {

        DocumentReference documentReference = firestore.collection("SentRequestsWallets").document(withDrawalid);
        HashMap hasshmap = new HashMap<>();
        hasshmap.put("Status", "Pending");
        documentReference.update(hasshmap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                Toast.makeText(getApplicationContext(), "DATA UPDATED", Toast.LENGTH_LONG).show();
                //adapterRequest.notifyDataSetChanged();
                //adapterPending.notifyDataSetChanged();

                onBackPressed();

               // Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                //startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "NOT UPDATED", Toast.LENGTH_LONG).show();
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       /* //checking
        HashMap hasshmap = new HashMap<>();
        hasshmap.put("Status", "Pending");
        firestore.collection("SentRequestsWallets")
                .whereEqualTo("Uid", getDetails).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot documentSnapshot = task.getResult();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Uidd = document.getString("Uid");
                        Statuss = document.getString("Status");
                        if (Statuss.equals("Requested")) {
                            firestore.collection("SentRequestsWallets").document().update(hasshmap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Sorry Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            });

                            //Toast.makeText(getContext(), "y"+userWithdrawlStatus, Toast.LENGTH_SHORT).show();
                        } else{

                            //userWithdrawlStatus = "False";
                            //Toast.makeText(getContext(), "n"+userWithdrawlStatus, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
*/
    }

    private void Updatestatusapproved() {

        DocumentReference documentReference = firestore.collection("SentRequestsWallets").document(withDrawalid);
        HashMap hasshmap = new HashMap<>();
        hasshmap.put("Status", "Approved");
        documentReference.update(hasshmap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                Toast.makeText(getApplicationContext(), "DATA UPDATED", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Sorry Data Haven't updated!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTotalProfit() {

        firestore.collection("wallets").document(getDetails).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        withdrawalAmount = document.getDouble("totalProfit").doubleValue();

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }


        });
    }
    private void UpdateWalletTotalProfit() {

        updateProfit= withdrawalAmount-detailswithdrawalAmount;
        DocumentReference documentReference = firestore.collection("wallets").document(getDetails);
        HashMap hasshmap = new HashMap<>();
        hasshmap.put("totalProfit", updateProfit);
        documentReference.update(hasshmap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                Toast.makeText(getApplicationContext(), "DATA UPDATED", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "NO!", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {

        //adapterRequest.notifyDataSetChanged();
        //adapterPending.notifyDataSetChanged();
        super.onBackPressed();

        //adapterRequest.notifyDataSetChanged();
        //adapterPending.notifyDataSetChanged();
    }

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(this);
            //  progress.setTitle("IN PROGRESS");
            progress.setCanceledOnTouchOutside(false);
            progress.setMessage("LOADING...");
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }
}





