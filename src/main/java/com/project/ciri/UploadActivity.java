package com.project.ciri;

import com.project.ciri.AndroidMultiPartEntity.ProgressListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class UploadActivity extends Activity {
	// LogCat tag
	private static final String TAG = "UploadActivity";

	private ProgressBar progressBar;
	private String filePath = null;
	private TextView txtPercentage;
	private ImageView imgPreview;
	private VideoView vidPreview;
	private Button btnUpload;
    private Button btnPlayAudio;
	long totalSize = 0;
    AppLocationService appLocationService;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		txtPercentage = (TextView) findViewById(R.id.txtPercentage);
		btnUpload = (Button) findViewById(R.id.btnUpload);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		imgPreview = (ImageView) findViewById(R.id.imgPreview);
		vidPreview = (VideoView) findViewById(R.id.videoPreview);
        btnPlayAudio = (Button) findViewById(R.id.btnPlayAudio);

        appLocationService = new AppLocationService(
                UploadActivity.this);

		// Changing action bar background color
//		getActionBar().setBackgroundDrawable(
//				new ColorDrawable(Color.parseColor(getResources().getString(
//						R.color.action_bar))));

		// Receiving the data from previous activity
		Intent i = getIntent();

		// image or video path that is captured in previous activity
		filePath = i.getStringExtra("filePath");

		// boolean flag to identify the media type, image or video
		String mediaType = i.getStringExtra("mediaType");


		if (filePath != null) {
			// Displaying the image or video on the screen
			previewMedia(mediaType);
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry, file path is missing!", Toast.LENGTH_LONG).show();
		}

        Location nwLocation = appLocationService
                .getLocation(LocationManager.NETWORK_PROVIDER);

        if (nwLocation != null) {
            double latitude = nwLocation.getLatitude();
            double longitude = nwLocation.getLongitude();
            Toast.makeText(
                    getApplicationContext(),
                    "Mobile Location (NW): \nLatitude: " + latitude
                            + "\nLongitude: " + longitude,
                    Toast.LENGTH_LONG).show();
        } else {
            showSettingsAlert("NETWORK");
        }

        //DatabaseHandler db = new DatabaseHandler(this);

		btnUpload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                // saving the report in SQLLite
                DatabaseHandler db = new DatabaseHandler(getBaseContext());
                String timestamp = getDateTime();

                Log.d("Insert: ", "Inserting ..");
                db.addReport(new Reports("Shrikant", timestamp, 45.67, 65.87, "9326994353", "image", "abc", "murder",1 ,1 ,0 ,"Blue eyes. Dark hair. Urgent Help."));


                // uploading the report to server
				new UploadFileToServer("Shrikant", timestamp, 45.67, 65.87, "9326994353", "image", "murder", 1, 1, 0, "Blue eyes. Dark hair. Urgent Help.").execute();
			}
		});



            //db.addReport();
//        SQLiteDatabase dbr;
//        db.onCreate(dbr);
//
            /**
             * CRUD Operations
             * */
            // Inserting Contacts

//        Log.d("Insert: ", "Inserting ..");
//        db.addReport(new Reports("Arwa",getDateTime(),4,5,"23667","Audio","abc","Murder",1,0,0,"asfghgsd"));
//        db.addReport(new Reports("Aadesh",getDateTime(),4,5,"23667","Audio","abc","Murder",0,1,0,"fafaffs"));

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

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                UploadActivity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        UploadActivity.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

	/**
	 * Displaying captured image/video on the screen
	 * */
	private void previewMedia(String mediaType) {
		// Checking whether captured media is image or video
		if (mediaType.contentEquals("image")) {
			imgPreview.setVisibility(View.VISIBLE);
			vidPreview.setVisibility(View.GONE);
			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// down sizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

			imgPreview.setImageBitmap(bitmap);
		} else if (mediaType.contentEquals("video")) {
			imgPreview.setVisibility(View.GONE);
			vidPreview.setVisibility(View.VISIBLE);
			vidPreview.setVideoPath(filePath);
			// start playing
			vidPreview.start();
		} else if (mediaType.contentEquals("audio")) {
            imgPreview.setVisibility(View.GONE);
            vidPreview.setVisibility(View.GONE);
            btnPlayAudio.setVisibility(View.VISIBLE);
        }

	}

    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException {

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(filePath);
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

    }

	/**
	 * Uploading the file to server
	 * */
	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        String strSender;
        String strTimestamp;
        double longi;
        double lati;
        String strCN;
        String strMT;
        String strIT;
        int police;
        int ambulance;
        int fire;
        String strNote;

        private UploadFileToServer(String sender, String timestamp, double longitude, double latitude, String cntct_nmbr, String media_type, String incident_type, int police, int ambulance, int fire, String note) {

            this.strSender = sender;
            this.strTimestamp = timestamp;
            this.longi = longitude;
            this.lati = latitude;
            this.strCN = cntct_nmbr;
            this.strMT = media_type;
            this.strIT = incident_type;
            this.police = police;
            this.ambulance = ambulance;
            this.fire = fire;
            this.strNote = note;
        }

        @Override
		protected void onPreExecute() {
			// setting progress bar to zero
			progressBar.setProgress(0);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			// Making progress bar visible
			progressBar.setVisibility(View.VISIBLE);

			// updating progress bar value
			progressBar.setProgress(progress[0]);

			// updating percentage value
			txtPercentage.setText(String.valueOf(progress[0]) + "%");
		}

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
            Log.i(TAG, "In UploadFile().");

			String responseString = null;

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

			try {
				AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
						new ProgressListener() {

							@Override
							public void transferred(long num) {
								publishProgress((int) ((num / (float) totalSize) * 100));
							}
						});

				File sourceFile = new File(filePath);

                Log.i(TAG, "In try{}.");

				// Adding file data to http body
				entity.addPart("image", new FileBody(sourceFile));

				// Extra parameters if you want to pass to server
				entity.addPart("website",
						new StringBody("www.androidhive.info"));
				entity.addPart("email", new StringBody("abc@gmail.com"));

                entity.addPart("sender",
                        new StringBody(strSender));
                entity.addPart("timestamp",
                        new StringBody(strTimestamp));
                //entity.a
                entity.addPart("longitude",
                        new StringBody(Double.toString(longi)));
                entity.addPart("latitude",
                        new StringBody(Double.toString(lati)));
                entity.addPart("cntct_nmbr",
                        new StringBody(strCN));
                entity.addPart("media_type",
                        new StringBody(strMT));
                entity.addPart("incident_type",
                        new StringBody(strIT));
                entity.addPart("police",
                        new StringBody(Integer.toString(police)));
                entity.addPart("ambulance",
                        new StringBody(Integer.toString(ambulance)));
                entity.addPart("fire",
                        new StringBody(Integer.toString(fire)));
                entity.addPart("note",
                        new StringBody(strNote));

				totalSize = entity.getContentLength();
				httppost.setEntity(entity);

				// Making server call
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity r_entity = response.getEntity();

				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					// Server response
					responseString = EntityUtils.toString(r_entity);
				} else {
					responseString = "Error occurred! Http Status Code: "
							+ statusCode;
				}

			} catch (ClientProtocolException e) {
				responseString = e.toString();
			} catch (IOException e) {
				responseString = e.toString();
			}

			return responseString;

		}

		@Override
		protected void onPostExecute(String result) {
			Log.e(TAG, "Response from server: " + result);

			// showing the server response in an alert dialog
			showAlert(result);

			super.onPostExecute(result);
		}

	}

	/**
	 * Method to show alert dialog
	 * */
	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setTitle("Response from Servers")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do nothing
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}