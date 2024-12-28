Here are benchmark results for version 9.0.0 per [jmh](http://openjdk.java.net/projects/code-tools/jmh/).
Absolute numbers differ from machine to machine, but the relative difference between benchmarks should remain
the same.

Overhead of assert()
---

| Benchmark                          | Nanoseconds   | Alloc rate (bytes / operation) |
|------------------------------------|---------------|--------------------------------|
| empty method                       | 0.354 ± 0.002 | 0.001 ±   0.001                |
| assumeThat() with asserts disabled | 0.352 ± 0.002 | 0.001 ±   0.001                |

* `assert requireThat()/assumeThat()/checkIf()` has no overhead when assertions are disabled.

Overhead of failed validations
---

| Benchmark                  | Nanoseconds        |
|----------------------------|--------------------|
| checkIf().elseGetMessage() | 4,613.103 ± 74.229 |
| requireThat().elseThrow()  | 15,072.5 ± 44.054  |

* checkIf() is the fastest way to collect validation failures.

Overhead of cleanStackTrace
---

| Benchmark                                        | Nanoseconds         |
|--------------------------------------------------|---------------------|
| cleanStackTrace(false).requireThat().elseThrow() | 15,267.432 ± 80.637 |
| cleanStackTrace(true).requireThat().elseThrow()  | 15,072.5 ± 44.054   |

* No noticeable overhead for processing stack traces.

Performance comparison to AssertJ
---

| Benchmark                                              | Nanoseconds          |
|--------------------------------------------------------|----------------------|
| requireThat() on successful validations                | 86.936 ± 1.854       |
| assertJ on successful validations                      | 46.598 ± 0.079       |
| requireThat() on failed validations                    | 15,072.5 ± 44.054    |
| assertJ on failed validations                          | 11,442.356 ± 17.581  |
| checkIf() on failed validations                        | 4,613.103 ± 74.229   |
| SoftAssertions.errorsCollected() on failed validations | 98,818.549 ± 181.496 |

* assertJ is faster for normal validations, but an order of magnitude slower when returning multiple failures.