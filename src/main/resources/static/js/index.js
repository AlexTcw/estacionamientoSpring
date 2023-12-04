function redirectByEdoRegistry() {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };
    fetch("http://localhost:8080/getedoreg", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result);
            if (result.trim() === "1") {
                console.log("Estado 1 detectado");
                window.location.href = 'bienvenida.html';
            }
            if (result.trim() === "2") {
                console.log("Estado 2 detectado. esperando instrucciones");
            }
            if (result.trim() === "3") {
                console.log("Estado 3 detectado. Redirigiendo a pago.html");
                window.location.href = 'pago.html';
            }
            if (result.trim() === "4") {
                console.log("Estado 4 detectado. Redirigiendo a pensionLoby.html");
                window.location.href = 'PensionLoby.html';
            }
            if (result.trim() === "5") {
                console.log("Estado 4 detectado. Redirigiendo a AdminLoby.html");
                window.location.href = 'AdminLoby.html';
            }
        })
        .catch(error => console.log('error', error));
}
setInterval(redirectByEdoRegistry, 1000);