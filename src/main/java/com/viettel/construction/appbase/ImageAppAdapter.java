package com.viettel.construction.appbase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.screens.atemp.other.FullScreenAppParamActivity;
import com.viettel.construction.server.util.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAppAdapter extends RecyclerView.Adapter<ImageAppAdapter.ImageViewHolder> {
    private List<ConstructionImageInfo> mList;
    private Context mContext;
    private Boolean hideDeleteImage;
    private RequestOptions requestOptions;
    private OnClickDelete onClickDelete;
    private int codeSourceImage;


    public ImageAppAdapter(int codeSourceImage, List<ConstructionImageInfo> mList, Context context, Boolean isShow, OnClickDelete onClickDelete) {
        this.codeSourceImage = codeSourceImage;
        this.mList = mList;
        this.hideDeleteImage = isShow;
        this.mContext = context;
        this.onClickDelete = onClickDelete;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img_default);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.img_default);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_image_from_url, parent, false);
        return new ImageViewHolder(view);
    }

    public void loadImageFromUrl(String base64, ImageView imageView) {

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .asBitmap()
                .load(StringUtil.setImage(base64))
                .into(imageView);
    }


    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        ConstructionImageInfo image = (ConstructionImageInfo) mList.get(position);
        holder.IVItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, FullScreenAppParamActivity.class);
                i.putExtra("position", position);
                i.putExtra("source", codeSourceImage);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(i);
            }
        });
        holder.IVItem.setClickable(false);
        if (hideDeleteImage) {
            holder.IVDelete.setVisibility(View.GONE);
        } else
            holder.IVDelete.setVisibility(View.VISIBLE);
        //  1 : is server =======  image name is filePath of image
        Glide.with(mContext).setDefaultRequestOptions(requestOptions.override(200, 200)).asBitmap().load(StringUtil.setImage(image.getBase64String())).into(new SimpleTarget<Bitmap>() {
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

    public interface OnClickDelete {
        void onDelete(int pos);
    }
}
