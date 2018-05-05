package vn.k2studio.appanhdai.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import vn.k2studio.appanhdai.R;

public class DrawTextInImage extends AsyncTask<String,Void, Bitmap> {
    Context mContext;

    public DrawTextInImage(Context context) {
        this.mContext = context;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        String countImageHide = strings[0];
        Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doan);

        Bitmap.Config config = bm.getConfig();
        int width = bm.getWidth();
        int height = bm.getHeight();

        Bitmap newImage = Bitmap.createBitmap(width, height, config);

        Canvas c = new Canvas(newImage);
        c.drawBitmap(bm, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        c.drawText("Some Text", 0, 25, paint);
        return newImage;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
