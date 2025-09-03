
package me.hauvo.thumbnail;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.FileOutputStream;


public class RNThumbnailModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNThumbnailModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNThumbnail";
    }

    @ReactMethod
    public void get(String filePath, Promise promise) {
        if (filePath.contains("thumb-")) {
            promise.resolve(filePath.startsWith("file://") ? filePath : "file://" + filePath);
            return;
        }
        filePath = filePath.replace("file://", "");
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filePath);
        Bitmap image = retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);

        String fullPath = reactContext.getCacheDir() + "/thumb";

        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File fName = new File(filePath);
            String fileNameStr = fName.getName();

            String fileName = "thumb-" + fileNameStr.substring(0, filePath.lastIndexOf(".")) + ".jpeg";
            File file = new File(fullPath, fileName);
            file.createNewFile();

            FileOutputStream fOut = new FileOutputStream(file);

            // 100 means no compression, the lower you go, the stronger the compression
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

            // MediaStore.Images.Media.insertImage(reactContext.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

            promise.resolve("file://" + fullPath + "/" + fileName);

        } catch (Exception e) {
            Log.e("E_RNThumnail_ERROR", e.getMessage());
            promise.reject("E_RNThumnail_ERROR", e.getMessage());
        }
    }
}
