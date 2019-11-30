package HIS_E2.app_sanidad;

import static org.junit.Assert.fail;

import java.io.IOException;

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

public class StepsdefsSprint2Login {
	//private Usuario user;	
	//private Usuario user1
	WebDriver driver;
	OkHttpClient client;
	Request request;
	
	@Given("^ClienteHttpLogin$")
	public void clientehttplogin() throws IOException {
		 client = new OkHttpClient();
	}

	@When("^Envío petición Post con todos los campos de autentificacion$")
	public void envío_petición_Post_con_todos_los_campos_de_autentificacion() {
		

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni\":\"05726690N\",\"contrasenia\":\"1234\"}");
		     request = new Request.Builder()
		  .url("http://esanidad.herokuapp.com/")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "411dfd2e-da9b-4d31-aecc-80b0410ede15")
		  .build();
		


	}
	@Given("^Un usuario$")
	public void un_usuario() {
		/*
		try {
			
	user.setNombre("Antonio");
		user.setApellidos("Pulido Hernandez")
		user.setDni("94898879A");
		user.setContrs("1111");
		}catch(Exception e) {
			fail("Imposible crear usuario");
			
		}
		*/
	    throw new PendingException();
	}

	@When("^el usuario se autentica$")
	public void el_usuario_se_autentica() {
        // user=usersRepo.findByDni(user.getDNI(), user.getPassword());
	    throw new PendingException();
	}

	@Then("^el sistema recoje la autenticacion$")
	public void el_sistema_recoje_la_autenticacion() {
		//assertNotNull(user)
	}

	

	@Then("^Recibo una respuesta satisfactoria  de autentificacion$")
	public void recibo_una_respuesta_satisfactoria_de_autentificacion() {

		try {
			Response response = client.newCall(request).execute();
			if(!response.isSuccessful()) {
				fail("Respuesta fallida");
			}
		} catch (Exception e) {
			fail("Error recibiendo la respuesta");
		}
	}



	@Given("^Abro un buscador y entro en la página de login$")
	public void abro_un_buscador_y_entro_en_la_página_de_login() {
		 try {
		    	
			    System.setProperty("webdriver.gecko.driver", "src/test/resources/HIS_E2/app_sanidad/geckodriver");					

			    DesiredCapabilities dc = new DesiredCapabilities();
			    dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			    driver = new FirefoxDriver();		
			    driver.manage().window().maximize();
			    
		    driver.get("http://esanidad.herokuapp.com");
		    }catch(Exception e) {
		    	driver.quit();
		    	fail("No se puede conectar a la aplicacion");
		    }
	}

	@When("^Relleno todos los campos dni \"([^\"]*)\" contrasenia \"([^\"]*)\"$")
	public void relleno_todos_los_campos_dni_contrasenia(String arg1, String arg2) {
		try {
		       driver.findElement(By.name("username")).sendKeys(arg1);							
		       driver.findElement(By.name("password")).sendKeys(arg2);
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los campos");
		}
		try {
			 driver.findElement(By.name("btnLogin")).click();
		}catch(Exception e) {
			fail("No se encuentra el boton de login");
		
	}
	}
	@Then("^Recibo un mensaje de autenticacion correcta$")
	public void recibo_un_mensaje_de_autenticacion_correcta() {
		 throw new PendingException();
	}



}
