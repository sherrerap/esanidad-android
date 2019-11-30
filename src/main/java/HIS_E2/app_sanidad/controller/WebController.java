package HIS_E2.app_sanidad.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import HIS_E2.app_sanidad.model.Cifrador;
import HIS_E2.app_sanidad.model.Cita;
import HIS_E2.app_sanidad.model.Especialidad;
import HIS_E2.app_sanidad.model.Medico;
import HIS_E2.app_sanidad.model.PacienteMedico;
import HIS_E2.app_sanidad.model.Usuario;

@RestController
public class WebController {

	/**Controla el mapeo de la página de incio.
	 * @return Devuelve el html del index.
	 */
	@GetMapping("/home")
	public String home() {
		return "index.html";
	}
	
	/**Controlador mapeo de la página de citas.
	 * @return Devuelve el html de la página de citas.
	 */
	@GetMapping(value = "/citas")
	public String getCitas() {
		return "views/citas.html";
	}
	
	/**
	 * Recibe peticiones POST de registro.
	 * @param jso el cuerpo de la petición.
	 * @return el usuario creado.
	 * @throws Exception si los datos son incorrectos.
	 */
	@PostMapping("/register")
	public Map<String, Object> register(@RequestBody Map<String, String> jso) throws Exception {
		String dni = jso.get("dni");
		String nombre = jso.get("nombre");
		String apellidos = jso.get("apellidos");
		String contrs = jso.get("pass");
		String numSS = jso.get("numSS");
		int idEspecialidad = 0;
		Usuario usuario = Manager.get().register(dni, nombre, apellidos, contrs, numSS, idEspecialidad);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("resultado", new ObjectMapper().writeValueAsString(usuario));
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST en /getCitas.
	 * @param jso el cuerpo de la peticion.
	 * @return la lista de citas del medico dado.
	 * @throws Exception si los datos son incorrectos.
	 */
	@PostMapping("/getCitas")
	public Map<String, Object> getCitas(@RequestBody Map<String, String> jso) throws Exception {
		String dni = jso.get("dni");
		String fecha = jso.get("fecha");
		List<Cita> list = Manager.get().getCitasMedico(dni, fecha);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("numero", list.size());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		for(int i = 0; i<list.size(); i++) {
				respuesta.put("fecha"+i, formatter.format(list.get(i).getFecha()));
				respuesta.put("dniPaciente"+i, list.get(i).getDniPaciente());
				respuesta.put("especialidad"+i,list.get(i).getEspecialidad());
		}
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST en /citasPaciente.
	 * @param jso el cuerpo de la petición.
	 * @return la lista de citas del paciente dado.
	 * @throws Exception si los datos son incorrectos.
	 */
	@PostMapping("/citasPaciente")
	public Map<String, Object> citasPaciente(@RequestBody Map<String, String> jso) throws Exception {
		String dni = jso.get("dni");
		List<Cita> list = Manager.get().getCitasPaciente(dni);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("numero", list.size());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		for(int i = 0; i<list.size(); i++) {
			respuesta.put("fecha"+i, formatter.format(list.get(i).getFecha()));
			respuesta.put("dniPaciente"+i, list.get(i).getDniPaciente());
			respuesta.put("especialidad"+i,list.get(i).getEspecialidad());
		}
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de autenticar.
	 * @param jso el cuerpo del mensaje.
	 * @return el usuario autenticado o error si no es correcto.
	 * @throws Exception si los datos dados no son correctos.
	 */
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@PostMapping(value = "/autenticar", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String autenticar(@RequestBody Map<String, String> jso) throws Exception {
		String dni = jso.get("dni");
		String pass = jso.get("pass");
		JSONObject resultado=new JSONObject();
		if(Manager.get().autenticar(dni, pass)) {
			resultado.put("type", "OK");
			resultado.put("resultado", "login correcto");
			String especialidad = Manager.get().obtenerEspecilidad(dni);
			if(especialidad != null) {
				resultado.put("especialidad", especialidad);
			}
		} else {
			throw new Exception("Credenciales invalidas");
		}
		return resultado.toString();
	}
	
	/**
	 * Recibe peticiones POST de petición de citas.
	 * @param jso el cuerpo de la petición.
	 * @return la cita creada.
	 * @throws Exception si los datos son incorrectos.
	 */
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@PostMapping(value = "/pedirCita")
	public Map<String, Object> pedirCita(@RequestBody Map<String, String> jso) throws Exception{
		String dniPaciente = jso.get("dniPaciente");
		String fecha = jso.get("fecha");
		String especialidad = jso.get("especialidad");
		Cita cita = Manager.get().pedirCita(dniPaciente, fecha, especialidad);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		respuesta.put("type", "OK");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		respuesta.put("fecha", formatter.format(cita.getFecha()));
		respuesta.put("dniPaciente", cita.getDniPaciente());
		respuesta.put("especialidad",cita.getEspecialidad());
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de consulta de las horas en las que un paciente tiene citas.
	 * @param jso el cuerpo de la petición.
	 * @return las fechas en las que tiene citas el paciente.
	 * @throws Exception si los datos no son correctos.
	 */
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@PostMapping(value = "/citasDisponibles")
	public Map<String, Object> citasDisponibles(@RequestBody Map<String, String> jso) throws Exception {
		String dniPaciente = jso.get("dniPaciente");
		String especialidad = jso.get("especialidad");
		List<Date> fechas = Manager.get().getCitas(dniPaciente, especialidad);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		for(int i = 0; i<fechas.size(); i++) {
			ObjectMapper objectmapper = new ObjectMapper();
			objectmapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
			respuesta.put("fecha"+i, objectmapper.writeValueAsString(fechas.get(i)));
		}
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de modificación de citas.
	 * @param jso el cuerpo de la petición.
	 * @return la cita modificada.
	 * @throws Exception si los datos no son correctos.
	 */
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@PostMapping(value = "/modificarCita")
	public Map<String, Object> modificarCita(@RequestBody Map<String, String> jso) throws Exception{
		String dniPaciente = jso.get("dniPaciente");
		String especialidad = jso.get("especialidad");
		String fechaActual = jso.get("fechaActual");
		String fechaModificar = jso.get("fechaModificar");
		Cita cita = Manager.get().modificarCita(dniPaciente, especialidad, fechaActual, fechaModificar);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		respuesta.put("fecha", formatter.format(cita.getFecha()));
		respuesta.put("dniPaciente", cita.getDniPaciente());
		respuesta.put("especialidad",cita.getEspecialidad());
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de anulación de citas.
	 * @param jso el cuerpo de la petición.
	 * @return la confirmación de cita anulada.
	 * @throws Exception si los datos no son correctos.
	 */
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@PostMapping(value = "/anularCita")
	public Map<String, Object> anularCita(@RequestBody Map<String, String> jso) throws Exception{
		String dniPaciente = jso.get("dniPaciente");
		String especialidad = jso.get("especialidad");
		String fecha = jso.get("fecha");
		Manager.get().eliminarCitas(dniPaciente, fecha, especialidad);;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("resultado", "cita anulada correctamente");
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de creacion de especialidad.
	 * @param jso
	 * @return el mensaje con la especializacion creada
	 * @throws Exception.
	 */
	@PostMapping(value = "/crearEspecialidad")
	public Map<String, Object> crearEspecialidad(@RequestBody Map<String, String> jso) throws Exception{
		String nombreEspecialidad = jso.get("nombreEspecialidad");
		String tiempoCita = jso.get("tiempoCita");
		String horaInicio = jso.get("horaInicio");
		String horaFin = jso.get("horaFin");
		Especialidad especialidad = Manager.get().crearEspecialidad(nombreEspecialidad, tiempoCita, horaInicio, horaFin);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("resultado", new ObjectMapper().writeValueAsString(especialidad));
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de la eliminación de especialidades.
	 * @param jso.
	 * @return el mensaje con la especialización creada.
	 * @throws Exception.
	 */
	@PostMapping(value = "/eliminarEspecialidad")
	public Map<String, Object> eliminarEspecialidad(@RequestBody Map<String, String> jso) throws Exception{
		String nombreEspecialidad = jso.get("nombreEspecialidad");
		Especialidad especialidad = Manager.get().eliminarEspecialidad(nombreEspecialidad);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("resultado", new ObjectMapper().writeValueAsString(especialidad));
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de consulta de especialidades.
	 * @param jso
	 * @return la lista de especialidades.
	 * @throws Exception.
	 */
	@PostMapping(value = "/consultaEspecialidades")
	public Map<String, Object> consultarEspecialidades(@RequestBody Map<String, String> jso) throws Exception{
		List<Especialidad> lista = Manager.get().consultarEspecialidades();
		Map<String, Object> respuesta = new HashMap<String, Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		respuesta.put("type", "OK");
		respuesta.put("numero", lista.size());
		for(int i = 0; i<lista.size(); i++) {
			respuesta.put("nombreEspecialidad"+i, lista.get(i).getNombreEspecialidad());
			respuesta.put("duracionCita"+i, lista.get(i).getDuracionCita());
			respuesta.put("horaInicio"+i,formatter.format(lista.get(i).getHoraInicio()));
			respuesta.put("horaFin"+i,formatter.format(lista.get(i).getHoraFin()));
		}
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de modificacion de especialidades.
	 * @param jso el cuerpo de la peticion.
	 * @return la especialidad modificada.
	 * @throws Exception.
	 */
	@PostMapping(value = "/modificarEspecialidad")
	public Map<String, Object> modificarEspecialidad(@RequestBody Map<String, String> jso) throws Exception{
		String nombre = jso.get("nombreEspecialidad");
		String duracionOld = jso.get("duracionOld");
		String horaInicioOld = jso.get("horaInicioOld");
		String horaFinOld = jso.get("horaFinOld");
		String duracionNew = jso.get("duracionNew");
		String horaInicioNew = jso.get("horaInicioNew");
		String horaFinNew = jso.get("horaFinNew");
		Especialidad especialidad = Manager.get().modificarEspecialidad(nombre, duracionOld, horaInicioOld, horaFinOld, duracionNew, horaInicioNew, horaFinNew);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("resultado", new ObjectMapper().writeValueAsString(especialidad));
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de creación de médicos.
	 * @param jso el cuerpo de la petición.
	 * @return la respuesta con el medico creado.
	 * @throws Exception.
	 */
	@PostMapping(value = "/crearMedico")
	public Map<String, Object> crearMedico(@RequestBody Map<String, String> jso) throws Exception {
		String dniMedico = jso.get("dni");
		String especialidad = jso.get("especialidad");
		Medico medico = Manager.get().crearMedico(dniMedico, especialidad);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("resultado", new ObjectMapper().writeValueAsString(medico));
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de eliminación de médicos.
	 * @param jso el cuerpo de la petición.
	 * @return la respuesta con el médico eliminado.
	 * @throws Exception.
	 */
	@PostMapping(value = "/eliminarMedico")
	public Map<String, Object> eliminarMedico(@RequestBody Map<String, String> jso) throws Exception {
		String dniMedico = jso.get("dni");
		Medico medico = Manager.get().eliminarMedico(dniMedico);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("resultado", new ObjectMapper().writeValueAsString(medico));
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de listar los medicos.
	 * @param jso el cuerpo de la peticion.
	 * @return la lista de dnis de medicos.
	 * @throws Exception.
	 */
	@PostMapping(value = "/listaMedicos")
	public Map<String, Object> listaMedicos(@RequestBody Map<String, String> jso) throws Exception {
		List<Medico> medicos = Manager.get().listaMedicos();
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("numero", medicos.size());
    for(int i = 0; i < medicos.size(); i++) {
      respuesta.put("dni"+i,Cifrador.descifrar(Cifrador.descifrar(medicos.get(i).getDni())));
      respuesta.put("nombre"+i,Cifrador.descifrar(Cifrador.descifrar(medicos.get(i).getNombre())));
      //respuesta.put("especialidad"+i,Cifrador.descifrar(Cifrador.descifrar(medicos.get(i).getIdEspecialidad())));
      
    }
    
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de creacion de Medico-paciente.
	 * @param jso el cuerpo de la peticion.
	 * @return la respuesta con la relacion creada.
	 * @throws Exception.
	 */
	@PostMapping(value = "/crearMedicoPaciente")
	public Map<String, Object> crearMedicoPaciente(@RequestBody Map<String, String> jso) throws Exception {
		String dniMedico = jso.get("dniMedico");
		String dniPaciente = jso.get("dniPaciente");
		PacienteMedico pacMed = Manager.get().crearMedicoPaciente(dniPaciente, dniMedico);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("resultado", new ObjectMapper().writeValueAsString(pacMed));
		return respuesta;
	}
	
	/**
	 * Recibe peticiones POST de eliminación de Medico-paciente.
	 * @param jso el cuerpo de la peticion.
	 * @return la respuesta con la relacion eliminada.
	 * @throws Exception.
	 */
	@PostMapping(value = "/eliminarPacienteMedico")
	public Map<String, Object> eliminarPacienteMedico(@RequestBody Map<String, String> jso) throws Exception {
		String dniMedico = jso.get("dniMedico");
		String dniPaciente = jso.get("dniPaciente");
		PacienteMedico pacMed = Manager.get().eliminarPacienteMedico(dniPaciente, dniMedico);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("resultado", new ObjectMapper().writeValueAsString(pacMed));
		return respuesta;
	}
	
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@PostMapping(value = "/getHoras")
	public Map<String, Object> getHoras(@RequestBody Map<String, String> jso) throws Exception {
		String especialidad = jso.get("especialidad");
		String dniPaciente = jso.get("dniPaciente");
		String fecha = jso.get("fecha");
		List<String> lista = Manager.get().getHoras(fecha, especialidad, dniPaciente);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("type", "OK");
		respuesta.put("numero", lista.size());
		for(int i = 0; i < lista.size(); i++) {
			respuesta.put("hora"+i, lista.get(i));
		}
		return respuesta;
	}
	
	@PostMapping(value = "/consultarEspecialdiadPaciente")
	  public Map<String, Object> consutlarEspecialidadPaciente(@RequestBody Map<String, String> jso) throws Exception {
	    String dniPaciente = jso.get("dniPaciente");
	    List<String> lista = Manager.get().consultarEspecialidadPaciente(dniPaciente);
	    Map<String, Object> respuesta = new HashMap<String, Object>();
	    respuesta.put("type", "OK");
	    respuesta.put("numero", lista.size());
	    for(int i = 0; i<lista.size(); i++) {
	      respuesta.put("nombreEspecialidad"+i, lista.get(i));
	    }
	    return respuesta;
	  }
	
	/**
	 * Recoge las excepciones generadas por la aplicación.
	 * @param ex la excepción generada.
	 * @return el mensaje type=error generado.
	 */
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleException(Exception ex) {
		Map<String, String> resultado = new HashMap<String, String>();
		resultado.put("type", "error");
		resultado.put("message", ex.getMessage());
	    return resultado;
	}
}