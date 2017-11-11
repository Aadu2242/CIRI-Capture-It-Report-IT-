package com.project.ciri;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "DB_CIRI";

	// Contacts table name
	private static final String TABLE_REPORTS = "reports";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_SENDER = "sender";
	private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_CONTACT_NUMBER = "cntct_nmbr";
    private static final String KEY_MEDIA_TYPE = "media_type";
    private static final String KEY_PATH = "path";
    private static final String KEY_TYPE_OF_INCIDENT = "incident_type";
    private static final String KEY_POLICE = "police";
    private static final String KEY_AMBULANCE = "ambulance";
    private static final String KEY_FIRE = "fire";
    private static final String KEY_NOTE = "note";



    public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_REPORTS_TABLE = "CREATE TABLE " + TABLE_REPORTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + KEY_SENDER + " TEXT NOT NULL,"
                + KEY_TIMESTAMP  + "  TEXT NOT NULL ,"
				+ KEY_LATITUDE + " REAL NOT NULL,"
                + KEY_LONGITUDE + "  REAL NOT NULL ,"
                + KEY_CONTACT_NUMBER  + "  TEXT ,"
                + KEY_MEDIA_TYPE  + "  TEXT NOT NULL ,"
                + KEY_PATH + "  TEXT NOT NULL ,"
                + KEY_TYPE_OF_INCIDENT  + "  TEXT NOT NULL ,"
                + KEY_POLICE + "  INT NOT NULL ,"
                + KEY_AMBULANCE  + "  INT NOT NULL ,"
                + KEY_FIRE  + "  INT NOT NULL ,"
                + KEY_NOTE + "  TEXT "
                + ")";
		db.execSQL(CREATE_REPORTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	//Adding new contact
	void addReport(Reports report) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_SENDER, report.get_sender());
        values.put(KEY_TIMESTAMP, report.get_timestamp());
        values.put(KEY_LATITUDE, report.get_latitude());
        values.put(KEY_LONGITUDE, report.get_longitude());
        values.put(KEY_CONTACT_NUMBER, report.get_cntct_nmbr());
        values.put(KEY_MEDIA_TYPE, report.get_media_type());
        values.put(KEY_PATH, report.get_path());
        values.put(KEY_TYPE_OF_INCIDENT, report.get_incident_type());
        values.put(KEY_POLICE, report.get_police());
        values.put(KEY_AMBULANCE, report.get_ambulance());
        values.put(KEY_FIRE, report.get_fire());
        values.put(KEY_NOTE, report.get_note());

		// Inserting Row
		db.insert(TABLE_REPORTS, null, values);
		//db.close(); // Closing database connection
	}



}
