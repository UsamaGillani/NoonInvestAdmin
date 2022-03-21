package com.techroof.nooninvestadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestCoordinator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.nooninvestadmin.Adapters.HomeFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.Adapters.RequestFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.ModelsClass.ProductsData;
import com.techroof.nooninvestadmin.ModelsClass.RequestData;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestAFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private ArrayList<RequestData> requestDataArrayList;
     RequestFragmentRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView showRv;
    private FirebaseFirestore firestore;
    private RequestFragmentRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManagershowrequests;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    ImageView imgg;
    String requestsCategory, catVal;
    private ProgressDialog progress;




    public RequestAFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        requestDataArrayList=new ArrayList<>();
        adapter = new RequestFragmentRecyclerViewAdapter(requestDataArrayList, getContext());

        showRv.setAdapter(adapter);

        getRequests();

        Toast.makeText(getContext(), "on", Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_a, container, false);
        showRv = view.findViewById(R.id.show_requestss_rv);
        firestore = FirebaseFirestore.getInstance();

        // requestsCategory = getArguments().getString("Requestscategory");
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            requestsCategory = bundle.getString("Requestscategoryy");
            String message = bundle.getString("Requestscategory");
            //Toast.makeText(getContext(), "y" + message, Toast.LENGTH_LONG).show();
            Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        }

        //function call





        //arraylist decleration
        requestDataArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("categorys", Context.MODE_PRIVATE);
        catVal = sharedPreferences.getString("str", "Note");
        Toast.makeText(getContext(), "" + catVal, Toast.LENGTH_LONG).show();
        showLoadingDialog();
        //getRequests();

        return view;
    }

    private void getRequests() {

        firestore.collection(catVal)
                .whereEqualTo("Status", "Requested")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    RequestData listData = documentSnapshot.toObject(RequestData.class);
                    requestDataArrayList.add(listData);

                }

                layoutManagershowrequests = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);
                showRv.setLayoutManager(layoutManagershowrequests);
                adapter = new RequestFragmentRecyclerViewAdapter(requestDataArrayList, getContext());
                //showRv.notifyAll();
                showRv.setAdapter(adapter);

                dismissLoadingDialog();
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