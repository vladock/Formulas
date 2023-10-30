package com.example.myapplication.ui;

import androidx.room.ColumnInfo;

import com.example.myapplication.ui.home.Variable;

import java.util.ArrayList;
import java.util.List;

public class Formula {
    @ColumnInfo(name = "formula")
    String formula;
    @ColumnInfo(name = "description")
    String descriptioin;
    ArrayList<Variable> variables;
    @ColumnInfo(name = "description")
    String vars;

    public Formula(String formula, String descriptioin, ArrayList<Variable> variables) {
        this.formula = formula;
        this.descriptioin = descriptioin;
        this.variables = variables;
    }
    public String getFormula() {
        return formula;
    }

    public String getDescription() {
        return descriptioin;
    }
    public String getVars(){
        if(vars==null) createVars();
        return vars;
    }
    //Функция для преобразования списка переменных в единую строку
    public void createVars(){
        if(variables == null) return;
        if(vars==null) this.vars = "";
        for(Variable variable: variables){
            if(variable.getVar().equals("")||variable.getVarDesc().equals(""))continue;
            this.vars += variable.getVar() + "-" + variable.getVarDesc() + "\n";
        }
    }
    public ArrayList<Variable> getVariables() {
        createVariables();
        return variables;
    }

    public void setVars(String vars) {
        this.vars = vars;
    }
    //Статически метод, который преобразует список Entity в формулы

    public static ArrayList<Formula> getFromEntity(List<FormulaEntity> formulaEntities){
        if(formulaEntities == null) return null;
        ArrayList<Formula> formulas = new ArrayList<>();
        for(FormulaEntity formulaEntity: formulaEntities){
            Formula formula = new Formula(formulaEntity.formula,formulaEntity.desc,null);
            formula.setVars(formulaEntity.vars);
            formulas.add(formula);
        }
        return formulas;
    }
    public void createVariables(){
        ArrayList<Variable> variables = new ArrayList<>();
        boolean a = true;
        boolean b = false;
        String v = "";
        String d = "";
        for (int i = 0; i < vars.length(); i++) {
            if(vars.charAt(i)=='\n'){
                a = true;
                b = false;
                Variable variable = new Variable(null);
                variable.setVariable(v);
                variable.setDesc(d);
                variables.add(variable);
                v = "";
                d = "";
            }
            if(vars.charAt(i)=='-'){
                a = false;
                b = true;
            }
            if(a){
                v += vars.charAt(i);
            }
            if (b){
                d += vars.charAt(i);
            }
        }
        this.variables = variables;
    }
    public FormulaEntity getFormulaEntity(){
        FormulaEntity formulaEntity = new FormulaEntity();
        formulaEntity.formula = formula;
        formulaEntity.desc = descriptioin;
        createVars();
        formulaEntity.vars = vars;
        formulaEntity.ID = System.nanoTime();
        return formulaEntity;
    }
}
