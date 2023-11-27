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

function getHoraDeEntrada(token) {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  // Usamos el token en la URL
  fetch(`http://localhost:8080/ingreso?token=${token}`, requestOptions)
    .then(response => response.json())  // Parsea la respuesta como JSON
    .then(result => {
      // Muestra la fecha y hora de entrada
      document.getElementById("fechaDeEntrada").textContent = result.fecha;
      document.getElementById("horaDeEntrada").textContent = result.hora;

      // Inicia el cronómetro
      startCronometro(result.hora);
    })
    .catch(error => console.log('error', error));
}

function startCronometro(horaDeEntrada) {
  // Obtén la hora actual
  var horaActual = new Date();

  // Parsea la hora de entrada a un objeto Date
  var horaEntrada = new Date(`${horaActual.toDateString()} ${horaDeEntrada}`);

  // Calcula la diferencia en milisegundos
  var diferencia = horaActual - horaEntrada;

  // Convierte la diferencia a segundos
  var segundosTranscurridos = Math.floor(diferencia / 1000);

  // Calcula horas, minutos y segundos
  var horas = Math.floor(segundosTranscurridos / 3600);
  var minutos = Math.floor((segundosTranscurridos % 3600) / 60);
  var segundos = segundosTranscurridos % 60;

  // Formatea la cadena en el formato deseado
  var tiempoFormateado = `${horas}:${minutos}:${segundos}`;

  // Actualiza el contenido del elemento <p> con el tiempo transcurrido
  document.getElementById("tiempoTranscurrido").textContent = `Tiempo transcurrido: ${tiempoFormateado}`;

  // Actualiza cada segundo
  setInterval(function () {
    segundosTranscurridos++;
    horas = Math.floor(segundosTranscurridos / 3600);
    minutos = Math.floor((segundosTranscurridos % 3600) / 60);
    segundos = segundosTranscurridos % 60;
    tiempoFormateado = `${horas}:${minutos}:${segundos}`;
    document.getElementById("tiempoTranscurrido").textContent = `${tiempoFormateado}`;
  }, 1000);
}
function tryPay(token) {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch(`http://localhost:8080/try-pay?token=${token}`, requestOptions)
    .then(response => response.json())
    .then(result => {
      document.getElementById("subtotal").textContent = "$ " + result.subTotal + " MNX";
      document.getElementById("total").textContent = "$ " + result.total + " MNX";
    })
    .catch(error => console.log('error', error));
}
// Llamada a las funciones
getLastToken().then(token => getHoraDeEntrada(token));
getLastToken().then(token => tryPay(token));

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

document.getElementById('comeBackButton').addEventListener('click', function () {
  redirectToIndex();
});

document.getElementById("popupbutton").addEventListener("click", function() {
  var pensionContainer = document.getElementById("pensionContainer");
  var overlay = document.getElementById("overlay");

  pensionContainer.style.display = "block"; // Muestra el contenedor
  overlay.style.display = "block"; // Muestra el fondo oscuro
});
