# Project implementation

## Structure

#### OUTDATED was done before variants of algorithms were made.

**Here is shown structure of app and file directories it uses with their purpose**
```
searching-comparison-with-map-gen
├───doc
│   │  
│   └───reports
│       ├───firstMap
│       │   └───An algorithm
│       ├───secondMap
│       │   ├───An algorithm
│       │   └───more algorithms..
│       └───moreMaps
│           └───An algorithm
└───maps
│   ├───firstMap.map
│   ├───secondMap.map
│   └───moreMaps.map..
│    
├───java 
│   ├───app (java/app or jar)
│   │       App.java
│   │       Main.java
│   │
│   ├───file_operations
│   │   │   RootFolderFinder.java
│   │   │
│   │   ├───analysis_writer
│   │   │       AnalysisWriter.java
│   │   │       ReportValidator.java
│   │   │
│   │   └───map_reader
│   │           MapLoader.java
│   │           MapLocator.java
│   │
│   ├───map_generator
│   │       MapGenerator.java
│   │       MapGeneratorFromFiles.java
│   │       NoWeightSimpleGenerator.java
│   │
│   ├───model
│   │   ├───structure
│   │   │       FIFOQueue.java
│   │   │       LIFOStack.java
│   │   │       MinHeap.java
│   │   │       NodeHandlingType.java
│   │   │
│   │   └───web
│   │           WebMap.java
│   │           WeightedPoint.java
│   │
│   ├───search_algorithm
│   │       AnalysableAlgorithm.java
│   │       AStar.java
│   │       BreathSearch.java
│   │       DepthSearch.java
│   │       Dijkstra.java
│   │
│   └───system_tools
│           DateConverter.java
│           SystemSpecReader.java
```

### app
Source code package structure
Packagin divided by responsibility.

### doc
Test results are written here. Because this is a comparison map structure is map/algorithm/timestamp. This makes it easier to read differences in performance between algorithms.

### maps
Purpose of this directory is to store maps that app will read.
Maps are needed by MapGeneratorFromFiles from map_generator. It loads to program's memory.
Adding new maps simply follow guide under step 1.1 [Use case](https://github.com/kapistelijaKrisu/searching-comparison-with-map-gen/blob/master/doc/app_use_cases.md)  
Maps are picked recursively -> subfolders are allowed

## Program structure

### model, everything used by search algorithms
* package web contains representation of web
* WebMap Rectangular 2D representation of a map in app's memory. This is used as the structure of a web. It handles getting neighbours of an edge, getting start and target edge, evaluating itself to be a valid map with a name for analysis file creation. Evaluates also size and not having start or target location being a wall (value 0)..or having negative weight edges inside itself...that's just weird. Weight of vertice is seen as edge's weight value as in how much does it costs to get inside an edge.  
* WebMap's sum of all cells should not exceed a value of maximum integer. Error handling not done.
* WeightedPoint a representation of edge. It contains own coordinates and weight value of stepping into. Handles calculating distance between other edges as well.

* package structure contains helper structures that are needed by search algoithms. Uses generics.
* FIFOQueue classic first-in-first-out queue with references between nodes. Not a table.
* LIFO classic last-in-first-out stack with references between nodes. Not a table.
* MinHeap is a table of comparable object type to itself that is kept in minimum heap order. Meaning it uses interface Comparable to evaluate which object is smaller or bigger to figure out the smallest object inside itself. Readjusts table size by need. Mulptiplies size by 2 if needed or decreases by 2 if more than half empty having minimum of 16 as minimum. Index 0 is ignored for perfoming left/right child and parent access method simple and O(1).
#### app package, running program
* Main creates an instance of app and runs it.
* App's job is to offer user to set up a map and analyze a search algorithm performance on it.

#### map_generator, setting up a map
* App has two options for generating a map. Either a no weight user defined rectangular map or load it from maps directory.
* Case simple: NoWeightSimpleGenerator needs only user input and WebMap's validation.
* Case load: MapGeneratorFromFiles. This generator uses file_operations/map_reader classes for locating and loading a map into memory. Asking for user input in choosing the map.

#### file_operations, reaching outide of apps memory.
* RootFolderFinder locates path where app is located. Helper tool used to locate doc/ and maps/ Works from running files in ide or jar giving same result.
* MapLocator lists all files. Used by MapGeneratorFromFiles to give the user an option to pick from all found .map files.
* MapLoader returns an instance of WebMap from .map file and nothing if reading it fails. Used by MapGeneratorFromFiles to load a selected map by user.
* ReportValidator validates a report made by an implementation of AnalysableAlgorithm. Used by AnalysisWriter to validate wether to write a report into a file or not. Purpose of this is to make sure developer hasn't forgetten to mentioned all fields. See [template_usage](https://github.com/kapistelijaKrisu/searching-comparison-with-map-gen/blob/master/doc/template_usage.md)  
* AnalysisWriter writes a report .md format file into doc/reports/map name/algorithm name. Used by AnalysableAlgorithm.

#### search_algorithm, research subject
* Base class AnalysableAlgorithm handles giving ReportValidator its required values. Implementing class only has to fill a HashMap with all pairs of "where to search goes" and call
TODO find method name with received parameters. This uses system_tools/DateConverter to generate file name and system_tools/SystemSpecReader to fetch some analysis values from hardware and jvm.
* Implementing classes TODO

#### system_tools helpers to get information about system
* DateConverter gets the time system is set on and converts it to an acceptable filename format for multiple platforms. "-" used as a delimeter.
* SystemSpecReader gets information from processor name using oshim and of the running jvm from process todo is this correct class. 

### runtime dependencys
#### oshi dependency, find out the power
* Gets processor name. This is vital information when doin a performance analysis. With this it makes it possible to compare computer efficiency as well (not in scope of tira though).
* Downside. It uses TODO logger library which throws annoying output if used by non english operating system because it can't locate some system registrys. Doesn't affect App otherwise.

#### TODO process class, keeping track of memory
* As a java program is run on JVM and memory is handled by it. We calculate memory usage by JVM over RAM. This also solves problem of not needing to worry about other process' memory usage. 


TODO add urls for javadoc
TODO add pick


## Testing 
[testaus dokumentti](https://github.com/kapistelijaKrisu/searching-comparison-with-map-gen/blob/master/doc/TestDoc.md)

## Javadoc
Kaikki public ja protected. Löytyy javadoc kansiosta.