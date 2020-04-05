When a [String comparison](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements/java/extension/ExtensibleObjectVerifier.html#isEqualTo(java.lang.Object))
fails, the library outputs a [diff](https://en.wikipedia.org/wiki/Diff) of the values being compared.

Depending on the terminal capability, you will see a [Textual](Textual_Diff.md) or [Colored](Colored_Diff.md) diff.

# Native Libraries

This feature tries accessing [native libraries](Deploying_Native_Libraries.md). If they are not present, a warning message will be logged and the library will silently fall back to using textual diffs.

See the aforementioned link for disabling the use of native libraries.

# Overriding Terminal Detection

We disable colors if stdout is redirected. This doesn't necessarily mean that ANSI codes are not supported, but we chose to err on the side of caution.
Users can override this behavior by invoking [GlobalRequirements.withTerminalEncoding(TerminalEncoding)](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements/java/GlobalRequirements.html#withTerminalEncoding(com.github.cowwoc.requirements.natives.terminal.TerminalEncoding)).
