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
import java.util.HashMap;
import java.util.Locale;

public class SpeechToText extends BaseActivity implements TextToSpeech.OnInitListener {

    /*ImageView imageView;*/
    ImageButton mic_button;
private final int REQ_CODE_SPEECH_INPUT = 100;
ArrayList<String> iconList = new ArrayList<>();
String Icon_List;
LinearLayout linearLayout34,linearLayout345;
ArrayList<String> iconNames = new ArrayList<>();
ArrayList<Integer> IconIDs = new ArrayList<>();
int IconID;
String iconName;
TextToSpeech tts;
String Feedback;
BroadcastRec broadcastRec = new BroadcastRec();
AlertDialog alert = null;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_speech_to_text);
    
  //  mic_button = (ImageButton) findViewById(R.id.mic_button);
    linearLayout34 = (LinearLayout) findViewById(R.id.linearLayout34);
    linearLayout345 = (LinearLayout) findViewById(R.id.linearLayout345);
    
/*    IntentFilter intentFilter = new IntentFilter("com.example.feedbackapplication.ACTION_BROADCAST");
    registerReceiver(broadcastRec, intentFilter);
    
    enableAutoStart();
    
    Intent intent = new Intent(SpeechToText.this, JobIntentService.class);
    JobIntentServiceClass.enqueueWork(this, intent);
    */
    registerReceiver(broadcastReceiver, new IntentFilter("com.example.feedbackapplication.ACTION_FEEDBACK"));
    
    tts = new TextToSpeech(this, this);
    
   /* mic_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        promptSpeech();
        }
    });*/
    
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
  //  System.out.println("Icon_List = " + Icon_List);
    
    String[] icon = Icon_List.split("\\|");
    for (int i = 0; i < icon.length; i++) {
        iconList.add(icon[i]);
    }
    //System.out.println("iconList = " + iconList.size());
    
    //if (iconList.size() !=0)
    for (int i = 0; i < iconList.size(); i++)
    {
        SQLiteDatabase db2 = dbh.getWritableDatabase();
        Cursor cursor2 = db2.rawQuery("Select * from feedback_admin_icondetails where Icon_Type ='Smiley' and Feedback_Name='"+iconList.get(i)+"';", null);
    
        String icon_name, icon_value,icon_name1;
    
        int icon_id;
        if (cursor2.moveToFirst()) {
    
            do {
                icon_id = cursor2.getInt(0);
               // IconID = icon_id;
                IconIDs.add(icon_id);
                
                icon_name = cursor2.getString(6);
                icon_name1 = cursor2.getString(6).toLowerCase();
                iconNames.add(icon_name1);
                linearLayout345.addView(subtextView(icon_id, icon_name));
                
            } while (cursor2.moveToNext());
        }
    }
    
    System.out.println("iconNames = " + iconNames);
}

private void enableAutoStart() {
    for (Intent intent : Constants.AUTO_START_INTENTS) {
        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            new android.app.AlertDialog.Builder(this)
                    .setTitle(R.string.enable_autostart)
                    .setMessage(R.string.ask_permission)
                    .setPositiveButton(getString(R.string.allow), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                               /* for (Intent intent1 : Constants.AUTO_START_INTENTS)
                                    if (getPackageManager().resolveActivity(intent1, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                                        startActivity(intent1);
                                        break;
                                    }*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .show();
            break;
        }
    }
}

private View imageView(String icon_name, int icon_id) {
    final String imgvalue = icon_name;
    ImageView imageView = new ImageView(this);
    imageView.setLayoutParams(new ViewGroup.LayoutParams(250, 250));
    final DatabaseHelper dbh = new DatabaseHelper(SpeechToText.this);
    Bitmap b = null;
    byte[] image_str = dbh.readDataIcon(icon_name);
    
    try {
        b = BitmapFactory.decodeByteArray(image_str, 0, image_str.length);
        
        imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 100, 100, true));
    } catch (Exception e) {
        
        e.printStackTrace();
    }
    
    return imageView;
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

private void promptSpeech() {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt));
    try {
        startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
    } catch (ActivityNotFoundException a) {
        Toast.makeText(getApplicationContext(),
                getString(R.string.speech_not_supported),
                Toast.LENGTH_SHORT).show();
    }
    
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
        case REQ_CODE_SPEECH_INPUT: {
            if (resultCode == Activity.RESULT_OK && null != data) {
                
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                System.out.println("result = " + result.get(0));
                if (iconNames.contains(result.get(0)))
                {
                    int pos = iconNames.indexOf(result.get(0));
                    int id = IconIDs.get(pos);
                    
                    System.out.println("pos = " + pos);
                    System.out.println("id = " + id);
                    
                    removeBackground();
                    
                    TextView textView = findViewById(id);
                    textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                   
                    if (result.get(0).equalsIgnoreCase("excellent") ||
                                result.get(0).equalsIgnoreCase("very good") ||
                                result.get(0).equalsIgnoreCase("good"))
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tts.setSpeechRate(1);
                            tts.setPitch(0.4f);
                           int speechStatus = tts.speak( "Thank You",TextToSpeech.QUEUE_ADD,null,null);
                            if (speechStatus == TextToSpeech.ERROR)
                            {
                                Log.e("TTS", "Error in converting Text to Speech!23");
                            }
                        } else
                            {
                            tts.setSpeechRate(1);
                            tts.setPitch(0.4f);
                          int speechStatus = tts.speak( "Thank You",TextToSpeech.QUEUE_ADD,null,null);
                            if (speechStatus == TextToSpeech.ERROR) {
                                Log.e("TTS", "Error in converting Text to Speech4!");
                            }
                            }
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Are you sure you want to select "+result.get(0).substring(0, 1).toUpperCase() + result.get(0).substring(1)+" ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        startActivity(new Intent(SpeechToText.this,SecondActivity.class));
    
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        removeBackground();
    
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        
                    
                    }
                    
                } else {
                    System.out.println("Not matched");
                    Toast.makeText(this, "Not Present", Toast.LENGTH_SHORT).show();
                }
            
            }
            break;
        }
        
    }
}

/*public void onPause(){
    if(tts !=null){
        tts.stop();
        tts.shutdown();
    }
    super.onPause();
}*/
@Override
protected void onDestroy() {
    super.onDestroy();
  /*  if (Speech.getInstance().isListening())
        Speech.getInstance().shutdown();*/
    unregisterReceiver(broadcastReceiver);
}

private void removeBackground() {
    
    for (int i = 0; i < iconNames.size(); i++){
    
        TextView textView = findViewById(IconIDs.get(i));
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

public boolean isAlertDialogShowing(AlertDialog thisAlertDialog) {
    if (thisAlertDialog != null) {
        return thisAlertDialog.isShowing();
    }
    return false;
}

BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("intent.getAction(): " + intent.getAction());
        if ("com.example.feedbackapplication.ACTION_FEEDBACK".equals(intent.getAction())) {
            Bundle b = intent.getExtras();
            String result = b.getString("value");
            
            JobIntentServiceClass jobIntentServiceClass = new JobIntentServiceClass();

               /* System.out.println("Checking: " + jobIntentServiceClass.onStopCurrentWork());
                System.out.println("Checking: " + jobIntentServiceClass.isStopped());*/
            
            AlertDialog.Builder builder;
            
            if (iconNames.contains(result)) {
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
                
            } else {
                
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
                
                //Toast.makeText(context, "Not matched.", Toast.LENGTH_SHORT).show();
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
