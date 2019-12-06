if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = '../index.html'
} else {
    var DNI = JSON.parse(sessionStorage.getItem("data"));
    var divCambioRol = document.getElementById("divCambioRol");
    var especialidadMedico = JSON.parse(sessionStorage.getItem("especialidadMedico"));
    sessionStorage.setItem("dniDoctor", DNI);

    var recurso = "https://esanidad.herokuapp.com/citasPaciente";
    var datosDNIP = [];
    var nombresMedico = [];
    var apellidosMedico = [];
    var datosES = [];
    var datosF = [];
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
            console.log("FUNCIONA");

            for (var i = 0; i < (data.numero); i++) {
                nombresMedico[i] = data['nombreMedico' + i];
                apellidosMedico[i] = data['apellidosMedico' + i];
                datosDNIP[i] = data['dniPaciente' + i];
                datosES[i] = data['especialidad' + i];
                datosF[i] = data['fecha' + i];
            }
            mostrarContenido(datosDNIP, datosES, datosF);
        }
        $("#table-basic").DataTable();
    }), 10000);

    console.log(especialidadMedico);

    if (especialidadMedico == null) {
        document.getElementById('divCambioRol').style.display = 'none';
    }
}

function cambiarRol() {

    alert("Esta cambiando el rol a Trabajador...")
    setTimeout(location.href = '../views/doctor.html', 10000);

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
            setTimeout(location.href = '../views/paciente.html', 10000);
        }
    }), 10000);
}

function modificarCita(id) {
    sessionStorage.setItem("fecha", JSON.stringify(datosF[id]));
    sessionStorage.setItem("dni", JSON.stringify(datosDNIP[id]));
    sessionStorage.setItem("especialidad", JSON.stringify(datosES[id]));
    location.href = '../views/modificarCita.html';
}

function mostrarContenido(datosDNIP, datosES, datosF) {
    var cuerpo = "";
    var recurso = "https://esanidad.herokuapp.com/anularCita";

    var cabecera = '<tr>' + '<th>FECHA</th>' + '<th>DNI PACIENTE</th>' +
        '<th>ESPECIALIDAD</th>' + '</tr>';

    for (var i = 0; i < datosDNIP.length; i++) {
        var data = {
            dniPaciente: datosDNIP[i],
            especialidad: datosES[i],
            fecha: datosF[i]
        };
        data = JSON.stringify(data);
        cuerpo += '<tr>' + '<td>' + datosF[i] + '</td>' + '<td>' +
            nombresMedico[i] + '</td>' + '<td>' + apellidosMedico[i] + '</td>' + '<td>' + datosES[i] +
            '</td>' + '<td><a id=' + i + ' href="javascript:void(0);" onclick="eliminarCita(id);">' + 'Eliminar' + '</a></td>' + '<td><a id=' + i + ' href="javascript:void(0);" onclick="modificarCita(id);">' + 'Modificar' + '</a></td>' + '</tr>';
    }
    $("#tablaCabecera").append(cabecera);
    $("#tablaCuerpo").append(cuerpo);
}

function cerrarSesion() {
    sessionStorage.removeItem("data");
    sessionStorage.removeItem("dniDoctor");
    sessionStorage.removeItem("especialidadMedico");
    setTimeout(location.href = '../index.html', 10000);
}