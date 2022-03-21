package com.techroof.nooninvestadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.techroof.nooninvestadmin.Adapters.HomeFragmentViewPagerAdapter;

public class ShowWithdrawalRequests extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;
    private String requestsCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_withdrawal_requests);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            RequestAFragment requestAFragment=new RequestAFragment();
            requestAFragment.setArguments(getIntent().getExtras());
            //requestsCategory = bundle.getString("Requestscategory");
           // Toast.makeText(getApplicationContext(), ""+requestsCategory, Toast.LENGTH_LONG).show();
        }






        imageView=findViewById(R.id.img_back_arrow);
        imageView.setClipToOutline(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tabLayout=findViewById(R.id.tablayoutShowWithDrawal);
        viewPager=findViewById(R.id.viewpager_ShowWithdrawal);
        tabLayout.setupWithViewPager(viewPager);
        HomeFragmentViewPagerAdapter viewPagerAdapter=
                new HomeFragmentViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addfragment(new RequestAFragment(),"REQUESTED");
        viewPagerAdapter.addfragment(new PendingBFragment(),"PENDING");
        viewPager.setAdapter(viewPagerAdapter);


    }
}