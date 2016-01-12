package com.domo.macka.testtimeapplication;

import android.content.Context;
import android.content.Intent;
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
    MyDBHandler weatherDatabase = new MyDBHandler(this, null, null, 1);
    private String cityAndCountry;

    // STARTS DETAILED ACTIVITY
    public void sendMessage(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        Bundle extras = new Bundle();
        extras.putDouble("latitude", latitude);
        extras.putDouble("longitude", longitude);
        extras.putString("city_and_country", cityAndCountry);
        extras.putString("temperature", temperatures[0]);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public String url;
    private XMLParserJava xmlParserJava;
    String[] temperatures = new String[5];
    String[] values = new String[5];
    String[] temperaturesClickedDate = new String[8];
    String[] valuesClickedDate = new String[8];


    Button cityButton, googleMap;
    TextView typeOfConn, databaseView, cityStatus;
    EditText city;
    boolean connected = false;
    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("onCreate", "LIFE");


        cityButton = (Button) findViewById(R.id.city_selection);
        googleMap = (Button) findViewById(R.id.mapButton);
        googleMap.setClickable(false);
        typeOfConn = (TextView) findViewById(R.id.typeOfConnection);
        city = (EditText) findViewById(R.id.write_city);
        databaseView = (TextView) findViewById(R.id.database);
        cityStatus = (TextView) findViewById(R.id.text_status);
        checkConnection();


        cityButton.setOnClickListener(new View.OnClickListener() {
            GridView grid = (GridView) findViewById(R.id.gridView);

            @Override
            public void onClick(View v) {
                checkConnection();
                final String cityName = city.getText().toString();

                if (connected) {

                    url = "http://api.openweathermap.org/data/2.5/forecast?q="
                            + cityName + "," + "&mode=xml&appid=2de143494c0b295cca9337e1e96b00e0";

                    if (clicked) {
                        clicked = false;
                        weatherDatabase.deleteTable();
                    }

                    if (!clicked) {
                        clicked = true;
                    }

                    xmlParserJava = new XMLParserJava(url, weatherDatabase);
                    xmlParserJava.fetchXML();

                    // WAIT FOR THE DOCUMENT TO BE READ
                    do {
                    } while (xmlParserJava.parsingComplete);
                    if (!xmlParserJava.getError()) {
                        final String[] dates = getDates();

                        latitude = Double.valueOf(xmlParserJava.getLatitude());
                        longitude = Double.valueOf(xmlParserJava.getLongitude());

                        cityAndCountry = xmlParserJava.getCity() + ", " + xmlParserJava.getCountryCode();
                        databaseView.setText(cityAndCountry);
                        setTemperatureAndDescriptions(dates);


                        MyGrid adapter = new MyGrid(MainActivity.this, dates, values, temperatures);
                        grid.setVisibility(View.VISIBLE);
                        grid.setAdapter(adapter);
                        city.setText("");
                        cityStatus.setText("");
                        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Intent intent = new Intent(view.getContext(), DetailedActivity.class);
                                Bundle extras = new Bundle();
                                String date = dates[position];

                                setDetailedTemperatureAndDescription(dates[position]);
                                extras.putString("city", cityAndCountry);
                                extras.putStringArray("values", valuesClickedDate);
                                extras.putStringArray("temperatures", temperaturesClickedDate);
                                extras.putStringArray("dates", weatherDatabase.getDatesClicked(date));
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        });
                        googleMap.setClickable(true);

                    } else if (xmlParserJava.getError()) {
                        cityStatus.setText(R.string.wrong_input);
                        googleMap.setClickable(false);
                        databaseView.setText("");
                        grid.setVisibility(View.GONE);
                    }
                }
                if (!connected) {
                    typeOfConn.setText(R.string.not_connected);
                    googleMap.setClickable(false);
                }
            }
        });


    }
    // SETTING DATA FOR DETAILED ACTIVITY GRID
    private void setDetailedTemperatureAndDescription(String date) {

        temperaturesClickedDate = weatherDatabase.getTemperatureValueClickedDate(date);
        valuesClickedDate = weatherDatabase.getSymbolNameClickedDate(date);
        Log.d("" + date, "date");


    }
    // SETTING DATA FOR MAIN ACTIVITY GRID
    private void setTemperatureAndDescriptions(String[] dates) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        Calendar c = Calendar.getInstance();
        int hour = Integer.parseInt(sdf.format(c.getTime()));
        if (hour < 3) {
            hour = 0;
        } else {
            hour = hour - (hour % 3);
        }


        if (hour < 10) {
            for (int i = 0; i <= 4; i++) {
                String date = dates[i] + ": 0" + hour + "h";
                temperatures[i] = weatherDatabase.getTemperatureValue(date);
                values[i] = weatherDatabase.getSymbolName(date);
            }
        }
        if (hour >= 10) {
            for (int i = 0; i <= 4; i++) {
                String date = dates[i] + ": " + hour + "h";
                temperatures[i] = weatherDatabase.getTemperatureValue(date);
                values[i] = weatherDatabase.getSymbolName(date);
            }
        }


    }
    // SETTING DATES
    private String[] getDates() {
        String[] dates = new String[5];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 5; i++) {
            dates[i] = sdf.format(c.getTime());//n
            c.add(Calendar.DATE, 1);
            Log.d(dates[i], "DATUM");

        }
        return dates;
    }


    private void checkConnection() {
        ConnectivityManager connManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();


        if (info != null && info.isConnected()) {
            boolean wifiConnected = info.getType() == ConnectivityManager.TYPE_WIFI;
            boolean mobileConnected = info.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {

                typeOfConn.setText(R.string.wifi_connected);
                connected = true;

            } else if (mobileConnected) {

                typeOfConn.setText(R.string.mobile_connected);
                connected = true;
            }
        } else {
            typeOfConn.setText(R.string.not_connected);
            connected = false;


        }


    }

    @Override
    protected void onDestroy() {
        weatherDatabase.deleteTable();
        super.onDestroy();
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
