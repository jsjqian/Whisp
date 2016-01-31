package com.example.jack.whisp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;


//IMPORTS FOR THE AUDIO CAPTURE//

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.jar.Manifest;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
//END OF IMPORTS FOR AUDIO CAPTURE//



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemClickListener {


    //VARS FOR AUDIO CAPTURE
    Button b1;
    private String filePath;
    private long time_in_long;
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = {MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP};
    private String file_exts[] = {AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP};
    /** Called when the activity is first created. */
    //END VARS FOR AUDIO CAPTURE

    private AudioListAdapter adapter;

    private GoogleApiClient client;

    private Location currentLocation;

    //for Navigation Layout
    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    private Button up;
    private Button down;
    private TextView votes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        // for M (jack dai's phone)
        String[] permissions = {android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, 0);

//        list.setOnItemClickListener(this);

//        this.adapter = new ArrayAdapter<>(this, R.layout.row, R.id.time_stamp);
//        list.setAdapter(adapter);
        if (client == null) {

            client = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        b1 = (Button) findViewById(R.id.button);
        b1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AppLog.logString("Start Recording");
                        try {
                            startRecording();
                        } catch (Exception e) {
                            Log.e("JACK", "failed to start recording");
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        AppLog.logString("Stop Recording");

                        try {
                            stopRecording();
                        } catch (Exception e) {

                            Log.e("JACK", "failed to stop recording");
                        }
                        break;
                }
                return false;
            }
        });

    }

    private void writetoParse() {

        if(currentLocation != null) {
        ParseFile parseFile = new ParseFile(new File(filePath));
        parseFile.saveInBackground();

            ParseObject whisper = new ParseObject("Whisper");
            whisper.put("filename", filePath);
            whisper.put("audio", parseFile);
            whisper.put("location", new ParseGeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude()));
            whisper.put("upvotes", "0");
            whisper.put("downvotes", "0");
            whisper.saveInBackground();
        }

    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + this.time_in_long + file_exts[currentFormat]);
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        this.time_in_long = System.currentTimeMillis();
        this.filePath = getFilename();
        recorder.setOutputFile(filePath);
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);
        recorder.setMaxDuration(10000);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            Log.d("JACK", "IllegalState");

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("JACK", "IOException");
        }
    }

    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            AppLog.logString("Error: " + what + ", " + extra);
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            AppLog.logString("Warning: " + what + ", " + extra);
        }
    };

    private void stopRecording() {
        if (null != recorder) {
            recorder.stop();
            recorder.reset();

            initiatePopupWindow();
        }
    }

    private PopupWindow pwindo;
    private Button btnClosePopup;
    private Button whisper;
    private Button replay;

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popout,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 1000, 300, true);
            pwindo.showAtLocation(layout, Gravity.BOTTOM, 0, 0);

            btnClosePopup = (Button) layout.findViewById(R.id.close);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);

            whisper = (Button) layout.findViewById(R.id.whisper);
            whisper.setOnClickListener(whisper_click_listener);

            replay = (Button) layout.findViewById(R.id.replay);
            replay.setOnClickListener(replay_click_listener);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnClickListener cancel_button_click_listener = new OnClickListener() {
        public void onClick(View v) {
            //use pathname and delete file
            File file = new File(filePath);
            boolean delete = file.delete();

            pwindo.dismiss();

        }
    };

    private OnClickListener whisper_click_listener = new OnClickListener() {
        public void onClick(View v) {

            writetoParse();

            File file = new File(filePath);
            //boolean delete = file.delete();
            //use pathname, delete file
            pwindo.dismiss();

        }
    };

    private OnClickListener replay_click_listener = new OnClickListener() {
        public void onClick(View v) {
            MediaPlayer m = new MediaPlayer();

            try {
                m.setDataSource(filePath);
                m.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            m.start();
            //Mediaplayer to play the audio.
        }
    };

    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("JACK","permission problem");
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(client);
        if (location != null){

            this.currentLocation = location;
            update();
            Log.d("JACK", "I'M HERE AND I GOT THE LOCATION!!!!!!!!");

        }
        else{

            Log.d("JACK", "failed to get location");

        }
    }

    private void update(){

        if(currentLocation == null){

            Log.d("JACK", "returning");
            return;
        }

        ParseGeoPoint userLocation = new ParseGeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Whisper");
        query.whereWithinMiles("location", userLocation, 0.00378);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects != null && objects.size() > 0) {

                    Log.d("JACK", objects.toString());
                    ListView list = (ListView) findViewById(R.id.list);
                    adapter = new AudioListAdapter(MainActivity.this, objects);
                    list.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStart(){

        client.connect();
        super.onStart();
    }

    @Override
    protected void onStop(){

        client.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toast.makeText(this, "Location connection failed", Toast.LENGTH_LONG);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Whisper whisper = (Whisper) parent.getItemAtPosition(position);
        ParseQuery query = new ParseQuery("Whisper");
        ParseObject object;
        try {
            object = query.get(whisper.objectId);

        } catch (ParseException e) {
            return;
        }

        ParseFile file = (ParseFile) object.get("audio");
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if (e == null){

                    try {
                        File f = File.createTempFile("audio", "mp4", getCacheDir());
                        f.deleteOnExit();
                        FileOutputStream output = new FileOutputStream(f);
                        output.write(data);
                        output.close();

                        MediaPlayer player = new MediaPlayer();
                        player.setDataSource(f.getAbsolutePath());
                        player.prepare();
                        player.start();

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}