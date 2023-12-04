document.addEventListener("DOMContentLoaded", function () {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/getFechaEstByToken", requestOptions)
    .then(response => response.json())
    .then(data => {
      // Update the HTML elements with the retrieved data
      document.getElementById("fechaDeEntrada").textContent = data.fecha;
      document.getElementById("horaDeEntrada").textContent = data.fechaEntrada;
      document.getElementById("subtotal").textContent = data.subTotal + " MNX";
      document.getElementById("total").textContent = data.total + " MNX";

      // Convierte la fechaCompleta a un objeto Date
      const fechaCompletaDate = new Date(data.fechaCompleta);

      // Calcula la diferencia con la fecha y hora actual
      const now = new Date();
      const diff = now.getTime() - fechaCompletaDate.getTime();

      // Para una mejor visualización, puedes convertir la diferencia a otros formatos:

      // Días, horas, minutos y segundos
      const days = Math.floor(diff / (1000 * 60 * 60 * 24));
      const hours = Math.floor(diff / (1000 * 60 * 60)) % 24;
      const minutes = Math.floor(diff / (1000 * 60)) % 60;
      const seconds = Math.floor(diff / 1000) % 60;

      // Crea un cronómetro de tiempo transcurrido y lo muestra en el elemento HTML
      cronometro(days, hours, minutes, seconds);

      // Agrega una nueva solicitud GET
      var requestOptionsPlacas = {
        method: 'GET',
        redirect: 'follow'
      };

      fetch("http://localhost:8080/getUsrPensionTblPlacasByToken", requestOptionsPlacas)
        .then(response => response.text())
        .then(result => {
          // Procesa el resultado para quitar los corchetes
          const processedResult = result.replace(/\[|\]/g, '');

          // Muestra el resultado procesado en el elemento con el ID "bodyPLacas"
          document.getElementById("bodyPLacas").textContent = processedResult;
        })
        .catch(error => console.log('error', error));

    })
    .catch(error => console.log('error', error));
});



function cronometro(initialDays, initialHours, initialMinutes, initialSeconds) {
  // Inicializa las variables
  let start = new Date().getTime();

  // Crea un temporizador que se ejecute cada segundo
  setInterval(() => {
    // Calcula el tiempo transcurrido desde la última petición
    let now = new Date();
    let diff = now.getTime() - start;

    // Calcula los días, horas, minutos y segundos transcurridos
    let elapsedDays = Math.floor(diff / (1000 * 60 * 60 * 24)) + initialDays;
    let elapsedHours = Math.floor(diff / (1000 * 60 * 60)) % 24 + initialHours;
    let elapsedMinutes = Math.floor(diff / (1000 * 60)) % 60 + initialMinutes;
    let elapsedSeconds = Math.floor(diff / 1000) % 60 + initialSeconds;

    // Ajusta los minutos y las horas si exceden 60
    if (elapsedSeconds >= 60) {
      elapsedMinutes += Math.floor(elapsedSeconds / 60);
      elapsedSeconds %= 60;
    }
    if (elapsedMinutes >= 60) {
      elapsedHours += Math.floor(elapsedMinutes / 60);
      elapsedMinutes %= 60;
    }

    // Actualiza el tiempo transcurrido en el elemento HTML
    document.getElementById("tiempoTranscurrido").textContent = `${elapsedHours}h:${elapsedMinutes}m:${elapsedSeconds}s`;
  }, 1000);
}

function mostrarNuevaPlaca() {
  const newPlacaDiv = document.getElementById("newPlaca");
  newPlacaDiv.style.display = "block";
}

const addButton = document.getElementById("addButton");
addButton.addEventListener("click", mostrarNuevaPlaca);

document.getElementById("enviarButton").addEventListener("click", function () {
  // Obtener el valor del input con id "placaInput"
  var placa = document.getElementById("placaInput").value;

  // Llamar a la función con el valor de placa como parámetro
  enviarPlaca(placa);
});

function enviarPlaca(placa) {
  var requestOptions = {
    method: 'POST',
    redirect: 'follow'
  };

  // Concatenar el valor de placa a la URL
  var url = "http://localhost:8080/updateUsuTablePlacas?placa=" + placa;

  fetch(url, requestOptions)
    .then(response => response.text())
    .then(result => window.location.reload())
    .catch(error => console.log('error', error));
}

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
