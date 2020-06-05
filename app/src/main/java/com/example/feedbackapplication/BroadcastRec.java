package com.example.feedbackapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastRec extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if ("com.example.feedbackapplication.ACTION_BROADCAST".equals(intent.getAction())){
            Intent intent1 = new Intent(context, SpeechToText.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            context.startActivity(intent1);
        }

    }
}
