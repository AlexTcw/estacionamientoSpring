function validateForm() {
    var nombre = document.getElementById("nombre").value;
    var apellido = document.getElementById("apellido").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    // Verifica si los campos requeridos están llenos
    if (nombre === "" || apellido === "" || email === "" || password === "") {
        alert("Por favor, complete todos los campos obligatorios.");
    } else {
        // Puedes agregar aquí lógica adicional si es necesario
        alert("Registro exitoso. Puedes avanzar.");
    }
}

function getLastToken() {
    var requestOptions = {
      method: 'GET',
      redirect: 'follow'
    };

    // Devuelve la promesa de la llamada fetch
    return fetch("http://localhost:8080/lastId", requestOptions)
      .then(response => response.text())
      .catch(error => console.log('error', error));
  }

  async function findUser() {
    // Obtener el último token usando la función getLastToken()
    var lastToken = await getLastToken();

    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    var requestOptions = {
      method: 'GET',
      redirect: 'follow'
    };

    // Asegúrate de agregar los valores de correo, contraseña y token a la URL de la solicitud
    var url = "http://localhost:8080/existUsu?correo="+encodeURIComponent(email)+"&pswd="+encodeURIComponent(password)+"&token="+encodeURIComponent(lastToken);

    fetch(url, requestOptions)
      .then(response => response.text())
      .then(result => console.log(result))
      .catch(error => console.log('error', error));
  }