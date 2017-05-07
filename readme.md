# Williams Sonoma Code Challenge

This simple project creates an algrithom used to consolidate input zip code ranges. To simplify the project,
the build process does not contain following:
* the check code style
* the code coverage (e.g., jacoco)
* integration tests

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

```
java 8
apache maven 3.x 

```

Note if runing the application in IDE, the IDE should install below plug-in
```
lombok plugin
```
### Build the application

Run following command to build the application

```
mvn clean install
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Following command to run any test
```
java -jar target/codechallenge-1.0.jar [00123,00124] [12345,12348]
java -jar target/codechallenge-1.0.jar [94133,94133] [94200,94299] [94226,94399]
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Michael Sun** - *Initial work* - [Git Hub Repository](https://github.com/mikessun/william-sonoma)
