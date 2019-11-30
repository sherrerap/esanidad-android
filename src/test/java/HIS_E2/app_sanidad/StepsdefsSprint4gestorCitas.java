package HIS_E2.app_sanidad;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.ParseException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.test.context.TestContextManager;

import HIS_E2.app_sanidad.controller.Manager;
import HIS_E2.app_sanidad.model.Cita;
import HIS_E2.app_sanidad.model.Especialidad;
import HIS_E2.app_sanidad.repositories.CitaRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepsdefsSprint4gestorCitas {
	private WebDriver driver;
	private CitaRepository citaRepo;
	
	@Given("^Abroo Firefox y entro en la aplicacion cita$")
	public void abroo_Firefox_y_entro_en_la_aplicacion_cita() {
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
	}

	@Given("^Me autentico como gestor dni \"([^\"]*)\" pwd \"([^\"]*)\"$")
	public void me_autentico_como_gestor_dni_pwd(String arg1, String arg2) {
		try {
		       driver.findElement(By.name("username")).sendKeys(arg1);							
		       driver.findElement(By.name("password")).sendKeys(arg2);
				 driver.findElement(By.name("btnLogin")).click();
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los username o paswword");
		}
	}

	@When("^presiono boton eliminar$")
	public void presiono_boton_eliminar() {
		try {
			 driver.findElement(By.name("btnEliminarCita")).click();
			   Alert alert = driver.switchTo().alert();
		        String alertText = alert.getText();
		        if (alertText.equals("Error: se ha procesado incorrectamente la petición de eliminación de la cita.")){
		        	fail("no debería ser error");
		        	
		        }
		        alert.accept();
		        
		} catch (UnhandledAlertException f) {

		  } catch (NoAlertPresentException e) {
			  driver.quit();
		        
		}catch(Exception e) {
			driver.quit();
			fail("No se puede encontrar boton de crear especialidad");
		
	}
	}

	@When("^Pido una cita como gestor dni-user \"([^\"]*)\", especialidad \"([^\"]*)\", fecha \"([^\"]*)\"$")
	public void pido_una_cita_como_gestor_dni_user_especialidad_fecha(String arg1, String arg2, String arg3) {
		/*
		try {
			String[] split = arg3.split(" ");
			driver.findElement(By.name("GCPedirCita")).click();
		       driver.findElement(By.name("dni")).sendKeys(arg1);							
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los campos");
		}*/
		driver.quit();

	}

	@Then("^Recibo una respuesta de gestor  cita \"([^\"]*)\"$")
	public void recibo_una_respuesta_de_gestor_cita(String arg1) {
		try {
			 driver.findElement(By.name("btnPedirCita")).click();
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

	@Then("^Borro la cita si ha sido insertada con exito \"([^\"]*)\", especialidad \"([^\"]*)\", fecha \"([^\"]*)\"$")
	public void borro_la_cita_si_ha_sido_insertada_con_exito_especialidad_fecha(String arg1, String arg2, String arg3) {
		if(arg3.equals("OK")) {
			try {
			 Manager.get().eliminarCitas(arg1, arg2, arg3);
			}catch(Exception e) {
				
			}
		}
	}
	@When("^modifico una cita como gestor dni-user \"([^\"]*)\", especialidad \"([^\"]*)\", fecha \"([^\"]*)\", fecha_mod \"([^\"]*)\"$")
	public void modifico_una_cita_como_gestor_dni_user_especialidad_fecha_fecha_mod(String arg1, String arg2, String arg3, String arg4) {
		try {
			 driver.findElement(By.name("BtnEliminarCita")).click();
			   Alert alert = driver.switchTo().alert();
		        String alertText = alert.getText();
		        if (alertText.equals("Error: se ha procesado incorrectamente la petición de eliminación de la cita.")){
		        	fail("no debería ser error");
		        	
		        }
		        alert.accept();
		        
		} catch (UnhandledAlertException f) {

		  } catch (NoAlertPresentException e) {
			  driver.quit();
		        
		}catch(Exception e) {
			driver.quit();
			fail("No se puede encontrar boton de crear especialidad");
		
	}
	}

	@Then("^Borro la cita si ha sido insertada con exito \"([^\"]*)\", especialidad \"([^\"]*)\", fecha \"([^\"]*)\", fecha_mod \"([^\"]*)\" Response \"([^\"]*)\"$")
	public void borro_la_cita_si_ha_sido_insertada_con_exito_especialidad_fecha_fecha_mod_Response(String arg1, String arg2, String arg3, String arg4, String arg5) {
		if( arg5.equals("OK")) {
			try {
				Manager.get().eliminarCitas(arg1, arg4, arg2);
			} catch (ParseException e) {
				fail("Deberia poder borrarse la cita insertada correctamente");
				e.printStackTrace();
			}
		}else {
			try {
				Manager.get().eliminarCitas(arg1, arg3, arg2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

}
