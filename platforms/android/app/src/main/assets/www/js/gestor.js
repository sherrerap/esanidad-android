if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = 'https://esanidad.herokuapp.com/'
} else {
    var DNI = JSON.parse(sessionStorage.getItem("data"));
    var recurso = "https://esanidad.herokuapp.com/consultaEspecialidades";
    var datosNombre = [];
    var datosDuracion = [];
    var datosHoraInicio = [];
    var datosHoraFin = [];

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
                datosDuracion[i] = data['duracionCita' + i];
                datosHoraInicio[i] = data['horaInicio' + i];
                datosHoraFin[i] = data['horaFin' + i];
            }
            mostrarEspecialidades(datosNombre, datosDuracion, datosHoraInicio, datosHoraFin);
        }
    }), 10000);
}

function mostrarEspecialidades(datosNombre, datosDuracion, datosHoraInicio, datosHoraFin) {
    var cuerpo_especialidades = "";

    for (var i = 0; i < datosNombre.length; i++) {
        cuerpo_especialidades += '<tr>' + '<td>' + datosNombre[i] + '</td>' + '<td>' +
            datosDuracion[i] + ' minutos </td>' + '<td>' + datosHoraInicio[i] + '</td>' + '<td>' + datosHoraFin[i] + '</td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="eliminarEspecialidad(id);">' + 'Eliminar' + '</a></td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="modificarEspecialidad(id);">' + 'Modificar' + '</a></td>' + '</tr>';
    }
    $("#tablaEspecialidadCuerpo").append(cuerpo_especialidades);
}

var recurso = "https://esanidad.herokuapp.com/listaMedicos";
var datosDNI = [];

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
            datosDNI[i] = data['dni' + i];
        }
        mostrarMedicos(datosDNI);
    }
}), 10000);

function mostrarMedicos(datosDNI) {
    var cuerpo_medico = "";

    for (var i = 0; i < datosDNI.length; i++) {
        var data = {
            dni: datosDNI[i],
        };
        data = JSON.stringify(data);
        cuerpo_medico += '<tr>' + '<td>' + datosDNI[i] + '</td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="eliminarMedico(id);">' + 'Eliminar' + '</a></td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="modificarMedico(id);">' + 'Modificar' + '</a></td>' + '</tr>';
    }
    $("#tablaMedicoCuerpo").append(cuerpo_medico);
}

function eliminarEspecialidad(id) {
    var recurso = "https://esanidad.herokuapp.com/eliminarEspecialidad";
    var data = {
        nombreEspecialidad: datosNombre[id]
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
            console.log(data);
            console.log("especialidad eliminada");
            setTimeout(location.href = 'https://esanidad.herokuapp.com/gestor', 10000);
        }
    }), 10000);
}

function eliminarMedico(id) {
    var recurso = "https://esanidad.herokuapp.com/eliminarMedico";
    var data = {
        dni: datosDNI[id]
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
            console.log(data);
            console.log("médico eliminado");
            setTimeout(location.href = 'https://esanidad.herokuapp.com/gestor', 10000);
        }
    }), 10000);
}

function modificarEspecialidad(id) {
    sessionStorage.setItem("nombre", JSON.stringify(datosNombre[id]));
    sessionStorage.setItem("tiempo", JSON.stringify(datosDuracion[id]));
    sessionStorage.setItem("inicio", JSON.stringify(datosHoraInicio[id]));
    sessionStorage.setItem("fin", JSON.stringify(datosHoraFin[id]));
    location.href = 'https://esanidad.herokuapp.com/modificarEspecialidad'
}

function cerrarSesion() {
    sessionStorage.removeItem("data");
    setTimeout(location.href = 'https://esanidad.herokuapp.com/', 10000);
}