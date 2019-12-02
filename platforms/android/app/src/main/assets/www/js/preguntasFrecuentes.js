var DNI = JSON.parse(sessionStorage.getItem("data"));

function cerrarSesion() {

    sessionStorage.removeItem("data");
    setTimeout(location.href = '../index.html', 10000);

}