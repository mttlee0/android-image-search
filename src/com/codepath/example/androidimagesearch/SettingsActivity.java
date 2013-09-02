package com.codepath.example.androidimagesearch;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

// Activity for the settings and filters
public class SettingsActivity extends Activity {
	Spinner spImageSize;
	Spinner spColorFilter;
	Spinner spImageType;
	EditText etSiteFilter;
	//String strSearchQuery;
	Button btSave;
	
	// String arrays for the possible values for the filters
	static String[] arrImageSizes = {"all", "icon", "small", "medium", "large", "xlarge", "xxlarge", "huge"};
	static String[] arrColorFilters = {"all", "black", "blue", "brown", "gray", "green", "orange", "pink", "purple", "red", "teal", "white", "yellow"};
	static String[] arrImageTypes = {"all", "face", "photo", "clipart", "lineart"};
	
	// Maps to allow fast determination of the position of the Spinner based on the value.
	// TODO: Find a better class/technique for 1) to populate the Spinners 2) map the value of the spinner to the position in the Spinner. 
	static final Map<String, Integer> mapImageSizes;
	static final Map<String, Integer> mapColorFilters;
	static final Map<String, Integer> mapImageTypes;
    static
    {
        mapImageSizes = new HashMap<String, Integer>();
        mapImageSizes.put("all", 0);
        mapImageSizes.put("icon", 1);
        mapImageSizes.put("small", 2);
        mapImageSizes.put("medium", 3);
        mapImageSizes.put("large", 4);
        mapImageSizes.put("xlarge", 5);
        mapImageSizes.put("xxlarge", 6);
        mapImageSizes.put("huge", 7);
        
        mapColorFilters = new HashMap<String, Integer>();
        mapColorFilters.put("all", 0);
        mapColorFilters.put("black", 1);
        mapColorFilters.put("blue", 2);
        mapColorFilters.put("brown", 3);
        mapColorFilters.put("gray", 4);
        mapColorFilters.put("green", 5);
        mapColorFilters.put("orange", 6);
        mapColorFilters.put("pink", 7);
        mapColorFilters.put("purple", 8);
        mapColorFilters.put("red", 9);
        mapColorFilters.put("teal", 10);
        mapColorFilters.put("white", 11);
        mapColorFilters.put("yellow", 12);
        
        mapImageTypes = new HashMap<String, Integer>();
        mapImageTypes.put("all", 0);
        mapImageTypes.put("face", 1);
        mapImageTypes.put("photo", 2);
        mapImageTypes.put("clipart", 3);
        mapImageTypes.put("lineart", 4);        
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setupViews();

	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}	
	
	// Method for: 1) find the views 2) populate the spinners 3) set the selection of the spinner based on past selections 4) setup a listener for the save button and pass the settings back to the main activity
	private void setupViews() {		
		spImageSize = (Spinner) findViewById(R.id.spImageSize);
		spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
		spImageType = (Spinner) findViewById(R.id.spImageType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		btSave = (Button) findViewById(R.id.btSave);
		
		spImageSize.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SettingsActivity.arrImageSizes));
		spColorFilter.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SettingsActivity.arrColorFilters));
		spImageType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SettingsActivity.arrImageTypes));
		
		spImageSize.setSelection(SettingsActivity.mapImageSizes.get(getIntent().getStringExtra("imageSize")));
		spColorFilter.setSelection(SettingsActivity.mapColorFilters.get(getIntent().getStringExtra("colorFilter")));
		spImageType.setSelection(SettingsActivity.mapImageTypes.get(getIntent().getStringExtra("imageType")));
		etSiteFilter.setText(getIntent().getStringExtra("siteFilter"));
		
		// TODO: Is there a way to save the settings? Right now, I hack it but sending the settings back & forth between the Main Activity and this one for persistence.
		btSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent data = new Intent();
				data.putExtra("imageSize", (String)spImageSize.getSelectedItem());
				data.putExtra("colorFilter", (String)spColorFilter.getSelectedItem());
				data.putExtra("imageType", (String)spImageType.getSelectedItem());
				data.putExtra("siteFilter", etSiteFilter.getText().toString());
				
				if (getParent() == null) {
				    setResult(Activity.RESULT_OK, data);
				} else {
				    getParent().setResult(Activity.RESULT_OK, data);
				}
				finish();
			}
		});
	}

}
