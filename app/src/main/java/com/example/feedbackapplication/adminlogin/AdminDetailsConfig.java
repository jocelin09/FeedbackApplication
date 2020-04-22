package com.example.feedbackapplication.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.feedbackapplication.R;
import com.example.feedbackapplication.database.DatabaseHelper;

import java.util.Calendar;

public class AdminDetailsConfig extends AppCompatActivity {

    Spinner s_client,s_region,s_site,s_building,s_wing,s_floor,s_areatype,s_area;
    TextView greeting;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindetailsconfig);
        
        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        
        greeting = (TextView) findViewById(R.id.greetingstxt);
        greeting.setText(greetingMessage());
    
        s_client = (Spinner) findViewById(R.id.clientname);
        s_region = (Spinner) findViewById(R.id.regionname);
        s_site = (Spinner) findViewById(R.id.sitename);
        s_building = (Spinner) findViewById(R.id.buildingname);
        s_wing = (Spinner) findViewById(R.id.wingname);
        s_floor = (Spinner) findViewById(R.id.floorname);
        s_areatype = (Spinner) findViewById(R.id.areatypename);
        s_area = (Spinner) findViewById(R.id.areaname);
        
    }

private String greetingMessage() {
    
    Calendar c = Calendar.getInstance();
    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
    String daytime= "";
    
    if(timeOfDay >= 0 && timeOfDay < 12){
        daytime = "Good Morning";
    }else if(timeOfDay >= 12 && timeOfDay < 16){
        daytime = "Good Afternoon";
    }else if(timeOfDay >= 16 && timeOfDay < 21){
        daytime = "Good Evening";
    }else if(timeOfDay >= 21 && timeOfDay < 24){
        daytime = "Good Night";
        
    }
    return daytime;
}

}
