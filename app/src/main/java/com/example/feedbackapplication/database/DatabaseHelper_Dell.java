package com.example.feedbackapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper_Dell extends SQLiteOpenHelper {
    private static DatabaseHelper mInstance = null;

    public static DatabaseHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public DatabaseHelper_Dell(Context context) {
        super(context, "feedback.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //sqLiteDatabase.execSQL(" CREATE TABLE  feedback_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Supervisor_Id TEXT,User_Id TEXT, Display_Name Text,Gender TEXT,Site_Location_Id TEXT,Feedback_Icon_Id TEXT, Feedback_Question_Id TEXT, Feedback_DateTime TEXT,Site_Name TEXT, Building_Name TEXT, Floor_Name TEXT, Area_Name TEXT, Comment TEXT, Company_Name TEXT, Employee_Name TEXT, Email TEXT, Phone_Number TEXT, UpdatedStatus TEXT, Last_IP TEXT, Update_Location TEXT, Apk_Web_Version TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  feedback_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Supervisor_Id TEXT,WorkStation_Number TEXT, Display_Name Text,Gender TEXT,Site_Location_Id TEXT,Feedback_Icon_Id TEXT, Feedback_Question_Id TEXT, Feedback_DateTime TEXT, Building_Id TEXT, Floor_Id TEXT, Area_Id TEXT, Comments TEXT, Company_Name TEXT, Employee_Name TEXT, Employee_Id TEXT, Email TEXT, Phone_Number TEXT, UpdatedStatus TEXT,Last_IP TEXT, Update_Location TEXT, Apk_Web_Version TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  feedback_icons_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Name TEXT,Score TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  feedback_adminquestions (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Question TEXT,Area_Id TEXT, RecordStatus TEXT,RatingValue TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  negative_feedback (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Id TEXT, Negative_Feedback TEXT, Company TEXT, Name TEXT, Email TEXT, Contact TEXT, Comment TEXT,UpdatedStatus TEXT, Last_IP TEXT, Update_Location TEXT, Apk_Web_Version TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  public_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Id TEXT, Public_Name TEXT, Email TEXT, Contact TEXT,UpdatedStatus TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  user_master (ID INTEGER PRIMARY KEY ,User_Id TEXT, Username TEXT, Password TEXT, Email TEXT, Name TEXT, User_Type TEXT,EmpImage BLOB)");
        sqLiteDatabase.execSQL(" CREATE TABLE  site_detail(ID INTEGER PRIMARY KEY , Auto_Id TEXT,Company_Id TEXT, Location_Id TEXT, Site_Name TEXT,RecordStatus TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  building_detail(ID INTEGER PRIMARY KEY , Auto_Id TEXT,Company_Id TEXT, Building_Name TEXT,Site_Location_Id TEXT,RecordStatus TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  floor_detail(ID INTEGER PRIMARY KEY , Auto_Id TEXT,Company_Id TEXT, Floor_Name TEXT,Building_Id TEXT ,Site_Location_Id TEXT,RecordStatus TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  area_detail(ID INTEGER PRIMARY KEY , Auto_Id TEXT, Company_Id TEXT, Area_Name TEXT,Site_Location_Id TEXT,RecordStatus TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE  store_setting(ID INTEGER PRIMARY KEY , Auto_Id TEXT, Site_Name TEXT, Building_Name TEXT, Floor_Name TEXT, Area_Name TEXT, Feedback_Question TEXT, Display_Name TEXT, Checked_Display_Name TEXT,Active_Setting Text)");
        sqLiteDatabase.execSQL(" CREATE TABLE emp_neg_rating_info(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,Emp_Feedback_Id TEXT,Name TEXT,Email TEXT,Contact TEXT,Comment TEXT,UpdatedStatus TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE emp_feedback(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,Emp_Id TEXT,Ratings TEXT,UpdatedStatus TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE feedback_sms(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,Building_Id TEXT, Mobile_Number TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE company_details(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,Company_Name TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE EmailList (Id INTEGER PRIMARY KEY, Auto_Id TEXT,Building_Id TEXT, Employee_Email TEXT, Recipient_Type TEXT, Record_Status TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE location_detail(ID INTEGER PRIMARY KEY , Auto_Id TEXT, Company_Id TEXT, Location_Name TEXT,RecordStatus TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE SyncInfo (Id INTEGER PRIMARY KEY, Auto_Id TEXT, Mac_Address TEXT, Apk_Version TEXT, Sync_Date_Time TEXT, Device_Name TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE sms_master(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,UserName TEXT, Password TEXT, Type TEXT, Source TEXT, URL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

/*
    public JSONArray SyncInfo(){

        JSONArray TicketJsonArray = new JSONArray();
        String[]loggerData = new LoggerFile().loggerFunction();
        try {
            JSONObject TicketJsonObject = new JSONObject();
            TicketJsonObject.put("Sync_Date_Time",loggerData[0]);
            TicketJsonObject.put("MacAddress",loggerData[1]);
            TicketJsonObject.put("Apk_Web_Version", loggerData[3]);
            //TicketJsonObject.put("GeoLoc", loggerData[5]);
            TicketJsonObject.put("Device_Name", loggerData[4]);

            TicketJsonArray.put(TicketJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return TicketJsonArray;
    }
*/


    public String getsmsURL(){

        String url="";
        try {
            String query = "SELECT URL FROM sms_master";

            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    url=res.getString(res.getColumnIndex("URL"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public String getsmsUsername(){

        String UserName="";
        try {
            String query = "SELECT UserName FROM sms_master";

            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    UserName=res.getString(res.getColumnIndex("UserName"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserName;
    }

    public String getsmsPassword(){

        String Password="";
        try {
            String query = "SELECT Password FROM sms_master";

            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    Password=res.getString(res.getColumnIndex("Password"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Password;
    }

    public String getsmsType(){

        String Type="";
        try {
            String query = "SELECT Type FROM sms_master";

            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    Type=res.getString(res.getColumnIndex("Type"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Type;
    }

    public String getsmsSource(){

        String Source="";
        try {
            String query = "SELECT Source FROM sms_master";

            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    Source=res.getString(res.getColumnIndex("Source"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Source;
    }

    public int loginCount(){
        int count =0;
        try {
            String query = "SELECT * FROM user_master";

            Log.d("ASdasdasdasd",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
           count = res.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public String getCompanyId(String Company_name){

        String company_id="";
        try {
            String query = "SELECT Auto_Id FROM company_details Where Company_Name ='"+Company_name+"'";

            Log.d("company_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    company_id=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return company_id;
    }
    public String getSiteId(String Site_name){

        String site_id="";
        try {
            String query = "SELECT Auto_Id FROM site_detail Where Site_Name ='"+Site_name+"'";

            Log.d("site_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    site_id=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return site_id;
    }

    public String getBuildingId(String Building_Name) {

        String auto_id = "";
        try {
            String query = "SELECT Auto_Id FROM building_detail Where Building_Name ='" + Building_Name + "'";

            Log.d("building_query", query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res = db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    auto_id = res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auto_id;
    }

    public String getBuildingName(String floor_Id){

        String Building_Name="";
        try {
            String query = "SELECT Building_Name FROM building_detail Where Auto_Id ='"+floor_Id+"'";

            Log.d("floor_query1",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    Building_Name=res.getString(res.getColumnIndex("Building_Name"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Building_Name;
    }

    public String getAreaId(String Area_Name){

        String site_id="";
        try {
            String query = "SELECT Auto_Id FROM area_detail Where Area_Name ='"+Area_Name+"'";

            Log.d("area_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    site_id=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();

            System.out.println("getAreaId Auto_Id  = " + site_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return site_id;
    }

    public String getQuestionId(String Question_Name){

        String site_id="";
        try {
            String query = "SELECT Auto_Id FROM feedback_questions Where Feedback_Question ='"+Question_Name+"'";

            Log.d("area_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    site_id=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return site_id;
    }

    public String getFeedbackQuestionId(String Question_Name){

        String site_id="";
        try {
            String query = "SELECT Auto_Id FROM feedback_questions Where Feedback_Question ='"+Question_Name+"' ";

            Log.d("area_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    site_id=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return site_id;
    }

    public String getFloorId(String Floor_Name){
       // sqLiteDatabase.execSQL(" CREATE TABLE  floor_detail(ID INTEGER PRIMARY KEY , Auto_Id TEXT,Company_Id TEXT, Floor_Name TEXT,Site_Location_Id TEXT,RecordStatus TEXT)");

        String AUTO_id="";
        try {
            String query = "SELECT Auto_Id FROM floor_detail Where Floor_Name ='"+Floor_Name+"'";

            Log.d("area_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    AUTO_id=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();

            System.out.println("getFloorId  = " + AUTO_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AUTO_id;
    }

    public List<String> getFeedbackQuestions(String area_id) {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT Feedback_Question FROM feedback_questions where Area_Id = '" + area_id + "' and ID IN (9,10,11,12,13) and RatingValue = 'no' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments
        // list.add("Select");
        // looping through all rows and adding to list
        if (cursor.moveToNext()) {
            // while (cursor.moveToNext();
            list.add(cursor.getString(0));//adding 2nd column data

        do {

        } while (cursor.moveToNext());
    }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        System.out.println("list = " + list);
        return list;
    }

    public int getTotalQuestionCount(){

        String countQuery = "SELECT  * FROM  feedback_questions where ID <= 8"  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        // System.out.println("count no.of questions= " + count);
        cursor.close();

        return count;
    }

    public int getCafeQuestionCount(String area_id){

        String countQuery = "SELECT  * FROM  feedback_questions Where Area_Id ='"+area_id+"' and ID IN (9,10,11,12,13,14)"  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
       // System.out.println("count no.of questions= " + count);
        cursor.close();

        return count;
    }

    public String getQuestionName(String Area_Id){

        String Area_Name="";
        
                try {
                    String query = "SELECT Feedback_Question FROM feedback_questions Where Area_Id ='"+Area_Id+"' and id <= 8 ";

                    Log.d("Feedback_Question_query",query+" :: "+Area_Id);
                    SQLiteDatabase db = getWritableDatabase();
                    Cursor res =db.rawQuery(query, null);
                    if (res.moveToFirst()) {
                        do {
                            Area_Name=res.getString(res.getColumnIndex("Feedback_Question"));
                        } while (res.moveToNext());
                    }
                    res.close();
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Area_Name DB= " + Area_Name);

        return Area_Name;
    }

    public String getRatingName(String area_id){

        String Area_Name="",Auto_Id="";
        try {
            String query = "SELECT Feedback_Question FROM feedback_questions where Area_Id = '"+area_id+"' and ID >= 9 and RatingValue = 'no' ";

            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst())
                {
               // do {
                   // Auto_Id = res.getString(res.getColumnIndex("Auto_Id"));
                    Area_Name = res.getString(res.getColumnIndex("Feedback_Question"));
                //} while (res.moveToNext());
                 }

            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("getRatingName Area_Name = " + Area_Name);
        return Area_Name;
    }

    public String getExistSiteId(String Site_Id){
        String site_id="";
        try {
            String query = "SELECT * FROM EmailList Where Site_Location_Id ='"+Site_Id+"'";

            Log.d("location_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    site_id=res.getString(res.getColumnIndex("Site_Location_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return site_id;
    }

    public String getLocationId(String Location_name){

        String site_id="";
        try {
            String query = "SELECT Auto_Id FROM location_detail Where Location_Name ='"+Location_name+"'";

            Log.d("location_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    site_id=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return site_id;
    }

    public String emailList_Cc(String BuildingId){
        String emailquery = "Select Building_Id, Employee_Email from EmailList where Building_Id = '"+BuildingId+"' AND Recipient_Type = 'Cc'";
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor emailList = database.rawQuery(emailquery, null);
        StringBuilder sb =new StringBuilder();

        if(emailList.moveToFirst()){
            do{
                sb.append(emailList.getString(emailList.getColumnIndex("Employee_Email"))+",");
            }while (emailList.moveToNext());
            Log.d("Email_Id_Value",sb.toString());

        }

        if(sb.lastIndexOf(",") != -1){
            return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
        } else {
            return sb.toString();
        }
    }

    public String emailList_Bcc(String BuildingId){
        String emailquery = "Select Building_Id, Employee_Email from EmailList where Building_Id = '"+BuildingId+"' AND Recipient_Type = 'Bcc'";
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor emailList = database.rawQuery(emailquery, null);
        StringBuilder sb =new StringBuilder();

        if(emailList.moveToFirst()){
            do{
                sb.append(emailList.getString(emailList.getColumnIndex("Employee_Email"))+",");
            }while (emailList.moveToNext());
            Log.d("Email_Id_Value",sb.toString());

        }

        if(sb.lastIndexOf(",") != -1){
            return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
        } else {
            return sb.toString();
        }
    }

    public String emailList_To(String BuildingId){
        String emailquery = "Select Building_Id, Employee_Email from EmailList where Building_Id = '"+BuildingId+"' AND Recipient_Type = 'To'";
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor emailList = database.rawQuery(emailquery, null);
        StringBuilder sb =new StringBuilder();

        if(emailList.moveToFirst()){
            do{
                sb.append(emailList.getString(emailList.getColumnIndex("Employee_Email"))+",");
            }while (emailList.moveToNext());
            Log.d("Email_Id_Value",sb.toString());

        }

        if(sb.lastIndexOf(",") != -1){
            return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
        } else {
            return sb.toString();
        }
    }

    public String getfeedBackDetails(String feedbackName){

        String feedbackId="";
        try {
            String query = "SELECT Auto_Id FROM feedback_icons_details Where FeedBack_Name ='"+feedbackName+"'";

            Log.d("ASdasdasdasd",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    feedbackId=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackId;
    }

    public String getMobileNumber(String Site_Id){
        StringBuilder stringBuilder = new StringBuilder();
        String feedbackId="";
        try {
            String query = "SELECT Mobile_Number FROM feedback_sms where Building_Id = '"+Site_Id+"'";

            Log.d("ASdasdasdasd",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    feedbackId=res.getString(res.getColumnIndex("Mobile_Number"));
                    stringBuilder.append(feedbackId+",");
                } while (res.moveToNext());
                Log.d("sjhfddjhv",stringBuilder.toString());

            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(stringBuilder.lastIndexOf(",") != -1){
            return stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(",")).toString();
        } else {
            return stringBuilder.toString();
        }
    }

    public boolean insertBitmapUserId(Bitmap EmpImage, String Username)  {
        boolean image = false;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        EmpImage.compress(Bitmap.CompressFormat.JPEG, 100, out);

        ByteArrayOutputStream meterOut = new ByteArrayOutputStream();
        byte[] selfieBuffer=out.toByteArray();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        try
        {
            values = new ContentValues();
            values.put("EmpImage", selfieBuffer);
            long i = db.update("user_master", values, "Username ='" + Username + "'", null);
            if(i == -1)
                image =  false;
            else
                image =  true;
            Log.i("InsertValues", image + "");
            // Insert into database successfully.
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        return image;


    }

    public boolean insertBitmap(Bitmap EmpImage, String User_Id)  {
        boolean image = false;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        EmpImage.compress(Bitmap.CompressFormat.JPEG, 100, out);

        ByteArrayOutputStream meterOut = new ByteArrayOutputStream();
        byte[] selfieBuffer=out.toByteArray();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        try
        {
            values = new ContentValues();
            values.put("EmpImage", selfieBuffer);
            long i = db.update("user_master", values, "User_Id ='" + User_Id + "'", null);
            if(i == -1)
                image =  false;
            else
                image =  true;
            Log.i("InsertValues", image + "");
            // Insert into database successfully.
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        return image;


    }

    public Bitmap getProfilePic(String UserId){
        Bitmap bitmap = null;
        try {

            byte[] image = null;
            SQLiteDatabase db = this.getWritableDatabase();

            String formQuery1 = "select EmpImage from user_master where User_Id='"+UserId+"' AND Username != 'GuestUser'";
            Cursor img =db.rawQuery(formQuery1, null);
            Log.d("valsdasdasd",formQuery1+" "+ img.getCount());

            if (img.getCount() > 0) {
                if (img.moveToNext()) {
                    do {
                        image = img.getBlob(0);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
                         bitmap = BitmapFactory.decodeStream(inputStream);


                    } while (img.moveToNext());
                }
            }

            img.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    
    public List<String> getAllBuilding() {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT Building_Name FROM building_detail" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments
        list.add("Select Building");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        System.out.println("list = " + list);
        return list;
    }
    
    public List<String> getAllLabels(String Building_Id) {
    List<String> list = new ArrayList<String>();
    
    // Select All Query
    String selectQuery = "SELECT Floor_Name FROM floor_detail where Building_Id ='"+Building_Id+"'";
    Log.d("Query",""+selectQuery);
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments
    list.add("Select Floor");
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            list.add(cursor.getString(0));//adding 2nd column data
        } while (cursor.moveToNext());
    }
    // closing connection
    cursor.close();
    db.close();
    // returning lables
    
    System.out.println("list = " + list);
    return list;
    }
    
}
