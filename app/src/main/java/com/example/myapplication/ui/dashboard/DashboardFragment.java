package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import com.example.myapplication.databinding.FragmentDashboardBinding;
import com.example.myapplication.ui.Formula;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.ui.MyRecyclerViewAdapter;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    public static MyRecyclerViewAdapter adapter;
    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        //создание recyclerView с формулами
        recyclerView = root.findViewById(R.id.Formulas);

        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());

        adapter = new MyRecyclerViewAdapter(root.getContext(),MainActivity.formulas);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}