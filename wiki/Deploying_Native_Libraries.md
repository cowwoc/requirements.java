Some features (such as [colored diffs](Colored_Diff.md)) require the use of a native library. To deploy it:

1. Unpack the [native](https://search.maven.org/search?q=g:com.github.cowwoc%20AND%20a:requirements.natives) library for your platform.
2. Add the enclosing directory to `-Djava.library.path` at runtime, or to `PATH` under Windows, `LD_LIBRARY_PATH` under Linux, and `DYLD_FALLBACK_LIBRARY_PATH` under Mac.

The following code snippet unpacks the native libraries under Maven:

```xml
<build>
  <plugins>
    <plugin>
      <groupId>com.github.cowwoc.requirements</groupId>
      <artifactId>maven_plugin</artifactId>
      <version>${requirements.version}</version>
      <executions>
      <execution>
          <goals>
          <goal>unpack</goal>
          </goals>
      </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

If you are using [Surefire](http://maven.apache.org/surefire/maven-surefire-plugin/) then you will need to add `java.library.path` to your configuration:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>${surefire.version}</version>
  <configuration>
    <argLine>-Djava.library.path=${project.build.directory}</argLine>
  </configuration>
</plugin>
```

If you are using [exec:java](http://www.mojohaus.org/exec-maven-plugin/) or Surefire with [forkCount = 0](http://maven.apache.org/surefire/maven-surefire-plugin/examples/fork-options-and-parallel-execution.html) then you will need to set `java.library.path` using the [MAVEN_OPTS environment variable](https://maven.apache.org/configure.html).

### I don't want to use native libraries

If the library cannot be loaded, the API will log a DEBUG-level warning and continue operating without it. To suppress the warning, set the logger level of `com.github.cowwoc.requirements.natives.internal.terminal.NativeTerminal` to a level higher than `DEBUG`.

If you are trying to suppress this warning for a Maven plugin, setting the environment variable `MAVEN_OPTS` to `-Dorg.slf4j.simpleLogger.log.com.github.cowwoc.requirements.natives.internal.terminal.NativeTerminal=INFO` will do the trick. See https://maven.apache.org/maven-logging.html for a more detailed explanation.