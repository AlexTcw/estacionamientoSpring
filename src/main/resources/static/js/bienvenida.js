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
      .then(response => response.text())
      .then(result => {
        // Intenta parsear la fecha y hora
        const fechaHora = new Date(result);
  
        // Verifica si la fecha y hora se han parseado correctamente
        if (!isNaN(fechaHora.getTime())) {
          // Obtiene los componentes de la fecha y hora
          const dia = fechaHora.getDate();
          const mes = fechaHora.getMonth() + 1; // Los meses van de 0 a 11
          const año = fechaHora.getFullYear();
          const horas = fechaHora.getHours();
          const minutos = fechaHora.getMinutes();
  
          // Formatea la fecha y hora según tus preferencias
          const fechaHoraFormateada = `${dia}/${mes}/${año} ${horas}:${minutos}`;
  
          // Actualiza el contenido del elemento <p> con el resultado formateado
          document.getElementById("horaDeEntrada").textContent = fechaHoraFormateada;
        } else {
          console.log('Error al parsear la fecha y hora:', result);
        }
      })
      .catch(error => console.log('error', error));
  }
  
  // Llamada a las funciones
  getLastToken()
    .then(token => getHoraDeEntrada(token));
  
  
  