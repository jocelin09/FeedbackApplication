package com.example.feedbackapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThankuActivityScore extends BaseActivity {

    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    View decorView;

    //    DatabaseHelper dbh;
//    SharedPreferences prefs;3
    String ShowLogo, Timeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanku);

        //  Toast.makeText(this, "ThankuActivityScore called...", Toast.LENGTH_SHORT).show();
//        prefs = PreferenceManager.getDefaultSharedPreferences(ThankuActivityScore.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("QuestNo", 1);
        editor.commit();
        try {
//        dbh = new DatabaseHelper(ThankuActivityScore.this);
            ContentValues values = new ContentValues();
            values.put("Weightage", "no");
            SQLiteDatabase sqLiteDatabase1 = dbh.getWritableDatabase();
            sqLiteDatabase1.update("feedback_adminquestions", values, null, null);
            sqLiteDatabase1.close();

           /* SQLiteDatabase db1 = dbh.getWritableDatabase();

            Cursor cursor1 = db1.rawQuery("Select Timeout,ShowLogo from store_setting ;", null);


            if (cursor1.moveToFirst()) {
                do {
                    Timeout = cursor1.getString(0);
                    ShowLogo = cursor1.getString(1);

                } while (cursor1.moveToNext());
                db1.close();
            }
        } */

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
//
//                startActivity(intent);
//            }
//        },Integer.parseInt(Timeout));
            countDownTimer = new CountDownTimer(20000, 1000) {

                public void onTick(long millisUntilFinished) {
                    Log.i("********", "seconds remaining: " + millisUntilFinished / 1000);
                    Toast.makeText(ThankuActivityScore.this, "seconds remaining for timeout: " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                }

                public void onFinish() {
                    Log.i("********", "Timer Finished");
                    countDownTimer.cancel();
                    Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                    startActivity(intent);
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
