package HIS_E2.app_sanidad;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringRunner;

import HIS_E2.app_sanidad.controller.Manager;
import HIS_E2.app_sanidad.model.Paciente;
import HIS_E2.app_sanidad.model.Usuario;
import HIS_E2.app_sanidad.repositories.PacienteRepository;
import HIS_E2.app_sanidad.repositories.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class StepsdefsSprint3Register extends JunitTests2{
	private WebDriver driver;
	OkHttpClient client;
	Request request;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PacienteRepository pacienteRepo;
	Paciente user2;
	Usuario user;
	
	
	@Given("^Abro Firefox y entro en la aplicacion$")
	public void abro_Firefox_y_entro_en_la_aplicacion() {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
	}

	@When("^Relleno los campos DNI \"([^\"]*)\", Nombre \"([^\"]*)\", Apellidos \"([^\"]*)\", Contraseña \"([^\"]*)\", Repetir_contraseña \"([^\"]*)\", NumeroSS \"([^\"]*)\"$")
	public void relleno_los_campos_DNI_Nombre_Apellidos_Contraseña_Repetir_contraseña_NumeroSS(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
		try {
		       driver.findElement(By.name("dni")).sendKeys(arg1);							
		       driver.findElement(By.name("name")).sendKeys(arg2);
		       driver.findElement(By.name("apellido")).sendKeys(arg3);							
		       driver.findElement(By.name("password")).sendKeys(arg4);
		       driver.findElement(By.name("confirm")).sendKeys(arg5);
		       driver.findElement(By.name("nss")).sendKeys(arg6);
		       
		}catch(Exception e) {
			fail("No se encuentran los campos");
		}

	}

	@Then("^Recibo respuesta \"([^\"]*)\"$")
	public void recibo_respuesta(String arg1) {
		try {
			 driver.findElement(By.name("btnRegistrar")).click();
			   Alert alert = driver.switchTo().alert();
		        String alertText = alert.getText();
		        System.out.println("Alert data: " + alertText);
		        alert.accept();
		} catch (UnhandledAlertException f) {

		  } catch (NoAlertPresentException e) {
		        
		}catch(Exception e) {
			fail("Can't find Register button");
		
	}
		if(arg1.equals("OK")) {
			String new_url = driver.getCurrentUrl();
			 assertTrue(new_url.equals("https://esanidad.herokuapp.com/")); 
		}else {
			String new_url = driver.getCurrentUrl();
			 assertTrue(new_url.equals("https://esanidad.herokuapp.com/register")); 
		}                               
		
		
	}
	
	@Then("^Borro usuario \"([^\"]*)\" Result \"([^\"]*)\"$")
	public void borro_usuario_Result(String arg1, String arg2) {
		
		try {
			userRepo.deleteById(arg1);
			pacienteRepo.deleteById(arg1);
			Usuario user4=userRepo.findByDni(arg1);
			assertNull(user4);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	@Given("^ClienteHttpRegistro$")
	public void clientehttpregistro() {
		client = new OkHttpClient();
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("^Envío petición Post con todos los campos de registro DNI \"([^\"]*)\", Nombre \"([^\"]*)\", Apellidos \"([^\"]*)\", Contraseña \"([^\"]*)\",NumeroSS \"([^\"]*)\"$")
	public void envío_petición_Post_con_todos_los_campos_de_registro_DNI_Nombre_Apellidos_Contraseña_NumeroSS(String arg1, String arg2, String arg3, String arg4, String arg5) {

		MediaType mediaType = MediaType.parse("application/json");
		String r="{\"dni\":\""+arg1+"\",\"nombre\":\""+arg2+"\",\"apellidos\":\""+arg3+"\",\"pass\":\""+arg4+"\",\"numSS\":\""+arg5+"\"}";
		RequestBody body = RequestBody.create(mediaType, "{\"dni\":\""+arg1+"\",\"nombre\":\""+arg2+"\",\"apellidos\":\""+arg3+"\",\"pass\":\""+arg4+"\",\"numSS\":\""+arg5+"\"}");
		 
		 request = new Request.Builder()
				  .url("https://esanidad.herokuapp.com/register")
				  .post(body)
				  .addHeader("Content-Type", "application/json")
				  .addHeader("User-Agent", "PostmanRuntime/7.19.0")
				  .addHeader("Accept", "*/*")
				  .addHeader("Cache-Control", "no-cache")
				  .addHeader("Postman-Token", "026c8d66-5ccb-453f-b1b4-c6f351f126ee,ca3db196-6148-4d81-a889-94d79002afe4")
				  .addHeader("Host", "esanidad.herokuapp.com")
				  .addHeader("Accept-Encoding", "gzip, deflate")
				  .addHeader("Content-Length", "84")
				  .addHeader("Connection", "keep-alive")
				  .addHeader("cache-control", "no-cache")
				  .build();

		
	}

	@Then("^Recibo una respuesta Result \"([^\"]*)\" DNI (.*)  de registro$")
	public void recibo_una_respuesta_Result_DNI_de_registro(String arg1, String arg2) {
		try {
			Response response = client.newCall(request).execute();
			System.out.println(response.toString());
			String prueba= response.body().string();
			JSONObject jsonObject = new JSONObject(prueba);
			
			
			if(arg1.equals("OK")) {
				if(jsonObject.get("type").equals("error")) {
					fail("Respuesta fallida pero debería ser correcta");
				}
			}else if(arg1.equals("Error")){
				if(!jsonObject.get("type").equals("error")) {
					try {
						userRepo.deleteById(arg2);
						pacienteRepo.deleteById(arg2);
					}catch(Exception e) {
						
					}
					fail("Respuesta debería ser fallida pero es correcta");
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Error recibiendo la respuesta");
		}
	}
	@Given("^Un usuario con todos los campos de registro DNI \"([^\"]*)\", Nombre \"([^\"]*)\", Apellidos \"([^\"]*)\", Contraseña \"([^\"]*)\",NumeroSS \"([^\"]*)\"$")
	public void un_usuario_con_todos_los_campos_de_registro_DNI_Nombre_Apellidos_Contraseña(String arg1, String arg2, String arg3, String arg4,String arg5) throws Exception {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 user2 = new Paciente(arg1, arg2, arg3, arg4, arg5);
		
	}

	@When("^se registra \"([^\"]*)\"$")
	public void se_registra(String arg1) {
		try {
			
	    Manager.get().register(user2.getDni(), user2.getNombre(), user2.getApellidos(), user2.getContrs(),user2.getNumeroSS(), -1);
		} catch( Exception e) {
			if(!arg1.contentEquals("Error")) {
				fail("Register should work here");
			}
		
		}
	}

	@Then("^Se guarda correctamente el nuevo usuario \"([^\"]*)\" Result \"([^\"]*)\"$")
	public void se_guarda_correctamente_el_nuevo_usuario_Result(String arg1, String arg2) {
		if(arg2.equals("OK")) {
	//		Paciente user=pacienteRepo.findByDni(arg1);
			Usuario user2=userRepo.findByDni(arg1);
			
			if(user2 ==null) {
				fail("user is not inserted");
			}
		}

	}


}