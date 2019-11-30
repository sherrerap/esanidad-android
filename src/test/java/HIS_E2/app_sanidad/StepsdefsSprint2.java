package HIS_E2.app_sanidad;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@WebMvcTest
@RunWith(SpringRunner.class)
public class StepsdefsSprint2 {
    @Autowired
    WebDriver driver;
	//private Paciente user;	
	@Autowired
//	private PacienteRepository usersRepo;
	private OkHttpClient client;
	private Request request; 
    
    

	
	@Given("^Abro un buscador y entro en la página de registro$")
	public void abro_un_buscador_y_entro_en_la_página_de_registro() {
	    try {
	    	
		    System.setProperty("webdriver.gecko.driver", "src/test/resources/HIS_E2/app_sanidad/geckodriver");					

		    DesiredCapabilities dc = new DesiredCapabilities();
		    dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		    driver = new FirefoxDriver();		
		    driver.manage().window().maximize();
		    
	    driver.get("https://esanidad.herokuapp.com/register");
	    }catch(Exception e) {
	    	driver.quit();
	    	fail("Can't connect to application");
	    }
	    try {
	    driver.findElement(By.name("btnRegister")).click();
	    }catch(Exception e) {
	    	fail("Can't find Register button");
	    }
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    String new_url = driver.getCurrentUrl();
	    assertTrue(new_url.equals("https://esanidad.herokuapp.com/register")); 
		
	}
	@When("^Relleno todos los campos Nombre (.*),Apellidos (.*) DNI (.*) NumeroSS (.*)  Password (.*), Repetir_password (.*)$")
	public void relleno_todos_los_campos_Nombre_Apellidos_DNI_NumeroSS_Password_Repetir_password(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
		try {
		       driver.findElement(By.name("text-nombre")).sendKeys(arg1);							
		       driver.findElement(By.name("text-apellidos")).sendKeys(arg2);
		       driver.findElement(By.name("text-DNI")).sendKeys(arg3);							
		       driver.findElement(By.name("text-numeroSS")).sendKeys(arg4);
		       driver.findElement(By.name("text-password")).sendKeys(arg5);							
		       driver.findElement(By.name("text-repetir_password")).sendKeys(arg6);
		       
		       
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los campos");
		}
		try {
			 driver.findElement(By.name("btnRegistrar")).click();
		}catch(Exception e) {
			fail("Can't find Register button");
		
	}
	}
	@Then("^Recibo un mensaje de registro correcto$")
	public void recibo_un_mensaje_de_registro_correcto() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	

	
	
	@Given("^los datos de una persona$")
	public void los_datos_de_una_persona() {
		/*
		try {
			
	user.setNombre("Antonio");
		user.setApellidos("Pulido Hernandez")
		user.setDni("94898879A");
		user.setContrs("1111");
		user.setNumeroSS(1234);
		}catch(Exception e) {
			fail("Imposible crear usuario");
			
		}
		*/
	    throw new PendingException();
	}

	@When("^inserto los datos en la base de datos$")
	public void inserto_los_datos_en_la_base_de_datos() {
//		usersRepo.save(player);
		throw new PendingException();
	}

	@Then("^se inserta correctamente en la base de datos$")
	public void se_inserta_correctamente_en_la_base_de_datos() {
//		Usuario player=usersRepo.findByDni(user.getDNI(), user.getPassword());
		
	    throw new PendingException();
	}
	@When("^Relleno algunos datos$")
	public void relleno_algunos_datos() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^ClienteHttp$")
	public void script_en_postman() {
		 client = new OkHttpClient();


	}

	@When("^Envío petición Post con todos los campos$")
	public void envío_petición_Post_con_todos_los_campos() {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni\":\"05726690N\",\"nombre\":\"Antonio\",\"apellidos\": \"Pulido Hernández\",\"pass\":\"1234\",\"numSS\":\"123456789012\"}");
		 request = new Request.Builder()
		  .url("http://esanidad.herokuapp.com/register")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "e6222eed-1777-47bf-aa4f-f5432ef45ba2")
		  .build();
	}

	@Then("^Recibo una respuesta satisfactoria$")
	public void recibo_una_respuesta_Okey() {
		try {
			Response response = client.newCall(request).execute();
			if(!response.isSuccessful()) {
				fail("Respuesta fallida");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Error recibiendo la respuesta");
		}
	}

}
