Simply add a dependency for the module you are interested in, rebuild your project, and additional API methods will get generated inside [DefaultRequirements](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultRequirements.html) and [Requirements](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/Requirements.html):

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
