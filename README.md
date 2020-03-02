# BigPay Task

## Getting Started

### Prerequisites
Project requires [Open JDK 13](https://www.azul.com/downloads/zulu-community/?&version=java-13&architecture=x86-64-bit&package=jdk) and [Maven](https://maven.apache.org/) 

### Installation
Clone project from [GitHub](https://github.com/glyubomirov/bigpaytask/) to empty folder

```bash
git clone https://github.com/glyubomirov/bigpaytask.git
```

Go to project directory

```bash
cd ./bigpaytask/
```

Use the package manager [maven](https://maven.apache.org/) to package build

```bash
mvn package
```

### Run Project
Go to project directory and execute

```bash
java -jar ./target/BigPayTask.jar < simple_input.txt
```

You can run it with more complex data
```bash
java -jar ./target/BigPayTask.jar < extended_input.txt
```

![Link](https://github.com/glyubomirov/bigpaytask/blob/master/resources/simple_graph.png) 

This is how graph looks like from simple input above

## Terms

* **Path** - Minimal distance between two stations

* **Connected Roads** - Roads that have common Station

* **Connected Road List** - List Connected Roads where first and second road has common Station, second and third have common Station and etc. 

* **Letter Path** - Path between source and target Letter Station

* **Train Path** - Path between current and target train Station

* **Nearest Letter Path** - Path between train and closest station with undelivered Letters

* **Source Letter Destination** - Station that letter starts its journey from.

* **Current Letter Destination** - Station that letter belongs to. It is possible to be nul if letter is on the train.

* **Target Letter Destination** - Station that letter has to be delivered to.

## Algorithm

Algorithm is separated in following steps:

I. Reads data from input and transforms it into domain objects that can store information in suitable format (Lists, Sets, etc.)

II. Performs Floyd Warshall algorithm to find shortest path between each two Stations. Generally it is 2D array, but in my case
i tis 3D array 3rd dimension contains list of roads to from and to each station.

III. Prepares and executes steps. Each step contains list of actions that has to be executes. One train can execute more then one
action(list of actions) as long as this list does not require more then 1 time step.

Each train has 5 possible actions:
1. Load - Loads letter from station. Takes 0 time steps.
2. Unload - train unloads letter at station. Takes 0 time steps.
3. Arrives - train arrives at station. Take 0 time steps.
4. Departs - trains departs station. Take 0 time steps.
5. Move - train moves on the rail road. Take 1 time step.

Every time train arrives at station it unloads all letters and performs new search.

Steps are executes until Current Letter Destination for any letter is different then Target Letter Destination.

Each step performs list of actions for each train:
 
1. If train is on Station then it looks for closest letter and chooses it. When letter is chosen by one train it can not be chosen by other.

    1.1. If collection of letter requires 0 time steps (Letter and Train are at the same Station) then train loads letter and chooses 
second letter if letter is in the same direction as already collected one (or more). Then it performs Departure and Move actions. 
If next station is in 1time step move it performs Arrive and Unload Action.

    1.2. If chosen letter is not at the same Station then it performs Depart and Moe actions to Current Letter Destination
    
    1.3. There are no letters then train does not perform any action.

2. If train is on the Road it performs Move action and if it reaches next station in one move then it performs Arrive and Unload Action, as well.

Above process repeats until any Current Letter Destination is the same as Target Letter Destination.

## Summary
Algorithm is not optimal, there is a lot of room for optimisations. Also some code parts are must be refactored and 
better documented. At this point application provides some solution for given mail train system.