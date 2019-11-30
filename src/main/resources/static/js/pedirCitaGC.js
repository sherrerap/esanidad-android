if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = 'https://esanidad.herokuapp.com/'
} else {
    var contenidoDespegable = true;
    var contenidoDespegableHoras = true;

    var inputDNI = document.getElementById("dni").value = JSON.parse(sessionStorage
        .getItem("dniPacienteGC"));
}

function volverAPedir() {

    contenidoDespegableHoras = true;
    var fh = document.getElementById("horaFecha");
    for (let i = fh.options.length; i >= 0; i--) {
        fh.remove(i);
    }

}

function controlTipodni() {

    var Dni = document.getElementById("dni").value;
    var letraDNI = Dni.charAt(Dni.length - 1);

    var numeroDNI = parseInt(Dni.substr(0, Dni.length - 1));
    var numeroDNIString = Dni.substr(0, Dni.length - 1);

    if (((Number.isInteger(numeroDNI) == false) && (isNaN(letraDNI) == false)) ||
        (numeroDNIString.length != 8)) {
        alert("¡Has introducido el DNI incorrectamente!");
    } else {
        var cadena = "TRWAGMYFPDXBNJZSQVHLCKET";
        var posicion = numeroDNI % 23;
        var letra = cadena.substring(posicion, posicion + 1);

        var dniComparar = numeroDNI + letra;
        var dniComparar2 = numeroDNI + letra;
        if (letraDNI != letra) {
            alert("¡Has introducido mal la letra de su DNI!");
        } else {
            if (dniComparar == dniComparar2) {

                pedirCita();
            }
        }
    }

}

function mostrarEspecializaciones() {
    var nombreEspecialidad = [];
    var duracionCita = [];
    var horaInicio = [];
    var horaFin = [];

    if (((document.getElementById("dni").value) !== (sessionStorage.getItem("dniPacienteGC1"))) || (sessionStorage.getItem("dniPacienteGC1").length == 0)) {

        if ((sessionStorage.getItem("dniPacienteGC1") != 0)) {

            sessionStorage.removeItem("dniPacienteGC1");

        }

        var recurso = "https://esanidad.herokuapp.com/consultarEspecialdiadPaciente";
        var selectEspecializacion = document.getElementById("especialidad");
        var data = {
            dniPaciente: document.getElementById("dni").value,
        };
        data = JSON.stringify(data);

        setTimeout(
            $
            .ajax({
                url: recurso,
                type: "POST",
                data: data,
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .done(
                function(data, textStatus, jqXHR) {
                    console.log(data.type);
                    if (data.type == "OK") {

                        for (let i = selectEspecializacion.options.length; i >= 0; i--) {
                            selectEspecializacion.remove(i);
                        }

                        var combo = document
                            .getElementById("especialidad");
                        for (var i = 0; i < (data.numero); i++) {

                            nombreEspecialidad[i] = data['nombreEspecialidad' +
                                i];
                            duracionCita[i] = data['duracionCita' +
                                i];
                            horaInicio[i] = data['horaInicio' +
                                i];
                            horaFin[i] = data['horaFin' + i];

                            var x = document
                                .getElementById("especialidad");
                            var option = document
                                .createElement("option");
                            option.text = nombreEspecialidad[i];
                            x.add(option);
                            contenidoDespegable = false;
                            sessionStorage
                                .setItem(
                                    "dniPacienteGC1",
                                    document
                                    .getElementById("dni").value);

                        }

                    } else {
                        if (data.type = "error") {
                            alert("Error al obtener las especialidades de las citas, contacte con el servicio de soporte.");
                        }

                    }

                }), 10000);

    }
}

function mostrarHora() {

    var fechas = [];
    var Fecha = document.getElementById("fecha").value;
    Fecha = Fecha.substring(8, 10) + Fecha.substring(4, 8) +
        Fecha.substring(0, 4) + Fecha.substring(10, Fecha.length);
    Fecha = Fecha.replace("-", "/");
    Fecha = Fecha.replace("-", "/");

    if (contenidoDespegableHoras == true) {
        var recurso = "https://esanidad.herokuapp.com/getHoras";
        var data = {
            dniPaciente: document.getElementById("dni").value,
            especialidad: document.getElementById("especialidad").value,
            fecha: Fecha,
        };
        data = JSON.stringify(data);

        setTimeout(
            $
            .ajax({
                url: recurso,
                type: "POST",
                data: data,
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .done(
                function(data, textStatus, jqXHR) {
                    console.log(data.type);
                    if (data.type == "OK") {

                        for (var i = 0; i < (data.numero); i++) {

                            fechas[i] = data['hora' + i];

                            var x = document
                                .getElementById("horaFecha");
                            var option = document
                                .createElement("option");
                            option.text = fechas[i];
                            x.add(option);
                            contenidoDespegableHoras = false;

                        }

                    } else {
                        if (data.type = "error") {
                            alert("Error al obtener las horas de las citas, contacte con el servicio de soporte.");
                        }

                    }

                }), 10000);

    }

}

function pedirCita() {
    var fecha = document.getElementById("fecha").value;
    var hora = document.getElementById("horaFecha").value;
    var especialidad = document.getElementById("especialidad").value;
    var DNI = document.getElementById("dni").value;

    fecha = fecha + ' ' + hora;
    fecha = fecha.substring(8, 10) + fecha.substring(4, 8) +
        fecha.substring(0, 4) + fecha.substring(10, fecha.length) + ":00";
    fecha = fecha.replace("T", " ");
    fecha = fecha.replace("-", "/");
    fecha = fecha.replace("-", "/");
    var recurso = "https://esanidad.herokuapp.com/pedirCita";
    var data = {
        type: "cita",
        dniPaciente: DNI,
        fecha: fecha,
        especialidad: especialidad
    };
    data = JSON.stringify(data);

    setTimeout(
        $
        .ajax({
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
        .done(
            function(data, textStatus, jqXHR) {
                console.log(data.type);
                if (data.type == "OK") {
                    alert("OK: se ha procesado correctamente la petición de creación de la cita.");
                    setTimeout(
                        location.href = 'https://esanidad.herokuapp.com/gestorCitas',
                        10000);

                } else {
                    if (data.type = "error") {
                        alert("Error: se ha procesado incorrectamente la petición de creación de la cita.");
                    }

                }

            }), 10000);

}