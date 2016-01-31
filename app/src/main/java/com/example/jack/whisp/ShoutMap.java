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
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import java.security.Security;


public class ShoutMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gmap;
    private MainActivity GL;
    private Location currentLocation;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shout_map);
        GL = new MainActivity();
        currentLocation = GL.getLatestLocation();

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                LatLng SYDNEY = new LatLng(-33.88, 151.21);
                LatLng MOUNTAIN_VIEW = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
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
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.shout_marker_final))
                        .position(MOUNTAIN_VIEW)
                        .title("55 upvotes!"));

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ShoutMap Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.jack.whisp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ShoutMap Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.jack.whisp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

//    @Override
//    public void onMapReady (GoogleMap map){
//        map.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0))
//                .title("Marker"));
//    }

}
//        private GoogleMap googleMap;
//
//        private GoogleMap.OnMyLocationChangeListener myLocationChangeListener;
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_shout_map);
//            // Getting Google Play availability status
//            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
//
//            // Showing status
//            if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
//
//                int requestCode = 10;
//                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
//                dialog.show();
//
//            } else { // Google Play Services are available
//
//                // Getting reference to the SupportMapFragment of activity_main.xml
//                SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//
//                // Getting GoogleMap object from the fragment
//                googleMap = fm.getMap();
//
//                // Enabling MyLocation Layer of Google Map
//                try {
//                    googleMap.setMyLocationEnabled(true);
//                } catch (SecurityException e) {
//                }
//
//                // Getting LocationManager object from System Service LOCATION_SERVICE
//                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//                // Creating a criteria object to retrieve provider
//                Criteria criteria = new Criteria();
//
//                // Getting the name of the best provider
//                String provider = locationManager.getBestProvider(criteria, true);
//                Location location = null;
//                // Getting Current Location
//                try {
//                    location = locationManager.getLastKnownLocation(provider);
//
//
//                LocationListener locationListener = new LocationListener() {
//                    void onLocationChanged(Location location) {
//                        // redraw the marker when get location update.
//                        drawMarker(location);
//                    }
//
//                    if(location!=null)
//
//                    {
//                        //PLACE THE INITIAL MARKER
//                        drawMarker(location);
//                    }
//
//                    locationManager.requestLocationUpdates(provider,20000,0,locationListener);
//                };
//                } catch (SecurityException e) {
//                }
//            }
//        }
//
//            private void drawMarker(Location location){
//                // Remove any existing markers on the map
//                googleMap.clear();
//                LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
//                googleMap.addMarker(new MarkerOptions()
//                        .position(currentPosition)
//                        .snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude())
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
//                        .title("ME"));
//            }
//
//            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).;
//
//            myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
//                @Override
//                public void onMyLocationChange(Location location) {
//                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
//                    Marker mMarker = map.addMarker(new MarkerOptions().position(loc));
//                    if(map != null){
//                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
//                    }
//                }
//            };
//
//            map.setOnMyLocationChangeListener(myLocationChangeListener);
//            map.setMyLocationEnabled(true);
//
//            //LocationSource a = (LocationSource) getSystemService(Context.LOCATION_SERVICE);
//            //LocationManager b = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            // map.setLocationSource(a);
//            LatLng SYDNEY = new LatLng(-33.88, 151.21);
//            LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);
//
//            // Move the camera instantly to Sydney with a zoom of 15.
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));
//
//            // Zoom in, animating the camera.
//            map.animateCamera(CameraUpdateFactory.zoomIn());
//
//            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
//            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//
//            // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
//                    .zoom(17)                   // Sets the zoom
//                    .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
//                    .build();                   // Creates a CameraPosition from the builder
//            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
////            cameraPosition));

//        }
//    }



//this works
//public class ShoutMap extends Activity {
//
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shout_map);
//    }
//
//}
//end of this work