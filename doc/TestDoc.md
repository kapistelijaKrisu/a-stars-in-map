# Testing

## Used Tools
* junit-jupiter-api:5.1.0
* mockito
* jacoco

## JUnit
Tests are written in unit tests, each root folder with own way of testing

* app - Tested if it calls components
* IOoperations - Unit tested only non io classes. Jar wont work same way with files anyway -> pointless
* mapGenerator - Tested user flow with happy and unhappy paths
* model - unit style tests. Testing that it works internally.
* searchAlgorithm - Integration tests where objective is to make sure unhappy cases wont result calling analysis writer, and happy cases call analysis writer with expected values. Expected values work as unit test for class itself.
* systemTools - only dateConverter is tested because others are firmware dependant

## Mocking
* Mockito is used to test that some important call are made
* Additional mock package for simplicity, contains test maps and mock analysis writer
* Maps to avoid copy paste and mistakes
* Mock analysis writer to evaluate its caller has called it with right parameters. 

## Test reporting
1. run ``` gradle test ``` 
2. Test results will be in  /app/build/reports/tests/test/
3. Test coverage will be in /app/build/reports/jacoco/test/html/
4. Some setters and getters are made for testing only, They are commented in code.

Ideal would be to keep passing percentage 100 and coverage 90