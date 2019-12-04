if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = '../index.html'
} else {
    var contenidoDespegableHoras = true;
}

function restaurarH() {

    contenidoDespegableHoras = true;
    var fh = document.getElementById("horaFecha");
    for (let i = fh.options.length; i >= 0; i--) {
        fh.remove(i);
    }


}


function obtenerH() {

    var fechas = [];
    var Fecha = document.getElementById("fecha").value;
    Fecha = Fecha.substring(8, 10) + Fecha.substring(4, 8) + Fecha.substring(0, 4) + Fecha.substring(10, Fecha.length);
    Fecha = Fecha.replace("-", "/");
    Fecha = Fecha.replace("-", "/");

    if (contenidoDespegableHoras == true) {
        var recurso = "https://esanidad.herokuapp.com/getHoras";
        var data = {
            dniPaciente: JSON.parse(sessionStorage.getItem("dniPacienteGC")),
            especialidad: JSON.parse(sessionStorage.getItem("especialidadPacienteGC")),
            fecha: Fecha,
        };
        data = JSON.stringify(data);

        setTimeout($.ajax({
                url: recurso,
                type: "POST",
                data: data,
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .done(function(data, textStatus, jqXHR) {
                console.log(data.type);
                if (data.type == "OK") {

                    for (var i = 0; i < (data.numero); i++) {




                        fechas[i] = data['hora' + i];


                        var x = document.getElementById("horaFecha");
                        var option = document.createElement("option");
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

function modificarCita() {

    var fechaActual = JSON.parse(sessionStorage.getItem("fechaPacienteGC"));
    var fecha = document.getElementById("fecha").value;
    var especialidad = JSON.parse(sessionStorage.getItem("especialidadPacienteGC"));
    var DNI = JSON.parse(sessionStorage.getItem("dniPacienteGC"));


    fecha = fecha.substring(8, 10) + fecha.substring(4, 8) + fecha.substring(0, 4) + fecha.substring(10, fecha.length);
    fecha = fecha.replace("T", " ");
    fecha = fecha.replace("-", "/");
    fecha = fecha.replace("-", "/");
    fecha = fecha + ' ' + document.getElementById("horaFecha").value + ':00';

    var recurso = "https://esanidad.herokuapp.com/modificarCita";
    var data = {
        type: "cita",
        dniPaciente: DNI,
        fechaActual: fechaActual,
        fechaModificar: fecha,
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
            if (data.type == "OK") {



                alert("OK: se ha procesado correctamente la petición de modificación de la cita.");
                setTimeout(location.href = '../views/gestorCitas.html', 10000);


            } else {
                if (data.type = "error") {
                    alert("Error: se ha procesado incorrectamente la petición de modificación de la cita.");
                    sessionStorage.removeItem("fechaPacienteGC");
                    sessionStorage.removeItem("especialidadPacienteGC");
                    sessionStorage.removeItem("dniPacienteGC");
                }
                sessionStorage.removeItem("fechaPacienteGC");
                sessionStorage.removeItem("especialidadPacienteGC");
                sessionStorage.removeItem("dniPacienteGC");
            }

        }), 10000);

}
function cerrarSesion() {

    sessionStorage.removeItem("dniPacienteGC");
    setTimeout(location.href = '../index.html', 10000);

}