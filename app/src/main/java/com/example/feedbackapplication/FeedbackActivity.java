package com.example.feedbackapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedbackapplication.adminlogin.SelectArea;
import com.example.feedbackapplication.database.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

public class FeedbackActivity extends BaseActivity {

    String rbid = "", Icon_List;
    int r_id = 0;
    ScrollView s;
    int totalfeedback, rec_id;
    LinearLayout linearLayout, linearLayout1, linearLayout2, linearLayout3, linearLayout4;
    Runnable runnable;
    String uuid1, uuid2, uuid3, uuid4;
    String uuid5, uuid6, uuid7, uuid8, uuid9, uuid10;
    //    DatabaseHelper dbh;
//    SQLiteDatabase sqLiteDatabase;
//    SharedPreferences prefs;
    int questionscount1;
    private ArrayList<TextView> textViewList = new ArrayList<TextView>();
    private ArrayList<String> questions = new ArrayList<>();
    String area;
    ArrayList<String> iconList = new ArrayList<>();
    //    private byte[] img = null;
    private byte[] img = null;
    private byte[] img1 = null;
    private byte[] img2 = null;
    private byte[] img3 = null;
    private byte[] img4 = null;
    private byte[] img5 = null;
    private byte[] img6 = null;
    private byte[] img7 = null;
    private byte[] img8 = null;
    private byte[] img9 = null;
    final Handler handler = new Handler();
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feedback);
//        dbh = new DatabaseHelper(FeedbackActivity.this);
//        sqLiteDatabase = dbh.getWritableDatabase();
//        prefs = PreferenceManager.getDefaultSharedPreferences(FeedbackActivity.this);
        try {
            rec_id = getIntent().getIntExtra("rec_id", 0);
//        area = getIntent().getStringExtra("area");
            SharedPreferences.Editor editor = prefs.edit();
            area = prefs.getString("area", "");
            if (!prefs.contains("QuestNo")) {
                editor.putInt("QuestNo", 1);
                editor.commit();
            } else {
                totalfeedback = prefs.getInt("QuestNo", 1);
            }
            if (!prefs.contains("isVisible")) {
                editor.putBoolean("isVisible", true);
                editor.commit();
            }

            //sqLiteDatabase.delete("feedback_admin_icondetails", "ID" + "=" + 10, null);



            questionscount1 = dbh.totalsmileyimage_count();
            if (questionscount1 == 0) {
//                uuid1 = UUID.randomUUID().toString();
//                dbh.insertData(uuid1, "How is your experience using cafeteria?", "1", "Smiley");
//
//
//                uuid2 = UUID.randomUUID().toString();
//                dbh.insertData(uuid2, "Do you really like the ambience of whole facility? How is it for you", "2", "Smiley");
//
//                uuid3 = UUID.randomUUID().toString();
//                dbh.insertData(uuid3, "Food Quality & Flavor", "3", "Smiley");
//
//                uuid4 = UUID.randomUUID().toString();
//                dbh.insertData(uuid4, "Service Personnel", "4", "Smiley");
//
//                uuid7 = UUID.randomUUID().toString();
//                dbh.insertData(uuid7, "Cafe Ambience", "5", "Smiley");
//
//                uuid8 = UUID.randomUUID().toString();
//                dbh.insertData(uuid8, "Food Hygiene", "6", "Smiley");
        
        
       /* uuid5 = UUID.randomUUID().toString();
        dbh.insertAreaDetails(uuid5, "123", "Washroom");
        
        uuid6 = UUID.randomUUID().toString();
        dbh.insertAreaDetails(uuid6, "456", "Cafeteria");*/


                try {

                    Bitmap b6 = BitmapFactory.decodeResource(getResources(), R.drawable.exellent);
                    ByteArrayOutputStream bos6 = new ByteArrayOutputStream();
                    b6.compress(Bitmap.CompressFormat.PNG, 100, bos6);
                    img6 = bos6.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv6 = new ContentValues();
                    cv6.put("Icon_Name", "Excellent");
                    cv6.put("Icon_value", img6);
                    cv6.put("Icon_Type", "Smiley");
                    // cv6.put("Area_Name", "");
                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv6);


                    Bitmap b5 = BitmapFactory.decodeResource(getResources(), R.drawable.verygood);
                    ByteArrayOutputStream bos5 = new ByteArrayOutputStream();
                    b5.compress(Bitmap.CompressFormat.PNG, 100, bos5);
                    img5 = bos5.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv5 = new ContentValues();
                    cv5.put("Icon_Name", "Very Good");
                    cv5.put("Icon_value", img5);
                    cv5.put("Icon_Type", "Smiley");
                    //  cv5.put("Area_Name", "");
                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv5);

                    Bitmap b7 = BitmapFactory.decodeResource(getResources(), R.drawable.average);
                    ByteArrayOutputStream bos7 = new ByteArrayOutputStream();
                    b7.compress(Bitmap.CompressFormat.PNG, 100, bos7);
                    img7 = bos7.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv7 = new ContentValues();
                    cv7.put("Icon_Name", "Average");
                    cv7.put("Icon_value", img7);
                    cv7.put("Icon_Type", "Smiley");
                    // cv7.put("Area_Name", "");
                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv7);

                    Bitmap b4 = BitmapFactory.decodeResource(getResources(), R.drawable.poor);
                    ByteArrayOutputStream bos4 = new ByteArrayOutputStream();
                    b4.compress(Bitmap.CompressFormat.PNG, 100, bos4);
                    img4 = bos4.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv4 = new ContentValues();
                    cv4.put("Icon_Name", "Poor");
                    cv4.put("Icon_value", img4);
                    cv4.put("Icon_Type", "Smiley");
                    //cv4.put("Area_Name", "");

                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv4);

                    Bitmap b8 = BitmapFactory.decodeResource(getResources(), R.drawable.hygeine);
                    ByteArrayOutputStream bos8 = new ByteArrayOutputStream();
                    b8.compress(Bitmap.CompressFormat.PNG, 100, bos8);
                    img8 = bos8.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv8 = new ContentValues();
                    cv8.put("Icon_Name", "Hygiene");
                    cv8.put("Icon_value", img8);
                    cv8.put("Icon_Type", "neg_feedback");
                    //cv8.put("Area_Name", "Cafeteria");

                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv8);

                    Bitmap b9 = BitmapFactory.decodeResource(getResources(), R.drawable.food);
                    ByteArrayOutputStream bos9 = new ByteArrayOutputStream();
                    b9.compress(Bitmap.CompressFormat.PNG, 100, bos9);
                    img9 = bos9.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv9 = new ContentValues();
                    cv9.put("Icon_Name", "Food");
                    cv9.put("Icon_value", img9);
                    cv9.put("Icon_Type", "neg_feedback");
                    //cv9.put("Area_Name", "Cafeteria");

                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv9);

                    Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.seating);
                    ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                    b2.compress(Bitmap.CompressFormat.PNG, 100, bos2);
                    img2 = bos2.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv2 = new ContentValues();
                    cv2.put("Icon_Name", "Seating");
                    cv2.put("Icon_value", img2);
                    cv2.put("Icon_Type", "neg_feedback");
//            cv2.put("Area_Name", "Cafeteria");

                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv2);

                    Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.service);
                    ByteArrayOutputStream bos3 = new ByteArrayOutputStream();
                    b3.compress(Bitmap.CompressFormat.PNG, 100, bos3);
                    img3 = bos3.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv3 = new ContentValues();
                    cv3.put("Icon_Name", "Service");
                    cv3.put("Icon_value", img3);
                    cv3.put("Icon_Type", "neg_feedback");
//            cv3.put("Area_Name", "Cafeteria");

                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv3);


                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ambience);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    img = bos.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv = new ContentValues();
                    cv.put("Icon_Name", "Ambience");
                    cv.put("Icon_value", img);
                    cv.put("Icon_Type", "neg_feedback");
//            cv.put("Area_Name", "Cafeteria");

                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv);


                    Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.others);
                    ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                    b1.compress(Bitmap.CompressFormat.PNG, 100, bos1);
                    img1 = bos1.toByteArray();
                    //myDB = new DatabaseHelper(this);

                    ContentValues cv1 = new ContentValues();
                    cv1.put("Icon_Name", "Others");
                    cv1.put("Icon_value", img1);
                    cv1.put("Icon_Type", "neg_feedback");
//            cv1.put("Area_Name", "Cafeteria");

                    sqLiteDatabase.insert("feedback_admin_icondetails", null, cv1);

                    //Toast.makeText(this, "inserted successfully", Toast.LENGTH_SHORT).show();

                    sqLiteDatabase.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("e = " + e);
                }

            }

            try {
                SQLiteDatabase db2 = dbh.getWritableDatabase();
                Cursor cursor1 = db2.rawQuery("Select Icon_List from store_setting ;", null);
                if (cursor1.moveToFirst()) {
                    do {
                        Icon_List = cursor1.getString(0);
                    } while (cursor1.moveToNext());
                    db2.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Icon_List = " + Icon_List);
            String[] icon = Icon_List.split("\\|");
            for (int i = 0; i < icon.length; i++) {
                iconList.add(icon[i]);
            }


            linearLayout = new LinearLayout(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(VERTICAL);
            //  linearLayout.setBackgroundColor(R.color.colorAccent);


            linearLayout1 = new LinearLayout(this);
            // new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);

            LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            mainLayoutParams.setMargins(10, 30, 10, 0);

            linearLayout1.setLayoutParams(mainLayoutParams);
            linearLayout1.setOrientation(VERTICAL);
            linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
//        linearLayout1.setPadding(10, 5, 2, 5);
//    totalfeedback = dbh.feedback_count();

            for (int i = 0; i < dbh.totalquestions_count(); i++) {
                if (dbh.isOrderIdpresent(totalfeedback)) {
                    break;
                } else {
                    totalfeedback++;
                }
            }
            try {

                SQLiteDatabase db1 = dbh.getWritableDatabase();
                // Cursor cursor1 = db1.rawQuery("Select * from feedback_adminquestions where ID =" + (totalfeedback + 1) + ";", null); //
                Cursor cursor1 = db1.rawQuery("Select * from feedback_adminquestions where Order_Id= '" + String.valueOf(totalfeedback) + "' ;", null);
                String feedback_question = "", order_id, a_id, icon_type;
                int id;
                if (cursor1.moveToFirst()) {

                    do {
                        id = cursor1.getInt(0);
                        a_id = cursor1.getString(1);
                        feedback_question = cursor1.getString(2);
//                    questions.add(feedback_question);

                        order_id = cursor1.getString(3);
//                   icon_type = cursor.getString(4);
                        linearLayout1.addView(textView(1, "Please Rate " + area));
                        linearLayout1.addView(textView(id, feedback_question));
                        linearLayout2 = new LinearLayout(this);
                        // new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                        buttonLayoutParams.setMargins(10, 100, 10, 0);
                        linearLayout2.setLayoutParams(buttonLayoutParams);
                        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout2.setGravity(Gravity.CENTER);

                        System.out.println("linear layout id = " + id);

                        linearLayout2.setId(id);
//
                            SQLiteDatabase db2 = dbh.getWritableDatabase();
//                    Cursor cursor2 = db2.rawQuery("Select * from feedback_admin_icondetails where ID IN(3,4,5,6) ;", null); //
                    Cursor cursor2 = db2.rawQuery("Select * from feedback_admin_icondetails where Icon_Type ='Smiley';", null);

                            String icon_name, icon_value;
                            int icon_id;
                            if (cursor2.moveToFirst()) {

                                do {
                                    icon_id = cursor2.getInt(0);
                                    icon_name = cursor2.getString(2);
//                            icon_value = cursor2.getString(3);
                                    linearLayout3 = new LinearLayout(this);
//                            new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
//                            linearLayout3.setLayoutParams(params);

                                    // ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
                                    params1.setMargins(10, 5, 10, 10);

                                    linearLayout3.setLayoutParams(params1);
                                    linearLayout3.setOrientation(VERTICAL);
                                    linearLayout3.setGravity(Gravity.CENTER);
//                                    for (int i = 0; i < iconList.size(); i++) {
//                                        System.out.print(iconList.get(i)+ i);
//                                        if (iconList.get(i).equals(icon_name)) {
                                    if (iconList.contains(icon_name)) {
                                            linearLayout3.addView(imageView(id, icon_name, icon_id));
                                            linearLayout3.addView(subtextView(icon_id, icon_name));
                                            linearLayout2.addView(linearLayout3);
                                        }
//                                    }
//                   icon_type = cursor.getString(4);
                                } while (cursor2.moveToNext());
                                db2.close();
                            }


                        int questionscount = dbh.totalquestions_count();
                        System.out.println("questionscount = " + questionscount);

                        linearLayout4 = new LinearLayout(this);
                        new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                        linearLayout4.setLayoutParams(params);


                        linearLayout4.setOrientation(LinearLayout.HORIZONTAL);
                        if (totalfeedback != 1) {
                            linearLayout4.addView(button("Prev"));
                        }
//                    if (totalfeedback != questionscount) {
//                        linearLayout4.addView(button("Next"));
//                    }

                        linearLayout1.addView(linearLayout2);
                        linearLayout1.addView(linearLayout4);
                        boolean isvisible = prefs.getBoolean("isVisible", true);
                        if (!isvisible) {
                            linearLayout4.setVisibility(View.GONE);
                        }
                        linearLayout4.setVisibility(View.GONE);//TODO comment later
                    } while (cursor1.moveToNext());
                    db1.close();

                }

                //


                linearLayout.addView(linearLayout1);

                setContentView(linearLayout);


            } catch (Exception e) {
                e.printStackTrace();
            }

            timer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void timer() {

        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.i("********feedback", "seconds remaining: " + millisUntilFinished / 1000);
//
                if (millisUntilFinished / 1000 == 5) {
                    snackbar = Snackbar
                            .make(linearLayout, "", snackbar.LENGTH_INDEFINITE)
                            .setAction("Wait", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                    countDownTimer.cancel();
                                    timer();
                                }

                            });
                    (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    snackbar.show();


                   /* Snackbar snackbar = Snackbar.make(linearLayout, "", Snackbar.LENGTH_LONG);
                    Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout)
                            snackbar.getView();
                    TextView textView = (TextView)
                            layout.findViewById(android.support.design.R.id.snackbarText);
                    textView.setVisibility(View.INVISIBLE);

                    View snackView = mInflater.inflate(R.layout.snckBar, null);
                    ImageView imageView = (ImageView) snackView.findViewById(R.id.image);
                    imageView.setImageBitmap(image);
                    TextView textViewTop = (TextView) snackView.findViewById(R.id.text);
                    textViewTop.setText(text);
                    textViewTop.setTextColor(Color.WHITE);
                    layout.setPadding(0,0,0,0);
                    layout.addView(snackView,0);
                    snackbar.show();*/

                }
                if (millisUntilFinished / 1000 < 6) {
                    snackbar.setText("Seconds Remaining for Timeout: " + millisUntilFinished / 1000);
                }

            }

            public void onFinish() {
                countDownTimer.cancel();
                Log.i("********feedbackfinish", "Timer Finished");
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("QuestNo", 1);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), SelectArea.class);
                startActivity(intent);
                finish();
            }
        }.start();


    }


    private void updateRating(int id) {


        ContentValues values = new ContentValues();
        values.put("Weightage", "yes");
        SQLiteDatabase sqLiteDatabase1 = dbh.getWritableDatabase();
        sqLiteDatabase1.update("feedback_adminquestions", values, "ID ='" + id + "' ", null);
        sqLiteDatabase1.close();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("QuestNo", totalfeedback + 1);
        editor.commit();
    }


    private TextView textView(int hint, String uname) {
        final TextView textView = new TextView(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        params1.setMargins(10, 60, 10, 10);
        textView.setLayoutParams(params1);
        textView.setId(hint);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(1, 30);
        textView.setText(uname);

        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        return textView;

    }

    private Button button(final String bname) {
        final Button button = new Button(this);
        button.setText(bname);
        button.setPadding(10, 10, 10, 10);
        button.setBackground(getDrawable(R.drawable.selectbackground_2));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                if (bname.equals("Prev")) {
//                button.setGravity(Gravity.LEFT);
                    Toast.makeText(FeedbackActivity.this, "Previous", Toast.LENGTH_SHORT).show();
                    editor.putInt("QuestNo", totalfeedback - 1);
                    editor.commit();
                    finish();
                    startActivity(getIntent());

                } else {
//                button.setGravity(Gravity.RIGHT);
                    Toast.makeText(FeedbackActivity.this, "Next", Toast.LENGTH_SHORT).show();
                    editor.putInt("QuestNo", totalfeedback + 1);
                    editor.commit();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        return button;

    }


    private TextView subtextView(int hint, String uname) {
        final TextView textView = new TextView(this);
        textView.setId(hint);
        textView.setTextSize(1, 20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(uname);

        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        return textView;

    }


    private ImageView imageView(final int id, final String strvalue, final int icon_id) {

        final String imgvalue = strvalue;
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
        final DatabaseHelper dbh = new DatabaseHelper(FeedbackActivity.this);
        Bitmap b = null;
        byte[] image_str = dbh.readDataIcon(strvalue);
        ;
        try {
            b = BitmapFactory.decodeByteArray(image_str, 0, image_str.length);

            imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 100, 100, true));
        } catch (Exception e) {

            e.printStackTrace();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(FeedbackActivity.this, "Image clicked" +, Toast.LENGTH_SHORT).show();
                System.out.println("imgvalue = " + imgvalue);

                updateRating(linearLayout2.getId());
//                handler.removeMessages(0);
//                handler.removeCallbacks(runnable);
                countDownTimer.cancel();

//                toast.cancel();
                int count = dbh.feedback_count();
                int totalquestionscount = dbh.totalquestions_count();
                dbh.insertFeedbackData(String.valueOf(rec_id), String.valueOf(id), String.valueOf(icon_id));
                System.out.print("********rec_count " + (rec_id) + "quest_id" + id + "icon_id" + icon_id);/**/
                //Last Question
                if (count == totalquestionscount) {
                    if (strvalue.equals("Poor") || strvalue.equals("Average")) {
                        // Toast.makeText(FeedbackActivity.this, "Negative", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(FeedbackActivity.this, WashroomNegativeFB.class);
                        intent.putExtra("area_name", "Cafeteria");
                        intent.putExtra("totalquestionscount", totalquestionscount);
                        intent.putExtra("current_question_id", id);
                        intent.putExtra("rec_id", rec_id);
                        startActivity(intent);
                        finish();
                    } else {
                        finish();
                        //Toast.makeText(FeedbackActivity.this, "Else " + count, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(FeedbackActivity.this, ThankuActivityScore.class);
                        i.putExtra("area", area);
                        startActivity(i);
                        finish();
                    }


                } else {
                    if (strvalue.equals("Poor") || strvalue.equals("Average")) {
                        // Toast.makeText(FeedbackActivity.this, "Negative", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(FeedbackActivity.this, WashroomNegativeFB.class);
                        intent.putExtra("area_name", "Cafeteria");
                        intent.putExtra("totalquestionscount", totalquestionscount);
                        intent.putExtra("current_question_id", id);

                        startActivity(intent);
                        finish();
                    } else {
                        finish();
                        startActivity(new Intent(FeedbackActivity.this, FeedbackActivity.class));
                    }

                }

            }
        });
        return imageView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedback_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.settings:
                String sentence;
                if (prefs.getBoolean("isVisible", true)) {
                    sentence = "Hide next & previous button";
                } else {
                    sentence = "Display next & previous button";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(sentence)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = prefs.edit();
//                Toast.makeText(getApplicationContext(), "settings Selected", Toast.LENGTH_LONG).show();
                                if (linearLayout4.getVisibility() == View.VISIBLE) {
                                    linearLayout4.setVisibility(View.GONE);
                                    editor.putBoolean("isVisible", false);
                                    editor.commit();
                                } else {
                                    linearLayout4.setVisibility(View.VISIBLE);
                                    editor.putBoolean("isVisible", true);
                                    editor.commit();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
//                                Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
//            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
    }
}
