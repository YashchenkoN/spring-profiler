# Spring Profiler

Spring Profiler is a small utility to benchmark of methods invocation time.
It provides possibility to measure invocation time without re-deploying (on the fly) if it set's already!
You can enable/disable benchmark using VirtualVM!

### Version
0.0.1

### Tech

To measuring time of invocation of your method you need to do a few easy steps:

* Add @Profiling annotation to your class
* Re-deploy your application
* Run Java Profiler such as VirtualVM and go to the tab of MBeans (you need to install MBeans plugin before).
* You can see profiler option and "enabled" field
* Just set field value to true if you wan't to see method's time or false if not.

### Installation

You need to add a dependency in your pom.xml

```xml
<repositories>
    <repository>
        <id>spring-profiler</id>
        <url>https://raw.github.com/yashchenkon/spring-profiler/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>tech.yashchenkon</groupId>
        <artifactId>profiling</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```