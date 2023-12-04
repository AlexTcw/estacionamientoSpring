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

document.getElementById("popupbutton").addEventListener("click", function () {
  var pensionContainer = document.getElementById("pensionContainer");
  var overlay = document.getElementById("overlay");

  pensionContainer.style.display = "block"; // Muestra el contenedor
  overlay.style.display = "block"; // Muestra el fondo oscuro
});
