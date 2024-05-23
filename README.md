[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.cowwoc.requirements/java/badge.svg)](https://search.maven.org/search?q=g:com.github.cowwoc.requirements)
[![build-status](../../workflows/Build/badge.svg)](../../actions?query=workflow%3ABuild)

# <img src="docs/checklist.svg" width=64 height=64 alt="checklist"> Requirements API
## Fluent API for Design Contracts

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

Designed for discovery using your favorite IDE's auto-complete feature.
The main entry points are:

* [requireThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/java/DefaultJavaValidators.html#requireThat(T,java.lang.String)) for method preconditions.
* [assumeThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/java/DefaultJavaValidators.html#assumeThat(T,java.lang.String)) for class invariants, method postconditions and private methods.
* [checkIfThat(value, name)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/java/DefaultJavaValidators.html#checkIf(T,java.lang.String)) for multiple failures and customized error handling.
* [JavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/java/JavaValidators.html) for custom configurations.

The first three methods use a shared configuration, while `JavaValidators` allows you to create an independent
configuration.

`requireThat()` and `assumeThat()` throw an exception on the first validation failure,
while `checkIf()` collects multiple validation failures before throwing an exception at the end.
`checkIf()` is more flexible than the others, but its syntax is more verbose.

Exceptions that are thrown in response to invalid method arguments (e.g. `isGreaterThan(null, value)`) are
thrown by all validators and cannot be configured. Exceptions that are thrown in response to the value
failing a validation check, e.g. `isGreaterThan(5)` on a value of 0, are thrown by `requireThat()` and
`assumeThat()` but are recorded by `checkIf()` without being thrown. The type of thrown exceptions is
configurable using [ConfigurationUpdater#exceptionTransformer(Function)](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/java/ConfigurationUpdater.html#exceptionTransformer(java.util.function.Function)).

See the [API documentation](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/) for more details.

## Usage Example

```java
import java.util.List;
import java.util.StringJoiner;

import static com.github.cowwoc.requirements.java.DefaultJavaValidators.assumeThat;
import static com.github.cowwoc.requirements.java.DefaultJavaValidators.checkIf;
import static com.github.cowwoc.requirements.java.DefaultJavaValidators.requireThat;

public final class MissionControl
{
  public static void main(String[] args)
  {
    // Method preconditions
    requireThat(args, "args").length().isPositive();
    String message = args[0];
    requireThat(message, "message").startsWith("Houston, we've got a ").endsWith(".");

    String[] words = message.replaceAll("[.,]", "").split("\\s+");
    requireThat(words, "words").length().isEqualTo(5);
    String subject = words[4];

    // Class invariants or method postconditions
    String reply = "What sort of " + subject + "?"; // <-- good reply
//    String reply = "What sort of " + subject + " do you see?"; // <-- bad reply
    assert assumeThat(reply, "reply").length().
        withContext(message, "message").
        isLessThan(message.length(), "message.length()").
        elseThrow();
    System.out.println("Message: " + message);
    System.out.println("Reply  : " + reply);
    System.out.println();

    // Return multiple validation failures at once
    List<String> messages = checkIf(message, "message").isEmpty().
      and(checkIf(subject, "subject").isEqualTo("cupcake")).
      elseGetMessages();
    StringJoiner joiner = new StringJoiner("\n\n");
    for (String failureMessage : messages)
      joiner.add(failureMessage);
    System.out.println("Multiple failures\n-----------------\n" + joiner);
  }
}
```

Potential error messages look like this:

```
java.lang.NullPointerException: "args" may not be null

java.lang.IllegalArgumentException: words.length must contain 5 elements.
words.length: 6
words       : ["Houston", "we've", "got", "a", "bunny", "rabbit"]

java.lang.AssertionError: reply.length() must contain less than message.length() characters.
message.length(): 25
reply.length()  : 28
reply           : "What sort of dog do you see?"
message         : "Houston, we've got a dog."

Multiple failures
-----------------
"message" must be empty.
Actual        : "Houston, we've got a problem."
message.length: 25

"subject" must be equal to "cupcake"
Actual: "problem"
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

## Tips

* Use `assert` with `assumeThat().elseThrow()` for sanity checks. When assertions are disabled, the checks will get removed.
* Use `checkIf().elseGetMessages()` to return failure messages without throwing an exception.
  This is the fastest validation approach, ideal for web services.
* To enhance the clarity of failure messages, you should provide parameter names, even when they are optional.

## Third-party libraries and tools

This library supports the following third-party libraries and tools:

* [guava](docs/Supported_Libraries.md)
* [IntelliJ IDEA](docs/Supported_Tools.md)

## Licenses

* This library is licensed under the [Apache License, Version 2.0](LICENSE)
* See [Third party licenses](LICENSE-3RD-PARTY.md) for the licenses of the dependencies
* Icons made by Flat Icons from www.flaticon.com are licensed by CC 3.0 BY
