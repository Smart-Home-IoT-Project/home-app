package com.gti.equipo4.assistedhome.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.gti.equipo4.assistedhome.R;
import com.gti.equipo4.assistedhome.model.Weight;

import androidx.annotation.NonNull;

public class WeigthsFirestoreUI extends
        FirestoreRecyclerAdapter<Weight, Weights.ViewHolder> {
    protected View.OnClickListener onClickListener;
    public WeigthsFirestoreUI(
            @NonNull FirestoreRecyclerOptions<Weight> options) {
        super(options);
    }
    @Override public Weights.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_lista, parent, false);
        return new Weights.ViewHolder(view);
    }
    @Override protected void onBindViewHolder(@NonNull Weights
            .ViewHolder holder, int position, @NonNull Weight lugar) {
        Weights.personalizaVista(holder, lugar);
        holder.itemView.setOnClickListener(onClickListener);
    }
    public void setOnItemClickListener(View.OnClickListener onClick) {
        onClickListener = onClick;
    }
    public String getKey(int pos) {
        return super.getSnapshots().getSnapshot(pos).getId();
    }
}