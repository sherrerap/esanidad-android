if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = 'https://esanidad.herokuapp.com/'
} else {
    var DNI = JSON.parse(sessionStorage.getItem("data"));
    var recurso = "https://esanidad.herokuapp.com/consultaEspecialidades";
    var datosNombre = [];

    var data = {
        dni: DNI,
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
    }).done(function(data, textStatus, jqXHR) {
        if (data.type == "OK") {
            for (var i = 0; i < (data.numero); i++) {
                datosNombre[i] = data['nombreEspecialidad' + i];
            }
            mostrarEspecialidades(datosNombre);
        }
    }), 10000);
}

function mostrarEspecialidades(datosNombre) {
    var select_especialidades = "";

    for (var i = 0; i < datosNombre.length; i++) {
        select_especialidades += '<option>' + datosNombre[i] + '</option>';
    }
    $("#especialidad").append(select_especialidades);
}

function crearMedico() {
    var dni = document.getElementById("dni").value;
    var especialidad = document.getElementById("especialidad").value;
    var DNI = JSON.parse(sessionStorage.getItem("data"));

    var recurso = "https://esanidad.herokuapp.com/crearMedico";
    var data = {
        type: "medico",
        dni: dni,
        especialidad: especialidad
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
            console.log(data.message);
            if (data.type == "OK") {
                setTimeout(location.href = 'https://esanidad.herokuapp.com/gestor', 10000);
            } else {
                if (data.type = "error") {
                    alert("Error al crear el médico, contacte con el servicio de soporte.");
                }
            }
        }), 10000);
}

function cerrarSesion() {
    sessionStorage.removeItem("data");
    setTimeout(location.href = 'https://esanidad.herokuapp.com/', 10000);
}