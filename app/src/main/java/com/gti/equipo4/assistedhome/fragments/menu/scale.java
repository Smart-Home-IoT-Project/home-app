package com.gti.equipo4.assistedhome.fragments.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.fragments.scale.SampleFragmentPagerAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class scale extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.scale_content, container, false);

        Context contexto = super.getContext();


        View view = inflater.inflate(R.layout.scale_content,container, false);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //setupViewPager(viewPager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getFragmentManager(),
                contexto));
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabs.setupWithViewPager(viewPager);


        return view;
    }
}