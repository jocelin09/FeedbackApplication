package com.example.feedbackapplication;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ApplicationClass extends Application {
    private static ApplicationClass mInstance;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static synchronized ApplicationClass getInstance() {
        return mInstance;
    }

    public String yymmddhhmmss()
    {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) return true;
        else return false;
    }


/*
    public View hideSystemUI(View view) {
        //View decorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        return    view;
    }
*/

    public View hideSystemUI(View view) {
        //View decorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        return    view;
    }

    public String urlString(){
        //return "https://punctualiti.com/hclfeedback/";
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
//            return "http://punctualiti.com/eclerxfeedback/";
//        } else {
//            return "https://punctualiti.com/eclerxfeedback/";
//        }

        return "http://45.118.160.162:81/punctualiti/com/dell_bangalore_feedback/";
    }

}