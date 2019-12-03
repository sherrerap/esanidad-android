if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = '../index.html'
} else {
    var nombre = JSON.parse(sessionStorage.getItem("nombreEspecialidad"));
    console.log(sessionStorage.getItem("nombreEspecialidad"));
    document.getElementById("nombre").value = nombre;

    function modificarEspecialidad() {
        var nombreNew = document.getElementById("nombre").value;
        var recurso = "https://esanidad.herokuapp.com/modificarEspecialidad";
        var data = {
            type: "modificar especialidad",
            nombreEspecialidadOld: nombre,
            nombreEspecialidadNew: nombreNew
        };
        data = JSON.stringify(data);
        setTimeout($.ajax({
                url: recurso,
                type: "POST",
                data: data,
                xhrFields: {
                    withCredentials: true
                },
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .done(function(data, textStatus, jqXHR) {
                console.log(data.type);
                if (data.type == "OK") {
                    setTimeout(location.href = '../views/gestor.html', 10000);
                    sessionStorage.removeItem("nombre");
                } else {
                    if (data.type = "error") {
                        alert("Error al modificar la especialidad, contacte con el servicio de soporte.");
                        sessionStorage.removeItem("nombre");

                    }
                    sessionStorage.removeItem("nombre");

                }

            }), 10000);
    }
}
function cerrarSesion() {
    sessionStorage.removeItem("data");
    setTimeout(location.href = '../index.html', 10000);
}