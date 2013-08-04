package com.Almaarik.badr_api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	EditText search_text;
	Button search_button;
	ListView search_list;
	String  DEBUG_TAG = "look_here", myurl1 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyD_eSCF6heEay7b4-KSbKRHTeRGR-apcjY&location=-33.8670522,151.1957362&radius=500&sensor=false&keyword=", result; 
	ProgressDialog mydialog;
	List <Place> finalPlace = new ArrayList<Place>();
	PlaceItemsAdaptor myAdaptor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		search_text =  (EditText)findViewById(R.id.search_text);
		search_button = (Button)findViewById(R.id.search_button);
		search_list = (ListView)findViewById(R.id.search_list);
		search_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isNetworkAvailable()){
					String keyword = search_text.getText().toString();
					DownloadWebpageTask dl = new DownloadWebpageTask();
					dl.execute(formatUrl(keyword));
					mydialog = ProgressDialog.show(MainActivity.this, "Dounloading!", "please wait!");
				}else{
					Toast.makeText(MainActivity.this, "No Network connection", Toast.LENGTH_SHORT).show();
					
				}
			}	
		});
	myAdaptor = new PlaceItemsAdaptor(finalPlace, this);
	search_list.setAdapter(myAdaptor);
	Log.d(DEBUG_TAG, "on_crate_finished");
	}

	private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
              
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
			
        	
			if (mydialog != null && mydialog.isShowing()){
				mydialog.dismiss();
			}
			finalPlace.clear();
			finalPlace.addAll(getPlaces(result));
			myAdaptor.notifyDataSetChanged();
       }
    }
	private List <Place> getPlaces(String result){
		try {
			
			List <Place> myPlaces = new ArrayList<Place>();
			JSONObject myList = new JSONObject(result);
			JSONArray myJArray =  myList.getJSONArray("results");
			
			for(int i=0; i< myJArray.length(); i++)
			{
				
				JSONObject place = 	myJArray.getJSONObject(i);
				Place myplace = new Place();
				myplace.setInconUrl(place.getString("icon"));
				myplace.setId(place.getString("id"));
				myplace.setName(place.getString("name"));
				myplace.setAddress(place.getString("vicinity"));
				myPlaces.add(myplace);
			}
			return myPlaces;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	private String formatUrl(String keyword){
		
		StringBuilder finalurl = new StringBuilder(myurl1);
		finalurl.append(keyword);
		return finalurl.toString();
	}
	
	private String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	    // Only display the first 500 characters of the retrieved
	    // web page content.
	    int len = 500;
	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d(DEBUG_TAG, "The response is: " + response);
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = readIt(is, len);
	        return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connMgr = (ConnectivityManager) 
		        getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		    if (networkInfo != null && networkInfo.isConnected()) {
		        return true;
		    }else{
		    	return false;
		    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		BufferedReader r = new BufferedReader(new InputStreamReader(stream));
		StringBuilder total = new StringBuilder();
		String line, finals;
		while ((line = r.readLine()) != null) {
		    total.append(line);
		}
	    finals = total.toString();
		return finals;
	}
}
