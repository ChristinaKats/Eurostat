# Eurostat
Eurostat Tech Assignment

# Technical assignment
The technical assignment is split to separate tasks and you have to deliver it in a public Git repository.
The assignment has several small tasks. Please create separate branches per task.
Feel free to make assumptions in case something is not clear enough.
 
# Task 1
You have to create a CRUD REST API for our Entity (Crawler) using the tools and instructions described below.
 
Details:
You have to create a micorservice using the Jhipster framework.
Skip WebFlux, Discovery, Cache, other technologies, internationalization, extra testing frameworks and other generators
Use OAuth 2.0 for authentication
The application should have a PostgreSQL as a DB for all environments.
 
Our domain model is called Crawler. A crawler has the following characteristics:
Name: mandatory and characters allowed are latin alphabet, numbers and the symbols _-
Fetch interval: Mandatory integer and minimum value is -1
Source: Mandatory and has to be a valid URL
 
You must use DTOs, pagination and 3 layers (controller/service/repository)
 
# Task 2
Update the crawler entity to support dynamic crawling filters
 
We need to add an extra entity to our domain which is called Filters.
A crawler can have many filters but a filter belongs only to a single crawler.
The filter does not have a specific schema, we have to store the JSON in the DB.
You should not create a CRUD API for the filters. Filters are retrieved by using the crawler API.
Example of a crawler GET response
{
  "id": 1,
  "name": "my_crawler",
  "fetchInterval": 5,
  "source": "http://www.google.com",
  "filters":[{
    "id": 1,
    "configuration": {
      "type": "integer",
      "maxDepth" : 20,
      "minDepth" : 1,
      "maxOutlinks": 100,
      "minOutlinks": 0
    }
  }, {
    "id": 2,
    "configuration": {
      "type": "boolean",
      "domain" : true,
      "host" : false,
      "schema": false
    }
  }, {
    "id": 3,
    "configuration": {
      "type": "string",
      "inclusion" : "^.*(map).*$",
      "exclusion" : "^.*(contact).*$"
    }
  }]
}
 
Hint for task 2: Postgres jsonb
 
 
# Task 3
Filter validation
 
In the last task we have to create custom filter validation service.
The validation checks the configuration of each filter during the POST and PUT methods.
For filters with type integer we validate that all parameters have a value and the value must be greater than 0
For filters with type boolean we validate that all parameters have a value
For filters with type String we validate that all parameters have a value and is not blank
 
If the validation is successful we save the filter. Otherwise we return a 4XX response.
 
Hints for task 3: Functional Interface and Lambda.
 
Test coverage in each task has to be greater than 80%
Use Sonarqube to get some insights of your code quality.
