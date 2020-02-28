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
java -jar ./target/BigPayTask.jar < simple_input.txt
```

You can run it with more complex data
```bash
java -jar ./target/BigPayTask.jar < extended_input.txt
```


## Terms

* **Path** - Minimal distance between two stations

* **Connected Roads** - Roads that have common Station

* **Connected Road List** - List Connected Roads where first and second road has common Station, second and third have common Station and etc. 

* **Letter Path** - Path between source and target Letter Station

* **Train Path** - Path between current and target train Station

* **Nearest Letter Path** - Path between train and closest station with undelivered Letters

## Algorithm

