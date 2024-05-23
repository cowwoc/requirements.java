Each module uses a separate class pair for validation. For example, 
[DefaultJavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/java/DefaultJavaValidators.html)
and
[JavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/java/JavaValidators.html)
validate the core Java API. Similarly,
[DefaultGuavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/guava/DefaultGuavaValidators.html)
and
[GuavaValidators](https://cowwoc.github.io/requirements.java/9.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/guava/GuavaValidators.html)
validate the Guava API.

The following table lists validators for third-party libraries: 

| Library                                                       | Dependency |
|---------------------------------------------------------------|------------|
| [Guava API](https://guava.dev/releases/28.0-jre/api/docs/)    | [com.github.cowwoc.requirements:guava](https://search.maven.org/search?q=g:com.github.cowwoc.requirements%20AND%20a:guava) |

For example:

```
<dependency>
  <groupId>com.github.cowwoc.requirements</groupId>
  <artifactId>guava</artifactId>
  <version>${requirements.version}</version>
</dependency>
```