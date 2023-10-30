package com.example.myapplication.ui;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FormulaEntity.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract FormulaDao FormulaDao();
}
