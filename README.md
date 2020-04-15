[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.cowwoc.requirements/java/badge.svg)](https://search.maven.org/search?q=g:com.github.cowwoc.requirements)
[![build-status](../../workflows/Build/badge.svg)](../../actions?query=workflow%3ABuild)
[![API](https://img.shields.io/badge/api_docs-5B45D5.svg)](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/)
[![Changelog](https://img.shields.io/badge/changelog-A345D5.svg)](wiki/Changelog.md)

![java](https://img.shields.io/badge/languages-java-black.svg)
[![js](https://img.shields.io/badge/js-457FD5.svg)](../../../requirements.js)

# <img src="wiki/checklist.svg" width=64 height=64 alt="checklist"> Requirements: Fluent Design by Contract for Java APIs

A [fluent API](https://en.wikipedia.org/wiki/Fluent_Interface.md) for enforcing 
[design contracts](https://en.wikipedia.org/wiki/Design_by_contract) with 
[automatic message generation](wiki/Features.md#automatic-message-generation).

```java
import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;

public final class Player
{
  public Player(String name, int age)
  {
    requireThat(name, "name").isNotEmpty();
    requireThat(age, "age").isBetween(18, 30);
  }
}
```

Exception messages will look like this:

```java
java.lang.NullPointerException: name may not be null
[...]
java.lang.IllegalArgumentException: name may not be empty
[...]
java.lang.IllegalArgumentException: age must be in range [18, 30).
Actual: 15
```

For the **Javadoc**, click **api docs** at the top of this page.

## Features

* [Automatic message generation](wiki/Features.md#automatic-message-generation)
* [Diffs provided whenever possible](wiki/Features.md#diffs-provided-whenever-possible)
* [Clean stack-traces](wiki/Features.md#clean-stack-traces)
* [Assertion support](wiki/Features.md#assertion-support)
* [Multiple validation errors](wiki/Features.md#multiple-validation-errors)
* [Grouping nested requirements](wiki/Features.md#grouping-nested-requirements)
* [String diff](wiki/Features.md#string-diff)
* [Fast... very fast!](wiki/Performance.md)

## Getting Started

Follow these 4 steps to get started:

1. Update pom.xml.
2. Rebuild your project.
3. Statically import `DefaultRequirements.requireThat()`.
4. Use `requireThat()` to verify design contracts.

### Step 1: Update pom.xml

```xml

<project>
  ...
  <properties>
    <requirements.version>6.0.0</requirements.version>
  </properties>
  <build>
    <plugins>
      <plugin>
        <groupId>com.github.cowwoc.requirements</groupId>
        <artifactId>maven_plugin</artifactId>
        <version>${requirements.version}</version>
        <executions>
          <execution>
            <goals>
              <!-- Generates the DefaultRequirements, Requirements endpoints -->
              <goal>generate-api</goal>
              <!-- Adds support for colored diffs (optional) -->
              <goal>unpack</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
  ...
  <dependencies>
    <dependency>
      <groupId>com.github.cowwoc.requirements</groupId>
      <artifactId>java</artifactId>
      <version>${requirements.version}</version>
    </dependency>
  <dependencies>
  ...
</project>
```

### Step 2: Rebuild your project

The build process will generate `Requirements` and `DefaultRequirements` classes. These classes are
 dynamically generated at build-time because their functionality varies depending on which
 [modules](wiki/Supported_Libraries.md) you have enabled.

### Step 3: Statically import `DefaultRequirements.requireThat()`

Statically import methods from `DefaultRequirements` to improve the readability of your code:

```java
import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;
```

### Step 4: Use `requireThat()` in your code to verify design contracts

```java
public final class Player
{
  public Player(String name, int age)
  {
    requireThat(name, "name").isNotEmpty();
    requireThat(age, "age").isBetween(18, 30);
  }
}
```

### Learn more

The best way to learn about the API is using your IDE's auto-complete engine. There are six entry points you can navigate from:

* [requireThat(value, name)](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultRequirements.html#requireThat(T,java.lang.String))
* [assertThat(value, name)](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultRequirements.html#assertThat(T,java.lang.String))
* [validateThat(value, name)](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultRequirements.html#validateThat(T,java.lang.String))
* [Requirements](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/Requirements.html)
* [GlobalRequirements](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements/java/GlobalRequirements.html)
* [ThreadRequirements](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements/java/ThreadRequirements.html)

The first three methods are designed to be statically imported from `DefaultRequirements`.

## Best practices

* Use `requireThat()` for public APIs to give users superior feedback when something goes wrong.
* Use `assertThat()` for private APIs and to verify code assumptions. This will result in excellent performance when assertions are disabled. Have your cake and eat it too!
* Favor `assertThat()` over `assert`. It has a slightly higher overhead, but most classes do not catch `AssertionError` so such failures do not get caught or logged.

## 3rd-party libraries and tools

Enhanced support is available for the following 3rd-party libraries and tools:

* [guava](wiki/Supported_Libraries.md)
* [IntelliJ IDEA](wiki/Supported_Tools.md)

## License

Code licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0

Icons made by Flat Icons from www.flaticon.com is licensed by CC 3.0 BY
