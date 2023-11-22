document.addEventListener("DOMContentLoaded", function () {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/ingreso", requestOptions)
    .then(response => response.json()) // Parse the response as JSON
    .then(data => {
      // Update the HTML elements with the retrieved data
      document.getElementById("fechaDeEntrada").textContent = "Fecha: " + data.fecha;
      document.getElementById("horaDeEntrada").textContent = "Hora: " + data.hora;

      // Llama a la función startCronometro con la hora de entrada como parámetro
      startCronometro(data.hora);
    })
    .catch(error => console.log('error', error));
});

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
  document.getElementById("tiempoTranscurrido").textContent = `${tiempoFormateado}`;

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


/*despues de 1 minuto si no ha pulsado el boton redirigir automaticamente si si lo presiono ir a index
independientemente se cambiara el edo a 2*/

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

// Llama a la función después de 1 minuto (60000 milisegundos)
setTimeout(redirectToIndex, 60000);

// Agrega un evento de clic al botón "CONTINUAR"
document.getElementById('continuarBtn').addEventListener('click', function () {
  redirectToIndex();
});




