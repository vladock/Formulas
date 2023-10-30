package com.example.myapplication.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.ui.home.VariablesAdapter;
import com.google.android.material.textfield.TextInputEditText;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private TextView desc;
    private TextView formula;
    private TextView usedFormula;
    private TextInputEditText vars;
    private Button button;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        desc = root.findViewById(R.id.formulaDescription);
        formula = root.findViewById(R.id.formulaText2);

        if (MainActivity.searchedFormula==null){
            Toast.makeText(this.getContext(),"Choose Formula",Toast.LENGTH_LONG).show();
        }else {
            desc.setText(MainActivity.searchedFormula.getDescription());
            formula.setText(MainActivity.searchedFormula.getFormula());
        }
        vars = root.findViewById(R.id.getVarsToUse);
        button = root.findViewById(R.id.use);
        usedFormula = root.findViewById(R.id.usedFormula);
        button.setOnClickListener(v -> {
            String used = MainActivity.searchedFormula.getFormula();
            String var = vars.getText().toString();
            String replace = "";
            String replaceTo = "";
            boolean c = true;
            boolean d = false;
            for (int i = 0; i < var.length(); i++) {
                if(var.charAt(i)=='-' && c){
                    c=false;
                    d=true;
                    replaceTo="";
                    continue;
                }
                if(var.charAt(i)==';'||i==var.length()-1){
                    c=true;
                    d=false;
                    used = used.replaceAll(replace,replaceTo);
                    replace="";
                    replaceTo="";
                    continue;
                }
                if(c){
                    replace += var.charAt(i);
                }
                if(d){
                    replaceTo += var.charAt(i);
                }
            }
            used = used.replaceAll("--","+");
            used = used.replaceAll("-+","-");
            used = used.replaceAll("\\+-","-");
            used = used.replaceAll("\\+\\+","+");
            usedFormula.setText(used);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}