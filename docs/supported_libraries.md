Each module uses a separate class pair for validation. For example, 
[DefaultJavaValidators](https://cowwoc.github.io/requirements.java/10.8/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements10/java/DefaultJavaValidators.html)
and
[JavaValidators](https://cowwoc.github.io/requirements.java/10.8/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements10/java/JavaValidators.html)
validate the core Java API. Similarly,
[DefaultGuavaValidators](https://cowwoc.github.io/requirements.java/10.8/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements10/guava/DefaultGuavaValidators.html)
and
[GuavaValidators](https://cowwoc.github.io/requirements.java/10.8/com.github.cowwoc.requirements.java/com/github/cowwoc/requirements10/guava/GuavaValidators.html)
validate the Guava API.

The following table lists validators for third-party libraries: 

| Library                                                       | Dependency |
|---------------------------------------------------------------|------------|
| [Guava API](https://guava.dev/releases/28.0-jre/api/docs/)    | [com.github.cowwoc.requirements:guava](https://search.maven.org/search?q=g:com.github.cowwoc.requirements%20AND%20a:guava) |

For example,

```
<dependency>
  <groupId>com.github.cowwoc.requirements</groupId>
  <artifactId>guava</artifactId>
  <version>${requirements.version}</version>
</dependency>
```