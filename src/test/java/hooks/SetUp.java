package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class SetUp {
    public static Scenario scenario;

    @Before
    public void before(Scenario scenario)
    {
        this.scenario = scenario;
    }


}
