document.addEventListener('DOMContentLoaded', function() {
    // Este código se ejecutará cuando el DOM esté completamente cargado

    // Agrega el evento al botón
    document.getElementById('loginButton').addEventListener('click', function(event) {
        // Evitar la recarga de la página
        event.preventDefault();

        // Obtener los valores de los campos de entrada
        var correo = document.getElementById('emailSignup').value;
        var contraseña = document.getElementById('passwordSignup').value;

        // Llamar a la función getTokenUsuario con los valores
        getTokenUsuario(correo, contraseña);
    });

    // Agrega el evento al enlace <a>
    document.getElementById('regresarLink').addEventListener('click', function(event) {
        // Evitar la recarga de la página
        event.preventDefault();

        // Llamar a la función closeAndReturn
        closeAndReturn();
        
        // Redirigir a index.html
        window.location.href = '/index.html';
    });

    function getTokenUsuario(correo, contraseña) {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        // Construir la URL con los valores de los campos de entrada
        var url = "http://localhost:8080/getTokenUsr?correo=" + encodeURIComponent(correo) + "&contraseña=" + encodeURIComponent(contraseña);

        fetch(url, requestOptions)
            .then(response => response.text())
            .then(result => {
                // Verificar si la respuesta es 0
                if (result.trim() === '0') {
                    // Mostrar una alerta si las credenciales son inválidas
                    alert('Correo o contraseña inválidos');
                } else {
                    // Llamar a la función setTokenEdo con el token obtenido
                    setTokenEdo(result);
                }
            })
            .catch(error => console.log('error', error));
    }

    function setTokenEdo(token) {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        // Construir la URL con el token y el estado deseados
        var url = "http://localhost:8080/setedoreg?token=" + encodeURIComponent(token) + "&edo=2";

        fetch(url, requestOptions)
            .then(response => response.text())
            .then(result => console.log(result),window.location.href = '/PensionLoby.html')
            .catch(error => console.log('error', error));
    }

    function closeAndReturn() {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch("http://localhost:8080/setedoreg2", requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
    }
});
