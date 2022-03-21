package com.techroof.nooninvestadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.techroof.nooninvestadmin.Adapters.HomeFragmentViewPagerAdapter;

public class ShowInvestmentRateActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_investment_rate);
        imageView=findViewById(R.id.img_back_arrow);
        imageView.setClipToOutline(true);
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onBackPressed();
    }
});

        tabLayout=findViewById(R.id.tablayoutClassInvestments);
        viewPager=findViewById(R.id.viewpager_ClassInvestment);
        tabLayout.setupWithViewPager(viewPager);
        HomeFragmentViewPagerAdapter viewPagerAdapter=
                new HomeFragmentViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addfragment(new ClassAFragment(),"Class A");
        viewPagerAdapter.addfragment(new ClassBFragment(),"Class B");
        viewPagerAdapter.addfragment(new ClassCFragment(),"Class C");
        viewPager.setAdapter(viewPagerAdapter);


        //Toast.makeText(getApplicationContext(), "ct"+getIntent().getExtras().getString("content"), Toast.LENGTH_SHORT).show();
    }
}