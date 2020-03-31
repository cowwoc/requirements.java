# Manual Builds

Building the project using `mvn clean install`.
Generate Javadoc using `mvn install javadoc:aggregate -pl !test,!benchmark,!wiki -e`

# Automated Builds

The following pages explain how to configure build machines inside VMWare for building this project:

* [Ubuntu 16.04 LTS](Jenkins_Configuration_-_Ubuntu_16.04_LTS)
* [MacOS Sierra](Jenkins_Configuration_-_MacOS__10.12_(Sierra))
* [Windows 10](Jenkins_Configuration_-_Windows10)

The project's Jenkins job configuration can be found at https://github.com/cowwoc/requirements.java/tree/master/jenkins