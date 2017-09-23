[![Build Status](https://travis-ci.org/m2ci-msp/jtgt.svg?branch=master)](https://travis-ci.org/m2ci-msp/jtgt)
[![Download](https://api.bintray.com/packages/m2ci-msp/maven/jtgt/images/download.svg)](https://bintray.com/m2ci-msp/maven/jtgt/_latestVersion)
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](http://www.gnu.org/licenses/lgpl-3.0)

# JTGT

JTGT is a porting of the TextGrid Tools python library into java. Original TextGridTools can be find here: https://github.com/hbuschme/TextGridTools

## Adding JTGT to your dependencies

To declare a depency on JTGT you can do it:

- in the `pom.xml` for Maven:
```xml
<repositories>
  <repository>
    <url>https://jcenter.bintray.com</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>org.m2ci.msp</groupId>
    <artifactId>jtgt</artifactId>
    <version>0.5.1</version>
  </dependency>
</dependencies>
```
- in the `build.gradle` for Gradle
```groovy
repositories {
  jcenter()
}

dependencies {
  compile group: 'org.m2ci.msp', name:'jtgt', version: '0.5.1'
}
```
