package com.example.feedbackapplication.adminlogin;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.UUID;

import androidx.work.WorkManager;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.HORIZONTAL;

public class AdminDetailsConfig extends BaseActivity {


int questionscount;
String str_companyname, str_locationname, str_sitename, str_buildingname, str_wingname, str_floorname,str_virtualareaname,str_feedbackservice,client_id;
String company_id, location_id, site_id, building_id, wing_id, floor_id, area_id;
private ArrayList<String> company_names = new ArrayList<String>();
private ArrayList<String> location_names = new ArrayList<String>();
private ArrayList<String> site_names = new ArrayList<String>();
private ArrayList<String> building_names = new ArrayList<String>();
private ArrayList<String> wing_names = new ArrayList<String>();
private ArrayList<String> floor_names = new ArrayList<String>();
private ArrayList<String> virtualarea_names = new ArrayList<String>();
private List<String> area_names = new ArrayList<String>();
private ArrayList<String> feedback_service_name = new ArrayList<String>();

MultipleSelectionSpinner area_spinner;
Button button;
String uuid="";

private WorkManager mWorkManager;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admindetailsconfig);
    
    //mWorkManager = WorkManager.getInstance();
   // startWorkManager();
    str_feedbackservice = getIntent().getStringExtra("feedbackservicename");
    feedback_service_name.add(str_feedbackservice);
    client_id = getIntent().getStringExtra("client_id");
    
    System.out.println("AdminDetailsConfig feedbackservicename = " + str_feedbackservice);
   // questionscount = dbh.admindetails_count();
    
    //VIRTUAL AREA SPINNER
    virtualarea_names.add("Select Virtual Area");
    virtualarea_names.add("Washroom");
    virtualarea_names.add("Cafeteria");
    
    
    //FEEDBACK SERVICE SPINNER
//    feedback_service_name.add("Select Feedback Service Type");
//    feedback_service_name.add("Area Specific");
//    feedback_service_name.add("Common Feedback");
//    feedback_service_name.add("Common Feedback with Area Specific");
 
    
    try {
        final LinearLayout main_Layout = (LinearLayout) findViewById(R.id.main_admindetails_layout); //vertical
        main_Layout.addView(textView());
        
        
        //////////COMPANY NAME/////////
        company_names = dbh.getAllCompanyNames(client_id);
        
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
    
        ////////////VIRTUAL AREA NAME//////////
        final Spinner virtualarea_spinner = new Spinner(getApplicationContext());
        virtualarea_spinner.setBackground(getDrawable(R.drawable.edit_style));
        virtualarea_spinner.setLayoutParams(params1);
        
        ////////////AREA NAME//////////
        area_spinner = findViewById(R.id.mSpinner);
        area_spinner.setBackground(getDrawable(R.drawable.edit_style));
        area_spinner.setLayoutParams(params1);
        
       
        
        //BUTTONS LAYOUT
        final LinearLayout sub1_secondlayout = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams params_sub1 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        params_sub1.setMargins(16,15,16,8);
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
//                        company_spinner.setId(Integer.parseInt(company_id));
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
//                                    location_spinner.setId(Integer.parseInt(location_id));
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
//                                                site_spinner.setId(Integer.parseInt(site_id));
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
//                                                            building_spinner.setId(Integer.parseInt(building_id));
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
//                                                                        wing_spinner.setId(Integer.parseInt(wing_id));
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
//                                                                                    floor_spinner.setId(Integer.parseInt(floor_id));
                                                                                    System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                                }
                                                                            } catch (NumberFormatException e) {
                                                                                e.printStackTrace();
                                                                                System.out.println("Floor Id Exception");
                                                                            }
    
                                                                            if (!virtualarea_names.isEmpty())
                                                                            
                                                                            {
                                                                                ArrayAdapter<String> spinnerArrayAdapter6 = new ArrayAdapter<String>(getApplicationContext(),
                                                                                        R.layout.spinner_text, virtualarea_names);
                                                                                spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                                virtualarea_spinner.setAdapter(spinnerArrayAdapter6);

                                                                                if (virtualarea_spinner.getParent() != null) {
                                                                                    ((ViewGroup) virtualarea_spinner.getParent()).removeView(virtualarea_spinner); //
                                                                                }
                                                                                main_Layout.addView(virtualarea_spinner);
                                                                                
                                                                                virtualarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                    @Override
                                                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                        str_virtualareaname = virtualarea_spinner.getSelectedItem().toString();
                                                                                        try {
                                                                                            if (!str_virtualareaname.equals("")) {
                                                                                                virtualarea_spinner.setId(i);
                                                                                                System.out.println("floor_spinner.getId() = " + virtualarea_spinner.getId());
                                                                                            }
                                                                                        } catch (NumberFormatException e) {
                                                                                            e.printStackTrace();
                                                                                            System.out.println("Virtual Area Id Exception");
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
        
                                                                                           /* //FEEDBACK SERVICE
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
                                                                                            });*/
        

        
        
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            main_Layout.removeView(area_spinner);
        
                                                                                        }
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
    
                                                                                    @Override
                                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
        
                                                                                    }
                                                                                });
                                                                                
                                                                                
                                                                                
                                                                                
                                                                            }
                                                                            else {
                                                                                main_Layout.removeView(virtualarea_spinner);
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
//                                                                            floor_spinner.setId(Integer.parseInt(floor_id));
                                                                            System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                        }
                                                                    } catch (NumberFormatException e) {
                                                                        e.printStackTrace();
                                                                        System.out.println("Floor Id Exception");
                                                                    }
                                                                    
                                                                    
                                                                    if (!virtualarea_names.isEmpty())
    
                                                                    {
                                                                        ArrayAdapter<String> spinnerArrayAdapter6 = new ArrayAdapter<String>(getApplicationContext(),
                                                                                R.layout.spinner_text, virtualarea_names);
                                                                        spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                        virtualarea_spinner.setAdapter(spinnerArrayAdapter6);
        
                                                                        if (virtualarea_spinner.getParent() != null) {
                                                                            ((ViewGroup) virtualarea_spinner.getParent()).removeView(virtualarea_spinner); //
                                                                        }
                                                                        main_Layout.addView(virtualarea_spinner);
        
                                                                        virtualarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                            @Override
                                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                str_virtualareaname = virtualarea_spinner.getSelectedItem().toString();
                                                                                try {
                                                                                    if (!str_virtualareaname.equals("")) {
                                                                                        virtualarea_spinner.setId(i);
                                                                                        System.out.println("floor_spinner.getId() = " + virtualarea_spinner.getId());
                                                                                    }
                                                                                } catch (NumberFormatException e) {
                                                                                    e.printStackTrace();
                                                                                    System.out.println("Virtual Area Id Exception");
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
                    
                                                                                    /*//FEEDBACK SERVICE
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
                                                                                    });*/
                    
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
                                                                        main_Layout.removeView(virtualarea_spinner);
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
//                                                    building_spinner.setId(Integer.parseInt(building_id));
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
//                                                                wing_spinner.setId(Integer.parseInt(wing_id));
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
//                                                                            floor_spinner.setId(Integer.parseInt(floor_id));
                                                                            System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                        }
                                                                    } catch (NumberFormatException e) {
                                                                        e.printStackTrace();
                                                                        System.out.println("Floor Id Exception");
                                                                    }
    
                                                                    if (!virtualarea_names.isEmpty())
    
                                                                    {
                                                                        ArrayAdapter<String> spinnerArrayAdapter6 = new ArrayAdapter<String>(getApplicationContext(),
                                                                                R.layout.spinner_text, virtualarea_names);
                                                                        spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                        virtualarea_spinner.setAdapter(spinnerArrayAdapter6);
        
                                                                        if (virtualarea_spinner.getParent() != null) {
                                                                            ((ViewGroup) virtualarea_spinner.getParent()).removeView(virtualarea_spinner); //
                                                                        }
                                                                        main_Layout.addView(virtualarea_spinner);
        
                                                                        virtualarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                            @Override
                                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                str_virtualareaname = virtualarea_spinner.getSelectedItem().toString();
                                                                                try {
                                                                                    if (!str_virtualareaname.equals("")) {
                                                                                        virtualarea_spinner.setId(i);
                                                                                        System.out.println("floor_spinner.getId() = " + virtualarea_spinner.getId());
                                                                                    }
                                                                                } catch (NumberFormatException e) {
                                                                                    e.printStackTrace();
                                                                                    System.out.println("Virtual Area Id Exception");
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
                                                                                   /* ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
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
                                                                                    });*/
                    
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
                                                                        main_Layout.removeView(virtualarea_spinner);
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
//                                                                    floor_spinner.setId(Integer.parseInt(floor_id));
                                                                    System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                }
                                                            } catch (NumberFormatException e) {
                                                                e.printStackTrace();
                                                                System.out.println("Floor Id Exception");
                                                            }
    
                                                            if (!virtualarea_names.isEmpty())
                                                            {
                                                                ArrayAdapter<String> spinnerArrayAdapter6 = new ArrayAdapter<String>(getApplicationContext(),
                                                                        R.layout.spinner_text, virtualarea_names);
                                                                spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                virtualarea_spinner.setAdapter(spinnerArrayAdapter6);
        
                                                                if (virtualarea_spinner.getParent() != null) {
                                                                    ((ViewGroup) virtualarea_spinner.getParent()).removeView(virtualarea_spinner); //
                                                                }
                                                                main_Layout.addView(virtualarea_spinner);
        
                                                                virtualarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                    @Override
                                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                        str_virtualareaname = virtualarea_spinner.getSelectedItem().toString();
                                                                        try {
                                                                            if (!str_virtualareaname.equals("")) {
                                                                                virtualarea_spinner.setId(i);
                                                                                System.out.println("floor_spinner.getId() = " + virtualarea_spinner.getId());
                                                                            }
                                                                        } catch (NumberFormatException e) {
                                                                            e.printStackTrace();
                                                                            System.out.println("Virtual Area Id Exception");
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
                    
                                                                            /*//FEEDBACK SERVICE
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
                                                                            });*/
                    
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
                                                                main_Layout.removeView(virtualarea_spinner);
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
//                                            site_spinner.setId(Integer.parseInt(site_id));
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
//                                                        building_spinner.setId(Integer.parseInt(building_id));
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
//                                                                    wing_spinner.setId(Integer.parseInt(wing_id));
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
//                                                                                floor_spinner.setId(Integer.parseInt(floor_id));
                                                                                System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                            }
                                                                        } catch (NumberFormatException e) {
                                                                            e.printStackTrace();
                                                                            System.out.println("Floor Exception");
                                                                        }
                                                                        
                                                                        if (!virtualarea_names.isEmpty())
                                                                        {
                                                                            ArrayAdapter<String> spinnerArrayAdapter6 = new ArrayAdapter<String>(getApplicationContext(),
                                                                                    R.layout.spinner_text, virtualarea_names);
                                                                            spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                            virtualarea_spinner.setAdapter(spinnerArrayAdapter6);
        
                                                                            if (virtualarea_spinner.getParent() != null) {
                                                                                ((ViewGroup) virtualarea_spinner.getParent()).removeView(virtualarea_spinner); //
                                                                            }
                                                                            main_Layout.addView(virtualarea_spinner);
        
                                                                            virtualarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                @Override
                                                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                    str_virtualareaname = virtualarea_spinner.getSelectedItem().toString();
                                                                                    try {
                                                                                        if (!str_virtualareaname.equals("")) {
                                                                                            virtualarea_spinner.setId(i);
                                                                                            System.out.println("floor_spinner.getId() = " + virtualarea_spinner.getId());
                                                                                        }
                                                                                    } catch (NumberFormatException e) {
                                                                                        e.printStackTrace();
                                                                                        System.out.println("Virtual Area Id Exception");
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
                                                                                        /*ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
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
                                                                                        });*/
                    
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
                                                                            main_Layout.removeView(virtualarea_spinner);
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
//                                                                        floor_spinner.setId(Integer.parseInt(floor_id));
                                                                        System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                    }
                                                                } catch (NumberFormatException e) {
                                                                    e.printStackTrace();
                                                                    System.out.println("Floor Exception");
                                                                }
    
                                                                if (!virtualarea_names.isEmpty())
    
                                                                {
                                                                    ArrayAdapter<String> spinnerArrayAdapter6 = new ArrayAdapter<String>(getApplicationContext(),
                                                                            R.layout.spinner_text, virtualarea_names);
                                                                    spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                    virtualarea_spinner.setAdapter(spinnerArrayAdapter6);
        
                                                                    if (virtualarea_spinner.getParent() != null) {
                                                                        ((ViewGroup) virtualarea_spinner.getParent()).removeView(virtualarea_spinner); //
                                                                    }
                                                                    main_Layout.addView(virtualarea_spinner);
        
                                                                    virtualarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                        @Override
                                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                            str_virtualareaname = virtualarea_spinner.getSelectedItem().toString();
                                                                            try {
                                                                                if (!str_virtualareaname.equals("")) {
                                                                                    virtualarea_spinner.setId(i);
                                                                                    System.out.println("floor_spinner.getId() = " + virtualarea_spinner.getId());
                                                                                }
                                                                            } catch (NumberFormatException e) {
                                                                                e.printStackTrace();
                                                                                System.out.println("Virtual Area Id Exception");
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
                                                                               /* ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
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
                                                                                });*/
                    
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
                                                                    main_Layout.removeView(virtualarea_spinner);
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
//                                                building_spinner.setId(Integer.parseInt(building_id));
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
//                                                            wing_spinner.setId(Integer.parseInt(wing_id));
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
//                                                                        floor_spinner.setId(Integer.parseInt(floor_id));
                                                                        System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                                    }
                                                                } catch (NumberFormatException e) {
                                                                    e.printStackTrace();
                                                                    System.out.println("Floor Id Exception");
                                                                }
    
                                                                if (!virtualarea_names.isEmpty())
    
                                                                {
                                                                    ArrayAdapter<String> spinnerArrayAdapter6 = new ArrayAdapter<String>(getApplicationContext(),
                                                                            R.layout.spinner_text, virtualarea_names);
                                                                    spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                    virtualarea_spinner.setAdapter(spinnerArrayAdapter6);
        
                                                                    if (virtualarea_spinner.getParent() != null) {
                                                                        ((ViewGroup) virtualarea_spinner.getParent()).removeView(virtualarea_spinner); //
                                                                    }
                                                                    main_Layout.addView(virtualarea_spinner);
        
                                                                    virtualarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                        @Override
                                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                            str_virtualareaname = virtualarea_spinner.getSelectedItem().toString();
                                                                            try {
                                                                                if (!str_virtualareaname.equals("")) {
                                                                                    virtualarea_spinner.setId(i);
                                                                                    System.out.println("floor_spinner.getId() = " + virtualarea_spinner.getId());
                                                                                }
                                                                            } catch (NumberFormatException e) {
                                                                                e.printStackTrace();
                                                                                System.out.println("Virtual Area Id Exception");
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
                                                                                /*ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
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
                                                                                });*/
                    
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
                                                                    main_Layout.removeView(virtualarea_spinner);
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
//                                                                floor_spinner.setId(Integer.parseInt(floor_id));
                                                                System.out.println("floor_spinner.getId() = " + floor_spinner.getId());
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            e.printStackTrace();
                                                            System.out.println("Floor Id Exception");
                                                        }
    
                                                        if (!virtualarea_names.isEmpty())
    
                                                        {
                                                            ArrayAdapter<String> spinnerArrayAdapter6 = new ArrayAdapter<String>(getApplicationContext(),
                                                                    R.layout.spinner_text, virtualarea_names);
                                                            spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            virtualarea_spinner.setAdapter(spinnerArrayAdapter6);
        
                                                            if (virtualarea_spinner.getParent() != null) {
                                                                ((ViewGroup) virtualarea_spinner.getParent()).removeView(virtualarea_spinner); //
                                                            }
                                                            main_Layout.addView(virtualarea_spinner);
        
                                                            virtualarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                    str_virtualareaname = virtualarea_spinner.getSelectedItem().toString();
                                                                    try {
                                                                        if (!str_virtualareaname.equals("")) {
                                                                            virtualarea_spinner.setId(i);
                                                                            System.out.println("floor_spinner.getId() = " + virtualarea_spinner.getId());
                                                                        }
                                                                    } catch (NumberFormatException e) {
                                                                        e.printStackTrace();
                                                                        System.out.println("Virtual Area Id Exception");
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
                                                                      /*  ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),
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
                                                                        });*/
                    
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
                                                            main_Layout.removeView(virtualarea_spinner);
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
                        main_Layout.removeView(virtualarea_spinner);
                        main_Layout.removeView(area_spinner);
                       // main_Layout.removeView(feedback_service);
                        main_Layout.removeView(sub1_secondlayout);
                    }
                    
                    
                }
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println(" Nothing selected...... ");
            }
        });


        ////////////FFEDBACK SERVICE NAME//////////
      /*  Spinner feedback_service = new Spinner(getApplicationContext());
        feedback_service.setBackground(getDrawable(R.drawable.edit_style));
        feedback_service.setLayoutParams(params1);
        
        final ArrayAdapter<String> spinnerArrayAdapter11 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, feedback_service_name);
        feedback_service.setAdapter(spinnerArrayAdapter11);
        feedback_service.setEnabled(false);*/
      
        main_Layout.addView(textView(str_feedbackservice));
        main_Layout.addView(company_spinner);
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
        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("e = " + e);
        
    }
  /*  Data source = new Data.Builder()
                          .putString("workType", "OneTime")
                          .build();
    
    OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyDownloadWorker.class)
                                                    .setConstraints(constraints())
                                                    .setInputData(source)
                                                    .build();
    WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    
    
    WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
            .observe(this, new Observer<WorkInfo>() {
                @Override
                public void onChanged(@Nullable WorkInfo workInfo) {
                    System.out.println("workInfo = " + workInfo.getOutputData());
                }
            });*/



}

/*private void startWorkManager() {
    
    //Passing Arguments to Worker
    Data source = new Data.Builder()
                          .putString("workType", "OneTime")
                          .build();
    
    OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyDownloadWorker.class)
                                                    .setConstraints(constraints())
                                                    .setInputData(source)
                                                    .build();
    WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    
    
}

private Constraints constraints() {
    Constraints constraints = new Constraints.Builder()
                                      .setRequiredNetworkType(NetworkType.CONNECTED)
                                      .setRequiresBatteryNotLow(true)
                                      .build();
    return constraints;
}*/

private String greetingMessage() {
    
    Calendar c = Calendar.getInstance();
    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
    String daytime = "";
    
    if (timeOfDay >= 0 && timeOfDay < 12) {
        daytime = "Good Morning";
    } else if (timeOfDay >= 12 && timeOfDay < 16) {
        daytime = "Good Afternoon";
    } else if (timeOfDay >= 16 && timeOfDay < 24) {
        daytime = "Good Evening";
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

private TextView textView(String service) {
    final TextView textView = new TextView(this);
    final LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, 100);
    params1.setMargins(16, 8, 16, 10);
    textView.setLayoutParams(params1);
    textView.setCompoundDrawablePadding(16);
    textView.setGravity(Gravity.CENTER);
    textView.setPadding(16, 16, 16, 16);
    textView.setTextColor(Color.BLACK);
    textView.setHintTextColor(Color.BLACK);
    textView.setBackground(getDrawable(R.drawable.edit_style));
    textView.setTextSize(1, 20);
    textView.setText(service);
    
    return textView;
    
}
private TextView button(int id, String uname) {
    
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
            if (button.getId() == R.id.submit)
            {
                if (str_virtualareaname.equalsIgnoreCase("Select Virtual Name"))
                {
                    Toast.makeText(AdminDetailsConfig.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    uuid = UUID.randomUUID().toString();
                    String areaName=area_spinner.getSelectedItemsAsString();
                    if(!areaName.equalsIgnoreCase(str_virtualareaname)){
                        areaName=areaName+"|"+str_virtualareaname;
                    }
                    boolean isInserted = dbh.insertStoreSettings(uuid,str_companyname,str_locationname,str_sitename,str_buildingname,str_wingname,str_floorname,str_virtualareaname,areaName,str_feedbackservice,"20000","yes","Excellent|Very Good|Average|Poor");//area_spinner.getSelectedItemsAsString()
                    System.out.println("isInserted = " + isInserted);
    
                    if (isInserted)
                    {
                        Toast.makeText(AdminDetailsConfig.this, "Submitted", Toast.LENGTH_SHORT).show();
//                        if(areaName.contains("|")) {
//                            startActivity(new Intent(AdminDetailsConfig.this, SelectArea.class));
//                        }else{
//                        SharedPreferences.Editor editor = prefs.edit();
//                        editor.putString("area", areaName);
//                        editor.commit();
//                            Intent i=new Intent(AdminDetailsConfig.this, FeedbackActivity.class);
//                            i.putExtra("area",areaName);
//                            startActivity(i);
//                        }
                        startActivity(new Intent(AdminDetailsConfig.this, SelectArea.class));
                    }
                    else
                    {
                        Toast.makeText(AdminDetailsConfig.this, "Some error occured, Please try again", Toast.LENGTH_SHORT).show();
                    }
                    

                }
                
                
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
