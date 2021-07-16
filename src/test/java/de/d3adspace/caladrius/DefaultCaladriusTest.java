package de.d3adspace.caladrius;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import de.d3adspace.caladrius.annotation.Config;
import de.d3adspace.caladrius.annotation.Key;
import de.d3adspace.caladrius.config.ConfigType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultCaladriusTest {
  private static final Path SIMPLE_CONFIG = Paths.get("src/test/resources/config_simple.yml");
  private static final Path SIMPLE_CONFIG_WRITE_TARGET = Paths.get("src/test/resources/config_simple_written.yml");
  private static final String SIMPLE_CONFIG_CONTENT = "test: 6\n";
  private static final Path COMPLEX_CONFIG = Paths.get("src/test/resources/config_complex.yml");
  private static final Path COMPLEX_CONFIG_WRITE_TARGET = Paths.get("src/test/resources/config_complex_written.yml");
  private static final String COMPLEX_CONFIG_CONTENT = "database:\n"
    + "  users:\n"
    + "    1: leon\n"
    + "    2: alex\n"
    + "balance: 1000.02\n"
    + "name: Ruby\n"
    + "ids:\n"
    + "- 1\n"
    + "- 13\n"
    + "age: 11\n";
  private Caladrius caladrius;

  @BeforeEach
  void setUp() {
    caladrius = CaladriusFactory.createCaladrius();
  }

  @Test
  void testReadConfigSimple() {
    ConfigSimple config = caladrius.readConfig(
      ConfigSimple.class,
      SIMPLE_CONFIG
    );
    assertEquals(5, config.test());
  }

  @Test
  void testWriteConfigSimple() {
    ConfigSimple config = caladrius.readConfig(
      ConfigSimple.class,
      SIMPLE_CONFIG
    );
    config.test = 6;
    caladrius.writeConfig(config, SIMPLE_CONFIG_WRITE_TARGET);
    try {
      String configString = Files.readString(SIMPLE_CONFIG_WRITE_TARGET);
      assertEquals(SIMPLE_CONFIG_CONTENT, configString);
    } catch (IOException e) {
      fail(e);
    }
  }

  @Test
  void testReadConfigComplex() {
    ConfigComplex config = caladrius.readConfig(
      ConfigComplex.class,
      COMPLEX_CONFIG
    );
    assertEquals(11, config.age);
    assertEquals("Ruby", config.name);
    assertEquals(2, config.databaseUsers.size());
  }

  @Test
  void testWriteConfigComplex() {
    ConfigComplex config = caladrius.readConfig(
      ConfigComplex.class,
      COMPLEX_CONFIG
    );

    caladrius.writeConfig(config, COMPLEX_CONFIG_WRITE_TARGET);
    try {
      String configString = Files.readString(COMPLEX_CONFIG_WRITE_TARGET);
      assertEquals(COMPLEX_CONFIG_CONTENT, configString);
    } catch (IOException e) {
      fail(e);
    }
  }

  @Config(name = "simple", type = ConfigType.YAML)
  public static final class ConfigSimple {
    @Key("test")
    private int test;

    public ConfigSimple() {
    }

    public ConfigSimple(
      int test
    ) {
      this.test = test;
    }

    public int test() {
      return test;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this)
        return true;
      if (obj == null || obj.getClass() != this.getClass())
        return false;
      var that = (ConfigSimple) obj;
      return this.test == that.test;
    }

    @Override
    public int hashCode() {
      return Objects.hash(test);
    }

    @Override
    public String toString() {
      return "ConfigSimple[" +
        "test=" + test + ']';
    }
  }

  @Config(name = "complex", type = ConfigType.YAML)
  public static final class ConfigComplex {
    @Key("database.users")
    private Map<Integer, String> databaseUsers;
    @Key("balance")
    private double balance;
    @Key("name")
    private String name;
    @Key("ids")
    private List<Integer> ids;
    @Key("age")
    private int age;

    public ConfigComplex() {
    }
  }
}