package com.example.feedbackapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ThankuActivityScore extends BaseActivity {

    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    View decorView;

    //    DatabaseHelper dbh;
//    SharedPreferences prefs;
    String ShowLogo, Timeout, area;
    Snackbar snackbar;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanku);
        try {
            countDownTimer=null;
            linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            //  Toast.makeText(this, "ThankuActivityScore called...", Toast.LENGTH_SHORT).show();
//        prefs = PreferenceManager.getDefaultSharedPreferences(ThankuActivityScore.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("QuestNo", 1);
            editor.commit();

            area = getIntent().getStringExtra("area");
//        dbh = new DatabaseHelper(ThankuActivityScore.this);
            ContentValues values = new ContentValues();
            values.put("Weightage", "no");
            SQLiteDatabase sqLiteDatabase1 = dbh.getWritableDatabase();
            sqLiteDatabase1.update("feedback_adminquestions", values, null, null);
            sqLiteDatabase1.close();

            SQLiteDatabase db1 = dbh.getWritableDatabase();

         /*   Cursor cursor1 = db1.rawQuery("Select Thankyou_Timeout,ShowLogo from store_setting ;", null);


            if (cursor1.moveToFirst()) {
                do {
//                    Timeout = cursor1.getString(0);
                    ShowLogo = cursor1.getString(1);

                } while (cursor1.moveToNext());
                db1.close();
            }*/

            Timeout = "20000";
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
//
//                startActivity(intent);
//            }
//        },Integer.parseInt(Timeout));

            countDownTimer = new CountDownTimer(Long.parseLong(Timeout), 1000) {

                public void onTick(long millisUntilFinished) {
                    Log.i("********", "seconds remaining: " + millisUntilFinished / 1000);
                    Toast.makeText(ThankuActivityScore.this, "You will be redirected to Home page in " + millisUntilFinished / 1000+" secs", Toast.LENGTH_SHORT).show();
                  /*  try {
                            snackbar = Snackbar
                                    .make(linearLayout, "", snackbar.LENGTH_INDEFINITE);
                            (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

                       if (!snackbar.isShown()) {
                           snackbar.show();
                       }
                         if (millisUntilFinished / 1000 < 21){
                            snackbar.setText("You will be redirected to Feedback Questions page in " + millisUntilFinished / 1000 + " secs");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }

                public void onFinish() {
                    Log.i("********", "Timer Finished");
                    countDownTimer.cancel();
                    Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                    intent.putExtra("area", area);
                    startActivity(intent);
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public JSONArray composeJSONFeedback() {
        JSONArray UploadArray = new JSONArray();
        JSONObject Feedback_details = new JSONObject();
        JSONObject Negative_feedback = new JSONObject();
        JSONObject Public_details = new JSONObject();

        JSONArray FeedbackArray = new JSONArray();
        JSONArray NegativeArray = new JSONArray();
        JSONArray PublicArray = new JSONArray();

        try {
//            String[] loggerData = new LoggerFile().loggerFunction();
            String selectQuery = "SELECT  * FROM admin_details where  RecordStatus = 'no' LIMIT 25";
            sqLiteDatabase = dbh.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do
                {//ID INTEGER PRIMARY KEY,Company_ID TEXT,Company_Name TEXT,Location_Id TEXT,Location_Name TEXT,Site_Id TEXT, Site_Name TEXT,Building_Id TEXT,
                    // Building_Name TEXT,Wing_Id TEXT,Wing_Name TEXT,Floor_Id TEXT, Floor_Name TEXT,Area_Id TEXT, Area_Name TEXT,Feedback_Service_Type text, RecordStatus TEXT

                    JSONObject feedbackObject = new JSONObject();
                    String feedbackId = cursor.getString(cursor.getColumnIndex("ID"));
                    feedbackObject.put("ID", cursor.getString(cursor.getColumnIndex("ID")));
                    feedbackObject.put("Company_ID", cursor.getString(cursor.getColumnIndex("Company_ID")));
                    feedbackObject.put("Company_Name", cursor.getString(cursor.getColumnIndex("Company_Name")));
                    feedbackObject.put("Location_Id", cursor.getString(cursor.getColumnIndex("Location_Id")));
                    feedbackObject.put("Gender", cursor.getString(cursor.getColumnIndex("Location_Name")));
                    feedbackObject.put("Location_Name", cursor.getString(cursor.getColumnIndex("Building_Id")));
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
//                    feedbackObject.put("Last_IP", loggerData[1]);
//                    feedbackObject.put("Update_Location", loggerData[2]);
//                    feedbackObject.put("Apk_Web_Version", loggerData[3]);



                    /*negative_feedback (ID INTEGER PRIMARY KEY ,Auto_Id TEXT,
                    Feedback_Id TEXT, Negative_Feedback TEXT,UpdatedStatus TEXT*/

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
//                                negativeFeedBack.put("Last_IP", loggerData[1]);
//                                negativeFeedBack.put("Update_Location", loggerData[2]);
//                                negativeFeedBack.put("Apk_Web_Version", loggerData[3]);

                            } while (cursorMeterReading.moveToNext());

                        }
                        NegativeArray.put(negativeFeedBack);
                    }
                    cursorMeterReading.close();

                    /*public_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Id TEXT,
                    Public_Name TEXT, Email TEXT, Contact TEXT,UpdatedStatus TEXT)*/

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
    }
}
