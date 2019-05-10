import de.d3adspace.caladrius.annotation.Config;
import de.d3adspace.caladrius.annotation.Key;
import de.d3adspace.caladrius.config.ConfigType;

import java.util.Objects;

@Config(name = "testConfig", type = ConfigType.YAML)
public class Test {

    @Key("name")
    private String name;

    @Key("amount")
    private int amount;

    @Key("credentials.user")
    private String user;

    @Key("credentials.password")
    private String password;

    public Test() {
    }

    public Test(String name, int amount, String user, String password) {
        this.name = name;
        this.amount = amount;
        this.user = user;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Test)) {
            return false;
        }
        Test test = (Test) o;
        return amount == test.amount &&
                name.equals(test.name) &&
                user.equals(test.user) &&
                password.equals(test.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, user, password);
    }
}
