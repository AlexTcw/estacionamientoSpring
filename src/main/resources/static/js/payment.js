document.addEventListener("DOMContentLoaded", function () {
  // Configuración para la primera solicitud
  var requestOptionsToken = {
    method: 'GET',
    redirect: 'follow'
  };

  // Realizar la primera solicitud y almacenar el resultado en la variable
  fetch("http://localhost:8080/getLastToken", requestOptionsToken)
    .then(response => response.text())
    .then(result => {
      // Almacenar el resultado en la variable
      var token = result;

      // Llamar a la función para realizar la segunda solicitud usando el token obtenido
      makeSecondRequest(token);
    })
    .catch(error => console.log('error', error));

  // Función para realizar la segunda solicitud usando el token almacenado
  function makeSecondRequest(token) {
    // Configuración para la segunda solicitud
    var requestOptionsFecha = {
      method: 'GET',
      redirect: 'follow'
    };

    // Incluir el token en la segunda solicitud (puedes ajustar la URL según tus necesidades)
    fetch(`http://localhost:8080/getFechaEstByToken?token=${token}`, requestOptionsFecha)
      .then(response => response.json())
      .then(data => {
        // Update the HTML elements with the retrieved data
        document.getElementById("fEntrada").textContent = data.fecha;
        document.getElementById("hEntrada").textContent = data.fechaEntrada;
        document.getElementById("subTotal").textContent = data.subTotal + " MNX";
        document.getElementById("total").textContent = data.total + " MNX";

        // Obtener la fecha actual
        var fechaActual = new Date();

        // Formatear la fecha como DD/MM/YY
        var dia = ('0' + fechaActual.getDate()).slice(-2);
        var mes = ('0' + (fechaActual.getMonth() + 1)).slice(-2);
        var ano = fechaActual.getFullYear().toString().slice(-2);

        var fechaFormateada = dia + '/' + mes + '/' + ano;

        // Asignar la fecha formateada al elemento con el id "fsalida"
        document.getElementById("fsalida").textContent = fechaFormateada;
      })
      .catch(error => console.log('error', error));
  }
});


// Obtener el último token y luego ejecutar setExitData
// Obtener el último token y luego ejecutar setExitData
function getLastTokenAndSetExitData() {
  var requestOptionsToken = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/getLastToken", requestOptionsToken)
    .then(response => response.text())
    .then(result => {
      // Almacenar el resultado en la variable
      var lastToken = result;

      // Puedes imprimir el resultado si lo necesitas
      console.log("Last Token:", lastToken);

      // Luego, llamar a la función para establecer datos de salida con el token obtenido
      setExitData(lastToken);
    })
    .catch(error => console.error('Error obteniendo el último token:', error));
}

function generateCSV(token) {
  // Function to handle errors
  function handleError(error) {
    console.error('Error:', error);
    // Handle the error appropriately (e.g., show a message to the user)
  }

  function updateHTMLElements(data) {
    // Update HTML elements with the retrieved data
    document.getElementById("fEntrada").textContent = data.fecha;
    document.getElementById("hEntrada").textContent = data.fechaEntrada;
    document.getElementById("subTotal").textContent = `${data.subTotal} MNX`;
    document.getElementById("total").textContent = `${data.total} MNX`;

    // Format and set the current date
    const fechaActual = new Date();
    const fechaFormateada = `${('0' + fechaActual.getDate()).slice(-2)}/${('0' + (fechaActual.getMonth() + 1)).slice(-2)}/${fechaActual.getFullYear().toString().slice(-2)}`;
    document.getElementById("fsalida").textContent = fechaFormateada;
  }

  function generateCSVDocument(data) {
    // Prepare data for CSV
    const facturaContent = `FACTURA DE TRANSACCION ELECTRONICA\n
      FD01-0000-${data.fecha}\n
      SecureGate Technologies\n
      RFC: EXTF9000101NI1\n
      Fecha: ${data.fecha}\n
      Fecha de Entrada: ${data.fechaEntrada}\n
      Subtotal MNX: ${data.subTotal}\n
      Total MNX: ${data.total}\n
      ---
      Información del Emisor (Vendedor):\n
      Nombre: SecureGate Technologies\n
      Dirección: Calle Ficticia 123, Ciudad Imaginaria\n
      RFC: EXT123456XYZ\n
      ---
      Información del Receptor (Comprador):\n
      Nombre: Cliente Imaginario SA de CV\n
      Dirección: Avenida Inventada 456, Ciudad Irreal\n
      RFC: CLI789ABCDEF\n
      ---
      Detalles de la Transacción:\n
      Descripción: Servicios de Consultoría\n
      Cantidad: 5 horas\n
      Precio Unitario: $100 MXN por hora\n
      Importe Total: $500 MXN\n
      Impuestos (IVA 16%): $80 MXN\n
      ---
      Número de Factura y Serie:\n
      Número de Factura: FD01-0000-${data.fecha}\n
      Serie: FD01\n
      ---
      Datos de Pago:\n
      Método de Pago: Tarjeta de Crédito\n
      Fecha de vencimiento: 15 días\n
      Datos Bancarios: Banco Imaginario, Sucursal 789, Cuenta 12345678\n
      ---
      Información Adicional:\n
      Condiciones de Pago: Pago a 15 días\n
      Este documento es una representación impresa de una CFDI\n
      ---
      Firma Digital: [Firma Digital Aquí]`;

    // Create Blob and create a download link
    const blob = new Blob([facturaContent], { type: 'text/plain' }); // Cambié csvContent a facturaContent
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'factura.txt'; // Cambié el nombre del archivo a 'factura.txt' ya que el contenido parece más adecuado para un archivo de texto

    // Append the download link to the document and trigger the download
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  }



  // Fetch last token
  fetch("http://localhost:8080/getLastToken")
    .then(response => response.text())
    .then(lastToken => {
      fetch(`http://localhost:8080/getFechaEstByToken?token=${token}`)
        .then(response => response.json())
        .then(data => {
          // Update HTML elements
          updateHTMLElements(data);

          // Generate CSV
          generateCSVDocument(data);

          // Fetch last token and set exit data
          getLastTokenAndSetExitData();
        })
        .catch(handleError);
    })
    .catch(handleError);
}

// Agregar un manejador de eventos al hacer clic en el botón de pago
document.getElementById("paymentbutton").addEventListener("click", function () {
  // Verificar si el checkbox está seleccionado
  var requiereFacturaCheckbox = document.getElementById("requiereFactura");

  if (requiereFacturaCheckbox.checked) {
    // Si el checkbox está seleccionado, ejecutar generateCSV
    generateCSV(/* pass the token here */);
  } else {
    // Si el checkbox no está seleccionado, ejecutar getLastTokenAndSetExitData
    getLastTokenAndSetExitData();
  }
});

// Función para alternar la entrada de texto según el estado del checkbox
function toggleInput() {
  var requiereFacturaCheckbox = document.getElementById("requiereFactura");
  var facturaButton = document.getElementById("factura");

  // Habilitar o deshabilitar el botón de factura según el estado del checkbox
  facturaButton.disabled = requiereFacturaCheckbox.checked;
}



// Función para enviar el token al servidor y realizar acciones después del pago
function setExitData(token) {
  var requestOptions = {
    method: 'POST',
    redirect: 'follow'
  };

  // Incluir el token en la URL de la solicitud
  fetch("http://localhost:8080/setExitData?token=" + token, requestOptions)
    .then(response => response.text())
    .then(result => {
      // Si la función setExitData se ejecuta correctamente, llamar a setEstacionamientoIntoHistorial
      setEstacionamientoIntoHistorial(token);
    })
    .catch(error => console.error('Error al enviar el token al servidor:', error));
}

// Función para almacenar estacionamiento en el historial después del pago
function setEstacionamientoIntoHistorial(token) {
  var requestOptions = {
    method: 'POST',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/setEstacionamientoIntoHistorial?token=" + token, requestOptions)
    .then(response => response.text())
    .then(result => {
      // Si la función setEstacionamientoIntoHistorial se ejecuta correctamente, llamar a deleteEstacionamientoByToken
      deleteEstacionamientoByToken(token);
    })
    .catch(error => console.error('Error al almacenar estacionamiento en el historial:', error));
}

// Función para eliminar estacionamiento por token después de almacenarlo en el historial
function deleteEstacionamientoByToken(token) {
  var requestOptions = {
    method: 'POST',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/deleteEstacionamientoByToken?token=" + token, requestOptions)
    .then(response => response.text())
    .then(result => {
      // Si la función deleteEstacionamientoByToken se ejecuta correctamente, mostrar el alert
      alert("Pago recibido. Puede continuar.");
      redirectToIndex();
    })
    .catch(error => console.error('Error al eliminar estacionamiento por token:', error));
}

// Función para redirigir a la página principal al presionar el botón "cancelbutton"
document.getElementById("cancelbutton").addEventListener('click', redirectToIndex);

// Función para redirigir a la página principal
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
    .catch(error => console.error('Error al redirigir a la página principal:', error));
}

