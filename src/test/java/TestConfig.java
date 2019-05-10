import de.d3adspace.caladrius.Caladrius;
import de.d3adspace.caladrius.CaladriusImpl;

import java.nio.file.Paths;

public class TestConfig {

    public static void main(String[] args) {
        Caladrius caladrius = new CaladriusImpl();
        Test test = caladrius.readConfig(Test.class, Paths.get("test.yml"));

        System.out.println(test);
    }
}
