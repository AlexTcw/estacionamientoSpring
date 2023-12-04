document.addEventListener('DOMContentLoaded', function () {
    // Este código se ejecutará cuando el DOM esté completamente cargado

    // Agrega el evento al botón
    document.getElementById('loginButton').addEventListener('click', function (event) {
        // Evitar la recarga de la página
        event.preventDefault();

        // Obtener los valores de los campos de entrada
        var correo = document.getElementById('emailSignup').value;
        var contraseña = document.getElementById('passwordSignup').value;

        // Llamar a la función validateAdminUsu con los valores
        validateAdminUsu(correo, contraseña);
    });

    // Función para validar el usuario administrador
    function validateAdminUsu(correo, contraseña) {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch(`http://localhost:8080/loginUsuForAdminByCorreoAndPassword?correo=${correo}&contraseña=${contraseña}`, requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result);

                // Verificar si el resultado es "true" (ajusta según la respuesta real)
                if (result.trim().toLowerCase() === "true") {
                    // Redirigir a index.html
                    window.location.href = "index.html";
                } else {
                    // Mostrar una alerta si el resultado es "false"
                    alert("Usuario o contraseña incorrectos");
                }
            })
            .catch(error => console.log('error', error));
    }

    // Función para redirigir a index.html
    function redirectToIndex() {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch("http://localhost:8080/setedoreg2", requestOptions)
            .then(response => response.text())
            .then(result => {
                window.location.href = 'index.html';
            })
            .catch(error => console.log('error', error));
    }

    // Agrega el evento al enlace "REGRESAR"
    document.getElementById('comeBackButton').addEventListener('click', function () {
        redirectToIndex();
    });
});
