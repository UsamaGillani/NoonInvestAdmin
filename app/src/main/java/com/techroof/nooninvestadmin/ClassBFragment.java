package com.techroof.nooninvestadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ClassBFragment extends Fragment {

    private TextView textViewInvestmentAmount, textViewDailyIncome;
    private FirebaseFirestore firestore;
    private Button btnupdate,btnadd;
    private String category, uid;
    private ProgressDialog progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_class_b, container, false);
        textViewInvestmentAmount = view.findViewById(R.id.investement_amount);
        textViewDailyIncome = view.findViewById(R.id.txtview_dailyincome);
        firestore=FirebaseFirestore.getInstance();
        btnupdate=view.findViewById(R.id.btn_upd_invest);
        btnadd=view.findViewById(R.id.btn_addInv);
       /* Intent iin = new Intent();

        Bundle extras = iin.getExtras();
        if (extras != null)
            category = extras.getString("content");*/
        category = getActivity().getIntent().getExtras().getString("content");
        /* Toast.makeText(getContext(), "yesss" + category, Toast.LENGTH_SHORT).show();*/
        showLoadingDialog();
        getdata();
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateInvestmentActivity.class);
                intent.putExtra("IdMoveToUpdate",uid);
                getActivity().startActivity(intent);
            }
        });
        btnadd.setVisibility(View.INVISIBLE);

        if (textViewDailyIncome.getText().toString().isEmpty()&&textViewInvestmentAmount.getText().toString().isEmpty()) {

            btnadd.setVisibility(View.VISIBLE);

        }

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentt=new Intent(getActivity(),AddInvestmentDetails.class);
                getActivity().startActivity(intentt);
            }
        });

        return view;
    }

    private void getdata() {

        firestore.collection("InvestmentDetails")
                .whereEqualTo("category", category)
                .whereEqualTo("class", "Class B").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String investmentrate = document.getString("InvestmentRate");
                        String dailyinvest = document.getString("DailyIncome");
                        uid = document.getString("id");

                        //Toast.makeText(getContext(), " id"+uid, Toast.LENGTH_SHORT).show();
                        textViewInvestmentAmount.setText(investmentrate);
                        textViewDailyIncome.setText(dailyinvest);
                        btnadd.setVisibility(View.INVISIBLE);
                        btnupdate.setVisibility(View.VISIBLE);
                      /*  DocumentReference uidRef = firestore.collection("InvestmentDetails").document();
                        uidRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String ivstrate = document.getString("InvestmentRate");
                                        String dailyy = document.getString("DailyIncome");

                                        Log.d("a", ivstrate + "/" + dailyy + "/");
                                    } else {
                                        Log.d("b", "No such document");
                                    }
                                } else {
                                    Log.d("c", "get failed with ", task.getException());
                                }
                            }
                        });*/
                        dismissLoadingDialog();
                    }
                } else {
                    Log.d("d", "Error getting documents: ", task.getException());
                    btnadd.setVisibility(View.VISIBLE);
                    btnupdate.setVisibility(View.INVISIBLE);
                    dismissLoadingDialog();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                btnadd.setVisibility(View.VISIBLE);
                btnupdate.setVisibility(View.INVISIBLE);

            }
        });


    }


    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(getActivity());
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