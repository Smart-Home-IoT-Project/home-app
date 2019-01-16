package com.gti.equipo4.assistedhome.fragments.initialSetUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gti.equipo4.assistedhome.R;

import androidx.fragment.app.Fragment;

public class InitialTabFragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate( R.layout.app_intro1, container, false);
    }
}
