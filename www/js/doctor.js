if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = '../index.html'
} else {
    var datosDNIP = [];
    var datosES = [];
    var datosF = [];
    var datosH = [];


    var especialidadD = document.getElementById("especialidadDoctor").value = JSON.parse(sessionStorage.getItem("especialidadMedico"));
    var hola = document.getElementById("especialidadDoctor");
}


function obtenerCitaPaciente() {
    var DNI = sessionStorage.getItem("dniDoctor");
    var recurso = "https://esanidad.herokuapp.com/getCitas";
    var año = (document.getElementById("fecha").value).substr(0, 4);
    var mes = (document.getElementById("fecha").value).substr(5, 2);
    var dia = (document.getElementById("fecha").value).substr(8, 2);
    var Fecha = dia + '/' + mes + '/' + año + ' 00:00:00';
    console.log(Fecha);
    $("#tablaCuerpo").empty();
    datosDNIP = [];
    datosES = [];
    datosF = [];
    datosH = [];

    var data = {
        dni: DNI,
        fecha: Fecha
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
            console.log("FUNCIONA");

            for (var i = 0; i < (data.numero); i++) {

                datosDNIP[i] = data['dniPaciente' + i];
                datosES[i] = data['especialidad' + i];
                datosF[i] = data['fecha' + i];
                datosH[i] = datosF[i].substr(11, 18);
            }
            mostrarContenido(datosDNIP, datosES, datosF, datosH);
        }
        $("#table-basic").DataTable();
    }), 10000);

}

function cambioRolPaciente() {
    alert("Esta cambiando el rol a Paciente...");
    setTimeout(location.href = '../views/paciente.html', 10000);
}

function eliminarCita(id) {
    var recurso = "https://esanidad.herokuapp.com/anularCita";
    var data = {
        dniPaciente: datosDNIP[id],
        especialidad: datosES[id],
        fecha: datosF[id]
    }
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
    }).done(function(data) {
        if (data.type == "OK") {
            console.log("eliminar");
            console.log(data);
            setTimeout(location.href = '../views/eliminarCP.html', 10000);
        }
    }), 10000);
}


function mostrarContenido(datosDNIP, datosES, datosF, datosH) {
    var cuerpo = "";

    var cabecera = '<tr>' + '<th>FECHA</th>' + '<th>HORA</th>' +
        '<th>DNI PACIENTE</th>' + '<th>ESPECIALIDAD</th>' + '</tr>';

    for (var i = 0; i < datosDNIP.length; i++) {
        var data = {
            dniPaciente: datosDNIP[i],
            especialidad: datosES[i],
            fecha: datosF[i],
            hora: datosH[i]
        };
        data = JSON.stringify(data);
        cuerpo += '<tr>' + '<td>' + datosF[i].substr(0, 10) + '</td>' + '<td>' +
            datosH[i] + '</td>' + '<td>' + datosDNIP[i] + '</td>' + '<td>' + datosES[i] +
            '</td>' + '<td><a id=' + i + ' href="javascript:void(0);" onclick="eliminarCita(id);">' + 'Eliminar' + '</a></td>' + '</tr>';
    }
    $("#tablaCabecera").append(cabecera);
    $("#tablaCuerpo").append(cuerpo);
    data = "";
}

function cerrarSesion() {

    sessionStorage.removeItem("dniDoctor");
    sessionStorage.removeItem("especialidadMedico");
    setTimeout(location.href = '../index.html', 10000);

}