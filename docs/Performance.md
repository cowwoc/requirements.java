Here are benchmark results for version 9.0.0 per [jmh](http://openjdk.java.net/projects/code-tools/jmh/).
Absolute numbers differ from machine to machine, so focus on the relative numbers.

Overhead of assert()
---

| Benchmark                          | Nanoseconds | Alloc rate (bytes / operation) |
|------------------------------------|-------------|--------------------------------|
| empty method                       | 0.360       | 0.000                          |
| assumeThat() with asserts disabled | 0.360       | 0.000                          |

* `assert requireThat()/assumeThat()/checkIf()` has no overhead when assertions are disabled.

Overhead of failed validations
---

| Benchmark                  | Nanoseconds |
|----------------------------|-------------|
| checkIf().elseGetMessage() | 4,639.568   |
| requireThat().elseThrow()  | 13,478.051  |

Overhead of cleanStackTrace
---

| Benchmark                                        | Nanoseconds |
|--------------------------------------------------|-------------|
| cleanStackTrace(false).requireThat().elseThrow() | 4,612.712   |
| cleanStackTrace(true).requireThat().elseThrow()  | 13,478.051  |

Performance comparison to AssertJ
---

| Benchmark                               | Nanoseconds |
|-----------------------------------------|-------------|
| requireThat() on successful validations | 49.229      |
| assertJ on successful validations       | 31.172      |
| requireThat() on failed validations     | 13,478.051  |
| assertJ on failed validations           | 108,914.127 |

* assertJ is fast when validations pass, but an order of magnitude slower on validation failure.