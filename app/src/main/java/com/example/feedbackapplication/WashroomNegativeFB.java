package com.example.feedbackapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
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

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.HORIZONTAL;

public class WashroomNegativeFB extends BaseActivity {
    final Handler handler = new Handler();
    Runnable runnable;
    String area_name = "";//Cafeteria
    int totalquestionscount, current_question_id, rec_id;
    LinearLayout main_Layout;
    ScrollView scrollView;
    //DatabaseHelper dbh;
//SQLiteDatabase sqLiteDatabase;
    ArrayList<String> negative_lists = new ArrayList<String>();
    boolean food = false, seating = false, service = false, hygiene = false, ambience = false, others = false;
    boolean overallratingdone = false;
    Snackbar snackbar;
    String q_id;
    public CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        countDownTimer=null;
        // area_name = getIntent().getStringExtra("area_name");
        totalquestionscount = getIntent().getIntExtra("totalquestionscount", 0);
        current_question_id = getIntent().getIntExtra("current_question_id", 0);
        rec_id = getIntent().getIntExtra("rec_id", 0);
        q_id=getIntent().getStringExtra("q_id");

//    dbh = new DatabaseHelper(this);
//    sqLiteDatabase = dbh.getWritableDatabase();


        System.out.println("totalquestionscount = " + totalquestionscount + " current_question_id = " + current_question_id);

        scrollView = (ScrollView) findViewById(R.id.scroll);
        main_Layout = (LinearLayout) findViewById(R.id.main_layout); //vertical
        LinearLayout first_layout = (LinearLayout) findViewById(R.id.first_layout); //vertical
        //TextViews
        first_layout.addView(textView(area_name));
//        first_layout.addView(subtextView("What went wrong?"));
        try {
            SQLiteDatabase db2 = dbh.getWritableDatabase();
            Cursor cursor1 = db2.rawQuery("Select Feedback_Sub_Question from feedback_adminsubquestions where Feedback_Id= '" + q_id+ "' ;", null);//String.valueOf(current_question_id)
            if (cursor1.moveToFirst()) {
                do {
                    String Feedback_Sub_Question = cursor1.getString(0);
                    first_layout.addView(subtextView(Feedback_Sub_Question));
                } while (cursor1.moveToNext());
                db2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayout second_layout = (LinearLayout) findViewById(R.id.second_layout); //vertical

        final ArrayList<String> neg_iconnames = new ArrayList<>();
        try {
            SQLiteDatabase db1 = dbh.getWritableDatabase();
            String query = "Select * from feedback_admin_icondetails where Icon_Type ='neg_feedback'";//ID IN(7,8,9,10,11,12) "
            Cursor cursor = db1.rawQuery(query, null);
            while (cursor.moveToNext()) {
                neg_iconnames.add(cursor.getString(cursor.getColumnIndex("Feedback_Name")));
            }
            db1.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Log.e(TAG,"Erro in geting friends "+ex.toString());
        }

//    int number = Math.round(neg_iconnames.size()/2);
//    System.out.println("number = " + number);
   // [Faulty Equipments, Food, Seating, Service, Ambience, Smelly, Others, Leakage, Dirty Floor, Wet Floor, No Tissue Paper , Hygiene]
        System.out.println("neg_iconnames = " + neg_iconnames);
    
        neg_iconnames.remove("Food");
        neg_iconnames.remove("Seating");
        neg_iconnames.remove("Service");
        neg_iconnames.remove("Ambience");
        neg_iconnames.remove("Wet Floor");
        neg_iconnames.remove("Hygiene");
        
        LinearLayout sub1_secondlayout = new LinearLayout(this);
        LinearLayout.LayoutParams params_sub1 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT, 0.5f);
        sub1_secondlayout.setLayoutParams(params_sub1);
        sub1_secondlayout.setWeightSum(neg_iconnames.size() / 2f);
        sub1_secondlayout.setOrientation(HORIZONTAL);
        sub1_secondlayout.setGravity(Gravity.CENTER_HORIZONTAL);

        for (int i = 0; i < neg_iconnames.size() / 2; i++) {
            final String icon_name = neg_iconnames.get(i);
            System.out.println("icon_name = " + icon_name);
            final LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1f);
            params2.setMargins(10, 10, 10, 10);
            linearLayout.setLayoutParams(params2);
            linearLayout.setPadding(10, 10, 10, 10);
            // linearLayout.setBackground(getDrawable(R.drawable.selectbackground_2));
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(HORIZONTAL);

            final LinearLayout linearLayout1 = new LinearLayout(this);
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(400, 400);
            linearLayout1.setLayoutParams(params3);
            // linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
            linearLayout1.setGravity(Gravity.CENTER);
            linearLayout1.setOrientation(HORIZONTAL);

            linearLayout.addView(linearLayout1(i + 1, neg_iconnames.get(i)));

            if (linearLayout.getParent() != null) {
                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout); // <- fix
            }

            sub1_secondlayout.addView(linearLayout);
        }

        LinearLayout sub2_secondlayout = new LinearLayout(this);
        LinearLayout.LayoutParams params_sub2 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT, 0.5f);
        sub2_secondlayout.setLayoutParams(params_sub2);
        sub2_secondlayout.setWeightSum(neg_iconnames.size() / 2f);
        sub2_secondlayout.setOrientation(HORIZONTAL);


        for (int i = neg_iconnames.size() / 2; i < neg_iconnames.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1f);
            params2.setMargins(10, 10, 10, 10);
            linearLayout.setLayoutParams(params2);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.setOrientation(HORIZONTAL);
            // linearLayout.setBackground(getDrawable(R.drawable.selectbackground_2));

            linearLayout.addView(linearLayout1(i + 1, neg_iconnames.get(i)));

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

        third_layout.addView(button(R.id.cancel, "Cancel"));
        third_layout.addView(button(R.id.submit, "Submit"));

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
        timer();


    }

    public void timer() {
       /* runnable=new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(this);
                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("QuestNo", current_question_id);
                editor.commit();
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable, 20000);*/

        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.i("********negfeedback", "seconds remaining: " + millisUntilFinished / 1000);
//                toast=Toast.makeText(WashroomNegativeFB.this, "seconds remaining for timeout: " + millisUntilFinished / 1000, Toast.LENGTH_SHORT);
//                toast.show();

                if (millisUntilFinished / 1000 == 5) {
                    snackbar = Snackbar
                            .make(scrollView, "", snackbar.LENGTH_INDEFINITE)
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

                }
                if (millisUntilFinished / 1000 < 6) {
                    snackbar.setText("seconds remaining for timeout: " + millisUntilFinished / 1000);
                }
            }

            public void onFinish() {
                Log.i("********negfeedbackfinish", "Timer Finished");
                countDownTimer.cancel();
                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("QuestNo", 1);
                editor.commit();
                startActivity(intent);
                finish();
            }
        }.start();
    }

    private TextView textView(String uname) {
        final TextView textView = new TextView(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        textView.setLayoutParams(params1);
        textView.setTextSize(1, 35);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(uname);

        return textView;

    }

    @SuppressLint("ResourceAsColor")
    private TextView button(int id, String uname) {
        final Button button = new Button(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(250, WRAP_CONTENT);
        params1.setMargins(16, 8, 16, 8);
        button.setLayoutParams(params1);
        button.setId(id);
        button.setTextSize(1, 17);
        button.setTextColor(Color.WHITE);
        button.setLetterSpacing(0.2f);
        button.setBackground(getDrawable(R.drawable.red_style));
        button.setTypeface(null, Typeface.NORMAL);
        button.setText(uname);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getId() == R.id.submit) {
                    countDownTimer.cancel();
//                    toast.cancel();
//                    handler.removeCallbacks(runnable);
                    if (negative_lists.size() != 0) {
                        Toast.makeText(WashroomNegativeFB.this, "Submited ", Toast.LENGTH_LONG).show();
                        String neg_feedback = "";
                        for (int i = 0; i < negative_lists.size(); i++) {
                            if ("".equals(neg_feedback)) {
                                neg_feedback = negative_lists.get(i);
                            } else {
                                neg_feedback = neg_feedback + "," + negative_lists.get(i);
                            }
                        }


                        dbh.insertsubFeedbackData(String.valueOf(rec_id), String.valueOf(current_question_id), String.valueOf(current_question_id), neg_feedback);
                        System.out.println("totalquestionscount = " + rec_id + "," + String.valueOf(current_question_id) + "," + String.valueOf(current_question_id) + "," + neg_feedback);

                        if (current_question_id == totalquestionscount) {
                            finish();
                            startActivity(new Intent(WashroomNegativeFB.this, ThankuActivityScore.class));
                        } else {
                            finish();
                            startActivity(new Intent(WashroomNegativeFB.this, FeedbackActivity.class));
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Please select atleast one value", Toast.LENGTH_SHORT).show();
                    }
                } else if (button.getId() == R.id.cancel) {
                    countDownTimer.cancel();
//                    toast.cancel();
//                    if (current_question_id == totalquestionscount) {
//                        finish();
//                        startActivity(new Intent(WashroomNegativeFB.this, ThankuActivityScore.class));
//                    } else {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("QuestNo", current_question_id);
                    editor.commit();
                        finish();
                        startActivity(new Intent(WashroomNegativeFB.this, FeedbackActivity.class));
//                    }
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

    private LinearLayout linearLayout1(int id, final String strvalue) {

        final LinearLayout linearLayout1 = new LinearLayout(this);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(350, 350);
        linearLayout1.setLayoutParams(params3);
        // linearLayout1.setBackground(getDrawable(R.drawable.selectbackground_2));
        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.setOrientation(HORIZONTAL);


        final ImageView imageView = new ImageView(this);
        imageView.setId(id);
        imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(300, 300));
        final DatabaseHelper dbh = new DatabaseHelper(WashroomNegativeFB.this);
        Bitmap b = null;
        //Faulty Equipments,No Toilet Paper ,Floor Not Clean,Leakage,Others,Smelly
        if (strvalue.equals("Faulty Equipments")) {
            imageView.setBackgroundResource(R.drawable.litter_bin);
        } else if (strvalue.equals("No Tissue Paper ")) {
            imageView.setBackgroundResource(R.drawable.notissue);
        } else if (strvalue.equals("Dirty Floor")) {
            imageView.setBackgroundResource(R.drawable.dirty_floor);
        } else if (strvalue.equals("Leakage")) {
            imageView.setBackgroundResource(R.drawable.dirty_basin);
        } else if (strvalue.equals("Smelly")) {
            imageView.setBackgroundResource(R.drawable.smelly);
        } else if (strvalue.equals("Others")) {
            imageView.setBackgroundResource(R.drawable.others);
        }
//        byte[] image_str = dbh.readDataIcon(strvalue);
//
//        try {
//            b = BitmapFactory.decodeByteArray(image_str, 0, image_str.length);
//            imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 100, 100, true));
//        } catch (Exception e) {
//}
//            e.printStackTrace();
//        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strvalue.equals("Faulty Equipments")) {
                    if (food == false) {
                        linearLayout1.setBackground(getDrawable(R.drawable.selected_item_green));
                        negative_lists.add(strvalue);
                        food = true;
                    } else {
                        linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                        negative_lists.remove(strvalue);
                        food = false;
                    }
                } else if (strvalue.equals("No Tissue Paper ")) {
                    if (seating == false) {
                        linearLayout1.setBackground(getDrawable(R.drawable.selected_item_green));
                        negative_lists.add(strvalue);
                        seating = true;
                    } else {
                        linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                        negative_lists.remove(strvalue);
                        seating = false;
                    }
                }
                if (strvalue.equals("Dirty Floor")) {
                    if (service == false) {
                        linearLayout1.setBackground(getDrawable(R.drawable.selected_item_green));
                        negative_lists.add(strvalue);
                        service = true;
                    } else {
                        linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                        negative_lists.remove(strvalue);
                        service = false;
                    }
                } else if (strvalue.equals("Leakage")) {
                    if (hygiene == false) {
                        linearLayout1.setBackground(getDrawable(R.drawable.selected_item_green));
                        negative_lists.add(strvalue);
                        hygiene = true;
                    } else {
                        linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                        negative_lists.remove(strvalue);
                        hygiene = false;
                    }
                } else if (strvalue.equals("Smelly")) {
                    if (ambience == false) {
                        linearLayout1.setBackground(getDrawable(R.drawable.selected_item_green));
                        negative_lists.add(strvalue);
                        ambience = true;
                    } else {
                        linearLayout1.setBackground(getDrawable(R.drawable.backgroundshape));
                        negative_lists.remove(strvalue);
                        ambience = false;
                    }
                } else if (strvalue.equals("Others")) {
                    if (others == false) {
                        linearLayout1.setBackground(getDrawable(R.drawable.selected_item_green));
                        negative_lists.add(strvalue);
                        others = true;
                    } else {
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
        countDownTimer.cancel();
//        toast.cancel();
        if (current_question_id == totalquestionscount) {
            finish();
            startActivity(new Intent(WashroomNegativeFB.this, ThankuActivityScore.class));
        } else {
            finish();
            startActivity(new Intent(WashroomNegativeFB.this, FeedbackActivity.class));
        }
    }
}
