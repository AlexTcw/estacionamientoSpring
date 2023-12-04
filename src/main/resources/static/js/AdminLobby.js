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

function downloadHistorialCSV(data) {
  // Convertir los datos a formato CSV
  var csv = Papa.unparse(data);

  // Crear un Blob con el contenido CSV
  var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });

  // Crear un enlace de descarga y simular el clic para iniciar la descarga
  var link = document.createElement("a");
  var url = URL.createObjectURL(blob);
  link.setAttribute("href", url);
  link.setAttribute("download", "historial.csv");
  document.body.appendChild(link);
  link.click();

  // Limpiar y liberar recursos
  document.body.removeChild(link);
  URL.revokeObjectURL(url);
}

function getAllHistorials() {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/getAllHistorials", requestOptions)
    .then(response => response.json())
    .then(result => {
      fillHistorialTable(result);

      // Agregar evento clic al botón de descarga
      document.getElementById("downloadHistorialCSV").addEventListener("click", function () {
        downloadHistorialCSV(result);
      });
    })
    .catch(error => console.log('error', error));
}




function fillHistorialTable(historials) {
  var tablaHistorial = $("#tablaHistorial tbody");
  tablaHistorial.empty(); // Clear the current content of the table

  historials.forEach(historial => {
    var fila = $("<tr>").appendTo(tablaHistorial);

    // Add cells with historial data
    $("<td>").text(historial.cveHist).appendTo(fila);
    $("<td>").text(historial.tiempoDeUso).appendTo(fila);
    $("<td>").text(historial.ingresoFec).appendTo(fila);
    $("<td>").text(historial.salidaFec).appendTo(fila);
    $("<td>").text(historial.total).appendTo(fila);
  });
}



function downloadCSV(data) {
  // Convertir los datos a formato CSV
  var csv = Papa.unparse(data);

  // Crear un Blob con el contenido CSV
  var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });

  // Crear un enlace de descarga y simular el clic para iniciar la descarga
  var link = document.createElement("a");
  var url = URL.createObjectURL(blob);
  link.setAttribute("href", url);
  link.setAttribute("download", "estacionamientos.csv");
  document.body.appendChild(link);
  link.click();

  // Limpiar y liberar recursos
  document.body.removeChild(link);
  URL.revokeObjectURL(url);
}

function getAllEstacionamientos() {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/getAllEstacionamientos", requestOptions)
    .then(response => response.json())
    .then(result => {
      fillEstacionamientoTable(result);

      // Agregar evento clic al botón de descarga
      document.getElementById("downloadEstCSV").addEventListener("click", function () {
        downloadCSV(result);
      });
    })
    .catch(error => console.log('error', error));
}


function fillEstacionamientoTable(estacionamientos) {
  var tablaEstacionamiento = document.getElementById("tablaEstacionamiento").getElementsByTagName('tbody')[0];
  tablaEstacionamiento.innerHTML = ""; // Limpiar el contenido actual de la tabla

  estacionamientos.forEach(estacionamiento => {
    var fila = tablaEstacionamiento.insertRow();

    // Añadir celdas con los datos del estacionamiento
    fila.insertCell().innerText = estacionamiento.cveEst;
    fila.insertCell().innerText = estacionamiento.ingresoFec;
    fila.insertCell().innerText = estacionamiento.tiempoDeUso;  // Asumiendo que tienes un campo 'tiempoDeUso' en tu JSON
    fila.insertCell().innerText = estacionamiento.salidaFec;
    fila.insertCell().innerText = estacionamiento.tokenIngreso;
    fila.insertCell().innerText = estacionamiento.total;
    fila.insertCell().innerText = estacionamiento.edoPago ? "Pagado" : "Pendiente";



    // Agregar un botón de borrar
    var botonBorrar = document.createElement("button");
    botonBorrar.innerText = "-";
    botonBorrar.classList.add("btn", "btn-danger", "btn-borrar");
    botonBorrar.dataset.id = estacionamiento.cveEst; // Guardar el ID en un atributo personalizado

    var celdaBotonBorrar = fila.insertCell();
    celdaBotonBorrar.appendChild(botonBorrar);
  });

  // Agregar eventos clic para los botones de borrar
  document.querySelectorAll(".btn-borrar").forEach(boton => {
    boton.addEventListener("click", function () {
      var idEstacionamiento = this.dataset.id;
      deleteEstacionamientoById(idEstacionamiento);
      // Puedes también remover la fila de la tabla después de borrar si es necesario
      this.closest("tr").remove();
    });
  });
}

function deleteEstacionamientoById(id) {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/deleteEstacionamientoById?id=" + id, requestOptions)
    .then(response => response.text())
    .then(result => console.log(result))
    .catch(error => console.log('error', error));
}

function downloadUsuCSV(data) {
  // Convertir los datos a formato CSV
  var csv = Papa.unparse(data);

  // Crear un Blob con el contenido CSV
  var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });

  // Crear un enlace de descarga y simular el clic para iniciar la descarga
  var link = document.createElement("a");
  var url = URL.createObjectURL(blob);
  link.setAttribute("href", url);
  link.setAttribute("download", "usuarios.csv");
  document.body.appendChild(link);
  link.click();

  // Limpiar y liberar recursos
  document.body.removeChild(link);
  URL.revokeObjectURL(url);
}

function getAllUsersInfo() {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/getAllUsu", requestOptions)
    .then(response => response.json())
    .then(result => {
      var tabla = $("#tablaUsuarios tbody");
      tabla.empty();

      result
        .filter(usuario => usuario.tokenEst !== 0)
        .forEach(usuario => {
          var fila = $("<tr>").appendTo(tabla);

          // Añadir celdas a la fila como inputs editables
          $("<td>").text(usuario.cveUsu).appendTo(fila);
          $("<td>").text(usuario.edoUsu).appendTo(fila);

          // Placas
          var placasCell = $("<td>").appendTo(fila);
          $("<input>").attr("type", "text").val(usuario.placas.join(", ")).addClass("editable-input").appendTo(placasCell);

          // Correo
          var correoCell = $("<td>").appendTo(fila);
          $("<input>").attr("type", "text").val(usuario.correo).addClass("editable-input").appendTo(correoCell);

          // Contraseña (considerando que es solo para fines de visualización, no mostrar contraseñas reales)
          $("<td>").text("*****").appendTo(fila);

          // Nombre
          var nombreCell = $("<td>").appendTo(fila);
          $("<input>").attr("type", "text").val(usuario.nomUsu).addClass("editable-input").appendTo(nombreCell);

          // Token
          $("<td>").text(usuario.tokenEst).appendTo(fila);

          // Botón Editar
          var editarCell = $("<td>").appendTo(fila);
          var botonEditar = $("<button>").addClass("btn btn-primary btn-editar").appendTo(editarCell);
          var iconoEditar = $('<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pen" viewBox="0 0 16 16"> <path d="..."/> </svg>');
          botonEditar.prepend(iconoEditar);

          // Botón Borrar
          var borrarCell = $("<td>").appendTo(fila);
          var botonBorrar = $("<button>").addClass("btn btn-danger btn-borrar").attr("data-id", usuario.cveUsu).appendTo(borrarCell);
          var iconoBorrar = $('<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16"> <path d="..."/> </svg>');
          botonBorrar.prepend(iconoBorrar);
        });

      // Delegación de eventos para botones de edición y eliminación
      tabla.on("click", ".btn-editar", function () {
        var fila = $(this).closest("tr");
        var idUsuario = fila.find("td:eq(0)").text();
        editUsuario(idUsuario);
      });

      tabla.on("click", ".btn-borrar", function () {
        var idUsuario = parseInt($(this).attr("data-id"), 10);
        deleteUsrById(idUsuario);
        fila.remove();
      });

      // Agregar evento clic al botón de descarga
      document.getElementById("downloadUsuCSV").addEventListener("click", function () {
        downloadUsuCSV(result);
      });

    })
    .catch(error => console.log('Error fetching user data:', error));
}


function editUsuario(idUsuario) {
  var fila = $("tr").find(`td:contains(${idUsuario})`).closest("tr");

  // Get the values from the row
  var placas = fila.find("td:eq(2) input").val();
  var correo = fila.find("td:eq(3) input").val();
  var contraseña = fila.find("td:eq(4)").text(); // Not editable, assuming it's a display-only field
  var nombre = fila.find("td:eq(5) input").val();
  var token = fila.find("td:eq(6)").text(); // Not editable

  // Call the updateUsuario method with the gathered data
  updateUsuario(correo, contraseña, token, placas, nombre);
}

function deleteUsrById(id) {
  var idAsLong = parseInt(id);

  if (isNaN(idAsLong)) {
    console.error("Error: El id no es un número válido.");
    return;
  }

  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch(`http://localhost:8080/deleteUsuById?cve=${idAsLong}`, requestOptions)
    .then(response => response.text())
    .then(result => {
      console.log(result);
      location.reload();
    })
    .catch(error => console.log('Error deleting user:', error));
}

function updateUsuario(correo, pswd, token, placas, nombre) {
  var requestOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      correo: correo,
      pswd: pswd,
      token: token,
      placas: placas,
      nombre: nombre
    })
  };

  fetch("http://localhost:8080/updateUsu", requestOptions)
    .then(response => response.text())
    .then(result => {
      console.log(result);
      //location.reload();
    })
    .catch(error => console.log('Error actualizando usuario:', error));
}



















