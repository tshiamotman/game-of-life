build:
	mvn clean
	mvn compile
	clear
	mvn exec:java

release:
	mvn clean
	mvn package

test:
	mvn clean
	mvn test;
