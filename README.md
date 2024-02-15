[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.cowwoc.requirements/java/badge.svg)](https://search.maven.org/search?q=g:com.github.cowwoc.requirements)
[![build-status](../../workflows/Build/badge.svg)](../../actions?query=workflow%3ABuild)

# <img src="docs/checklist.svg" width=64 height=64 alt="checklist"> Requirements API
## Fluent API for Design Contracts

[![API](https://img.shields.io/badge/api_docs-5B45D5.svg)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/)
[![Changelog](https://img.shields.io/badge/changelog-A345D5.svg)](docs/Changelog.md)
[![javascript, typescript](https://img.shields.io/badge/other%20languages-javascript,%20typescript-457FD5.svg)](../../../requirements.js)

A [fluent API](https://en.m.wikipedia.org/docs/Fluent_interface) for enforcing
[design contracts](https://en.wikipedia.org/docs/Design_by_contract) with
[automatic message generation](wiki/Features.md#automatic-message-generation):

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

`Designed for discovery using your favorite IDE's auto-complete feature.
The main entry points are:

* [requireThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#requireThat(T,java.lang.String)) for preconditions
* [assumeThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#assumeThat(T,java.lang.String)) for postconditions and class invariants
* [checkIfThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#checkIf(T,java.lang.String)) for multiple failures and everything else
* [JavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/JavaValidators.html) for custom configurations

The first three methods use a shared configuration, while `JavaValidators` allows you to create an independent
configuration.

See the [API documentation](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/) for more details.

## Usage Example

```java
import static com.github.cowwoc.requirements.java.DefaultJavaValidators.assumeThat;
import static com.github.cowwoc.requirements.java.DefaultJavaValidators.checkIf;
import static com.github.cowwoc.requirements.java.DefaultJavaValidators.requireThat;

public final class HelloWorldTest
{
  public static void main(String[] args)
  {
    // Preconditions
    requireThat(args, "args").length().isPositive();

    // Class invariants or method postconditions
    assert assumeThat("args[0]", args[0]).isEqualTo("world").elseThrow();

    // Return multiple validation failures at once
    List<String> messages = checkIf(args, "args").isEmpty().
      and(checkIf("args[0]", args[0]).isEqualTo("planet")).
      elseGetMessages();
    StringJoiner joiner = new StringJoiner("\n\n");
    for (String message : messages)
      joiner.add(message);
    System.out.println("Multiple failures\n" + joiner.toString());
  }
}
```

Failure messages look like this:

```
java.lang.NullPointerException: "args" may not be null

java.lang.IllegalArgumentException: args.length() must be positive
Actual: 0

java.lang.AssertionError: args[0] must be equal to "world"
Actual: "planet"

Multiple failures
-----------------
"args" must be empty.
args       : ["world"]
args.length: 1

args[0] must be equal to "planet".
Actual: "world"
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

## Getting Started

The best way to learn about the API is using your IDE's auto-complete engine.
The main entry points you should be aware of are:

* [requireThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#requireThat(T,java.lang.String))
* [assumeThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#assumeThat(T,java.lang.String))
* [checkIfThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#checkIf(T,java.lang.String))

The three static methods share the same configuration.
To create an independent configuration, use [JavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/JavaValidators.html).

See the [API documentation](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/) for more details.

## Best practices

* Use `requireThat()` to verify the preconditions of public APIs.
* Use `assert assumeThat()` to verify class invariants, method post-conditions, and the preconditions of
  private methods.
  The JVM will remove these checks if assertions are disabled.
* Use `checkIf()` to return multiple failures at once.
* Use `checkIf().elseGetMessages()` to return failures without throwing an exception.
  This is the best-performing approach, ideal for web services.
* To enhance the clarity of failure messages, you should provide parameter names, even though they are
  optional.

## Third-party libraries and tools

This library supports the following third-party libraries and tools:

* [guava](docs/Supported_Libraries.md)
* [IntelliJ IDEA](docs/Supported_Tools.md)

## Licenses

* This library is licensed under the [Apache License, Version 2.0](LICENSE)
* See [Third party licenses](LICENSE-3RD-PARTY.md) for the licenses of the dependencies
* Icons made by Flat Icons from www.flaticon.com are licensed by CC 3.0 BY