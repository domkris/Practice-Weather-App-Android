package com.domo.macka.testtimeapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    double latitude;
    double longitude;
    MyDBHandler weatherDatabase = new MyDBHandler(this,null,null,1);

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        Bundle extras = new Bundle();
        extras.putDouble("latitude", latitude);
        extras.putDouble("longitude", longitude);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public String url;
    private XMLParserJava obj;
    String[] temperatures = new String[5];
    String[] values = new String[5];
    String[] temperaturesClickedDate = new String[8];
    String[] valuesClickedDate = new String[8];


    Button cityButton, googleMap;
    TextView typeOfConn,databaseView;
    EditText city;
    boolean connected = false;
    boolean clicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("onCreate", "LIFE");


        cityButton = (Button) findViewById(R.id.city_selection);
        googleMap = (Button) findViewById(R.id.mapButton);
        googleMap.setClickable(false);
        typeOfConn = (TextView) findViewById(R.id.typeOfConnection);
        city = (EditText) findViewById(R.id.write_city);
        checkConnection();


        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                if (connected) {

                    final String cityName = city.getText().toString();
                    url = "http://api.openweathermap.org/data/2.5/forecast?q="
                            + cityName + "," + "&mode=xml&appid=2de143494c0b295cca9337e1e96b00e0";

                    if(clicked){
                        clicked = false;
                        weatherDatabase.deleteTable();
                    }
                    if(!clicked){
                        clicked=true;
                    }
                    obj = new XMLParserJava(url,weatherDatabase);
                    obj.fetchXML();

                    // WAIT FOR THE DOCUMENT TO BE READ
                    do {
                    } while (obj.parsingComplete);

                    final String[] dates = getDates();

                    latitude = Double.valueOf(obj.getLatitude());
                    longitude = Double.valueOf(obj.getLongitude());

                    databaseView = (TextView)findViewById(R.id.database);
                    databaseView.setText(cityName);
                    setTemperatureAndDescriptions(dates);


                   //
                   // weatherDatabase.deleteTable();



                    MyGrid adapter = new MyGrid(MainActivity.this, dates, values, temperatures);
                    GridView grid = (GridView) findViewById(R.id.gridView);
                    grid.setAdapter(adapter);
                    Log.d(""+ values.length,"ADAPTER");
                    city.setText("");
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Intent intent = new Intent(view.getContext(), DetailedActivity.class);
                            Bundle extras = new Bundle();
                            String date = dates[position];
                            Log.d(date,"DATUM2");
                            Log.d(""+position,"Pozicija");

                            setDetailedTemperatureAndDescription(dates[position]);
                            extras.putString("city", cityName);
                            extras.putStringArray("values", valuesClickedDate);
                            extras.putStringArray("temperatures",temperaturesClickedDate);
                            extras.putStringArray("dates",weatherDatabase.getDatesClicked(date));
                            intent.putExtras(extras);
                            startActivity(intent);


                        }
                    });



                    googleMap.setClickable(true);

                } else {
                    typeOfConn.setText("ERROR not connected!");
                    typeOfConn.setTextColor(Color.RED);


                }
            }
        });


    }

    private void setDetailedTemperatureAndDescription(String date) {

            temperaturesClickedDate=weatherDatabase.getTemperatureValueClickedDate(date);
            valuesClickedDate = weatherDatabase.getSymbolNameClickedDate(date);
            Log.d(""+date,"date");



    }

    private void setTemperatureAndDescriptions(String[] dates) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        Calendar c = Calendar.getInstance();
        int hour = Integer.parseInt(sdf.format(c.getTime()));
        if(hour< 3){
            hour =0;
        }
        else {
            hour = hour - (hour%3);
        }


        if(hour<10){
            for(int i = 0; i<=4; i++){
                String date = dates[i]+":0"+hour+"h";
                Log.d(date,"VRIJEME NAKNADNO");
                temperatures[i]=weatherDatabase.getTemperatureValue(date);
                values[i] = weatherDatabase.getSymbolName(date);
            }
        }
        if(hour>=10){
            for(int i = 0; i<=4; i++){
                String date = dates[i]+":"+hour+"h";
                Log.d(date,"VRIJEME NAKNADNO");
                temperatures[i]=weatherDatabase.getTemperatureValue(date);
                values[i] = weatherDatabase.getSymbolName(date);
            }
        }








    }

    private String[] getDates() {
        String[] dates = new String[5];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        for(int i = 0;i<5;i++){
            dates[i]= sdf.format(c.getTime());//n
            c.add(Calendar.DATE, 1);
            Log.d(dates[i],"DATUM");

        }
        return dates;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "LIFE");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause", "LIFE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop", "LIFE");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "LIFE");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart", "LIFE");
    }

    private void checkConnection() {
        ConnectivityManager connManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();


        if (info != null && info.isConnected()) {
            boolean wifiConnected = info.getType() == ConnectivityManager.TYPE_WIFI;
            boolean mobileConnected = info.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {

                typeOfConn.setText("Connected: WI-FI ");
                typeOfConn.setTextColor(Color.GREEN);
                connected = true;

            } else if (mobileConnected) {

                typeOfConn.setText("Connected: MOBILE ");
                typeOfConn.setTextColor(Color.GREEN);
                connected = true;
            }
        } else {
            typeOfConn.setText("ERROR: not connected ");
            typeOfConn.setTextColor(Color.RED);
            connected = false;


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
