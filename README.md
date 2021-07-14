# caladrius
Effective configuration provider. You can read and write simple config files in various formats.

# Build Status

|             | CodeQL | Gradle Build | Gradle Publish |                                                                                           |
|-------------| ------ | ------------ | -------------- |
| master      | | | |

# Installation / Usage

## Gradle

**Gradle repositories**
```groovy
repositories {
  maven {
    name = "d3adspace-caladrius-github-package-registry"
    description = "3adspace Enterprises Caladrius GitHub Package Registry"
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

You first have to define a config model, that could look like this:
```java
import com.google.common.base.MoreObjects;
import de.d3adspace.caladrius.annotation.Config;
import de.d3adspace.caladrius.annotation.Key;
import de.d3adspace.caladrius.config.ConfigType;

import java.util.List;
import java.util.Map;

@Config(name = "exmapleConfig", type = ConfigType.YAML)
public class ExampleConfig {

    @Key("name")
    private String name;

    @Key("balance")
    private double balance;

    @Key("age")
    private int age;

    @Key("ids")
    private List<Integer> ids;

    @Key("database.users")
    private Map<Integer, String> users;

    public ExampleConfig() {
    }

    public ExampleConfig(String name, double balance, int age, List<Integer> ids, Map<Integer, String> users) {
        this.name = name;
        this.balance = balance;
        this.age = age;
        this.ids = ids;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public int getAge() {
        return age;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public Map<Integer, String> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("balance", balance)
                .add("age", age)
                .add("ids", ids)
                .add("users", users)
                .toString();
    }
}
```

The usage is then really simple:

```java
import de.d3adspace.caladrius.Caladrius;
import de.d3adspace.caladrius.CaladriusFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ExampleConfigApp {

    public static void main(String[] args) {

        // Define config path
        var path = Paths.get("config.yml");

        // Create new caladrius instance
        var caladrius = CaladriusFactory.createCaladrius();

        // Read the config
        var exampleConfig = caladrius.readConfig(ExampleConfig.class, path);

        // Print out config
        System.out.println(exampleConfig);

        // Add a new user to the users key value store
        exampleConfig.getUsers().put(5, "ruby");

        // Write the config back to disk
        caladrius.writeConfig(exampleConfig, path);
    }
}
```
