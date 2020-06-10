package com.example.feedbackapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import io.reactivex.functions.Consumer;

import com.sac.speech.GoogleVoiceTypingDisabledException;
import com.sac.speech.Speech;
import com.sac.speech.SpeechDelegate;
import com.sac.speech.SpeechRecognitionNotAvailable;
import com.vanniktech.rxpermission.Permission;
import com.vanniktech.rxpermission.RealRxPermission;
import com.vanniktech.rxpermission.RxPermission;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class JobIntentServiceClass extends JobIntentService implements SpeechDelegate, Speech.stopDueToDelay {
public static SpeechDelegate delegate;
public static Context contextClass;
public boolean megavision = true;

public static void enqueueWork(Context context, Intent work) {
    contextClass = context;
    enqueueWork(context, JobIntentServiceClass.class, 123, work);
}

@Override
protected void onHandleWork(@NonNull Intent intent) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((AudioManager) Objects.requireNonNull(
                    getSystemService(Context.AUDIO_SERVICE))).adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, 0);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    if (isStopped()) return;
    delegate = this;
    
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(new Runnable() {
        @Override
        public void run() {
            
            Speech.init(getApplication());
            Speech.getInstance().setListener(JobIntentServiceClass.this);
            
            RxPermission rxPermission = RealRxPermission.getInstance(getApplication());
            rxPermission.request(Manifest.permission.RECORD_AUDIO)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            try {
                                System.out.println("Permission Accepted");
                                Speech.getInstance().stopTextToSpeech();
                                Speech.getInstance().setLocale(Locale.getDefault());
                                Speech.getInstance().startListening(null, JobIntentServiceClass.this);
                            } catch (SpeechRecognitionNotAvailable exc) {
                                //showSpeechNotSupportedDialog();
                                
                            } catch (GoogleVoiceTypingDisabledException exc) {
                                //showEnableGoogleVoiceTyping();
                            }
                        }
                    }).isDisposed();
            
            
        }
    });
    muteBeepSoundOfRecorder();
}

@Override
public void onSpecifiedCommandPronounced(String event) {
    System.out.println("On Specified command pronounced");
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((AudioManager) Objects.requireNonNull(
                    getSystemService(Context.AUDIO_SERVICE))).adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, 0);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    RxPermission rxPermission = RealRxPermission.getInstance(getApplication());
    rxPermission.request(Manifest.permission.RECORD_AUDIO)
            .subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    try {
                        Speech.getInstance().stopTextToSpeech();
                        Speech.getInstance().setLocale(Locale.getDefault());
                        Speech.getInstance().startListening(null, delegate);
                    } catch (SpeechRecognitionNotAvailable exc) {
                        //showSpeechNotSupportedDialog();
                        
                    } catch (GoogleVoiceTypingDisabledException exc) {
                        //showEnableGoogleVoiceTyping();
                    }
                }
            }).isDisposed();
    
    muteBeepSoundOfRecorder();
}


@Override
public void onStartOfSpeech() {
}

@Override
public void onSpeechRmsChanged(float value) {
}

@Override
public void onSpeechPartialResults(List<String> results) {
    for (String partial : results) {
        //Log.d("Result", partial + "");
        System.out.println("partial = " + partial);
    }
}

@Override
public void onSpeechResult(String result) {
    System.out.println("result = " + result);
   // Toast.makeText(contextClass, "Result: "+result, Toast.LENGTH_SHORT).show();
    if (!TextUtils.isEmpty(result)) {
        //Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        if (megavision) {
            if (/*result.toLowerCase().equalsIgnoreCase("hello feedback") ||
                        result.toLowerCase().equalsIgnoreCase("hey feedback") ||*/
                        result.toLowerCase().contains("start feedback")) {
                //muteBeepSoundOfRecorder();
                megavision = false;
                
                Intent intent = new Intent("com.example.feedbackapplication.ACTION_BROADCAST");
                sendBroadcast(intent);
                
            }
        } else {
            switch (result.toLowerCase()) {
                //case "hello feedback":
                ///case "hey feedback":
                case "start feedback":
                    Intent intent = new Intent("com.example.feedbackapplication.ACTION_BROADCAST");
                    sendBroadcast(intent);
                    break;
                default:
                    sendBroadcastMessage(result.toLowerCase());
                    break;
            }
            
        }
        
    }
}

public void sendBroadcastMessage(String message) {
    Intent intent = new Intent("com.example.feedbackapplication.ACTION_FEEDBACK");
    intent.putExtra("value", message);
    sendBroadcast(intent);
}

@Override
public boolean onStopCurrentWork() {
    return super.onStopCurrentWork();
}

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

@Override
public void onTaskRemoved(Intent rootIntent) {
    PendingIntent service =
            PendingIntent.getService(getApplicationContext(), new Random().nextInt(),
                    new Intent(getApplicationContext(), JobIntentService.class), PendingIntent.FLAG_ONE_SHOT);
    
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    assert alarmManager != null;
    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, service);
    super.onTaskRemoved(rootIntent);
}
}
