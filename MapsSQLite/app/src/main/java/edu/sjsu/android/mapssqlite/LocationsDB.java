package edu.sjsu.android.mapssqlite;

import android.content.ContentValues ;
import android.content.Context ;
import android.database.Cursor ;
import android.database.sqlite.SQLiteDatabase ;
import android.database.sqlite.SQLiteOpenHelper ;

public class LocationsDB extends SQLiteOpenHelper
{

    static final String DATABASE_NAME = "Location" ;
    static final String TABLE_NAME = "locations" ;
    static final int DATABASE_VERSION = 1 ;
    static final String _ID = "_id";
    static final String LATITUDE = "latitude" ;
    static final String LONGITUDE = "longitude" ;
    static final String ZOOM_LEVEL = "zoom" ;

    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " latitude DOUBLE NOT NULL, " + " longitude DOUBLE NOT NULL, " + " zoom INTEGER NOT NULL);" ;
    private SQLiteDatabase db ;

    public LocationsDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION) ;
        this.db = getWritableDatabase() ;
    }

    @Override public void onCreate(SQLiteDatabase db) {db.execSQL(CREATE_DB_TABLE) ;}

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME) ;
        onCreate(db) ;
    }


    public long insertLocation(ContentValues values)
    {
        long ID = db.insert(TABLE_NAME, "", values) ;
        return ID;
    }


    public int deleteLocation()
    {
        int count = db.delete(TABLE_NAME, null, null) ;
        return count;
    }

    public Cursor returnLocation()
    {
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null) ;
        return c;
    }
}