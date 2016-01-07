package com.domo.macka.testtimeapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weather.db";
    public static final String TABLE_NAME = "weather";
    public static final String COLUMN_DATE_FROM = "datefrom";
    public static final String COLUMN_SYMBOL_NAME = "symbolname";
    public static final String COLUMN_TEMPERATURE_VALUE = "temperaturevalue";




    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_DATE_FROM + " TEXT PRIMARY KEY, "+
                COLUMN_SYMBOL_NAME + " TEXT, "+
                COLUMN_TEMPERATURE_VALUE + " TEXT " +
                " ); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME );
        onCreate(db);
    }
    public void addWeather(String dateFrom, String symbolName, String temperatureValue){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE_FROM,dateFrom);
        values.put(COLUMN_SYMBOL_NAME,symbolName);
        values.put(COLUMN_TEMPERATURE_VALUE,temperatureValue);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void deleteTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query  = "SELECT * FROM " + TABLE_NAME + " WHERE 1 ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("datefrom"))!=null){
                dbString += c.getString(c.getColumnIndex("datefrom")) +c.getString(c.getColumnIndex("symbolname")) + c.getString(c.getColumnIndex("temperaturevalue"));
                dbString += "\n";
                c.moveToNext();
            }
        }
        db.close();
        return dbString;
    }
    public String getSymbolName(String date){
        String symbol = "";
        SQLiteDatabase db = getWritableDatabase();
        String query  = "SELECT "+ COLUMN_SYMBOL_NAME + " FROM " +TABLE_NAME+ " WHERE "+ COLUMN_DATE_FROM + "= " +"'" +date+"'" +";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            symbol = c.getString(c.getColumnIndex("symbolname"));
            c.moveToNext();
        }
        return symbol;

    }
    public String getTemperatureValue(String date){
        String temperature = "";
        SQLiteDatabase db = getWritableDatabase();
        String query  = "SELECT "+ COLUMN_TEMPERATURE_VALUE + " FROM " +TABLE_NAME+ " WHERE "+ COLUMN_DATE_FROM + " = " +"'" +date+"'" +";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            temperature = c.getString(c.getColumnIndex("temperaturevalue"));

            c.moveToNext();
        }
        return temperature;
    }
    public String[] getSymbolNameClickedDate(String date){
        String[] symbols = new String[9];
        SQLiteDatabase db = getWritableDatabase();
        String query  = "SELECT "+ COLUMN_SYMBOL_NAME + " FROM " +TABLE_NAME+ " WHERE "+ COLUMN_DATE_FROM + " LIKE " +"'" +date+"%'" +";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        int i = 0;
        while(!c.isAfterLast()){
            symbols[i] = c.getString(c.getColumnIndex("symbolname"));
            i++;
            c.moveToNext();
        }
        return symbols;

    }
    public String[] getTemperatureValueClickedDate(String date){
        String[] temperatures = new String[9];
        SQLiteDatabase db = getWritableDatabase();
        String query  = "SELECT "+ COLUMN_TEMPERATURE_VALUE + " FROM " +TABLE_NAME+ " WHERE "+ COLUMN_DATE_FROM + " LIKE " +"'" +date+"%'" +";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        int i =0;
        while(!c.isAfterLast()){
            temperatures[i] = c.getString(c.getColumnIndex("temperaturevalue"));
            i++;
            c.moveToNext();
        }
        return temperatures;
    }
    public String[] getDatesClicked(String date){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+ COLUMN_DATE_FROM + " FROM " +TABLE_NAME+ " WHERE "+ COLUMN_DATE_FROM + " LIKE " +"'" +date+"%'" +";";
        Cursor c = db.rawQuery(query,null);
        String[] dates = new String[8];
        c.moveToFirst();
        int i = 0;
        while(!c.isAfterLast()){
            dates[i]= c.getString(c.getColumnIndex("datefrom"));
            i++;
            c.moveToNext();
        }
        return dates;
    }

}
