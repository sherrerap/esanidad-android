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
            dniPaciente: JSON.parse(sessionStorage.getItem("data")),
            especialidad: JSON.parse(sessionStorage.getItem("especialidad")),
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

    var fechaActual = JSON.parse(sessionStorage.getItem("fecha"));
    var fecha = document.getElementById("fecha").value;
    var especialidad = JSON.parse(sessionStorage.getItem("especialidad"));
    var DNI = JSON.parse(sessionStorage.getItem("dni"));

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

                setTimeout(location.href = '../views/paciente.html', 10000);
                sessionStorage.removeItem("fecha");
                sessionStorage.removeItem("especialidad");
                sessionStorage.removeItem("dni");

            } else {
                if (data.type = "error") {
                    alert("Error al crear cita, contacte con el servicio de soporte.");
                    sessionStorage.removeItem("fecha");
                    sessionStorage.removeItem("especialidad");
                    sessionStorage.removeItem("dni");
                }
                sessionStorage.removeItem("fecha");
                sessionStorage.removeItem("especialidad");
                sessionStorage.removeItem("dni");
            }

        }), 10000);

}
function cerrarSesion() {
    sessionStorage.removeItem("data");
    sessionStorage.removeItem("dniDoctor");
    sessionStorage.removeItem("especialidadMedico");
    setTimeout(location.href = '../index.html', 10000);
}