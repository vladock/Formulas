package com.example.myapplication.ui;

import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.example.myapplication.ui.home.Variable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static Formula searchedFormula;
    public static ArrayList<Formula> formulas;
    public static AppDataBase db;
    public static FormulaDao formulaDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getDatabasePath("FormulaEntity").exists()) db = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"FormulaEntity").createFromAsset(getDatabasePath("FormulaEntity").getPath()).setJournalMode(RoomDatabase.JournalMode.TRUNCATE).allowMainThreadQueries().build();
        else db = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"FormulaEntity").setJournalMode(RoomDatabase.JournalMode.TRUNCATE).allowMainThreadQueries().build();
        formulaDao = db.FormulaDao();
        LiveData<List<FormulaEntity>> formulaEntitiesLiveData = formulaDao.getAll();
        if (formulaEntitiesLiveData != null && formulaEntitiesLiveData.getValue() != null) {
            formulas = Formula.getFromEntity(formulaEntitiesLiveData.getValue());
        } else {
            formulas = new ArrayList<>();
        }
        formulaDao.getAll().observe(this, new Observer<List<FormulaEntity>>() {
            @Override
            public void onChanged(List<FormulaEntity> formulaEntities) {
                if (formulaEntities != null) {
                    formulas = Formula.getFromEntity(formulaEntities);
                    if(DashboardFragment.adapter!=null) DashboardFragment.adapter.notifyDataSetChanged();
                }
            }
        });
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DashboardFragment.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                DashboardFragment.adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        formulaDao.getAll().removeObservers(this);
        formulaDao.deleteAll();
        ArrayList<FormulaEntity> formulaEntities = new ArrayList<>();
        for(Formula formula: formulas){
            formulaEntities.add(formula.getFormulaEntity());
        }
        formulaDao.insertAll(formulaEntities);
        db.close();
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();

    }
}