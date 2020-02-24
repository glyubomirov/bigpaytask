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
cd ./bigpayproject/
```

Use the package manager [maven](https://maven.apache.org/) to package build

```bash
mvn package
```

### Run Project
Go to project directory and execute

```bash
java -jar ./target/BigPayTask.jar
```

## Terms

* **Distance** - distance that train does for 1 time step (e.g 1 distance per 1 time step)

* **Letter Path** - Distance between source and target Letter Station

* **Total Letter Path** - Sum of all Letter Paths

* **Average Letter Path** - Total Letter Path divided by number of Letters

* **Train Path** - Distance between current and target train Station

* **Total Letter Capacity** - Sum of all Letter Weights

* **Total Train Capacity** - Sum of all capacity of all Trains

* **Average Train Capacity** - Total Train Path divided by number of Trains

* **Average Train Delivery Capacity** - Total Letter Capacity divided by Total Train Capacity

* **Nearest Letter Path** - Distance between train and closest station with undelivered Letters

* **Total Nearest Letter Path** - Distance between all empty trains and closest station with undelivered letters

## Algorithm

