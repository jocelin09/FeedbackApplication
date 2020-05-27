package com.example.feedbackapplication.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.feedbackapplication.BaseActivity;
import com.example.feedbackapplication.FeedbackActivity;
import com.example.feedbackapplication.LoggerFile;
import com.example.feedbackapplication.R;
import com.example.feedbackapplication.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

public class SelectArea extends BaseActivity {

    //    DatabaseHelper databaseHelper;
//    SQLiteDatabase sqLiteDatabase;
    String AreaName,Icon_List  ;//= "Cafeteria|Washroom";
    ArrayList<String> areaList;
    LinearLayout linearLayout, linearLayout1;
    private byte[] img = null;
    private byte[] img1 = null;
    int imagecount,rec_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_area);
//        databaseHelper = new DatabaseHelper(this);
//        sqLiteDatabase = databaseHelper.getWritableDatabase();
        imagecount = dbh.totalimage_count();
        if (imagecount == 0) {
            try {
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.cafeteria);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                img = bos.toByteArray();
                //myDB = new DatabaseHelper(this);

                ContentValues cv = new ContentValues();
                cv.put("Icon_Name", "Cafeteria");
                cv.put("Icon_value", img);
                cv.put("Icon_Type", "area");
//            cv.put("Area_Name", "Cafeteria");

                sqLiteDatabase.insert("feedback_admin_icondetails", null, cv);


                Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.washroom);
                ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                b1.compress(Bitmap.CompressFormat.PNG, 100, bos1);
                img1 = bos1.toByteArray();
                //myDB = new DatabaseHelper(this);

                ContentValues cv1 = new ContentValues();
                cv1.put("Icon_Name", "Washroom");
                cv1.put("Icon_value", img1);
                cv1.put("Icon_Type", "area");
//            cv1.put("Area_Name", "Cafeteria");

                sqLiteDatabase.insert("feedback_admin_icondetails", null, cv1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        linearLayout = new LinearLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(VERTICAL);
        //  linearLayout.setBackgroundColor(R.color.colorAccent);


        linearLayout1 = new LinearLayout(this);
        // new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);

        LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        mainLayoutParams.setMargins(10, 30, 10, 0);

        linearLayout1.setLayoutParams(mainLayoutParams);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setGravity(Gravity.CENTER);
        areaList = new ArrayList<String>();
        try {
            SQLiteDatabase db1 = dbh.getWritableDatabase();

            Cursor cursor1 = db1.rawQuery("Select Admin_Id,Area_Name,Icon_List from store_setting ;", null);


            if (cursor1.moveToFirst()) {
                do {
                    rec_id= cursor1.getInt(0);
                    AreaName = cursor1.getString(1);
                    Icon_List = cursor1.getString(2);

                } while (cursor1.moveToNext());
                db1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (AreaName.contains("|")) {
            String[] areas = AreaName.split("\\|");
            for (int i = 0; i < areas.length; i++) {
                areaList.add(areas[i]);
            }
        } else {
            areaList.add(AreaName);
        }

        for (int j = 0; j < areaList.size(); j++) {

            String area = areaList.get(j).toString();
            linearLayout1.addView(imageView(j + 1, area));
            /*if (area.equalsIgnoreCase("Cafeteria")) {
                linearLayout1.addView(imageView(j + 1, area));

            } else if (area.equalsIgnoreCase("Washroom")) {
                linearLayout1.addView(imageView(j + 1, area));

            }*/


        }
        linearLayout.addView(linearLayout1);
        setContentView(linearLayout);
    }

    private ImageView imageView(final int id, final String strvalue) {

        final String imgvalue = strvalue;
        ImageView imageView = new ImageView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(700, 300);
        params.setMargins(50, 20, 50, 20);
        imageView.setLayoutParams(params);
        Bitmap b = null;

           /* if (imgvalue.equalsIgnoreCase("Cafeteria")) {
                imageView.setBackgroundResource(R.drawable.cafeteria);
            } else if (imgvalue.equalsIgnoreCase("Washroom")) {
                imageView.setBackgroundResource(R.drawable.washroom);
            }*/
        byte[] image_str = dbh.readDataIcon(strvalue);

        try {
            b = BitmapFactory.decodeByteArray(image_str, 0, image_str.length);

            imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 600, 200, true));
        } catch (Exception e) {

            e.printStackTrace();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("area", imgvalue);
                editor.commit();

                if (imgvalue.equalsIgnoreCase("Cafeteria")) {
                    Intent i = new Intent(SelectArea.this, FeedbackActivity.class);
                    i.putExtra("rec_id",rec_id);
                    i.putExtra("area",imgvalue);
                    startActivity(i);
                } else if (imgvalue.equalsIgnoreCase("Washroom")) {
                    Intent i = new Intent(SelectArea.this, FeedbackService.class);
                    i.putExtra("area",imgvalue);
                    startActivity(i);
                }
            }
        });
        return imageView;
    }

    private TextView subtextView(int hint, String uname) {
        final TextView textView = new TextView(this);
        textView.setId(hint);
        textView.setTextSize(1, 40);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(uname);

        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        return textView;

    }

   /* public JSONArray composeJSONFeedback() {
        JSONArray UploadArray = new JSONArray();
        JSONObject Feedback_details = new JSONObject();
        JSONObject Negative_feedback = new JSONObject();
        JSONObject Public_details = new JSONObject();

        JSONArray FeedbackArray = new JSONArray();
        JSONArray NegativeArray = new JSONArray();
        JSONArray PublicArray = new JSONArray();

        try {
            String[] loggerData = new LoggerFile().loggerFunction();
            String selectQuery = "SELECT  * FROM admin_details where  RecordStatus = 'no' LIMIT 25";
            sqLiteDatabase = dbh.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {//ID INTEGER PRIMARY KEY,Company_ID TEXT,Company_Name TEXT,Location_Id TEXT,Location_Name TEXT,Site_Id TEXT, Site_Name TEXT,Building_Id TEXT,
                    // Building_Name TEXT,Wing_Id TEXT,Wing_Name TEXT,Floor_Id TEXT, Floor_Name TEXT,Area_Id TEXT, Area_Name TEXT,Feedback_Service_Type text, RecordStatus TEXT

                    JSONObject feedbackObject = new JSONObject();
                    String feedbackId = cursor.getString(cursor.getColumnIndex("ID"));
                    feedbackObject.put("ID", cursor.getString(cursor.getColumnIndex("ID")));
                    feedbackObject.put("Company_ID", cursor.getString(cursor.getColumnIndex("Company_ID")));
                    feedbackObject.put("Company_Name", cursor.getString(cursor.getColumnIndex("Company_Name")));
                    feedbackObject.put("Location_Id", cursor.getString(cursor.getColumnIndex("Location_Id")));
                    feedbackObject.put("Location_Name", cursor.getString(cursor.getColumnIndex("Location_Name")));
                    feedbackObject.put("Building_Id", cursor.getString(cursor.getColumnIndex("Building_Id")));
                    feedbackObject.put("Building_Name", cursor.getString(cursor.getColumnIndex("Building_Name")));
                    feedbackObject.put("Floor_Id", cursor.getString(cursor.getColumnIndex("Floor_Id")));
                    feedbackObject.put("Floor_Name", cursor.getString(cursor.getColumnIndex("Floor_Name")));
                    feedbackObject.put("Area_Id", cursor.getString(cursor.getColumnIndex("Area_Id")));
                    feedbackObject.put("Area_Name", cursor.getString(cursor.getColumnIndex("Area_Name")));
                    feedbackObject.put("Feedback_Service_Type", cursor.getString(cursor.getColumnIndex("Feedback_Service_Type")));



                    feedbackObject.put("Feedback_Icon_Id", cursor.getString(cursor.getColumnIndex("Feedback_Icon_Id")));
                    feedbackObject.put("Feedback_Question_Id", cursor.getString(cursor.getColumnIndex("Feedback_Question_Id")));
                    feedbackObject.put("Comments", cursor.getString(cursor.getColumnIndex("Comments")));
                    feedbackObject.put("Company_Name", cursor.getString(cursor.getColumnIndex("Company_Name")));
                    feedbackObject.put("Employee_Id", cursor.getString(cursor.getColumnIndex("Employee_Id")));
                    feedbackObject.put("Employee_Name", cursor.getString(cursor.getColumnIndex("Employee_Name")));
                    feedbackObject.put("Emp_Code", cursor.getString(cursor.getColumnIndex("Emp_Code")));
                    feedbackObject.put("Email", cursor.getString(cursor.getColumnIndex("Email")));
                    feedbackObject.put("Phone_Number", cursor.getString(cursor.getColumnIndex("Phone_Number")));
                    feedbackObject.put("Feedback_DateTime", cursor.getString(cursor.getColumnIndex("Feedback_DateTime")));
                    feedbackObject.put("Last_IP", loggerData[1]);
                    feedbackObject.put("Update_Location", loggerData[2]);
                    feedbackObject.put("Apk_Web_Version", loggerData[3]);



                    *//*negative_feedback (ID INTEGER PRIMARY KEY ,Auto_Id TEXT,
                    Feedback_Id TEXT, Negative_Feedback TEXT,UpdatedStatus TEXT*//*

                    String selectQuery1 = "SELECT  * FROM  negative_feedback WHERE Feedback_Id ='" + feedbackId + "'";
                    StringBuffer Negative_Feedback = new StringBuffer();
                    StringBuffer autoIdString = new StringBuffer();
                    Cursor cursorMeterReading = sqLiteDatabase.rawQuery(selectQuery1, null);
                    JSONObject negativeFeedBack = new JSONObject();
                    if (cursorMeterReading.getCount() != 0) {
                        if (cursorMeterReading.moveToFirst()) {
                            do {
                                Negative_Feedback.append(cursorMeterReading.getString(cursorMeterReading.getColumnIndex("Negative_Feedback"))).append("|");
                                autoIdString.append(cursorMeterReading.getString(cursorMeterReading.getColumnIndex("Auto_Id"))).append("|");
                                negativeFeedBack.put("Auto_Id", autoIdString.toString());
                                negativeFeedBack.put("Negative_Feedback", Negative_Feedback.toString());
                                negativeFeedBack.put("Feedback_Id", cursorMeterReading.getString(cursorMeterReading.getColumnIndex("Feedback_Id")));
                                negativeFeedBack.put("Company", cursorMeterReading.getString(cursorMeterReading.getColumnIndex("Company")));
                                negativeFeedBack.put("Name", cursorMeterReading.getString(cursorMeterReading.getColumnIndex("Name")));
                                negativeFeedBack.put("Email", cursorMeterReading.getString(cursorMeterReading.getColumnIndex("Email")));
                                negativeFeedBack.put("Contact", cursorMeterReading.getString(cursorMeterReading.getColumnIndex("Contact")));
                                negativeFeedBack.put("Comment", cursorMeterReading.getString(cursorMeterReading.getColumnIndex("Comment")));
                                negativeFeedBack.put("Last_IP", loggerData[1]);
                                negativeFeedBack.put("Update_Location", loggerData[2]);
                                negativeFeedBack.put("Apk_Web_Version", loggerData[3]);

                            } while (cursorMeterReading.moveToNext());

                        }
                        NegativeArray.put(negativeFeedBack);
                    }
                    cursorMeterReading.close();

                    *//*public_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Id TEXT,
                    Public_Name TEXT, Email TEXT, Contact TEXT,UpdatedStatus TEXT)*//*

                    JSONObject publicObject = new JSONObject();
                    String selectQueryData = "SELECT  * FROM public_details WHERE Feedback_Id ='" + feedbackId + "'";
                    Cursor cursorDataPosting = sqLiteDatabase.rawQuery(selectQueryData, null);
                    if (cursorDataPosting.getCount() != 0) {
                        if (cursorDataPosting.moveToFirst()) {
                            do {
                                publicObject.put("Auto_Id", cursorDataPosting.getString(cursorDataPosting.getColumnIndex("Auto_Id")));
                                publicObject.put("Feedback_Id", cursorDataPosting.getString(cursorDataPosting.getColumnIndex("Feedback_Id")));
                                publicObject.put("Public_Name", cursorDataPosting.getString(cursorDataPosting.getColumnIndex("Public_Name")));
                                publicObject.put("Email", cursorDataPosting.getString(cursorDataPosting.getColumnIndex("Email")));
                                publicObject.put("Contact", cursorDataPosting.getString(cursorDataPosting.getColumnIndex("Contact")));


                            } while (cursorDataPosting.moveToNext());
                        }
                        PublicArray.put(publicObject);
                    }
                    cursorDataPosting.close();

                    FeedbackArray.put(feedbackObject);
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception E) {
            E.printStackTrace();
        }

        try {

            Feedback_details.put("FeedbackDetails", FeedbackArray);
            Negative_feedback.put("NegativeFeedback", NegativeArray);
            Public_details.put("PublicInfo", PublicArray);


            UploadArray.put(Feedback_details);
            UploadArray.put(Negative_feedback);
            UploadArray.put(Public_details);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return UploadArray;
    }*/


}
