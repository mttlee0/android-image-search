package com.codepath.example.androidimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.loopj.android.image.SmartImageView;

// Class to display the full-size image on the screen. Pretty simple.
public class DisplayFullImage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_full_image);
		
		// Get the ImageResult object and set the image URL for the SmartImageView
		ImageResult imgResult = (ImageResult) getIntent().getSerializableExtra("imgResult");
		SmartImageView ivFullImage = (SmartImageView) findViewById(R.id.ivFullImage);
		ivFullImage.setImageUrl(imgResult.getFullUrl());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_full_image, menu);
		return true;
	}

}
