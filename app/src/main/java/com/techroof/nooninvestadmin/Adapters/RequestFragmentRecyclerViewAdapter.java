package com.techroof.nooninvestadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techroof.nooninvestadmin.ChangeRequestStatusActivity;
import com.techroof.nooninvestadmin.ModelsClass.ProductsData;
import com.techroof.nooninvestadmin.ModelsClass.RequestData;
import com.techroof.nooninvestadmin.R;
import com.techroof.nooninvestadmin.ShowInvestmentRateActivity;

import java.util.ArrayList;

public class RequestFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RequestFragmentRecyclerViewAdapter.ViewAdapter> {
    private static final String TAG = "RecyclerViewAdapter";


    private ArrayList<RequestData> ShowRequestData;
    private Context context;

    public RequestFragmentRecyclerViewAdapter(ArrayList<RequestData> ShowRequestData, Context context) {
        this.ShowRequestData = ShowRequestData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: 1");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_requests_custom_recyclerview, parent, false);
        return new ViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {


        Log.d(TAG, "onBindViewHolder: called");
        //ProductsData ld = ProductlistData.get(position);
        RequestData ld = ShowRequestData.get(position);
        holder.textView.setText(ld.getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String moveDocumentid=ld.getWithDrawalId();
                String movecategory = ld.getUid();
                Log.d(TAG, "onClick: " + ld.getUid());
                Intent intent = new Intent(context.getApplicationContext(), ChangeRequestStatusActivity.class);
                intent.putExtra("withdrawlid",moveDocumentid );
                intent.putExtra("movepending", movecategory);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                notifyItemRemoved(holder.getAdapterPosition());

            }
        });


    }

    @Override
    public int getItemCount() {
        return ShowRequestData.size();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {
        TextView textView;
        RelativeLayout relativeLayout;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.requester_name);
            relativeLayout = itemView.findViewById(R.id.show_request_layoutt);

        }
    }

}
