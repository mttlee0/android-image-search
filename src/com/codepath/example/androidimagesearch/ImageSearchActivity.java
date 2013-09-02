package com.codepath.example.androidimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

// Main class for the Image Search application. Primary purpose is to display images in a grid based on a search query.
public class ImageSearchActivity extends Activity {
	EditText etSearchQuery;
	Button btSearch;
	GridView gvImageGrid;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
	static private String googleImageSearchRestUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
	
	// Default settings for the filters/settings
	String strImageSize = "all";
	String strColorFilter = "all";
	String strImageType = "all";
	String strSiteFilter = "";
	// Integer to keep track of the index of the image search results
	int startIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvImageGrid.setAdapter(imageAdapter);
		
		// Create a listener when the user clicks on an image and it will start a new activity to display the full-sized image
		gvImageGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(), DisplayFullImage.class);
				ImageResult imgResult = imageResults.get(position);
				i.putExtra("imgResult", imgResult);
				startActivity(i);
			}
		});
	}
	
	// Finds the views and creates a Listener for the Search button to send a HTTP request to retrieve image data from Google
	private void setupViews() {
		etSearchQuery = (EditText) findViewById(R.id.etSearchQuery);
		btSearch = (Button) findViewById(R.id.btSearch);
		gvImageGrid = (GridView) findViewById(R.id.gvImageGrid);
		
		btSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "Searching for " + etSearchQuery.getText().toString(), Toast.LENGTH_SHORT).show();
				startIndex = 0;
				queryGoogleAndDisplay();								
			}
		});
		
	}
	
	// TODO: Replace using a button to load more images and use a ScrollListener
	// This method is linked to the onClick for the "See More" button. It will get the next 8 images from Google given the search query.
	public void refreshGrid(View view) {
		startIndex += 8;
		Toast.makeText(getApplicationContext(), "Loading more images...", Toast.LENGTH_SHORT).show();
		queryGoogleAndDisplay();
	}

	// This method is linked to the Settings/Filter icon in the ActionBar. It will start a new activity to allow the user to specify filters on the search query.
	public void onSettingsClick(MenuItem item) {
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		i.putExtra("imageSize", strImageSize);
		i.putExtra("colorFilter", strColorFilter);
		i.putExtra("imageType", strImageType);
		i.putExtra("siteFilter", strSiteFilter);
		// TODO: Ask Tim/Nathan why the below doesn't work when ACTIVITY.RESULT_OK is used for the request code
		startActivityForResult(i, 7);
	}
	
	// Given a search query, this method will submit the query to Google, get the data back asynchronously as JSON and use the ImageAdapter to display the images in the GridView
	private void queryGoogleAndDisplay() {						
		AsyncHttpClient client = new AsyncHttpClient();
		String fullSearchUrl = ImageSearchActivity.googleImageSearchRestUrl + "&q=" + Uri.encode(etSearchQuery.getText().toString()) + "&start=" + String.valueOf(startIndex);				
		
		if (!strImageSize.equals("all")) {
			fullSearchUrl = fullSearchUrl + "&imgsz=" + strImageSize;
		}
		
		if (!strColorFilter.equals("all")) {
			fullSearchUrl = fullSearchUrl + "&imgcolor=" + strColorFilter;
		}
		
		if (!strImageType.equals("all")) {
			fullSearchUrl = fullSearchUrl + "&imgtype=" + strImageType;
		}

		if (strSiteFilter != null && strSiteFilter.length() > 0) {
			fullSearchUrl = fullSearchUrl + "&as_sitesearch=" + strSiteFilter;
		}
				
		client.get(fullSearchUrl, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));							
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// onActivityResult() for when control returns back to this activity after the user is finished with the Settings/Filters screen. Search query will be resubmitted at this time.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		strImageSize = data.getStringExtra("imageSize");
		strColorFilter = data.getStringExtra("colorFilter");
		strImageType = data.getStringExtra("imageType");
		strSiteFilter = data.getStringExtra("siteFilter");
		startIndex = 0;		
		
		queryGoogleAndDisplay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}

}
