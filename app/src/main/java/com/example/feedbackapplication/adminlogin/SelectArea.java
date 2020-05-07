package com.example.feedbackapplication.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.feedbackapplication.BaseActivity;
import com.example.feedbackapplication.FeedbackActivity;
import com.example.feedbackapplication.R;
import com.example.feedbackapplication.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

public class SelectArea extends BaseActivity {

    //    DatabaseHelper databaseHelper;
//    SQLiteDatabase sqLiteDatabase;
    String AreaName  ;//= "Cafeteria|Washroom";
    ArrayList<String> areaList;
    LinearLayout linearLayout, linearLayout1;
    private byte[] img = null;
    private byte[] img1 = null;
    int imagecount,rec_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_area);
//        databaseHelper = new DatabaseHelper(this);
//        sqLiteDatabase = databaseHelper.getWritableDatabase();
        imagecount = dbh.totalimage_count();
        if (imagecount == 0) {
            try {
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.cafeteria);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                img = bos.toByteArray();
                //myDB = new DatabaseHelper(this);

                ContentValues cv = new ContentValues();
                cv.put("Icon_Name", "Cafeteria");
                cv.put("Icon_value", img);
                cv.put("Icon_Type", "area");
//            cv.put("Area_Name", "Cafeteria");

                sqLiteDatabase.insert("feedback_admin_icondetails", null, cv);


                Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.washroom);
                ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                b1.compress(Bitmap.CompressFormat.PNG, 100, bos1);
                img1 = bos1.toByteArray();
                //myDB = new DatabaseHelper(this);

                ContentValues cv1 = new ContentValues();
                cv1.put("Icon_Name", "Washroom");
                cv1.put("Icon_value", img1);
                cv1.put("Icon_Type", "area");
//            cv1.put("Area_Name", "Cafeteria");

                sqLiteDatabase.insert("feedback_admin_icondetails", null, cv1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        linearLayout = new LinearLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(VERTICAL);
        //  linearLayout.setBackgroundColor(R.color.colorAccent);


        linearLayout1 = new LinearLayout(this);
        // new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);

        LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        mainLayoutParams.setMargins(10, 30, 10, 0);

        linearLayout1.setLayoutParams(mainLayoutParams);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setGravity(Gravity.CENTER);
        areaList = new ArrayList<String>();
        try {
            SQLiteDatabase db1 = dbh.getWritableDatabase();

            Cursor cursor1 = db1.rawQuery("Select Admin_Id,Area_Name from store_setting ;", null);


            if (cursor1.moveToFirst()) {
                do {
                    rec_id= cursor1.getInt(0);
                    AreaName = cursor1.getString(1);

                } while (cursor1.moveToNext());
                db1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AreaName.contains("|")) {
            String[] areas = AreaName.split("\\|");
            for (int i = 0; i < areas.length; i++) {
                areaList.add(areas[i]);
            }
        } else {
            areaList.add(AreaName);
        }

        for (int j = 0; j < areaList.size(); j++) {

            String area = areaList.get(j).toString();
            linearLayout1.addView(imageView(j + 1, area));
            /*if (area.equalsIgnoreCase("Cafeteria")) {
                linearLayout1.addView(imageView(j + 1, area));

            } else if (area.equalsIgnoreCase("Washroom")) {
                linearLayout1.addView(imageView(j + 1, area));

            }*/


        }
        linearLayout.addView(linearLayout1);
        setContentView(linearLayout);
    }

    private ImageView imageView(final int id, final String strvalue) {

        final String imgvalue = strvalue;
        ImageView imageView = new ImageView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(700, 300);
        params.setMargins(50, 20, 50, 20);
        imageView.setLayoutParams(params);
        Bitmap b = null;

           /* if (imgvalue.equalsIgnoreCase("Cafeteria")) {
                imageView.setBackgroundResource(R.drawable.cafeteria);
            } else if (imgvalue.equalsIgnoreCase("Washroom")) {
                imageView.setBackgroundResource(R.drawable.washroom);
            }*/
        byte[] image_str = dbh.readDataIcon(strvalue);
        ;
        try {
            b = BitmapFactory.decodeByteArray(image_str, 0, image_str.length);

            imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 600, 200, true));
        } catch (Exception e) {

            e.printStackTrace();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgvalue.equalsIgnoreCase("Cafeteria")) {
                    Intent i = new Intent(SelectArea.this, FeedbackActivity.class);
                    i.putExtra("rec_id",rec_id);
                    startActivity(i);
                } else if (imgvalue.equalsIgnoreCase("Washroom")) {
                    Intent i = new Intent(SelectArea.this, FeedbackService.class);
                    startActivity(i);
                }
            }
        });
        return imageView;
    }

    private TextView subtextView(int hint, String uname) {
        final TextView textView = new TextView(this);
        textView.setId(hint);
        textView.setTextSize(1, 40);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(uname);

        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        return textView;

    }
}
