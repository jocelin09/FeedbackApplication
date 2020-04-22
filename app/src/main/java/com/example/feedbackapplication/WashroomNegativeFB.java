package com.example.feedbackapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedbackapplication.database.DatabaseHelper;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.HORIZONTAL;

public class WashroomNegativeFB extends AppCompatActivity {

String area_name = "Cafeteria";
int totalquestionscount,current_question_id;

DatabaseHelper dbh;
SQLiteDatabase sqLiteDatabase;
ArrayList<String> negative_lists = new ArrayList<String>();
boolean food = false, seating = false, service = false, hygiene= false, ambience = false,others = false;
boolean overallratingdone = false;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_feedback);
    
   // area_name = getIntent().getStringExtra("area_name");
    totalquestionscount = getIntent().getIntExtra("totalquestionscount",0);
    current_question_id = getIntent().getIntExtra("current_question_id",0);
    
    dbh = new DatabaseHelper(this);
    sqLiteDatabase = dbh.getWritableDatabase();
    
    
    System.out.println("totalquestionscount = " + totalquestionscount + " current_question_id = " +current_question_id);
    
    LinearLayout main_Layout = (LinearLayout) findViewById(R.id.main_layout); //vertical
    LinearLayout first_layout = (LinearLayout) findViewById(R.id.first_layout); //vertical
    //TextViews
    first_layout.addView(textView(area_name));
    first_layout.addView(subtextView("What went wrong?"));
    
    LinearLayout second_layout = (LinearLayout) findViewById(R.id.second_layout); //vertical
 
    final ArrayList<String> neg_iconnames = new ArrayList<>();
    try {
        String query = "Select * from feedback_admin_icondetails where ID IN(5,6,7,8,9,10) ";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext()) {
            neg_iconnames.add(cursor.getString(cursor.getColumnIndex("Icon_Name")));
        }
    }catch(Exception ex){
       // Log.e(TAG,"Erro in geting friends "+ex.toString());
    }
    
//    int number = Math.round(neg_iconnames.size()/2);
//    System.out.println("number = " + number);
    
    
    LinearLayout sub1_secondlayout = new LinearLayout(this);
    LinearLayout.LayoutParams params_sub1 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT, 0.5f);
    sub1_secondlayout.setLayoutParams(params_sub1);
    sub1_secondlayout.setWeightSum(neg_iconnames.size()/2f);
    sub1_secondlayout.setOrientation(HORIZONTAL);
    sub1_secondlayout.setGravity(Gravity.CENTER_HORIZONTAL);
    
    for (int i = 0; i < neg_iconnames.size()/2; i++)
    
    {
        final String icon_name = neg_iconnames.get(i);
        System.out.println("icon_name = " + icon_name);
        final LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1f);
        params2.setMargins(10, 10, 10, 10);
        linearLayout.setLayoutParams(params2);
        linearLayout.setPadding(10,10,10,10);
       // linearLayout.setBackground(getDrawable(R.drawable.selectbackground_2));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(HORIZONTAL);
    
        final LinearLayout linearLayout1 = new LinearLayout(this);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(400, 400);
        linearLayout1.setLayoutParams(params3);
       // linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.setOrientation(HORIZONTAL);
        
        linearLayout.addView(linearLayout1(i+1,neg_iconnames.get(i)));
        
        if (linearLayout.getParent() != null) {
            ((ViewGroup) linearLayout.getParent()).removeView(linearLayout); // <- fix
        }
    
        sub1_secondlayout.addView(linearLayout);
    }
    
    LinearLayout sub2_secondlayout = new LinearLayout(this);
    LinearLayout.LayoutParams params_sub2 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT, 0.5f);
    sub2_secondlayout.setLayoutParams(params_sub2);
    sub2_secondlayout.setWeightSum(neg_iconnames.size()/2f);
    sub2_secondlayout.setOrientation(HORIZONTAL);
    
    
    for (int i = neg_iconnames.size()/2; i < neg_iconnames.size(); i++)
    
    {
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1f);
        params2.setMargins(10, 10, 10, 10);
        linearLayout.setLayoutParams(params2);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setOrientation(HORIZONTAL);
       // linearLayout.setBackground(getDrawable(R.drawable.selectbackground_2));
        
        linearLayout.addView(linearLayout1(i+1,neg_iconnames.get(i)));
        
        if (linearLayout.getParent() != null) {
            ((ViewGroup) linearLayout.getParent()).removeView(linearLayout); // <- fix
        }
        
        sub2_secondlayout.addView(linearLayout);
    }
    
    
    if (sub1_secondlayout.getParent() != null) {
        ((ViewGroup) sub1_secondlayout.getParent()).removeView(sub1_secondlayout); // <- fix
    }
    if (sub2_secondlayout.getParent() != null) {
        ((ViewGroup) sub2_secondlayout.getParent()).removeView(sub2_secondlayout); // <- fix
    }
    
    second_layout.addView(sub1_secondlayout);
    second_layout.addView(sub2_secondlayout);
    
    LinearLayout third_layout = (LinearLayout) findViewById(R.id.third_layout); //vertical
    
    third_layout.addView(button(R.id.submit,"Submit"));
    third_layout.addView(button(R.id.cancel,"Cancel"));
    
    
    if (first_layout.getParent() != null) {
        ((ViewGroup) first_layout.getParent()).removeView(first_layout); // <- fix
    }
    if (second_layout.getParent() != null) {
        ((ViewGroup) second_layout.getParent()).removeView(second_layout); // <- fix
    }
    if (third_layout.getParent() != null) {
        ((ViewGroup) third_layout.getParent()).removeView(third_layout); // <- fix
    }
    
    main_Layout.addView(first_layout);
    main_Layout.addView(second_layout);
    main_Layout.addView(third_layout);
}

private TextView textView(String uname) {
    final TextView textView = new TextView(this);
    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
    textView.setLayoutParams(params1);
    textView.setTextSize(1, 30);
    textView.setGravity(Gravity.CENTER_HORIZONTAL);
    textView.setTypeface(null, Typeface.BOLD);
    textView.setText(uname);
    
    return textView;
    
}

@SuppressLint("ResourceAsColor")
private TextView button(int id, String uname) {
    final Button button = new Button(this);
    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    params1.setMargins(10,0,10,0);
    button.setLayoutParams(params1);
    button.setId(id);
    button.setTextSize(1, 20);
    button.setTextColor(Color.WHITE);
    button.setBackgroundColor(R.color.colorPrimary);
    button.setGravity(Gravity.CENTER_HORIZONTAL);
    button.setTypeface(null, Typeface.NORMAL);
    button.setText(uname);
    
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        if (button.getId() == R.id.submit){
            if (negative_lists.size() != 0) {
                Toast.makeText(WashroomNegativeFB.this, "Submited " +negative_lists, Toast.LENGTH_LONG).show();
                if (current_question_id == totalquestionscount)
                {
                    finish();
                    startActivity(new Intent(WashroomNegativeFB.this, ThankuActivityScore.class));
                }
                else
                {
                    finish();
                    startActivity(new Intent(WashroomNegativeFB.this, FeedbackActivity.class));
                }
                
            } else {
                Toast.makeText(getApplicationContext(), "Please select atleast one value", Toast.LENGTH_SHORT).show();
            }
        }
        else if(button.getId() == R.id.cancel)
        {
            if (current_question_id == totalquestionscount)
        {
            finish();
            startActivity(new Intent(WashroomNegativeFB.this, ThankuActivityScore.class));
        }
        else {
                finish();
                startActivity(new Intent(WashroomNegativeFB.this, FeedbackActivity.class));
            }
        }
        }
    });
    
    return button;
    
}

private TextView subtextView(String uname) {
    final TextView textView = new TextView(this);
    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
    textView.setLayoutParams(params1);
    textView.setTextSize(1, 25);
    textView.setGravity(Gravity.CENTER_HORIZONTAL);
    textView.setTypeface(null, Typeface.NORMAL);
    textView.setText(uname);
    
    return textView;
    
}

private LinearLayout linearLayout1(int id,final String strvalue) {
    
    final LinearLayout linearLayout1 = new LinearLayout(this);
    LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(200, 200);
    linearLayout1.setLayoutParams(params3);
    // linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
    linearLayout1.setGravity(Gravity.CENTER);
    linearLayout1.setOrientation(HORIZONTAL);
    
    final ImageView imageView = new ImageView(this);
    imageView.setId(id);
    imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(150, 150));
    final DatabaseHelper dbh = new DatabaseHelper(WashroomNegativeFB.this);
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
            if (strvalue.equals("Food"))
            {
                if (food == false){
                    linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
                    negative_lists.add(strvalue);
                    food = true;
                }else {
                    linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                    negative_lists.remove(strvalue);
                    food = false;
                }
            }
            else if (strvalue.equals("Seating"))
            {
                if (seating == false){
                    linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
                    negative_lists.add(strvalue);
                    seating = true;
                }else {
                    linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                    negative_lists.remove(strvalue);
                    seating = false;
                }
            }
            if (strvalue.equals("Service"))
            {
                if (service == false){
                    linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
                    negative_lists.add(strvalue);
                    service = true;
                }else {
                    linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                    negative_lists.remove(strvalue);
                    service = false;
                }
            }
            else if (strvalue.equals("Hygiene"))
            {
                if (hygiene == false){
                    linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
                    negative_lists.add(strvalue);
                    hygiene = true;
                }else {
                    linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                    negative_lists.remove(strvalue);
                    hygiene = false;
                }
            }
            else if (strvalue.equals("Ambience"))
            {
                if (ambience == false){
                    linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
                    negative_lists.add(strvalue);
                    ambience = true;
                }else {
                    linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                    negative_lists.remove(strvalue);
                    ambience = false;
                }
            }
            else if (strvalue.equals("Others"))
            {
                if (others == false){
                    linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
                    negative_lists.add(strvalue);
                    others = true;
                }else {
                    linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                    negative_lists.remove(strvalue);
                    others = false;
                }
            }
            System.out.println("negative_lists = " + negative_lists);
            
        }
    });
    
    linearLayout1.addView(imageView);
    
    return linearLayout1;
}


@Override
public void onBackPressed() {
    super.onBackPressed();
    if (current_question_id == totalquestionscount)
    {
        finish();
        startActivity(new Intent(WashroomNegativeFB.this, ThankuActivityScore.class));
    }
    else
    {
        finish();
        startActivity(new Intent(WashroomNegativeFB.this, FeedbackActivity.class));
    }
}
}
