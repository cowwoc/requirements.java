Minor updates involving cosmetic changes have been omitted from this list.

See https://github.com/cowwoc/requirements.java/commits/main for a full list.

## Version 10.9 - 2025/01/13

* Bugfix: `PathValidator.contains(Path)` returned false when the first path was the current directory and the
  second path was a subdirectory.

## Version 10.8 - 2025/01/13

* When returning multiple failures, eliminate any duplicate messages.

## Version 10.7 - 2025/01/03

* Bugfix: If the string representation of the expected and actual values was equal, the class names were not
  being compared.
* Compare the types of the expected and actual values even if their String representation spans multiple
  lines.

## Version 10.6 - 2025/01/01

* Added `StringValidator.matches(Pattern)`.

## Version 10.5 - 2025/01/01

* Added `PathValidator.contains(Path)`.

## Version 10.2 - 2024/12/07

* Escape linefeed characters in diffs.

## Version 10.1 - 2024/12/06

* Improvements
    * When referencing to a variable number of elements, always use the plural form of a type (e.g.
      `actual must contain X entries` even if `X` is one).
    * Added special handling for `Collection.contains(Object)` throwing `NullPointerException` because the
      collection does not permit null elements.

## Version 10.0 - 2024/10/31

* Breaking changes:
    * The module and package names now contain the major version number of the library,
      per: https://www.reddit.com/r/java/comments/1dxbag2/comment/lc63gz1/
    * Parameter names may not contain whitespace (previously only leading or trailing whitespace was
      disallowed).
    * Removed ability to configure validators in order to simplify API.
    * Reduced the library scope by removing all type-conversion methods, such as `asString()` or `asList()`.
    * Removed `UrlValidator`.
    * Replaced `assumeThat()` with `assert that()`.
    * Moved `com.github.cowwoc.requirements10.java.terminal.TerminalEncoding`
      to `com.github.cowwoc.requirements10.java.TerminalEncoding`.
    * Added `GenericType` to represent types with type parameters. `ClassValidator` was replaced
      by `GenericTypeValidator`.
    * Replaced `Validator.elseGetMessages()` with `Validator.getFailures().getMessages()`.
    * Replaced `Validator.elseGetException()` with `Validator.getFailures().getException()`.
    * Renamed `Validator.isSameReferenceAs()` to `isReferenceEqualTo()`, `Validator.isNotSameReferenceAs()` to
      `isReferenceNotEqualTo()`.
    * Removed `CollectionValidator.containsSameNullity()` yet again due to poor usability.
* New features:
    * Added `GenericTypeValidator.isPrimitive()`.
    * Added `validationFailed()` and `getValueOrDefault()` to all validators.
    * Added `StringValidator.doesNotContainWhitespace()`.
* Improvements
    * If `checkIf()` cannot run validations due to a null value, the expected conditions are still reported.

## Version 9.0.0 - 2024/05/23

* Breaking changes:
    1. The library now requires JDK 21.
    2. Added a `jackson` module to validate `JsonNode`.
    3. Removed the Maven plugin and code generator.
        * The code generator was causing a "split package" issue because it was generating code into the same
          package in all dependent libraries.
        * This caused a “split package” error since only one module is allowed to export a package.
    4. Removed the use of native binaries. Colored DIFFs are still supported, but Windows requires the use of
       Windows Terminal (free download in Windows 10, built-in feature in Windows 11).
    5. Replaced `validateThat()` with `checkIf()`.
    6. Replaced `assertThat()` with `assert assumeThat().elseThrow()`.
    7. Added support for the built-in `assert` mechanism.
        - Asserts can be used with any type of validator, but are typically used
          with `assumeThat().orElseThrow()` and `checkIf().orElseThrow()`.
    8. Use consistent parameter ordering across the entire API: `(value, name)`
        - Adding contextual information now looks like this: `requireThat().withContext(value, name)`
    9. Added `Validator.apply(Consumer)` to nest validations, and `Validator.and(Validator)` to combine
       validation results.
    10. Renamed `Validator.getActual()` to `getValue()`.
    11. Replaced `isBetweenClosed(min, max)` with `isBetween(min, true, max, true)`.
    12. Renamed `StringValidator.isInteger()` to `asPrimitiveInteger()` and `isCharacter()`
        to `asPrimitiveCharacter()`.
    13. Improved performance by reducing memory allocation and the frequency of GC runs.
    14. Added support for primitive types to avoid boxing when possible.
    15. Dropped the `isOneOf()` and `isNotOneOf()` functionality yet again. I haven't figured out a good
        design for this yet.
    16. Added `ObjectValidator.isX()` methods to downcast to known types.
    17. Replaced thread context with `Validators.getContext()`, `withContext()`, `removeContext()`.
    18. Added `StringValidator.matches(regex)`.
* Bugfixes:
    * `StringValidator/Verifier.asShort()`, `asInteger()` and `asLong()` were not handling the case where a
      string could not be converted to a number.
    * Validators were only returning the first failure.

## Version 8.0.10 - 2023/06/24

* Improvements:
    * Added `CollectionValidator/Verifier.doesNotContainMixedNulls()`.

## Version 8.0.9 - 2023/06/08

* Improvements:
    * Added `StringValidator/Verifier.asShort()`, `asInteger()`, `asLong()` and `asBigDecimal()`.
    * Removed unused imports in the generated API files.

## Version 8.0.8 - 2023/06/08

* Skipped due to deployment error

## Version 8.0.7 - 2023/04/18

* Bugfixes
    * `StringValidator.length(l -> l.isBetweenClosed(min, max))` was not throwing an exception on failure.

## Version 8.0.6 - 2023/04/18

* No changes (deployed by mistake)

## Version 8.0.5 - 2022/10/04

* Bugfixes
    * `Configuration.toString(Map)` did not print out the correct values.
    * The `lock` field of optimized exceptions is now `transient`.

## Version 8.0.4 - 2022/10/04

* Improvements:
    * `cleanStackTrace` no longer strip public library methods that invoke user code (e.g. `assertThat`).

## Version 8.0.3 - 2022/09/28

* Improvements:
    * Added `StringValidator/Verifier.strip()` and `isStripped()`.
    * Added `@CheckReturnValue` to `ObjectValidator/Verifier.getActual()`.

## Version 8.0.2 - 2022/09/22

* Improvements:
    * Replaced use of `synchronized` keyword with `StampedLock` to improve compatibility with virtual threads.

## Version 8.0.1 - 2022/09/18

* Improvements:
    * Instantiate exceptions 3% faster by using `LambdaMetafactory` instead of `MethodHandle.invokeExact()`.
    * Updated list of 3rd-party licenses.
    * Updated look of documentation on Github (minor formatting changes).

## Version 8.0.0 - 2022/09/18

* Breaking changes:
    * Replaced `Requirements.assertThat(actual, name)`
      by `assertThat(requirements -> requirements.requireThat(actual, name))`
      and `assertThatAndReturn(requirements -> requirements.requireThat(actual, name))`. The latter is used
      for assertions with a return value. This change improves the runtime performance of `assertThat()` and
      reduces code duplication across the library.
    * Replaced `ThreadRequirements.getContextMessage()` by `DefaultRequirements.getContextMessage()`.
    * Removed `Object.isActualAvailable()` in favor of `Requirements.assertionsAreEnabled()`.
* Bugfixes:
    * `DefaultRequirements` was not thread-safe.
* Improvements
    * Added `Configuration`-related methods (except for `withoutContext()` and `withoutAnyContext()` which
      wouldn't have any effect) to `DefaultRequirements`.

## Version 7.0.4 - 2022/08/22

* Improvements
    * Updated dependencies

## Version 7.0.3 - 2022/07/17

* Improvements
    * Added support for `aarch64` architecture. Native libraries are not available at this time.

## Version 7.0.2 - 2022/07/13

* Improvements
    * `AbstractObjectVerifier.validator` is now final.
    * Optimized common case where `validationResult()` returns `this`.

## Version 7.0.1 - 2022/07/11

* Bugfix: Fixed regression that prevented ThreadRequirements from being thread-safe.

## Version 7.0.0 - 2022/07/05

* Breaking changes
    * Removed thread-safety from most classes for performance-reasons. It seems like single-threaded usage is
      far more common.
    * Removed deprecated methods `Configuration.putContext()`, `Configuration.removeContext()` that were
      replaced by `Configuration.withContext()` and `Configuration.withoutContext()`, respectively.
    * Removed deprecated methods `ThreadRequirements.putContext()`, `ThreadRequirements.removeContext()`,
      `ThreadRequirements.removeAllContext()` that were replaced by `ThreadRequirements.withContext()`,
      `ThreadRequirements.withoutContext()` and `ThreadRequirements.withoutAnyContext()`, respectively.
* Improvements
    * Added default string converter for `Set`, `Map` and `Throwable`.
    * `ArrayValidator`: avoid converting array into a list until absolutely necessary.
    * Added `Configuration.getContextMessage()` and `ThreadRequirements.getContextMessage()`.
    * Added `Configuration.copy()`.
    * Added `Configuration.withoutAnyContext()`.

## Version 6.1.0 - 2020/02/10

* New Features
    * Added `StringValidator`/`Verifier.asBoolean()`.
    * Added `StringValidator/StringVerifier.isBlank()`, `isNotBlank()`.
    * Added `ArrayValidator/ArrayVerifier.asList()`.
    * Added `ArrayValidator/ArrayVerifier.isSorted(Comparator)`.
    * Added `ListValidator/ListVerifier.isSorted(Comparator)`.
* Improvements
    * Diffs
        * Split words on colons and semicolons.
        * Improved readability by reducing the number of diff components per word.
        * Improved readability by avoiding short diff components in short words, even if the overall number of
          components is low.
    * Improved readability of Configuration
        * `putContext()` replaced by `withContext()`
        * `removeContext()` replaced by `withoutContext()`
    * Improved readability of ThreadConfiguration
        * `putContext()` replaced by `withContext()`
        * `removeContext()` replaced by `withoutContext()`
        * `removeAllContext()` replaced by `withoutAnyContext()`
    * Annotated methods whose return value should not be ignored with
      `com.github.cowwoc.requirements.annotation.CheckReturnValue`
    * `MainConfiguration.toString()` now handles multidimensional arrays.
* Bugfixes
    * `ListValidator.isEqualTo()` was throwing a `ClassCastExeception` when `actual` was being compared
      to a non-`List`.
* Deprecations
    * Warn that `StringVerifier/Validator.asString()` are no-op operations.
* Breaking changes
    * Increased minimum JDK version from 11 to 17.
    * Renamed `com.github.cowwoc.requirements.annotations` to `com.github.cowwoc.requirements.annotation`. You
      shouldn't need to change your source code, but the project will need to be rebuilt.

## Version 6.0.4 - 2020/04/23

* Bugfixes
    * When list elements were unequal, but had the same string value, their types were not being compared.

## Version 6.0.3 - 2020/04/19

* Improvements
    * If a failure message is longer than the terminal width, push the expected value from the failure message
      to the exception context. This helps failure messages remain readable in the face of long values.
    * Added `GlobalConfiguration.getTerminalWidth()`, `withDefaultTerminalWidth()`, `withTerminalWidth()`.

## Version 6.0.1 - 2020/04/15

* Improvements
    * Improved diff output for lists, multiline strings.

## Version 6.0.0 - 2020/03/30

* Moved project from https://bitbucket.org/cowwoc/requirements.java/ to
  https://github.com/cowwoc/requirements.java/
* Breaking changes
    * Changed groupId from `org.bitbucket.cowwoc.requirements` to `com.github.cowwoc.requirements`.

## Version 5.2.2 - 2019/09/18

* Bugfixes
    * `cleanStackTrace()` was not being enforced if `Throwable.printStackTrace(PrintWriter)` was invoked.

## Version 5.2.1 - 2019/08/31

* Bugfixes
    * `ValidationFailureImpl.createMessageWithContext()` was throwing `NullPointerException` if the
      context contained an empty line.

## Version 5.2.0 - 2019/08/11

* Breaking changes
    * Removed `PrimitiveArrayVerifier`.
* Bugfixes
    * `PrimitiveArrayVerifier.isNull()`, `isNotNull()` was incorrectly showing up as deprecated.

## Version 5.1.2 - 2019/07/29

* New features
    * Added `ClassValidator.isSubtypeOf()`.
* Improvements
    * `Configuration.putContext()` now retains the order in which entries were inserted into the map. Note
      that insertion order is not affected if a key is re-inserted into the map.
    * `ValidationFailure.getMessage()` now includes the failure context.
    * `asUri()`, `asUrl()`, `asInetAddress()` now mention the actual value's expected type when it is null.
* Bug fixes
    * Multi-line diffs were sometimes incrementing line numbers when they shouldn't have.
    * Multi-line diffs were sometimes treating changed lines as duplicates.
    * `asString()` no longer fails if value is null. Instead, it converts the value to "null".

## Version 5.1.1 - 2019/07/22

* Improvements
    * Textual diffs now use `'+'` for characters that needs to be added, `'-'` for characters that needs to be
      removed, and `' '` for identical characters. End of stream is marked with \0 even for single-line diffs.
    * Optimize thrown `IOException`s (improves efficiency when the stack trace is not consumed).
* Bug fixes
    * `isPositive()` was not failing for `NaN`.

## Version 5.1.0 - 2019/07/16

* New features
    * `Configuration` (and all implementing `Requirements`) are thread-safe. Verifiers, Validators still
      assume single-threaded use.
* Breaking changes
    * `Configuration` methods now return updated configuration instead of `this`.
* Bug fixes
    * `assertThat()` was using the assertion status of `MainConfiguration` instead of `Configuration` by
      mistake.
    * `GlobalConfiguration.isCleanStackTrace()` was being ignored.

## Version 5.0.0 - 2019/07/10

* New features
    * Added `Requirements.validateThat()`, an entry-point that aggregates multiple errors before returning
      feedback to the user.
* Breaking changes
    * Verifiers now assume single-threaded use. This allows us to make some performance optimizations at the
      cost of thread-safety.
    * Removed `Configuration.getException()`, `withException()`, `withDefaultException()` because they don't
      allow users to throw checked exceptions and removing them simplified the implementation. Users wishing
      to override the exception type should catch and wrap the thrown exception.
    * Replaced `ObjectVerifier.getActualIfPresent()` with `getActual()` and `isActualAvailable()` because the
      previous API could not differentiate between `null` and the value not being present.
    * `length(Consumer<PrimitiveNumberVerifier<Integer>)`
      and `size(Consumer<PrimitiveNumberVerifier<Integer>)` now take a `Consumer<SizeVerifier>` argument.
    * Renamed `GlobalRequirements.isLibraryRemovedFromStackTrace()` to `cleanStackTrace()`.
    * Moved annotations from `org.bitbucket.cowwoc.requirements.java.annotations` to
      `org.bitbucket.cowwoc.requirements.annotations`.
    * Renamed `ApiGenerator.apply()` to `writeTo()`.

* Bug fixes
    * `CollectionVerifier<T>.isNotEqualTo(Object value)` should return `true` when `value` is not of
      type `<T>` (as opposed to throwing an exception).
    * Improved diff color scheme for IntelliJ IDEA.
    * Don't diff `boolean`s.
    * `Terminal.setEncodingImpl()` wasn't actually setting the encoding unless the platform was Windows.

## Version 4.0.6-RC - 2019/03/06

* Breaking changes
    * Removed `Configuration.getConfiguration()`

## Version 4.0.5-RC - 2019/03/06

* New features
    * Java 11 support.
    * Added `Object.isNotInstanceOf()`.
    * Added `ThreadRequirements` to specify contextual information on a thread-local level.
* Breaking changes
    * All method parameters except for `addContext()` order their parameters as `(value, name)`. This is the
      original method ordering from version 3.x.
        * Here is a regular expression to help automate the change. It isn't perfect (so be sure to review the
          changes) but it will handle most cases.
        * Replace: `requireThat\("(.+?)", (.+?)\)\.`
        * With   : `requireThat($2, "$1").`
    * Renamed `Requirements` to `DefaultRequirements` and `Verifiers` to `Requirements`. You should be
      statically importing `DefaultRequirements` from now on, and instantiate `Requirements` if you wish to
      override the default configuration.
    * Renamed `GlobalConfiguration` to `GlobalRequirements`.
    * `ComparableVerifier.isBetween()` now expects an exclusive upper-bound. Please
      use `ComparableVerifier.isBetweenClosed()` for the old functionality (when you need an inclusive
      upper-bound).
    * Renamed `groupId` from `org.bitbucket.cowwoc` to `org.bitbucket.cowwoc.requirements`. `artifactId` has
      also changed slightly.
    * Verifier methods that always return true or false are now `@Deprecated` and throw an exception
      explaining why the method should not be invoked.
    * Removed deprecated `TerminalEncoding.detect()` method.
    * Renamed `GlobalConfiguration.getAvailableTerminalEncodings()` to `listTerminalEncodings()`.
    * Renamed `GlobalConfiguration.apiInStacktrace()` to `removeLibraryFromStacktrace()`.
    * Renamed `Object.isIn()` to `isOneOf()`, `isNotIn()` to `isNotOneOf()`.
    * Dropped 32-bit builds.
* Minor changes
    * Log using `DEBUG` level if the native library is missing, instead of `WARN`.

## Version 4.0.4-RC - 2018/02/08

* New features
    * Added `ObjectVerifier.isSameObjectAs()`, `isNotSameObjectAs()`.
    * Added `ArrayVerifier.doesNotContainExactly()`, `CollectionVerifier.doesNotContainExactly()`.
    * Added verifiers for primitive arrays: `byte[]`, `short[]`, `int[]`, `long[]`, `double[]`, `float[]`
      , `boolean[]` and `char[]`.
* Bugfixes
    * `CollectionVerifier.doesNotContainAny()` was listing the wrong unwanted elements on failure.

## Version 4.0.3-RC - 2018/02/07

* New features
    * Added `Comparable.isComparableTo()`, `isNotComparableTo()`.

## Version 4.0.2-RC - 2018/01/06

* Bugfixes
    * NumberVerifier was handling fractional values incorrectly.

## Version 4.0.1-RC - 2017/12/27

* Bugfixes
    * Added support for Windows versions with four components (e.g. 10.0.16299.125). Previous versions
      only had three components.

## Version 4.0.0-RC - 2017/07/30

* Breaking changes
    * **Swapped order of the `name` and `value` parameters for all methods except `addContext()`.** All
      methods now take parameters in the same order: `(name, value)`
        * Here is a regular expression to help automate the change:
        * Replace: `requireThat\((.+?), "(.+?)"\)\.`
        * With   : `requireThat("$2", $1).`
    * Methods `isEquals()`, `isNotEquals()` now compare to `Object` instead of `T`.
        * This enables `requireThat("actual", someInteger).isEqualTo(someNumber)` which was previously not
          possible.
    * Textual diff omits "Diff" if lines are identical.
    * Removed `Configurable.addContext(String key, Supplier<String> value)`.
    * `Configurable.getContext()` is now `@Beta` because the return type might change in the future.
* New features
    * Added `Configurable.withStringConverter()`, `withoutStringConverter()`.
    * Added verifiers for `char` values.
* Bugfixes
    * If `actual != expected` but the String representations were equal, the output should have contained the
      actual value, but did not.

## Version 3.0.13 - 2017/07/13

* Bugfixes
    * Fixed warning when running under Linux: "java.lang.UnsatisfiedLinkError: librequirements.so:
      /usr/lib64/libstdc++.so.6: version 'GLIBCXX_3.4.21' not found (required by librequirements.so)".

## Version 3.0.12 - 2017/06/07

* New featurees
    * Added `String.isTrimmed()`.

## Version 3.0.11 - 2017/05/10

* New features
    * Added `Configurable.withDiff()`, `withoutDiff()`.
    * Ability to override String representation of context value
      using `Configurable.addContext(String, Supplier<String>)`
    * Windows colored diffs now support `XTERM_8COLOR` terminal encoding.
* Improvements
    * Only log an error once when the native library is not available.
* Bugfixes
    * `Configuration` was supposed to be immutable but `addContext()` was modifying the object.
    * Colored diffs were not reseting colors at the end of each line.
    * We no longer strip the padding marker from the end of diff lines.
    * `XTERM_8COLOR` terminal encoding no longer uses "bold" attribute which it does not support.

## Version 3.0.10 - 2017/05/02

* Bugfixes
    * An `AssertionError` was thrown if a verifier threw an exception with a cause
      and `GlobalConfiguration.apiInStacktrace` was false.

## Version 3.0.9 - 2017/05/02

* Bugfixes
    * ANSI colors were being used on non-Windows platforms even when stdout was redirected.

## Version 3.0.8 - 2017/05/02

* Bugfixes
    * `OperatingSystem.Version.detect()` was failing if the operating-system version did not contain a build
      number (e.g. MacOS 10.12).
    * Windows 10 terminal was never using colors, even if they were supported.
* Changes
    * Deprecated `TerminalEncoding.detect()`.

## Version 3.0.7 - 2017/04/26

* New features
    * Added `BooleanVerifier`, `PrimitiveBooleanVerifier`
* Improvements
    * `CollectionVerifier.getActual()` now returns subclasses of type `Collection` (e.g. `List`, `Iterable`).

## Version 3.0.6-RC - 2017/04/05

* New features
    * Added `StringVerifier.contains()`, `doesNotContain()`

## Version 3.0.5-RC - 2017/04/05

* Bugfixes
    * Verifier's default constructor was reusing the same Configuration across all Verifiers; fixed.

## Version 3.0.4-RC - 2017/04/04

* New features
    * Added requirements-maven-plugin:unpack for unpacking native libraries.
    * Added `StringVerifier.asUrl()`, `UriVerifier.asUrl()` and `UrlVerifier`.

## Version 3.0.1-RC - 2017/03/01

* New features
    * Added `ObjectVerifier.isNotIn()`.
    * Added `requireThat()`, `assertThat()` for all primitive numbers.
* Changes
    * Deprecated `BigDecimal.precision().isNotZero()`, `isPositive()`, `isNotNegative()`.
    * Deprecated `CollectionVerifier.size().isNegative()`, `isNotNegative()`.
    * Deprecated `isNotNull()` for primitive numbers.
* Bugfixes
    * `ContainerSizeVerifier` was outputting `Actual` instead of the container name.
    * `BigDecimal.scale().isZero()`, `isNotZero()`, `isPositive()`, `isNotPositive()`, `isNegative()`
      , `isNotNegative()` were not checking the actual value; fixed.
    * `Optional.isNotNull()` was not checking the actual value; fixed.

## Version 3.0.0-RC - 2017/02/09

* Footprint/Performance
    * Size decreased by 10%
        * Version 2.0.7: 185KB across all modules.
        * Version 3.0.0: 154 KB for the core module, 13KB for the Guava module
    * requireThat() performance improved by 43%
* New features
    * If objects are not equal but their `toString()` looks the same, we compare their class, followed by
      their `hashCode()`.
    * Added `ObjectVerifier.getActual()`, `ObjectVerifier.getActualIfPresent()`.
    * Added `ObjectVerifier.asString()`.
    * Added `StringVerifier.asUri()`.
    * Added `InetAddressVerifier.isIpV4()`, `InetAddressVerifier.isIpV6()`.
    * Added `OptionalVerifier.contains()`.
    * Added `ArrayVerifier.asCollection()` and `CollectionVerifier.asArray()`.
    * Added support for ANSI colors under Windows 10 build 14931 (and later) but
      this [requires the use of a native library](Deploying_Native_Libraries.md)
    * Added support for ANSI 24-bit colors for platforms that support it.
* Breaking changes
    * Separated project into `requirements-core` module for Core JDK classes and `requirements-guava` for
      Guava classes.
    * Replaced `AssertionVerifier` and `RequirementVerifier` by `Verifiers`. The neat thing about this class
      is that it exposes different functionality based on the modules present on the compiler classpath.
    * Any method that returns a new verifier (e.g. `MapVerifier.keySet()`) is expected to provide an overload
      that consumes the verifier instead of returning it. This mechanism replaces `ObjectVerifier.isolate()`
      which has been removed.
    * Renamed `MultimapVerifier.entrySet()` to `MultimapVerifier.entries()`
    * Removed `StringVerifier.isEmailFormat()` because it wasn't adding sufficient value (there is no good way
      to validate emails offline).
    * Removed `@Beta` from all methods. Any methods that were kept are now considered stable. The remaining
      methods were removed.
    * Renamed `ComparableVerifier.isIn()` to `ComparableVerifier.isBetween()`.
    * Replaced `StringVerifier.isIpAddressFormat()` with `StringVerifier.asInetAddress()`.
    * Now using `Requirements.globalConfiguration()` to configure system-wide settings instead of using system
      properties.
    * Verifiers are no longer configurable after `requireThat()`/`assertThat()` are invoked.
* Bugfixes
    * `Array.length()` was invoking `Object.toString()` instead of `Arrays.toString()`
    * `ObjectVerifier.isInstanceOf()` was throwing `NullPointerException` if `actual == null`.

## Version 2.0.6 - 2016/11/21

* New features
    * Added ArrayRequirements.
    * Improved output for objects with the same toString() that are not equal.
* Bugfixes
    * `Object.isEqualTo()` now handles the parameter or value being null.

## Version 2.0.5 - 2016/11/15

* Bugfixes
    * ANSI colors outputted on Windows 10 versions that did not support it; fixed.
    * `Collection.size().isIn(Range)`, `Map.size().isIn(Range)` and `String.length().isIn(Range)` were
      outputting unicode characters; fixed.
    * `ComparableRequirementsImpl.isGreaterthanOrEqualTo()` was outputting the wrong parameter name.

## Version 2.0.3 - 2016/10/13

* New features
    * Added [String diffs](String_Diff.md).
    * Added `ObjectRequirements.isIn()`.
    * Added `RequirementVerifier` and `AssertionVerifier`.
    * Added `DoubleRequirements.isNumber()`, `isNotNumber()`, `isFinite()`, `isNotFinite()`.
* Changes
    * Deprecated `Assertions` in favor of `AssertionVerifier`.
    * Deprecated methods that depend on Guava, in anticipation of an upcoming release that removes it as a
      dependency.
    * `Map.entrySet()`, `Map.keySet()`, `Map.values()` now return the entry, key, value in "Actual" and
      the enclosing map in "Map".
    * Replaced `ObjectRequirements.withContext(Map<String, Object>)` with `addContext(String, Object)`.
* Bugfixes
    * `UriRequirements` was using the wrong parameter name in exception messages; fixed.

## Version 2.0.2 - 2016/08/06

* New features
    * Added Requirements.assertionsAreEnabled().

## Version 2.0.0 - 2016/07/31

* Renamed library from `Preconditions` to `Requirements` to imply that it handles more than just
  preconditions.
* Breaking changes
    * Renamed `ObjectPreconditions.usingException()` to `withException()`.
* New features
    * Added `ObjectPreconditions.withContext()` which allows users to pass in context to append to the
      exception message.
    * The library no longer shows up in exception stack-traces. Set
      `org.bitbucket.cowwoc.requirements.Requirements.showFullStackTrace` to `true` to disable this feature.
    * Added convenience methods `Requirements.assertThat()`.

## Version 1.29 - 2016/05/06

* New feature
    * Added `ComparablePreconditions`

## Version 1.28 - 2016/01/20

* New features
    * Added `ObjectPreconditions.isolate()` for combining nested elements into a single statement.
    * Added `CollectionPreconditions.doesNotContainDuplicates()`.
    * Added `CollectionPreconditions.containsExactly()`.
    * Added `Assertions.isEnabled()`.

## Version 1.27 - 2015/12/09

* New features
    * Added ability to name element(s) passed to `CollectionPreconditions.contains*()`.

## Version 1.26 - 2015/12/09

* New features
    * Added `CollectionPreconditions.containsAny()`, `containsAll()`, `doesNotContainAny()`
      and `doesNotContainAll()`.

## Version 1.25 - 2015/07/06

* New feature
    * Added locale-specific grouping separator to most numbers.

## Version 1.24 - 2015/07/06

* New features
    * Report actual value for `CollectionPreconditions.doesNotContain()`
      , `NumberPreconditionsImpl.isNotNegative()` and `isNotPositive()`.
* Bugfixes
    * `Preconditions.usingException()` was throwing a `ClassCastException` if the precondition type was
      not Object.

## Version 1.22 - 2015/06/29

* New features
    * When objects are not equal, highlight the difference using a visual diff.

## Version 1.21 - 2015/06/08

* New features
    * Added `CollectionPreconditions.contains()`, `doesNotContain()`

## Version 1.20 - 2015/06/01

* New features
    * Added `Preconditions.isNotEqualTo()`

## Version 1.19 - 2015/05/27

* Bugfixes
    * Order of parameters was reversed for `Collection.size()`, `Map.size()`, `String.length()` error
      messages.

## Version 1.18 - 2015/05/19

* New features
    * Added `CollectionPreconditions.isEmpty()` and `MapPreconditions.isEmpty()`

## Version 1.17 - 2015/05/19

* Bugfixes
    * TestNG maven dependency was not test-scoped.

## Version 1.16 - 2015/05/15

* Bugfixes
    * `Assertions.requireThat()` was throwing a `StackOverflowError` if assertions were enabled.
* New features
    * Added `ObjectPreconditions.isEqualTo()`.

## Version 1.15 - 2015/05/09

* New features
    * Added `CollectionPreconditions.size()`

## Version 1.14 - 2015/05/05

* New features
    * Improved modularity by adding preconditions over properties.
        * Example: `Preconditions.requireThat(name, "name").length().isLessThanOrEqualTo(30)` instead
          of `Preconditions.requireThat(name, "name").hasMaximumLength(30)`.

## Version 1.13 - 2015/05/04

* New features
    * Added `AssertionPreconditions` (preconditions that get skipped if assertions are disabled)

## Version 1.11 - 2014/11/09

* New features
    * Added `YearPreconditions`
    * Added `OptionalPreconditions`

## Version 1.10 - 2014/09/08

* New features
    * Added `StringPreconditions.isEmpty()`, `trim()`.

## Version 1.9 - 2014/08/27

* New features
    * Added `BigDecimalPreconditions`

## Version 1.8 - 2014/07/25

* New features
    * `StringPreconditions.lengthIn()`, `hasMinimumLength()`, `hasLength()`, `hasMaximumLength()` now
      mention actual value on failure.

## Version 1.7 - 2014/07/08

* New features
    * Added `StringPreconditions.startsWith(String)`, `doesNotStartWith(String)`, `endsWith(String)`
      , `doesNotEndWith(String)`.

## Version 1.6 - 2014/05/29

* Breaking changes
    * Renamed `StringPreconditions.isShorterThan()` to `hasMaximumLength()`.
    * Renamed `StringPreconditions.isValidEmail()` to `isEmailFormat()`.
* New features:
    * Added `PathPreconditions.isRelative()`, `isAbsolute()`.
    * Added `StringPreconditions.hasMinimumLength()`, `hasLength()`, `isIpAddressFormat()`.

## Version 1.4 - 2014/02/02

* New features
    * Added `NumberPreconditions`

## Version 1.3 - 2014/01/19

* Breaking change
    * Swapped order of `Preconditions.requireThat()` parameters to facilitate migration from
      Guava.

## Version 1.1 - 2014/01/08

* Breaking changes
    * Renamed `Preconditions.valueOf()` to `Preconditions.checkThat()`.
    * `PathPreconditions.exists()` now throws `IllegalArgumentException` instead of `IllegalStateException`.
* New features
    * Added `Preconditions.isInstanceOf()`.
    * Added `ClassPreconditions`.

## Version 1.0 - 2013/11/11

* Initial release