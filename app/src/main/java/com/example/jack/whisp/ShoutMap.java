package com.example.jack.whisp;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShoutMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gmap;
    private MainActivity GL;
    private Location currentLocation;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shout_map);
        GL = new MainActivity();
        //currentLocation = GL.getLatestLocation();

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                LatLng SYDNEY = new LatLng(-33.88, 151.21);
                LatLng MOUNTAIN_VIEW = new LatLng(38.91, -77.07);
                // Move the camera instantly to Sydney with a zoom of 15.
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));

                // Zoom in, animating the camera.
                gmap.animateCamera(CameraUpdateFactory.zoomIn());

                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                gmap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

                // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                gmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                gmap.addMarker(new MarkerOptions()
                        // .icon(BitmapDescriptorFactory.fromResource(R.drawable.shout_marker_final))
                        .position(MOUNTAIN_VIEW)
                        .title("You're here!"));


                /////////////////////////////////////////////////////////////////////////////////
                ////////THIS IS ALL NEW SHIT. EVERYTHING WORKED BEFORE THIS ADDITION/////////////
                /////////////////////////////////////////////////////////////////////////////////

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Whisper");
                query.whereGreaterThan("upvotes", 50);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> morethan50, ParseException e) {
                        if (e == null) {
                            for(ParseObject p : morethan50){
                                ParseGeoPoint l = p.getParseGeoPoint("location");
                                    gmap.addMarker(new MarkerOptions()
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.shout_marker_final))
                                            .position(new LatLng(l.getLatitude(),l.getLongitude()))
                                            .title(p.getInt("upvotes") + " upvotes!"));

                            }
                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });
                /////////////////////////////////ENDNEW SHIT//////////////////////////////////////
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


}
