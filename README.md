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

* **Distance** - time steps e.g. 1 time step = 1 distance

* **Letter Path** - Distance between source and target letter station

* **Total Letter Path** - sum of all Letter Paths for all Letters

* **Train Path** - Distance between current and target train station

* **Nearest Letter Path** - Distance between empty train and closest station with undelivered letters

* **Total Nearest Letter Path** - Distance between all empty trains and closest station with undelivered letters

## Algorithm

