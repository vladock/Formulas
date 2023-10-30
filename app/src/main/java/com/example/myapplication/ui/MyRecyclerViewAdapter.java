package com.example.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {
    private ArrayList<Formula> data;
    private ArrayList<Formula> search;
    private LayoutInflater mInflater;
    public MyRecyclerViewAdapter(Context context,ArrayList<Formula> data) {
        this.data = data;
        search = this.data;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.formula,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        holder.desc.setText(data.get(pos).getDescription());
        holder.formula.setText(data.get(pos).getFormula());
        holder.vars.setText(data.get(pos).getVars());
        holder.delButton.setOnClickListener(v -> {
            removeItem(data.get(pos));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }
    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Formula> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(search);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Formula item : search) {
                    if (item.getDescription().toLowerCase().contains(filterPattern)||item.getFormula().toLowerCase().contains(filterPattern)||item.getVars().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView formula;
        TextView vars;
        ImageButton delButton;
        ViewHolder(View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.formulaDesc);
            formula = itemView.findViewById(R.id.formulaText);
            vars = itemView.findViewById(R.id.formulaVariables);
            delButton = itemView.findViewById(R.id.delFormula);
            itemView.setOnClickListener(v -> {
                MainActivity.searchedFormula = MainActivity.formulas.get(getAdapterPosition());
                MainActivity.searchedFormula.createVariables();
            });
        }
    }
    Formula getItem(int id) {
        return data.get(id);
    }
    public void removeItem(Formula formula){

        notifyDataSetChanged();
        data.remove(formula);
        MainActivity.formulas.remove(formula);
        MainActivity.formulaDao.delete(formula.getFormulaEntity());
    }

}
