package com.example.myapplication.ui;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FormulaDao {
    @Query("SELECT * FROM FormulaEntity")
    LiveData<List<FormulaEntity>> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FormulaEntity> formulas);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FormulaEntity formula);
    @Delete
    void delete(FormulaEntity formula);
    @Query("DELETE FROM FormulaEntity")
    public void deleteAll();
    @RawQuery
    boolean checkpoint(SupportSQLiteQuery supportSQLiteQuery);
}
