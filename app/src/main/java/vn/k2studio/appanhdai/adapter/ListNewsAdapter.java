package vn.k2studio.appanhdai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import vn.k2studio.appanhdai.R;
import vn.k2studio.appanhdai.Utils.DrawTextInImage;
import vn.k2studio.appanhdai.Utils.Utils;
import vn.k2studio.appanhdai.model.ItemNew;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ItemNewViewHolder> {
    private ArrayList<ItemNew> mItemNews;
    private Context mContext;
    private int width;

    public ListNewsAdapter(ArrayList<ItemNew> itemNews, Context context) {
        mItemNews = itemNews;
        mContext = context;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        Log.e("width: ", width + "");
        //        width = Utils.convertPixelsToDp(width, mContext);
        //        Log.e("widthdp: ", width + "");
    }

    @NonNull
    @Override
    public ItemNewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_news, parent, false);
        return new ItemNewViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ItemNewViewHolder holder, int position) {
        ItemNew itemNew = mItemNews.get(position);
        holder.mItemListTxtName.setText(itemNew.getNameOwnPost());
        Glide.with(mContext)
                .load(itemNew.getAvaOwnPost())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mItemListImgAvatar);
        holder.mItemListTxtTitleNew.setText(itemNew.getTitleNew());
        holder.mItemListTxtDescriptionNew.setText(itemNew.getDescripNew());
        final ArrayList<Integer> listImage = itemNew.getListImageNew();
        int countImage = width / 250;
        final int size = 250;
        boolean more = true;
        if (countImage > listImage.size()) {
            countImage = listImage.size();
            more = false;
            //            size = width/(listImage.size())-30;
            //            Log.e("size: ", size + "");
        }
        for (int i = 0; i < countImage; i++) {
            final ImageView imageView = new ImageView(mContext);
            imageView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_border));
            imageView.setPadding(2, 2, 2, 2);
            if (more && i == (countImage - 1)) {
                //                new DrawTextInImage(mContext){
                //                    @Override
                //                    protected void onPostExecute(Bitmap bitmap) {
                //                        super.onPostExecute(bitmap);
                //                        Glide.with(mContext)
                //                                .load(bitmap)
                //                                .apply(new RequestOptions().override(size, size)
                //                                        .transform(new RoundedCorners(20)))
                //                                .into(imageView);
                //                    }
                //                }.execute("abc");
                FrameLayout frameLayout = new FrameLayout(mContext);
                TextView textView = new TextView(mContext);
                textView.setText("+" + (listImage.size() - countImage));
                textView.setBackground(
                        mContext.getResources().getDrawable(R.drawable.bg_border_tv));
                textView.setTextColor(Color.parseColor("#ffffffff"));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(18);
                Glide.with(mContext)
                        .load(listImage.get(i))
                        .apply(new RequestOptions().override(size, size)
                                .transform(new RoundedCorners(20)))
                        .into(imageView);
                frameLayout.addView(imageView);
                frameLayout.addView(textView);
                holder.mItemListLnListImage.addView(frameLayout);
            } else {
                Glide.with(mContext)
                        .load(listImage.get(i))
                        .apply(new RequestOptions().override(size, size)
                                .transform(new RoundedCorners(20)))
                        .into(imageView);
                holder.mItemListLnListImage.addView(imageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItemNews.size();
    }

    public static class ItemNewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_img_avatar)
        ImageView mItemListImgAvatar;
        @BindView(R.id.item_list_txt_name)
        TextView mItemListTxtName;
        @BindView(R.id.item_list_txt_title_new)
        TextView mItemListTxtTitleNew;
        @BindView(R.id.item_list_txt_description_new)
        TextView mItemListTxtDescriptionNew;
        @BindView(R.id.item_list_ln_list_image)
        LinearLayout mItemListLnListImage;

        public ItemNewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
