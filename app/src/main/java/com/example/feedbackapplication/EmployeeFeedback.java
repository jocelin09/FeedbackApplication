package com.example.feedbackapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.feedbackapplication.adminlogin.FeedbackServiceAct;
import com.example.feedbackapplication.adminlogin.LoginActivity;

import java.util.UUID;

public class EmployeeFeedback extends BaseActivity {

    private RatingBar rBar;
    Button rate;
    String uuid="",client_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_feedback);
        rBar = (RatingBar) findViewById(R.id.ratingBar1);
        rate=(Button)findViewById(R.id.rate);
        uuid = UUID.randomUUID().toString();
        client_id = getIntent().getStringExtra("client_id");
    }

    public void rate_employee(View view){

        int noofstars = rBar.getNumStars();
        float getrating = rBar.getRating();
        System.out.print("***********Rating: "+getrating+"/"+noofstars);
        boolean isInserted = dbh.store_EmpRating(uuid,"",String.valueOf(getrating),"update");
        Intent intent = new Intent(EmployeeFeedback.this, FeedbackServiceAct.class);
        intent.putExtra("client_id",client_id);
        startActivity(intent);
        finish();

    }
}
