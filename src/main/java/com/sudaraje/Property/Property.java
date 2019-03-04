package com.sudaraje.Property;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Property {

	private double lat;
	private double lng;
	private int minBedroom;
	private int maxBedroom;
	private int minBathroom;
	private int maxBathroom;
	private int minBudget;
	private int maxBudget;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public int getMinBedroom() {
		return minBedroom;
	}
	public void setMinBedroom(int minBedroom) {
		this.minBedroom = minBedroom;
	}
	public int getMaxBedroom() {
		return maxBedroom;
	}
	public void setMaxBedroom(int maxBedroom) {
		this.maxBedroom = maxBedroom;
	}
	public int getMinBathroom() {
		return minBathroom;
	}
	public void setMinBathroom(int minBathroom) {
		this.minBathroom = minBathroom;
	}
	public int getMaxBathroom() {
		return maxBathroom;
	}
	public void setMaxBathroom(int maxBathroom) {
		this.maxBathroom = maxBathroom;
	}
	public int getMinBudget() {
		return minBudget;
	}
	public void setMinBudget(int minBudget) {
		this.minBudget = minBudget;
	}
	public int getMaxBudget() {
		return maxBudget;
	}
	public void setMaxBudget(int maxBudger) {
		this.maxBudget = maxBudger;
	}
	
}
