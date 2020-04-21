package com.example.feedbackapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ThankuActivityScore extends AppCompatActivity {

    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    View decorView;

    DatabaseHelper dbh;
    SharedPreferences prefs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanku);
        
      //  Toast.makeText(this, "ThankuActivityScore called...", Toast.LENGTH_SHORT).show();
        prefs = PreferenceManager.getDefaultSharedPreferences(ThankuActivityScore.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("QuestNo", 1);
        editor.commit();
        
        dbh = new DatabaseHelper(ThankuActivityScore.this);
        ContentValues values = new ContentValues();
        values.put("Is_Rated", "no");
        SQLiteDatabase sqLiteDatabase1 = dbh.getWritableDatabase();
        sqLiteDatabase1.update("feedback_questions", values, null, null);
        sqLiteDatabase1.close();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                
                startActivity(intent);
            }
        },5000);
        
    }
}
