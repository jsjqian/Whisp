package com.example.jack.whisp;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//IMPORTS FOR THE AUDIO CAPTURE//

import java.io.File;
import java.io.IOException;
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

public class MainActivity extends AppCompatActivity {


    //VARS FOR AUDIO CAPTURE
    Button b1;
    private String FilePath;
    private long time_in_long;
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,             MediaRecorder.OutputFormat.THREE_GPP };
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP };
    /** Called when the activity is first created. */
    //END VARS FOR AUDIO CAPTURE

    private static final String[] dummy = {"wheat", "rye", "sourdough"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView)findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, dummy);
        list.setAdapter(adapter);
        b1=(Button)findViewById(R.id.button);
        b1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        AppLog.logString("Start Recording");
                        try {
                            startRecording();
                        }
                        catch(Exception e){}
                        break;
                    case MotionEvent.ACTION_UP:
                        AppLog.logString("stop Recording");
                        try {
                            stopRecording();
                        }
                        catch(Exception e){
                        }
                        break;
                }
                return false;
            }
        });
    }


    private String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);

        if(!file.exists()){
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }
    private void startRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        FilePath = getFilename();
        recorder.setOutputFile(FilePath);
        time_in_long = System.currentTimeMillis();
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);
        recorder.setMaxDuration(10000);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private MediaRecorder.OnErrorListener errorListener = new        MediaRecorder.OnErrorListener() {
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
    private void stopRecording(){
        if(null != recorder){
            recorder.stop();
            recorder.reset();

            recorder = null;

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

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            //use pathname and delete file
            File file = new File(FilePath);
            boolean delete = file.delete();

            pwindo.dismiss();

        }
    };

    private View.OnClickListener whisper_click_listener = new View.OnClickListener() {
        public void onClick(View v) {

            //Jack send parse info etcetcetc.


            File file = new File(FilePath);
            boolean delete = file.delete();
            //use pathname, delete file
            pwindo.dismiss();

        }
    };

    private View.OnClickListener replay_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            MediaPlayer m = new MediaPlayer();

            try {
                m.setDataSource(FilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                m.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            m.start();
            //Mediaplayer to play the audio.

        }
    };

}