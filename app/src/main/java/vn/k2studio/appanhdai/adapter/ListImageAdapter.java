package vn.k2studio.appanhdai.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import java.util.List;
import vn.k2studio.appanhdai.R;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ListImageViewHolder> {
    private Context mContext;
    private List<Uri> mUriList;
    private OnHandleClickImageListener mListener;

    public ListImageAdapter(Context context, List<Uri> uriList) {
        mContext = context;
        mUriList = uriList;
    }

    public void setListener(OnHandleClickImageListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ListImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image_add, parent, false);
        return new ListImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListImageViewHolder holder, final int position) {
        Uri uri = mUriList.get(position);
        Glide.with(mContext).load(uri).into(holder.mItemImage);
        holder.mItemImageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelImage(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUriList.size();
    }

    public static class ListImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        ImageView mItemImage;
        @BindView(R.id.item_image_cancel)
        ImageView mItemImageCancel;

        public ListImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnHandleClickImageListener{
        void onCancelImage(int position);
    }
}
