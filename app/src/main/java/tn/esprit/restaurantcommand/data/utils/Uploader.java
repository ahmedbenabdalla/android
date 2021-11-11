package tn.esprit.restaurantcommand.data.utils;


import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class Uploader extends Activity {
    public static ImageView Image;
    private Activity activity;
    private Context context;
    private  Uri imagePath;

    public Uploader(Context context, Activity activity ){
       this.context=context;
       this.activity=activity;
    }

    public  void initConfig(Context context) {
        Map config = new HashMap();
        config.put("cloud_name", AppConstant.CLOUD_NAME);
        config.put("api_key",AppConstant.API_KEY);
        config.put("api_secret",AppConstant.API_SECRET);
        // config.put("secure", true);
        MediaManager.init(context, config);
    }

    public  void upload(){
        Uri u = Uri.parse(String.valueOf(this.imagePath));
        File f = new File("" + u);
           MediaManager.get().upload(imagePath).option("public_id", f.getName()).callback(new UploadCallback() {
               @Override
               public void onStart(String requestId) {Log.d(TAG, "onStart: "+requestId);
               }
               @Override
               public void onProgress(String requestId, long bytes, long totalBytes) {
                   Log.d(TAG, "onProgress: "+requestId);
               }
               @Override
               public void onSuccess(String requestId, Map resultData) {
                   String url=resultData.get("url").toString();
                   PreferencesUtils.save(url,"imageURL",context);
                   Log.d(TAG, "onSuccess: "+url);
               }
               @Override
               public void onError(String requestId, ErrorInfo error) {
                   Log.d(TAG, "onError: "+requestId);
               }
               @Override
               public void onReschedule(String requestId, ErrorInfo error) {
                   Log.d(TAG, "onReschedule: "+requestId);
               }
           }).dispatch();
    }
    public  void  requestPermission() {
        if(ContextCompat.checkSelfPermission(ContextCompat.createDeviceProtectedStorageContext(context), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            selectImage();
        }else{
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},AppConstant.IMAGE_REQ);
        }
    }
    public void selectImage() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, AppConstant.IMAGE_REQ);
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }

}