package com.project.ciri;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UseCameraActivity extends Activity {

    protected static final String TAG = "UseCameraActivity";
    protected static final int MEDIA_TYPE_IMAGE = 0;

    private Camera cameraObject;
    private ShowCamera showCamera;
    private ImageView pic;
    private FrameLayout preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_camera);
        pic = (ImageView)findViewById(R.id.imageView1);
        cameraObject = isCameraAvailiable();
        showCamera = new ShowCamera(this, cameraObject);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(showCamera);
    }

    public void snapIt(View view) {
        cameraObject.takePicture(null, null, capturedIt);

        //Get the location coordinates
        GetCoordinates_AsyncTask mGetCoordinates_AsyncTask = new GetCoordinates_AsyncTask(getBaseContext());
        mGetCoordinates_AsyncTask.execute("");
    }


    public static Camera isCameraAvailiable(){
        Camera object = null;
        try {
            object = Camera.open();
        }
        catch (Exception e){

        }
        return object;
    }

    private PictureCallback capturedIt = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {


            Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);

            if(bitmap==null){
                Toast.makeText(getApplicationContext(), "not taken", Toast.LENGTH_SHORT).show();
            }
            else
            {
                pic.setImageBitmap(bitmap);
                preview.addView(showCamera);

                Toast.makeText(getApplicationContext(), "taken", Toast.LENGTH_SHORT).show();
            }
            cameraObject.release();

            //cameraObject.startPreview();

            File picFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            if (picFile == null) {
                Log.e(TAG, "Couldn't create media file; check storage permissions?");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(picFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.e(TAG, "File not found: " + e.getMessage());
                e.getStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "I/O error writing file: " + e.getMessage());
                e.getStackTrace();
            }
        }
    };

    private File getOutputMediaFile(int type) {
//        File dir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), getPackageName());

        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CIRI");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(TAG, "Failed to create storage directory.");
                return null;
            }
        }

        String timeStamp =  new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());

        if (type == MEDIA_TYPE_IMAGE) {
            return new File(dir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
    }


    public class GetCoordinates_AsyncTask extends AsyncTask<String, Void, Boolean> {

        Context mContext = null;
        String strNameToSearch = "";

        //LatLng latLng;
        double latitude = 0;
        double longitude = 0;

        //Result data
        String strFirstName;


        Exception exception = null;

        GetCoordinates_AsyncTask(Context context){
            mContext = context;

        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try{


                // Get LocationManager object from System Service LOCATION_SERVICE
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                // Create a criteria object to retrieve provider
                //Criteria criteria = new Criteria();

                // Get the name of the best provider
                //String provider = locationManager.getBestProvider(criteria, true);

                String locationProvider = LocationManager.NETWORK_PROVIDER;

                //String locationProvider = LocationManager.GPS_PROVIDER;

                // Get Current Location
                Location myLocation = locationManager.getLastKnownLocation(locationProvider);

                // set map type
                // mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



                if (myLocation != null) {
                    // Get latitude of the current location
                    latitude = myLocation.getLatitude();

                    // Get longitude of the current location
                    longitude = myLocation.getLongitude();
                    Log.i(TAG, "Inside IF");

                    System.out.println("Inside if:");
                }
                Log.i(TAG, "Latitude is: "+ latitude+ " Longitude: "+longitude);

                System.out.println("Latitude: "+ latitude+ " Longitude: "+longitude);


            }catch (Exception e){
                Log.i(TAG, "Error:", e);
                exception = e;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean valid){


            Toast.makeText(getApplicationContext(), "Latitude is: "+ latitude+ " Longitude: "+longitude, Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_use_camera, menu);
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

    @Override
    protected void onPause() {
        if (cameraObject != null) {
            cameraObject.release();
            cameraObject = null;
        }
        super.onPause();
    }
}
