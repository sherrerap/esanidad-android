if (sessionStorage.getItem("data") == null) {
    alert("no tienes acceso a esta vista");
    location.href = '../index.html'
} else {
    var DNI = JSON.parse(sessionStorage.getItem("data"));
    var recurso = "https://esanidad.herokuapp.com/consultaEspecialidades";
    var datosNombreEspecialidad = [];
    var datosDuracion = [];
    var datosHoraInicio = [];
    var datosHoraFin = [];
    var datosNombreEspecialidad = []

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
                datosNombreEspecialidad[i] = data['nombreEspecialidad' + i];

            }
            mostrarEspecialidades(datosNombreEspecialidad);
        }
    }), 10000);
}

function mostrarEspecialidades(datosNombre) {
    var cuerpo_especialidades = "";

    for (var i = 0; i < datosNombre.length; i++) {
        cuerpo_especialidades += '<tr>' + '<td>' + datosNombreEspecialidad[i] + '</td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="eliminarEspecialidad(id);">' + 'Eliminar' + '</a></td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="modificarEspecialidad(id);">' + 'Modificar' + '</a></td>' + '</tr>';
    }
    $("#tablaEspecialidadCuerpo").append(cuerpo_especialidades);
}

var recurso = "https://esanidad.herokuapp.com/listaMedicos";
var datosDNI = [];
var datosNombre = [];
var datosApellido = [];
var datosEspecialidad = [];
var datosCentro = [];

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
            datosNombre[i] = data['nombre' + i];
            datosApellido[i] = data['apellidos' + i];
            datosEspecialidad[i] = data['especialidad' + i];
            datosCentro[i] = data['centro' + i];
        }
        mostrarMedicos(datosDNI, datosNombre, datosApellido, datosEspecialidad, datosCentro);
    }
}), 10000);

function mostrarMedicos(datosDNI, datosNombre, datosApellido, datosEspecialidad, datosCentro) {
    var cuerpo_medico = "";

    for (var i = 0; i < datosDNI.length; i++) {
        var data = {
            dni: datosDNI[i],
        };
        data = JSON.stringify(data);
        console.log(datosEspecialidad[i])
        console.log(datosCentro[i]);
        cuerpo_medico += '<tr>' + '<td>' + datosDNI[i] + '</td>' +
            '<td>' + datosNombre[i] + ' </td>' +
            '<td>' + datosApellido[i] + ' </td>' +
            '<td>' + datosEspecialidad[i] + ' </td>' +
            '<td>' + datosCentro[i] + ' </td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="eliminarMedico(id);">' + 'Eliminar' + '</a></td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="modificarMedico(id);">' + 'Modificar' + '</a></td>' + '</tr>';
    }
    $("#tablaMedicoCuerpo").append(cuerpo_medico);
}

function modificarMedico(id) {
    sessionStorage.setItem("dni", JSON.stringify(datosDNI[id]));
    sessionStorage.setItem("nombre", JSON.stringify(datosNombre[id]));
    sessionStorage.setItem("apellidos", JSON.stringify(datosApellido[id]));
    sessionStorage.setItem("especialidad", JSON.stringify(datosEspecialidad[id]));
    sessionStorage.setItem("centro", JSON.stringify(datosCentro[id]));
    location.href = '../views/modificarMedico.html'
}


function eliminarEspecialidad(id) {
    var recurso = "https://esanidad.herokuapp.com/eliminarEspecialidad";
    var data = {
        nombreEspecialidad: datosNombreEspecialidad[id]
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
            setTimeout(location.href = './gestor.html', 10000);
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
            setTimeout(location.href = './gestor.html', 10000);
        }
    }), 10000);
}

function modificarEspecialidad(id) {
    sessionStorage.setItem("nombreEspecialidad", JSON.stringify(datosNombreEspecialidad[id]));
    location.href = '../views/modificarEspecialidad.html'
}

function cerrarSesion() {
    sessionStorage.removeItem("data");
    setTimeout(location.href = '../index.html', 10000);
}

var recurso = "https://esanidad.herokuapp.com/consultaHorarios";
var datosDNIMedico = [];
var datosDuracionM = [];
var datosHoraInicioM = [];
var datosHoraFinM = [];

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
            datosDNIMedico[i] = data['dniMedico' + i];
            datosDuracionM[i] = data['duracionCita' + i];
            datosHoraInicioM[i] = data['horaInicio' + i];
            datosHoraFinM[i] = data['horaFin' + i];
        }
        mostrarHorarios(datosDNIMedico, datosDuracionM, datosHoraInicioM, datosHoraFinM);
    }
}), 10000);

function mostrarHorarios(datosDNIMedico, datosDuracionM, datosHoraInicioM, datosHoraFinM) {
    var cuerpo_horario = "";

    for (var i = 0; i < datosDNIMedico.length; i++) {
        cuerpo_horario += '<tr>' + '<td>' + datosDNIMedico[i] + '</td>' + '<td>' +
            datosDuracionM[i] + ' minutos </td>' + '<td>' + datosHoraInicioM[i] + '</td>' + '<td>' + datosHoraFinM[i] + '</td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="eliminarHorario(id);">' + 'Eliminar' + '</a></td>' +
            '<td><a id=' + i + ' href="javascript:void(0);" onclick="modificarHorario(id);">' + 'Modificar' + '</a></td>' + '</tr>';
    }
    $("#tablaHorarioCuerpo").append(cuerpo_horario);
}

function modificarHorario(id) {
    sessionStorage.setItem("dniMedico", JSON.stringify(datosDNIMedico[id]));
    sessionStorage.setItem("tiempo", JSON.stringify(datosDuracionM[id]));
    sessionStorage.setItem("inicio", JSON.stringify(datosHoraInicioM[id]));
    sessionStorage.setItem("fin", JSON.stringify(datosHoraFinM[id]));
    location.href = '../views/modificarHorario.html'
}

function eliminarHorario(id) {
    var recurso = "https://esanidad.herokuapp.com/eliminarHorario";
    var data = {
        dniMedico: datosDNIMedico[id]
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
            console.log("horario eliminado");
            setTimeout(location.href = './gestor.html', 10000);
        }
    }), 10000);
}
function cerrarSesion() {
    sessionStorage.removeItem("data");
    setTimeout(location.href = '../index.html', 10000);
}