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

### Data model

To help with separating out concerns and possible future expansion, I decided to explore having a producer ->
transformer -> consumer type approach.

As for how to model the specific anagram problem, processing the words and identifying a common index led to using a
map, where the map key is the index to a group of anagrams. By sorting the characters of each word this provided the
index and allowed for easy parsing of all the words.

Initial implementation didn't consider memory implications and was very simple to implement. It quickly became a problem
with large data sets. The change here was to batch the data based on string size given related anagrams will be of the
same length. This solution so far has proven to be a good balance of simplicity and memory efficiency.

### Big O Notation

Processing of the data is quite simple:

1. each line of the file is read into batches
1. each line of each batch is read and chunked into anagram groups
1. each line of each anagram is read, joined and printed

Based on this I was consider this to be `O(3n)`.

### Future ideas

Some other examples can be found on the `more-time` branch. Most notable was an attempt to be more memory efficient by
means of building out an in-memory tree model of all the indexes. This benefits by deduplicating characters and
compressing the overall memory foot print.

Though this worked it wasn't anymore memory efficient, though I believe this was likely due to a combination of the data
set and my attempt to roll my own word handling within `SimpleCharBuffer`.

Given more time to expand on this approach, I'd be keen to leverage this tree but instead of holding the anagrams, write
the groups out to file and instead have the tree index positions within the file. This approach could allow for more
controlled batching, such as configuring a batch size of 1000, rather than splitting batches of word length.

Building on this further, utilising the producer/consumer approach, a queue based consumer implementation could be built
along with multithreading, to allow for concurrent processing. This solution could allow for very large data sets and be
much faster to process. 