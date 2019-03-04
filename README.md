The API's are created using Spring Boot
POST call
1. localhost:8091/filter
{
"category" : "Upper Primary",
"gender" : "co-ed",
"mediumOfIns" : "english"
}
GET CALL
1. localhost:8091/search/Gopalapura
	When a search is performed the resultant json array contains a json object which gives the values applicable for the filters
2. localhost:8091/sort/schoolname
3. localhost:8091/sort/mediumOfInstruction
4. localhost:8091/sort/pincode

It is done with the help of Spring boot.
Importing the project in eclipse and doing a maven install should make the server up and running
Can be verified using postman 
