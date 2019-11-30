package HIS_E2.app_sanidad;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

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

public class StepsdefsSprint2Citas {
	private OkHttpClient client;
	private Request request;
	private OkHttpClient client1;
	private Request request1; 
	private WebDriver driver;	
	

	@Given("^ClienteHttpcita$")
	public void clientehttpcita() {
		client1 = new OkHttpClient();
	}

	@When("^Envío petición Post con todos los campos de cita$")
	public void envío_petición_Post_con_todos_los_campos_de_cita() {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni\":\"05726690N\",\"dni2\":\"05727690A\",\"fecha\": \"29/06/1998\",\"hora\":\"16:30\"}");
		 request1 = new Request.Builder()
		  .url("http://esanidad.herokuapp.com/getCitas")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "30638af0-cfb7-49b1-b26d-1427256e2aa3")
		  .build();
	}

	@Then("^Recibo una respuesta satisfactoria  de cita$")
	public void recibo_una_respuesta_satisfactoria_de_cita() {
		try {
			Response response = client1.newCall(request1).execute();
			if(!response.isSuccessful()) {
				fail("Respuesta fallida");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Error recibiendo la respuesta");
		}
	}

	@Given("^Una cita$")
	public void una_cita() {
		/**
	    Cita cita=new Cita();
	    cita.setId("123");
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
	    cita.setDate(dtf.format(localDate));
	    cita.setHora("16:40");
	    user.setNombre("Antonio");
		user.setApellidos("Pulido Hernandez")
		user.setDNI("94898879A");
		user.setContrasena("1111");
		user.setNumeroSS(1234);
	    cita.setPaciente(user);
	    
	    medico.setNombre("Antonio");
		medico.setApellidos("Pulido Hernandez")
		medico.setDNI("94898879A");
		medico.setContrasena("1111");
		medico.setNumeroSS(1234);
	    
	    cita.setMedico(medico)
	    */
	    throw new PendingException();
	}

	@When("^la cita se intenta guardar$")
	public void la_cita_se_intenta_guardar() {
		//citasRepo.save(cita);

	}

	@Then("^la cita ha sido guardada$")
	public void la_cita_ha_sido_guardada() {
		//Cita cita=citasRepo.findByID(cita.getId());
	    throw new PendingException();
	}

	@Given("^Un usuario conectado en la pagina de citas$")
	public void un_usuario_conectado_en_la_pagina_de_citas() {
		 try {
			    System.setProperty("webdriver.gecko.driver", "src/test/resources/HIS_E2/app_sanidad/geckodriver");					

			    DesiredCapabilities dc = new DesiredCapabilities();
			    dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			    driver = new FirefoxDriver();		
			    driver.manage().window().maximize();
			    
		    driver.get("http://esanidad.herokuapp.com");
		    }catch(Exception e) {
		    	driver.quit();
		    	fail("Can't connect to application");
		    }
		 try {
		       driver.findElement(By.name("username")).sendKeys("username12");							
		       driver.findElement(By.name("password")).sendKeys("password12");	
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los campos");
		}
		 driver.findElement(By.name("btnLogin")).click();
	       driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	       String new_url = driver.getCurrentUrl();
	       assertTrue(new_url.equals("https://esanidad.herokuapp.com/citas"));
	    
	}

	@When("^Relleno todos los campos Fecha \"([^\"]*)\" , hora \"([^\"]*)\" y especialidad \"([^\"]*)\"$")
	public void relleno_todos_los_campos_Fecha_hora_y_especialidad(String arg1, String arg2, String arg3) {
		driver.findElement(By.name("text-fecha")).sendKeys(arg1);							
	       driver.findElement(By.name("text-hora")).sendKeys(arg2);
	       driver.findElement(By.name("text-especialidad")).sendKeys(arg3);
	}

	@Then("^Recibo un mensaje de cita confirmada$")
	public void recibo_un_mensaje_de_cita_confirmada() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
	
	@Given("^Un medico$")
	public void un_medico() {
		client = new OkHttpClient();
	}

	@When("^envia peticion de visualizar citas$")
	public void envia_peticion_de_visualizar_citas() {
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"dni\":\"05726690N\",\"pass\":\"1234\"}");
		 request = new Request.Builder()
		  .url("http://esanidad.herokuapp.com/citas")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "5603d304-38ed-41da-9a5f-035b330c8d61")
		  .build();
	}

	@Then("^Se recibe una respuecta satisfactoria$")
	public void se_recibe_una_respuecta_satisfactoria() {
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
