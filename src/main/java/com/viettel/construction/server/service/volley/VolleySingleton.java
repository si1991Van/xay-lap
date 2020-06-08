package com.viettel.construction.server.service.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.viettel.construction.common.App;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    //private ImageLoader mImageLoader;
    //private ImageLoader.ImageCache mImageCache;
    private OkHttp3Stack mOkHttp3Stack;
    private static final String TAG = VolleySingleton.class.getSimpleName();
    private static final String TAG1 = "VTVolleySingleton";

    private VolleySingleton(Context context) {
        //use okhttp stack
        mOkHttp3Stack = new OkHttp3Stack();
        // Todo check ok htpp stack
//        mRequestQueue = Volley.newRequestQueue(context, mOkHttp3Stack);
        Log.d(TAG1,"VolleySingleton : " + context);
        mRequestQueue = Volley.newRequestQueue(context);
    }

    //    private void initiateImageLoader(){
    //        mImageCache = new ImageLoader.ImageCache() {
    //            // int cacheSize = 1024 * 1024 * 10;
    //            private final BitmapLruCache mCache = new BitmapLruCache();
    //
    //            public void putBitmap(String url, Bitmap bitmap) {
    //                mCache.put(url, bitmap);
    //            }
    //
    //            public Bitmap getBitmap(String url) {
    //                return mCache.get(url);
    //            }
    //        };
    //        mImageLoader = new ImageLoader(this.mRequestQueue, mImageCache);
    //    }

    public static synchronized VolleySingleton getInstance() {
        if (mInstance == null) {
            Log.d(TAG,"VolleySingleton - instance - null "  + " - context : " + App.getContext());
            mInstance = new VolleySingleton(App.getContext());
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

    //    public ImageLoader getImageLoader() {
    //        return this.mImageLoader;
    //    }

    //    public ImageLoader.ImageCache getImageCache() {
    //        return this.mImageCache;
    //    }


    public <T> void addToRequestQueue(Request<T> req, Object tag) {
        // set the default tag if tag is empty
        req.setTag(tag == null ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
//        req.setRetryPolicy(new DefaultRetryPolicy(3 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
//        Log.d(TAG1,"addToRequestQueue - req : " + req.toString() +
//                " - getRequestQueue : " + getRequestQueue());
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
            mOkHttp3Stack.cancelRequestOnGoing(tag);
        }
    }

    public void cancelPendingRequestsNoTag() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
            mOkHttp3Stack.cancelRequestOnGoing(TAG);
        }
    }

    public void clearVolleyCache() {
        if (mRequestQueue != null) {
            mRequestQueue.getCache().clear();
        }
    }
//
}