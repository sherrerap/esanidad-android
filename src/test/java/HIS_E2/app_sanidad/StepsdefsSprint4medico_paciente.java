package HIS_E2.app_sanidad;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
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
import org.springframework.test.context.TestContextManager;

import HIS_E2.app_sanidad.controller.Manager;
import HIS_E2.app_sanidad.model.Cifrador;
import HIS_E2.app_sanidad.model.Especialidad;
import HIS_E2.app_sanidad.model.Medico;
import HIS_E2.app_sanidad.model.PacienteMedico;
import HIS_E2.app_sanidad.repositories.MedicoRepository;
import HIS_E2.app_sanidad.repositories.PacienteMedicoRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StepsdefsSprint4medico_paciente extends JunitTests2 {
	private WebDriver driver;
	OkHttpClient client;
	Request request;
	private String dni_medico;
	private String dni_paciente;
	private PacienteMedico  pacienteMedico;
	@Autowired
	private PacienteMedicoRepository pacienteMedicoRepo;
	@Autowired
	private MedicoRepository medicoRepo;
	private List<PacienteMedico> pacienteMedicos;
	
	
	
	@Given("^Tengo dni-user \"([^\"]*)\", dni-medico \"([^\"]*)\", Response \"([^\"]*)\"$")
	public void tengo_dni_user_dni_medico_Response(String arg1, String arg2, String arg3) {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e) {
			e.getMessage();
		}
		dni_paciente = arg1;
		dni_medico = arg2;
	}

	@When("^creo la relacion \"([^\"]*)\"$")
	public void creo_la_relacion(String arg1) {
		try {
			pacienteMedico = Manager.get().crearMedicoPaciente(dni_paciente,dni_medico);
		     if(arg1.equals("Error")){
		    	 fail("debería haber un error al insertar (Borrar de la base de datos)");
		     }
			} catch( Exception e) {
				if(!arg1.equals("Error")) {
					fail("Debería haberse creado la relación Médico-Paciente");
				}
			}
	}

	@Then("^la relacion ha sido guardada dni-user \"([^\"]*)\", dni-medico \"([^\"]*)\", Response \"([^\"]*)\"$")
	public void la_relacion_ha_sido_guardada_dni_user_dni_medico_Response(String arg1, String arg2, String arg3) {
		if(arg3.equals("OK")) {
			Medico medico = null;
			try {
				medico = medicoRepo.findByDni(Cifrador.cifrar(arg2));
				String especialidad = medico.getIdEspecialidad();
				PacienteMedico pacienteMedico = pacienteMedicoRepo.findCustomMedico(arg1, especialidad);
				assertNotNull(pacienteMedico);
			} catch (Exception e) {
				fail("debería poder encontrarse la relación");
			}
				
			}
		
	}

	@Then("^borro la relacion dni-user \"([^\"]*)\", dni-medico \"([^\"]*)\", Response \"([^\"]*)\"$")
	public void borro_la_relacion_dni_user_dni_medico_Response(String arg1, String arg2, String arg3) {
		if(arg3.equals("OK")) {
			try {
			PacienteMedico pacienteMedico = Manager.get().eliminarPacienteMedico(arg1,arg2);
			assertNotNull(pacienteMedico);
			}catch(Exception e) {
				
			}
		}
	}
	@Given("^ClienteHttpMedicoPaciente$")
	public void clientehttpmedicopaciente() {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e) {
		}
		client = new OkHttpClient();

	}

	@When("^Envio peticion crear MedicoPaciente dni-user \"([^\"]*)\", dni-medico \"([^\"]*)\", Response \"([^\"]*)\"$")
	public void envio_peticion_crear_MedicoPaciente_dni_user_dni_medico_Response(String arg1, String arg2, String arg3) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dniPaciente\":\""+arg1+"\",\"dniMedico\":\""+arg2+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/crearMedicoPaciente")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "af9231cc-f85a-4fbc-a63a-3c3ea6a900a6")
		  .build();
	}

	@Then("^Recibo una respuesta  dni-user \"([^\"]*)\", dni-medico \"([^\"]*)\", Response \"([^\"]*)\"$")
	public void recibo_una_respuesta_dni_user_dni_medico_Response(String arg1, String arg2, String arg3) {
		try {
			Response response = client.newCall(request).execute();
			String prueba= response.body().string();
			JSONObject jsonObject = new JSONObject(prueba);
			if(arg1.equals("OK")) {
				if(jsonObject.get("type").equals("error")) {
					fail("Respuesta fallida pero debería ser correcta");
				}
			}else if(arg1.equals("Error")){
				if(!jsonObject.get("type").equals("error")) {
					fail("Respuesta debería ser fallida pero es correcta");
				}
			}
		} catch (Exception e) {
			fail("Error recibiendo la respuesta");
		}
	}


	@When("^Envio peticion eliminar relacion dni-user \"([^\"]*)\", dni-medico \"([^\"]*)\", Response \"([^\"]*)\"$")
	public void envio_peticion_eliminar_relacion_dni_user_dni_medico_Response(String arg1, String arg2, String arg3) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dniPaciente\":\""+arg1+"\",\"dniMedico\":\""+arg2+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/eliminarPacienteMedico")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "af9231cc-f85a-4fbc-a63a-3c3ea6a900a6")
		  .build();
	}

	@When("^Pido la lista de relaciones MedicoPaciente$")
	public void pido_la_lista_de_relaciones_MedicoPaciente() {
	    throw new PendingException();
	}

	@Then("^Recibo la Lista MedicoPaciente$")
	public void recibo_la_Lista_MedicoPaciente() {
		try {
			Response response = client.newCall(request).execute();
			String prueba= response.body().string();
			JSONObject jsonObject = new JSONObject(prueba);
				if(jsonObject.get("type").equals("error")) {
					fail("Respuesta fallida pero debería ser correcta");
				}
		} catch (Exception e) {
			fail("Error recibiendo la respuesta");
		}
	}
	@Then("^la relacion ha sido borrada \"([^\"]*)\", dni-medico \"([^\"]*)\", Response \"([^\"]*)\"$")
	public void la_relacion_ha_sido_borrada_dni_medico_Response(String arg1, String arg2, String arg3) {
		   if(arg3.equals("OK")) {
				try {
					Medico medico = medicoRepo.findByDni(Cifrador.cifrar(arg2));
					String especialidad = medico.getIdEspecialidad();
					PacienteMedico pacienteMedico = pacienteMedicoRepo.findCustomMedico(arg1, especialidad);
					assertNull(pacienteMedico);
				} catch (Exception e) {
					fail("debería poder encontrarse la relación");
				}
			 
		   }
	}

	@Then("^Tengo la Lista MedicoPaciente$")
	public void tengo_la_Lista_MedicoPaciente() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
	@When("^hago peticion  MedicoPaciente$")
	public void hago_peticion_MedicoPaciente() {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni_paciente\":\"\",\"dni_medico\":\"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/crearPacienteMedico")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "af9231cc-f85a-4fbc-a63a-3c3ea6a900a6")
		  .build();
	}
	
	
	@Given("^Entro en la vista del administrador dni \"([^\"]*)\" contraseña \"([^\"]*)\"$")
	public void entro_en_la_vista_del_administrador_dni_contraseña(String arg1, String arg2) {
		try {
		       driver.findElement(By.name("username")).sendKeys(arg1);							
		       driver.findElement(By.name("password")).sendKeys(arg2);
				 driver.findElement(By.name("btnLogin")).click();
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los username o paswword");
		}
	}
	
	@Given("^Abroo Firefox y entro en la aplicacion$")
	public void abroo_Firefox_y_entro_en_la_aplicacion() {
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
		    
	    driver.get("https://esanidad.herokuapp.com");
	    }catch(Exception e) {
	    	driver.quit();
	    	fail("Can't connect to application");
	    }
	    driver.quit(); //eliminarlo cuando se ejécuten los tests
	}

	@When("^presiono boton crear relacion medico paciente$")
	public void presiono_boton_crear_relacion_medico_paciente() {
		try {
			driver.findElement(By.name("href_crearRelacion")).click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}catch(Exception e) {
			fail("deberia poder entrar a la vista para crear la relacion");
		}
	}
	@When("^presiono boton eliminar relacion medico paciente$")
	public void presiono_boton_eliminar_relacion_medico_paciente() {
		try {
			driver.findElement(By.name("href_eliminarRelacion")).click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}catch(Exception e) {
			fail("deberia poder entrar a la vista para crear la relacion");
		}
	}

	@Then("^creo relacion medico paciente dni-user \"([^\"]*)\" \"([^\"]*)\"$")
	public void creo_relacion_medico_paciente_dni_user(String arg1, String arg2) {
		   driver.findElement(By.name("dni-paciente")).sendKeys(arg1);							
	       driver.findElement(By.name("dni-medico")).sendKeys(arg2);  
	       try {
	    	   driver.findElement(By.name("Btn-crearRelacion")).click();
				   Alert alert = driver.switchTo().alert();
			        String alertText = alert.getText();
			        alert.accept();
			} catch (UnhandledAlertException f) {

			  } catch (NoAlertPresentException e) {
				  driver.quit();
				  if(arg1.equals("error")){
					  fail("Debería haber una alerta de error");
				  }
			        
			}catch(Exception e) {
				driver.quit();
				fail("No se puede encontrar boton de crear especialidad");
			
		}
	}

	@Then("^elimino relacion medico paciente dni-user \"([^\"]*)\" \"([^\"]*)\"$")
	public void elimino_relacion_medico_paciente_dni_user(String arg1, String arg2) {
		   driver.findElement(By.name("dni-paciente")).sendKeys(arg1);							
	       driver.findElement(By.name("dni-medico")).sendKeys(arg2);  
	       try {
	    	   driver.findElement(By.name("Btn-eliminarRelacion")).click();
				   Alert alert = driver.switchTo().alert();
			        String alertText = alert.getText();
			        alert.accept();
			} catch (UnhandledAlertException f) {

			  } catch (NoAlertPresentException e) {
				  driver.quit();
				  if(arg1.equals("error")){
					  fail("Debería haber una alerta de error");
				  }
			        
			}catch(Exception e) {
				driver.quit();
				fail("No se puede encontrar boton de crear especialidad");
			
		}
	}

}

