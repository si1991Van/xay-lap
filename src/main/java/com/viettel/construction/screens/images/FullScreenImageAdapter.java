package com.viettel.construction.screens.images;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.viettel.construction.screens.custom.image.TouchImageViewCamera;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.screens.custom.image.TouchImageView;

/**
 * Created by manro on 3/2/2018.
 */

public class FullScreenImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<ConstructionImageInfo> _imagePaths = new ArrayList<>();
    private LayoutInflater inflater;
    private boolean isDisplayDetails = false;
    private IOnImageListener listener;
    private RequestOptions requestOptions;


    // constructor
    public FullScreenImageAdapter(Context context, List<ConstructionImageInfo> imagePaths, IOnImageListener listener) {
        this.mContext = context;
        for (ConstructionImageInfo item : imagePaths) {
            if (item != null)
                _imagePaths.add(item);
        }
        inflater = LayoutInflater.from(context);
        this.listener = listener;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img_default);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.img_default);
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final TouchImageViewCamera imgDisplay;
        final ConstructionImageInfo item = _imagePaths.get(position);
        if (item == null)
            return null;
        if (listener != null) {
            listener.setUpData(position);
        }
        final View view = inflater.inflate(R.layout.img_full_screen, container,
                false);
        // Declare layout item
        isDisplayDetails = false;
        ((ViewPager) container).addView(view);
        imgDisplay = view.findViewById(R.id.imgDisplay);
//        Glide.with(mContext).asBitmap().load(item.getUrl()).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
//                imgDisplay.setImageBitmap(resource);
//            }
//        });

        // 1 : is server
//        if (item.getStatus() == 1 || item.getStatus() == 49) {
//            imgDisplay.setImageBitmap(StringUtil.setImage(item.getBase64String()));
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).asBitmap().load(StringUtil.setImage(item.getBase64String())).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                    imgDisplay.setImageBitmap(resource);
                }
            });
//        } else {
//            //  0 : is local
//            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(new File(item.getImageName())).into(imgDisplay);
////            Glide.with(mContext).load(new File(item.getImageName())).into(imgDisplay);
//        }

        imgDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.showImageDetails(isDisplayDetails);
                isDisplayDetails = !isDisplayDetails;
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }

    public interface IOnImageListener {
        void setUpData(int position);

        void showImageDetails(boolean isShow);
    }
}
