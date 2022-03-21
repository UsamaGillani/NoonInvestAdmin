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
import com.techroof.nooninvestadmin.ModelsClass.ProductsData;
import com.techroof.nooninvestadmin.R;
import com.techroof.nooninvestadmin.ShowInvestmentRateActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragmentRecyclerViewAdapter extends RecyclerView.Adapter<HomeFragmentRecyclerViewAdapter.ViewAdapter> {
    private static final String TAG = "RecyclerViewAdapter";


    private ArrayList<ProductsData> ProductlistData;
    private Context context;

    public HomeFragmentRecyclerViewAdapter(ArrayList<ProductsData> ProductslistData, Context context) {
        this.ProductlistData = ProductslistData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: 1");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview, parent, false);
        return new ViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {


        Log.d(TAG, "onBindViewHolder: called");
        ProductsData ld = ProductlistData.get(position);
        holder.textView.setText(ld.getName());
        Glide.with(context).load(ld.getImage()).into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context.getApplicationContext(), "position"+ld.getImage(), Toast.LENGTH_SHORT).show();
                /*Snackbar snackbar = Snackbar
                        .make(Ho, "www.journaldev.com", Snackbar.LENGTH_LONG);
                snackbar.show();*/
                String movecategory=ld.getCategory();
                Log.d(TAG, "onClick: "+ld.getName());
                Intent intent= new Intent(context.getApplicationContext(), ShowInvestmentRateActivity.class);
                intent.putExtra("content", movecategory);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ProductlistData.size();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        RelativeLayout relativeLayout;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview_recyclerview);
            textView = itemView.findViewById(R.id.name);
            relativeLayout=itemView.findViewById(R.id.parentlayout);
            imageView.setClipToOutline(true);

        }
    }

}
