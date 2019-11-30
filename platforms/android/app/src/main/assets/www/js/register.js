function validate() {
    var nom = document.getElementById("name").value;
    var Apellidos = document.getElementById("apellido").value;
    var contraseña = document.getElementById("password").value;
    var contraseña2 = document.getElementById("confirm").value;
    var nss = document.getElementById("nss").value;
    var Dni = document.getElementById("dni").value;

    controlNumeroSS(nom, Apellidos, contraseña, contraseña2, nss, Dni);
    /*controlTipoContraseña(nom, Apellidos, contraseña, contraseña2, nss, Dni);
    controlTipodni(nom, Apellidos, contraseña, contraseña2, nss, Dni);
    controlEspacioVacio(nom, Apellidos, contraseña, contraseña2, nss, Dni);*/


    function controlNumeroSS(nom, Apellidos, contraseña, contraseña2, nss, Dni) {

        if (nss.length == 12) {
            controlTipoContraseña(nom, Apellidos, contraseña, contraseña2, nss, Dni);
        } else {
            if (nss.length != 12) {
                alert("¡Debe introducir correctamente su nº de Seguridad Social!");
            }
        }

    }

    function controlTipoContraseña(nom, Apellidos, contraseña, contraseña2, nss, Dni) {

        if (contraseña == contraseña2) {


            var contadorMayuscula = 0;
            var contadorMinuscula = 0;
            var contadorNumerico = 0;
            for (i = 0; i <= contraseña.length - 1; i++) {

                if (contraseña.charAt(i) == contraseña.charAt(i).toUpperCase() && (isNaN(contraseña.charAt(i)) == true)) {
                    contadorMayuscula++;
                }
                if (contraseña.charAt(i) == contraseña.charAt(i).toLowerCase() && (isNaN(contraseña.charAt(i)) == true)) {
                    contadorMinuscula++;
                }
                if (contraseña.charAt(i) == parseInt(contraseña.charAt(i))) {
                    contadorNumerico++;
                }


            }
            if (contadorMayuscula < 1 || contadorMinuscula < 1 || contadorNumerico < 1) {
                alert("¡Has introducido las contraseñas incorrectamente!")
            } else {
                controlTipodni(nom, Apellidos, contraseña, contraseña2, nss, Dni);
            }
        } else {
            alert("¡Deben de coincidir las dos contraseñas!")
        }
    }

    function controlTipodni(nom, Apellidos, contraseña, contraseña2, nss, Dni) {

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
                    controlEspacioVacio(nom, Apellidos, contraseña, contraseña2, nss, Dni);
                }
            }
        }


    }

    function controlEspacioVacio(nom, Apellidos, contraseña, contraseña2, nss, Dni) {

        if (nom == "" || Apellidos == "" || contraseña == "" || contraseña2 == "" || nss == "" || Dni == "") {

            alert("¡Debe introducir todos los campos para registrarse!");
        } else {
            enviarContenido(nom, Apellidos, contraseña, contraseña2, nss, Dni);
        }


    }

    function enviarContenido(nom, Apellidos, contraseña, contraseña2, nss, Dni) {

        var recurso = "https://esanidad.herokuapp.com/register";
        var data = {
            type: "register",
            nombre: nom,
            apellidos: Apellidos,
            pass: contraseña,
            numSS: nss,
            dni: Dni
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

                    setTimeout(location.href = '../index.html', 10000);

                } else {
                    if (data.type = "error") {
                        alert("¡Registro incorrecto o usuario ya existente!");
                    }


                }

            }), 10000);

    }




}