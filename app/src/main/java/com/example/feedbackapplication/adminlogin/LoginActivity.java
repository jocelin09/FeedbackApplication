package com.example.feedbackapplication.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.feedbackapplication.R;
import com.example.feedbackapplication.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    String str_companyname,str_username,str_pwd,uuid="";
    Spinner companyname;
    EditText edt_username,edt_pwd;
    Button btn_login;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    databaseHelper = new DatabaseHelper(this);
    sqLiteDatabase = databaseHelper.getWritableDatabase();
    
    
    companyname = (Spinner) findViewById(R.id.s_company_name);
    
    edt_username = (EditText) findViewById(R.id.edt_username);
    edt_pwd = (EditText) findViewById(R.id.edt_password);
    
    btn_login = (Button) findViewById(R.id.btn_login);
    
    
    // you need to have a list of data that you want the spinner to display
    List<String> spinnerArray =  new ArrayList<String>();
    //spinnerArray.add("Company Name");
    spinnerArray.add("Dell Score Feedback");
    spinnerArray.add("ISS Feedback");
    
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, R.layout.spinner_text, spinnerArray);
    
    adapter.setDropDownViewResource(R.layout.spinner_dropdown_text);
    companyname.setAdapter(adapter);
    
    btn_login.setOnClickListener(this);
    
}

@Override
public void onClick(View view) {
    
    str_companyname = companyname.getSelectedItem().toString();
    str_username = edt_username.getText().toString().trim();
    str_pwd = edt_pwd.getText().toString().trim();
    
    
    if(str_username.length()==0){
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
        databaseHelper.insertLoginDetails(uuid,str_username,str_pwd,str_companyname);
       Toast.makeText(this, str_username + " "+str_pwd + " " +str_companyname+" Succes....", Toast.LENGTH_LONG).show();
    
       startActivity(new Intent(LoginActivity.this,AdminDetailsConfig.class));
    }
    
}
}
