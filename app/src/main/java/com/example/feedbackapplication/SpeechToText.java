package com.example.feedbackapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.JobIntentService;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedbackapplication.database.DatabaseHelper;
import com.sac.speech.Speech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class SpeechToText extends BaseActivity implements TextToSpeech.OnInitListener {

    /*ImageView imageView;*/
    ImageButton mic_button;
private final int REQ_CODE_SPEECH_INPUT = 100;
ArrayList<String> iconList = new ArrayList<>();
String Icon_List;
LinearLayout linearLayout34,linearLayout345,linearlayout_3,linearlayout_4,linearlayout_5,linearlayout_6,linearlayout_7,linearlayout_8;
ArrayList<String> iconNames = new ArrayList<>();
ArrayList<String> iconNameswithBad = new ArrayList<>(Arrays.asList("excellent", "very good", "good","average","poor","bad"));
ArrayList<String> iconwithNegative = new ArrayList<>(Arrays.asList("litter bin full", "smelly", "dirty basin","dirty floor","no tissue paper","others"));
ArrayList<String> iconwithNegativeCafe = new ArrayList<>(Arrays.asList("food", "seating", "service","ambience","hygiene","food others"));
ArrayList<String> iconNameswithNos = new ArrayList<>(Arrays.asList("5", "4", "3","2","1"));
ArrayList<String> iconNameswithAlphabets = new ArrayList<>(Arrays.asList("a", "b", "c","d","e"));
ArrayList<String> iconNameswithOptions = new ArrayList<>(Arrays.asList("option a", "option b", "option c","option e","option e"));
ArrayList<String> negativelists = new ArrayList<>();
ArrayList<Integer> IconIDs = new ArrayList<>();
ArrayList<Integer> IconIDswithAlphabets = new ArrayList<>(Arrays.asList(40,41,42,43,44));
ArrayList<Integer> IconIDswithOptions = new ArrayList<>(Arrays.asList(60,61,62,63,64));
ArrayList<Integer> IconIDswithBad = new ArrayList<>(Arrays.asList(10,11,12,13,14,15));
ArrayList<Integer> IconIDswithnos = new ArrayList<>(Arrays.asList(20,21,22,23,24));
ArrayList<Integer> IconIDsforneg = new ArrayList<>(Arrays.asList(30,31,32,33,34,35));
ArrayList<Integer> IconIDsfornegcafe = new ArrayList<>(Arrays.asList(50,51,52,53,54,55));
int IconID;
String iconName;
TextToSpeech tts;
String Feedback;
BroadcastRec broadcastRec = new BroadcastRec();
AlertDialog alert = null;
//String [] iconameswithbad = ["sdfd"];
final TextView[] myTextViews = new TextView[5]; // create an empty array;
final TextView[] negTextViews = new TextView[6]; // create an empty array;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_speech_to_text);
    
    linearLayout34 = (LinearLayout) findViewById(R.id.linearLayout34);
    linearLayout345 = (LinearLayout) findViewById(R.id.linearLayout345);
    linearlayout_3 = (LinearLayout) findViewById(R.id.linearlayout_3);
    linearlayout_4 = (LinearLayout) findViewById(R.id.linearlayout_4);
    linearlayout_5 = (LinearLayout) findViewById(R.id.linearlayout_5);
    linearlayout_6 = (LinearLayout) findViewById(R.id.linearlayout_6);
    linearlayout_7 = (LinearLayout) findViewById(R.id.linearlayout_7);
    linearlayout_8 = (LinearLayout) findViewById(R.id.linearlayout_8);
    
    
   // System.out.println("iconNameswithBad = " + iconNameswithBad);
    
    registerReceiver(broadcastReceiver, new IntentFilter("com.example.feedbackapplication.ACTION_FEEDBACK"));
    
    tts = new TextToSpeech(this, this);
 
  /*  try {
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
    
    String[] icon = Icon_List.split("\\|");
    for (int i = 0; i < icon.length; i++) {
        iconList.add(icon[i]);
    }
    
    for (int i = 0; i < iconList.size(); i++)
    {
        SQLiteDatabase db2 = dbh.getWritableDatabase();
        Cursor cursor2 = db2.rawQuery("Select * from feedback_admin_icondetails where Icon_Type ='Smiley' and Feedback_Name='"+iconList.get(i)+"';", null);
    
        String icon_name, icon_value,icon_name1;
    
        int icon_id;
        if (cursor2.moveToFirst()) {
    
            do {
                icon_id = cursor2.getInt(0);
                IconIDs.add(icon_id);
                
                icon_name = cursor2.getString(6);
                icon_name1 = cursor2.getString(6).toLowerCase();
                
                iconNames.add(icon_name1);
                linearLayout345.addView(subtextView(icon_id, icon_name));
              
            } while (cursor2.moveToNext());
        }
    }*/
    
    for (int j = 0; j < iconNameswithBad.size(); j++) {
        final TextView textView = new TextView(this);
        //textView.setId(j);
        textView.setId(IconIDswithBad.get(j));
        textView.setPadding(20,20,20,20);
        textView.setTextSize(1, 20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(iconNameswithBad.get(j).substring(0,1).toUpperCase() + iconNameswithBad.get(j).substring(1));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout_3.addView(textView);
        negTextViews[j] = textView;
    }
    
    for (int k = 0; k < iconNameswithNos.size(); k++) {
        final TextView textView = new TextView(this);
        //textView.setId(k);
        textView.setId(IconIDswithnos.get(k));
        textView.setPadding(20,20,20,20);
        textView.setTextSize(1, 20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(iconNameswithNos.get(k));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout_4.addView(textView);
        myTextViews[k] = textView;
    }
    
    for (int m = 0; m < iconNameswithAlphabets.size(); m++) {
        final TextView textView = new TextView(this);
        //textView.setId(k);
        textView.setId(IconIDswithAlphabets.get(m));
        textView.setPadding(20,20,20,20);
        textView.setTextSize(1, 20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(iconNameswithAlphabets.get(m).substring(0,1).toUpperCase() + iconNameswithAlphabets.get(m).substring(1));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout_6.addView(textView);
        myTextViews[m] = textView;
    }
    for (int m = 0; m < iconNameswithOptions.size(); m++) {
        final TextView textView = new TextView(this);
        //textView.setId(k);
        textView.setId(IconIDswithOptions.get(m));
        textView.setPadding(20,20,20,20);
        textView.setTextSize(1, 20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(iconNameswithOptions.get(m).substring(0,1).toUpperCase() + iconNameswithOptions.get(m).substring(1));
       // textView.setText(iconNameswithOptions.get(m).toUpperCase());
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout_8.addView(textView);
        myTextViews[m] = textView;
    }
    for (int l = 0; l < iconwithNegative.size(); l++) {
        final TextView textView = new TextView(this);
        //textView.setId(k);
        textView.setId(IconIDsforneg.get(l));
        textView.setPadding(20,20,20,20);
        textView.setTextSize(1, 20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(iconwithNegative.get(l).substring(0,1).toUpperCase() + iconwithNegative.get(l).substring(1));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout_5.addView(textView);
        negTextViews[l] = textView;
    }
    
    for (int l = 0; l < iconwithNegativeCafe.size(); l++) {
        final TextView textView = new TextView(this);
        //textView.setId(k);
        textView.setId(IconIDsfornegcafe.get(l));
        textView.setPadding(20,20,20,20);
        textView.setTextSize(1, 20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(iconwithNegativeCafe.get(l).substring(0,1).toUpperCase() + iconwithNegativeCafe.get(l).substring(1));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout_7.addView(textView);
        negTextViews[l] = textView;
    }
    
}


private TextView subtextView(int hint, String uname) {
    System.out.println("hint = " + hint);
    final TextView textView = new TextView(this);
    textView.setId(hint);
    textView.setPadding(20,20,20,20);
    textView.setTextSize(1, 20);
    textView.setTypeface(null, Typeface.BOLD);
    textView.setText(uname);
    textView.setGravity(Gravity.CENTER_HORIZONTAL);
    
    return textView;
    
}

@Override
protected void onDestroy() {
    super.onDestroy();
  /*  if (Speech.getInstance().isListening())
        Speech.getInstance().shutdown();*/
    unregisterReceiver(broadcastReceiver);
}

private void removeBackground() {
    
   /* for (int i = 0; i < iconNames.size(); i++){
    
        TextView textView = findViewById(IconIDs.get(i));
        textView.setBackgroundColor(getResources().getColor(R.color.white));
    }*/
    for (int j = 0; j < iconNameswithBad.size(); j++){
        
        TextView textView = findViewById(IconIDswithBad.get(j));
        textView.setBackgroundColor(getResources().getColor(R.color.white));
    }
    for (int k = 0; k < iconNameswithNos.size(); k++){
        
        TextView textView = findViewById(IconIDswithnos.get(k));
        textView.setBackgroundColor(getResources().getColor(R.color.white));
    }
    
    for (int m = 0; m < iconNameswithAlphabets.size(); m++){
        
        TextView textView = findViewById(IconIDswithAlphabets.get(m));
        textView.setBackgroundColor(getResources().getColor(R.color.white));
    }
    for (int m = 0; m < iconNameswithOptions.size(); m++){
        
        TextView textView = findViewById(IconIDswithOptions.get(m));
        textView.setBackgroundColor(getResources().getColor(R.color.white));
    }
    for (int l = 0; l < iconwithNegative.size(); l++){
        
        TextView textView = findViewById(IconIDsforneg.get(l));
        textView.setBackgroundColor(getResources().getColor(R.color.white));
    }
    for (int n = 0; n < iconwithNegativeCafe.size(); n++){
        
        TextView textView = findViewById(IconIDsfornegcafe.get(n));
        textView.setBackgroundColor(getResources().getColor(R.color.white));
    }
}


@Override
public void onInit(int status) {
    if (status == TextToSpeech.SUCCESS) {
        
        int result = tts.setLanguage(Locale.getDefault());
        
        if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("TTS", "This Language is not supported");
        } else {
            System.out.println("Lang supported");
        }
        
    } else {
        Log.e("TTS", "Initilization Failed!");
    }
}

@Override
public void onBackPressed() {
    super.onBackPressed();
    startActivity(new Intent(SpeechToText.this,FeedbackActivity.class));
}


BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("intent.getAction(): " + intent.getAction());
        if ("com.example.feedbackapplication.ACTION_FEEDBACK".equals(intent.getAction())) {
            Bundle b = intent.getExtras();
            String result = b.getString("value");
            System.out.println("result onReceive = " + result);
            AlertDialog.Builder builder;
            
            /*if (iconNames.contains(result)) {
                System.out.println("if condition  iconNames= ");
                int pos = iconNames.indexOf(result);
                int id = IconIDs.get(pos);
                
                System.out.println("pos = " + pos);
                System.out.println("id = " + id);
                
                removeBackground();
                
                TextView textView = findViewById(id);
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                
                if (result.equalsIgnoreCase("excellent") ||
                            result.equalsIgnoreCase("very good") ||
                            result.equalsIgnoreCase("good")) {
                    
                    textToSpeechNormal("Thank You");
                    
                } else {
                    textToSpeech(result);
                    
                    
                    builder = new AlertDialog.Builder(SpeechToText.this);
                    builder.setMessage("Are you sure you want to select " + result.substring(0, 1).toUpperCase() + result.substring(1) + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(SpeechToText.this, SecondActivity.class));
                                    
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeBackground();
                                    
                                    dialog.cancel();
                                }
                            });
                    alert = builder.create();
                    alert.show();
                    
                }
                
            }
            
           else */
            if (iconNameswithBad.contains(result)) {
                System.out.println("if condition iconNameswithBad = ");
                int pos = iconNameswithBad.indexOf(result.toLowerCase());
                int id = IconIDswithBad.get(pos);
    
                System.out.println("pos = " + pos);
                System.out.println("id = " + id);
    
                removeBackground();
    
                TextView textView = findViewById(id);
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    
                if (result.equalsIgnoreCase("excellent") ||
                            result.equalsIgnoreCase("very good") ||
                            result.equalsIgnoreCase("good")) {
        
                    textToSpeechNormal("Thank You");
        
                } else {
                    textToSpeech(result);
        
        
                    builder = new AlertDialog.Builder(SpeechToText.this);
                    builder.setMessage("Are you sure you want to select " + result.substring(0, 1).toUpperCase() + result.substring(1) + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(SpeechToText.this, SecondActivity.class));
                        
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeBackground();
                        
                                    dialog.cancel();
                                }
                            });
                    alert = builder.create();
                    alert.show();
        
                }
    
            }
            
           else if (iconNameswithNos.contains(result)) {
                System.out.println("if condition  iconNameswithNos= ");
                int pos = iconNameswithNos.indexOf(result);
                int id = IconIDswithnos.get(pos);
        
                System.out.println("pos = " + pos);
                System.out.println("id = " + id);
        
                removeBackground();
        
                TextView textView = findViewById(id);
                System.out.println("textView.getId() = " + textView.getText());
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        
                if (result.equalsIgnoreCase("5") ||
                            result.equalsIgnoreCase("4") ||
                            result.equalsIgnoreCase("3")) {
            
                    textToSpeechNormal("Thank You");
            
                } else {
                    textToSpeech(result);
            
            
                    builder = new AlertDialog.Builder(SpeechToText.this);
                    builder.setMessage("Are you sure you want to select " + result.substring(0, 1).toUpperCase() + result.substring(1) + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(SpeechToText.this, SecondActivity.class));
                            
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeBackground();
                            
                                    dialog.cancel();
                                }
                            });
                    alert = builder.create();
                    alert.show();
            
                }
        
            }

            else if (iconNameswithAlphabets.contains(result)) {
                System.out.println("if condition  iconNameswithAlphabets= ");
                int pos = iconNameswithAlphabets.indexOf(result);
                int id = IconIDswithAlphabets.get(pos);
    
                System.out.println("pos = " + pos);
                System.out.println("id = " + id);
    
                removeBackground();
    
                TextView textView = findViewById(id);
                System.out.println("textView.getId() = " + textView.getText());
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    
                if (result.equalsIgnoreCase("a") ||
                            result.equalsIgnoreCase("b") ||
                            result.equalsIgnoreCase("c")) {
        
                    textToSpeechNormal("Thank You");
        
                } else {
                    textToSpeech(result);
        
        
                    builder = new AlertDialog.Builder(SpeechToText.this);
                    builder.setMessage("Are you sure you want to select " + result.substring(0, 1).toUpperCase() + result.substring(1) + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(SpeechToText.this, SecondActivity.class));
                        
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeBackground();
                        
                                    dialog.cancel();
                                }
                            });
                    alert = builder.create();
                    alert.show();
        
                }
    
            }

            else if (iconNameswithOptions.contains(result)) {
                System.out.println("if condition  iconNameswithAlphabets= ");
                int pos = iconNameswithOptions.indexOf(result);
                int id = IconIDswithOptions.get(pos);
    
                System.out.println("pos = " + pos);
                System.out.println("id = " + id);
    
                removeBackground();
    
                TextView textView = findViewById(id);
                System.out.println("textView.getId() = " + textView.getText());
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    
                if (result.equalsIgnoreCase("option a") ||
                            result.equalsIgnoreCase("option b") ||
                            result.equalsIgnoreCase("option c")) {
        
                    textToSpeechNormal("Thank You");
        
                } else {
                    textToSpeech(result);
        
        
                    builder = new AlertDialog.Builder(SpeechToText.this);
                    builder.setMessage("Are you sure you want to select " + result.substring(0, 1).toUpperCase() + result.substring(1) + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(SpeechToText.this, SecondActivity.class));
                        
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeBackground();
                        
                                    dialog.cancel();
                                }
                            });
                    alert = builder.create();
                    alert.show();
        
                }
    
            }
            
            else if (iconwithNegative.contains(result)) {
                System.out.println("if condition  iconNameswithNos= ");
                int pos = iconwithNegative.indexOf(result);
                int id = IconIDsforneg.get(pos);
    
                System.out.println("pos = " + pos);
                System.out.println("id = " + id);
    
                negativelists.remove(result);
                removeBackground();
    
                TextView textView = findViewById(id);
                System.out.println("textView.getId() = " + textView.getText());
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                
                negativelists.add(result);
    
                System.out.println("negativelists = " + negativelists);
                textToSpeechNormal("You have selected "+result.substring(0, 1).toUpperCase() + result.substring(1));

             /*   if (result.equalsIgnoreCase("5") ||
                            result.equalsIgnoreCase("4") ||
                            result.equalsIgnoreCase("3")) {
        
                    textToSpeechNormal("Thank You");
        
                } else {
                    textToSpeech(result);
        
        
                    builder = new AlertDialog.Builder(SpeechToText.this);
                    builder.setMessage("Are you sure you want to select " + result.substring(0, 1).toUpperCase() + result.substring(1) + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(SpeechToText.this, SecondActivity.class));
                        
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeBackground();
                        
                                    dialog.cancel();
                                }
                            });
                    alert = builder.create();
                    alert.show();
        
                }*/
    
            }

            else if (iconwithNegativeCafe.contains(result)) {
                System.out.println("if condition iconwithNegativeCafe= ");
                int pos = iconwithNegativeCafe.indexOf(result);
                int id = IconIDsfornegcafe.get(pos);
    
                System.out.println("pos = " + pos);
                System.out.println("id = " + id);
    
                removeBackground();
    
                TextView textView = findViewById(id);
                System.out.println("textView.getId() = " + textView.getText());
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    
                textToSpeechNormal("You have selected "+result.substring(0, 1).toUpperCase() + result.substring(1));

             /*   if (result.equalsIgnoreCase("5") ||
                            result.equalsIgnoreCase("4") ||
                            result.equalsIgnoreCase("3")) {
        
                    textToSpeechNormal("Thank You");
        
                } else {
                    textToSpeech(result);
        
        
                    builder = new AlertDialog.Builder(SpeechToText.this);
                    builder.setMessage("Are you sure you want to select " + result.substring(0, 1).toUpperCase() + result.substring(1) + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(SpeechToText.this, SecondActivity.class));
                        
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeBackground();
                        
                                    dialog.cancel();
                                }
                            });
                    alert = builder.create();
                    alert.show();
        
                }*/
    
            }

            else {
                System.out.println("else condition");
                if (alert != null && alert.isShowing()) {
                    System.out.println("Alert is showing..");
                    
                    if (result.equalsIgnoreCase("Yes")) {
                        alert.dismiss();
                        startActivity(new Intent(SpeechToText.this, SecondActivity.class));
                    } else if (result.equalsIgnoreCase("no")) {
                        alert.dismiss();
                        removeBackground();
                    }
                    
                } else {
                    System.out.println("Alert is not showing");
                }
                
            }
            
        }
        
        
    }
};


private void muteBeepSoundOfRecorder() {
    AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    if (amanager != null) {
        amanager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
        amanager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
        amanager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
        amanager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
        amanager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
    }
}

private void unmuteBeepSoundOfRecorder() {
    AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    if (amanager != null) {
        amanager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
        amanager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
        amanager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
        amanager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
        amanager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
    }
}

private void textToSpeechNormal(String result) {
    
    unmuteBeepSoundOfRecorder();
    
    int speechStatus;
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        tts.setSpeechRate(1);
        tts.setPitch(0.4f);
        speechStatus = tts.speak(result, TextToSpeech.QUEUE_ADD, null, null);
        
    } else {
        tts.setSpeechRate(1);
        tts.setPitch(0.4f);
        speechStatus = tts.speak(result, TextToSpeech.QUEUE_ADD, null, null);
        
    }
    
    if (speechStatus == TextToSpeech.ERROR) {
        Log.e("TTS", "Error in converting Text to Speech4!");
    }
    
}

private void textToSpeech(String result) {
    
    unmuteBeepSoundOfRecorder();
    
    int speechStatus;
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        tts.setSpeechRate(1f);
        tts.setPitch(0.4f);
        speechStatus = tts.speak("Are you sure you want to select " + result + " ?", TextToSpeech.QUEUE_ADD, null, null);
        
    } else {
        tts.setSpeechRate(1f);
        tts.setPitch(0.4f);
        speechStatus = tts.speak("Are you sure you want to select " + result + " ?", TextToSpeech.QUEUE_ADD, null, null);
        
    }
    
    if (speechStatus == TextToSpeech.ERROR) {
        Log.e("TTS", "Error in converting Text to Speech4!");
    }
    
}


}
