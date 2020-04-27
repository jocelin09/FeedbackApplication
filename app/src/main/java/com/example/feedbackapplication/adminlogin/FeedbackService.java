package com.example.feedbackapplication.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

public class FeedbackService extends BaseActivity {

    String Gender = "Male|Female";
    ArrayList<String> areaList;
    LinearLayout linearLayout, linearLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        if (Gender.contains("|")) {
            String[] areas = Gender.split("\\|");
            for (int i = 0; i < areas.length; i++) {
                areaList.add(areas[i]);
            }
        } else {
            areaList.add(Gender);
        }

        for (int j = 0; j < areaList.size(); j++) {

            String area = areaList.get(j).toString();

            if (area.equalsIgnoreCase("Male")) {
                linearLayout1.addView(imageView(j + 1, area));
//                linearLayout1.addView(subtextView(area));

            } else if (area.equalsIgnoreCase("Female")) {
                linearLayout1.addView(imageView(j + 1, area));
//                linearLayout1.addView(subtextView(area));
            }


        }
        linearLayout.addView(linearLayout1);
        setContentView(linearLayout);
    }

    private ImageView imageView(final int id, final String strvalue) {

        final String imgvalue = strvalue;
        ImageView imageView = new ImageView(this);
        imageView.setForegroundGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 700);
        params.setMargins(50, 20, 50, 20);
        imageView.setLayoutParams(params);
        Bitmap b = null;

        if (imgvalue.equalsIgnoreCase("Male")) {
            imageView.setBackgroundResource(R.drawable.men_sign);
        } else if (imgvalue.equalsIgnoreCase("Female")) {
            imageView.setBackgroundResource(R.drawable.women_sign);
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
                if (imgvalue.equalsIgnoreCase("Male")) {

                } else if (imgvalue.equalsIgnoreCase("Female")) {

                }
            }
        });
        return imageView;
    }

    private TextView subtextView(String uname) {
        final TextView textView = new TextView(this);
//        textView.setId(hint);
        textView.setTextSize(1, 40);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(uname);

        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        return textView;

    }

}
