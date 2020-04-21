package com.example.feedbackapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
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

    public DatabaseHelper(Context context) {
        super(context, "pun_feedback.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        
        //sqLiteDatabase.execSQL(" CREATE TABLE  feedback_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Supervisor_Id TEXT,User_Id TEXT, Display_Name Text,Gender TEXT,Site_Location_Id TEXT,Feedback_Icon_Id TEXT, Feedback_Question_Id TEXT, Feedback_DateTime TEXT,Site_Name TEXT, Building_Name TEXT, Floor_Name TEXT, Area_Name TEXT, Comment TEXT, Company_Name TEXT, Employee_Name TEXT, Email TEXT, Phone_Number TEXT, UpdatedStatus TEXT, Last_IP TEXT, Update_Location TEXT, Apk_Web_Version TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE admin_details(ID INTEGER PRIMARY KEY,Company_AutoID TEXT,Company_Name TEXT,Location_Auto_Id TEXT,Location_Name TEXT,Site_Auto_Id TEXT, Site_Name TEXT,Building_Auto_Id TEXT, Building_Name TEXT,Wing_Auto_Id TEXT,Wing_Name TEXT,Floor_Auto_Id TEXT, Floor_Name TEXT,Area_Auto_Id TEXT, Area_Name TEXT,RecordStatus TEXT)");
        //                                                                          company..............................location detail starts.......................................................site details starts...................................................................building detail starts...........................................................................wing details starts.........................................................................................floor detail starts...........................................................................................area detail starts.........................................................................
        sqLiteDatabase.execSQL(" CREATE TABLE area_details(ID INTEGER PRIMARY KEY , Auto_Id TEXT, Company_Id TEXT, Area_Name TEXT,Site_Location_Id TEXT,RecordStatus TEXT)");
    
        sqLiteDatabase.execSQL(" CREATE TABLE admin_login (ID INTEGER PRIMARY KEY,User_Id TEXT, User_Name TEXT, Password TEXT,Company_Name TEXT)");
      
       // sqLiteDatabase.execSQL(" CREATE TABLE admin_asset_site_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Supervisor_Id TEXT,User_Id TEXT, Display_Name Text,Gender TEXT,Site_Location_Id TEXT,Feedback_Icon_Id TEXT, Feedback_Question_Id TEXT, Feedback_DateTime TEXT, Building_Id TEXT, Floor_Id TEXT, Area_Id TEXT, Comments TEXT, Company_Name TEXT,Service_Id TEXT ,Emp_Code TEXT, Employee_Name TEXT, Employee_Id TEXT, Email TEXT, Phone_Number TEXT, UpdatedStatus TEXT,Last_IP TEXT, Update_Location TEXT, Apk_Web_Version TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE feedback_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Supervisor_Id TEXT,WorkStation_Number TEXT, Display_Name Text,Gender TEXT,Site_Location_Id TEXT,Feedback_Icon_Id TEXT, Feedback_Question_Id TEXT, Feedback_DateTime TEXT, Building_Id TEXT, Floor_Id TEXT, Area_Id TEXT, Comments TEXT, Company_Name TEXT, Employee_Name TEXT, Employee_Id TEXT, Email TEXT, Phone_Number TEXT, UpdatedStatus TEXT,Last_IP TEXT, Update_Location TEXT, Apk_Web_Version TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE feedback_questions (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Question TEXT,Order_Id TEXT,Icon_Type TEXT, Area_Name TEXT, Is_Rated TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE feedback_sub_questions (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Id TEXT, Feedback_Sub_Question TEXT,Order_Id TEXT,Is_Rated TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE feedback_icons_details (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Icon_Name TEXT, Icon_value BLOB,Icon_Type TEXT, Area_Name TEXT, Status TEXT)");
        
        
        sqLiteDatabase.execSQL(" CREATE TABLE EmailSMSList (Id INTEGER PRIMARY KEY, Auto_Id TEXT,Building_Id TEXT, Employee_Email TEXT, Recipient_Type TEXT,Mobile_Number TEXT, Record_Status TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE sms_master(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,UserName TEXT, Password TEXT, Type TEXT, Source TEXT, URL TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE SyncInfo (Id INTEGER PRIMARY KEY, Auto_Id TEXT, Mac_Address TEXT, Apk_Version TEXT, Sync_Date_Time TEXT, Device_Name TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE store_setting(ID INTEGER PRIMARY KEY , Auto_Id TEXT, Site_Name TEXT, Building_Name TEXT, Floor_Name TEXT, Area_Name TEXT, Feedback_Question TEXT, Display_Name TEXT, Checked_Display_Name TEXT,Active_Setting Text)");
        sqLiteDatabase.execSQL(" CREATE TABLE emp_neg_rating_info(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,Emp_Feedback_Id TEXT,Name TEXT,Email TEXT,Contact TEXT,Comment TEXT,UpdatedStatus TEXT)");
        sqLiteDatabase.execSQL(" CREATE TABLE emp_feedback(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,Emp_Id TEXT,Ratings TEXT,UpdatedStatus TEXT)");
    
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public JSONArray SyncInfo(){
//
//        JSONArray TicketJsonArray = new JSONArray();
//        String[]loggerData = new LoggerFile().loggerFunction();
//        try {
//            JSONObject TicketJsonObject = new JSONObject();
//            TicketJsonObject.put("Sync_Date_Time",loggerData[0]);
//            TicketJsonObject.put("MacAddress",loggerData[1]);
//            TicketJsonObject.put("Apk_Web_Version", loggerData[3]);
//            //TicketJsonObject.put("GeoLoc", loggerData[5]);
//            TicketJsonObject.put("Device_Name", loggerData[4]);
//
//            TicketJsonArray.put(TicketJsonObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return TicketJsonArray;
//    }


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

    public String getBuildingId(String Building_Name){

        String site_id="";
        try {
            String query = "SELECT Auto_Id FROM building_detail Where Building_Name ='"+Building_Name+"'";

            Log.d("building_query",query);
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

        String floor_id="";
        try {
            String query = "SELECT Auto_Id FROM floor_detail Where Floor_Name ='"+Floor_Name+"'";

            Log.d("floor_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    floor_id=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return floor_id;
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

        String area_id="";
        try {
            String query = "SELECT Auto_Id FROM area_detail Where Area_Name ='"+Area_Name+"'";

            Log.d("area_query",query);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    area_id=res.getString(res.getColumnIndex("Auto_Id"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return area_id;
    }

    public String getAreaName(String Area_Id){

        String Area_Name="";
        try {
            String query = "SELECT Area_Name FROM area_detail Where Auto_Id ='"+Area_Id+"'";

            Log.d("Area_query",query+" :: "+Area_Id);
            SQLiteDatabase db = getWritableDatabase();
            Cursor res =db.rawQuery(query, null);
            if (res.moveToFirst()) {
                do {
                    Area_Name=res.getString(res.getColumnIndex("Area_Name"));
                } while (res.moveToNext());
            }
            res.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            String query = "SELECT Mobile_Number FROM feedback_sms where Site_Location_Id = '"+Site_Id+"'";

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

public byte[] readDataIcon(String strval) {


    byte[] bdata = null;
// Select All Query
    String Sqlstring = "Select Icon_value from feedback_icons_details Where Icon_Name='" + strval + "'";
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(Sqlstring, null);

    if (cursor.moveToFirst()) {
        do {
            bdata = cursor.getBlob(0);
        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();

    return bdata;
}

public int feedback_count() {
    SQLiteDatabase db1 = this.getWritableDatabase();
    Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_questions where Is_Rated = 'yes';", null);
    cursor1.moveToFirst();
    int count = cursor1.getInt(0);
    cursor1.close();
    db1.close();
    return count;
}
public int totalquestions_count() {
    SQLiteDatabase db1 = this.getWritableDatabase();
    Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_questions ;", null);
    cursor1.moveToFirst();
    int count = cursor1.getInt(0);
    cursor1.close();
   // db1.close();
    return count;
}



public boolean insertData (String AutoId ,String FeedbackQuestion ,String OrderId ,String IconType)
{
    SQLiteDatabase db=this.getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put("Auto_Id",AutoId);
    contentValues.put("Feedback_Question",FeedbackQuestion);
    contentValues.put("Order_Id",OrderId);
    contentValues.put("Icon_Type",IconType);
    long result=db.insert("feedback_questions",null,contentValues);
    if(result==-1)
        return false;
    else
        return true;
    
}

public boolean insertAreaDetails (String AutoId ,String Company_Id, String Area_Name)
{
    SQLiteDatabase db=this.getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put("Auto_Id",AutoId);
    contentValues.put("Company_Id",Company_Id);
    contentValues.put("Area_Name",Area_Name);
    long result=db.insert("area_details",null,contentValues);
    
    if(result==-1)
        return false;
    else
        return true;
    
}

public Boolean isOrderIdpresent(int order_id)
{
    SQLiteDatabase db1 = this.getWritableDatabase();
    Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_questions where Order_Id = '"+String.valueOf(order_id)+"' ;", null);
    cursor1.moveToFirst();
    int count = cursor1.getInt(0);
    cursor1.close();
    db1.close();
    if (count == 0) {
        return false;
    }else{
        return true;
    }
}


}
