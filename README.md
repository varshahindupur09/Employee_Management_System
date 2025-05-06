# Flywire Interview Exercise

> Create an application to act as a web service for managing and retrieving company employee information. Please use any frameworks and libraries you feel comfortable with and would make sense for the situation. The application uses Spring Boot framework and Apache Tomcat to run the app locally. The data.json file will be the data source used to. If you would like to convert the JSON file into a different database respository, feel free. This base project should provide everything needed to start the web service application.

> All http requests must return a JSON response.

<b>Once you are ready to submit your code, please either create a repo or a zip file.<br/>Name it: "{your-last-name}-flywire-exercise"<b>
 
 1. Create an http request endpoint that returns a list of all active employees in alphabetical order of last name.
 curl http://localhost:8080/api/employees/active
 curl http://localhost:8080/api/employees/inactive
 
 2. Create an http request endpoint that takes in an ID and returns a JSON response of the matching employees, as well as the names of their direct hires. Employee IDs are unique.
 curl http://localhost:8080/api/employees/1
 
 3. Create an http request endpoint that takes a date range, and returns a JSON response of all employees hired in that date range. Sort by descending order of date hired.
 curl "http://localhost:8080/api/employees/hired?startDate=01/01/2015&endDate=12/31/2020"
 
 4. Create an http request endpoint that takes a name, id, position, direct reports, and manager to creates a new employee. The employee should be added to the JSON file. Add any validation and error handling you see fit.
 curl -X POST http://localhost:8080/api/employees \
-H "Content-Type: application/json" \
-d '{
  "id": 203,
  "name": "Jason Aqua",
  "position": "Marketing Lead",
  "directReports": [24, 129]
}'
 
 5. Create an http request endpoint that takes in an ID and deactivates an employee. Add any validation you see fit.
 curl -X DELETE http://localhost:8080/api/employees/132

 FRONT-END: Create a web page show a list of employees. Flex your design skills and front-end experience to make it as complex or presentable as you like.