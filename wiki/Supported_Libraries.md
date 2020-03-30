Simply add a dependency for the module you are interested in, rebuild your project, and additional API methods will get generated inside [DefaultRequirements](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements/com/github/cowwoc/requirements/DefaultRequirements.html) and [Requirements](https://cowwoc.github.io/requirements.java/6.0.0/docs/api/com.github.cowwoc.requirements/org/bitbucket/cowwoc/requirements/Requirements.html):

| Library                                                       | Dependency |
|---------------------------------------------------------------|------------|
| [Guava API](https://guava.dev/releases/28.0-jre/api/docs/)    | [com.github.cowwoc:requirements.guava](https://search.maven.org/search?q=g:org.bitbucket.cowwoc%20AND%20a:requirements.guava) |

For example:
```
<dependency>
  <groupId>org.bitbucket.cowwoc</groupId>
  <artifactId>requirements.guava</artifactId>
  <version>${requirements.version}</version>
</dependency>
```
