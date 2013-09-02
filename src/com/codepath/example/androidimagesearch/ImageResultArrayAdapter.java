package com.codepath.example.androidimagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

// Adapter for the GridView that will display a SmartImageView into the grid cells
public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult>{
	public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.item_image_result, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imgResult = this.getItem(position);
		SmartImageView imgView;
		
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			imgView = (SmartImageView) inflater.inflate(R.layout.item_image_result, parent, false);
		} else {
			imgView = (SmartImageView) convertView;
			imgView.setImageResource(android.R.color.transparent);
		}
		
		imgView.setImageUrl(imgResult.getThumbUrl());
					
		return imgView;
	}
}
