package com.example.feedbackapplication.adminlogin;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedbackapplication.BaseActivity;
import com.example.feedbackapplication.HttpHandler;
import com.example.feedbackapplication.R;
import com.example.feedbackapplication.database.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    String str_clientid,str_username,str_pwd,uuid="";
    String feedbackservicename="";
    EditText edt_username,edt_pwd,edt_clientid;
    Button btn_login;
    private ProgressDialog pDialog;
    int questionscount;
    public boolean internetConnection = false;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

   /* try {
        questionscount = dbh.admindetails_count();
        if (questionscount == 0) {
            dbh.insertAdminDetails("1", "Dell Score Feedback", "12", "Location 1",
                    "13", "Site 1", "14", "Building 1", "15", "Wing-1", "16",
                    "1st Floor", "17", "Gents Washroom","Area specific|Common Feedback|Common Feedback with Area Specific");

            dbh.insertAdminDetails("1", "Dell Score Feedback", "12", "Location 1",
                    "13", "Site 1", "14", "Building 1", "15", "Wing-1", "16",
                    "1st Floor", "17", "Ladies Washroom","Common Feedback|Common Feedback with Area Specific");

           dbh.insertAdminDetails("1", "Dell Score Feedback", "12", "Location 1",
                    "13", "Site 5", "14", "Building 3", "", "", "16",
                    "5th Floor", "17", "Cafeteria","Area specific");

            dbh.insertAdminDetails("1", "Dell Score Feedback", "21", "Location 2",
                    "22", "Site 4", "23", "Building 4", "24", "Wing 2", "18",
                    "4th Floor", "17", "Pantry","Common Feedback with Area Specific");

            dbh.insertAdminDetails("2", "ISS Feedback", "", "",
                    "24", "Site 2", "25", "Building 2", "123456", "Wing extra", "27",
                    "2nd Floor", "28", "Cafeteria","Common Feedback");

            dbh.insertAdminDetails("2", "ISS Feedback", "", "",
                    "24", "Site 3", "25", "Building 3", "", "", "27",
                    "3rd Floor", "28", "Washroom","Area specific|Common Feedback with Area Specific");

            dbh.insertAdminDetails("3", "NTT Feedback", "120", "Location 4",
                    "", "", "17", "Building 6", "15", "Wing 4", "16",
                    "6th Floor", "17", "Washroom","Area specific|Common Feedback|Common Feedback with Area Specific");

            dbh.insertAdminDetails("3", "NTT Feedback", "120", "Location 47",
                    "", "", "17", "Building 16", "15", "Wing 4", "4545",
                    "13th Floor", "17", "Washroom","Area specific");

            dbh.insertAdminDetails("4", "E-clerx", "", "",
                    "", "", "157", "Building 7", "15", "Wing 5", "16",
                    "7th Floor", "17", "Washroom","Area specific|Common Feedback|Common Feedback with Area Specific");
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Exception = " + e);
    }*/


    try {
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_pwd = (EditText) findViewById(R.id.edt_password);
        edt_clientid = (EditText) findViewById(R.id.edt_clientid);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Login Activity 65 = " + e);
    }

}

@Override
public void onClick(View view) {

    try {
        // str_companyname = companyname.getSelectedItem().toString();
        str_clientid = edt_clientid.getText().toString().trim();
        str_username = edt_username.getText().toString().trim();
        str_pwd = edt_pwd.getText().toString().trim();

        if(str_clientid.length()==0){
            edt_clientid.setError("Client ID is not entered");
            edt_clientid.requestFocus();
        }
        else if(str_username.length()==0){
            edt_username.setError("Username is not entered");
            edt_username.requestFocus();
        }
        else if(str_pwd.length()==0){
            edt_pwd.setError("Password is not entered");
            edt_pwd.requestFocus();
        }
        else
        {
            uuid = UUID.randomUUID().toString();
            //dbh.insertLoginDetails(uuid,str_username,str_pwd,str_companyname);
            boolean isInserted = dbh.insertLoginDetails(uuid,str_clientid,str_username,str_pwd);
            System.out.println("isInserted = " + isInserted);

            if (isInserted)
            {
                Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                questionscount = dbh.admindetails_count();
                if (questionscount == 0) {
                    new CheckingInternetConnectivity().execute();
                }else {


//                feedbackservicename = dbh.getFeedbackServiceType(str_clientid);
//                if (!feedbackservicename.contains("|"))
//                {
                    Intent intent = new Intent(LoginActivity.this,AdminDetailsConfig.class);
                    intent.putExtra("feedbackservicename",feedbackservicename);
                    intent.putExtra("client_id",str_clientid);
                    startActivity(intent);
//                }
//                else{
//                    Intent intent = new Intent(LoginActivity.this,FeedbackServiceAct.class);
//                    intent.putExtra("client_id",str_clientid);
//                    startActivity(intent);
//                }
                }
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Some error occured, Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error while logging in = " + e);
    }

}

    private class CheckingInternetConnectivity extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pDialog = new ProgressDialog(LoginActivity.this);
                pDialog.setMessage("Checking internet connectivity. Please Wait..");
                pDialog.setIndeterminate(false);
                pDialog.setMax(200);
                pDialog.setProgress(0);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(true);
                pDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                URL url = new URL("https://www.google.com/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(1500);
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    internetConnection = true;
                } else {
                    internetConnection = false;
                }
            } catch (Exception E) {
                E.printStackTrace();
                internetConnection = false;
            }

            return internetConnection;


        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result == true) {
                pDialog.dismiss();
                new DataDownload().execute();
            } else {
                pDialog.dismiss();

            }
        }

    }

    private class DataDownload extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... URL) {
            DatabaseHelper databaseHelper1 = DatabaseHelper.getInstance(getApplicationContext());
            SQLiteDatabase db1 = databaseHelper1.getWritableDatabase();
            HttpHandler handler = new HttpHandler();
            String jsonFeedbackSms = handler.taskDataCall();
            System.out.print("*********data downloaded:"+jsonFeedbackSms);
//            finish();
            if (jsonFeedbackSms != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonFeedbackSms);

                    ////////////////////////Company Details////////////////////////////
                    try {
                        JSONArray task = jsonObj.getJSONArray("adminDetails");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                //{"Company_Id":"39fe893c-dac8-11e9-99e9-00163cd36c5e","Company_Name":"Dell_Bangalore","Location_Id":null,"Location_Name":null,"Site_Id":"70ac1719-dac8-11e9-99e9-00163cd36c5e","Site_Name":"Dell bangalore","Building_Id":"d1b1ddf4-9f44-11ea-a8e4-1c1b0dc1b24f","Building_Name":"Dell","Wing_Id":null,"Wing_Name":null,"Floor_Id":"1bbab775-5f7d-11ea-b264-00163cd36c5e","Floor_Name":"Ground Floor","Area_Id":null,"Area_Name":null}
//                                String Auto_Id = c1.getString("Auto_Id");
                                String Company_Name = c1.getString("Company_Name");
                                String Company_Id = c1.getString("Company_Id");
                                String Location_Id="",Location_Name="",Site_Name="",Site_Id="",Building_Name="",Building_Id="",Floor_Name="",Floor_Id="",Area_Name="",Area_Id="",Wing_Id="",Wing_Name="";
                                if(!c1.getString("Location_Id").equals("null")) {
                                    Location_Id = c1.getString("Location_Id");
                                }
                                if(!c1.getString("Location_Name").equals("null")) {
                                    Location_Name = c1.getString("Location_Name");
                                }
                                if(!c1.getString("Site_Id").equals("null")) {
                                    Site_Id = c1.getString("Site_Id");
                                }
                                if(!c1.getString("Site_Name").equals("null")) {
                                    Site_Name = c1.getString("Site_Name");
                                }
                                if(!c1.getString("Building_Id").equals("null")) {
                                    Building_Id = c1.getString("Building_Id");
                                }
                                if(!c1.getString("Building_Name").equals("null")) {
                                    Building_Name = c1.getString("Building_Name");
                                }
                                if(!c1.getString("Floor_Id").equals("null")) {
                                    Floor_Id = c1.getString("Floor_Id");
                                }
                                if(!c1.getString("Floor_Name").equals("null")) {
                                    Floor_Name = c1.getString("Floor_Name");
                                }
                                if(!c1.getString("Area_Id").equals("null")) {
                                    Area_Id = c1.getString("Area_Id");
                                }
                                if(!c1.getString("Area_Name").equals("null")) {
                                    Area_Name = c1.getString("Area_Name");
                                }
                                if(!c1.getString("Wing_Id").equals("null")) {
                                    Wing_Id = c1.getString("Wing_Id");
                                }
                                if(!c1.getString("Wing_Name").equals("null")) {
                                    Wing_Name = c1.getString("Wing_Name");
                                }
                                String selectQuery = "SELECT * FROM admin_details";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    /*ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Company_Name", Company_Name);
                                    contentValues.put("Company_Id", Company_Id);
                                    contentValues.put("Location_Id", Location_Id);
                                    contentValues.put("Site_Name", Site_Name);
                                    contentValues.put("Building_Name", Building_Name);
                                    contentValues.put("Site_Location_Id", Site_Location_Id);
                                    contentValues.put("Floor_Name", Floor_Name);
                                    contentValues.put("Area_Name", Area_Name);
                                    db1.insert("admin_details", null, contentValues);*/
                                    dbh.insertAdminDetails(Company_Id, Company_Name, Location_Id, Location_Name,
                                            Site_Id, Site_Name, Building_Id, Building_Name,
                                            Wing_Id, Wing_Name, Floor_Id,
                                            Floor_Name, Area_Id, Area_Name,
                                            "Area specific|Common Feedback|Common Feedback with Area Specific");//Area specific|Common Feedback|Common Feedback with Area Specific
                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }
                    ////////////////////SMS Master///////////////////////////////
                   /* try {
                        JSONArray task = jsonObj.getJSONArray("SMSMaster");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                String Auto_Id = c1.getString("Auto_Id");
                                String UserName = c1.getString("UserName");
                                String Password = c1.getString("Password");
                                String Type = c1.getString("Type");
                                String Source = c1.getString("Source");
                                String Sms_URL = c1.getString("URL");
                                String selectQuery = "SELECT * FROM sms_master";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("UserName", UserName);
                                    contentValues.put("Password", Password);
                                    contentValues.put("Type", Type);
                                    contentValues.put("Source", Source);
                                    contentValues.put("URL", Sms_URL);
                                    db1.insert("sms_master", null, contentValues);

                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }
                    ////////////////////Feedback SMS///////////////////////////////
                    try {
                        JSONArray task = jsonObj.getJSONArray("FeedbackSMS");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                String Auto_Id = c1.getString("Auto_Id");
                                String Mobile_Number = c1.getString("Mobile");
                                String Site_Location_Id = c1.getString("Site_Location_Id");
                                String selectQuery = "SELECT * FROM feedback_sms";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Mobile_Number", Mobile_Number);
                                    contentValues.put("Site_Location_Id", Site_Location_Id);
                                    db1.insert("feedback_sms", null, contentValues);

                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }*/
                    ////////////////////////Feedback Questions////////////////////////////
                    try {
                        JSONArray task = jsonObj.getJSONArray("feedbackQuestionDetails");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                String Auto_Id = c1.getString("Auto_Id");
                                String Feedback_Question = c1.getString("Feedback_Question");
                                String Area_Id="";
                                if(!c1.getString("Area_Id").equals("null")){
                                    Area_Id= c1.getString("Area_Id");
                                }
                                String Order_Id = c1.getString("Disp_Order");
//                                String Icon_Type= c1.getString("Icon_Type");
                                String selectQuery = "SELECT * FROM feedback_adminquestions";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Feedback_Question", Feedback_Question);
                                    contentValues.put("Order_Id", Order_Id);
                                    contentValues.put("Area_Id", Area_Id);
//                                    contentValues.put("Icon_Type", Icon_Type);
                                    db1.insert("feedback_adminquestions", null, contentValues);
//                                    dbh.insertData(Auto_Id, Feedback_Question, Order_Id, "Smiley",Area_Id);
                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }

                    try {
                        JSONArray task = jsonObj.getJSONArray("feedbackSubQuestionDetails");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                String Auto_Id = c1.getString("Auto_Id");
                                String Feedback_Question_Id = c1.getString("Feedback_Question_Id");
                                String Feedback_Sub_Question= c1.getString("Feedback_Sub_Question");

                                String Order_Id = c1.getString("Disp_Order");
                                String selectQuery = "SELECT * FROM feedback_adminsubquestions";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    //Auto_Id TEXT, Feedback_Id TEXT, Feedback_Sub_Question TEXT,Icon Blob, Order_Id TEXT,Weightage TEXT,RecordStatus TEXT
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Feedback_Id", Feedback_Question_Id);
                                    contentValues.put("Feedback_Sub_Question", Feedback_Sub_Question);
                                    contentValues.put("Order_Id", Order_Id);
                                    db1.insert("feedback_adminsubquestions", null, contentValues);

                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }

                    ////////////////////////Site Details////////////////////////////
                   /* try {
                        JSONArray task = jsonObj.getJSONArray("siteDetails");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                String Auto_Id = c1.getString("Auto_Id");
                                String Company_Id = c1.getString("Company_Id");
                                String Location_Id = c1.getString("Location_Id");
                                String Site_Name = c1.getString("Site_Name");
                                String selectQuery = "SELECT * FROM admin_details";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Company_Id", Company_Id);
                                    contentValues.put("Location_Id", Location_Id);
                                    contentValues.put("Site_Name", Site_Name);
                                    db1.insert("admin_details", null, contentValues);
                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }*/
                    ////////////////////////Wing Details////////////////////////////
                 /*   try {
                        JSONArray task = jsonObj.getJSONArray("wingDetails");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                String Auto_Id = c1.getString("Auto_Id");
                                String Building_Name = c1.getString("Building_Name");
                                String Site_Location_Id = c1.getString("Site_Location_Id");
                                //String Code = c1.getString("Code");
                                String selectQuery = "SELECT * FROM building_detail";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Building_Name", Building_Name);
                                    contentValues.put("Site_Location_Id", Site_Location_Id);
                                    //contentValues.put("Code",Code);
                                    db1.insert("building_detail", null, contentValues);

                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }*/
                    ////////////////////////Floor Details////////////////////////////
                   /* try {
                        JSONArray task = jsonObj.getJSONArray("floorDetails");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                String Auto_Id = c1.getString("Auto_Id");
                                String Floor_Name = c1.getString("Floor_Name");
                                String Site_Location_Id = c1.getString("Site_Location_Id");
                                //String Code = c1.getString("Code");
                                String selectQuery = "SELECT * FROM floor_detail";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Floor_Name", Floor_Name);
                                    contentValues.put("Site_Location_Id", Site_Location_Id);
                                    //contentValues.put("Code",Code);
                                    db1.insert("floor_detail", null, contentValues);
                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }*/
                    ////////////////////////Location Details////////////////////////////
                /*    try {
                        JSONArray task = jsonObj.getJSONArray("ServiceMaster");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                String Auto_Id = c1.getString("Auto_Id");
                                String Service_Name = c1.getString("Service_Name");
                                String Site_Location_Id = c1.getString("Site_Location_Id");
                                //String Code = c1.getString("Code");
                                String selectQuery = "SELECT * FROM Service_detail";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Service_Name", Service_Name);
                                    contentValues.put("Site_Location_Id", Site_Location_Id);
                                    //contentValues.put("Code",Code);
                                    db1.insert("Service_detail", null, contentValues);
                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }*/
                    ////////////////////////Area Details////////////////////////////
                /*    try {
                        JSONArray task = jsonObj.getJSONArray("areaDetails");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                String Auto_Id = c1.getString("Auto_Id");
                                String Area_Name = c1.getString("Area_Name");
                                String Site_Location_Id = c1.getString("Site_Location_Id");
                                //String Code = c1.getString("Code");
                                String selectQuery = "SELECT * FROM area_detail";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Area_Name", Area_Name);
                                    contentValues.put("Site_Location_Id", Site_Location_Id);
                                    //contentValues.put("Code",Code);
                                    db1.insert("area_detail", null, contentValues);
                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }*/
                    //////////////////////Feedback Icons////////////////////////////
                    try {
                        JSONArray task = jsonObj.getJSONArray("feedbackiconDetails");
                        Log.d("DATasValue", task + "" + task.length());
                        if (!task.toString().equals("[]")) {

                            for (int j = 0; j < task.length(); j++) {
                                JSONObject c1 = task.getJSONObject(j);
                                //"Auto_Id":"0978d793-a018-11ea-a8e4-1c1b0dc1b24f",
                                //         "Feedback_Name":"Average",
                                //         "Icon_Value":null,
                                //         "Icon_Type":"Smiley"
                                //Auto_Id TEXT, Icon_Name TEXT, Icon_value BLOB,Icon_Type TEXT, Status TEXT
                                String Auto_Id = c1.getString("Auto_Id");
                                String Feedback_Name = c1.getString("Feedback_Name");
//                                String Icon_Value = c1.getString("Icon_Value");
                                String Icon_Type = c1.getString("Icon_Type");
                                String selectQuery = "SELECT * FROM feedback_admin_icondetails";
                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
                                db1 = databaseHelper1.getWritableDatabase();
                                Cursor cursor = db1.rawQuery(selectQuery, null);
                                Log.d("asdgvdafg", cursor.getCount() + "");
                                if (cursor.getCount() < task.length()) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("Auto_Id", Auto_Id);
                                    contentValues.put("Icon_Name", Feedback_Name);
                                    contentValues.put("Icon_Type", Icon_Type);
                                    db1.insert("feedback_admin_icondetails", null, contentValues);
                                }
                                cursor.close();
                                db1.close();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //db1.endTransaction();
                    }
                    //insert icon hardcoded values
                  /*  try {
                        SQLiteDatabase db2 = databaseHelper1.getWritableDatabase();
                        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.cafeteria);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                         byte[] img = bos.toByteArray();
                        //myDB = new DatabaseHelper(this);

                        ContentValues cv = new ContentValues();
                        cv.put("Icon_Name", "Cafeteria");
                        cv.put("Icon_value", img);
                        cv.put("Icon_Type", "area");
//            cv.put("Area_Name", "Cafeteria");

                        db2.insert("feedback_admin_icondetails", null, cv);


                        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.washroom);
                        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                        b1.compress(Bitmap.CompressFormat.PNG, 100, bos1);
                        byte[] img1 = bos1.toByteArray();
                        //myDB = new DatabaseHelper(this);

                        ContentValues cv1 = new ContentValues();
                        cv1.put("Icon_Name", "Washroom");
                        cv1.put("Icon_value", img1);
                        cv1.put("Icon_Type", "area");
//            cv1.put("Area_Name", "Cafeteria");

                        db2.insert("feedback_admin_icondetails", null, cv1);

                      *//*  Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.washroom);
                        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                        b1.compress(Bitmap.CompressFormat.PNG, 100, bos2);
                        byte[] img2 = bos2.toByteArray();
                        //myDB = new DatabaseHelper(this);

                        ContentValues cv2 = new ContentValues();
                        cv2.put("Icon_Name", "Washroom");
                        cv2.put("Icon_value", img1);
                        cv2.put("Icon_Type", "area");
//            cv1.put("Area_Name", "Cafeteria");

                        sqLiteDatabase.insert("feedback_admin_icondetails", null, cv2);*//*
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/


//                    ////////////////////////Feedback Email Details////////////////////////////
//                    try {
//                        JSONArray task = jsonObj.getJSONArray("feedbackEmail");
//                        Log.d("DATasValue", task + "" + task.length());
//                        if (!task.toString().equals("[]")) {
//
//                            for (int j = 0; j < task.length(); j++) {
//                                JSONObject c1 = task.getJSONObject(j);
//                                String Auto_Id = c1.getString("Auto_Id");
//                                String Employee_Email = c1.getString("Employee_Email");
//                                String Recipient_Type = c1.getString("Recipient_Type");
//                                String Building_Id = c1.getString("Building_Id");
//                                String selectQuery = "SELECT * FROM EmailList";
//                                databaseHelper1 = new DatabaseHelper(getApplicationContext());
//                                db1 = databaseHelper1.getWritableDatabase();
//                                Cursor cursor = db1.rawQuery(selectQuery, null);
//                                Log.d("asdgvdafg", cursor.getCount() + "");
//                                if (cursor.getCount() < task.length()) {
//                                    ContentValues contentValues = new ContentValues();
//                                    contentValues.put("Auto_Id", Auto_Id);
//                                    contentValues.put("Employee_Email", Employee_Email);
//                                    contentValues.put("Recipient_Type", Recipient_Type);
//                                    contentValues.put("Building_Id", Building_Id);
//                                    db1.insert("EmailList", null, contentValues);
//                                }
//                                cursor.close();
//                                db1.close();
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        //db1.endTransaction();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Intent intent = new Intent(getApplicationContext(), AdminDetailsConfig.class);
            intent.putExtra("feedbackservicename",feedbackservicename);
            intent.putExtra("client_id",str_clientid);
//            count++;
//            intent.putExtra("Count", count);
            startActivity(intent);
        }

    }

}
