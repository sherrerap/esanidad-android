function crearEspecialidad() {
    var nombre = document.getElementById("nombreEspecialidad").value;
    var recurso = "https://esanidad.herokuapp.com/crearEspecialidad";
    var data = {
        type: "especialidad",
        nombreEspecialidad: nombre,
    };
    console.log(nombre);
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
            console.log(data.message);
            if (data.type == "OK") {
                setTimeout(location.href = '../views/gestor.html', 10000);
            } else {
                if (data.type = "error") {
                    alert("Error al crear la especialidad, contacte con el servicio de soporte.");
                }
            }
        }), 10000);
}

function cerrarSesion() {
    sessionStorage.removeItem("data");
    setTimeout(location.href = '../index.html', 10000);
}