package HIS_E2.app_sanidad;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(Cucumber.class)

@CucumberOptions(plugin = {"pretty"})
public class RunCucumberTest {
}
