Here are benchmark results for version 5.1.2 per [jmh](http://openjdk.java.net/projects/code-tools/jmh/). Ignore absolute numbers as they will differ from machine to machine.

requireThat() overhead
---

| Benchmark                                                     | Nanoseconds |
|---------------------------------------------------------------|-------------|
| Empty method                                                  | 0.932       |
| requireThat()                                                 | 121.388     |
| Normal Java exception thrown, stack trace not consumed        | 1,716.733   |
| requireThat() failed, stack trace not consumed                | 2,613.664   |

* `requireThat()` overhead is very low (less than 1 µs) regardless of whether checks pass or fail, so long as the exception stack trace is not consumed.


Overhead of consuming stack trace
---

| Benchmark                                                     | Nanoseconds |
|---------------------------------------------------------------|-------------|
| Normal Java exception thrown, stack trace consumed            | 63,727.654  |
| requireThat() throws, stack trace consumed                    | 73,249.623  |

* `requireThat()` overhead is low (~10 µs) if checks fail and the stack trace is consumed.


assertThat() overhead
---

| Benchmark                         | Nanoseconds | Alloc rate (bytes / operation) |
|-----------------------------------|-------------|--------------------------------|
| empty method                      | 0.932       | 0.000                          |
| assertThat() with asserts disabled| 5.283       | 0.000                          |
| requireThat()                     | 121.388     | 216.000                        |

* `assertThat()` is cheap and allocation-free if assertions are disabled.


Overhead of assertThat() vs requireThat()
---

| Benchmark                                                     | Nanoseconds |
|---------------------------------------------------------------|-------------|
| requireThat()                                                 | 121.388     |
| assertThat() with asserts enabled                             | 130.504     |

* There is no downside to using `assertThat()`. Its overhead is negligible.
