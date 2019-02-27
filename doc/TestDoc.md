# Testing

## Used Tools
* junit-jupiter-api:5.1.0
* mockito
* jacoco
* codacy

## JUnit
Tests are written in unit tests, each root folder with own goal in mind. Tests expect WebMap neighbours are picked in the following order : down, up, right, left.

* app - Tested if it calls components - Unit tests
* IOoperations - Unit tested only non io classes and mocked File if needed. Unit tests.
* mapGenerator - Tested user flow with happy and unhappy paths - Integration tests
* model - unit style tests. Testing that it works internally. - Unit tests
* searchAlgorithm - Integration tests where objective is to make sure unhappy cases wont result calling analysis writer, and happy cases call analysis writer with expected values. Expected values work as unit test for class itself. - Integration tests
* systemTools - only dateConverter is tested because others are firmware dependant - Unit tests

## Mocking
* Mockito is used to test that some important call are made
* Additional mock package for simplicity, contains test maps and mock analysis writer
* Maps to avoid copy paste and mistakes
* Mock analysis writer to evaluate its caller has called it with right parameters. 

## Test reporting
* Tests
1. run ``` gradle test ``` 
2. Test results will be in  /app/build/reports/tests/test/
3. Test coverage will be in /app/build/reports/jacoco/test/html/
4. Some setters and getters are made for testing only, They have a comment in code.

* Code quality
1. Push commit
2. Codecov begins analyzing
3. Badge updates automatically

### Test goals
* Passing percentage : 100
* Coverage: overall 90, branches 80
* Codacy certification level: A
* Currently achieved


## Not testing
* Output - Reason UI is not a priority.
* Classes requiring runtime, hardware, directory structure information or writing into a file. These are listed in build.gradle under jacocoTestReport task. - Reason they might pass even though under different environment conditions they would fail.

