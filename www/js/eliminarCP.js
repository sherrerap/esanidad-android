function borrarCitaPaciente() {

    setTimeout(location.href = '../views/doctor.html', 10000);

}
function cerrarSesion() {
    sessionStorage.removeItem("data");
    sessionStorage.removeItem("dniDoctor");
    sessionStorage.removeItem("especialidadMedico");
    setTimeout(location.href = '../index.html', 10000);
}