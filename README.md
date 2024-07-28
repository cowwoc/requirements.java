[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.cowwoc.requirements/java/badge.svg)](https://search.maven.org/search?q=g:com.github.cowwoc.requirements)
[![build-status](../../workflows/Build/badge.svg)](../../actions?query=workflow%3ABuild)

# <img src="docs/checklist.svg" width=64 height=64 alt="checklist"> Requirements API

[![API](https://img.shields.io/badge/api_docs-5B45D5.svg)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/)
[![Changelog](https://img.shields.io/badge/changelog-A345D5.svg)](docs/Changelog.md)
[![javascript, typescript](https://img.shields.io/badge/other%20languages-javascript,%20typescript-457FD5.svg)](../../../requirements.js)

A [fluent API](https://en.m.wikipedia.org/docs/Fluent_interface) for enforcing
[design contracts](https://en.wikipedia.org/docs/Design_by_contract) with
[automatic message generation](docs/Features.md#automatic-message-generation):

✔️ Easy to use  
✔️ Fast  
✔️ Production-ready

To get started, add this Maven dependency:

```xml

<dependency>
  <groupId>com.github.cowwoc.requirements</groupId>
  <artifactId>java</artifactId>
  <version>9.0.0</version>
</dependency>
```

## Usage Example

```java
import java.util.List;
import java.util.StringJoiner;

import static com.github.cowwoc.requirements.java.DefaultJavaValidators.assumeThat;
import static com.github.cowwoc.requirements.java.DefaultJavaValidators.checkIf;
import static com.github.cowwoc.requirements.java.DefaultJavaValidators.requireThat;

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
    assert assumeThat(bitesTaken, "bitesTaken").isNotNegative().elseThrow();

    piecesLeft -= ThreadLocalRandom.current().nextInt(5);

    assert assumeThat(piecesLeft, "piecesLeft").isNotNegative().elseThrow();
    return piecesLeft;
  }

  public List<String> getFailures()
  {
    return checkIf(bitesTaken, "bitesTaken").isNotNegative().
      and(checkIf(piecesLeft, "piecesLeft").isGreaterThan(3)).
      elseGetMessages();
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

* [Automatic message generation](docs/Features.md#automatic-message-generation) for validation failures
* [Diffs provided whenever possible](docs/Features.md#diffs-provided-whenever-possible) to highlight the
  differences between expected and actual values
* [Clean stack-traces](docs/Features.md#clean-stack-traces) that remove unnecessary frames
* [Zero overhead when assertions are disabled](docs/Features.md#assertion-support) for better performance
* [Multiple validation failures](docs/Features.md#multiple-validation-failures) that report all the errors at
  once
* [Nested validations](docs/Features.md#nested-validations) that allow you to validate complex objects
* [String diff](docs/Features.md#string-diff) that shows the differences between two strings
* [Performant and robust](docs/Performance.md)

## Entry Points

Designed for discovery using your favorite IDE's auto-complete feature.
The main entry points are:

* [requireThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements/java/DefaultJavaValidators.html#requireThat(T,java.lang.String)) for method preconditions.
* [assumeThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements/java/DefaultJavaValidators.html#assumeThat(T,java.lang.String)) for class invariants, method postconditions and private methods.
* [checkIfThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements/java/DefaultJavaValidators.html#checkIf(T,java.lang.String)) for multiple failures and customized error handling.
* [JavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements/java/JavaValidators.html) for custom configurations.

The first three methods use a shared configuration, while `JavaValidators` allows you to create an independent
configuration.

* `requireThat()` and `assumeThat()` throw an exception on the first validation failure.
* `checkIf()` returns multiple validation failures at once. It is more flexible than the others, but its syntax
is more verbose.

Thrown exceptions may be configured using [ConfigurationUpdater.exceptionTransformer(Function)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements/java/ConfigurationUpdater.html#exceptionTransformer(java.util.function.Function)).

See the [API documentation](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/) for more details.

## Tips

* Use `assert` with `assumeThat().elseThrow()` for sanity checks. When assertions are disabled, the checks will get removed.
* Use `checkIf().elseGetMessages()` to return failure messages without throwing an exception.
  This is the fastest validation approach, ideal for web services.
* To enhance the clarity of failure messages, you should provide parameter names, even when they are optional. 
  In other words, favor `assumeThat(value, name)` to `assumeThat(value)`.

## Third-party libraries and tools

This library supports the following third-party libraries and tools:

* [guava](docs/Supported_Libraries.md)
* [IntelliJ IDEA](docs/Supported_Tools.md)

## Licenses

* This library is licensed under the [Apache License, Version 2.0](LICENSE)
* See [Third party licenses](LICENSE-3RD-PARTY.md) for the licenses of the dependencies
* Icons made by Flat Icons from www.flaticon.com are licensed by CC 3.0 BY
