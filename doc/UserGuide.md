# Building app

Requires java 11

0. clone this repo from https://github.com/kapistelijaKrisu/searching-comparison-with-map-gen
1. Go to its app folder 
2. run in cmd ```gradle build```
3. now in projectfolder/app/build/libs has a jar
4. place jar somewhere next to /maps folder if you have .map files described in [Use cases in Finnish](https://github.com/kapistelijaKrisu/searching-comparison-with-map-gen/blob/master/doc/app_use_cases.md). You can use the ones that come with the git repository. Meaning drop the jar inside cloned projectfolder/.
5. java -jar app-1.x.x.jar where x is version number
6. follow instructions from cmd

[Use cases in Finnish](https://github.com/kapistelijaKrisu/searching-comparison-with-map-gen/blob/master/doc/app_use_cases.md)  Might be helpful if something is not understood by cmd instructor.

All file paths are relative from where the app is located.