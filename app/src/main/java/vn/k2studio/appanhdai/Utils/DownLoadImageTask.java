package vn.k2studio.appanhdai.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStream;

public class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {

    public void DownloadImageTask() {
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bm = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bm = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bm;
    }

    protected void onPostExecute(Bitmap result) {
    }
}