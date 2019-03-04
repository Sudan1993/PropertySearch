package com.sudaraje.Property;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyController {

	PropertyRepository propertyRepo = new PropertyRepository();

	@GetMapping("/getAllProperties")
	public ResponseEntity<Object> getAllProperties() {
		return new ResponseEntity<>(propertyRepo.readJson(), HttpStatus.ACCEPTED);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/filter")
	public ResponseEntity<Object> search(@RequestBody Property propertyParams) {
		// min and max condition
		float distanceMatch = 0, budgetMatch = 0, bathroomMatch = 0, bedroomMatch = 0;
		if (propertyParams.getMinBudget() == 0 && propertyParams.getMaxBudget() == 0)
			return new ResponseEntity<>("Either Min or Max for Budget must be present", HttpStatus.NOT_ACCEPTABLE);
		else if (propertyParams.getMinBedroom() == 0 && propertyParams.getMaxBedroom() == 0)
			return new ResponseEntity<>("Either Min or Max for Bedroom must be present", HttpStatus.NOT_ACCEPTABLE);
		else if (propertyParams.getMinBathroom() == 0 && propertyParams.getMaxBathroom() == 0)
			return new ResponseEntity<>("Either Min or Max for Bathroom must be present", HttpStatus.NOT_ACCEPTABLE);
		else if (propertyParams.getLat() == 0 && propertyParams.getLng() == 0)
			return new ResponseEntity<>("Lat and Long must be present", HttpStatus.NOT_ACCEPTABLE);

		JSONArray properties = propertyRepo.readJson();
		JSONArray filteredArray = new JSONArray();
		Map<JSONObject, Float> propertyHashMap = new HashMap<JSONObject, Float>();
		for (Object o : properties) {
			JSONObject jsonObject = (JSONObject) o;
			// budget is between +/- 25%
			int givenBudget = (propertyParams.getMinBudget() != 0 && propertyParams.getMaxBudget() != 0) ? 0
					: (propertyParams.getMinBudget() == 0 && propertyParams.getMaxBudget() != 0)
							? (propertyParams.getMaxBudget())
							: (propertyParams.getMinBudget());
			int givenBathroom = (propertyParams.getMinBathroom() != 0 && propertyParams.getMaxBathroom() != 0) ? 0
					: (propertyParams.getMinBathroom() == 0 && propertyParams.getMaxBathroom() != 0)
							? propertyParams.getMaxBathroom()
							: propertyParams.getMinBathroom();
			int givenBedroom = (propertyParams.getMinBedroom() != 0 && propertyParams.getMaxBedroom() != 0) ? 0
					: (propertyParams.getMinBedroom() == 0 && propertyParams.getMaxBedroom() != 0)
							? propertyParams.getMaxBedroom()
							: propertyParams.getMinBedroom();
			float miles = CoordinateDistance.calculateDistanceInMiles(propertyParams.getLat(), propertyParams.getLng(),
					Double.valueOf((String) jsonObject.get("LAT")), Double.valueOf((String) jsonObject.get("LNG")));
			boolean condition = false;
			switch ("distance") {
			case "distance":
				if (miles <= 10) {
					condition = true;
				} else
					break;
			case "withinBudget":
				if (givenBudget != 0) {
					if (Float.valueOf((String) jsonObject.get("BUDGET")) <= givenBudget * 1.25
							&& Float.valueOf((String) jsonObject.get("BUDGET")) >= givenBudget * 0.75) {
						condition = true;
					} else
						break;
				} else if (propertyParams.getMinBudget() != 0 && propertyParams.getMaxBudget() != 0
						&& ((Integer.valueOf((String) jsonObject.get("BUDGET")) >= propertyParams.getMinBudget())
								&& Integer.valueOf((String) jsonObject.get("BUDGET")) <= propertyParams
										.getMaxBudget())) {
					condition = true;
				} else
					break;
			case "bedroomMatch":
				if (givenBedroom != 0) {
					if (((Integer.valueOf((String) jsonObject.get("BEDROOM"))) >= givenBedroom - 2)
							&& ((Integer.valueOf((String) jsonObject.get("BEDROOM"))) <= givenBedroom + 2)) {
						condition = true;
					} else
						break;
				} else if (propertyParams.getMinBedroom() != 0 && propertyParams.getMaxBedroom() != 0
						&& ((Integer.valueOf((String) jsonObject.get("BEDROOM")) >= propertyParams.getMinBedroom())
								&& Integer.valueOf((String) jsonObject.get("BEDROOM")) <= propertyParams
										.getMaxBedroom())) {
					condition = true;
				} else
					break;
			case "bathroomMatch":
				if (givenBathroom != 0) {
					if (((Integer.valueOf((String) jsonObject.get("BATHROOM"))) >= givenBathroom - 2)
							&& ((Integer.valueOf((String) jsonObject.get("BATHROOM"))) <= givenBathroom + 2)) {
						condition = true;
					} else
						break;
				} else if (propertyParams.getMinBathroom() != 0 && propertyParams.getMaxBathroom() != 0
						&& ((Integer.valueOf((String) jsonObject.get("BATHROOM")) >= propertyParams.getMinBathroom())
								&& Integer.valueOf((String) jsonObject.get("BATHROOM")) <= propertyParams
										.getMaxBathroom())) {
					condition = true;
				} else
					break;
			default:
				if (condition) {
					filteredArray.add(o);
				}
			}
		}
		for (Object obj : filteredArray) {
			int givenBudget = (propertyParams.getMinBudget() != 0 && propertyParams.getMaxBudget() != 0) ? 0
					: (propertyParams.getMinBudget() == 0 && propertyParams.getMaxBudget() != 0)
							? (propertyParams.getMaxBudget())
							: (propertyParams.getMinBudget());
			int givenBathroom = (propertyParams.getMinBathroom() != 0 && propertyParams.getMaxBathroom() != 0) ? 0
					: (propertyParams.getMinBathroom() == 0 && propertyParams.getMaxBathroom() != 0)
							? propertyParams.getMaxBathroom()
							: propertyParams.getMinBathroom();
			int givenBedroom = (propertyParams.getMinBedroom() != 0 && propertyParams.getMaxBedroom() != 0) ? 0
					: (propertyParams.getMinBedroom() == 0 && propertyParams.getMaxBedroom() != 0)
							? propertyParams.getMaxBedroom()
							: propertyParams.getMinBedroom();
			JSONObject jsonObj = (JSONObject) obj;
			boolean minMaxBudgetPresent = false;
			boolean minMaxBathroomPresent = false;
			boolean minMaxBedroomPresent = false;
			float distance = CoordinateDistance.calculateDistanceInMiles(propertyParams.getLat(),
					propertyParams.getLng(), Double.valueOf((String) jsonObj.get("LAT")),
					Double.valueOf((String) jsonObj.get("LNG")));
			if (distance < 2)
				distanceMatch = (float) 0.3;
			else if (propertyParams.getMinBudget() != 0 && propertyParams.getMaxBudget() != 0
					&& ((Integer.valueOf((String) jsonObj.get("BUDGET")) >= propertyParams.getMinBudget())
							&& Integer.valueOf((String) jsonObj.get("BUDGET")) <= propertyParams.getMaxBudget())) {
				minMaxBudgetPresent = true;
				budgetMatch = (float) 0.3;
			} else if ((givenBudget * 0.9 <= (Float.valueOf((String) jsonObj.get("BUDGET"))))
					&& (givenBudget * 1.1 >= (Float.valueOf((String) jsonObj.get("BUDGET"))))) {
				if (!minMaxBudgetPresent)
					budgetMatch = (float) 0.3;
			} else if (propertyParams.getMinBedroom() != 0 && propertyParams.getMaxBedroom() != 0
					&& ((Integer.valueOf((String) jsonObj.get("BEDROOM")) >= propertyParams.getMinBedroom())
							&& Integer.valueOf((String) jsonObj.get("BEDROOM")) <= propertyParams.getMaxBedroom())) {
				minMaxBedroomPresent = true;
				bedroomMatch = (float) 0.2;
			} else if ((givenBedroom - 1 <= (Integer.valueOf((String) jsonObj.get("BEDROOM"))))
					&& (givenBedroom + 1 >= (Integer.valueOf((String) jsonObj.get("BEDROOM"))))) {
				if (!minMaxBedroomPresent)
					bedroomMatch = (float) 0.1;
			} else if (propertyParams.getMinBedroom() != 0 && propertyParams.getMaxBedroom() != 0
					&& ((Integer.valueOf((String) jsonObj.get("BATHROOM")) >= propertyParams.getMinBedroom())
							&& Integer.valueOf((String) jsonObj.get("BATHROOM")) <= propertyParams.getMaxBedroom())) {
				minMaxBathroomPresent = true;
				bathroomMatch = (float) 0.2;
			} else if ((givenBathroom - 1 <= (Integer.valueOf((String) jsonObj.get("BATHROOM"))))
					&& (givenBathroom + 1 >= (Integer.valueOf((String) jsonObj.get("BATHROOM"))))) {
				if (!minMaxBathroomPresent)
					bathroomMatch = (float) 0.1;
			}
			float totalWeightage = distanceMatch + budgetMatch + bedroomMatch + bathroomMatch;
			if (totalWeightage >= 0.4)
				propertyHashMap.put(jsonObj, Float.valueOf(totalWeightage));
		}
		// sort the hashmap
		Map<JSONObject, Float> sortedMap = propertyHashMap.entrySet().stream()
				.sorted(Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		// send the keyset in the hashmap
		return new ResponseEntity<>(sortedMap.keySet(), HttpStatus.ACCEPTED);
	}
}