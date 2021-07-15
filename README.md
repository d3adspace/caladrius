# caladrius
Effective configuration provider. You can read and write simple config files in various formats.

# Build Status

| Branch             | CodeQL | Gradle Build | Gradle Publish                                                                                            |
|-------------| ------ | ------------ | -------------- |
| master      | [![CodeQL](https://github.com/d3adspace/caladrius/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/d3adspace/caladrius/actions/workflows/codeql-analysis.yml) | [![Gradle Build](https://github.com/d3adspace/caladrius/actions/workflows/gradle.yml/badge.svg)](https://github.com/d3adspace/caladrius/actions/workflows/gradle.yml) | [![Gradle Publish](https://github.com/d3adspace/caladrius/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/d3adspace/caladrius/actions/workflows/gradle-publish.yml) |

# Installation / Usage

## Gradle

**Gradle repositories**
```groovy
repositories {
  maven {
    name = "d3adspace-caladrius-github-package-registry"
    description = "d3adspace Enterprises Caladrius GitHub Package Registry"
    url = "https://maven.pkg.github.com/d3adspace/caladrius/"
  }
}
```

**Gradle dependencies**
```groovy
dependencies {
  implementation 'de.d3adspace:caladrius:2.0.0'
}
```

## Maven

**Maven repositories**
```xml
<repositories>
    <repository>
        <id>d3adspace-caladrius-github-package-registry</id>
        <name>D3adspace Enterprises Caladrius GitHub Package Registry</name>
        <url>https://maven.pkg.github.com/d3adspace/caladrius/</url>
    </repository>
</repositories>
```

**Maven dependencies**
```xml
<dependency>
  <groupId>de.d3adspace</groupId>
  <artifactId>caladrius</artifactId>
  <version>2.0.0</version>
</dependency>
```

# Example
