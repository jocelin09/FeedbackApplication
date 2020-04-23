package com.example.feedbackapplication.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedbackapplication.R;
import com.example.feedbackapplication.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AdminDetailsConfig extends AppCompatActivity {

    Spinner s_client, s_region, s_site, s_building, s_wing, s_floor, s_areatype, s_area;
    TextView greeting;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    private ArrayList<Spinner> textSpinnerList = new ArrayList<Spinner>();
    int questionscount;

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
        questionscount = databaseHelper.admindetails_count();
        if (questionscount == 0) {
            databaseHelper.insertAdminDetails("1", "Dell Score Feedback", "12", "Mumbai",
                    "13", "Mira Rd", "14", "Dell-6", "15", "Wing-A", "16",
                    "1st Floor", "17", "Washroom");
            databaseHelper.insertAdminDetails("2", "ISS Feedback", "23", "Bangalore",
                    "24", "Mysore", "25", "HP Tower", "", "", "27",
                    "7-Floor", "28", "Cafeteria");
            databaseHelper.insertAdminDetails("1", "Dell Score Feedback", "21", "Pune",
                    "22", "Pimpri", "23", "Dell-5", "24", "Wing-C", "18",
                    "2nd Floor", "17", "Washroom");
            databaseHelper.insertAdminDetails("1", "Dell Score Feedback", "12", "Mumbai",
                    "13", "Mira Rd", "14", "Dell-6", "15", "Wing-A", "16",
                    "1st Floor", "17", "Washroom");
        }
        try {
            SQLiteDatabase db1 = databaseHelper.getWritableDatabase();
            // Cursor cursor1 = db1.rawQuery("Select * from feedback_adminquestions where ID =" + (totalfeedback + 1) + ";", null); //
            Cursor cursor1 = db1.rawQuery("Select * from admin_login ;", null);
            String feedback_question = "", order_id, a_id, icon_type;
            int id;
            if (cursor1.moveToFirst()) {
                do {


                } while (cursor1.moveToNext());
                db1.close();
            }

        } catch (Exception e) {
        }
    }

    private String greetingMessage() {

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String daytime = "";

        if (timeOfDay >= 0 && timeOfDay < 12) {
            daytime = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            daytime = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            daytime = "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            daytime = "Good Night";

        }
        return daytime;
    }


    private Spinner spinner(int a_id, String strvalue, String matchvalue) {
        Spinner spinner = new Spinner(this);
        spinner.setId(a_id);
        List<String> list = new ArrayList<String>();
        try {
            String[] str_chk1 = strvalue.split(",");
            list.add("Select");
            int selectspinner = 0;
            for (int i = 0; i < str_chk1.length; i++) {
                list.add(str_chk1[i]);
                if (str_chk1[i].equals(matchvalue)) {

                    selectspinner = i + 1;
                }
            }


            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

//            MySpinnerAdapter adapter = new MySpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, list);
//            spinner.setAdapter(adapter);


            textSpinnerList.add(spinner.getSelectedItemPosition(), spinner);
            if (selectspinner > 0) {
                spinner.setSelection(selectspinner);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error code: fd814", Toast.LENGTH_SHORT).show();
            System.out.println("  EXCEPTION fd814==" + e);
        }
        //spinner=spinner.getSelectedItemPosition();
        return spinner;
    }

    private static class MySpinnerAdapter extends ArrayAdapter<String> {


        private MySpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        // Affects default (closed) state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            return view;
        }
    }
}
