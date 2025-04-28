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

Trying to reason with this, it is most definitely not `O(1)` or even `O(n)`. It's more along the lines of: `n x batch count x
chunk count`. So it's more than `O(3n)` but considerably less than `O(n^3)`.

### Future ideas

Some other examples can be found on the `more-time` branch. Most notable was an attempt to be more memory efficient by
means of building out an in-memory tree model of all the indexes. This benefits by deduplicating characters and
compressing the overall memory footprint. The memory footprint of the tree would be as wide as there are characters (26)
and as deep as the longest word. This could very quickly consume a lot of memory with `1 + c + c^2 + c^3...`, so 
`1 + 26+ 26^2 + 26^3...` and so forth. The hope here would be that the data inputted would reflect more real life 
scenarios with real words, and therefore the breath of the tree would be smaller given the compression nature of the 
tree.

Though this worked it wasn't anymore memory efficient, though I believe this was likely due to a combination of the data
set and my attempt to roll my own word handling within `SimpleCharBuffer`.

Given more time to expand on this approach, I'd be keen to leverage this tree but instead of holding the anagrams, write
the groups out to file and instead have the tree index positions within the file. This approach could allow for more
controlled batching, such as configuring a batch size of 1000, rather than splitting batches of word length.

Building on this further, utilising the producer/consumer approach, a queue based consumer implementation could be built
along with multithreading, to allow for concurrent processing. This solution could allow for very large data sets and be
much faster to process. With this map/reduce approach we could see Big O reducing significantly, more in line with
`O(log n)`.