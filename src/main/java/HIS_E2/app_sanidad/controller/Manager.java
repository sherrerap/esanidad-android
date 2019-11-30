package HIS_E2.app_sanidad.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import HIS_E2.app_sanidad.model.Cifrador;
import HIS_E2.app_sanidad.model.Cita;
import HIS_E2.app_sanidad.model.Especialidad;
import HIS_E2.app_sanidad.model.Medico;
import HIS_E2.app_sanidad.model.Paciente;
import HIS_E2.app_sanidad.model.PacienteMedico;
import HIS_E2.app_sanidad.model.Usuario;
import HIS_E2.app_sanidad.repositories.CitaRepository;
import HIS_E2.app_sanidad.repositories.EspecialidadRepository;
import HIS_E2.app_sanidad.repositories.MedicoRepository;
import HIS_E2.app_sanidad.repositories.PacienteMedicoRepository;
import HIS_E2.app_sanidad.repositories.PacienteRepository;
import HIS_E2.app_sanidad.repositories.UserRepository;

/**
 * Clase Manager de la aplicación.
 * @author Miguel.
 */
@Service
public class Manager {
	
	static final long ONE_MINUTE_IN_MILLIS=60000;
	
	/**
	 * repositorio de usuarios.
	 */
	@Autowired
	private UserRepository userRepo;
	/**
	 * repositorio de pacientes.
	 */
	@Autowired
	private PacienteRepository pacienteRepo;
	/**
	 * repositorio de medicos.
	 */
	@Autowired
	private MedicoRepository medicoRepo;
	/**
	 * repositorio de citas.
	 */
	@Autowired
	private CitaRepository citaRepo;
	/**
	 * repositorio de especialidades.
	 */
	@Autowired
	private EspecialidadRepository especialidadRepo;
	/**
	 * repositorio de PacientesMedicos.
	 */
	@Autowired
	private PacienteMedicoRepository pacienteMedicoRepo;
	/**
	 * Constructor vacío para el singleton del Manager.
	 */
	private Manager() {
		
	}

	/**
	 * Creador del patrón singleton del manager.
	 * @author Miguel.
	 */
	private static class ManagerHolder {
		private static Manager singleton = new Manager();
	}
	
	/**
	 * Devuelve la instancia singleton de Manager.
	 * @return la instancia de Manager.
	 */
	@Bean
	public static Manager get() {
		return ManagerHolder.singleton;
	}
	
	/**
	 * Controla que la contraseña sea mayor de 8 carácteres
	 * y tiene mayuscula y número.
	 * @param contrs.
	 * @throws Exception Si la contraseña es incorrecta.
	 */
	private void comprobarPassword(String contrs) throws Exception {
		int contadorMayus = 0;
		int contadorMinus = 0;
		int contadorNum = 0;
		for(int i = 0; i<contrs.length(); i++) {
			if(Character.isUpperCase(contrs.charAt(i))) {
				contadorMayus++;
			}
			if(Character.isLowerCase(contrs.charAt(i))) {
				contadorMinus++;
			}
			if(Character.isDigit(contrs.charAt(i))) {
				contadorNum++;
			}
		}
		if(contadorMayus == 0 || contadorMinus == 0 || contadorNum == 0) {
			throw new Exception("La contraseña debe contener Mayúscula, Minúscula y Número");
		}
	}
	
	/**
	 * Calcula la letra del dni dado un número del dni.
	 * @param dniNum.
	 * @return la letra calculada.
	 */
	public char calcularLetraDni(int dniNum) {
	    String juegoCaracteres="TRWAGMYFPDXBNJZSQVHLCKE";
	    int modulo= dniNum % 23;
	    char letra = juegoCaracteres.charAt(modulo);
	    return letra;
	}
	
	/**
	 * Comprueba la validez del dni
	 * 9 carácteres y letra correcta.
	 * @param dni.
	 * @throws Exception si el dni es incorrecto.
	 */
	public void comprobarDni(String dni) throws Exception{
		int dniNum;
		if(dni.length() > 9) {
			throw new Exception("El dni es incorrecto");
		}
		for(int i = 0; i<dni.length(); i++) {
			if(i < 8 && !Character.isDigit(dni.charAt(i))) {
				throw new Exception("El dni es incorrecto");
			}
			if( i == 8 && !Character.isLetter(dni.charAt(i))) {
				throw new Exception("El dni es incorrecto");
			}
		}
		dniNum = Integer.parseInt(dni.substring(0, 8));
		char letra = calcularLetraDni(dniNum);
		if(Character.toUpperCase(letra) != Character.toUpperCase(dni.charAt(8))) {
			throw new Exception("El dni es incorrecto");
		}
	}
	
	/**
	 * Comprueba que el número de la seguridad social
	 * es correcto.
	 * @param numSS.
	 * @throws Exception si el número es incorrecto.
	 */
	private void comprobarNSS(String numSS) throws Exception{
		if(numSS.length()!=12) {
			throw new Exception("El numero de seguridad social no es correcto");
		}
		for(int i = 0; i<numSS.length(); i++) {
			if(!Character.isDigit(numSS.charAt(i))) {
				throw new Exception("El número de seguridad social no es correcto");
			}
		}
	}
	
	/**
	 * Controla que al crear una cita no se solape con una ya existente.
	 * @param dniPaciente el paciente dueño de la cita.
	 * @param dniMedico medico de la cita.
	 * @param fechaCita fecha a comprobar con formato dd/MM/yyyy HH:mm:ss.
	 * @throws Exception si la fecha se solapa.
	 */
	private void controlarSolapamiento(String dniPaciente, String dniMedico, Date fechaCita) throws Exception{
		List<Cita> citas = citaRepo.findByDniPaciente(dniPaciente);
		Medico medico = medicoRepo.findByDni(Cifrador.cifrar(dniMedico));
		Especialidad especialidad = especialidadRepo.findCustomEspecialidad(medico.getIdEspecialidad());
		int duracion = especialidad.getDuracionCita();
		Date fechaCitaPlusDuracion = new Date(fechaCita.getTime() + (duracion * ONE_MINUTE_IN_MILLIS));
		for(int i = 0; i<citas.size(); i++) {
			Date fechaSolapada = citas.get(i).getFecha();
			Date fechaSolapadaPlusDuracion = new Date(fechaSolapada.getTime() + (duracion * ONE_MINUTE_IN_MILLIS));
			if((fechaCita.after(fechaSolapada) && fechaCita.before(fechaSolapadaPlusDuracion)) ||
					(fechaCita.before(fechaSolapada) && fechaCitaPlusDuracion.after(fechaSolapada))) {
				throw new Exception("Las fechas se solapan, cita incorrecta");
			}
			if(fechaCita.equals(fechaSolapada) || fechaCita.equals(fechaSolapadaPlusDuracion)) {
				throw new Exception("Las fechas no pueden ser iguales");
			}
		}
		citas = citaRepo.findByDniMedico(dniMedico);
		for(int i = 0; i<citas.size(); i++) {
			Date fechaSolapada = citas.get(i).getFecha();
			Date fechaSolapadaPlusDuracion = new Date(fechaSolapada.getTime() + (duracion * ONE_MINUTE_IN_MILLIS));
			if((fechaCita.after(fechaSolapada) && fechaCita.before(fechaSolapadaPlusDuracion)) ||
					(fechaCita.before(fechaSolapada) && fechaCitaPlusDuracion.after(fechaSolapada))) {
				throw new Exception("Las fechas se solapan, cita incorrecta");
			}
			if(fechaCita.equals(fechaSolapada) || fechaCita.equals(fechaSolapadaPlusDuracion)) {
				throw new Exception("Las fechas no pueden ser iguales");
			}
		}
		
	}
	
	/**
	 * Registra los usuarios en la base de datos.
	 * @param dni del usuario.
	 * @param nombre del usuario.
	 * @param apellidos del usuario.
	 * @param contrs del usuario.
	 * @param numSS del usuario.
	 * @param idEspecialidad del medico a guardar.
	 * @return el usuario creado.
	 * @throws Exception si algun dato es incorrecto.
	 */
	public Usuario register(String dni,	String nombre, String apellidos, String contrs, String numSS, int idEspecialidad) throws Exception {
		if(dni == null || nombre == null || apellidos == null || contrs == null || numSS == null) {
			throw new Exception("No debe haber campos vacios");
		}
		if(dni.equals("") || nombre.equals("") || apellidos.equals("") || contrs.equals("") || numSS.equals("")) {
			throw new Exception("No debe haber campos vacios");
		}
		comprobarPassword(contrs);
		comprobarDni(dni);
		comprobarNSS(numSS);
		Usuario usuario = new Usuario(dni, nombre, apellidos, contrs);
		userRepo.insert(usuario);
		Paciente paciente = new Paciente(dni, nombre, apellidos, contrs, numSS);
		pacienteRepo.insert(paciente);
		return usuario;
	}

	/**
	 * Devuelve las citas de un medico dado.
	 * @param dni del medico.
	 * @param fecha dia de las citas del medico.
	 * @return la lista de citas del medico si la contraseña es correcta.
	 * @throws Exception si la contraseña es incorrecta.
	 */
	public List<Cita> getCitasMedico(String dni, String fecha) throws Exception {
		List<Cita> listaCitas = citaRepo.findByDniMedico(dni);
		List<Cita> lista = new ArrayList<Cita>();
		Date fechaDia = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
		Date fechaDiaFin = new Date(fechaDia.getTime() + (1440 * ONE_MINUTE_IN_MILLIS));
		for(int i = 0; i < listaCitas.size(); i++) {
			Date fechaCita = listaCitas.get(i).getFecha();
			if((fechaCita.after(fechaDia) && fechaCita.before(fechaDiaFin)) || fechaCita.equals(fechaDia)) {
				lista.add(listaCitas.get(i));
			}
		}
		return lista;
	}

	/**
	 * Devuelve las citas de un paciente dado.
	 * @param dni del usuario.
	 * @return lista de citas.
	 * @throws Exception si el dni no es válido.
	 */
	public List<Cita> getCitasPaciente(String dni) throws Exception {
		List<Cita> lista = citaRepo.findByDniPaciente(dni);
		return lista;
	}

	/**
	 * Comprueba que la contraseña y dni dados con correctos.
	 * @param dni del usuario.
	 * @param pass del usuario.
	 * @return resultado de la comprobacion.
	 * @throws Exception si los datos son incorrectos.
	 */
	public boolean autenticar(String dni, String pass) throws Exception {
		String dniABuscar= Cifrador.cifrar(dni);
		String passABuscar=Cifrador.cifrarHash(pass);

		Usuario user = userRepo.findByDni(dniABuscar);
		return user.getContrs().equals(passABuscar);
	}
	
	/**
	 * Crea una cita con los parametros dados.
	 * @param dniPaciente.
	 * @param fecha de la cita.
	 * @param especialidad de la cita.
	 * @return la cita creada.
	 * @throws Exception si la fecha se solapa o es pasada.
	 */
	public Cita pedirCita(String dniPaciente, String fecha, String especialidad) throws Exception {
		PacienteMedico pacienteMed = pacienteMedicoRepo.findCustomMedico(dniPaciente, especialidad);
		String dniMedico = pacienteMed.getDniMedico();
		Date fechaCita = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fecha);
		Date sysdate = new Date(System.currentTimeMillis());
		if(fechaCita.compareTo(sysdate) < 0) {
	          throw new Exception("La fecha de la cita no puede ser pasada");
		}
		controlarSolapamiento(dniPaciente, dniMedico, fechaCita);
		Cita cita = new Cita(fechaCita, dniMedico, dniPaciente, especialidad);
		cita = citaRepo.insert(cita);
		return cita;
	}

	/**
	 * Devuelve las fechas con cita de un paciente en una especialidad dada.
	 * @param dniPaciente.
	 * @param especialidad de las citas.
	 * @return la lista de fechas con cita.
	 * @throws Exception si los datos son incorrectos.
	 */
	public List<Date> getCitas(String dniPaciente, String especialidad) throws Exception {
		PacienteMedico pacienteMed = pacienteMedicoRepo.findCustomMedico(dniPaciente, especialidad);
		String dniMedico = pacienteMed.getDniMedico();
		List <Cita> citas = citaRepo.findByDniPaciente(dniPaciente);
		citas.addAll(citaRepo.findByDniMedico(dniMedico));
		List <Date> fechas = new ArrayList<Date>();
		for(int i = 0; i<citas.size(); i++) {
			fechas.add(citas.get(i).getFecha());
		}
		return fechas;
	}
	
	/**
	 * Elimina una cita con los parametros dados.
	 * @param dniPaciente.
	 * @param fecha con formato dd/MM/yyyy HH:mm:ss.
	 * @param especialidad de la cita.
	 * @throws ParseException si el formato de la fecha es incorrecto.
	 */
	public void eliminarCitas(String dniPaciente, String fecha, String especialidad) throws ParseException {
		PacienteMedico pacienteMed = pacienteMedicoRepo.findCustomMedico(dniPaciente, especialidad);
		String dniMedico = pacienteMed.getDniMedico();
		Date fechaCita = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fecha);
		List<Cita> lista = citaRepo.deleteCustomCita(dniPaciente, dniMedico, fechaCita);
		citaRepo.delete(lista.get(0));
	}
		
	/**
	 * Comprueba si existe la cita dada.
	 * @param dniPaciente.
	 * @param especialidad.
	 * @param fecha con formato dd/MM/yyyy HH:mm:ss.
	 * @return true si existe la cita.
	 * @throws ParseException si el formato de la fecha es incorrecto.
	 */
	public boolean existeCita(String dniPaciente, String especialidad, String fecha) throws ParseException {
		PacienteMedico pacienteMed = pacienteMedicoRepo.findCustomMedico(dniPaciente, especialidad);
		String dniMedico = pacienteMed.getDniMedico();
		Date fechaCita = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fecha);
		return citaRepo.existCustomCita(dniPaciente, dniMedico, fechaCita);
	}
	
	/**
	 * Cambia la fecha de una cita con los parametros dados.
	 * @param dniPaciente.
	 * @param especialidad.
	 * @param fechaActual con formato dd/MM/yyyy HH:mm:ss.
	 * @param fechaModificar con formato dd/MM/yyyy HH:mm:ss.
	 * @return la cita modificada.
	 * @throws ParseException si la fecha tiene formato incorrecto.
	 */
	public Cita modificarCita(String dniPaciente, String especialidad, String fechaActual, String fechaModificar) throws ParseException {
		PacienteMedico pacienteMed = pacienteMedicoRepo.findCustomMedico(dniPaciente, especialidad);
		String dniMedico = pacienteMed.getDniMedico();
		Date fechaCita = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fechaActual);
		Date fechaNueva = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fechaModificar);
		List<Cita> list = citaRepo.deleteCustomCita(dniPaciente, dniMedico, fechaCita);
		Cita cita = new Cita(fechaNueva, dniMedico, dniPaciente, especialidad);
		citaRepo.delete(list.get(0));
		citaRepo.insert(cita);
		return cita;
	}
	
	/**
	 * Controla que el string dado no contenga números o sea vacio.
	 * @param nombre el string a controlar.
	 * @throws Exception si el string contiene numeros.
	 */
	public void numerosNombre(String nombre) throws Exception {
		if(nombre.equals("")) {
			throw new Exception("El nombre no puede ser vacio");
		}
		if(nombre.equals(null)) {
			throw new Exception("El nombre no puede ser vacio");
		}
		for(int i = 0; i < nombre.length(); i++) {
			if(Character.isDigit(nombre.charAt(i))) {
				throw new Exception("El nombre no puede contener numeros");
			}
		}
	}
	/**
	 * Crea una especialidad con los datos dados y la inserta en la base de datos.
	 * @param nombrEspecialidad.
	 * @param tiempoCita.
	 * @param horaInicio.
	 * @param horaFin.
	 * @return la especialidad insertada.
	 * @throws ParseException.
	 */
	public Especialidad crearEspecialidad(String nombrEspecialidad, String tiempoCita, String horaInicio, String horaFin) throws Exception {
		Date horaInicioDate = new SimpleDateFormat("HH:mm").parse(horaInicio);
		Date horaFinDate = new SimpleDateFormat("HH:mm").parse(horaFin);
		int tiempoCitaInt = Integer.parseInt(tiempoCita);
		numerosNombre(nombrEspecialidad);
		if(tiempoCitaInt <= 0 || tiempoCitaInt > 60) {
			throw new Exception("La duracion de la cita de la especialidad debe ser mayor que 0 y menor o igual que 60");
		}
		Especialidad especialidad = new Especialidad(nombrEspecialidad, tiempoCitaInt, horaInicioDate, horaFinDate);
		if(especialidadRepo.findCustomEspecialidad(nombrEspecialidad) != null) {
			throw new Exception("Especialidad ya existente");
		}
		especialidadRepo.insert(especialidad);
		return especialidad;
	}
	
	/**
	 * Elimina una especialidad dado un nombre.
	 * @param nombreEspecialidad.
	 * @return la especialidad eliminada.
	 */
	public Especialidad eliminarEspecialidad(String nombreEspecialidad) throws Exception{
		if(nombreEspecialidad.equals("Gestor Sistema")) {
			throw new Exception("No se puede eliminar el gestor del sistema");
		}
		Especialidad lista = especialidadRepo.findCustomEspecialidad(nombreEspecialidad);
		especialidadRepo.delete(lista);
		return lista;
	}
	
	/**
	 * Consulta las especialidades existentes.
	 * @return la lista con las especialidades encontradas.
	 */
	public List<Especialidad> consultarEspecialidades(){
		List<Especialidad> lista = especialidadRepo.findAll();
		return lista;
	}
	
	/**
	 * Modifica la especialidad con los valores New.
	 * @param nombre.
	 * @param duracionOld.
	 * @param horaInicioOld.
	 * @param horaFinOld.
	 * @param duracionNew.
	 * @param horaInicioNew.
	 * @param horaFinNew.
	 * @return la especialidad modificada.
	 * @throws Exception.
	 */
	public Especialidad modificarEspecialidad(String nombre, String duracionOld, String horaInicioOld, String horaFinOld, String duracionNew, String horaInicioNew, String horaFinNew) throws Exception {
		Especialidad especialidad = especialidadRepo.findCustomEspecialidad(nombre);
		especialidadRepo.delete(especialidad);
		int duracionNewInt = Integer.parseInt(duracionNew);
		Date horaInicioDate = new SimpleDateFormat("HH:mm").parse(horaInicioNew);
		Date horaFinDate = new SimpleDateFormat("HH:mm").parse(horaFinNew);
		numerosNombre(nombre);
		if(duracionNewInt <= 0 || duracionNewInt > 60) {
			throw new Exception("La duracion de la cita de la especialidad debe ser mayor que 0 y menor o igual que 60");
		}
		Especialidad esp = new Especialidad(nombre, duracionNewInt, horaInicioDate, horaFinDate);
		especialidadRepo.insert(esp);
		return esp;
	}
	
	/**
	 * Metodo para crear medico en el sistema.
	 * @param dniMedico.
	 * @param especialidad.
	 * @return el medico creado.
	 * @throws Exception si el medico o la especialidad no existen.
	 */
	public Medico crearMedico(String dniMedico,String especialidad) throws Exception {
		Usuario user = userRepo.findByDni(Cifrador.cifrar(dniMedico));
		if(user == null) {
			throw new Exception("El medico debe estar registrado como usuario");
		}
		if(especialidadRepo.findCustomEspecialidad(especialidad) == null) {
			throw new Exception("Especialidad no existente");
		}
		Medico medico = new Medico(Cifrador.descifrar(user.getDni()), Cifrador.descifrar(user.getNombre()), Cifrador.descifrar(user.getApellidos()), user.getContrs(), especialidad);
		medico.setContrs(user.getContrs());
		medicoRepo.insert(medico);
		return medico;
	}
	
	/**
	 * Método para la eliminación de médicos.
	 * @param dniMedico.
	 * @return el médico eliminado.
	 * @throws Exception.
	 */
	public Medico eliminarMedico(String dniMedico) throws Exception {
		Medico medico = medicoRepo.findByDni(Cifrador.cifrar(dniMedico));
		List<PacienteMedico> pacMed = pacienteMedicoRepo.findCustomDniMedico(dniMedico);
		for(int i = 0; i < pacMed.size(); i++) {
			pacienteMedicoRepo.delete(pacMed.get(i));
		}
		medicoRepo.delete(medico);
		return medico;
	}
	
	/**
	 * Metodo para listar los dni de los medicos.
	 * @return la lista de dnis de medicos.
	 * @throws Exception.
	 */
  public List<Medico> listaMedicos() throws Exception {
    
    List<Medico> medicos = medicoRepo.findAll();
  
    
    return medicos;
  }
	
	/**
	 * Crea una relacion medico-paciente.
	 * @param dniPaciente.
	 * @param dniMedico.
	 * @return la relacion creada.
	 * @throws Exception.
	 */
	public PacienteMedico crearMedicoPaciente(String dniPaciente, String dniMedico) throws Exception {
		Medico medico = medicoRepo.findByDni(Cifrador.cifrar(dniMedico));
		String especialidad = medico.getIdEspecialidad();
		if(userRepo.findByDni(Cifrador.cifrar(dniPaciente)) == null) {
			throw new Exception ("El Dni no corresponde con un paciente");
		}
		if(pacienteMedicoRepo.findCustomMedico(dniPaciente, especialidad) == null) {
			PacienteMedico pacMed = new PacienteMedico(dniPaciente, dniMedico, especialidad);
			pacienteMedicoRepo.insert(pacMed);
			return pacMed;
		} else {
			throw new Exception("El paciente ya tiene un medico asignado para la especialidad"+especialidad);
		}
	}
	
	/**
	 * Elimina la relacion Paciente-Medico.
	 * @param dniPaciente dni del paciente a eliminar.
	 * @param dniMedico dni del medico relacionado.
	 * @return la relación eliminada.
	 * @throws Exception 
	 */
	public PacienteMedico eliminarPacienteMedico(String dniPaciente, String dniMedico) throws Exception {
		String especialidad = medicoRepo.findByDni(Cifrador.cifrar(dniMedico)).getIdEspecialidad();
		PacienteMedico pacMed = pacienteMedicoRepo.findCustomMedico(dniPaciente, especialidad);
		pacienteMedicoRepo.delete(pacMed);
		return pacMed;
	}
	
	/**
	 * Devuelve la especialidad de un medico si existe.
	 * @param dni del usuario a busca.
	 * @return la especialidad asignada, null si no existe.
	 * @throws Exception.
	 */
	public String obtenerEspecilidad(String dni) throws Exception {
		Medico medico = medicoRepo.findByDni(Cifrador.cifrar(dni));
		if(medico != null) {
			String especialidad = medico.getIdEspecialidad();
			return especialidad;
		} else {
			return null;
		}
	}
	public List<String> getHoras(String fecha, String especialidad, String dniPaciente) throws Exception{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
		LocalDateTime fechaDia = fechaLocal.atStartOfDay();
		Especialidad esp = especialidadRepo.findCustomEspecialidad(especialidad);
		PacienteMedico pacMed = pacienteMedicoRepo.findCustomMedico(dniPaciente, especialidad);
		int duracion = esp.getDuracionCita();
		Date horaI = esp.getHoraInicio();
		LocalDateTime horaInicio = horaI.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		Date horaF = esp.getHoraFin();
		LocalDateTime horaFin = horaF.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		List<Cita> citasMedico = citaRepo.findByDniMedico(pacMed.getDniMedico());
		List<Cita> citasPaciente = citaRepo.findByDniPaciente(dniPaciente);
		List<String> listaMedico = controlarHoras(fechaDia, duracion, horaInicio, horaFin, citasMedico);
		List<String> listaPaciente = controlarHoras(fechaDia, duracion, horaInicio, horaFin, citasPaciente);
		for(int i = 0; i < listaPaciente.size(); i++) {
			if(!listaMedico.contains(listaPaciente.get(i))) {
				listaMedico.add(listaPaciente.get(i));
			}
			
		}
		return listaMedico;
	}

	private List<String> controlarHoras(LocalDateTime fechaDia, int duracion, LocalDateTime horaInicio,
			LocalDateTime horaFin, List<Cita> citasMedico) {
		LocalDateTime fechaInicioCita = fechaDia.plusHours(horaInicio.getHour());
		LocalDateTime fechaInicioDuracion = fechaInicioCita.plusMinutes(duracion);
		LocalDateTime fechaFinCita = fechaDia.plusHours(horaFin.getHour());
		List<String> lista = new ArrayList<String>();
		boolean continuar = true;
		while(continuar) {
			if(citasMedico.size() == 0) {
				continuar = false;
				while(fechaInicioCita.isBefore(fechaFinCita)) {
					lista.add(DateTimeFormatter.ofPattern("HH:mm").format(fechaInicioCita));
					fechaInicioCita = fechaInicioCita.plusMinutes(duracion);
				}
			}
			
			for(int i = 0; i<citasMedico.size(); i++) {
				LocalDateTime fechaCita = citasMedico.get(i).getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime fechaCitaDuracion = fechaCita.plusMinutes(duracion);
				if(!((fechaInicioCita.isAfter(fechaCita) && fechaInicioCita.isBefore(fechaCitaDuracion)) || 
						fechaInicioCita.isBefore(fechaCita) && fechaInicioDuracion.isAfter(fechaCita))) {
					if(!(fechaInicioCita.equals(fechaCita) || fechaInicioCita.equals(fechaCitaDuracion))) {
						lista.add(DateTimeFormatter.ofPattern("HH:mm").format(fechaInicioCita));
					}
				}
				
				fechaInicioCita = fechaInicioCita.plusMinutes(duracion);
				fechaInicioDuracion = fechaInicioCita.plusMinutes(duracion);
				if(fechaInicioCita.isAfter(fechaFinCita) || fechaInicioCita.equals(fechaFinCita)) {
					continuar = false;
				}
			}
		}
		return lista;
	}
	
	public List<String> consultarEspecialidadPaciente(String dniPaciente) {
	    List<PacienteMedico> pacMed = pacienteMedicoRepo.findCustomDniPaciente(dniPaciente);
	    List<String> lista = new ArrayList<String>();
	    for(int i = 0; i < pacMed.size(); i++) {
	      lista.add(pacMed.get(i).getEspecialidad());
	    }
	    return lista;
	  }
}