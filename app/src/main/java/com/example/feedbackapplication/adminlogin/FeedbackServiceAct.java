package com.example.feedbackapplication.adminlogin;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.feedbackapplication.BaseActivity;
import com.example.feedbackapplication.R;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class FeedbackServiceAct extends BaseActivity {

LinearLayout mainLayout;
String client_id,feedbackservicetypes="";
private ArrayList<String> feedbackserviceList;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_feedback_service);
    
    client_id = getIntent().getStringExtra("client_id");
    System.out.println("client_id = " + client_id);
    
    feedbackserviceList = new ArrayList<>();
   
    feedbackservicetypes = dbh.getFeedbackServiceType(client_id);
   
        if (feedbackservicetypes.contains("|")) {
            String[] areas = feedbackservicetypes.split("\\|");
            for (int i = 0; i < areas.length; i++) {
                feedbackserviceList.add(areas[i]);
            }
        } else {
            feedbackserviceList.add(feedbackservicetypes);
        }
    
    System.out.println("feedbackservicetypes = " + feedbackservicetypes);
    
    mainLayout = new LinearLayout(this);
    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    mainLayout.setLayoutParams(params);
    mainLayout.setOrientation(LinearLayout.HORIZONTAL);
    mainLayout.setGravity(Gravity.CENTER);
    mainLayout.setWeightSum(feedbackserviceList.size());
    
    for (int i = 0; i < feedbackserviceList.size(); i++) {
        String feedbackservicename = feedbackserviceList.get(i);
        mainLayout.addView(textView(i + 1, feedbackservicename));
    }
    setContentView(mainLayout);
    
}

private View textView(int i, final String feedbackservicename) {
    TextView textView = new TextView(this);
    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, 300,1f);
    params1.setMargins(10,10,10,10);
    textView.setLayoutParams(params1);
    textView.setId(i);
    textView.setTextSize(1, 20);
    textView.setGravity(Gravity.CENTER);
    textView.setBackgroundResource(R.drawable.edit_style);
    textView.setTypeface(null, Typeface.BOLD);
    textView.setText(feedbackservicename);
    textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(FeedbackServiceAct.this,AdminDetailsConfig.class);
            intent.putExtra("feedbackservicename",feedbackservicename);
            intent.putExtra("client_id",client_id);
            startActivity(intent);
            
        }
    });
    return textView;
}
}
