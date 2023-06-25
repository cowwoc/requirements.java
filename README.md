[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.cowwoc.requirements/java/badge.svg)](https://search.maven.org/search?q=g:com.github.cowwoc.requirements)
[![build-status](../../workflows/Build/badge.svg)](../../actions?query=workflow%3ABuild)

# <img src="docs/checklist.svg" width=64 height=64 alt="checklist"> Fluent API for Design Contracts

[![API](https://img.shields.io/badge/api_docs-5B45D5.svg)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/)
[![Changelog](https://img.shields.io/badge/changelog-A345D5.svg)](docs/Changelog.md)
[![js](https://img.shields.io/badge/other%20languages-js-457FD5.svg)](../../../requirements.js)

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

You can learn about the API by using your IDE's auto-complete feature.
The main entry points are:

* [requireThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#requireThat(T,java.lang.String))
* [assumeThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#assumeThat(T,java.lang.String))
* [checkIfThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#checkIf(T,java.lang.String))
* [JavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/JavaValidators.html)

The first three methods use a global configuration, while `JavaValidators` allows you to create a local configuration.

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
java.lang.NullPointerException: args may not be null

java.lang.IllegalArgumentException: args.length() must be positive
args.length(): 0

java.lang.AssertionError: args[0] must be equal to "world"
args[0]: "planet"

Multiple failures
-----------------
args must be empty.
args       : ["world"]
args.length: 1

args[0] must be equal to "planet".
args[0]: "world"
```

## Features

This library offers the following features:

* [Automatic message generation](docs/Features.md#automatic-message-generation) for validation failures
* [Diffs provided whenever possible](docs/Features.md#diffs-provided-whenever-possible) to highlight the differences between expected and actual values
* [Clean stack-traces](docs/Features.md#clean-stack-traces) that remove unnecessary frames
* [Zero overhead when assertions are disabled](docs/Features.md#assertion-support) for better performance
* [Multiple validation failures](docs/Features.md#multiple-validation-failures) that report all the errors at once
* [Nested validations](docs/Features.md#nested-validations) that allow you to validate complex objects
* [String diff](docs/Features.md#string-diff) that shows the differences between two strings
* [Fast... very fast!](docs/Performance.md) compared to other validation libraries

## Getting Started

The best way to learn about the API is using your IDE's auto-complete engine.
The main entry points you should be aware of are:

* [requireThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#requireThat(T,java.lang.String))
* [assumeThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#assumeThat(T,java.lang.String))
* [checkIfThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultJavaValidators.html#checkIf(T,java.lang.String))
* [JavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/JavaValidators.html)

The three static methods share a global configuration.
`JavaValidators` contains a local configuration, which is useful if you want to use different configuration at
once.

See the [API documentation](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/) for more details.

## Performance Tips

## Best practices

* Use `requireThat()` to verify the preconditions of public APIs.
* Use `assert assumeThat()` to verify class invariants, method post-conditions, and the preconditions of
  private methods.
  The JVM will remove these checks if assertions are disabled.
* Use `checkIf()` to return multiple failures at once.
* Use `checkIf().elseGetMessages()` to return failures without throwing an exception.
  This is the best-performing approach, ideal for web services.
* You can omit parameter names, but it is better to include them for clarity of failure messages.

## Third-party libraries and tools

This library supports the following 3rd-party libraries and tools:

* [guava](docs/Supported_Libraries.md)
* [IntelliJ IDEA](docs/Supported_Tools.md)

## Licenses

* This library is licensed under the [Apache License, Version 2.0](LICENSE)
* See [Third party licenses](LICENSE-3RD-PARTY.md) for the licenses of the dependencies
* Icons made by Flat Icons from www.flaticon.com are licensed by CC 3.0 BY