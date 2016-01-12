package com.domo.macka.testtimeapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGrid extends BaseAdapter {
    private Context myContext;
    private TextView textView;
    private ImageView imageView;

    private final String[] dates_from;
    private final String[] symbol_names;
    private final String[] temperature_values;


    public MyGrid(Context c, String[] dates, String[] values, String[] temperatures) {
        myContext = c;
        this.dates_from = dates;
        this.symbol_names = values;
        this.temperature_values = temperatures;


    }

    @Override
    public int getCount() {
        return dates_from.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater layoutInflater = (LayoutInflater) myContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = layoutInflater.inflate(R.layout.grid_single, null);
            textView = (TextView) grid.findViewById(R.id.text_grid);
            textView.setTextColor(myContext.getResources().getColorStateList(R.color.colorTextTwo));
            imageView = (ImageView) grid.findViewById(R.id.image_grid);

            Log.d(dates_from[0] + " " + symbol_names[0] + " " + temperature_values[0], "PRVA");
            if (symbol_names[position] != null) {
                setGeneralWeatherIcon(symbol_names[position]);
                textView.setText(dates_from[position] + "\n" + symbol_names[position] + "\n" + temperature_values[position] + "C");
            }

        } else {
            grid = convertView;
        }
        return grid;
    }

    public void setGeneralWeatherIcon(String description) {

        String weather = description;
        if (weather.contains("clear")) {
            imageView.setImageResource(R.drawable.clear_sky_d);
        }

        if (weather.contains("few clouds")) {
            imageView.setImageResource(R.drawable.few_clouds_d);
        }
        if (weather.contains("scattered clouds")) {
            imageView.setImageResource(R.drawable.scattered_clouds);
        }
        if (weather.contains("broken clouds")) {
            imageView.setImageResource(R.drawable.broken_clouds);
        }
        if (weather.contains("shower rain")) {
            imageView.setImageResource(R.drawable.shower_rain);
        }
        if (weather.contains("rain")) {
            imageView.setImageResource(R.drawable.rain_d);
        }
        if (weather.contains("overcast")) {
            imageView.setImageResource(R.drawable.broken_clouds);
        }
        if (weather.contains("thunder storm")) {
            imageView.setImageResource(R.drawable.thunderstorm);
        }
        if (weather.contains("snow")) {
            imageView.setImageResource(R.drawable.snow);
        }
        if (weather.contains("mist")) {
            imageView.setImageResource(R.drawable.mist);
        }
    }
}
