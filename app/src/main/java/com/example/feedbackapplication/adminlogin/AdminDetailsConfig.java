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

import com.example.feedbackapplication.BaseActivity;
import com.example.feedbackapplication.R;
import com.example.feedbackapplication.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class AdminDetailsConfig extends BaseActivity {

//Spinner s_client, s_region, s_site, s_building, s_wing, s_floor, s_areatype, s_area;
//TextView greeting;
//SQLiteDatabase sqLiteDatabase;

private ArrayList<String> company_names = new ArrayList<String>();
private ArrayList<String> location_names = new ArrayList<String>();
private ArrayList<String> site_names = new ArrayList<String>();
private ArrayList<String> building_names = new ArrayList<String>();
private ArrayList<String> wing_names = new ArrayList<String>();
private ArrayList<String> floor_names = new ArrayList<String>();

int questionscount;
String str_companyname, str_locationname,str_sitename,str_buildingname;
String company_id, location_id, site_id, building_id, wing_id, floor_id, area_id;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admindetailsconfig);

//    databaseHelper = new DatabaseHelper(this);
//    sqLiteDatabase = databaseHelper.getWritableDatabase();
    
    questionscount = dbh.admindetails_count();
    
    if (questionscount == 0) {
        dbh.insertAdminDetails("11111", "Dell Score Feedback", "12", "Location 1",
                "13", "Site 1", "14", "Building 1", "15", "Wing-1", "16",
                "1st Floor", "17", "Washroom");
        
        dbh.insertAdminDetails("22222", "ISS Feedback", "", "",
                "24", "Site 2", "25", "Building 2", "123456", "Wing extra", "27",
                "2nd Floor", "28", "Cafeteria");
        
        dbh.insertAdminDetails("22222", "ISS Feedback", "", "",
                "24", "Site 3", "25", "Building 3", "", "", "27",
                "3rd Floor", "28", "Washroom");
        
        dbh.insertAdminDetails("11111", "Dell Score Feedback", "21", "Location 2",
                "22", "Site 4", "23", "Building 4", "24", "Wing 2", "18",
                "4th Floor", "17", "Washroom");
        
        dbh.insertAdminDetails("11111", "Dell Score Feedback", "12", "Location 3",
                "13", "Site 5", "14", "Building 5", "15", "Wing 3", "16",
                "5th Floor", "17", "Washroom");
        
        dbh.insertAdminDetails("333333", "NTT Feedback", "120", "Location 4",
                "", "", "17", "Building 6", "15", "Wing 4", "16",
                "6th Floor", "17", "Washroom");
        
        dbh.insertAdminDetails("444444", "E-clerx", "", "",
                "", "", "157", "Building 7", "15", "Wing 5", "16",
                "7th Floor", "17", "Washroom");
    }
    
    final LinearLayout main_Layout = (LinearLayout) findViewById(R.id.main_admindetails_layout); //vertical
    main_Layout.addView(textView());
    
    //////////COMPANY NAME/////////
    company_names = dbh.getAllCompanyNames();
    
    final Spinner company_spinner = new Spinner(this);
    final LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, 60);
    params1.setMargins(16, 8, 16, 8);
    company_spinner.setBackground(getDrawable(R.drawable.edit_style));
    company_spinner.setLayoutParams(params1);
    
    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, company_names);
    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    company_spinner.setAdapter(spinnerArrayAdapter);
    
    ////////////LOCATION NAME//////////
    final Spinner location_spinner = new Spinner(getApplicationContext());
    location_spinner.setBackground(getDrawable(R.drawable.edit_style));
    location_spinner.setLayoutParams(params1);
    
    ////////////SITE NAME//////////
    final Spinner site_spinner = new Spinner(getApplicationContext());
    site_spinner.setBackground(getDrawable(R.drawable.edit_style));
    site_spinner.setLayoutParams(params1);
    
    ////////////BUILDING NAME//////////
    final Spinner building_spinner = new Spinner(getApplicationContext());
    building_spinner.setBackground(getDrawable(R.drawable.edit_style));
    building_spinner.setLayoutParams(params1);
    
    ////////////WING NAME//////////
    final Spinner wing_spinner = new Spinner(getApplicationContext());
    wing_spinner.setBackground(getDrawable(R.drawable.edit_style));
    wing_spinner.setLayoutParams(params1);
    
    company_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            str_companyname = company_spinner.getSelectedItem().toString();
            
            //////////LOCATION NAME//////////
            location_names = dbh.getAllLocations(str_companyname);
            System.out.println("Add location spinner.");
    
            if (!location_names.isEmpty()) {
                ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.spinner_text, location_names);
                spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
                if (!str_companyname.equals("")){
                    company_id = dbh.getCompanyId(str_companyname);
                    company_spinner.setId(Integer.parseInt(company_id));
                }
                
                location_spinner.setAdapter(spinnerArrayAdapter1);
                
                if (location_names.size() == 0)
                {
                    main_Layout.removeView(location_spinner);
                }
                else
                {
                    if (location_spinner.getParent() != null) {
                        ((ViewGroup) location_spinner.getParent()).removeView(location_spinner); //
                    }
                    main_Layout.addView(location_spinner);
                }
    
                location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        str_locationname = location_spinner.getSelectedItem().toString();
                        
                        site_names = dbh.getAllSites(str_companyname,str_locationname);
                        if (!site_names.isEmpty())
                        {
                            ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_text, site_names);
                            spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            if (!str_locationname.equals(""))
                            {
                                location_id = dbh.getLocationId(str_locationname);
                                location_spinner.setId(Integer.parseInt(location_id));
                                
                            }
                            site_spinner.setAdapter(spinnerArrayAdapter2);
    
                            if (site_names.size() == 0)
                            {
                                main_Layout.removeView(site_spinner);
    
                            }
                            else
                            {
                                if (site_spinner.getParent() != null) {
                                    ((ViewGroup) site_spinner.getParent()).removeView(site_spinner); //
                                }
                                main_Layout.addView(site_spinner);
                            }
                            
    
                            site_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    
                                    str_sitename = site_spinner.getSelectedItem().toString();
                                    
                                    building_names = dbh.getAllBuildings(str_companyname,str_locationname,str_sitename);
                                    if (!building_names.isEmpty())
                                    {
                                        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),
                                                R.layout.spinner_text, building_names);
                                        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        if (!str_sitename.equals(""))
                                        {
                                            site_id = dbh.getSiteId(str_sitename);
                                            site_spinner.setId(Integer.parseInt(site_id));
                                        }
    
                                        building_spinner.setAdapter(spinnerArrayAdapter3);
                                        if (building_names.size() == 0)
                                        {
                                            main_Layout.removeView(building_spinner);
        
                                        }
                                        else
                                        {
                                            if (building_spinner.getParent() != null) {
                                                ((ViewGroup) building_spinner.getParent()).removeView(building_spinner); //
                                            }
                                            main_Layout.addView(building_spinner);
                                        }
                                        
                                        building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                str_buildingname = building_spinner.getSelectedItem().toString();
                                                System.out.println("str_buildingname ===== " + str_buildingname);
                                                
                                                wing_names = dbh.getAllWings(str_companyname,str_locationname,str_sitename,str_buildingname);
    
                                                if (!wing_names.isEmpty())
                                                {
                                                    ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),
                                                            R.layout.spinner_text, wing_names);
                                                    spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    if (!str_buildingname.equals(""))
                                                    {
                                                        building_id = dbh.getBuildingId(str_buildingname);
                                                        building_spinner.setId(Integer.parseInt(building_id));
                                                    }
                                                    wing_spinner.setAdapter(spinnerArrayAdapter4);
                                                    
                                                    if (wing_names.size() == 0)
                                                    {
                                                        main_Layout.removeView(wing_spinner);
                                                    }
                                                    else
                                                    {
                                                        if (wing_spinner.getParent() != null) {
                                                            ((ViewGroup) wing_spinner.getParent()).removeView(wing_spinner); //
                                                        }
                                                        main_Layout.addView(wing_spinner);
                                                    }
    
                                                }
                                                else {
                                                    main_Layout.removeView(wing_spinner);
                                                }
                                            }
    
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {
        
                                            }
                                        });
                                    }
                                    else
                                    {
                                        main_Layout.removeView(building_spinner);
                                    }
    
                                }
    
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
        
                                }
                            });
                        }
                        else
                        {
                            str_sitename = "";
    
                            main_Layout.removeView(site_spinner);
                            System.out.println("When no sites available display next spinner building..");
    
                            building_names = dbh.getAllBuildings(str_companyname,str_locationname,str_sitename);
                            if (!building_names.isEmpty())
                            {
                                ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),
                                        R.layout.spinner_text, building_names);
                                spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                building_spinner.setAdapter(spinnerArrayAdapter3);
    
                                if (building_names.size() == 0)
                                {
                                    main_Layout.removeView(building_spinner);
        
                                }
                                else
                                {
                                    if (building_spinner.getParent() != null) {
                                        ((ViewGroup) building_spinner.getParent()).removeView(building_spinner); //
                                    }
                                    main_Layout.addView(building_spinner);
                                }
                            }
                        }
                    }
    
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
        
                    }
                });
            
            }
            
            else
                {
                if (location_names.size() == 0)
                {
                    System.out.println("When Location is blank..add next spinner site name");
                    str_locationname = "";
    
                    main_Layout.removeView(location_spinner);
                    
                    site_names = dbh.getAllSites(str_companyname,str_locationname);
                    if (!site_names.isEmpty())
                    {
                        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.spinner_text, site_names);
                        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        site_spinner.setAdapter(spinnerArrayAdapter2);
    
                        if (site_names.size() == 0)
                        {
                            main_Layout.removeView(site_spinner);
                        }
                        else
                            {
                            if (site_spinner.getParent() != null) {
                                ((ViewGroup) site_spinner.getParent()).removeView(site_spinner); //
                            }
                            main_Layout.addView(site_spinner);
                        }
        
                        site_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                str_sitename = site_spinner.getSelectedItem().toString();
                                
                                    building_names = dbh.getAllBuildings(str_companyname,str_locationname,str_sitename);
                                if (!building_names.isEmpty())
                                {
                                    ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),
                                            R.layout.spinner_text, building_names);
                                    spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    if (!str_sitename.equals(""))
                                    {
                                        site_id = dbh.getSiteId(str_sitename);
                                        site_spinner.setId(Integer.parseInt(site_id));
                                    }
                                    building_spinner.setAdapter(spinnerArrayAdapter3);
    
                                    if (building_names.size() == 0)
                                    {
                                        main_Layout.removeView(building_spinner);
                                    }
                                    else
                                    {
                                        if (building_spinner.getParent() != null) {
                                            ((ViewGroup) building_spinner.getParent()).removeView(building_spinner); //
                                        }
                                        main_Layout.addView(building_spinner);
                                    }
                                    
                                    building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            str_buildingname = building_spinner.getSelectedItem().toString();
                                            
                                            wing_names = dbh.getAllWings(str_companyname,str_locationname,str_sitename,str_buildingname);
                                            if (!wing_names.isEmpty())
                                            {
                                                ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),
                                                        R.layout.spinner_text, wing_names);
                                                spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                wing_spinner.setAdapter(spinnerArrayAdapter4);
        
                                                if (wing_names.size() == 0)
                                                {
                                                    main_Layout.removeView(wing_spinner);
                                                }
                                                else
                                                {
                                                    if (wing_spinner.getParent() != null) {
                                                        ((ViewGroup) wing_spinner.getParent()).removeView(wing_spinner); //
                                                    }
                                                    main_Layout.addView(wing_spinner);
                                                }
        
                                            }
                                            else
                                            {
                                                main_Layout.removeView(wing_spinner);
                                            }
                                            
                                            
                                        }
    
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
        
                                        }
                                    });
                                }
                                else
                                    {
                                    main_Layout.removeView(building_spinner);
                                    main_Layout.removeView(wing_spinner);
                                }
                            }
    
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
        
                            }
                        });
                    }
                    else
                    {
                        main_Layout.removeView(site_spinner);
                        main_Layout.removeView(building_spinner);
                        main_Layout.removeView(wing_spinner);
                        
                        //Add building data when location, site data is blank
                        System.out.println("Add building data when location, site data is blank");
                        str_locationname="";
                        str_sitename="";
                        
                        building_names = dbh.getAllBuildings(str_companyname,str_locationname,str_sitename);
                        
                        if (!building_names.isEmpty())
                        {
                            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_text, building_names);
                            spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                         
                            building_spinner.setAdapter(spinnerArrayAdapter3);
                            if (building_names.size() == 0)
                            {
                                main_Layout.removeView(building_spinner);
                            }
                            else
                            {
                                if (building_spinner.getParent() != null) {
                                    ((ViewGroup) building_spinner.getParent()).removeView(building_spinner); //
                                }
        
                                main_Layout.addView(building_spinner);
                            }
                            
                            building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    
                                    str_buildingname = building_spinner.getSelectedItem().toString();
                                    wing_names = dbh.getAllWings(str_companyname,str_locationname,str_sitename,str_buildingname);
                                    if (!wing_names.isEmpty())
                                    {
                                        ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),
                                                R.layout.spinner_text, wing_names);
                                        spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        wing_spinner.setAdapter(spinnerArrayAdapter4);
    
                                        if (wing_names.size() == 0)
                                        {
                                            main_Layout.removeView(wing_spinner);
                                        }
                                        else
                                        {
                                            if (wing_spinner.getParent() != null) {
                                                ((ViewGroup) wing_spinner.getParent()).removeView(wing_spinner); //
                                            }
                                            main_Layout.addView(wing_spinner);
                                        }
                                        
                                    }
                                    else
                                    {
                                        main_Layout.removeView(wing_spinner);
                                    }
    
                                }
    
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
        
                                }
                            });
                        }
                        else
                        {
                            main_Layout.removeView(building_spinner);
                        }
                    }
                }
                else
                {
                    System.out.println("For Select Company Name Spinner ");
                    main_Layout.removeView(location_spinner);
                    main_Layout.removeView(site_spinner);
                    main_Layout.removeView(building_spinner);
                    main_Layout.removeView(wing_spinner);
                }
               
                
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
    params1.setMargins(16, 8, 16, 8);
    spinner.setBackground(getDrawable(R.drawable.edit_style));
    spinner.setLayoutParams(params1);
    
    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, spinner_company);
    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
