package com.example.myapplication.ui.home;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

public class Variable {
    TextInputEditText var;
    String variable;
    String desc;
    TextInputEditText vardesc;

    public Variable(View view) {
        if(view==null)return;
        var = view.findViewById(R.id.var);
        vardesc = view.findViewById(R.id.vardesc);
    }
    public String getVar() {
        if(var!=null){
            return var.getText().toString();
        }
        return "";
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVarDesc() {

        if(vardesc!=null){
            return vardesc.getText().toString();
        }
        return "";
    }
}
