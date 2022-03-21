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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.techroof.nooninvestadmin.Adapters.PendingFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.Adapters.RequestFragmentRecyclerViewAdapter;
import com.techroof.nooninvestadmin.ModelsClass.RequestData;

import java.util.ArrayList;


public class PendingBFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private ArrayList<RequestData> requestDataArrayList;
    private RequestFragmentRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView showRv;
    private FirebaseFirestore firestore;
    private PendingFragmentRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManagershowrequests;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ImageView imgg;
    private String requestsCategory, catVal;
    private ProgressDialog progress;


    public PendingBFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pending_b, container, false);
        showRv = view.findViewById(R.id.show_pendingrequestss_rv);
        firestore = FirebaseFirestore.getInstance();

       /* Bundle bundle = this.getArguments();
        if(bundle!=null){

            requestsCategory = bundle.getString("Requestscategory");
            Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        }*/


        //arraylist decleration
        requestDataArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("categorys", Context.MODE_PRIVATE);
        catVal = sharedPreferences.getString("str", "Note");
        //Toast.makeText(getContext(), "yes" + catVal, Toast.LENGTH_LONG).show();
        showLoadingDialog();
        getRequests();

        //adapter.notifyDataSetChanged();


        return view;
    }

    private void getRequests() {

        firestore.collection(catVal)
                .whereEqualTo("Status", "Pending")
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
                adapter = new PendingFragmentRecyclerViewAdapter(requestDataArrayList, getContext());
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