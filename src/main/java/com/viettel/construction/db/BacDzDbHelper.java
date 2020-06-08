package com.viettel.construction.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.viettel.construction.model.db.ImageCapture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BacDzDbHelper extends SQLiteOpenHelper {

    private static BacDzDbHelper _instantce = null;
    public static BacDzDbHelper getInstance(Context context) {
        if (_instantce == null) {
            _instantce = new BacDzDbHelper(context);
        }
        return _instantce;
    }

    private static final String DATABASE_NAME = "bacnd4Dzvl";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "image";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATA = "data";
    private static final String KEY_DATE = "date";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LON = "lon";

    public BacDzDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_image_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_DATA, KEY_DATE, KEY_LON, KEY_LAT, KEY_NAME);
        db.execSQL(create_image_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_image_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_image_table);

        onCreate(db);
    }

    public boolean insertImage(ImageCapture imageCapture) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String pictureName = "IMG_" + timeStamp + ".jpg";
        values.put(KEY_NAME, pictureName);
        values.put(KEY_DATA, imageCapture.getImageData());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        values.put(KEY_DATE, format.format(cal.getTime()));
        values.put(KEY_LAT, imageCapture.getLongtitude() + "");
        values.put(KEY_LON, imageCapture.getLattitude() + "");

        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        if (id > 0) {
            return true;
        }

        return false;
    }

    public ArrayList<ImageCapture> getAllImage() {
        ArrayList<ImageCapture>  imageList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            ImageCapture imageCapture = new ImageCapture(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    Double.parseDouble(cursor.getString(3)),
                    Double.parseDouble(cursor.getString(4)),
                    cursor.getString(5)
            );
            imageList.add(imageCapture);
            cursor.moveToNext();
        }
        return imageList;
    }

    public void deleteImage(int imageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(imageId) });
        db.close();
    }
}
