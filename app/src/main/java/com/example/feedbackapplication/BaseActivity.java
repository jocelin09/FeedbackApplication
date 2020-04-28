package com.example.feedbackapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.feedbackapplication.database.DatabaseHelper;

public class BaseActivity extends AppCompatActivity {

    public DatabaseHelper dbh;

    public SQLiteDatabase sqLiteDatabase;

    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
        dbh = new DatabaseHelper(BaseActivity.this);
        sqLiteDatabase = dbh.getWritableDatabase();
    }

@Override
public void onBackPressed() {
    //super.onBackPressed();
}

}
