package com.domo.macka.testtimeapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Macka on 11/20/15.
 */


public class XMLParserJava {
    private String urlString;
    private String city;
    private String latitude;
    private String longitude;
    public MyDBHandler database;
    private String date_from;
    private String symbol_name;
    private String temperature_value;



    private boolean checkTodayTemp = false;
    private int timeNow = 15;


    private XmlPullParserFactory xmlFactoryObject;
    public boolean parsingComplete = true;

    public XMLParserJava(String url, MyDBHandler database) {

        this.urlString = url;
        this.database = database;
    }

    public void parseXMLandStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;


        try {

            event = myParser.getEventType();
            int i = 0;
            while (event != XmlPullParser.END_DOCUMENT) {

                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("location")) {
                            latitude = myParser.getAttributeValue(null, "latitude");
                            longitude = myParser.getAttributeValue(null, "longitude");
                        }
                        if (name.equals("time")) {

                            String timeFrom = myParser.getAttributeValue(null, "from");
                            date_from = timeFrom.substring(0, 10) +":"+ timeFrom.substring(11, 13)+"h";
                            i++;

                        }
                        if (name.equals("symbol")) {
                            String symbol = myParser.getAttributeValue(null, "name");
                            symbol_name = symbol;
                            i++;
                        }
                        if (name.equals("temperature")) {
                            String temperature = myParser.getAttributeValue(null,"value");
                            temperature_value = temperature;
                            i++;
                        }


                        break;

                    case XmlPullParser.TEXT:

                        text = myParser.getText();

                        break;

                    case XmlPullParser.END_TAG:


                        if (name.equalsIgnoreCase("name")) {
                            city = text;
                        }


                        break;

                }
                if(i == 3){
                    i = 0;
                    database.addWeather(date_from,symbol_name,temperature_value);
                }
                event = myParser.next();

            }
            parsingComplete = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();
                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLandStoreIt(myparser);
                    stream.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }



    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}
