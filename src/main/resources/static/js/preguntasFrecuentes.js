var DNI = JSON.parse(sessionStorage.getItem("data"));

function cerrarSesion() {

    sessionStorage.removeItem("data");
    setTimeout(location.href = 'https://esanidad.herokuapp.com/', 10000);

}