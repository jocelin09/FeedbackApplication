package com.example.feedbackapplication.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

public class SelectArea extends BaseActivity {

    //    DatabaseHelper databaseHelper;
//    SQLiteDatabase sqLiteDatabase;
    String AreaName = "Cafeteria|Washroom";
    ArrayList<String> areaList;
    LinearLayout linearLayout, linearLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_area);
//        databaseHelper = new DatabaseHelper(this);
//        sqLiteDatabase = databaseHelper.getWritableDatabase();
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
       /* try {
            SQLiteDatabase db1 = dbh.getWritableDatabase();

            Cursor cursor1 = db1.rawQuery("Select Area_Name from store_setting ;", null);


            if (cursor1.moveToFirst()) {
                do {
                    AreaName = cursor1.getString(0);

                } while (cursor1.moveToNext());
                db1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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

            if (area.equalsIgnoreCase("Cafeteria")) {
                linearLayout1.addView(imageView(j + 1, area));

            } else if (area.equalsIgnoreCase("Washroom")) {
                linearLayout1.addView(imageView(j + 1, area));

            }


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

        if (imgvalue.equalsIgnoreCase("Cafeteria")) {
            imageView.setBackgroundResource(R.drawable.cafeteria);
        } else if (imgvalue.equalsIgnoreCase("Washroom")) {
            imageView.setBackgroundResource(R.drawable.washroom);
        }
//        byte[] image_str = dbh.readDataIcon(strvalue);
//        ;
//        try {
//            b = BitmapFactory.decodeByteArray(image_str, 0, image_str.length);
//
//            imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 100, 100, true));
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgvalue.equalsIgnoreCase("Cafeteria")) {
                   Intent i=new Intent(SelectArea.this,FeedbackActivity.class);
                   startActivity(i);
                } else if (imgvalue.equalsIgnoreCase("Washroom")) {
                    Intent i=new Intent(SelectArea.this,FeedbackService.class);
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
