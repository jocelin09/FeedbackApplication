package com.example.feedbackapplication.adminlogin;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedbackapplication.BaseActivity;
import com.example.feedbackapplication.R;
import java.util.UUID;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    String str_clientid,str_username,str_pwd,uuid="";
    String feedbackservicename="";
    EditText edt_username,edt_pwd,edt_clientid;
    Button btn_login;
    
    int questionscount;
    
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    
    try {
        questionscount = dbh.admindetails_count();
        if (questionscount == 0) {
            dbh.insertAdminDetails("1", "Dell Score Feedback", "12", "Location 1",
                    "13", "Site 1", "14", "Building 1", "15", "Wing-1", "16",
                    "1st Floor", "17", "Gents Washroom","Area specific|Common Feedback|Common Feedback with Area Specific");
            
            dbh.insertAdminDetails("1", "Dell Score Feedback", "12", "Location 1",
                    "13", "Site 1", "14", "Building 1", "15", "Wing-1", "16",
                    "1st Floor", "17", "Ladies Washroom","Common Feedback|Common Feedback with Area Specific");
          
           dbh.insertAdminDetails("1", "Dell Score Feedback", "12", "Location 1",
                    "13", "Site 5", "14", "Building 3", "", "", "16",
                    "5th Floor", "17", "Cafeteria","Area specific");
            
            dbh.insertAdminDetails("1", "Dell Score Feedback", "21", "Location 2",
                    "22", "Site 4", "23", "Building 4", "24", "Wing 2", "18",
                    "4th Floor", "17", "Pantry","Common Feedback with Area Specific");
            
            dbh.insertAdminDetails("2", "ISS Feedback", "", "",
                    "24", "Site 2", "25", "Building 2", "123456", "Wing extra", "27",
                    "2nd Floor", "28", "Cafeteria","Common Feedback");
            
            dbh.insertAdminDetails("2", "ISS Feedback", "", "",
                    "24", "Site 3", "25", "Building 3", "", "", "27",
                    "3rd Floor", "28", "Washroom","Area specific|Common Feedback with Area Specific");
            
            dbh.insertAdminDetails("3", "NTT Feedback", "120", "Location 4",
                    "", "", "17", "Building 6", "15", "Wing 4", "16",
                    "6th Floor", "17", "Washroom","Area specific|Common Feedback|Common Feedback with Area Specific");
            
            dbh.insertAdminDetails("3", "NTT Feedback", "120", "Location 47",
                    "", "", "17", "Building 16", "15", "Wing 4", "4545",
                    "13th Floor", "17", "Washroom","Area specific");
            
            dbh.insertAdminDetails("4", "E-clerx", "", "",
                    "", "", "157", "Building 7", "15", "Wing 5", "16",
                    "7th Floor", "17", "Washroom","Area specific|Common Feedback|Common Feedback with Area Specific");
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Exception = " + e);
    }
    
    
    try {
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_pwd = (EditText) findViewById(R.id.edt_password);
        edt_clientid = (EditText) findViewById(R.id.edt_clientid);
        
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Login Activity 65 = " + e);
    }
    
}

@Override
public void onClick(View view) {
    
    try {
        // str_companyname = companyname.getSelectedItem().toString();
        str_clientid = edt_clientid.getText().toString().trim();
        str_username = edt_username.getText().toString().trim();
        str_pwd = edt_pwd.getText().toString().trim();
    
        if(str_clientid.length()==0){
            edt_clientid.setError("Client ID is not entered");
            edt_clientid.requestFocus();
        }
        else if(str_username.length()==0){
            edt_username.setError("Username is not entered");
            edt_username.requestFocus();
        }
        else if(str_pwd.length()==0){
            edt_pwd.setError("Password is not entered");
            edt_pwd.requestFocus();
        }
        else
        {
            uuid = UUID.randomUUID().toString();
            //dbh.insertLoginDetails(uuid,str_username,str_pwd,str_companyname);
            boolean isInserted = dbh.insertLoginDetails(uuid,str_clientid,str_username,str_pwd);
            System.out.println("isInserted = " + isInserted);
            
            if (isInserted)
            {
                Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                feedbackservicename = dbh.getFeedbackServiceType(str_clientid);
                if (!feedbackservicename.contains("|"))
                {
                    Intent intent = new Intent(LoginActivity.this,AdminDetailsConfig.class);
                    intent.putExtra("feedbackservicename",feedbackservicename);
                    intent.putExtra("client_id",str_clientid);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(LoginActivity.this,FeedbackServiceAct.class);
                    intent.putExtra("client_id",str_clientid);
                    startActivity(intent);
                }
                
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Some error occured, Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error while logging in = " + e);
    }
    
}

}
