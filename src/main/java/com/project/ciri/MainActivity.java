package com.project.ciri;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnItemSelectedListener{

    String TAG = "MainActivity";
    private CheckBox chk1, chk2, chk3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc);

        addListenerOnCheck1();
        addListenerOnCheck2();
        addListenerOnCheck3();

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Accident");
        categories.add("Murder");
        categories.add("Robbery");
        categories.add("Rape");
        categories.add("Fire");
        categories.add("Chain Snatching");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


    private void addListenerOnCheck1() {
        chk1 = (CheckBox) findViewById(R.id.txtPolice);
        chk2 = (CheckBox) findViewById(R.id.txtAmbulance );
        chk3 = (CheckBox) findViewById(R.id.txtFire);
        chk2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
                result.append("POLICE Selection : ").append
                        (chk1.isChecked());
                result.append("\nAMBULANCE Selection : ").append
                        (chk2.isChecked());
                result.append("\nFIRE Selection :").append
                        (chk3.isChecked());

                Toast.makeText(MainActivity.this, result.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    // method for CheckBox2
    private void addListenerOnCheck2() {

        chk1 = (CheckBox) findViewById(R.id.txtPolice);
        chk2 = (CheckBox) findViewById(R.id.txtAmbulance);
        chk3 = (CheckBox) findViewById(R.id.txtFire);
        chk3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
                result.append("POLICE Selection : ").append
                        (chk1.isChecked());
                result.append("\nAMBULANCE Selection : ").append
                        (chk2.isChecked());
                result.append("\nFIRE Selection :").append
                        (chk3.isChecked());

                Toast.makeText(MainActivity.this, result.toString
                        (),Toast.LENGTH_LONG).show();
            }
        });
    }

    /* method for CheckBox3 - */
    private void addListenerOnCheck3() {
        // TODO Auto-generated method stub
        chk1 = (CheckBox) findViewById(R.id.txtPolice);
        chk2 = (CheckBox) findViewById(R.id.txtAmbulance);
        chk3 =  (CheckBox) findViewById(R.id.txtFire);

        chk1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
                result.append("POLICE Selection : ").append(chk1.isChecked());
                result.append("\nAMBULANCE Selection : ").append(chk2.isChecked());
                result.append("\nFIRE Selection :").append(chk3.isChecked());

                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClickAddName(View view) {
        // Add a new student record

        Log.i(TAG, "In on clickAddName()");
        ContentValues values = new ContentValues();

        values.put(StudentsProvider1.NAME,
                ((EditText)findViewById(R.id.txtName)).getText().toString());

        values.put(StudentsProvider1.TIMESTAMP,
                ((EditText)findViewById(R.id.txtTimestamp)).getText().toString());

        values.put(StudentsProvider1.LONGITUDE,
                ((EditText)findViewById(R.id.txtLongitude)).getText().toString());

        values.put(StudentsProvider1.LATITUDE,
                ((EditText)findViewById(R.id.txtLatitude)).getText().toString());

        values.put(StudentsProvider1.MEDIA_TYPE,
                ((EditText)findViewById(R.id.txtMedia)).getText().toString());

        values.put(StudentsProvider1.PATH,
                ((EditText)findViewById(R.id.txtPath)).getText().toString());

        values.put(StudentsProvider1.CONTACT_NUMBER,
                ((EditText)findViewById(R.id.txtContact)).getText().toString());

        values.put(StudentsProvider1.NOTE,
                ((EditText)findViewById(R.id.txtNote)).getText().toString());

       values.put(StudentsProvider1.INCIDENT_TYPE,
                ((Spinner) findViewById(R.id.spinner2)).getSelectedItem().toString());

        values.put(StudentsProvider1.POLICE,
                ((CheckBox) findViewById(R.id.txtPolice)).isChecked());

        values.put(StudentsProvider1.AMBULANCE,
                ((CheckBox) findViewById(R.id.txtAmbulance)).isChecked());

        values.put(StudentsProvider1.FIRE,
                ((CheckBox) findViewById(R.id.txtFire)).isChecked());



        Uri uri = getContentResolver().insert(
                StudentsProvider1.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }


}