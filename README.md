# caladrius
Effective configuration provider. You can read and write simple config files in various formats.

# Build Status

|             | Build Status                                                                                                            |
|-------------|-------------------------------------------------------------------------------------------------------------------------|
| Master      | [![Build Status](https://travis-ci.org/d3adspace/caladrius.svg?branch=master)](https://travis-ci.org/d3adspace/caladrius) |
| Development | [![Build Status](https://travis-ci.org/d3adspace/caladrius.svg?branch=dev)](https://travis-ci.org/d3adspace/caladrius)    |

# Installation / Usage

**Maven repositories**
```xml
<repositories>
    <!-- Klauke Enterprises Releases -->
    <repository>
        <id>klauke-enterprises-maven-releases</id>
        <name>Klauke Enterprises Maven Releases</name>
        <url>https://repository.klauke-enterprises.com/repository/maven-releases/</url>
    </repository>
	
    <!-- Klauke Enterprises Snapshots -->
    <repository>
        <id>klauke-enterprises-maven-snapshots</id>
        <name>Klauke Enterprises Maven Snapshots</name>
        <url>https://repository.klauke-enterprises.com/repository/maven-snapshots/</url>
    </repository>
</repositories>
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
import de.d3adspace.caladrius.CaladriusImpl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ExampleConfigApp {

    public static void main(String[] args) {
        
        // Define config path
        Path path = Paths.get("config.yml");
        
        // Create new caladrius instance
        Caladrius caladrius = new CaladriusImpl();
        
        // Read the config
        ExampleConfig exampleConfig = caladrius.readConfig(ExampleConfig.class, path);

        // Print out config
        System.out.println(exampleConfig);
    
        // Add a new user to the users key value store
        exampleConfig.getUsers().put(5, "ruby");

        // Write the config back to disk
        caladrius.writeConfig(exampleConfig, path);
    }
}
```
