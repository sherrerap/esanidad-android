package HIS_E2.app_sanidad;

import static org.junit.Assert.assertEquals;
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
import HIS_E2.app_sanidad.model.Especialidad;
import HIS_E2.app_sanidad.model.Usuario;
import HIS_E2.app_sanidad.repositories.EspecialidadRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StepsdefsSprint4Especialidades extends JunitTests2{
	private WebDriver driver;
	OkHttpClient client;
	Request request;
	private String nombre_especialidad;
	private String duracion_especialidad;
	private String duracion_especialidad_mod;
	private String hora_inicio_especialidad;
	private String hora_inicio_especialidad_mod;
	private String hora_final_especialidad;
	private String hora_final_especialidad_mod;
	private String duracion_modificar_especialidad;
	@Autowired
	private EspecialidadRepository especialidadRepo;
	private List<Especialidad> lista_especialidades;
	private Usuario user_admin = new Usuario();
	private Especialidad especialidad;
	
	
	
	@Given("^Tengo nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inicio \"([^\"]*)\",hora final \"([^\"]*)\",duracion_mod \"([^\"]*)\", hora_inicio_mod \"([^\"]*)\", hora_final_mod \"([^\"]*)\"$")
	public void tengo_nombre_duracion_hora_inicio_hora_final_duracion_mod_hora_inicio_mod_hora_final_mod(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e) {
			e.getMessage();
		}
		nombre_especialidad = arg1;
	    duracion_especialidad = arg2;
	    hora_inicio_especialidad = arg3;
	    hora_final_especialidad = arg4;
	    duracion_especialidad_mod = arg5;
	    hora_inicio_especialidad_mod = arg6;
	    hora_final_especialidad_mod = arg7;
	}
	
	
	@Given("^Tengo nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inicio \"([^\"]*)\",hora final \"([^\"]*)\"$")
	public void tengo_nombre_duracion_hora_inicio_hora_final(String arg1, String arg2, String arg3, String arg4) {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e) {
			e.getMessage();
		}
		nombre_especialidad = arg1;
	    duracion_especialidad = arg2;
	    hora_inicio_especialidad = arg3;
	    hora_final_especialidad = arg4;

	}

	@When("^creo la especialidad \"([^\"]*)\"$")
	public void creo_la_especialidad(String arg1) {
		
		try {
			
			
		     especialidad = Manager.get().crearEspecialidad(nombre_especialidad, duracion_especialidad,hora_inicio_especialidad,hora_final_especialidad);
		     if(arg1.equals("Error")){
		    	 fail("debería haber un error al insertar (Borrar de la base de datos)");
		     }
			} catch( Exception e) {
				if(!arg1.equals("Error")) {
					fail("Debería haberse creado la especialidad");
				}
			
			}
		

	}

	@Then("^La especialidad ha sido guardada nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inico \"([^\"]*)\",hora final \"([^\"]*)\",response \"([^\"]*)\"$")
	public void la_especialidad_ha_sido_guardada_nombre_duracion_hora_inico_hora_final_response(String arg1, String arg2, String arg3, String arg4, String arg5) {
		

		if(arg5.equals("OK")) {
			Especialidad especialidad =especialidadRepo.findCustomEspecialidad(arg1);
			int duracion=0;
			try {
				 duracion = Integer.parseInt(arg2);
			}catch(Exception e) {
				fail("La duración no es un número");
			}
			
			if(especialidad.getDuracionCita()!=duracion || !especialidad.getNombreEspecialidad().equals(arg1)){
				fail("La especialidad insertada y la guardada no coinciden");
				
			}
		}
		
	}

	@Then("^borro la especialidad nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inico \"([^\"]*)\",hora final \"([^\"]*)\",response \"([^\"]*)\"$")
	public void borro_la_especialidad_nombre_duracion_hora_inico_hora_final_response(String arg1, String arg2, String arg3, String arg4, String arg5) {
		if(arg5.equals("OK")) {
			try {
			Especialidad especialidad_borrada = Manager.get().eliminarEspecialidad(arg1);
			assertNotNull(especialidad_borrada);
			}catch(Exception e) {
				
			}
		}
		
	}
	
	
	@Given("^ClienteHttpEspecialidad$")
	public void clientehttpespecialidad() {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e) {
		}
		client = new OkHttpClient();

	}

	@When("^Envio peticion crear especialidad nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inico \"([^\"]*)\",hora final \"([^\"]*)\",response \"([^\"]*)\"$")
	public void envio_peticion_crear_especialidad_nombre_duracion_hora_inico_hora_final_response(String arg1, String arg2, String arg3, String arg4, String arg5) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"nombreEspecialidad\":\""+arg1+"\",\"tiempoCita\":\""+arg2+"\",\"horaInicio\":\""+arg3+"\",\"horaFin\":\""+arg4+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/crearEspecialidad")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "af9231cc-f85a-4fbc-a63a-3c3ea6a900a6")
		  .build();
	}

	@Then("^Recibo una respuesta  nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inico \"([^\"]*)\",hora final \"([^\"]*)\",response \"([^\"]*)\"$")
	public void recibo_una_respuesta_nombre_duracion_hora_inico_hora_final_response(String arg1, String arg2, String arg3, String arg4, String arg5) {
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
	
	
	@Given("^Abroo Firefox y entro en la aplicacion citas$")
	public void abroo_Firefox_y_entro_en_la_aplicacion_citas() {
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
	    throw new PendingException();
	}
	
	
	
	@Given("^Entro en la vista del gestor dni\"([^\"]*)\" contraseña \"([^\"]*)\"$")
	public void entro_en_la_vista_del_gestor_dni_contraseña(String arg1, String arg2) {
		try {
		       driver.findElement(By.name("username")).sendKeys(arg1);							
		       driver.findElement(By.name("password")).sendKeys(arg2);
				 driver.findElement(By.name("btnLogin")).click();
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los username o paswword");
		}
	}

	@When("^relleno los campos nombre nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inico \"([^\"]*)\",hora final \"([^\"]*)\"$")
	public void relleno_los_campos_nombre_nombre_duracion_hora_inico_hora_final(String arg1, String arg2, String arg3, String arg4) {
		try {
		driver.findElement(By.name("href_crearEspecialidad")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	       driver.findElement(By.name("nombre")).sendKeys(arg1);							
	       driver.findElement(By.name("duracion")).sendKeys(arg2);
	       driver.findElement(By.name("hora_inicio")).sendKeys(arg3);							
	       driver.findElement(By.name("hora_fin")).sendKeys(arg4);
		}catch(Exception e) {
			driver.quit();
			fail("no se encuentran los campos de nombre duración o boton crear especialidad");
		}
	}

	@Then("^Presiono el boton crear especialidad y recibo respuesta \"([^\"]*)\"$")
	public void presion_el_boton_crear_especialidad_y_recibo_respuesta(String arg1) {
		try {
			 driver.findElement(By.name("btnCrearEspecialidad")).click();
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
	
	
	@Then("^la especialidad ha sido borrada correctamente nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inico \"([^\"]*)\",hora final \"([^\"]*)\",response \"([^\"]*)\"$")
	public void la_especialidad_ha_sido_borrada_correctamente_nombre_duracion_hora_inico_hora_final_response(String arg1, String arg2, String arg3, String arg4, String arg5) {
	   if(arg5.equals("OK")) {
		   Especialidad especialidad = especialidadRepo.findCustomEspecialidad(arg1);
		   
		 assertNull(especialidad);
		 
	   }
	}
	@When("^Presiono el boton eliminar especialidad y recibo respuesta \"([^\"]*)\"$")
	public void presiono_el_boton_eliminar_especialidad_y_recibo_respuesta(String arg1) {
		try {
			 driver.findElement(By.name("btnEliminar0")).click();
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
			fail("No se puede encontrar boton de eliminar especialidad");
		
	}
	}

	@Then("^la especialidad se ha borrado nombre \"([^\"]*)\",duracion\"([^\"]*)\",response\"([^\"]*)\"$")
	public void la_especialidad_se_ha_borrado_nombre_duracion_response(String arg1, String arg2, String arg3) {
		
		Especialidad especialidades = especialidadRepo.findCustomEspecialidad(arg1);
		if(especialidades!=null) {
			 //especialidadRepo.deleteCustomespecialidad(arg1);
			fail("La especialidad Tal vez no se haya borrad bien, se intentará borrar de la base de datos");
		}
	}

	@When("^Envio peticion eliminar especialidad nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inico \"([^\"]*)\",hora final \"([^\"]*)\",response \"([^\"]*)\"$")
	public void envio_peticion_eliminar_especialidad_nombre_duracion_hora_inico_hora_final_response(String arg1, String arg2, String arg3, String arg4, String arg5) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"nombreEspecialidad\":\""+arg1+"\",\"tiempoCita\":\""+arg2+"\",\"horaInicio\":\""+arg3+"\",\"horaFin\":\""+arg4+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/eliminarEspecialidad")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "af9231cc-f85a-4fbc-a63a-3c3ea6a900a6")
		  .build();
	}
	
	
	@Given("^usuario admin \"([^\"]*)\", contrasenia \"([^\"]*)\"$")
	public void usuario_admin_contrasenia(String arg1, String arg2) {
		user_admin.setDni(arg1);
		user_admin.setContrs(arg2);
	}

	@When("^Pido la lista de especialidades \"([^\"]*)\"$")
	public void pido_la_lista_de_especialidades(String arg1) {
		try {
			
			
			lista_especialidades = Manager.get().consultarEspecialidades();
		     

			} catch( Exception e) {
				fail("no se puede visualizar la lista");
				}
			
			

	    
	}
	


	@Then("^Tengo una lista de especialidades$")
	public void tengo_una_lista_de_especialidades() {
		//assertNotNull(lista_especialidades);
	}
	
	@Then("^Recibo una respuesta lista de especialidades \"([^\"]*)\"$")
	public void recibo_una_respuesta_lista_de_especialidades(String arg1) {
	    if(arg1.equals("OK")) {
	    	assertNotNull(lista_especialidades);
	    }
	}

	@When("^Envio peticion recibir lista especialidades dni_admin \"([^\"]*)\"$")
	public void envio_peticion_recibir_lista_especialidades_dni_admin(String arg1) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni-admin\":\""+arg1+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/consultaEspecialidades")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "af9231cc-f85a-4fbc-a63a-3c3ea6a900a6")
		  .build();
	}

	@Then("^Recibo Respuesta lista especialidades \"([^\"]*)\"$")
	public void recibo_Respuesta_lista_especialidades(String arg1) {
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
					try {
					
					}catch(Exception e) {	
						fail("Respuesta debería ser fallida pero es correcta y no se ha podido eliminar");
				}
					fail("Respuesta debería ser fallida pero es correcta");
				}
			}
		} catch (Exception e) {
			fail("Error recibiendo la respuesta");
		}
	}

	@Given("^Tengo el nombre y la duracion de una especialidad \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
	public void tengo_el_nombre_y_la_duracion_de_una_especialidad(String arg1, String arg2, String arg3) {
		 nombre_especialidad = arg1;
		 duracion_especialidad = arg2;
		 duracion_modificar_especialidad = arg3;
	}



	@Then("^la especialidad ha sido modificada correctamente nombre \"([^\"]*)\", nueva duracion \"([^\"]*)\",\"([^\"]*)\"$")
	public void la_especialidad_ha_sido_modificada_correctamente_nombre_nueva_duracion(String arg1, String arg2, String arg3) {
		if(arg3.equals("OK")) {
			int duracion = 0;
			duracion = Integer.parseInt(arg2);
			especialidad = especialidadRepo.findCustomEspecialidad(arg1);
			if(especialidad.getDuracionCita()!= duracion) {
				fail("la especialidad no se ha modificado correctamente");
			}
		}
	    
	}
	
	@When("^Envio peticion modificar especialidad nombre\"([^\"]*)\",duracion\"([^\"]*)\",hora inicio \"([^\"]*)\",hora final \"([^\"]*)\",nueva duracion\"([^\"]*)\", response\"([^\"]*)\"$")
	public void envio_peticion_modificar_especialidad_nombre_duracion_hora_inicio_hora_final_nueva_duracion_response(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"nombreEspecialidad\":\""+arg1+"\",\"duracionOld\":\""+arg2+"\",\"horaInicioOld\":\""+arg3+"\",\"horaFinOld\":\""+arg4+"\",\"N_duracion\":\""+arg5+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/modificarEspecialidad")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "261c6c14-638b-4338-bce3-9e11efae04a9")
		  .build();
	}

	@Then("^Recibo una respuesta de modificacion nombre\"([^\"]*)\",duracion\"([^\"]*)\",hora inicio \"([^\"]*)\",hora final \"([^\"]*)\",nueva duracion\"([^\"]*)\", response\"([^\"]*)\"$")
	public void recibo_una_respuesta_de_modificacion_nombre_duracion_hora_inicio_hora_final_nueva_duracion_response(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
		try {
			Response response = client.newCall(request).execute();
			String prueba= response.body().string();
			JSONObject jsonObject = new JSONObject(prueba);
			if(arg6.equals("OK")) {
				if(jsonObject.get("type").equals("error")) {
					fail("Respuesta fallida pero debería ser correcta");
				}
			}else if(arg6.equals("Error")){
				if(!jsonObject.get("type").equals("error")) {
					fail("Respuesta debería ser fallida pero es correcta");
				}
			}
		} catch (Exception e) {
			fail("Error recibiendo la respuesta");
		}
	}
	@Then("^relleno los campos de modificacion duracion_mod \"([^\"]*)\",hora_inicio_mod \"([^\"]*)\", hora_final_mod \"([^\"]*)\"$")
	public void relleno_los_campos_de_modificacion_duracion_mod_hora_inicio_mod_hora_final_mod(String arg1, String arg2, String arg3) {
		try {
		       driver.findElement(By.name("duracion_mod")).sendKeys(arg1);							
		       driver.findElement(By.name("hora_inicio_mod")).sendKeys(arg2);
		       driver.findElement(By.name("hora_final_mod")).sendKeys(arg3);	
			 driver.findElement(By.name("btnModificarEspecialidad")).click();
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
	
	
	

	
	@When("^modifico la especialidad response \"([^\"]*)\"$")
	public void modifico_la_especialidad_response(String arg1) {
		
		try {
			
			
		     especialidad = Manager.get().modificarEspecialidad(nombre_especialidad, 
			duracion_especialidad,hora_inicio_especialidad,hora_final_especialidad,duracion_especialidad_mod,hora_inicio_especialidad_mod,hora_final_especialidad_mod);
		     

			} catch( Exception e) {
				if(arg1.equals("Error")) {
					borro_la_especialidad_nombre_duracion_hora_inico_hora_final_response(nombre_especialidad, duracion_especialidad, hora_inicio_especialidad, hora_final_especialidad, "OK");
					
				}
				if(!arg1.contentEquals("Error")) {
					fail("Debería haberse creado la especialidad");
				}
			
			}
	}
	
	@Then("^la especialidad ha sido modificada correctamente nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inicio \"([^\"]*)\",hora final \"([^\"]*)\",duracion_mod \"([^\"]*)\", hora_inicio_mod \"([^\"]*)\", hora_final_mod \"([^\"]*)\",response \"([^\"]*)\"$")
	public void la_especialidad_ha_sido_modificada_correctamente_nombre_duracion_hora_inicio_hora_final_duracion_mod_hora_inicio_mod_hora_final_mod_response(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8) {
		if(arg8.equals("OK")) {
			assertEquals(especialidad.getNombreEspecialidad(),arg1);
			assertEquals(especialidad.getDuracionCita(),arg5);
			//	assertEquals(especialidad.getHoraInicio,arg6);
		//	assertEquals(especialidad.getHoraFin(),arg7);

			
		}

	}
	
	@When("^Envio peticion modificar especialidad nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inicio \"([^\"]*)\",hora final \"([^\"]*)\",duracion_mod \"([^\"]*)\", hora_inicio_mod \"([^\"]*)\", hora_final_mod \"([^\"]*)\",response \"([^\"]*)\"$")
	public void envio_peticion_modificar_especialidad_nombre_duracion_hora_inicio_hora_final_duracion_mod_hora_inicio_mod_hora_final_mod_response(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8) {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"nombreEspecialidad\":\""+arg1+"\",\"duracionOld\":\""+arg2+"\",\"horaInicioOld\":\""+arg3+"\",\"horaFinOld\":\""+arg4+"\",\"duracionNew\":"
				+ "\""+arg5+"\",\"horaInicioNew\":\""+arg6+"\",\"horaFinNew\":\""+arg7+"\"}");
		 request = new Request.Builder()
		  .url("https://esanidad.herokuapp.com/modificarEspecialidad")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "af9231cc-f85a-4fbc-a63a-3c3ea6a900a6")
		  .build();
	}
	
	@Then("^Recibo una respuesta de modificacion nombre \"([^\"]*)\",duracion \"([^\"]*)\",hora inicio \"([^\"]*)\",hora final \"([^\"]*)\",duracion_mod \"([^\"]*)\", hora_inicio_mod \"([^\"]*)\", hora_final_mod \"([^\"]*)\",response \"([^\"]*)\"$")
	public void recibo_una_respuesta_de_modificacion_nombre_duracion_hora_inicio_hora_final_duracion_mod_hora_inicio_mod_hora_final_mod_response(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8) {
		try {
			Response response = client.newCall(request).execute();
			String prueba= response.body().string();
			JSONObject jsonObject = new JSONObject(prueba);
			if(arg8.equals("OK")) {
				if(jsonObject.get("type").equals("error")) {
					fail("Respuesta fallida pero debería ser correcta");
				}
			}else if(arg8.equals("Error")){
				if(!jsonObject.get("type").equals("error")) {
					fail("Respuesta debería ser fallida pero es correcta");
				}
				borro_la_especialidad_nombre_duracion_hora_inico_hora_final_response(nombre_especialidad, duracion_especialidad, hora_inicio_especialidad, hora_final_especialidad, "OK");
				
			}
		} catch (Exception e) {
			fail("Error recibiendo la respuesta");
		}
	}
	
	@When("^Presiono el boton modificar especialidad y recibo respuesta \"([^\"]*)\"$")
	public void presiono_el_boton_modificar_especialidad_y_recibo_respuesta(String arg1) {
		try {
			 driver.findElement(By.name("btnModificar0")).click();
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
			fail("No se puede encontrar boton de eliminar especialidad");
		
	}
	}

	@Then("^la especialidad ha sido modificada correctamente nombre\"([^\"]*)\",duracion\"([^\"]*)\",hora inicio \"([^\"]*)\",hora final \"([^\"]*)\",nueva duracion\"([^\"]*)\", response \"([^\"]*)\"$")
	public void la_especialidad_ha_sido_modificada_correctamente_nombre_duracion_hora_inicio_hora_final_nueva_duracion_response(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
		Especialidad especialidades = especialidadRepo.findCustomEspecialidad(arg1);

		if(especialidades !=null) {
			 //especialidadRepo.deleteCustomespecialidad(arg1);
			driver.quit();
			fail("La especialidad Tal vez no se haya borrad bien, se intentará borrar de la base de datos");
		}
	}


}
