The API's are created using Sping Boot API.

Initially I populated the data with the help of Excel using some formulae and then I converted the excel to json which acts as a data source.

The POST API is localhost:8091/filter
which takes in minimum of five to eight params where either a min or a max value should be present for Budget, Bedroom and Bathroom .
Both Lat and lng should be present.
Example:
{
	"lat" : 77.5009,
	"lng" : 13.03224,
	"minBudget" : 27000,
	"maxBudget":30000,
	"maxBedroom" : 3,
	"minBathroom" : 2,
	"maxBathroom" : 3
}

Initially the filter will happen based on the base criteria provided in the requirement. After which the weightage logic is incorporated into it. This is done with the help of a HashMap. The HashMap is then sorted based on the value present which is nothing but weightage and the corresponding keyset is returned.

In Excel the Bedroom column can have three values between 1 and 3 based on which Bathroom is designed where 1-Bedroom will have a minimum of 1 Bathroom and 2-Bedroom can have 1 or 2 bathrooms and 3Bedroom asset can have 1 or 2 or 3 bathrooms in it. Based on the Bedroom value Budget is populated accordingly.
