Scope
-----

**In Scope:**

- Validating the state of values
- Navigating across class properties (e.g., `ListValidator.length()`)

**Out of Scope:**

- Type conversion (e.g., converting a `String` to an `InetAddress`)
- Validation of nested types (e.g., checking if a `String` contains an email address)

**Reasoning:** Common classes like `Object` or `String` would include numerous type-conversion methods, making
it impractical to divide the functionality into multiple modules.

Library name
------------

* Requirements
    * Con
        * Virtually impossible to find in Google search results due to the keyword's popularity.

Preconditions
-------------

* Naming has to imply action (throw on failure). Ideally imply failing-fast.
    * requireThat()
    * ensureThat()

Assertions
----------

* assertThat() returning a no-op implementation.
    * Pro
        * No overhead. The code gets optimized-away the same as `assert`.
    * Con
        * There is no way to prevent the user from interacting with the validator when assertions are
          disabled, so the implementation has to return something reasonable to avoid exceptions getting
          thrown
          and to result in the code getting optimized-away.
* assertThat(v -> v.requireThat(value, name))
    * Pro
        * No overhead.
        * Easy to understand.
        * Easier to maintain (no need to maintain parallel hierarchies).
    * Con
        * Uses lambdas, so external variables must be final.
* assert assertThat(value, name)
    * Pro
        * No overhead
    * Con
        * Syntax looks a bit clunky
            * Having to repeat the word "assert" twice.
            * Having to return `elseThrow()`.
            * Difficult to do anything that might require a code block.

Multiple failures
-----------------

* and(), or()
    * Pro
        * Makes it easier to aggregate validations
    * Con
        * Readability is not great, though possibly better than aggregating manually.
        * Using lambdas prevents us from accessing non-final variables.

Removing the library from exception stack traces
------------------------------------------------

* Per JMH, the overhead of doing this is low (2.5%).
* There isn't a performance justification for making this configurable.

Constructing exceptions lazily
------------------------------

* Pro
    * Performance improves by 39%.
    * This is useful for web applications that are only interested in error messages, and don't need an actual
      exception.
* Con
    * The absolute performance gains are low (1289 nanoseconds).

Providing separate validators for primitives
--------------------------------------------

* Pro
    * Easier to validate primitives and avoid unnecessary null checks.
    * This is especially true for arrays of primitives.
* Con
    * Per JMH, there is no performance justification for this. The JIT optimizes away any performance
      difference.

Modeling undefined values
-------------------------

* In some cases, it's clear that `null` denotes an undefined value (e.g. primitive values).
* In other cases, there is no way to know. For example, is a Collection `null` or did a user dereference
`Map.keys()` on a `null` value?