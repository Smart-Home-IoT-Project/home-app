package com.gti.equipo4.assistedhome.fragments.scale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.adapters.WeigthsFirestoreUI;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class TabFragment2 extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    public static WeigthsFirestoreUI adaptador2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_3, container, false);
    }
}