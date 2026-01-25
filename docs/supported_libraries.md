Each module uses a separate class pair for validation. For example, 
[DefaultJavaValidators](https://cowwoc.github.io/requirements.java/13.2/io.github.cowwoc.requirements.java/com/github/cowwoc/requirements13/java/DefaultJavaValidators.html)
and
[JavaValidators](https://cowwoc.github.io/requirements.java/13.2/io.github.cowwoc.requirements.java/com/github/cowwoc/requirements13/java/JavaValidators.html)
validate the core Java API. Similarly,
[DefaultGuavaValidators](https://cowwoc.github.io/requirements.java/13.2/io.github.cowwoc.requirements.java/com/github/cowwoc/requirements13/guava/DefaultGuavaValidators.html)
and
[GuavaValidators](https://cowwoc.github.io/requirements.java/13.2/io.github.cowwoc.requirements.java/com/github/cowwoc/requirements13/guava/GuavaValidators.html)
validate the Guava API.

The following table lists validators for third-party libraries: 

| Library                                                       | Dependency |
|---------------------------------------------------------------|------------|
| [Guava API](https://guava.dev/releases/28.0-jre/api/docs/)    | [io.github.cowwoc.requirements:guava](https://search.maven.org/search?q=g:io.github.cowwoc.requirements%20AND%20a:guava) |

For example,

```
<dependency>
  <groupId>io.github.cowwoc.requirements</groupId>
  <artifactId>requirements-guava</artifactId>
  <version>${requirements.version}</version>
</dependency>
```