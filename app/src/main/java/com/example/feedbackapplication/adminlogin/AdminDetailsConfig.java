package com.example.feedbackapplication.adminlogin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedbackapplication.BaseActivity;
import com.example.feedbackapplication.FeedbackActivity;
import com.example.feedbackapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.HORIZONTAL;

public class AdminDetailsConfig extends BaseActivity {


int questionscount;
String str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname, str_floorname,str_areaname,str_feedbackservice;
String company_id, location_id, site_id, building_id, wing_id, floor_id, area_id;
private ArrayList<String> company_names = new ArrayList<String>();
private ArrayList<String> location_names = new ArrayList<String>();
private ArrayList<String> site_names = new ArrayList<String>();
private ArrayList<String> building_names = new ArrayList<String>();
private ArrayList<String> wing_names = new ArrayList<String>();
private ArrayList<String> floor_names = new ArrayList<String>();
private List<String> area_names = new ArrayList<String>();
private ArrayList<String> feedback_service_name = new ArrayList<String>();

MultipleSelectionSpinner area_spinner;
Button button;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admindetailsconfig);
    
    questionscount = dbh.admindetails_count();
    //FEEDBACK SERVICE SPINNER
    feedback_service_name.add("Area Specific");
    feedback_service_name.add("Common Feedback");
    feedback_service_name.add("Common Feedback with Area Specific");
    
    if (questionscount == 0) {
        dbh.insertAdminDetails("11111", "Dell Score Feedback", "12", "Location 1",
                "13", "Site 1", "14", "Building 1", "15", "Wing-1", "16",
                "1st Floor", "17", "Washroom");
    
        dbh.insertAdminDetails("11111", "Dell Score Feedback", "12", "Location 1",
                "13", "Site 1", "14", "Building 1", "15", "Wing-1", "16",
                "1st Floor", "17", "Cafeteria");
        
        dbh.insertAdminDetails("11111", "Dell Score Feedback", "12", "Location 1",
                "13", "Site 5", "14", "Building 3", "", "", "16",
                "5th Floor", "17", "Cafeteria");
        
        dbh.insertAdminDetails("11111", "Dell Score Feedback", "21", "Location 2",
                "22", "Site 4", "23", "Building 4", "24", "Wing 2", "18",
                "4th Floor", "17", "Pantry");
        
        dbh.insertAdminDetails("22222", "ISS Feedback", "", "",
                "24", "Site 2", "25", "Building 2", "123456", "Wing extra", "27",
                "2nd Floor", "28", "Cafeteria");
        
        dbh.insertAdminDetails("22222", "ISS Feedback", "", "",
                "24", "Site 3", "25", "Building 3", "", "", "27",
                "3rd Floor", "28", "Washroom");
        
        dbh.insertAdminDetails("333333", "NTT Feedback", "120", "Location 4",
                "", "", "17", "Building 6", "15", "Wing 4", "16",
                "6th Floor", "17", "Washroom");
        
        dbh.insertAdminDetails("333333", "NTT Feedback", "120", "Location 47",
                "", "", "17", "Building 16", "15", "Wing 4", "4545",
                "13th Floor", "17", "Washroom");
        
        dbh.insertAdminDetails("444444", "E-clerx", "", "",
                "", "", "157", "Building 7", "15", "Wing 5", "16",
                "7th Floor", "17", "Washroom");
    }
    
    try {
        final LinearLayout main_Layout = (LinearLayout) findViewById(R.id.main_admindetails_layout); //vertical
        main_Layout.addView(textView());
        
        //////////COMPANY NAME/////////
        company_names = dbh.getAllCompanyNames();
        
        final Spinner company_spinner = new Spinner(this);
        final LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, 100);
        params1.setMargins(16, 8, 16, 8);
        company_spinner.setBackground(getDrawable(R.drawable.edit_style));
        company_spinner.setLayoutParams(params1);
        
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, company_names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        company_spinner.setAdapter(spinnerArrayAdapter);
        if (company_names.size() == 1)
        {
            company_spinner.setEnabled(false);
        }
        else
        {
            company_spinner.setEnabled(true);
        }
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
        
        ////////////FLOOR NAME//////////
        final Spinner floor_spinner = new Spinner(getApplicationContext());
        floor_spinner.setBackground(getDrawable(R.drawable.edit_style));
        floor_spinner.setLayoutParams(params1);
    
        ////////////AREA NAME//////////
        area_spinner = findViewById(R.id.mSpinner);
        area_spinner.setBackground(getDrawable(R.drawable.edit_style));
        area_spinner.setLayoutParams(params1);
        
    
        ////////////FFEDBACK SERVICE NAME//////////
        final Spinner feedback_service = new Spinner(getApplicationContext());
        feedback_service.setBackground(getDrawable(R.drawable.edit_style));
        feedback_service.setLayoutParams(params1);
        
        //BUTTONS LAYOUT
        final LinearLayout sub1_secondlayout = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams params_sub1 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        params_sub1.setMargins(16,8,16,8);
        sub1_secondlayout.setLayoutParams(params_sub1);
        sub1_secondlayout.setOrientation(HORIZONTAL);
       // sub1_secondlayout.setWeightSum(2f);
        sub1_secondlayout.setGravity(Gravity.CENTER);
    
    
        button = new Button(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        params2.setMargins(16,8,16,8);
        button.setLayoutParams(params2);
        //button.setGravity(Gravity.CENTER_HORIZONTAL);
        
    
        
        company_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_companyname = company_spinner.getSelectedItem().toString();
                try {
                    if (!str_companyname.equals("")) {
                        company_id = dbh.getCompanyId(str_companyname);
                        company_spinner.setId(Integer.parseInt(company_id));
                        System.out.println("company_spinner.getId() = " + company_spinner.getId());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("company id exception = " + e);
                }
                //////////LOCATION NAME//////////
                location_names = dbh.getAllLocations(str_companyname);
    
               
                
                if (!location_names.isEmpty()) {
    
                    //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                    if (location_names.size() == 1)
                    {
                        location_spinner.setEnabled(false);
                    }
                    else
                    {
                        location_spinner.setEnabled(true);
                    }
                    
                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.spinner_text, location_names);
                    spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    location_spinner.setAdapter(spinnerArrayAdapter1);
                    
//                    if (location_names.size() == 0) {
//                        main_Layout.removeView(location_spinner);
//                    } else {
                        if (location_spinner.getParent() != null) {
                            ((ViewGroup) location_spinner.getParent()).removeView(location_spinner); //
                        }
                        main_Layout.addView(location_spinner);
                   // }
                    
                    location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            str_locationname = location_spinner.getSelectedItem().toString();
                            try {
                                if (!str_locationname.equals("")) {
                                    location_id = dbh.getLocationId(str_locationname);
                                    location_spinner.setId(Integer.parseInt(location_id));
                                    System.out.println("location_spinner.getId() = " + location_spinner.getId());
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                System.out.println("Location Id Exception");
                            }
                            
                            site_names = dbh.getAllSites(str_companyname, str_locationname);
                            
                            if (!site_names.isEmpty()) {
    
                                //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                if (site_names.size() == 1)
                                {
                                    site_spinner.setEnabled(false);
                                }
                                else
                                {
                                    site_spinner.setEnabled(true);
                                }
                                
                                ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),
                                        R.layout.spinner_text, site_names);
                                spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                site_spinner.setAdapter(spinnerArrayAdapter2);
                                
//                                if (site_names.size() == 0) {
//                                    main_Layout.removeView(site_spinner);
//                                } else {
                                    if (site_spinner.getParent() != null) {
                                        ((ViewGroup) site_spinner.getParent()).removeView(site_spinner); //
                                    }
                                    main_Layout.addView(site_spinner);
                                //}
                                
                                
                                site_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        str_sitename = site_spinner.getSelectedItem().toString();
                                        try {
                                            if (!str_sitename.equals("")) {
                                                site_id = dbh.getSiteId(str_sitename);
                                                site_spinner.setId(Integer.parseInt(site_id));
                                                System.out.println("site_spinner.getId() = " + site_spinner.getId());
                                            }
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                            System.out.println("Site Id Exception");
                                        }
                                        
                                        building_names = dbh.getAllBuildings(str_companyname, str_locationname, str_sitename);
                                        
                                        if (!building_names.isEmpty()) {
                                            //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                            if (building_names.size() == 1)
                                            {
                                                building_spinner.setEnabled(false);
                                            }
                                            else
                                            {
                                                building_spinner.setEnabled(true);
                                            }
                                            
                                            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),
                                                    R.layout.spinner_text, building_names);
                                            spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            building_spinner.setAdapter(spinnerArrayAdapter3);
                                            
//                                            if (building_names.size() == 0) {
//                                                main_Layout.removeView(building_spinner);
//                                            } else {
                                                if (building_spinner.getParent() != null) {
                                                    ((ViewGroup) building_spinner.getParent()).removeView(building_spinner); //
                                                }
                                                main_Layout.addView(building_spinner);
                                           // }
                                            
                                            building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                    str_buildingname = building_spinner.getSelectedItem().toString();
                                                    try {
                                                        if (!str_buildingname.equals("")) {
                                                            building_id = dbh.getBuildingId(str_buildingname);
                                                            building_spinner.setId(Integer.parseInt(building_id));
                                                            System.out.println("building_spinner.getId() = " + building_spinner.getId());
                                                            
                                                        }
                                                    } catch (NumberFormatException e) {
                                                        e.printStackTrace();
                                                        System.out.println("Building Id Exception");
                                                    }
                                                    
                                                    wing_names = dbh.getAllWings(str_companyname, str_locationname, str_sitename, str_buildingname);
                                                    
                                                    if (!wing_names.isEmpty()) {
    
                                                        //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                        if (wing_names.size() == 1)
                                                        {
                                                            wing_spinner.setEnabled(false);
                                                        }
                                                        else
                                                        {
                                                            wing_spinner.setEnabled(true);
                                                        }
                                                        
                                                        ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),
                                                                R.layout.spinner_text, wing_names);
                                                        spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        wing_spinner.setAdapter(spinnerArrayAdapter4);
                                                        
//                                                        if (wing_names.size() == 0) {
//                                                            main_Layout.removeView(wing_spinner);
//                                                        } else {
                                                            if (wing_spinner.getParent() != null) {
                                                                ((ViewGroup) wing_spinner.getParent()).removeView(wing_spinner); //
                                                            }
                                                            main_Layout.addView(wing_spinner);
                                                        //}
                                                        
                                                        wing_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                str_wingname = wing_spinner.getSelectedItem().toString();
                                                                try {
                                                                    if (!str_wingname.equals("")) {
                                                                        wing_id = dbh.getWingId(str_wingname);
                                                                        wing_spinner.setId(Integer.parseInt(wing_id));
                                                                        System.out.println("wing_spinner.getId() = " + wing_spinner.getId());
                                                                    }
                                                                } catch (NumberFormatException e) {
                                                                    e.printStackTrace();
                                                                    System.out.println("Wing Id Exception");
                                                                }
                                                                
                                                                floor_names = dbh.getAllFloors(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname);
                                                                
                                                                if (!floor_names.isEmpty()) {
    
                                                                    //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                                    if (floor_names.size() == 1)
                                                                    {
                                                                        floor_spinner.setEnabled(false);
                                                                    }
                                                                    else
                                                                    {
                                                                        floor_spinner.setEnabled(true);
                                                                    }
                                                                    
                                                                    ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                            R.layout.spinner_text, floor_names);
                                                                    spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                    floor_spinner.setAdapter(spinnerArrayAdapter5);
                                                                    
//                                                                    if (floor_names.size() == 0)
//                                                                    {
//                                                                        main_Layout.removeView(floor_spinner);
//                                                                    } else {
                                                                        if (floor_spinner.getParent() != null) {
                                                                            ((ViewGroup) floor_spinner.getParent()).removeView(floor_spinner); //
                                                                        }
                                                                        main_Layout.addView(floor_spinner);
                                                                   // }
                                                                    
                                                                    floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                        @Override
                                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                            str_floorname = floor_spinner.getSelectedItem().toString();
                                                                            try {
                                                                                if (!str_floorname.equals("")) {
                                                                                    floor_id = dbh.getFloorId(str_floorname);
                                                                                    floor_spinner.setId(Integer.parseInt(floor_id));
                                                                                    System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                                }
                                                                            } catch (NumberFormatException e) {
                                                                                e.printStackTrace();
                                                                                System.out.println("Floor Id Exception");
                                                                            }
                                                                            
                                                                            area_names = dbh.getAllAreas(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname,str_floorname);
                                                                            System.out.println("area_names = " + area_names);
                                                                            if (!area_names.isEmpty())
                                                                            {
                                                                                //MULTI SELECT SPINNER
                                                                               area_spinner.setVisibility(View.VISIBLE);
                                                                               area_spinner.setItems(area_names);
                                                                                System.out.println("area_spinner.getSelectedItemsAsString() = " + area_spinner.getSelectedStrings());
    
                                                                                if (area_spinner.getParent() != null) {
                                                                                    ((ViewGroup) area_spinner.getParent()).removeView(area_spinner); //
                                                                                }
                                                                                main_Layout.addView(area_spinner);
                                                                             
                                                                                //FEEDBACK SERVICE
                                                                                ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                                        R.layout.spinner_text, feedback_service_name);
                                                                                spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                                feedback_service.setAdapter(spinnerArrayAdapter5);
                                                                                
                                                                                if (feedback_service.getParent() != null) {
                                                                                    ((ViewGroup) feedback_service.getParent()).removeView(feedback_service); //
                                                                                }
                                                                                main_Layout.addView(feedback_service);
                                                                                
                                                                                feedback_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                    @Override
                                                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                        str_feedbackservice = feedback_service.getSelectedItem().toString();
                                                                                        try {
                                                                                            if (!str_feedbackservice.equals("")) {
                                                                                                feedback_service.setId(i);
                                                                                                System.out.println("floor_spinner.getId() = " + feedback_service.getId());
                                                                                            }
                                                                                        } catch (NumberFormatException e) {
                                                                                            e.printStackTrace();
                                                                                            System.out.println("Feedback Service Id Exception");
                                                                                        }
                                                                                    }
    
                                                                                    @Override
                                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
        
                                                                                    }
                                                                                });
                                                                                
                                                                                    //BUTTONS
                                                                                   if (button(R.id.cancel,"Cancel").getParent() != null) {
                                                                                        ((ViewGroup) button(R.id.cancel,"Cancel").getParent()).removeView(button(R.id.cancel,"Cancel")); //
                                                                                    }
                                                                                    sub1_secondlayout.addView(button(R.id.cancel,"Cancel"));
        
                                                                                    if (button(R.id.submit,"Submit").getParent() != null) {
                                                                                        ((ViewGroup) button(R.id.submit,"Submit").getParent()).removeView(button(R.id.submit,"Submit")); //
                                                                                    }
                                                                                    sub1_secondlayout.addView(button(R.id.submit,"Submit"));
        
        
                                                                                    if (sub1_secondlayout.getParent() != null) {
                                                                                        ((ViewGroup) sub1_secondlayout.getParent()).removeView(sub1_secondlayout); //
                                                                                    }
                                                                                    main_Layout.addView(sub1_secondlayout);
                                                                              
    
                                                                            }
                                                                            else
                                                                            {
                                                                                main_Layout.removeView(area_spinner);
    
                                                                            }
    
    
                                                                        }
                                                                        
                                                                        @Override
                                                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                                                        
                                                                        }
                                                                    });
                                                                }
                                                                
                                                                else {
                                                                    main_Layout.removeView(floor_spinner);
                                                                }
                                                            }
                                                            
                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                                            
                                                            }
                                                        });
                                                    }
                                                    
                                                    else {
                                                        main_Layout.removeView(wing_spinner);
                                                        
                                                        //If no wing data, then add next spinner floor
                                                        str_wingname = "";
                                                        
                                                        //if no wing data available then add next spinner floor data
                                                        floor_names = dbh.getAllFloors(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname);
                                                        if (!floor_names.isEmpty()) {
                                                            //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                            if (floor_names.size() == 1)
                                                            {
                                                                floor_spinner.setEnabled(false);
                                                            }
                                                            else
                                                            {
                                                                floor_spinner.setEnabled(true);
                                                            }
                                                            
                                                            ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                    R.layout.spinner_text, floor_names);
                                                            spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            floor_spinner.setAdapter(spinnerArrayAdapter5);
                                                            
//                                                            if (floor_names.size() == 0) {
//                                                                main_Layout.removeView(floor_spinner);
//                                                            } else {
                                                                if (floor_spinner.getParent() != null) {
                                                                    ((ViewGroup) floor_spinner.getParent()).removeView(floor_spinner); //
                                                                }
                                                                main_Layout.addView(floor_spinner);
                                                           // }
                                                            
                                                            floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                    str_floorname = floor_spinner.getSelectedItem().toString();
                                                                    try {
                                                                        if (!str_floorname.equals("")) {
                                                                            floor_id = dbh.getFloorId(str_floorname);
                                                                            floor_spinner.setId(Integer.parseInt(floor_id));
                                                                            System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                        }
                                                                    } catch (NumberFormatException e) {
                                                                        e.printStackTrace();
                                                                        System.out.println("Floor Id Exception");
                                                                    }
    
                                                                    area_names = dbh.getAllAreas(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname,str_floorname);
                                                                    System.out.println("area_names = " + area_names);
                                                                    if (!area_names.isEmpty())
                                                                    {
                                                                        //MULTI SELECT SPINNER
                                                                        area_spinner.setVisibility(View.VISIBLE);
                                                                        area_spinner.setItems(area_names);
                                                                        System.out.println("area_spinner.getSelectedItemsAsString() = " + area_spinner.getSelectedStrings());
        
                                                                        if (area_spinner.getParent() != null) {
                                                                            ((ViewGroup) area_spinner.getParent()).removeView(area_spinner); //
                                                                        }
                                                                        main_Layout.addView(area_spinner);
        
                                                                        //FEEDBACK SERVICE
                                                                        ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                                R.layout.spinner_text, feedback_service_name);
                                                                        spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                        feedback_service.setAdapter(spinnerArrayAdapter5);
        
                                                                        if (feedback_service.getParent() != null) {
                                                                            ((ViewGroup) feedback_service.getParent()).removeView(feedback_service); //
                                                                        }
                                                                        main_Layout.addView(feedback_service);
        
                                                                        feedback_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                            @Override
                                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                str_feedbackservice = feedback_service.getSelectedItem().toString();
                                                                                try {
                                                                                    if (!str_feedbackservice.equals("")) {
                                                                                        feedback_service.setId(i);
                                                                                        System.out.println("floor_spinner.getId() = " + feedback_service.getId());
                                                                                    }
                                                                                } catch (NumberFormatException e) {
                                                                                    e.printStackTrace();
                                                                                    System.out.println("Feedback Service Id Exception");
                                                                                }
                                                                            }
            
                                                                            @Override
                                                                            public void onNothingSelected(AdapterView<?> adapterView) {
                
                                                                            }
                                                                        });
        
                                                                        //BUTTONS
                                                                                  /*  if (button(R.id.cancel,"Cancel").getParent() != null) {
                                                                                        ((ViewGroup) button(R.id.cancel,"Cancel").getParent()).removeView(button(R.id.cancel,"Cancel")); //
                                                                                    }
                                                                                    sub1_secondlayout.addView(button(R.id.cancel,"Cancel"));*/
        
                                                                        if (button(R.id.submit,"Submit").getParent() != null) {
                                                                            ((ViewGroup) button(R.id.submit,"Submit").getParent()).removeView(button(R.id.submit,"Submit")); //
                                                                        }
                                                                        sub1_secondlayout.addView(button(R.id.submit,"Submit"));
        
        
                                                                        if (sub1_secondlayout.getParent() != null) {
                                                                            ((ViewGroup) sub1_secondlayout.getParent()).removeView(sub1_secondlayout); //
                                                                        }
                                                                        main_Layout.addView(sub1_secondlayout);
        
        
                                                                    }
                                                                    else
                                                                    {
                                                                        main_Layout.removeView(area_spinner);
        
                                                                    }
                                                                    
                                                                }
                                                                
                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> adapterView) {
                                                                
                                                                }
                                                            });
                                                            
                                                        } else {
                                                            main_Layout.removeView(floor_spinner);
                                                            
                                                        }
                                                    }
                                                }
                                                
                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {
                                                
                                                }
                                            });
                                        } else {
                                            main_Layout.removeView(building_spinner);
                                        }
                                        
                                    }
                                    
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    
                                    }
                                });
                            }
                            
                            else {
                                str_sitename = "";
                                
                                main_Layout.removeView(site_spinner);
                                
                                System.out.println("When no sites available display next spinner building..");
                                
                                building_names = dbh.getAllBuildings(str_companyname, str_locationname, str_sitename);
                                
                                if (!building_names.isEmpty()) {
                                    //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                    if (building_names.size() == 1)
                                    {
                                        building_spinner.setEnabled(false);
                                    }
                                    else
                                    {
                                        building_spinner.setEnabled(true);
                                    }
                                    
                                    ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),
                                            R.layout.spinner_text, building_names);
                                    spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    building_spinner.setAdapter(spinnerArrayAdapter3);
                                    
//                                    if (building_names.size() == 0) {
//                                        main_Layout.removeView(building_spinner);
//                                    } else {
                                        if (building_spinner.getParent() != null) {
                                            ((ViewGroup) building_spinner.getParent()).removeView(building_spinner); //
                                        }
                                        main_Layout.addView(building_spinner);
                                    //}
                                    
                                    //wing
                                    building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            str_buildingname = building_spinner.getSelectedItem().toString();
                                            try {
                                                if (!str_buildingname.equals("")) {
                                                    building_id = dbh.getBuildingId(str_buildingname);
                                                    building_spinner.setId(Integer.parseInt(building_id));
                                                    System.out.println("building_spinner.getId() = " + building_spinner.getId());
                                                    
                                                }
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                                System.out.println("Building Id Exception");
                                            }
                                            
                                            wing_names = dbh.getAllWings(str_companyname, str_locationname, str_sitename, str_buildingname);
                                            
                                            if (!wing_names.isEmpty()) {
                                                //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                if (wing_names.size() == 1)
                                                {
                                                    wing_spinner.setEnabled(false);
                                                }
                                                else
                                                {
                                                    wing_spinner.setEnabled(true);
                                                }
                                                
                                                ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),
                                                        R.layout.spinner_text, wing_names);
                                                spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                wing_spinner.setAdapter(spinnerArrayAdapter4);
                                                
//                                                if (wing_names.size() == 0) {
//                                                    main_Layout.removeView(wing_spinner);
//                                                } else {
                                                    if (wing_spinner.getParent() != null) {
                                                        ((ViewGroup) wing_spinner.getParent()).removeView(wing_spinner); //
                                                    }
                                                    main_Layout.addView(wing_spinner);
                                               // }
                                                
                                                wing_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                        str_wingname = wing_spinner.getSelectedItem().toString();
                                                        try {
                                                            if (!str_wingname.equals("")) {
                                                                wing_id = dbh.getWingId(str_wingname);
                                                                wing_spinner.setId(Integer.parseInt(wing_id));
                                                                System.out.println("wing_spinner.getId() = " + wing_spinner.getId());
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            e.printStackTrace();
                                                            System.out.println("Wing Id Exception");
                                                        }
                                                        
                                                        floor_names = dbh.getAllFloors(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname);
                                                        
                                                        if (!floor_names.isEmpty()) {
                                                            //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                            if (floor_names.size() == 1)
                                                            {
                                                                floor_spinner.setEnabled(false);
                                                            }
                                                            else
                                                            {
                                                                floor_spinner.setEnabled(true);
                                                            }
                                                            
                                                            ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                    R.layout.spinner_text, floor_names);
                                                            spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            floor_spinner.setAdapter(spinnerArrayAdapter5);
                                                            
//                                                            if (floor_names.size() == 0) {
//                                                                main_Layout.removeView(floor_spinner);
//                                                            } else {
                                                                if (floor_spinner.getParent() != null) {
                                                                    ((ViewGroup) floor_spinner.getParent()).removeView(floor_spinner); //
                                                                }
                                                                main_Layout.addView(floor_spinner);
                                                           // }
                                                            
                                                            floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                    str_floorname = floor_spinner.getSelectedItem().toString();
                                                                    try {
                                                                        if (!str_floorname.equals("")) {
                                                                            floor_id = dbh.getFloorId(str_floorname);
                                                                            floor_spinner.setId(Integer.parseInt(floor_id));
                                                                            System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                        }
                                                                    } catch (NumberFormatException e) {
                                                                        e.printStackTrace();
                                                                        System.out.println("Floor Id Exception");
                                                                    }
    
                                                                    area_names = dbh.getAllAreas(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname,str_floorname);
                                                                    System.out.println("area_names = " + area_names);
                                                                    if (!area_names.isEmpty())
                                                                    {
                                                                        //MULTI SELECT SPINNER
                                                                        area_spinner.setVisibility(View.VISIBLE);
                                                                        area_spinner.setItems(area_names);
                                                                        System.out.println("area_spinner.getSelectedItemsAsString() = " + area_spinner.getSelectedStrings());
        
                                                                        if (area_spinner.getParent() != null) {
                                                                            ((ViewGroup) area_spinner.getParent()).removeView(area_spinner); //
                                                                        }
                                                                        main_Layout.addView(area_spinner);
        
                                                                        //FEEDBACK SERVICE
                                                                        ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                                R.layout.spinner_text, feedback_service_name);
                                                                        spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                        feedback_service.setAdapter(spinnerArrayAdapter5);
        
                                                                        if (feedback_service.getParent() != null) {
                                                                            ((ViewGroup) feedback_service.getParent()).removeView(feedback_service); //
                                                                        }
                                                                        main_Layout.addView(feedback_service);
        
                                                                        feedback_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                            @Override
                                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                str_feedbackservice = feedback_service.getSelectedItem().toString();
                                                                                try {
                                                                                    if (!str_feedbackservice.equals("")) {
                                                                                        feedback_service.setId(i);
                                                                                        System.out.println("floor_spinner.getId() = " + feedback_service.getId());
                                                                                    }
                                                                                } catch (NumberFormatException e) {
                                                                                    e.printStackTrace();
                                                                                    System.out.println("Feedback Service Id Exception");
                                                                                }
                                                                            }
            
                                                                            @Override
                                                                            public void onNothingSelected(AdapterView<?> adapterView) {
                
                                                                            }
                                                                        });
        
                                                                        //BUTTONS
                                                                                  /*  if (button(R.id.cancel,"Cancel").getParent() != null) {
                                                                                        ((ViewGroup) button(R.id.cancel,"Cancel").getParent()).removeView(button(R.id.cancel,"Cancel")); //
                                                                                    }
                                                                                    sub1_secondlayout.addView(button(R.id.cancel,"Cancel"));*/
        
                                                                        if (button(R.id.submit,"Submit").getParent() != null) {
                                                                            ((ViewGroup) button(R.id.submit,"Submit").getParent()).removeView(button(R.id.submit,"Submit")); //
                                                                        }
                                                                        sub1_secondlayout.addView(button(R.id.submit,"Submit"));
        
        
                                                                        if (sub1_secondlayout.getParent() != null) {
                                                                            ((ViewGroup) sub1_secondlayout.getParent()).removeView(sub1_secondlayout); //
                                                                        }
                                                                        main_Layout.addView(sub1_secondlayout);
        
        
                                                                    }
                                                                    else
                                                                    {
                                                                        main_Layout.removeView(area_spinner);
        
                                                                    }
                                                                }
                                                                
                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> adapterView) {
                                                                
                                                                }
                                                            });
                                                        }
                                                        
                                                        else {
                                                            main_Layout.removeView(floor_spinner);
                                                            
                                                        }
                                                    }
                                                    
                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                    
                                                    }
                                                });
                                            }
                                            
                                            else {
                                                main_Layout.removeView(wing_spinner);
                                                
                                                //If no wing data, then add next spinner floor
                                                str_wingname = "";
                                                
                                                //if no wing data available then add next spinner data floor
                                                floor_names = dbh.getAllFloors(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname);
                                                if (!floor_names.isEmpty()) {
                                                    //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                    if (floor_names.size() == 1)
                                                    {
                                                        floor_spinner.setEnabled(false);
                                                    }
                                                    else
                                                    {
                                                        floor_spinner.setEnabled(true);
                                                    }
                                                    
                                                    ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                            R.layout.spinner_text, floor_names);
                                                    spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    floor_spinner.setAdapter(spinnerArrayAdapter5);
                                                    
//                                                    if (floor_names.size() == 0) {
//                                                        main_Layout.removeView(floor_spinner);
//                                                    } else {
                                                        if (floor_spinner.getParent() != null) {
                                                            ((ViewGroup) floor_spinner.getParent()).removeView(floor_spinner); //
                                                        }
                                                        main_Layout.addView(floor_spinner);
                                                   // }
                                                    
                                                    floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                            str_floorname = floor_spinner.getSelectedItem().toString();
                                                            try {
                                                                if (!str_floorname.equals("")) {
                                                                    floor_id = dbh.getFloorId(str_floorname);
                                                                    floor_spinner.setId(Integer.parseInt(floor_id));
                                                                    System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                }
                                                            } catch (NumberFormatException e) {
                                                                e.printStackTrace();
                                                                System.out.println("Floor Id Exception");
                                                            }
    
                                                            area_names = dbh.getAllAreas(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname,str_floorname);
                                                            System.out.println("area_names = " + area_names);
                                                            if (!area_names.isEmpty())
                                                            {
                                                                //MULTI SELECT SPINNER
                                                                area_spinner.setVisibility(View.VISIBLE);
                                                                area_spinner.setItems(area_names);
                                                                System.out.println("area_spinner.getSelectedItemsAsString() = " + area_spinner.getSelectedStrings());
        
                                                                if (area_spinner.getParent() != null) {
                                                                    ((ViewGroup) area_spinner.getParent()).removeView(area_spinner); //
                                                                }
                                                                main_Layout.addView(area_spinner);
        
                                                                //FEEDBACK SERVICE
                                                                ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                        R.layout.spinner_text, feedback_service_name);
                                                                spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                feedback_service.setAdapter(spinnerArrayAdapter5);
        
                                                                if (feedback_service.getParent() != null) {
                                                                    ((ViewGroup) feedback_service.getParent()).removeView(feedback_service); //
                                                                }
                                                                main_Layout.addView(feedback_service);
        
                                                                feedback_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                    @Override
                                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                        str_feedbackservice = feedback_service.getSelectedItem().toString();
                                                                        try {
                                                                            if (!str_feedbackservice.equals("")) {
                                                                                feedback_service.setId(i);
                                                                                System.out.println("floor_spinner.getId() = " + feedback_service.getId());
                                                                            }
                                                                        } catch (NumberFormatException e) {
                                                                            e.printStackTrace();
                                                                            System.out.println("Feedback Service Id Exception");
                                                                        }
                                                                    }
            
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                
                                                                    }
                                                                });
        
                                                                //BUTTONS
                                                                                  /*  if (button(R.id.cancel,"Cancel").getParent() != null) {
                                                                                        ((ViewGroup) button(R.id.cancel,"Cancel").getParent()).removeView(button(R.id.cancel,"Cancel")); //
                                                                                    }
                                                                                    sub1_secondlayout.addView(button(R.id.cancel,"Cancel"));*/
        
                                                                if (button(R.id.submit,"Submit").getParent() != null) {
                                                                    ((ViewGroup) button(R.id.submit,"Submit").getParent()).removeView(button(R.id.submit,"Submit")); //
                                                                }
                                                                sub1_secondlayout.addView(button(R.id.submit,"Submit"));
        
        
                                                                if (sub1_secondlayout.getParent() != null) {
                                                                    ((ViewGroup) sub1_secondlayout.getParent()).removeView(sub1_secondlayout); //
                                                                }
                                                                main_Layout.addView(sub1_secondlayout);
        
        
                                                            }
                                                            else
                                                            {
                                                                main_Layout.removeView(area_spinner);
        
                                                            }
                                                            
                                                        }
                                                        
                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                                        
                                                        }
                                                    });
                                                    
                                                } else {
                                                    main_Layout.removeView(floor_spinner);
                                                }
                                            }
                                        }
                                        
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        
                                        }
                                    });
                                    
                                } else {
                                    main_Layout.removeView(building_spinner);
                                }
                            }
                        }
                        
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        
                        }
                    });
                    
                } else {
                    if (location_names.size() == 0) {
                        System.out.println("When Location is empty..add next spinner site");
                        str_locationname = "";
                        
                        main_Layout.removeView(location_spinner);
                        
                        site_names = dbh.getAllSites(str_companyname, str_locationname);
                        
                        if (!site_names.isEmpty()) {
                            //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                            if (site_names.size() == 1)
                            {
                                site_spinner.setEnabled(false);
                            }
                            else
                            {
                                site_spinner.setEnabled(true);
                            }
                            
                            ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_text, site_names);
                            spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            site_spinner.setAdapter(spinnerArrayAdapter2);
                            
//                            if (site_names.size() == 0) {
//                                main_Layout.removeView(site_spinner);
//                            } else {
                                if (site_spinner.getParent() != null) {
                                    ((ViewGroup) site_spinner.getParent()).removeView(site_spinner); //
                                }
                                main_Layout.addView(site_spinner);
                            //}
                            
                            site_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    str_sitename = site_spinner.getSelectedItem().toString();
                                    
                                    building_names = dbh.getAllBuildings(str_companyname, str_locationname, str_sitename);
                                    
                                    if (!building_names.isEmpty()) {
                                        //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                        if (building_names.size() == 1)
                                        {
                                            building_spinner.setEnabled(false);
                                        }
                                        else
                                        {
                                            building_spinner.setEnabled(true);
                                        }
                                        
                                        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),
                                                R.layout.spinner_text, building_names);
                                        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        building_spinner.setAdapter(spinnerArrayAdapter3);
                                        
                                        if (!str_sitename.equals("")) {
                                            site_id = dbh.getSiteId(str_sitename);
                                            site_spinner.setId(Integer.parseInt(site_id));
                                            System.out.println("site_spinner.getId() = " + site_spinner.getId());
                                            
                                        }
                                        
//                                        if (building_names.size() == 0) {
//                                            main_Layout.removeView(building_spinner);
//                                        } else {
                                            if (building_spinner.getParent() != null) {
                                                ((ViewGroup) building_spinner.getParent()).removeView(building_spinner); //
                                            }
                                            main_Layout.addView(building_spinner);
                                       // }
                                        
                                        building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                str_buildingname = building_spinner.getSelectedItem().toString();
                                                try {
                                                    if (!str_buildingname.equals("")) {
                                                        building_id = dbh.getBuildingId(str_buildingname);
                                                        building_spinner.setId(Integer.parseInt(building_id));
                                                        System.out.println("building_spinner.getId() = " + building_spinner.getId());
                                                    }
                                                } catch (NumberFormatException e) {
                                                    e.printStackTrace();
                                                    System.out.println(" Building Id Exception ");
                                                }
                                                
                                                wing_names = dbh.getAllWings(str_companyname, str_locationname, str_sitename, str_buildingname);
                                                
                                                if (!wing_names.isEmpty()) {
                                                    //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                    if (wing_names.size() == 1)
                                                    {
                                                        wing_spinner.setEnabled(false);
                                                    }
                                                    else
                                                    {
                                                        wing_spinner.setEnabled(true);
                                                    }
                                                    
                                                    ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),
                                                            R.layout.spinner_text, wing_names);
                                                    spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    wing_spinner.setAdapter(spinnerArrayAdapter4);
                                                    
//                                                    if (wing_names.size() == 0) {
//                                                        main_Layout.removeView(wing_spinner);
//                                                    } else {
                                                        if (wing_spinner.getParent() != null) {
                                                            ((ViewGroup) wing_spinner.getParent()).removeView(wing_spinner); //
                                                        }
                                                        main_Layout.addView(wing_spinner);
                                                   // }
                                                    
                                                    wing_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                            str_wingname = wing_spinner.getSelectedItem().toString();
                                                            try {
                                                                if (!str_wingname.equals("")) {
                                                                    wing_id = dbh.getWingId(str_wingname);
                                                                    wing_spinner.setId(Integer.parseInt(wing_id));
                                                                    System.out.println("wing_spinner.getId() = " + wing_spinner.getId());
                                                                }
                                                            } catch (NumberFormatException e) {
                                                                e.printStackTrace();
                                                                System.out.println(" Wing Id Exception ");
                                                            }
                                                            floor_names = dbh.getAllFloors(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname);
                                                            
                                                            if (!floor_names.isEmpty()) {
                                                                //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                                if (floor_names.size() == 1)
                                                                {
                                                                    floor_spinner.setEnabled(false);
                                                                }
                                                                else
                                                                {
                                                                    floor_spinner.setEnabled(true);
                                                                }
                                                                
                                                                ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                        R.layout.spinner_text, floor_names);
                                                                spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                floor_spinner.setAdapter(spinnerArrayAdapter5);
                                                                
//                                                                if (floor_names.size() == 0) {
//                                                                    main_Layout.removeView(floor_spinner);
//                                                                } else {
                                                                    if (floor_spinner.getParent() != null) {
                                                                        ((ViewGroup) floor_spinner.getParent()).removeView(floor_spinner); //
                                                                    }
                                                                    main_Layout.addView(floor_spinner);
                                                               // }
                                                                
                                                                floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                    @Override
                                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                        str_floorname = floor_spinner.getSelectedItem().toString();
                                                                        try {
                                                                            if (!str_floorname.equals("")) {
                                                                                floor_id = dbh.getFloorId(str_floorname);
                                                                                floor_spinner.setId(Integer.parseInt(floor_id));
                                                                                System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                            }
                                                                        } catch (NumberFormatException e) {
                                                                            e.printStackTrace();
                                                                            System.out.println("Floor Exception");
                                                                        }
    
                                                                        area_names = dbh.getAllAreas(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname,str_floorname);
                                                                        System.out.println("area_names = " + area_names);
                                                                        if (!area_names.isEmpty())
                                                                        {
                                                                            //MULTI SELECT SPINNER
                                                                            area_spinner.setVisibility(View.VISIBLE);
                                                                            area_spinner.setItems(area_names);
                                                                            System.out.println("area_spinner.getSelectedItemsAsString() = " + area_spinner.getSelectedStrings());
        
                                                                            if (area_spinner.getParent() != null) {
                                                                                ((ViewGroup) area_spinner.getParent()).removeView(area_spinner); //
                                                                            }
                                                                            main_Layout.addView(area_spinner);
        
                                                                            //FEEDBACK SERVICE
                                                                            ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                                    R.layout.spinner_text, feedback_service_name);
                                                                            spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                            feedback_service.setAdapter(spinnerArrayAdapter5);
        
                                                                            if (feedback_service.getParent() != null) {
                                                                                ((ViewGroup) feedback_service.getParent()).removeView(feedback_service); //
                                                                            }
                                                                            main_Layout.addView(feedback_service);
        
                                                                            feedback_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                @Override
                                                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                    str_feedbackservice = feedback_service.getSelectedItem().toString();
                                                                                    try {
                                                                                        if (!str_feedbackservice.equals("")) {
                                                                                            feedback_service.setId(i);
                                                                                            System.out.println("floor_spinner.getId() = " + feedback_service.getId());
                                                                                        }
                                                                                    } catch (NumberFormatException e) {
                                                                                        e.printStackTrace();
                                                                                        System.out.println("Feedback Service Id Exception");
                                                                                    }
                                                                                }
            
                                                                                @Override
                                                                                public void onNothingSelected(AdapterView<?> adapterView) {
                
                                                                                }
                                                                            });
        
                                                                            //BUTTONS
                                                                                  /*  if (button(R.id.cancel,"Cancel").getParent() != null) {
                                                                                        ((ViewGroup) button(R.id.cancel,"Cancel").getParent()).removeView(button(R.id.cancel,"Cancel")); //
                                                                                    }
                                                                                    sub1_secondlayout.addView(button(R.id.cancel,"Cancel"));*/
        
                                                                            if (button(R.id.submit,"Submit").getParent() != null) {
                                                                                ((ViewGroup) button(R.id.submit,"Submit").getParent()).removeView(button(R.id.submit,"Submit")); //
                                                                            }
                                                                            sub1_secondlayout.addView(button(R.id.submit,"Submit"));
        
        
                                                                            if (sub1_secondlayout.getParent() != null) {
                                                                                ((ViewGroup) sub1_secondlayout.getParent()).removeView(sub1_secondlayout); //
                                                                            }
                                                                            main_Layout.addView(sub1_secondlayout);
        
        
                                                                        }
                                                                        else
                                                                        {
                                                                            main_Layout.removeView(area_spinner);
        
                                                                        }
                                                                        
                                                                        
                                                                        
                                                                    }
                                                                    
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                                    
                                                                    }
                                                                });
                                                                
                                                            } else {
                                                                main_Layout.removeView(floor_spinner);
                                                            }
                                                        }
                                                        
                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                                        
                                                        }
                                                    });
                                                } else {
                                                    main_Layout.removeView(wing_spinner);
                                                    
                                                    //if no wing data available then add next spinner floor data
                                                    str_wingname = "";
                                                    
                                                    //if no wing data available then add next spinner data floor
                                                    floor_names = dbh.getAllFloors(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname);
                                                    if (!floor_names.isEmpty()) {
    
                                                        //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                        if (floor_names.size() == 1)
                                                        {
                                                            floor_spinner.setEnabled(false);
                                                        }
                                                        else
                                                        {
                                                            floor_spinner.setEnabled(true);
                                                        }
                                                        
                                                        ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                R.layout.spinner_text, floor_names);
                                                        spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        floor_spinner.setAdapter(spinnerArrayAdapter5);
                                                        
//                                                        if (floor_names.size() == 0) {
//                                                            main_Layout.removeView(floor_spinner);
//                                                        } else {
                                                            if (floor_spinner.getParent() != null) {
                                                                ((ViewGroup) floor_spinner.getParent()).removeView(floor_spinner); //
                                                            }
                                                            main_Layout.addView(floor_spinner);
                                                       // }
                                                        
                                                        floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                str_floorname = floor_spinner.getSelectedItem().toString();
                                                                try {
                                                                    if (!str_floorname.equals("")) {
                                                                        floor_id = dbh.getFloorId(str_floorname);
                                                                        floor_spinner.setId(Integer.parseInt(floor_id));
                                                                        System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                    }
                                                                } catch (NumberFormatException e) {
                                                                    e.printStackTrace();
                                                                    System.out.println("Floor Exception");
                                                                }
    
                                                                area_names = dbh.getAllAreas(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname,str_floorname);
                                                                System.out.println("area_names = " + area_names);
                                                                if (!area_names.isEmpty())
                                                                {
                                                                    //MULTI SELECT SPINNER
                                                                    area_spinner.setVisibility(View.VISIBLE);
                                                                    area_spinner.setItems(area_names);
                                                                    System.out.println("area_spinner.getSelectedItemsAsString() = " + area_spinner.getSelectedStrings());
        
                                                                    if (area_spinner.getParent() != null) {
                                                                        ((ViewGroup) area_spinner.getParent()).removeView(area_spinner); //
                                                                    }
                                                                    main_Layout.addView(area_spinner);
        
                                                                    //FEEDBACK SERVICE
                                                                    ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                            R.layout.spinner_text, feedback_service_name);
                                                                    spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                    feedback_service.setAdapter(spinnerArrayAdapter5);
        
                                                                    if (feedback_service.getParent() != null) {
                                                                        ((ViewGroup) feedback_service.getParent()).removeView(feedback_service); //
                                                                    }
                                                                    main_Layout.addView(feedback_service);
        
                                                                    feedback_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                        @Override
                                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                            str_feedbackservice = feedback_service.getSelectedItem().toString();
                                                                            try {
                                                                                if (!str_feedbackservice.equals("")) {
                                                                                    feedback_service.setId(i);
                                                                                    System.out.println("floor_spinner.getId() = " + feedback_service.getId());
                                                                                }
                                                                            } catch (NumberFormatException e) {
                                                                                e.printStackTrace();
                                                                                System.out.println("Feedback Service Id Exception");
                                                                            }
                                                                        }
            
                                                                        @Override
                                                                        public void onNothingSelected(AdapterView<?> adapterView) {
                
                                                                        }
                                                                    });
        
                                                                    //BUTTONS
                                                                                  /*  if (button(R.id.cancel,"Cancel").getParent() != null) {
                                                                                        ((ViewGroup) button(R.id.cancel,"Cancel").getParent()).removeView(button(R.id.cancel,"Cancel")); //
                                                                                    }
                                                                                    sub1_secondlayout.addView(button(R.id.cancel,"Cancel"));*/
        
                                                                    if (button(R.id.submit,"Submit").getParent() != null) {
                                                                        ((ViewGroup) button(R.id.submit,"Submit").getParent()).removeView(button(R.id.submit,"Submit")); //
                                                                    }
                                                                    sub1_secondlayout.addView(button(R.id.submit,"Submit"));
        
        
                                                                    if (sub1_secondlayout.getParent() != null) {
                                                                        ((ViewGroup) sub1_secondlayout.getParent()).removeView(sub1_secondlayout); //
                                                                    }
                                                                    main_Layout.addView(sub1_secondlayout);
        
        
                                                                }
                                                                else
                                                                {
                                                                    main_Layout.removeView(area_spinner);
        
                                                                }
                                                                
                                                                
                                                            }
                                                            
                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                                            
                                                            }
                                                        });
                                                        
                                                    } else {
                                                        main_Layout.removeView(floor_spinner);
                                                        
                                                    }
                                                    
                                                }
                                                
                                            }
                                            
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                            
                                            }
                                        });
                                    } else {
                                        main_Layout.removeView(building_spinner);
                                        main_Layout.removeView(wing_spinner);
                                        main_Layout.removeView(floor_spinner);
                                    }
                                }
                                
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                
                                }
                            });
                        } else {
                            main_Layout.removeView(site_spinner);
                            main_Layout.removeView(building_spinner);
                            main_Layout.removeView(wing_spinner);
                            main_Layout.removeView(floor_spinner);
                            
                            //Add building data when location, site both are blank
                            System.out.println("Add building data when location, site both are blank");
                            str_locationname = "";
                            str_sitename = "";
                            
                            building_names = dbh.getAllBuildings(str_companyname, str_locationname, str_sitename);
                            
                            if (!building_names.isEmpty()) {
                                //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                if (building_names.size() == 1)
                                {
                                    building_spinner.setEnabled(false);
                                }
                                else
                                {
                                    building_spinner.setEnabled(true);
                                }
                                
                                ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),
                                        R.layout.spinner_text, building_names);
                                spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                building_spinner.setAdapter(spinnerArrayAdapter3);
                                
//                                if (building_names.size() == 0) {
//                                    main_Layout.removeView(building_spinner);
//                                } else {
                                    if (building_spinner.getParent() != null) {
                                        ((ViewGroup) building_spinner.getParent()).removeView(building_spinner); //
                                    }
                                    
                                    main_Layout.addView(building_spinner);
                               // }
                                
                                building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        str_buildingname = building_spinner.getSelectedItem().toString();
                                        try {
                                            if (!str_buildingname.equals("")) {
                                                building_id = dbh.getBuildingId(str_buildingname);
                                                building_spinner.setId(Integer.parseInt(building_id));
                                                System.out.println("building_spinner.getId() = " + building_spinner.getId());
                                            }
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                            System.out.println(" Building Id Exception ");
                                        }
                                        
                                        wing_names = dbh.getAllWings(str_companyname, str_locationname, str_sitename, str_buildingname);
                                        
                                        if (!wing_names.isEmpty()) {
                                            //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                            if (wing_names.size() == 1)
                                            {
                                                wing_spinner.setEnabled(false);
                                            }
                                            else
                                            {
                                                wing_spinner.setEnabled(true);
                                            }
                                            
                                            ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),
                                                    R.layout.spinner_text, wing_names);
                                            spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            wing_spinner.setAdapter(spinnerArrayAdapter4);
                                            
//                                            if (wing_names.size() == 0) {
//                                                main_Layout.removeView(wing_spinner);
//                                            } else {
                                                if (wing_spinner.getParent() != null) {
                                                    ((ViewGroup) wing_spinner.getParent()).removeView(wing_spinner); //
                                                }
                                                main_Layout.addView(wing_spinner);
                                           // }
                                            
                                            //
                                            wing_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                    str_wingname = wing_spinner.getSelectedItem().toString();
                                                    try {
                                                        if (!str_wingname.equals("")) {
                                                            wing_id = dbh.getWingId(str_wingname);
                                                            wing_spinner.setId(Integer.parseInt(wing_id));
                                                            System.out.println("wing_spinner.getId() = " + wing_spinner.getId());
                                                        }
                                                        
                                                    } catch (NumberFormatException e) {
                                                        e.printStackTrace();
                                                        System.out.println("Wing Id Exception");
                                                    }
                                                    
                                                    floor_names = dbh.getAllFloors(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname);
                                                    
                                                    if (!floor_names.isEmpty()) {
                                                        //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                        if (floor_names.size() == 1)
                                                        {
                                                            floor_spinner.setEnabled(false);
                                                        }
                                                        else
                                                        {
                                                            floor_spinner.setEnabled(true);
                                                        }
                                                        
                                                        ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                R.layout.spinner_text, floor_names);
                                                        spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        floor_spinner.setAdapter(spinnerArrayAdapter5);
                                                        
                                                        
//                                                        if (floor_names.size() == 0) {
//                                                            main_Layout.removeView(floor_spinner);
//                                                        } else {
                                                            if (floor_spinner.getParent() != null) {
                                                                ((ViewGroup) floor_spinner.getParent()).removeView(floor_spinner); //
                                                            }
                                                            main_Layout.addView(floor_spinner);
                                                       // }
                                                        
                                                        ///
                                                        
                                                        floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                str_floorname = floor_spinner.getSelectedItem().toString();
                                                                try {
                                                                    if (!str_floorname.equals("")) {
                                                                        floor_id = dbh.getFloorId(str_floorname);
                                                                        floor_spinner.setId(Integer.parseInt(floor_id));
                                                                        System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                    }
                                                                } catch (NumberFormatException e) {
                                                                    e.printStackTrace();
                                                                    System.out.println("Floor Id Exception");
                                                                }
    
                                                                area_names = dbh.getAllAreas(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname,str_floorname);
                                                                System.out.println("area_names = " + area_names);
                                                                if (!area_names.isEmpty())
                                                                {
                                                                    //MULTI SELECT SPINNER
                                                                    area_spinner.setVisibility(View.VISIBLE);
                                                                    area_spinner.setItems(area_names);
                                                                    System.out.println("area_spinner.getSelectedItemsAsString() = " + area_spinner.getSelectedStrings());
        
                                                                    if (area_spinner.getParent() != null) {
                                                                        ((ViewGroup) area_spinner.getParent()).removeView(area_spinner); //
                                                                    }
                                                                    main_Layout.addView(area_spinner);
        
                                                                    //FEEDBACK SERVICE
                                                                    ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                            R.layout.spinner_text, feedback_service_name);
                                                                    spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                    feedback_service.setAdapter(spinnerArrayAdapter5);
        
                                                                    if (feedback_service.getParent() != null) {
                                                                        ((ViewGroup) feedback_service.getParent()).removeView(feedback_service); //
                                                                    }
                                                                    main_Layout.addView(feedback_service);
        
                                                                    feedback_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                        @Override
                                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                            str_feedbackservice = feedback_service.getSelectedItem().toString();
                                                                            try {
                                                                                if (!str_feedbackservice.equals("")) {
                                                                                    feedback_service.setId(i);
                                                                                    System.out.println("floor_spinner.getId() = " + feedback_service.getId());
                                                                                }
                                                                            } catch (NumberFormatException e) {
                                                                                e.printStackTrace();
                                                                                System.out.println("Feedback Service Id Exception");
                                                                            }
                                                                        }
            
                                                                        @Override
                                                                        public void onNothingSelected(AdapterView<?> adapterView) {
                
                                                                        }
                                                                    });
        
                                                                    //BUTTONS
                                                                                  /*  if (button(R.id.cancel,"Cancel").getParent() != null) {
                                                                                        ((ViewGroup) button(R.id.cancel,"Cancel").getParent()).removeView(button(R.id.cancel,"Cancel")); //
                                                                                    }
                                                                                    sub1_secondlayout.addView(button(R.id.cancel,"Cancel"));*/
        
                                                                    if (button(R.id.submit,"Submit").getParent() != null) {
                                                                        ((ViewGroup) button(R.id.submit,"Submit").getParent()).removeView(button(R.id.submit,"Submit")); //
                                                                    }
                                                                    sub1_secondlayout.addView(button(R.id.submit,"Submit"));
        
        
                                                                    if (sub1_secondlayout.getParent() != null) {
                                                                        ((ViewGroup) sub1_secondlayout.getParent()).removeView(sub1_secondlayout); //
                                                                    }
                                                                    main_Layout.addView(sub1_secondlayout);
        
        
                                                                }
                                                                else
                                                                {
                                                                    main_Layout.removeView(area_spinner);
        
                                                                }
                                                                
                                                                
                                                            }
                                                            
                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                                            
                                                            }
                                                        });
                                                        
                                                    } else {
                                                        main_Layout.removeView(floor_spinner);
                                                        
                                                    }
                                                }
                                                
                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {
                                                
                                                }
                                            });
                                        }
                                        else {
                                            main_Layout.removeView(wing_spinner);
                                            
                                            str_wingname = "";
                                            
                                            //if no wing data available then add next spinner data floor
                                            floor_names = dbh.getAllFloors(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname);
                                            if (!floor_names.isEmpty()) {
    
                                                //IF ONLY ONE ITEM IS PRESENT, SET SPINNER AS NON-EDITABLE
                                                if (floor_names.size() == 1)
                                                {
                                                    floor_spinner.setEnabled(false);
                                                }
                                                else
                                                {
                                                    floor_spinner.setEnabled(true);
                                                }
                                                
                                                ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                        R.layout.spinner_text, floor_names);
                                                spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                floor_spinner.setAdapter(spinnerArrayAdapter5);
                                                
//                                                if (floor_names.size() == 0) {
//                                                    main_Layout.removeView(floor_spinner);
//                                                } else {
                                                    if (floor_spinner.getParent() != null) {
                                                        ((ViewGroup) floor_spinner.getParent()).removeView(floor_spinner); //
                                                    }
                                                    main_Layout.addView(floor_spinner);
                                              //  }
                                                
                                                
                                                floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                        str_floorname = floor_spinner.getSelectedItem().toString();
                                                        try {
                                                            if (!str_floorname.equals("")) {
                                                                floor_id = dbh.getFloorId(str_floorname);
                                                                floor_spinner.setId(Integer.parseInt(floor_id));
                                                                System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            e.printStackTrace();
                                                            System.out.println("Floor Id Exception");
                                                        }
    
                                                        area_names = dbh.getAllAreas(str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname,str_floorname);
                                                        System.out.println("area_names = " + area_names);
                                                        if (!area_names.isEmpty())
                                                        {
                                                            //MULTI SELECT SPINNER
                                                            area_spinner.setVisibility(View.VISIBLE);
                                                            area_spinner.setItems(area_names);
                                                            System.out.println("area_spinner.getSelectedItemsAsString() = " + area_spinner.getSelectedStrings());
        
                                                            if (area_spinner.getParent() != null) {
                                                                ((ViewGroup) area_spinner.getParent()).removeView(area_spinner); //
                                                            }
                                                            main_Layout.addView(area_spinner);
        
                                                            //FEEDBACK SERVICE
                                                            ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
                                                                    R.layout.spinner_text, feedback_service_name);
                                                            spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            feedback_service.setAdapter(spinnerArrayAdapter5);
        
                                                            if (feedback_service.getParent() != null) {
                                                                ((ViewGroup) feedback_service.getParent()).removeView(feedback_service); //
                                                            }
                                                            main_Layout.addView(feedback_service);
        
                                                            feedback_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                    str_feedbackservice = feedback_service.getSelectedItem().toString();
                                                                    try {
                                                                        if (!str_feedbackservice.equals("")) {
                                                                            feedback_service.setId(i);
                                                                            System.out.println("floor_spinner.getId() = " + feedback_service.getId());
                                                                        }
                                                                    } catch (NumberFormatException e) {
                                                                        e.printStackTrace();
                                                                        System.out.println("Feedback Service Id Exception");
                                                                    }
                                                                }
            
                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> adapterView) {
                
                                                                }
                                                            });
        
                                                            //BUTTONS
                                                                                  /*  if (button(R.id.cancel,"Cancel").getParent() != null) {
                                                                                        ((ViewGroup) button(R.id.cancel,"Cancel").getParent()).removeView(button(R.id.cancel,"Cancel")); //
                                                                                    }
                                                                                    sub1_secondlayout.addView(button(R.id.cancel,"Cancel"));*/
        
                                                            if (button(R.id.submit,"Submit").getParent() != null) {
                                                                ((ViewGroup) button(R.id.submit,"Submit").getParent()).removeView(button(R.id.submit,"Submit")); //
                                                            }
                                                            sub1_secondlayout.addView(button(R.id.submit,"Submit"));
        
        
                                                            if (sub1_secondlayout.getParent() != null) {
                                                                ((ViewGroup) sub1_secondlayout.getParent()).removeView(sub1_secondlayout); //
                                                            }
                                                            main_Layout.addView(sub1_secondlayout);
        
        
                                                        }
                                                        else
                                                        {
                                                            main_Layout.removeView(area_spinner);
        
                                                        }
                                                        
                                                        
                                                    }
                                                    
                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                    
                                                    }
                                                });
                                                
                                                
                                            } else {
                                                main_Layout.removeView(floor_spinner);
                                                
                                            }
                                            
                                        }
                                        
                                    }
                                    
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    
                                    }
                                });
                            } else {
                                main_Layout.removeView(building_spinner);
                            }
                        }
                    } else {
                        System.out.println("For Select Company Name Spinner ");
                        main_Layout.removeView(location_spinner);
                        main_Layout.removeView(site_spinner);
                        main_Layout.removeView(building_spinner);
                        main_Layout.removeView(wing_spinner);
                        main_Layout.removeView(floor_spinner);
                        main_Layout.removeView(area_spinner);
                        main_Layout.removeView(feedback_service);
                        main_Layout.removeView(sub1_secondlayout);
                    }
                    
                    
                }
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println(" Nothing selected...... ");
            }
        });
        
        main_Layout.addView(company_spinner);
        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("e = " + e);
        
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

private TextView button(int id, String uname) {
    
//    final Button button = new Button(this);
//    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT,1f);
//    params1.setMargins(16,8,16,8);
//    button.setLayoutParams(params1);
    
    button.setId(id);
    button.setTextSize(1, 17);
    button.setTextColor(Color.WHITE);
    button.setLetterSpacing(0.2f);
    button.setBackground(getDrawable(R.drawable.red_style));
    button.setTypeface(null, Typeface.BOLD);
    button.setText(uname);
    
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (button.getId() == R.id.submit){
                Toast.makeText(AdminDetailsConfig.this, "Submitted"+area_spinner.getSelectedItemsAsString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminDetailsConfig.this,SelectArea.class));
            }
            else if(button.getId() == R.id.cancel)
            {
                Toast.makeText(AdminDetailsConfig.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    });
    
    return button;
    
}



}
