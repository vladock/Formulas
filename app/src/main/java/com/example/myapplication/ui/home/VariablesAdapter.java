package com.example.myapplication.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class VariablesAdapter extends RecyclerView.Adapter<VariablesAdapter.ViewHolder> {
    private ArrayList<Variable> localDataSet;
    private LayoutInflater mInflater;

    public VariablesAdapter(Context context, ArrayList<Variable> data) {

        localDataSet = data;
        if (localDataSet == null){
            localDataSet = new ArrayList<Variable>();
        }
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.variable,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HomeFragment.variablesArray.get(i).var = viewHolder.var;
        HomeFragment.variablesArray.get(i).vardesc = viewHolder.varDesc;
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextInputEditText var;
        TextInputEditText varDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            var = itemView.findViewById(R.id.var);
            varDesc = itemView.findViewById(R.id.vardesc);
        }
    }
}
