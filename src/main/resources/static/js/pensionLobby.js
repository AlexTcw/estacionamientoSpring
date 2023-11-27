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

document.getElementById("enviarButton").addEventListener("click", function () {
  getLastToken().then(token => {
    updatePlacas(token);
  });
});

// La función updatePlacas permanece igual
function updatePlacas(token) {
  var placa = document.getElementById("placaInput").value;

  var requestOptions = {
    method: 'POST',
    redirect: 'follow'
  };

  fetch(`http://localhost:8080/updateUsuTable?token=${token}&placa=${placa}`, requestOptions)
    .then(response => response.text())
    .then(result => {
      console.log(result);
      // Recargar la página después de actualizar las placas
      location.reload();
    })
    .catch(error => console.log('error', error));
}

function getDataUsr(token) {

  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch(`http://localhost:8080/getUsrInfo?token=${token}`, requestOptions)
    .then(response => response.json())
    .then(result => {
      console.log(result);

      document.getElementById("Usuario").textContent = result.nombreUsu;

      // Accede al tbody de la tabla donde se mostrarán las placas
      var tbody = document.getElementById("bodyPLacas");

      // Elimina cualquier contenido existente en el tbody
      tbody.innerHTML = "";

      // Verifica si hay placas en el resultado
      if (result.placas && result.placas.length > 0) {
        // Itera sobre las placas y crea filas para cada una
        result.placas.forEach(placa => {
          var row = document.createElement("tr");
          var cell = document.createElement("td");
          cell.textContent = placa;
          row.appendChild(cell);
          tbody.appendChild(row);
        });
      } else {
        // Si no hay placas, puedes agregar un mensaje o realizar alguna acción
        var row = document.createElement("tr");
        var cell = document.createElement("td");
        cell.textContent = "No hay placas asociadas.";
        row.appendChild(cell);
        tbody.appendChild(row);
      }
    })
    .catch(error => console.log('error', error));
}

function mostrarNuevaPlaca() {
  const newPlacaDiv = document.getElementById("newPlaca");
  newPlacaDiv.style.display = "block";
}

const addButton = document.getElementById("addButton");
addButton.addEventListener("click", mostrarNuevaPlaca);




function startCronometro(horaDeEntrada) {
  // Obtén la hora actual
  var horaActual = new Date();

  // Parsea la hora de entrada a un objeto Date
  var horaEntrada = new Date(`${horaActual.toDateString()} ${horaDeEntrada}`);

  // Calcula la diferencia en milisegundos
  var diferencia = horaActual - horaEntrada;

  // Convierte la diferencia a segundos
  var segundosTranscurridos = Math.floor(diferencia / 1000);

  // Calcula días, horas, minutos y segundos
  var dias = Math.floor(segundosTranscurridos / (24 * 3600));
  var horas = Math.floor((segundosTranscurridos % (24 * 3600)) / 3600);
  var minutos = Math.floor((segundosTranscurridos % 3600) / 60);
  var segundos = segundosTranscurridos % 60;

  // Formatea la cadena en el formato deseado
  var tiempoFormateado = `${dias}d ${horas}h ${minutos}m ${segundos}s`;

  // Actualiza el contenido del elemento <p> con el tiempo transcurrido
  document.getElementById("tiempoTranscurrido").textContent = `Tiempo transcurrido: ${tiempoFormateado}`;

  // Actualiza cada segundo
  setInterval(function () {
    segundosTranscurridos++;
    dias = Math.floor(segundosTranscurridos / (24 * 3600));
    horas = Math.floor((segundosTranscurridos % (24 * 3600)) / 3600);
    minutos = Math.floor((segundosTranscurridos % 3600) / 60);
    segundos = segundosTranscurridos % 60;
    tiempoFormateado = `${dias}d ${horas}h ${minutos}m ${segundos}s`;
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
getLastToken().then(token => getDataUsr(token));
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

document.getElementById('comeBackButton').addEventListener('click', function (event) {

  event.preventDefault();


  redirectToIndex();

});

