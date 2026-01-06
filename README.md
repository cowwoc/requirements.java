# <img src="docs/logo.svg" width=64 height=64 alt="checklist"> Requirements API
[![build-status](../../workflows/Build/badge.svg)](../../actions?query=workflow%3ABuild)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.cowwoc.requirements/requirements/badge.svg)](https://central.sonatype.com/search?q=g:io.github.cowwoc.requirements)
<br>
[![API](https://img.shields.io/badge/api_docs-5B45D5.svg)](https://cowwoc.github.io/requirements.java/13.0/)
[![Changelog](https://img.shields.io/badge/changelog-A345D5.svg)](docs/changelog.md)
[![javascript, typescript](https://img.shields.io/badge/other%20languages-javascript,%20typescript-457FD5.svg)](../../../requirements.js)

A [fluent API](https://en.wikipedia.org/wiki/Fluent_interface) for
enforcing [design contracts](https://en.wikipedia.org/docs/Design_by_contract) with
[automatic message generation](docs/features.md#automatic-message-generation):

✔️ Easy to use<br>
✔️ Fast<br>
✔️ Production-ready<br>

To get started, add this Maven dependency:

```xml

<dependency>
  <groupId>io.github.cowwoc.requirements</groupId>
  <artifactId>requirements-java</artifactId>
  <version>13.0</version>
</dependency>
```

## Usage Example

```java
import java.util.List;
import java.util.StringJoiner;

import static io.github.cowwoc.requirements13.java.DefaultJavaValidators.checkIf;
import static io.github.cowwoc.requirements13.java.DefaultJavaValidators.requireThat;
import static io.github.cowwoc.requirements13.java.DefaultJavaValidators.that;

public final class Cake
{
  private byte bitesTaken = 0;
  private int piecesLeft;

  public Cake(int piecesLeft)
  {
    requireThat(piecesLeft, "piecesLeft").isPositive();
    this.piecesLeft = piecesLeft;
  }

  public int eat()
  {
    ++bitesTaken;
    assert that(bitesTaken, "bitesTaken").isNotNegative().elseThrow();

    piecesLeft -= ThreadLocalRandom.current().nextInt(5);

    assert that(piecesLeft, "piecesLeft").isNotNegative().elseThrow();
    return piecesLeft;
  }

  public List<String> getFailures()
  {
    return checkIf(bitesTaken, "bitesTaken").isNotNegative().
      and(checkIf(piecesLeft, "piecesLeft").isGreaterThan(3)).
      elseGetFailures();
  }
}
```

If you violate a **precondition**:

```java
Cake cake = new Cake(-1000);
```

You'll get:

```
java.lang.IllegalArgumentException: "piecesLeft" must be positive.
actual: -1000
```

If you violate a **class invariant**:

```java
Cake cake = new Cake(1_000_000);
while (true)
  cake.eat();
```

You'll get:

```
java.lang.AssertionError: "bitesTaken" may not be negative.
actual: -128
```

If you violate a **postcondition**:

```java
Cake cake = new Cake(100);
while (true)
  cake.eat();
```

You'll get:

```
java.lang.AssertionError: "piecesLeft" may not be negative.
actual: -4
```

If you violate **multiple** conditions at once:

```java
Cake cake = new Cake(1);
cake.bitesTaken = -1;
cake.piecesLeft = 2;
StringJoiner failures = new StringJoiner("\n\n");
for (String failure : cake.getFailures())
    failures.add(failure);
System.out.println(failures);
```

You'll get:

```
"bitesTaken" may not be negative.
actual: -1

"piecesLeft" must be greater than 3.
actual: 2
```

## Features

This library offers the following features:

* [Automatic message generation](docs/features.md#automatic-message-generation) for validation failures
* [Diffs provided whenever possible](docs/features.md#diffs-provided-whenever-possible) to highlight the
  differences between expected and actual values
* [Clean stack-traces](docs/features.md#clean-stack-traces) that remove unnecessary frames
* [Zero overhead when assertions are disabled](docs/features.md#assertion-support) for better performance
* [Multiple validation failures](docs/features.md#multiple-validation-failures) that report all the errors at
  once
* [Nested validations](docs/features.md#nested-validations) that allow you to validate complex objects
* [String diff](docs/features.md#string-diff) that shows the differences between two strings
* [Performant and robust](docs/performance.md)

## Entry Points

Designed for discovery using your favorite IDE's auto-complete feature.
The main entry points are:

* [requireThat(value, name)](https://cowwoc.github.io/requirements.java/13.0/io.github.cowwoc.requirements.java/com/github/cowwoc/requirements13/java/DefaultJavaValidators.html#requireThat(T,java.lang.String))
  for method preconditions.
* [that(value, name)](https://cowwoc.github.io/requirements.java/13.0/io.github.cowwoc.requirements.java/com/github/cowwoc/requirements13/java/DefaultJavaValidators.html#that(T,java.lang.String))
  for [class invariants, method postconditions and private methods](docs/features.md#assertion-support).
* [checkIf(value, name)](https://cowwoc.github.io/requirements.java/13.0/io.github.cowwoc.requirements.java/com/github/cowwoc/requirements13/java/DefaultJavaValidators.html#checkIf(T,java.lang.String))
  for multiple failures and customized error handling.

See the [API documentation](https://cowwoc.github.io/requirements.java/13.0/) for more details.

## Best practices

* Use `checkIf().elseGetFailures()` to return failure messages without throwing an exception.
  This is the fastest validation approach, ideal for web services.
* To enhance the clarity of failure messages, you should provide parameter names, even when they are optional.
  In other words, favor `assert that(value, name)` over `assert that(value)`.

## Third-party libraries and tools

This library supports the following third-party libraries and tools:

* [guava](docs/supported_libraries.md)
* [IntelliJ IDEA](docs/supported_tools.md)

## Licenses

* This library is licensed under the [Apache License, Version 2.0](LICENSE)
* See [Third party licenses](LICENSE-3RD-PARTY.md) for the licenses of the dependencies
* Icons made by Flat Icons from www.flaticon.com are licensed by CC 3.0 BY
