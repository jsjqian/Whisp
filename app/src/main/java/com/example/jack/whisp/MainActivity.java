package com.example.jack.whisp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private static final String[] dummy = {"wheat", "rye", "sourdough"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView)findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, dummy);
        list.setAdapter(adapter);
    }


}
