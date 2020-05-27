package com.example.feedbackapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.feedbackapplication.database.DatabaseHelper;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    public DatabaseHelper dbh;

    public SQLiteDatabase sqLiteDatabase;

    public SharedPreferences prefs;

    public CountDownTimer countDownTimer;

    public Toast toast;

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

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public String DeviceDetails() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        return manufacturer + model;
    }
}
