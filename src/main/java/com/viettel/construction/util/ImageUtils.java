package com.viettel.construction.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.SysUserDTO;


public class ImageUtils {

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeBitmapFromFile(String filePath, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    //draw text on image
    public static Bitmap drawTextOnImage(Bitmap bitmap, double latitude,
                                         double logitude, String maCT, String hangMuc) {

        Bitmap newBitmap;
        Canvas newCanvas = null;
        Paint paint = null;

        Bitmap.Config config = bitmap.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }

        newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        if (newCanvas == null)
            newCanvas = new Canvas(newBitmap);
        newCanvas.drawBitmap(bitmap, 0, 0, null);

        Date currentTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(currentTime).toString();

        if (paint == null)
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setTextSize(11);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.SANS_SERIF);


        //Xu ly lat, long
        String strLongtitue = String.valueOf(logitude);
        if ((strLongtitue.substring(strLongtitue.indexOf(".")).length() > 2))
            strLongtitue = strLongtitue.substring(0, strLongtitue.indexOf(".") + 3);
        String strLattitue = String.valueOf(latitude);
        if ((strLattitue.substring(strLattitue.indexOf(".")).length() > 2))
            strLattitue = strLattitue.substring(0, strLattitue.indexOf(".") + 3);
        //
        final int textHeight = 15;
        final int fromX = 5;
        int fromY = 10;
        //Time
        newCanvas.drawText(date, fromX, fromY, paint);
        fromY += textHeight;
        //Long + Lat
        newCanvas.drawText(strLongtitue + "-" + strLattitue, fromX, fromY, paint);
        fromY += textHeight;

        //Ma cong trinh
        newCanvas.drawText(maCT, fromX, fromY, paint);
        fromY += textHeight;

        //Hang muc
        if (hangMuc != null && hangMuc.length() > 0) {
            newCanvas.drawText(hangMuc, fromX, fromY, paint);
            fromY += textHeight;
        }

        //User
        SysUserDTO dto = VConstant.getDTO();
        if (dto != null) {
            newCanvas.drawText(dto.getEmail().substring(0, dto.getEmail().indexOf("@")) + "-" + dto.getPhoneNumber(), fromX, fromY, paint);
            fromY += textHeight;
        }

        return newBitmap;
    }


    public static Bitmap drawTextOnImage(Bitmap bitmap, double latitude,
                                         double logitude) {

        Bitmap newBitmap = null;
        Canvas newCanvas = null;
        Paint paint = null;

        Bitmap.Config config = bitmap.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }

        newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        if (newCanvas == null)
            newCanvas = new Canvas(newBitmap);
        newCanvas.drawBitmap(bitmap, 0, 0, null);

        Date currentTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(currentTime).toString();

        if (paint == null)
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setTextSize(11);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.SANS_SERIF);


        //Xu ly lat, long
        String strLongtitue = String.valueOf(logitude);
        if ((strLongtitue.substring(strLongtitue.indexOf(".")).length() > 2))
            strLongtitue = strLongtitue.substring(0, strLongtitue.indexOf(".") + 3);
        String strLattitue = String.valueOf(latitude);
        if ((strLattitue.substring(strLattitue.indexOf(".")).length() > 2))
            strLattitue = strLattitue.substring(0, strLattitue.indexOf(".") + 3);
        //
        final int textHeight = 15;
        final int fromX = 5;
        int fromY = 10;
        //Time
        newCanvas.drawText(date, fromX, fromY, paint);
        fromY += textHeight;
        //Long + Lat
        newCanvas.drawText(strLongtitue + "-" + strLattitue, fromX, fromY, paint);
        fromY += textHeight;

//        //Ma cong trinh
//        newCanvas.drawText(maCT + "-" + strLattitue, fromX, fromY, paint);
//        fromY += textHeight;
//
//        //Hang muc
//        if (hangMuc != null && hangMuc.length() > 0) {
//            newCanvas.drawText(hangMuc, fromX, fromY, paint);
//            fromY += textHeight;
//        }

        //User
        SysUserDTO dto = VConstant.getDTO();
        if (dto != null) {
            newCanvas.drawText(dto.getEmail().substring(0, dto.getEmail().indexOf("@")) + "-" + dto.getPhoneNumber(), fromX, fromY, paint);
            fromY += textHeight;
        }

        return newBitmap;
    }
}
