package HIS_E2.app_sanidad;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestContextManager;

import HIS_E2.app_sanidad.controller.Manager;
import HIS_E2.app_sanidad.model.Cifrador;
import HIS_E2.app_sanidad.model.Especialidad;
import HIS_E2.app_sanidad.model.Medico;
import HIS_E2.app_sanidad.model.Usuario;
import HIS_E2.app_sanidad.repositories.MedicoRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StepsdefsSprint4adminMedicos extends JunitTests2{
	private WebDriver driver;
	OkHttpClient client;
	Request request;
	private String medico_dni;
	private String medico_especialidad;
	private Medico medico;
	private List<Medico> medicosDNIs;
	@Autowired
	private MedicoRepository medicoRepo;
	private Usuario user_admin = new Usuario();
	
	
	
	
	
	
	
	
	
	
	@Given("^Tengo de un medico dni \"([^\"]*)\", especialidad \"([^\"]*)\"$")
	public void tengo_de_un_medico_dni_especialidad(String arg1, String arg2) {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e) {
			e.getMessage();
		}
		medico_dni = arg1;
		medico_especialidad = arg2;
	}

	@When("^creo el medico \"([^\"]*)\"$")
	public void creo_el_medico(String arg1) {
		try {
			
			
			    medico = Manager.get().crearMedico(medico_dni,medico_especialidad);
				} catch( Exception e) {
					if(!arg1.equals("Error")) {
						fail("Debería haberse creado el médico");
					}
				
				}
		
	}

	@Then("^el medico ha sido guardado correctamente dni \"([^\"]*)\", especialidad \"([^\"]*)\", response \"([^\"]*)\"$")
	public void el_medico_ha_sido_guardado_correctamente_dni_especialidad_response(String arg1, String arg2, String arg3) {
		if(arg3.equals("OK")) {
			try {
				medico = medicoRepo.findByDni(Cifrador.cifrar(arg1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertNotNull(medico);
		}
			
	}

	@Then("^borro el medico dni \"([^\"]*)\", especialidad \"([^\"]*)\", response \"([^\"]*)\"$")
	public void borro_el_medico_dni_especialidad_response(String arg1, String arg2, String arg3) {
		if(arg3.equals("OK")) {
			try {
				Medico medico = Manager.get().eliminarMedico(arg1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Medico medico_borrado = null;
			try {
				medico_borrado = medicoRepo.findByDni(Cifrador.cifrar(arg1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertNull(medico_borrado);
			
		}
	}
	
	
	@Given("^ClienteHttpCrearMedico$")
	public void clientehttpcrearmedico() {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e) {
		}
		client = new OkHttpClient();
	}

	@When("^Envio peticion crear medico dni \"([^\"]*)\",especialidad \"([^\"]*)\", response \"([^\"]*)\"$")
	public void envio_peticion_crear_medico_dni_especialidad_response(String arg1, String arg2, String arg3) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni\":\""+arg1+"\",\"especialidad\":\""+arg2+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/crearMedico")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "774c3406-0328-4313-88f8-56ac75e600a2")
		  .build();
	}
	
	@Then("^Recibo una respuesta response \"([^\"]*)\"$")
	public void recibo_una_respuesta_response(String arg1) {
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
	
	
	@Then("^relleno los campos de creacion medico dni\"([^\"]*)\" especialidad \"([^\"]*)\"$")
	public void relleno_los_campos_de_creacion_medico_dni_especialidad(String arg1, String arg2) {
		try {
			driver.findElement(By.name("href_crearMedico")).click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		       driver.findElement(By.name("dni")).sendKeys(arg1);							
		       driver.findElement(By.name("especialidad")).sendKeys(arg2);			
		       }catch(Exception e) {
				driver.quit();
				fail("no se encuentran los campos de nombre duración o boton crear especialidad");
			}
	}

	@When("^Envio peticion de crear medicco dni \"([^\"]*)\" , especlialidad \"([^\"]*)\", response \"([^\"]*)\"$")
	public void envio_peticion_de_crear_medicco_dni_especlialidad_response(String arg1, String arg2, String arg3) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni\":\""+arg1+"\",\"especialidad\":\""+arg2+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/crearMedico")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "907529fd-13c9-436d-bd25-a8af5dbe6492")
		  .build();
	}

	@Then("^elmedico ha sido borrado correctamente dni \"([^\"]*)\", especialdiad \"([^\"]*)\" response \"([^\"]*)\"$")
	public void elmedico_ha_sido_borrado_correctamente_dni_especialdiad_response(String arg1, String arg2, String arg3) {
		   if(arg3.equals("OK")) {
			   Medico medico = medicoRepo.findByDni(arg1);
			   
			 assertNotNull(medico);
			 
		   }
		
	}
	@Given("^usuario administrador \"([^\"]*)\"$")
	public void usuario_administrador(String arg1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^Pido lista de DNIs manager$")
	public void pido_lista_de_DNIs_manager() {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			
			medicosDNIs = Manager.get().listaMedicos();
				} catch( Exception e) {
			}
				
	}

	



	@When("^pido lista de médicos web$")
	public void pido_lista_de_médicos_web() {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni\":\"05726690N\",\"nombre\":\"Hector Cespedes\",\"especialidad\":\"Podología\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/listaMedicos")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "37ebd8d3-96eb-473e-b143-4ac8b89aabd0")
		  .build();
	}


	@Then("^recibo una lista de DNIs \"([^\"]*)\"$")
	public void recibo_una_lista_de_DNIs(String arg1) {
		assertNotNull(medicosDNIs);
	}
	
	
	@Given("^Entroo en la vista del gestor dni\"([^\"]*)\" contraseña \"([^\"]*)\"$")
	public void entro_en_la_vista_del_gestor_dni_contraseña(String arg1, String arg2) {
		try {
		       driver.findElement(By.name("username")).sendKeys(arg1);							
		       driver.findElement(By.name("password")).sendKeys(arg2);
				 driver.findElement(By.name("btnCrearMedico")).click();
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los username o paswword");
		}
	}
	
	@Then("^relleno los campos de eliminacion medico dni\"([^\"]*)\" especialidad \"([^\"]*)\", response \"([^\"]*)\"$")
	public void relleno_los_campos_de_eliminacion_medico_dni_especialidad_response(String arg1, String arg2, String arg3) {
		try {
			driver.findElement(By.name("href_EliminarMedico")).click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		       driver.findElement(By.name("dni")).sendKeys(arg1);								
		       }catch(Exception e) {
				driver.quit();
				fail("no se encuentran los campos de nombre duración o boton crear especialidad");
			}
	}

	@Then("^el medico  ha sido borrado correctamente dni \"([^\"]*)\", especialidad \"([^\"]*)\", response \"([^\"]*)\"$")
	public void el_medico_ha_sido_borrado_correctamente_dni_especialidad_response(String arg1, String arg2, String arg3) {
		   if(arg3.equals("OK")) {
			   Medico medico = medicoRepo.findByDni(arg1);
			   
			 assertNotNull(medico);
			 
		   }
	}

	@When("^Envio peticion de eliminar medico dni \"([^\"]*)\" , especlialidad \"([^\"]*)\", response \"([^\"]*)\"$")
	public void envio_peticion_de_eliminar_medico_dni_especlialidad_response(String arg1, String arg2, String arg3) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni\":\""+arg1+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/eliminarMedico")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "907529fd-13c9-436d-bd25-a8af5dbe6492")
		  .build();
	}
}
