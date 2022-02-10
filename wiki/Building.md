# Manual Builds

Build the project using `mvn clean install`.

Generate the Javadoc using `mvn install javadoc:aggregate -pl !test,!benchmark,!wiki -e`.

# Automated Builds

The following pages explain how to configure build machines inside VMWare for building this project:

* [Ubuntu 20.04.3 LTS](Jenkins_Configuration_-_Ubuntu_20.04.3_LTS.md)
* [MacOS Catalina](Jenkins_Configuration_-_MacOS_10.15_(Catalina).md)
* [Windows 10](Jenkins_Configuration_-_Windows10.md)

The project's Jenkins job configuration can be found at
https://github.com/cowwoc/requirements.java/tree/master/jenkins