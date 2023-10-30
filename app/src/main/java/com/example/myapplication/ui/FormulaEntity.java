package com.example.myapplication.ui;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.ui.home.Variable;

import java.util.ArrayList;

@Entity(tableName = "FormulaEntity")
public class FormulaEntity {
    @PrimaryKey
    public long ID;
    @ColumnInfo(name = "desc")
    public String desc;
    @ColumnInfo(name = "formula")
    public String formula;
    @ColumnInfo(name = "vars")
    public String vars;
}
