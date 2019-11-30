package HIS_E2.app_sanidad;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.concurrent.TimeUnit;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest
@RunWith(SpringRunner.class)
public class Stepdefs {
    @Autowired
    private MockMvc mockMvc;
    private WebDriver driver;	
	@Given("^Abrir Firefox y escribir url de la aplicación$")
	public void abrir_Firefox_y_escribir_url_de_la_aplicación() {
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

	@When("^escribir usuario y contraseña$")
	public void escribir_usuario_y_contraseña() {
		try {
		       driver.findElement(By.name("username")).sendKeys("username12");							
		       driver.findElement(By.name("password")).sendKeys("password12");	
		}catch(Exception e) {
			driver.quit();
			fail("No se encuentran los campos");
		}

	    
	}
	@Then("^clickeas boton login y se abre pagina de listas$")
	public void se_abre_pagina_de_listas() {
	       driver.findElement(By.name("btnLogin")).click();
	       driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	       String new_url = driver.getCurrentUrl();
	       assertTrue(new_url.equals("https://esanidad.herokuapp.com/citas"));
	       driver.quit();
	}
	
	
	
	@When("^escribes solo usuario$")
	public void escribes_solo_usuario() {
		try {
		       driver.findElement(By.name("username")).sendKeys("username12");							

		}catch(Exception e) {
			fail("No se encuentran los campos");
			driver.quit();
		}

	}

	@When("^escribes solo contraseña$")
	public void escribes_solo_contraseña() {
		try {
			driver.findElement(By.name("password")).sendKeys("password12");
		}catch(Exception e) {
			fail("No se encuentran los campos");
			driver.quit();
		}

	}
	@When("^no escribes usuario y contraseña$")
	public void no_escribes_usuario_y_contraseña() {
	    // Write code here that turns the phrase above into concrete actions

	}

	@Then("^clickeas boton login y no se abre página con las citas$")
	public void clickeas_boton_login_y_no_se_abre_página_con_las_citas() {
		try{
			driver.findElement(By.name("btnLogin")).click();
			 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		       String new_url = driver.getCurrentUrl();
		       assertFalse(new_url.equals("https://esanidad.herokuapp.com/citas"));
		}catch(UnhandledAlertException f) {
			driver.quit();
			
		}catch(Exception e) {
			driver.quit();
			fail("No existe el boton");
		}
	       
	      
	}	

}
