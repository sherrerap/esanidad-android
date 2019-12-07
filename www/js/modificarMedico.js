if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = '../index.html'
} else {
    var DNI = JSON.parse(sessionStorage.getItem("data"));
    var recurso = "https://esanidad.herokuapp.com/consultaEspecialidades";
    var recursoCentros = "https://esanidad.herokuapp.com/consultaCentros";
    var datosNombre = [];
    var datosCentros = [];

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

    setTimeout($.ajax({
        url: recursoCentros,
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
                datosCentros[i] = data['nombreCentro' + i];
            }
            mostrarCentros(datosCentros);
        }
    }), 10000);


    function mostrarCentros(datosCentros) {
        var select_centros = "";

        for (var i = 0; i < datosCentros.length; i++) {
            select_centros += '<option>' + datosCentros[i] + '</option>';
        }
        $("#centro").append(select_centros);
    }

    function mostrarEspecialidades(datosNombre) {
        var select_especialidades = "";

        for (var i = 0; i < datosNombre.length; i++) {
            select_especialidades += '<option>' + datosNombre[i] + '</option>';
        }
        $("#especialidad").append(select_especialidades);
    }
    var dni = JSON.parse(sessionStorage.getItem("dni"));
    var nombre = JSON.parse(sessionStorage.getItem("nombre"));
    var apellidos = JSON.parse(sessionStorage.getItem("apellidos"));
    var especialidad = JSON.parse(sessionStorage.getItem("especialidad"));
    var centro = JSON.parse(sessionStorage.getItem("centro"));

    document.getElementById("dni").value = dni;
    document.getElementById("nombre").value = nombre;
    document.getElementById("apellidos").value = apellidos;
    document.getElementById("especialidad").value = especialidad;
    document.getElementById("centro").value = centro;

    function modificarMedico() {
        var dni2 = JSON.parse(sessionStorage.getItem("dni"));
        var nombre2 = JSON.parse(sessionStorage.getItem("nombre"));
        var apellido2 = JSON.parse(sessionStorage.getItem("apellidos"));

        var dniNew = document.getElementById("dni").value;
        var nombreNew = document.getElementById("nombre").value;
        var apellidoNew = document.getElementById("apellidos").value;
        var centroNew = document.getElementById("centro").value;
        var especialidadNew = document.getElementById("especialidad").value;
        var tiempoCitaNew = document.getElementById("tiempoCita").value;
        var horaInicioNew = document.getElementById("horaInicio").value;
        var horaFinNew = document.getElementById("horaFin").value;


        var recurso = "https://esanidad.herokuapp.com/modificarMedico";
        var data = {
            type: "modificar especialidad",
            dniOld: dni2,
            dni: dniNew,
            nombre: nombreNew,
            apellidos: apellidoNew,
            centro: centroNew,
            especialidad: especialidadNew,
            tiempoCita: tiempoCitaNew,
            horaInicio: horaInicioNew,
            horaFin: horaFinNew
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
                    sessionStorage.removeItem("dni");
                    sessionStorage.removeItem("nombre");
                    sessionStorage.removeItem("apellidos");
                } else {
                    if (data.type = "error") {
                        alert("Error al modificar el médico, contacte con el servicio de soporte.");
                        sessionStorage.removeItem("dni");
                        sessionStorage.removeItem("nombre");
                        sessionStorage.removeItem("apellidos");
                    }
                    sessionStorage.removeItem("dni");
                    sessionStorage.removeItem("nombre");
                    sessionStorage.removeItem("apellidos");
                }

            }), 10000);
    }
}
function cerrarSesion() {
    sessionStorage.removeItem("data");
    setTimeout(location.href = '../index.html', 10000);
}