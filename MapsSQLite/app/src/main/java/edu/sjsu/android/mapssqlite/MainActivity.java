package edu.sjsu.android.mapssqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity ;
import androidx.loader.app.LoaderManager ;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.database.Cursor ;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle ;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory ;
import com.google.android.gms.maps.GoogleMap ;
import com.google.android.gms.maps.OnMapReadyCallback ;
import com.google.android.gms.maps.SupportMapFragment ;
import com.google.android.gms.maps.model.LatLng ;
import com.google.android.gms.maps.model.MarkerOptions ;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor>
{
    private GoogleMap map ;
    private final LatLng LOCATION_UNI = new LatLng(37.335371, -121.881050) ;
    private final LatLng LOCATION_CS = new LatLng(37.333714, -121.881860) ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main) ;


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap ;
        LoaderManager.getInstance(this).initLoader(0, null, this) ;

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override public void onMapClick(LatLng point)
            {
                map.addMarker(new MarkerOptions().position(point).title("Find Me Here!")) ;
                LocationInsertTask insertTask = new LocationInsertTask() ;


                ContentValues values = new ContentValues() ;
                values.put(LocationsDB.LATITUDE, point.latitude) ;
                values.put(LocationsDB.LONGITUDE, point.longitude) ;
                values.put(LocationsDB.ZOOM_LEVEL, map.getCameraPosition().zoom) ;
                insertTask.execute(values) ;

                Toast.makeText(getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show() ; // display "Maker is added to the Map" message
            }
        }) ;

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
        {
            @Override public void onMapLongClick(LatLng point)
            {
                map.clear() ;
                LocationDeleteTask deleteTask = new LocationDeleteTask() ;
                deleteTask.execute() ;
                Toast.makeText(getBaseContext(), "All markers are removed", Toast.LENGTH_LONG).show() ; // display “ALL makers are removed" message
            }
        }) ;
    }

    public void onClickCS(View v)
    {
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN) ;
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_CS, 18) ;
        map.animateCamera(update) ;
    }

    public void onClickUni(View v)
    {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL) ;
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, 14) ;
        map.animateCamera(update) ;
    }

    public void onClickCity(View v)
    {
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE) ;
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, 10) ;
        map.animateCamera(update) ;
    }

    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void>
    {
        @Override protected Void doInBackground(ContentValues... contentValues)
        {

            getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]) ;
            return null;
        }
    }

    private class LocationDeleteTask extends AsyncTask<Void, Void, Void>
    {
        @Override protected Void doInBackground(Void... params)
        {

            getContentResolver().delete(LocationsContentProvider.CONTENT_URI, null, null) ;
            return null;
        }
    }

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1)
    {
        Loader<Cursor> c = null ;
        Uri uri = LocationsContentProvider.CONTENT_URI ;

        c = new CursorLoader(this, uri, null, null, null, null) ;
        return c;
    }

    @SuppressLint("Range")
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1)
    {
        int locationCount = 0 ;
        double lat = 0 ;
        double lng = 0 ;
        float zoom = 0 ;


        if (arg1 != null)
        {
            locationCount = arg1.getCount() ;
            arg1.moveToFirst() ;
        }
        else {locationCount = 0 ;}

        for (int i = 0; i < locationCount; i++)
        {
            lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.LATITUDE)) ;
            lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.LONGITUDE)) ;
            zoom = arg1.getFloat(arg1.getColumnIndex(LocationsDB.ZOOM_LEVEL)) ;

            LatLng location = new LatLng(lat, lng) ;
            map.addMarker(new MarkerOptions().position(location).title("Marker added!")) ;
            arg1.moveToNext() ;
        }

        if (locationCount > 0)
        {
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng))) ;
            map.animateCamera(CameraUpdateFactory.zoomTo(zoom)) ;
        }
    }

    @Override public void onLoaderReset(Loader<Cursor> loader) {}
}