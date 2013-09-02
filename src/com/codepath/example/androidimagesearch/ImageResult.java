package com.codepath.example.androidimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Simple class to store the key details of an image from the Google Image Search API
public class ImageResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private String thumbUrl;
	private String fullUrl;
	
	public ImageResult(JSONObject json) {
		try {
			this.thumbUrl = json.getString("tbUrl");
			this.fullUrl = json.getString("url");
		} catch (JSONException e) {
			this.thumbUrl = null;
			this.fullUrl = null;
		}
	}
	
	public String getThumbUrl() {
		return thumbUrl;
	}
	
	public String getFullUrl() {
		return fullUrl;
	}
	
	public String toString() {
		return this.thumbUrl;
	}
	
	// Returns an ArrayList of ImageResult objects using the JSONArray. The Google API we use returns the image data as a JSON Array.
	public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
		ArrayList<ImageResult> imgArray = new ArrayList<ImageResult>();
		
		try {
			for (int i = 0; i < array.length(); i++) {
				imgArray.add(new ImageResult(array.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}			
		
		return imgArray;
	}
}
