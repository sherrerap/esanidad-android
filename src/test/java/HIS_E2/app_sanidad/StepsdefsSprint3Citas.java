package HIS_E2.app_sanidad;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.ParseException;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestContextManager;

import HIS_E2.app_sanidad.controller.Manager;
import HIS_E2.app_sanidad.model.Cita;
import HIS_E2.app_sanidad.model.Paciente;
import HIS_E2.app_sanidad.model.Usuario;
import HIS_E2.app_sanidad.repositories.CitaRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StepsdefsSprint3Citas extends JunitTests2{
	private WebDriver driver;
	OkHttpClient client;
	Request request;
	private String pedirCita_dnipaciente;
	private String pedirCita_fecha;
	private String pedirCita_especialidad;
	private  Cita cita;
	private Cita cita_modificada;
	@Autowired CitaRepository citasRepo;
	private String cita_modificacionEspecialidad;
	private String cita_modificacionDniUser;
	private String cita_modificacionFechaAntigua;
	private String cita_modificacionFechaNueva;

	@Given("^Abro Firefox y entro en la aplicacion citas$")
	public void abro_Firefox_y_entro_en_la_aplicacion_citas() {
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

	@Given("^Me autentico como un usuario$")
	public void me_autentico_como_un_usuario() {
		try {
		       driver.findElement(By.name("username")).sendKeys("05726690N");							
		       driver.findElement(By.name("password")).sendKeys("Antonio123");
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

	@When("^Pido una cita dni-user \"([^\"]*)\", especialidad \"([^\"]*)\", fecha \"([^\"]*)\"$")
	public void pido_una_cita_dni_user_especialidad_fecha(String arg1, String arg2, String arg3) {
		try {
		       driver.findElement(By.name("txt-especialidad")).sendKeys(arg1);							
		       driver.findElement(By.name("txt-fecha")).sendKeys(arg2);
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los campos");
		}
		try {
			 driver.findElement(By.name("btnPedirCita")).click();
		}catch(Exception e) {
			fail("No se encuentra el boton de pedir cita");
		
	}
	}

	@Then("^Recibo una respuesta de cita \"([^\"]*)\"$")
	public void recibo_una_respuesta_de_cita(String arg1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^Borro la cita si ha sido insertada con exito \"([^\"]*)\", especialidad \"([^\"]*)\", fecha \"([^\"]*)\" Result \"([^\"]*)\"$")
	public void borro_la_cita_si_ha_sido_insertada_con_exito_especialidad_fecha_Result(String arg1, String arg2, String arg3, String arg4) {
		if( arg4.equals("OK1")) {
			try {
				Manager.get().eliminarCitas(arg1, arg3, arg2);
			} catch (ParseException e) {
				fail("Deberia poder borrarse la cita insertada correctamente");
				e.printStackTrace();
			}
		}

	}
	
	
	
	
	@Given("^ClienteHttpPedirCita$")
	public void clientehttppedircita() {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
//			Manager.get().crearMedicoPaciente("05726693S", "nombre", "apellidos", "Antonio1234", "Cabecera", "05726690N");
		} catch (Exception e1) {
		}
		client = new OkHttpClient();


	}

	@When("^Envío petición Post con todos los campos de pedir cita dni-user \"([^\"]*)\", especialidad \"([^\"]*)\" , fecha \"([^\"]*)\"$")
	public void envío_petición_Post_con_todos_los_campos_de_pedir_cita_dni_user_especialidad_fecha(String arg1, String arg2, String arg3) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni-user\":\""+arg1+"\",\"especialidad\":\""+arg2+"\",\"fecha\":\""+arg3+"\"}");
		 
		 request = new Request.Builder()
				  .url("https://esanidad.herokuapp.com/pedirCita")
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

	@Then("^Recibo una respuesta de cita Result \"([^\"]*)\" dni-user \"([^\"]*)\", especialidad \"([^\"]*)\" , fecha \"([^\"]*)\"$")
	public void recibo_una_respuesta_de_cita_Result_dni_user_especialidad_fecha(String arg1, String arg2, String arg3, String arg4) {
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
					Manager.get().eliminarCitas(arg2, arg4, arg3);
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
	
	
	@Given("^Una cita con todos los campos dni-user \"([^\"]*)\" , especialidad \"([^\"]*)\", fecha \"([^\"]*)\"$")
	public void una_cita_con_todos_los_campos_dni_user_especialidad_fecha(String arg1, String arg2, String arg3) {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e1) {
		}
		 pedirCita_dnipaciente = arg1;
		 pedirCita_fecha = arg3;
		 pedirCita_especialidad = arg2;
		 
		 /**
		  * Precondiciones del tests de inserción sin solapamiento
		  * 
		  */
			try {
	//			Manager.get().crearMedicoPaciente("05726693S", "nombre", "apellidos", "Antonio1234", "Cabecera", "05726691J");
			} catch (Exception e1) {
			}
			try {
	//			Manager.get().crearMedicoPaciente("03879902D", "nombre", "apellidos", "Antonio1234", "Cabecera", "05726692Z");
			} catch (Exception e1) {
			}
			try {
				
				
			     cita =Manager.get().pedirCita("05726690N","10/12/2019 18:30","Cabecera"); 
				} catch( Exception e) {
					if(!arg1.contentEquals("Error")) {
						fail("Register should work here");
					}
				
				}
		
	}

	@When("^pido la cita \"([^\"]*)\"$")
	public void pido_la_cita(String arg1) {
		/**
		try {
			Manager.get().crearEspecialidad("Oncología", 15);
			Manager.get().crearEspecialidad("Podología", 15);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		try {
			Manager.get().crearMedicoPaciente("287678890L", "nombre", "apellidos", "Antonio1234", "Oncología", "05726690N");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		try {
			Manager.get().crearMedicoPaciente("287678891C", "nombre", "apellidos", "Antonio1234", "Podología", "05726690N");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		
**/
		
		try {
			
			
		     cita = Manager.get().pedirCita(pedirCita_dnipaciente,pedirCita_fecha,pedirCita_especialidad); 
			} catch( Exception e) {
				if(!arg1.contentEquals("Error")) {
					fail("Register should work here");
				}
			
			}
	}

	@Then("^Se guarda correctamente la cita dni-user \"([^\"]*)\" , especialidad \"([^\"]*)\", fecha \"([^\"]*)\" Result \"([^\"]*)\"$")
	public void se_guarda_correctamente_la_cita_dni_user_especialidad_fecha_Result(String arg1, String arg2, String arg3, String arg4) {
		if(arg4.equals("OK")) {
			
			assertNotNull(cita);
			
		}
		
			
	
	}
	
	@When("^Modifico una cita dni-user \"([^\"]*)\", especialidad \"([^\"]*)\", fecha \"([^\"]*)\",nueva fecha \"([^\"]*)\",$")
	public void modifico_una_cita_dni_user_especialidad_fecha_nueva_fecha(String arg1, String arg2, String arg3, String arg4) {
		try {
			 driver.findElement(By.name("btnModificarCita")).click();
		}catch(Exception e) {
			fail("No se encuentra el boton de pedir cita");
		
	}
		
		try {
		       driver.findElement(By.name("especialidad")).sendKeys(arg1);							
		       driver.findElement(By.name("fecha")).sendKeys(arg2);
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los campos");
		}
		try {
			 driver.findElement(By.name("btnModificarCita")).click();
		}catch(Exception e) {
			fail("No se encuentra el boton de pedir cita");
		
	}
		
	}

	@Then("^Recibo una respuesta de modificación cita \"([^\"]*)\"$")
	public void recibo_una_respuesta_de_modificación_cita(String arg1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}


	
	@Given("^Una modificaciónde cita con todos los campos dni-user \"([^\"]*)\" , especialidad \"([^\"]*)\", fecha \"([^\"]*)\", nueva fecha \"([^\"]*)\"$")
	public void una_modificaciónde_cita_con_todos_los_campos_dni_user_especialidad_fecha_nueva_fecha(String arg1, String arg2, String arg3, String arg4) {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cita_modificacionEspecialidad=arg2;
		cita_modificacionDniUser=arg1;
		cita_modificacionFechaAntigua=arg3;
		cita_modificacionFechaNueva=arg4;
        pedirCita_dnipaciente = arg1;
        pedirCita_fecha = arg3;
        pedirCita_especialidad =arg2;
	}


	

@When("^modifico la cita \"([^\"]*)\"$")
public void modifico_la_cita(String arg1) {
	try {
		
		cita_modificada = Manager.get().modificarCita(cita_modificacionDniUser,cita_modificacionEspecialidad,cita_modificacionFechaAntigua,cita_modificacionFechaNueva); 
			} catch( Exception e) {
				if(!arg1.contentEquals("Error")) {
					fail("Register should work here");
				}
			
			}
}

@Then("^Se modifica  correctamente la cita dni-user \"([^\"]*)\" , especialidad \"([^\"]*)\", fecha \"([^\"]*)\" Result \"([^\"]*)\"$")
public void se_modifica_correctamente_la_cita_dni_user_especialidad_fecha_Result(String arg1, String arg2, String arg3, String arg4) {
	if(arg4.equals("OK")) {
		
		assertNotNull(cita_modificada);
		
	}
}



@When("^Envío petición Post con todos los campos de modificar cita dni-user \"([^\"]*)\", especialidad \"([^\"]*)\" , fecha \"([^\"]*)\", fecha nueva \"([^\"]*)\"$")
public void envío_petición_Post_con_todos_los_campos_de_modificar_cita_dni_user_especialidad_fecha_fecha_nueva(String arg1, String arg2, String arg3, String arg4) {
	MediaType mediaType = MediaType.parse("application/json");
	RequestBody body = RequestBody.create(mediaType, "{\"dniPaciente\":\""+arg1+"\",\"especialidad\":\""+arg2+"\",\"fechaActual\": \""+arg3+"\",\"fechaModificar\":\""+arg4+"\"}");
	 
	 request = new Request.Builder()
			  .url("https://esanidad.herokuapp.com/modificarCita")
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
@When("^Elimino una cita dni-user \"([^\"]*)\", especialidad \"([^\"]*)\", fecha \"([^\"]*)\"$")
public void elimino_una_cita_dni_user_especialidad_fecha(String arg1, String arg2, String arg3) {
	try {
	       driver.findElement(By.name("txt-especialidad")).sendKeys(arg1);							
	       driver.findElement(By.name("txt-fecha")).sendKeys(arg2);
	}catch(Exception e) {
		driver.quit();
		fail("No se encuentran los campos");
	}
	try {
		 driver.findElement(By.name("btnPedirCita")).click();
	}catch(Exception e) {
		fail("No se encuentra el boton de pedir cita");
	
}
}

@Then("^Recibo una respuesta de cita eliminada \"([^\"]*)\"$")
public void recibo_una_respuesta_de_cita_eliminada(String arg1) {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@When("^elimino la cita \"([^\"]*)\"$")
public void elimino_la_cita(String arg1) {
	try {
		
		
	    // cita =Manager.get().elminarCita(pedirCita_dnipaciente,pedirCita_fecha,pedirCita_especialidad); 
		} catch( Exception e) {
			if(!arg1.contentEquals("Error")) {
				fail("Debería poder ser eliminada está cita");
			}
		
		}
}

@Then("^Se elmina correctamente la cita dni-user \"([^\"]*)\" , especialidad \"([^\"]*)\", fecha \"([^\"]*)\" Result \"([^\"]*)\"$")
public void se_elmina_correctamente_la_cita_dni_user_especialidad_fecha_Result(String arg1, String arg2, String arg3, String arg4) {
	if(arg4.equals("OK")) {
		try {
			//Manager.get().getCita();
			//fail("Esta cita debería haber sido eliminada");
			
		}catch(Exception e) {
			
		}
		
		
		assertNotNull(cita);
		
	}
}

	@When("^Envío petición Post con todos los campos de eliminar cita dni-user \"([^\"]*)\", especialidad \"([^\"]*)\" , fecha \"([^\"]*)\"$")
	public void envío_petición_Post_con_todos_los_campos_de_eliminar_cita_dni_user_especialidad_fecha(String arg1, String arg2, String arg3) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni-user\":\""+arg1+"\",\"especialidad\":\""+arg2+"\",\"fecha\":\""+arg3+"\"}");
		 
		 request = new Request.Builder()
				  .url("https://esanidad.herokuapp.com/modificarCita")
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
	





}
