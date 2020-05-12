package com.example.feedbackapplication.adminlogin;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyDownloadWorker extends Worker {

public MyDownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
}

@NonNull
@Override
public Result doWork() {
    Context context = getApplicationContext();
    //receiving data from activity
    String workType = getInputData().getString("workType");
    
    Log.d("MyDownloadWorker", "downloading data.... "+workType);
    System.out.println("workType = " + workType);
    
    
    //sending data back to activity
    String outputdata = "Done doing work";
    Data refreshTime = new Data.Builder()
                               .putString("refreshTime", outputdata)
                               .build();
    //setOutputData(refreshTime);
    return Result.success(refreshTime);
}


}
