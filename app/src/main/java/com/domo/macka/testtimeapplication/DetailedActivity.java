package com.domo.macka.testtimeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        String[] values =b.getStringArray("values");
        String[] temperatures = b.getStringArray("temperatures");
        String[] dates = b.getStringArray("dates");

        GridView gridView = (GridView)findViewById(R.id.gridViewDetail);
        MyGrid adapter = new MyGrid(DetailedActivity.this, dates, values, temperatures);
        gridView.setAdapter(adapter);
    }
}
