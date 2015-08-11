package edu.uw.tacoma.mmuppa.locationlab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mmuppa on 8/11/15.
 */
public class LocationDB {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Locations.db";

    private LocationDBHelper mLocationDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public LocationDB(Context context) {
        mLocationDBHelper = new LocationDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mLocationDBHelper.getWritableDatabase();
    }

    public boolean insertLocation(double lat, double lng) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("lat", lat);
        contentValues.put("lng", lng);

        long rowId = mSQLiteDatabase.insert("Location", null, contentValues);
        return rowId != -1;
    }

    public List<MyLocation> selectLocations() {
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] columns = {
                "lat", "lng"
        };


        Cursor c = mSQLiteDatabase.query(
                "Location",  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        List<MyLocation> list = new ArrayList<MyLocation>();

        for (int i=0; i<c.getCount(); i++) {
            double lat = c.getDouble(0);
            double lng = c.getDouble(1);
            MyLocation myLocation = new MyLocation(lat, lng);
            list.add(myLocation);
            c.moveToNext();
        }

        return list;
    }

    public void deleteAllLocations() {
        mSQLiteDatabase.delete("Location", null, null);
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }
}



class LocationDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_LOCATION_SQL =
            "CREATE TABLE IF NOT EXISTS Location (id INTEGER PRIMARY KEY AUTOINCREMENT, lat REAL, lng REAL)";

    private static final String DROP_LOCATION_SQL =
            "DROP TABLE IF EXISTS Location";

    public LocationDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LOCATION_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("");
        onCreate(sqLiteDatabase);
    }

}
