package com.example.jack.whisp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Jack on 1/30/2016.
 */
public class AudioListAdapter extends ArrayAdapter<ParseObject> {

    private List<ParseObject> objs;
    private int layoutResourceId;
    private Context context;

    private int downvotes;
    private int current_votes;
    private int upvotes;

    public AudioListAdapter(Context context, List<ParseObject> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ParseObject audio = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        // Lookup view for data population
        final Button up = (Button) convertView.findViewById(R.id.upvote);
        final TextView votes = (TextView) convertView.findViewById(R.id.votes);
        final Button down = (Button) convertView.findViewById(R.id.downvote);
        // Populate the data into the template view using the data object

        TextView timeStamp = (TextView) convertView.findViewById(R.id.time_stamp);
        long t = audio.getCreatedAt().getTime();
        String id = audio.getObjectId();
        Whisper whisp = new Whisper(t, id);
        timeStamp.setText(whisp.toString());
        int upvotes = audio.getInt("upvotes");
        int downvotes = audio.getInt("downvotes");
        votes.setText(String.valueOf(upvotes - downvotes));


        up.setOnClickListener(new View.OnClickListener() {
            int upvotess = audio.getInt("upvotes");
            int downvotess = audio.getInt("downvotes");
            @Override
            public void onClick(View view) {
                ColorStateList mList = up.getTextColors();
                int color = mList.getDefaultColor();
                if (color == Color.WHITE) {
                    votes.setText(String.valueOf((upvotess + 1) - downvotess));
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Whisper");
                    query.getInBackground(audio.getObjectId(), new GetCallback<ParseObject>() {
                        public void done(ParseObject whisper, ParseException e) {
                            if (e == null) {
                                whisper.put("upvotes", upvotess + 1);
                                whisper.saveInBackground();
                            }
                        }
                    });
                    up.setBackgroundColor(Color.parseColor("#00CD00"));
                    up.setTextColor(Color.parseColor("#000000"));
                }
                else {
                    votes.setText(String.valueOf((upvotess) - downvotess));
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Whisper");
                    query.getInBackground(audio.getObjectId(), new GetCallback<ParseObject>() {
                        public void done(ParseObject whisper, ParseException e) {
                            if (e == null) {
                                whisper.put("upvotes", upvotess);
                                whisper.saveInBackground();
                            }
                        }
                    });
                    up.setBackgroundColor(Color.parseColor("#BBBBBB"));
                    up.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            int upvotess = audio.getInt("upvotes");
            int downvotess = audio.getInt("downvotes");
            @Override
            public void onClick(View view) {
                ColorStateList mList = down.getTextColors();
                int color = mList.getDefaultColor();
                if (color == Color.WHITE) {
                    votes.setText(String.valueOf(upvotess - (downvotess + 1)));
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Whisper");
                    query.getInBackground(audio.getObjectId(), new GetCallback<ParseObject>() {
                        public void done(ParseObject whisper, ParseException e) {
                            if (e == null) {
                                whisper.put("downvotes", downvotess + 1);
                                whisper.saveInBackground();
                            }
                        }
                    });
                    down.setBackgroundColor(Color.parseColor("#FF3D0D"));
                    down.setTextColor(Color.parseColor("#000000"));
                } else {
                    votes.setText(String.valueOf(upvotess - (downvotess)));
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Whisper");
                    query.getInBackground(audio.getObjectId(), new GetCallback<ParseObject>() {
                        public void done(ParseObject whisper, ParseException e) {
                            if (e == null) {
                                whisper.put("downvotes", downvotess);
                                whisper.saveInBackground();
                            }
                        }
                    });
                    down.setBackgroundColor(Color.parseColor("#BBBBBB"));
                    down.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }

}
