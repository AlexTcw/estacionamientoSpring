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

  // Obtenemos la fecha actual
  var fechaActual = new Date();
  var formatoFecha = { year: 'numeric', month: '2-digit', day: '2-digit' };
  var fechaHoy = fechaActual.toLocaleDateString('es-ES', formatoFecha);

  // Usamos el token en la URL
  fetch(`http://localhost:8080/ingreso?token=${token}`, requestOptions)
    .then(response => response.json())  // Parsea la respuesta como JSON
    .then(result => {
      // Muestra la fecha y hora de entrada
      document.getElementById("fEntrada").textContent = result.fecha;
      document.getElementById("hEntrada").textContent = result.hora;

      // Asigna la fecha actual a fsalida
      document.getElementById("fsalida").textContent = fechaHoy;
    })
    .catch(error => console.log('error', error));
}


function tryPay(token) {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch(`http://localhost:8080/try-pay?token=${token}`, requestOptions)
    .then(response => response.json())
    .then(result => {
      console.log(result);

      // Accede directamente a las propiedades subTotal y total
      document.getElementById("subTotal").textContent = "$ " + result.subTotal + " MNX";
      document.getElementById("total").textContent = "$ " + result.total + " MNX";
    })
    .catch(error => console.log('error', error));
}

function pay(token){
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };
  
  fetch(`http://localhost:8080/transferHist?token=${token}`, requestOptions)
    .then(response => response.text())
    .then(result => console.log(result))
    .catch(error => console.log('error', error));
}

getLastToken().then(token => tryPay(token));
getLastToken().then(token => getHoraDeEntrada(token));
getLastToken().then(token => pay(token));

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


document.getElementById('cancelbutton').addEventListener('click', function () {
  redirectToIndex();
});

document.getElementById("paymentbutton").addEventListener("click", function() {
  document.querySelector(".confirmed").style.display = "block";
});

document.getElementById('continueButton').addEventListener('click', function () {
  pay()
    .then(function() {
      redirectToIndex();
    });
});

