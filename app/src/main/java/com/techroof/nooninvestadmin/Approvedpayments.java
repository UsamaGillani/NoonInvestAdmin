package com.techroof.nooninvestadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.nooninvestadmin.Adapters.ApprovedFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.ModelsClass.RequestData;
import com.techroof.nooninvestadmin.ModelsClass.UsersData;

import java.util.ArrayList;

public class Approvedpayments extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<UsersData> requestDataArrayList;
    private ApprovedFragmentRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView showRv;
    private FirebaseFirestore firestore;
    private ApprovedFragmentRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManagershowrequests;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ImageView imgg;
    private String requestsCategory;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvedpayments);
        showRv = findViewById(R.id.show_appprovedrequestss_rv);
        firestore = FirebaseFirestore.getInstance();

       /* Bundle bundle = this.getArguments();
        if(bundle!=null){

            requestsCategory = bundle.getString("Requestscategory");
            Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        }*/


        //arraylist decleration
        requestDataArrayList = new ArrayList<>();

        getRequests();
        showLoadingDialog();

    }

    private void getRequests() {


        firestore.collection("SentRequestsWallets")
                .whereEqualTo("Status", "Approved")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                            String uid = documentSnapshot.get("Uid").toString();


                            firestore.collection("users").document(uid)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                                    UsersData listData = task.getResult().toObject(UsersData.class);


                                    requestDataArrayList.add(listData);

                                    layoutManagershowrequests = new LinearLayoutManager(getApplicationContext(),
                                            LinearLayoutManager.VERTICAL, false);
                                    showRv.setLayoutManager(layoutManagershowrequests);
                                    adapter = new ApprovedFragmentRecyclerViewAdapter(requestDataArrayList, getApplicationContext());
                                    showRv.setAdapter(adapter);
                                    dismissLoadingDialog();

                                }
                            });


                        }


                    }
                });


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