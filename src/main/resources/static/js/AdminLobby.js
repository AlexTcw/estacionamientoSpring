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

function newUsuario(correo, pswd, token, placa, nombre) {
  var requestOptions = {
    method: 'POST',
    redirect: 'follow'
  };

  // Construir la URL con los parámetros proporcionados
  var url = `http://localhost:8080/createUsuario?correo=${correo}&pswd=${pswd}&token=${token}&placa=${placa}&nombre=${nombre}`;

  fetch(url, requestOptions)
    .then(response => response.text())
    .then(result => {
      console.log(result);
      location.reload();
    })
    .catch(error => console.log('error', error));
}

function deleteUsrbyID(id) {
  // Convertir el id a tipo long
  var idAsLong = parseInt(id);

  // Verificar si la conversión fue exitosa
  if (isNaN(idAsLong)) {
    console.error("Error: El id no es un número válido.");
    return;
  }

  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/deleteUsuById?cve=" + idAsLong, requestOptions)
    .then(response => response.text())
    .then(result => {
      console.log(result);
      location.reload();
    })
    .catch(error => console.log('error', error));
}

function updateUsuario(correo, pswd, token, placas, nombre) {
  var requestOptions = {
    method: 'POST',
    redirect: 'follow'
  };

  // Construir la URL con los parámetros proporcionados
  var url = `http://localhost:8080/updateUsu?correo=${correo}&pswd=${pswd}&token=${token}&placas=${placas}&nombre=${nombre}`;

  fetch(url, requestOptions)
    .then(response => response.text())
    .then(result => {
      console.log(result);
      location.reload();
    })
    .catch(error => console.log('error', error));
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

      result.forEach(usuario => {
        var fila = $("<tr>").appendTo(tabla);

        // Añade celdas a la fila como inputs editables
        $("<td>").text(usuario.cveUsu).appendTo(fila);
        $("<td>").text(usuario.edoUsu).appendTo(fila);

        // Placas
        var placasCell = $("<td>").appendTo(fila);
        $("<input>").attr("type", "text").val(usuario.placas.join(", ")).addClass("editable-input").appendTo(placasCell);

        // Correo
        var correoCell = $("<td>").appendTo(fila);
        $("<input>").attr("type", "text").val(usuario.correo).addClass("editable-input").appendTo(correoCell);

        // Contraseña
        var contraseñaCell = $("<td>").appendTo(fila);
        $("<input>").attr("type", "text").val(usuario.contraseña).addClass("editable-input").appendTo(contraseñaCell);

        // Nombre
        var nombreCell = $("<td>").appendTo(fila);
        $("<input>").attr("type", "text").val(usuario.nomUsu).addClass("editable-input").appendTo(nombreCell);

        // Token
        $("<td>").text(usuario.tokenEst).appendTo(fila);



        // Botón Editar
        var editarCell = $("<td>").appendTo(fila);
        var botonEditar = $("<button>").addClass("btn btn-primary btn-editar").appendTo(editarCell);
        var iconoEditar = $('<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pen" viewBox="0 0 16 16"> <path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001m-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"/> </svg>');
        botonEditar.prepend(iconoEditar);

        // Botón Borrar
        var borrarCell = $("<td>").appendTo(fila);
        var botonBorrar = $("<button>").addClass("btn btn-danger btn-borrar").attr("data-id", usuario.cveUsu).appendTo(borrarCell);
        var iconoBorrar = $('<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16"><path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/><path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/></svg>)')
        botonBorrar.prepend(iconoBorrar);
      });

      // Agrega un evento clic para activar la edición al hacer clic en una celda
      $(".editable-input").on("click", function () {
        $(this).attr("contenteditable", true);
      });

      // Agrega eventos clic para los botones Editar y Borrar
      $(".btn-editar").on("click", function () {
        var fila = $(this).closest("tr");

        // Obtener los valores de la fila
        var idUsuario = fila.find("td:eq(0)").text(); // ID de usuario
        var estado = fila.find("td:eq(1)").text(); // Estado (si es necesario)
        var placas = fila.find("td:eq(2) input").val(); // Placas
        var correo = fila.find("td:eq(3) input").val(); // Correo
        var contraseña = fila.find("td:eq(4) input").val(); // Contraseña
        var nombre = fila.find("td:eq(5) input").val(); // Nombre
        var token = fila.find("td:eq(6)").text(); // Token (si es necesario)

        // Llamada a la función updateUsuario con los valores de la fila
        updateUsuario(correo, contraseña, token, placas, nombre);

        // Puedes también realizar acciones adicionales según sea necesario
      });


      $(".btn-borrar").on("click", function () {
        var idUsuario = parseInt($(this).attr("data-id"), 10);
        deleteUsrbyID(idUsuario);
        // Puedes también remover la fila de la tabla después de borrar si es necesario
        $(this).closest("tr").remove();
      });
      var nuevaFila = $("<tr>").appendTo(tabla);
      $("<td>").appendTo(nuevaFila); // Espacio para el ID (puedes ocultarlo si no es necesario)
      $("<td>").appendTo(nuevaFila); // Espacio para el Estado
      $("<td>").html('<input type="text" class="editable-input" placeholder="Placas">').appendTo(nuevaFila);
      $("<td>").html('<input type="text" class="editable-input" placeholder="Correo">').appendTo(nuevaFila);
      $("<td>").html('<input type="text" class="editable-input" placeholder="Contraseña">').appendTo(nuevaFila);
      $("<td>").html('<input type="text" class="editable-input" placeholder="Nombre">').appendTo(nuevaFila);
      $("<td>").html('<input type="text" class="editable-input" placeholder="Token">').appendTo(nuevaFila);
      $("<td>").appendTo(nuevaFila); // Espacio para el Token
      $("<td>").html('<button class="btn btn-success btn-nuevo">+</button>').appendTo(nuevaFila);

      // Agrega un evento clic para activar la edición al hacer clic en una celda
      $(".editable-input").on("click", function () {
        $(this).attr("contenteditable", true);
      });

      // Agrega eventos clic para los botones Editar, Borrar y Nuevo
      $(".btn-editar").on("click", function () {
        var fila = $(this).closest("tr");
        var idUsuario = fila.find("td:first-child").text();
        console.log("Editar usuario con ID: " + idUsuario);
      });

      $(".btn-borrar").on("click", function () {
        var fila = $(this).closest("tr");
        var idUsuario = fila.find("td:first-child").text();
        console.log("Borrar usuario con ID: " + idUsuario);
      });

      $(".btn-nuevo").on("click", function () {
        // Obtener los valores de la fila
        var placas = $(this).closest("tr").find("td:eq(2) input").val();
        var correo = $(this).closest("tr").find("td:eq(3) input").val();
        var contraseña = $(this).closest("tr").find("td:eq(4) input").val();
        var nombre = $(this).closest("tr").find("td:eq(5) input").val();
        var token = $(this).closest("tr").find("td:eq(6) input").val();

        // Llamada a la función newUsuario con los valores de la fila
        newUsuario(correo, contraseña, token, placas, nombre);
      });

    })
    .catch(error => console.log('error', error));
}


function fillHistorialTable(historials) {
  var tablaHistorial = document.getElementById("tablaHistorial").getElementsByTagName('tbody')[0];
  tablaHistorial.innerHTML = ""; // Limpiar el contenido actual de la tabla

  historials.forEach(historial => {
    var fila = tablaHistorial.insertRow();

    // Añadir celdas con los datos del historial
    fila.insertCell().innerText = historial.cveHist;
    fila.insertCell().innerText = historial.tiempoDeUso;
    fila.insertCell().innerText = historial.ingresoFec;
    fila.insertCell().innerText = historial.salidaFec;
    fila.insertCell().innerText = historial.total;
  });
}

function getAllHistorials() {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/getAllHistorials", requestOptions)
    .then(response => response.json())  // Parsear la respuesta JSON
    .then(result => fillHistorialTable(result))  // Llenar la tabla con los datos obtenidos
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


function getAllEstacionamientos() {
  var requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  fetch("http://localhost:8080/getAllEstacionamientos", requestOptions)
    .then(response => response.json())  // Parsear la respuesta JSON
    .then(result => fillEstacionamientoTable(result))  // Llenar la tabla con los datos obtenidos
    .catch(error => console.log('error', error));
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






