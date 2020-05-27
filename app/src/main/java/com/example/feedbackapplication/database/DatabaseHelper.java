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

public class DatabaseHelper extends SQLiteOpenHelper {
private static DatabaseHelper mInstance = null;

public DatabaseHelper(Context context) {
    super(context, "pun_feedback.db", null, 1);
}

public static DatabaseHelper getInstance(Context ctx) {
    
    // Use the application context, which will ensure that you
    // don't accidentally leak an Activity's context.
    // See this article for more information: http://bit.ly/6LRzfx
    if (mInstance == null) {
        mInstance = new DatabaseHelper(ctx.getApplicationContext());
    }
    return mInstance;
}

@Override
public void onCreate(SQLiteDatabase sqLiteDatabase) {
    
    sqLiteDatabase.execSQL(" CREATE TABLE SyncInfo (Id INTEGER PRIMARY KEY, Auto_Id TEXT, Mac_Address TEXT, Apk_Version TEXT, Sync_Date_Time TEXT, Device_Name TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE admin_login (ID INTEGER PRIMARY KEY,User_Id TEXT,Client_Id TEXT, User_Name TEXT, Password TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE admin_details(ID INTEGER PRIMARY KEY,Company_ID TEXT,Company_Name TEXT,Location_Id TEXT,Location_Name TEXT,Site_Id TEXT, Site_Name TEXT,Building_Id TEXT, Building_Name TEXT,Wing_Id TEXT,Wing_Name TEXT,Floor_Id TEXT, Floor_Name TEXT,Area_Id TEXT, Area_Name TEXT,Feedback_Service_Type text, RecordStatus TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE feedback_adminquestions (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Question TEXT,Order_Id TEXT, Area_Id TEXT, Weightage TEXT,EmailSMS TEXT,RecordStatus TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE feedback_adminsubquestions (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Feedback_Id TEXT, Feedback_Sub_Question TEXT,Icon Blob, Order_Id TEXT,Weightage TEXT,RecordStatus TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE feedback_admin_icondetails (ID INTEGER PRIMARY KEY ,Auto_Id TEXT, Icon_Name TEXT, Icon_value BLOB,Icon_Type TEXT, Status TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE feedback_details (ID INTEGER PRIMARY KEY ,Rec_Id TEXT,Admin_Id TEXT, Supervisor_Id TEXT, Display_Name Text,Feedback_DateTime TEXT, UpdatedStatus TEXT,Last_IP TEXT,Apk_Web_Version TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE feedback_userquestiondata (ID INTEGER PRIMARY KEY ,Rec_Id TEXT,Question_ID TEXT, Feedback_Icon_Id TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE feedback_usersubquestiondata (ID INTEGER PRIMARY KEY ,Rec_Id TEXT,Question_ID TEXT,Sub_Question_ID TEXT,Response TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE emp_ratings(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,Emp_Id TEXT,Ratings TEXT,UpdatedStatus TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE emp_neg_ratings(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,Emp_Feedback_Id TEXT,Name TEXT,Email TEXT,Contact TEXT,Comment TEXT,UpdatedStatus TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE EmailSMSList (Id INTEGER PRIMARY KEY, Auto_Id TEXT,Building_Id TEXT, Employee_Email TEXT, Recipient_Type TEXT,Mobile_Number TEXT, Record_Status TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE sms_master(Id INTEGER PRIMARY KEY ,Auto_Id TEXT,UserName TEXT, Password TEXT, Type TEXT, Source TEXT, URL TEXT)");
    sqLiteDatabase.execSQL(" CREATE TABLE store_setting(Admin_Id INTEGER PRIMARY KEY , Auto_Id TEXT,Company_Name TEXT,Location_Name TEXT, Site_Name TEXT, Building_Name TEXT,Wing_Name TEXT, Floor_Name TEXT, Virtual_Area_Name TXET, Area_Name TEXT, Feedback_Service_Name TEXT, Display_Name TEXT, Checked_Display_Name TEXT,Active_Setting Text,Icon_Type TEXT,Question_Timeout TEXT,Thankyou_Timeout TEXT,ShowLogo TEXT,Icon_List TEXT)");

}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

}

/////COUNT QUERIES//
public int feedback_count() {
    SQLiteDatabase db1 = this.getWritableDatabase();
    Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_adminquestions where Weightage = 'yes';", null);
    cursor1.moveToFirst();
    int count = cursor1.getInt(0);
    cursor1.close();
    db1.close();
    return count;
}

public int admindetails_count() {
    SQLiteDatabase db1 = this.getWritableDatabase();
    Cursor cursor1 = db1.rawQuery("Select count(*) from admin_details;", null);
    cursor1.moveToFirst();
    int count = cursor1.getInt(0);
    cursor1.close();
    db1.close();
    return count;
}

public int totalquestions_count() {
    SQLiteDatabase db1 = this.getWritableDatabase();
    Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_adminquestions ;", null);
    cursor1.moveToFirst();
    int count = cursor1.getInt(0);
    cursor1.close();
    // db1.close();
    return count;
}

    public int totalimage_count() {
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_admin_icondetails ;", null);
        cursor1.moveToFirst();
        int count = cursor1.getInt(0);
        cursor1.close();
        // db1.close();
        return count;
    }
    public int totalsmileyimage_count() {
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_admin_icondetails where Icon_Type='Smiley';", null);
        cursor1.moveToFirst();
        int count = cursor1.getInt(0);
        cursor1.close();
        // db1.close();
        return count;
    }

    public int totalfeedbackcount() {
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_userquestiondata ;", null);
        cursor1.moveToFirst();
        int count = cursor1.getInt(0);
        cursor1.close();
        // db1.close();
        return count;
    }

    public int totalsubfeedbackcount() {
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_usersubquestiondata ;", null);
        cursor1.moveToFirst();
        int count = cursor1.getInt(0);
        cursor1.close();
        // db1.close();
        return count;
    }
    
//INSERTION///
public boolean insertLoginDetails(String User_Id,String Client_Id, String User_Name, String Password) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    
    contentValues.put("User_Id", User_Id);
    contentValues.put("Client_Id", Client_Id);
    contentValues.put("User_Name", User_Name);
    contentValues.put("Password", Password);
    
    long result = db.insert("admin_login", null, contentValues);
    
    if (result == -1)
        return false;
    else
        return true;
    
}


public boolean insertAdminDetails(String Company_ID, String Company_Name, String Location_Id, String Location_Name,
                                  String Site_Id, String Site_Name, String Building_Id, String Building_Name,
                                  String Wing_Id,String Wing_Name,String Floor_Id,String Floor_Name,String Area_Id,String Area_Name,String Feedback_Service_Type) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();

    contentValues.put("Company_ID", Company_ID);
    contentValues.put("Company_Name", Company_Name);
    contentValues.put("Location_Id", Location_Id);
    contentValues.put("Location_Name", Location_Name);

    contentValues.put("Site_Id", Site_Id);
    contentValues.put("Site_Name", Site_Name);
    contentValues.put("Building_Id", Building_Id);
    contentValues.put("Building_Name", Building_Name);

    contentValues.put("Wing_Id", Wing_Id);
    contentValues.put("Wing_Name", Wing_Name);
    contentValues.put("Floor_Id", Floor_Id);
    contentValues.put("Floor_Name", Floor_Name);
    
    contentValues.put("Area_Id", Area_Id);
    contentValues.put("Area_Name", Area_Name);
    contentValues.put("Feedback_Service_Type", Feedback_Service_Type);
    
    long result = db.insert("admin_details", null, contentValues);
    
    if (result == -1)
        return false;
    else
        return true;
    
}


public boolean insertStoreSettings(String Auto_Id, String Company_Name, String Location_Name, String Site_Name, String Building_Name,
                                  String Wing_Name,String Floor_Name,String Virtual_Area,String Area_Name,String Feedback_Service_Name,String Timeout,String ShowLogo,String Icon_List) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    
    contentValues.put("Auto_Id", Auto_Id);
    contentValues.put("Company_Name", Company_Name);
    contentValues.put("Location_Name", Location_Name);
    contentValues.put("Site_Name", Site_Name);
    contentValues.put("Building_Name", Building_Name);
    contentValues.put("Wing_Name", Wing_Name);
    contentValues.put("Floor_Name", Floor_Name);
    contentValues.put("Virtual_Area_Name", Virtual_Area);
    contentValues.put("Area_Name", Area_Name);
    contentValues.put("Feedback_Service_Name", Feedback_Service_Name);
    contentValues.put("Question_Timeout",Timeout);
    contentValues.put("ShowLogo",ShowLogo);
    contentValues.put("Icon_List",Icon_List);
    long result = db.insert("store_setting", null, contentValues);
    
    if (result == -1)
        return false;
    else
        return true;
    
}

    public boolean insertFeedbackData(String Rec_Id, String Question_ID, String Feedback_Icon_Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Rec_Id", Rec_Id);
        contentValues.put("Question_ID", Question_ID);
        contentValues.put("Feedback_Icon_Id", Feedback_Icon_Id);
        //contentValues.put("Icon_Type",IconType);
        //    sqLiteDatabase.execSQL(" CREATE TABLE feedback_usersubquestiondata (ID INTEGER PRIMARY KEY ,Rec_Id TEXT,Question_ID TEXT,Sub_Question_ID TEXT,Response TEXT)");
        long result = db.insert("feedback_userquestiondata", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }
    public boolean insertsubFeedbackData(String Rec_Id ,String Question_ID ,String Sub_Question_ID ,String Response) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Rec_Id", Rec_Id);
        contentValues.put("Question_ID", Question_ID);
        contentValues.put("Sub_Question_ID", Sub_Question_ID);
        contentValues.put("Response",Response);
        //    sqLiteDatabase.execSQL(" CREATE TABLE feedback_usersubquestiondata (ID INTEGER PRIMARY KEY ,Rec_Id TEXT,Question_ID TEXT,Sub_Question_ID TEXT,Response TEXT)");
        long result = db.insert("feedback_usersubquestiondata", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }


public boolean insertData(String AutoId, String FeedbackQuestion, String OrderId, String IconType) {//,String AreaId
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("Auto_Id", AutoId);
    contentValues.put("Feedback_Question", FeedbackQuestion);
    contentValues.put("Order_Id", OrderId);
    //contentValues.put("Icon_Type",IconType);
    long result = db.insert("feedback_adminquestions", null, contentValues);
    if (result == -1)
        return false;
    else
        return true;
    
}

public boolean store_EmpRating(String AutoId,String Emp_Id,String Ratings,String UpdatedStatus){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("Auto_Id", AutoId);
    contentValues.put("Emp_Id", Emp_Id);
    contentValues.put("Ratings", Ratings);
    contentValues.put("UpdatedStatus",UpdatedStatus);
    long result = db.insert("emp_ratings", null, contentValues);
    if (result == -1)
        return false;
    else
        return true;
    //emp_ratings
}


//Get all data in spinner-dropdown///
public ArrayList<String> getAllCompanyNames(){
    ArrayList<String> company_name_lists = new ArrayList<String>();
    company_name_lists.add("Select Company");
    String selectQuery = "SELECT distinct(Company_Name) FROM admin_details ORDER BY Company_Name ASC ";
    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    if (cursor.moveToFirst()) {
        do {
            company_name_lists.add(cursor.getString(0));
        } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return company_name_lists;
}

public ArrayList<String> getAllCompanyNames(String client_id){
    ArrayList<String> company_name_lists = new ArrayList<String>();
    //company_name_lists.add("Select Company");
//    String selectQuery = "SELECT distinct(Company_Name) FROM admin_details where Company_ID = '"+client_id+"' ORDER BY Company_Name ASC ";
    String selectQuery = "SELECT Company_Name FROM admin_details GROUP BY Company_Name "; // where Company_ID = '"+client_id+"' ORDER BY Company_Name ASC ";
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    if (cursor.moveToFirst()) {
        do {
            company_name_lists.add(cursor.getString(0));
        } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return company_name_lists;
}

public ArrayList<String> getAllLocations(String company){
    ArrayList<String> location_name_lists = new ArrayList<String>();
    //location_name_lists.add("Select Location");
    // Select All Query
    String selectQuery = "SELECT distinct(Location_Name) FROM admin_details where Company_Name = '"+company+"'";
    String loc_name="";
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            loc_name = cursor.getString(0);
            if (!loc_name.equals(""))
            {
                location_name_lists.add(loc_name);
            }
        } while (cursor.moveToNext());
    }
    
    cursor.close();
    db.close();
    
    return location_name_lists;
}

public ArrayList<String> getAllSites(String companynm,String locationname){
    System.out.println(" getAllSites called= " );
    
    ArrayList<String> site_name_lists = new ArrayList<String>();
    //site_name_lists.add("Select Site");
    String selectQuery = "SELECT distinct(Site_Name) FROM admin_details where Company_Name = '"+companynm+"' and Location_Name = '"+locationname+"'";
    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    String sitename="";
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            sitename = cursor.getString(0);
            if (!sitename.equals(""))
            {
                site_name_lists.add(sitename);
            }
        } while (cursor.moveToNext());
    }
    System.out.println("site_name_lists = " + site_name_lists);
    
    cursor.close();
    db.close();
    
    return site_name_lists;
}

public ArrayList<String> getAllBuildings(String companynm,String locationname,String sitenm){
    System.out.println(" getAllBuildings called= " );
    ArrayList<String> building_name_lists = new ArrayList<String>();
    // Select All Query
    System.out.println("locationname="+locationname+sitenm);
    String selectQuery = "SELECT distinct(Building_Name) FROM admin_details where Company_Name = '"+companynm+"' and Location_Name = '"+locationname+"' and Site_Name = '"+sitenm+"'";
    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    String buildingname="";
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            buildingname = cursor.getString(0);
            if (!buildingname.equals(""))
            {
                building_name_lists.add(buildingname);
            }
        } while (cursor.moveToNext());
    }
    System.out.println("building_name_lists = " + building_name_lists);
    
    cursor.close();
    db.close();
    
    return building_name_lists;
}

public ArrayList<String> getAllWings(String companynm,String locationname,String sitenm,String buildingnm){
    System.out.println(" getAllWings called= " );
    ArrayList<String> wing_name_lists = new ArrayList<String>();
    // Select All Query
    String selectQuery = "SELECT distinct(Wing_Name) FROM admin_details where Company_Name = '"+companynm+"' and Location_Name = '"+locationname+"' and Site_Name = '"+sitenm+"' and Building_Name = '"+buildingnm+"'";
    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    String wingname="";
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            wingname = cursor.getString(0);
            if (!wingname.equals(""))
            {
                wing_name_lists.add(wingname);
            }
        } while (cursor.moveToNext());
    }
    System.out.println("wing_name_lists = " + wing_name_lists);
    
    cursor.close();
    db.close();
    
    return wing_name_lists;
}

public ArrayList<String> getAllFloors(String companynm,String locationname,String sitenm,String buildingnm,String wingnm){
    System.out.println(" getAllFloors called= " );
    ArrayList<String> floor_name_lists = new ArrayList<String>();
    // Select All Query
    String selectQuery = "SELECT distinct(Floor_Name) FROM admin_details where Company_Name = '"+companynm+"' and Location_Name = '"+locationname+"' and Site_Name = '"+sitenm+"' and Building_Name = '"+buildingnm+"' and Wing_Name = '"+wingnm+"'";
    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    String floorname="";
    if (cursor.moveToFirst()) {
        do {
            floorname = cursor.getString(0);
            if (!floorname.equals(""))
            {
                floor_name_lists.add(floorname);
            }
        } while (cursor.moveToNext());
    }
    System.out.println("floor_name_lists = " + floor_name_lists);
    
    cursor.close();
    db.close();
    
    return floor_name_lists;
}

public ArrayList<String> getAllAreas(String companynm,String locationname,String sitenm,String buildingnm,String wingnm,String floornm){
    ArrayList<String> area_name_lists = new ArrayList<String>();
    String selectQuery = "SELECT distinct(Area_Name) FROM admin_details where Company_Name = '"+companynm+"' and Location_Name = '"+locationname+"' and Site_Name = '"+sitenm+"' and Building_Name = '"+buildingnm+"' and Wing_Name = '"+wingnm+"' and Floor_Name = '"+floornm+"'";
    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    String areaname="";
    if (cursor.moveToFirst()) {
        do {
            areaname = cursor.getString(0);
            if (!areaname.equals(""))
            {
                area_name_lists.add(areaname);
            }
        } while (cursor.moveToNext());
    }
    System.out.println("area_name_lists = " + area_name_lists);
    
    cursor.close();
    db.close();
    
    return area_name_lists;
}



///GET ID DATA///
public String getCompanyId(String Company_name) {
    String company_id="" ;
    try {
        String query = "SELECT * FROM admin_details Where Company_Name ='" + Company_name + "'";
        
        Log.d("company_query", query);
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                company_id = res.getString(1);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("e 332 = " + e);
    }
    
   // System.out.println("company_id = " + company_id);
    
    return company_id;
}

public String getLocationId(String Location_Name) {
    String location_id="" ;
    try {
        String query = "SELECT Location_Id FROM admin_details Where Location_Name ='" + Location_Name + "'";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                location_id = res.getString(0);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return location_id;
}

public String getSiteId(String Site_name) {
    
    String site_id = "";
    try {
        String query = "SELECT Site_Id FROM admin_details Where Site_Name ='" + Site_name + "'";
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                site_id = res.getString(0);
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
    
    String building_id = "";
    try {
        String query = "SELECT Building_Id FROM admin_details Where Building_Name ='" + Building_Name + "'";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                building_id = res.getString(0);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return building_id;
}

public String getWingId(String Wing_Name) {
    
    String wing_id = "";
    try {
        String query = "SELECT Wing_Id FROM admin_details Where Wing_Name ='" + Wing_Name + "'";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                wing_id = res.getString(0);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return wing_id;
}

public String getFloorId(String Floor_Name) {
    
    String floor_id = "";
    try {
        String query = "SELECT Floor_Id FROM admin_details Where Floor_Name ='" + Floor_Name + "'";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                floor_id = res.getString(0);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return floor_id;
}

public String getAreaId(String Area_Name) {
    
    String area_id = "";
    try {
        String query = "SELECT Area_Id FROM admin_details Where Area_Name ='" + Area_Name + "'";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                area_id = res.getString(0);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return area_id;
}

public String getFeedbackServiceType(String Client_ID) {
    
    String feedbackservice_type = "";
    try {
        String query = "SELECT Feedback_Service_Type FROM admin_details Where Company_ID ='" + Client_ID + "'";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                feedbackservice_type = res.getString(0);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return feedbackservice_type;
}


public String getsmsURL() {
    
    String url = "";
    try {
        String query = "SELECT URL FROM sms_master";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                url = res.getString(res.getColumnIndex("URL"));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return url;
}

public String getsmsUsername() {
    
    String UserName = "";
    try {
        String query = "SELECT UserName FROM sms_master";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                UserName = res.getString(res.getColumnIndex("UserName"));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return UserName;
}

public String getsmsPassword() {
    
    String Password = "";
    try {
        String query = "SELECT Password FROM sms_master";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                Password = res.getString(res.getColumnIndex("Password"));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return Password;
}

public String getsmsType() {
    
    String Type = "";
    try {
        String query = "SELECT Type FROM sms_master";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                Type = res.getString(res.getColumnIndex("Type"));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return Type;
}

public String getsmsSource() {
    
    String Source = "";
    try {
        String query = "SELECT Source FROM sms_master";
        
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                Source = res.getString(res.getColumnIndex("Source"));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return Source;
}

public int loginCount() {
    int count = 0;
    try {
        String query = "SELECT * FROM user_master";
        
        Log.d("ASdasdasdasd", query);
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        count = res.getCount();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}







public String getBuildingName(String floor_Id) {
    
    String Building_Name = "";
    try {
        String query = "SELECT Building_Name FROM building_detail Where Auto_Id ='" + floor_Id + "'";
        
        Log.d("floor_query1", query);
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                Building_Name = res.getString(res.getColumnIndex("Building_Name"));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return Building_Name;
}


public String getAreaName(String Area_Id) {
    
    String Area_Name = "";
    try {
        String query = "SELECT Area_Name FROM area_detail Where Auto_Id ='" + Area_Id + "'";
        
        Log.d("Area_query", query + " :: " + Area_Id);
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                Area_Name = res.getString(res.getColumnIndex("Area_Name"));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return Area_Name;
}

public String getExistSiteId(String Site_Id) {
    String site_id = "";
    try {
        String query = "SELECT * FROM EmailList Where Site_Location_Id ='" + Site_Id + "'";
        
        Log.d("location_query", query);
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                site_id = res.getString(res.getColumnIndex("Site_Location_Id"));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return site_id;
}

public String emailList_Cc(String BuildingId) {
    String emailquery = "Select Building_Id, Employee_Email from EmailList where Building_Id = '" + BuildingId + "' AND Recipient_Type = 'Cc'";
    SQLiteDatabase database = this.getWritableDatabase();
    
    Cursor emailList = database.rawQuery(emailquery, null);
    StringBuilder sb = new StringBuilder();
    
    if (emailList.moveToFirst()) {
        do {
            sb.append(emailList.getString(emailList.getColumnIndex("Employee_Email")) + ",");
        } while (emailList.moveToNext());
        Log.d("Email_Id_Value", sb.toString());
        
    }
    
    if (sb.lastIndexOf(",") != -1) {
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    } else {
        return sb.toString();
    }
}

public String emailList_Bcc(String BuildingId) {
    String emailquery = "Select Building_Id, Employee_Email from EmailList where Building_Id = '" + BuildingId + "' AND Recipient_Type = 'Bcc'";
    SQLiteDatabase database = this.getWritableDatabase();
    
    Cursor emailList = database.rawQuery(emailquery, null);
    StringBuilder sb = new StringBuilder();
    
    if (emailList.moveToFirst()) {
        do {
            sb.append(emailList.getString(emailList.getColumnIndex("Employee_Email")) + ",");
        } while (emailList.moveToNext());
        Log.d("Email_Id_Value", sb.toString());
        
    }
    
    if (sb.lastIndexOf(",") != -1) {
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    } else {
        return sb.toString();
    }
}

public String emailList_To(String BuildingId) {
    String emailquery = "Select Building_Id, Employee_Email from EmailList where Building_Id = '" + BuildingId + "' AND Recipient_Type = 'To'";
    SQLiteDatabase database = this.getWritableDatabase();
    
    Cursor emailList = database.rawQuery(emailquery, null);
    StringBuilder sb = new StringBuilder();
    
    if (emailList.moveToFirst()) {
        do {
            sb.append(emailList.getString(emailList.getColumnIndex("Employee_Email")) + ",");
        } while (emailList.moveToNext());
        Log.d("Email_Id_Value", sb.toString());
        
    }
    
    if (sb.lastIndexOf(",") != -1) {
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    } else {
        return sb.toString();
    }
}

public String getfeedBackDetails(String feedbackName) {
    
    String feedbackId = "";
    try {
        String query = "SELECT Auto_Id FROM feedback_admin_icondetails Where FeedBack_Name ='" + feedbackName + "'";
        
        Log.d("ASdasdasdasd", query);
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                feedbackId = res.getString(res.getColumnIndex("Auto_Id"));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return feedbackId;
}

public String getMobileNumber(String Site_Id) {
    StringBuilder stringBuilder = new StringBuilder();
    String feedbackId = "";
    try {
        String query = "SELECT Mobile_Number FROM feedback_sms where Site_Location_Id = '" + Site_Id + "'";
        
        Log.d("ASdasdasdasd", query);
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        if (res.moveToFirst()) {
            do {
                feedbackId = res.getString(res.getColumnIndex("Mobile_Number"));
                stringBuilder.append(feedbackId + ",");
            } while (res.moveToNext());
            Log.d("sjhfddjhv", stringBuilder.toString());
            
        }
        res.close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    if (stringBuilder.lastIndexOf(",") != -1) {
        return stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(",")).toString();
    } else {
        return stringBuilder.toString();
    }
}

public boolean insertBitmapUserId(Bitmap EmpImage, String Username) {
    boolean image = false;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    EmpImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
    
    ByteArrayOutputStream meterOut = new ByteArrayOutputStream();
    byte[] selfieBuffer = out.toByteArray();
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values;
    try {
        values = new ContentValues();
        values.put("EmpImage", selfieBuffer);
        long i = db.update("user_master", values, "Username ='" + Username + "'", null);
        if (i == -1)
            image = false;
        else
            image = true;
        Log.i("InsertValues", image + "");
        // Insert into database successfully.
    } catch (SQLiteException e) {
        e.printStackTrace();
        
    }
    return image;
    
    
}

public boolean insertBitmap(Bitmap EmpImage, String User_Id) {
    boolean image = false;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    EmpImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
    
    ByteArrayOutputStream meterOut = new ByteArrayOutputStream();
    byte[] selfieBuffer = out.toByteArray();
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values;
    try {
        values = new ContentValues();
        values.put("EmpImage", selfieBuffer);
        long i = db.update("user_master", values, "User_Id ='" + User_Id + "'", null);
        if (i == -1)
            image = false;
        else
            image = true;
        Log.i("InsertValues", image + "");
        // Insert into database successfully.
    } catch (SQLiteException e) {
        e.printStackTrace();
        
    }
    return image;
    
    
}

public Bitmap getProfilePic(String UserId) {
    Bitmap bitmap = null;
    try {
        
        byte[] image = null;
        SQLiteDatabase db = this.getWritableDatabase();
        
        String formQuery1 = "select EmpImage from user_master where User_Id='" + UserId + "' AND Username != 'GuestUser'";
        Cursor img = db.rawQuery(formQuery1, null);
        Log.d("valsdasdasd", formQuery1 + " " + img.getCount());
        
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
    String Sqlstring = "Select Icon_value from feedback_admin_icondetails Where Icon_Name='" + strval + "'";
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


public Boolean isOrderIdpresent(int order_id) {
    SQLiteDatabase db1 = this.getWritableDatabase();
    Cursor cursor1 = db1.rawQuery("Select count(*) from feedback_adminquestions where Order_Id = '" + String.valueOf(order_id) + "' ;", null);
    cursor1.moveToFirst();
    int count = cursor1.getInt(0);
    cursor1.close();
    db1.close();
    if (count == 0) {
        return false;
    } else {
        return true;
    }
}


}
