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

public class PropertyRepository {

	public static JSONArray jsonArray = null;

	public PropertyRepository() {
		readJson();
	}

	public JSONArray readJson() {
		JSONParser parser = new JSONParser();
		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(System.getProperty("user.dir") + "\\property.json"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

}
