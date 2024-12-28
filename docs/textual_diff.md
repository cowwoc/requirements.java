A textual diff looks like this:

```text
Actual  : foosball    \0
Diff    : ----    ++++  
Expected:     ballroom\0
```

## Basic Rules for Interpreting Diff

* Minus (`-`) denotes a character that needs to be removed from `Actual`.
* Space (` `) denotes a character that is equal in `Actual` and `Expected`.
* Plus (`+`) denotes a character that needs to be added to `Actual`.
* "Diff" is omitted for lines that are identical.
* When `-` is present, `Actual` is padded to line up vertically with `Expected`.
* When `+` is present, `Expected` is padded to line up vertically with `Actual`.
* The padding is not part of `Actual` and `Expected`'s value, respectively. Read on for concrete examples.
* Lines always end with `\n` or `\0`. The former denotes a newline. The latter denotes the end of the string.
* Lines ending with "\n\n" or "\0\0" represents the literal string "\n" followed by a newline character, or the literal string "\0" followed by the end of string, respectively.

## Example 1: insert

```text
Actual   = ""
Expected = "text"
```

results in the following diff:

```text
Actual  :     \0
Diff    : ++++  
Expected: text\0
```

Meaning, to go from `Actual` to `Expected` we need to insert "text".

## Example 2: delete

```text
Actual   = "text"
Expected = ""
```

results in the following diff:

```text
Actual  : text\0
Diff    : ----  
Expected:     \0
```

Meaning, to go from `Actual` to `Expected` we need to delete "text".

## Example 3: padding

```text
Actual   = "foo"
Expected = "   foo"
```

results in the following diff:

```text
Actual  :    foo\0
Diff    : +++     
Expected:    foo\0
```

Meaning:

* To go from `Actual` to `Expected` we need to insert three spaces at the beginning of `Actual`.
* There is no whitespace in `Expected` in front of "foo". This padding is used to line up the strings vertically.

## Example 4: delete, keep, insert

```text
Actual   = "foosball"
Expected = "ballroom"
```

results in the following diff:

```text
Actual  : foosball    \0
Diff    : ----    ++++  
Expected:     ballroom\0
```

Meaning, we need to:

* Delete "foos".
* Keep "ball".
* Insert "room".
* There is no whitespace before "ballroom" or after "foosball". This padding is used to line up the strings vertically.

## Example 5: Objects with the same toString() that are not equal

* If objects are not equal, and their `toString()` values differ, we output their String representations.
* If the `toString()` values are equal, but their types differ, we output the string representation of `Actual` followed by the two types (i.e. `Actual.class` vs `Expected.class`).
* If their classes are equal, but their `hashCode()` values differ, we output the string representation of `Actual` followed by the two hashcodes (i.e. `Actual.hashCode()` vs `Expected.hashCode()`).

For example,

```text
Actual   = "null"
Expected = null
```

results in the following diff:

```text
Actual        : null
Actual.class  : java.lang.String    \0
Diff          : ----------------++++
Expected.class:                 null\0
```

## Example 6: Multi-line Strings

When comparing multi-line strings:

* We display the diff on a per-line basis.
* `Actual` and `Expected` are followed by a 0-based line number.
* Lines that are identical (with the exception of the first and last line) are omitted.

For example,

```text
Actual   = "first\nsecond\nfoo\nforth\nfifth"
Expected = "first\nsecond\nbar\nforth\nfifth"
```

results in the following diff:

```text
Actual@0  : first\n
Expected@0: first\n

[...]

Actual@2  : foo   \n
Diff      : ---+++  
Expected@2:    bar\n

[...]

Actual@4  : fifth\0
Expected@4: fifth\0
```

Meaning:

* Lines 1-2 were equal.
* On line 3, we need to delete "foo" and insert "bar".
* Lines 4-5 were equal.

## Example 7: Missing Line Numbers

When `Actual` or `Expected` contain a line that does not have a corresponding line on the other side we omit the latter's line number.

```text
Actual   = "Foo\nBar"
Expected = "Bar"
```

results in the following diff:

```text
Actual@0  : Foo\n     
Diff      : -----     
Expected@0:      Bar\0


Actual@1  : Bar\0
Expected  :       
```

Meaning:

* `Actual` contained more lines than `Expected`.
* `Expected` did not have a line that corresponded to `Actual` line 1.
* We need to delete line 1 and retain line 2 unchanged.

## Example 8: Lists and Arrays

When comparing lists or arrays:

* We display the diff on a per-element basis.
* `Actual` and `Expected` are followed by the element's index number.
* Indexes that are identical (with the exception of the first and last line) are omitted.

For example,

```java

Actual   = List.of("1", "foo\nbar", "3");
Expected = List.of("1", "bar\nfoo", "3");
```

results in the following diff:

```text
Actual[0]    : 1\0
Expected[0]  : 1\0

Actual[1]@0  : foo\n
Diff         : -----   ++
Expected[1]@0:      bar\n     

Actual[1]@1  : bar   \0
Diff         :    +++
Expected[1]@1:    foo\0

Actual[2]    : 3\0
Expected[2]  : 3\0
```

Meaning:

* Index 0 was equal.
* On index 1 line 0, we need to delete "foo\n".
* On index 1 line 1, we need to insert "\n" after "bar".
* On index 1 line 1, we need to insert "foo" before the end of string.
* Index 2 was equal.