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
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.screens.atemp.other.ViewFullImageActivity;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionImageInfo;

/**
 * Created by Ramona on 2/21/2018.
 */

public class ImageInProcessAdapter
        extends RecyclerView.Adapter<ImageInProcessAdapter.ImageViewHolder> {
    private ArrayList<ConstructionImageInfo> mList;

    public void setmList(ArrayList<ConstructionImageInfo> mList) {
        this.mList = mList;
    }

    private Context mContext;
    private Boolean isShow;
    private RequestOptions requestOptions;
    private OnClickDelete onClickDelete;

    public ImageInProcessAdapter(ArrayList<ConstructionImageInfo> mList, Context context, Boolean isShow) {
        this.mList = mList;
        this.isShow = isShow;
        this.mContext = context;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img_default);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.img_default);
    }

    public ImageInProcessAdapter(ArrayList<ConstructionImageInfo> mList, Context context, Boolean isShow, OnClickDelete onClickDelete) {
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
                Intent i = new Intent(mContext, ViewFullImageActivity.class);
                i.putExtra(ParramConstant.ListImage_Key,mList);
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
//        } else {
//            //  0 : is local
//            Glide.with(mContext).setDefaultRequestOptions(requestOptions.override(200,200)).load(new File(image.getImageName())).into(holder.IVItem);
//
//        }


//        holder.IVDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
////                builder.setTitle("Xóa ảnh");
////                builder.setMessage("Bạn có muốn xóa ảnh ?");
////                builder.setCancelable(false);
////                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        dialogInterface.dismiss();
////                        ((DetailInProcessCameraActivity)mContext).mListUrlUpload.get(position).setStatus(-1);
////                        mList.remove(position);
////                        notifyDataSetChanged();
////                    }
////                });
////                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        dialogInterface.dismiss();
////                    }
////                });
////                AlertDialog alertDialog = builder.create();
////                alertDialog.show();
////                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.c5));
////                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.c5));
////
//
//
//
//            }
//        });

//        holder.IVItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(mContext, ViewFullImageActivity.class);
//                i.putExtra("position", position);
//                mContext.startActivity(i);
//            }
//        });

//        loadImageFromUrl(image.getUrl(),holder.IVItem);
//        Glide.with(mContext).asBitmap().load(image.getUrl()).into(holder.IVItem);

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
