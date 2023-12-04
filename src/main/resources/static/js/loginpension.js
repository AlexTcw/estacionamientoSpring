document.addEventListener("DOMContentLoaded", function () {
  var signupButton = document.getElementById("signup-button");
  signupButton.addEventListener("click", validateAndSignup);
});

function validateAndSignup() {
  // Validar el formulario antes de continuar
  if (!validateForm()) {
    // Si la validación falla, no realizar el registro
    return;
  }

  // Si la validación es exitosa, realizar la acción de registro
  signupfunction();
}

function validateForm() {
  // Obtener los valores de los campos del formulario
  var nombre = document.getElementById("nombre").value;
  var apellido = document.getElementById("placa").value;
  var email = document.getElementById("emailSignup").value;
  var password = document.getElementById("passwordSignup").value;

  // Verificar si los campos requeridos están vacíos
  if (nombre.trim() === '' || apellido.trim() === '' || email.trim() === '' || password.trim() === '') {
    alert('Por favor, complete todos los campos.');
    return false;
  }

  // Puedes agregar más validaciones según sea necesario

  return true; // El formulario es válido
}

function signupfunction() {
  // Obtener valores de los campos de correo y contraseña
  var email = document.getElementById("emailSignup").value;
  var password = document.getElementById("passwordSignup").value;
  var placa = document.getElementById("placa").value;
  var nombreUsu = document.getElementById("nombre").value;

  // Construir la URL con los valores de correo, contraseña y otros datos
  var url = "http://localhost:8080/createUsuPension?correo=" + email + "&pswd=" + password + "&nombre=" + nombreUsu + "&placa=" + placa;
  var requestOptions = {
    method: 'POST',
    redirect: 'follow'
  };

  // Realizar la llamada fetch con la URL actualizada
  fetch(url, requestOptions)
    .then(response => response.text())
    .then(result => {
      // Mostrar la respuesta del servidor en un alert
      alert(result);
      console.log(result); // También puedes imprimirlo en la consola si lo deseas
      window.location.href = 'PensionLoby.html';
    })
    .catch(error => console.log('error', error));
}



