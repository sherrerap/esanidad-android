if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = '../index.html'
} else {
    var contenidoDespegable = true;
    var contenidoDespegableHoras = true;

    function volverAPedir() {

        contenidoDespegableHoras = true;
        var fh = document.getElementById("horaFecha");
        for (let i = fh.options.length; i >= 0; i--) {
            fh.remove(i);
        }

    }
}

function obtenerHoras() {

    var fechas = [];
    var Fecha = document.getElementById("fecha").value;
    Fecha = Fecha.substring(8, 10) + Fecha.substring(4, 8) + Fecha.substring(0, 4) + Fecha.substring(10, Fecha.length);
    Fecha = Fecha.replace("-", "/");
    Fecha = Fecha.replace("-", "/");


    if (contenidoDespegableHoras == true) {
        var recurso = "https://esanidad.herokuapp.com/getHoras";
        var data = {
            dniPaciente: JSON.parse(sessionStorage.getItem("data")),
            especialidad: document.getElementById("especialidad").value,
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

function mostrarEspecializaciones() {
    var nombreEspecialidad = [];
    var duracionCita = [];
    var horaInicio = [];
    var horaFin = [];



    if (contenidoDespegable == true) {
        var recurso = "https://esanidad.herokuapp.com/consultarEspecialdiadPaciente";
        var data = {
            dniPaciente: JSON.parse(sessionStorage.getItem("data")),
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
                    var combo = document.getElementById("especialidad");
                    for (var i = 0; i < (data.numero); i++) {




                        nombreEspecialidad[i] = data['nombreEspecialidad' + i];
                        duracionCita[i] = data['duracionCita' + i];
                        horaInicio[i] = data['horaInicio' + i];
                        horaFin[i] = data['horaFin' + i];

                        var x = document.getElementById("especialidad");
                        var option = document.createElement("option");
                        option.text = nombreEspecialidad[i];
                        x.add(option);
                        contenidoDespegable = false;


                    }




                } else {
                    if (data.type = "error") {
                        alert("Error al obtener las especialidades de las citas, contacte con el servicio de soporte.");
                    }


                }



            }), 10000);



    }







}

function pedirCita() {
    var fecha = document.getElementById("fecha").value;
    var especialidad = document.getElementById("especialidad").value;
    var DNI = JSON.parse(sessionStorage.getItem("data"));

    fecha = fecha.substring(8, 10) + fecha.substring(4, 8) + fecha.substring(0, 4) + fecha.substring(10, fecha.length);
    fecha = fecha.replace("T", " ");
    fecha = fecha.replace("-", "/");
    fecha = fecha.replace("-", "/");
    fecha = fecha + " " + document.getElementById("horaFecha").value + ":00";
    var recurso = "https://esanidad.herokuapp.com/pedirCita";
    var data = {
        type: "cita",
        dniPaciente: DNI,
        fecha: fecha,
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

            } else {
                if (data.type = "error") {
                    alert("Error al crear cita, contacte con el servicio de soporte.");
                }


            }

        }), 10000);

}
function cerrarSesion() {
    sessionStorage.removeItem("data");
    sessionStorage.removeItem("dniDoctor");
    sessionStorage.removeItem("especialidadMedico");
    setTimeout(location.href = '../index.html', 10000);
}