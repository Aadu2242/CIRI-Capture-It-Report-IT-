package com.project.ciri;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AndroidSQLiteTutorialActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout);
        
        DatabaseHandler db = new DatabaseHandler(this);

        //db.addReport();
//        SQLiteDatabase dbr;
//        db.onCreate(dbr);
//
        /**
         * CRUD Operations
         * */
        // Inserting Contacts

        Log.d("Insert: ", "Inserting ..");
        db.addReport(new Reports("Arwa",getDateTime(),4,5,"23667","Audio","abc","Murder",1,0,0,"asfghgsd"));
        db.addReport(new Reports("Aadesh",getDateTime(),4,5,"23667","Audio","abc","Murder",0,1,0,"fafaffs"));

//        db.addContact(new Contact("Srinivas", "9199999999"));
//        db.addContact(new Contact("Tommy", "9522222222"));
//        db.addContact(new Contact("Karthik", "9533333333"));
//
//        // Reading all contacts
//        Log.d("Reading: ", "Reading all contacts..");
//        List<Contact> contacts = db.getAllContacts();
//
//        for (Contact cn : contacts) {
//            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
//                // Writing Contacts to log
//        Log.d("Name: ", log);
//
//        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}