package com.example.feedbackapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();


    public String LoginDetails(String empcode) {
        String response = null;

        try {
            Log.d("UsernamePassword123456", " "+ empcode);
            String reqUrl =  new ApplicationClass().urlString()+"android/LoginDetailsv2.0.php";
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            String data = URLEncoder.encode("EmpCode", "UTF-8") + "=" + URLEncoder.encode(empcode, "UTF-8");
            Log.d("LoginUrlValue", reqUrl + "?" + data);
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            Log.d("checkResponse",response.toString());

            // Log.d("checkResponse","-->"+response.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    public String taskDataCall() {
        String response = null;
        ApplicationClass applicationClass;
        applicationClass = new ApplicationClass();
        try {
            String reqUrl =  applicationClass.urlString()+"android/DataDownloadv2.0.php";
            Log.d("fdsaf",reqUrl);
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());

            response = convertStreamToString(in);

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public String LoginDetails_main(String username, String password) {
        String response = null;
        try {
            String reqUrl =  new ApplicationClass().urlString()+"android/LoginDetails_20200529.php";
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            String data = URLEncoder.encode("Username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")+"&"+
                    URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") ;
            Log.d("LoginUrlValue", reqUrl + "?" + data);
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            Log.d("checkResponse",response.toString());

            // Log.d("checkResponse","-->"+response.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }


    public Bitmap ProfilePic(String profilename) {
//
        //http://45.118.160.162:81/punctualiti/com/dell_bangalore_feedback/android/imageslist.php
        String reqUrl =  new ApplicationClass().urlString()+"/AVFS/"+profilename+".jpg";

        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new URL(reqUrl).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    public Bitmap GetImage(String image_name) {
//
        //http://45.118.160.162:81/punctualiti/com/dell_bangalore_feedback/android/imageslist.php
        String reqUrl =  new ApplicationClass().urlString()+"/android/"+image_name;

        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new URL(reqUrl).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
                Log.d("JsonOUtput", sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}


