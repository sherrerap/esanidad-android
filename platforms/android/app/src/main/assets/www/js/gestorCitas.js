if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = '../index.html'
} else {
    var datosDNIP = [];
    var datosES = [];
    var datosF = [];
}

function mandarDatosEnter(event) {

    if (event.keyCode === 13) {
        controlTipodniGC();
    }

}


function controlTipodniGC() {

    var Dni = document.getElementById("dni").value;
    var letraDNI = Dni.charAt(Dni.length - 1);

    var numeroDNI = parseInt(Dni.substr(0, Dni.length - 1));
    var numeroDNIString = Dni.substr(0, Dni.length - 1);


    if (((Number.isInteger(numeroDNI) == false) && (isNaN(letraDNI) == false)) || (numeroDNIString.length != 8)) {
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
                sessionStorage.setItem("dniPacienteGC", JSON.stringify(Dni));

                mostrarCitas();
            }
        }
    }


}


function mostrarBtnBuscar() {

    var cargarCitas = document.getElementById('cargarCitas');
    var dni = document.getElementById("dni");

    cargarCitas = document.getElementById('cargarCitas').style.display = 'inline';
    document.getElementById("dni").value = "";

    datosDNIP = [];
    datosES = [];
    datosF = [];

    mostrarContenido(datosDNIP, datosES, datosF);
    setTimeout(location.href = './gestorCitas.html', 10000);
}

function mostrarCitas() {


    var cargarCitas = JSON.parse(sessionStorage.getItem("dniPacienteGC"));

    var DNI = document.getElementById("dni").value;
    var recurso = "https://esanidad.herokuapp.com/citasPaciente";

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

                datosDNIP[i] = data['dniPaciente' + i];
                datosES[i] = data['especialidad' + i];
                datosF[i] = data['fecha' + i];
            }
            mostrarContenido(datosDNIP, datosES, datosF);
        }
        $("#table-basic").DataTable();
        cargarCitas = document.getElementById('cargarCitas').style.display = 'none';
    }), 10000);



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
            alert("OK: se ha procesado correctamente la petición de eliminación de la cita.");
            setTimeout(location.href = './gestorCitas.html', 10000);
        } else {

            if (data.type == "error") {
                alert("Error: se ha procesado incorrectamente la petición de eliminación de la cita.");
            };
        };

    }), 10000);
}

function modificarCita(id) {
    sessionStorage.setItem("fechaPacienteGC", JSON.stringify(datosF[id]));
    sessionStorage.setItem("dniPacienteGC", JSON.stringify(datosDNIP[id]));
    sessionStorage.setItem("especialidadPacienteGC", JSON.stringify(datosES[id]));
    location.href = '../views/modificarCitaGC.html';
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
            datosDNIP[i] + '</td>' + '<td>' + datosES[i] +
            '</td>' + '<td><a id=' + i + ' href="javascript:void(0);" onclick="eliminarCita(id);">' + 'Eliminar' + '</a></td>' + '<td><a id=' + i + ' href="javascript:void(0);" onclick="modificarCita(id);">' + 'Modificar' + '</a></td>' + '</tr>';
    }
    $("#tablaCabecera").append(cabecera);
    $("#tablaCuerpo").append(cuerpo);
}

function cerrarSesion() {

    sessionStorage.removeItem("dniPacienteGC");
    setTimeout(location.href = '../index.html', 10000);

}