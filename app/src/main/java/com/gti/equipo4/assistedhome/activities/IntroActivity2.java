package com.gti.equipo4.assistedhome.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.fragments.initialSetUp.InitialSampleFragmentPagerAdapter;
import com.gti.equipo4.assistedhome.fragments.medicines.SampleFragmentPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class IntroActivity2 extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Context context = super.getApplicationContext();
        setContentView(R.layout.app_intro_content);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //setupViewPager(viewPager);
        viewPager.setAdapter(new InitialSampleFragmentPagerAdapter(getSupportFragmentManager(), context));
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
