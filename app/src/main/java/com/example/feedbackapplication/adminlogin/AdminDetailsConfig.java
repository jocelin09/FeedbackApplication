package com.example.feedbackapplication.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedbackapplication.R;
import com.example.feedbackapplication.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class AdminDetailsConfig extends AppCompatActivity {

//Spinner s_client, s_region, s_site, s_building, s_wing, s_floor, s_areatype, s_area;
//TextView greeting;
DatabaseHelper databaseHelper;
SQLiteDatabase sqLiteDatabase;

private ArrayList<String> company_names = new ArrayList<String>();
private ArrayList<String> location_names = new ArrayList<String>();
private ArrayList<String> site_names = new ArrayList<String>();
private ArrayList<String> building_names = new ArrayList<String>();
private ArrayList<String> wing_names = new ArrayList<String>();
private ArrayList<String> floor_names = new ArrayList<String>();

int questionscount;
String str_companyname,str_locationname;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admindetailsconfig);
    
    databaseHelper = new DatabaseHelper(this);
    sqLiteDatabase = databaseHelper.getWritableDatabase();
    
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
    
    final LinearLayout main_Layout = (LinearLayout) findViewById(R.id.main_admindetails_layout); //vertical
    main_Layout.addView(textView());
    
    //////////COMPANY NAME/////////
    company_names = databaseHelper.getAllCompanyNames();
    
    final Spinner company_spinner = new Spinner(this);
    final LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, 60);
    params1.setMargins(16,8,16,8);
    company_spinner.setBackground(getDrawable(R.drawable.edit_style));
    company_spinner.setLayoutParams(params1);
    
    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, company_names);
    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_text);
    company_spinner.setAdapter(spinnerArrayAdapter);
    
    
    final Spinner site_spinner = new Spinner(getApplicationContext());
    site_spinner.setBackground(getDrawable(R.drawable.edit_style));
    site_spinner.setLayoutParams(params1);
    
    company_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            str_companyname = company_spinner.getSelectedItem().toString();
            System.out.println("str_companyname = " + str_companyname);
    
            //////////LOCATION NAME//////////
            location_names = databaseHelper.getAllLocations(str_companyname);
            if (!location_names.isEmpty())
            {
                    System.out.println(" not EMPTY = ");
                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.spinner_text, location_names);
                    spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_dropdown_text);
                    site_spinner.setAdapter(spinnerArrayAdapter1);
                    str_locationname = site_spinner.getSelectedItem().toString();
                    System.out.println("str_locationname = " + str_locationname);
    
                    if (site_spinner.getParent() != null) {
                        ((ViewGroup) site_spinner.getParent()).removeView(site_spinner); // <- fix
                    }
                    main_Layout.addView(site_spinner);
            }
            else
            {
                System.out.println(" 3 = " );
                main_Layout.removeView(site_spinner);
                
            }
        }
    
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            System.out.println(" Nothing selected...... ");
        }
    });
   
    main_Layout.addView(company_spinner);
    
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

private TextView textView() {
    final TextView textView = new TextView(this);
    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
    textView.setLayoutParams(params1);
    textView.setCompoundDrawablePadding(16);
    textView.setGravity(Gravity.CENTER_HORIZONTAL);
    textView.setPadding(16, 16, 16, 16);
    textView.setTextColor(Color.BLACK);
    textView.setHintTextColor(Color.BLACK);
    textView.setTypeface(null, Typeface.BOLD);
    textView.setTextSize(1, 50);
    textView.setText(greetingMessage());
    
    return textView;
    
}

private Spinner spinner(int s_id, ArrayList<String> spinner_company) {
    final Spinner spinner = new Spinner(this);
    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, 60);
    params1.setMargins(16,8,16,8);
    spinner.setBackground(getDrawable(R.drawable.edit_style));
    spinner.setLayoutParams(params1);
    
    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, spinner_company);
    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_text);
    spinner.setAdapter(spinnerArrayAdapter);
    
    return spinner;
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
        
        
        //textSpinnerList.add(spinner.getSelectedItemPosition(), spinner);
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
