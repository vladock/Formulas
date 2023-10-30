package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.Formula;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    TextInputEditText description;
    TextInputEditText f;
    RecyclerView variables;
    Button addvar;
    Button add;
    Button delete;
    VariablesAdapter adapter;
    public static ArrayList<Variable> variablesArray;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Получение объекта view
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Получение экземпляров элементов
        description = root.findViewById(R.id.description);
        f = root.findViewById(R.id.F);
        add = root.findViewById(R.id.add_button);
        addvar = root.findViewById(R.id.addvar);
        delete = root.findViewById(R.id.delButton);
        //создание recyclerview с переменными
        variablesArray = new ArrayList<>();
        variables = root.findViewById(R.id.variables);
        adapter = new VariablesAdapter(root.getContext(), variablesArray);
        variables.setAdapter(adapter);
        variables.setLayoutManager(new LinearLayoutManager(root.getContext()));
        variables.setItemAnimator(null);
        //назначение функций кнопок
        addvar.setOnClickListener(v -> {
            variablesArray.add(new Variable(root));
            adapter.notifyItemInserted(variablesArray.size()-1);
            });
        delete.setOnClickListener(v -> {
            if(variablesArray.size()==0) return;
            variablesArray.remove(variablesArray.size()-1);
            adapter.notifyDataSetChanged();
        });
        add.setOnClickListener(v -> {
            String form = f.getText().toString();
            String desc = description.getText().toString();
            ArrayList<Variable> vars = variablesArray;
            if(form.equals("") || desc.equals("")) return;
            for (int i = 0; i < vars.size(); i++) {
                if(vars.get(i).getVar().equals("")) return;
            }
            Formula formula = new Formula(form,desc,vars);
            MainActivity.formulaDao.insert(formula.getFormulaEntity());
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}