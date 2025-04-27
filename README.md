# Anagram Finder
A simple command line utility for finding anagrams in a specified file

## Software required to run this
* Java 17

## Building and Running the tests
```
./gradlew clean build
```

## Running the program
```
./gradlew bootRun --args="example2.txt" 
```
where example2.txt is the text file that we want to search for anagrams

It may be necessary to include the `GRADLE_OPTS` at the beginning if the gradle wrapper struggles with memory due to 
writing to the console:
```
GRADLE_OPTS=-Xmx1g ./gradlew bootRun --args="example2.txt"
```

## Solution discussion

