package com.kocen.zan.sun.data;

import android.database.Cursor;
import android.test.AndroidTestCase;
import com.kocen.zan.sun.data.WeatherContract.WeatherEntry;
import com.kocen.zan.sun.data.WeatherContract.LocationEntry;

/**
 * Created by zan on 25/08/2016.
 * Tests for basic functionality of the app
 */
public class TestProvider extends AndroidTestCase {

    public static String LOG_TAG = TestProvider.class.getSimpleName();

     /*
       This helper function deletes all records from both database tables using the ContentProvider.
       It also queries the ContentProvider to make sure that the database has been successfully
       deleted, so it cannot be used until the Query and Delete functions have been written
       in the ContentProvider.


     */

    public void deleteAllRecordsFromProvider(){
        mContext.getContentResolver().delete(
                WeatherEntry.CONTENT_URI,
                null,
                null
        );

        mContext.getContentResolver().delete(
                LocationEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                LocationEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: records not deleted from Weather table during delete",
                0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                LocationEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: records not deleted from Location table during delete",
                0, cursor.getCount());
        cursor.close();
    }
}
