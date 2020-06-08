package com.viettel.construction.screens.atemp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.viettel.construction.screens.atemp.other.FullScreenRefundActivity;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionImageInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageRefundAdapter extends RecyclerView.Adapter<ImageRefundAdapter.ImageViewHolder> {
    private List<ConstructionImageInfo> mList;
    private Context mContext;
    private Boolean isShow;
    private RequestOptions requestOptions;
    private ImageRefundAdapter.OnClickDelete onClickDelete;

    public ImageRefundAdapter(List<ConstructionImageInfo> mList, Context context, Boolean isShow,ImageRefundAdapter.OnClickDelete onClickDelete) {
        this.mList = mList;
        this.isShow = isShow;
        this.mContext = context;
        this.onClickDelete = onClickDelete;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img_default);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.img_default);
    }

    @Override
    public ImageRefundAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_image_from_url, parent, false);
        return new ImageRefundAdapter.ImageViewHolder(view);
    }

    public void loadImageFromUrl(String base64, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img_default);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.img_default);
        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .asBitmap()
                .load(StringUtil.setImage(base64))
                .into(imageView);
    }


    @Override
    public void onBindViewHolder(final ImageRefundAdapter.ImageViewHolder holder, final int position) {
        ConstructionImageInfo image = (ConstructionImageInfo) mList.get(position);
        holder.IVItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, FullScreenRefundActivity.class);
                i.putExtra("position", position);
                mContext.startActivity(i);
            }
        });
        holder.IVItem.setClickable(false);
        if (!isShow) {
            holder.IVDelete.setVisibility(View.GONE);
        } else
            holder.IVDelete.setVisibility(View.VISIBLE);
        //  1 : is server =======  image name is filePath of image
//        if (image.getStatus() == 1 || image.getStatus() == 49) {
//            holder.IVItem.setImageBitmap(StringUtil.setImage(image.getBase64String()));
        Glide.with(mContext).setDefaultRequestOptions(requestOptions.override(200,200)).asBitmap().load(StringUtil.setImage(image.getBase64String())).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                holder.IVItem.setImageBitmap(resource);
                holder.IVItem.setClickable(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item)
        ImageView IVItem;
        @BindView(R.id.btn_image_delete)
        ImageView IVDelete;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            IVDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickDelete.onDelete(getLayoutPosition());
                }
            });
        }
    }

    public interface OnClickDelete{
        void onDelete(int pos);
    }

}
