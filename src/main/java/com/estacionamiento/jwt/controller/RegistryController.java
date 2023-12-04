package com.estacionamiento.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.service.registry.RegistryService;

@RestController
@CrossOrigin(origins = { "*" })
public class RegistryController {

    @Autowired
    RegistryService registryService;

    @GetMapping("/getLastToken")
    public int getLatToken() {
        return registryService.getLastTokenRegistry();
    }

    /* Verificar estado */
    @GetMapping("/getedoreg")
    public int getEdoRegistry() {
        return registryService.getLastEdoRegistry();
    }

    @GetMapping("/setedoreg")
    public ResponseEntity<String> setEdoReg(@RequestParam int token, @RequestParam int edo) {
        Boolean registrySet = registryService.setRegistryEdo(token, edo);
        if (registrySet != null && registrySet) {
            return ResponseEntity.ok("Registro establecido correctamente");
        } else {
            return ResponseEntity.status(500).body("Error al establecer el registro");
        }
    }

    @GetMapping("/setedoreg1")
    public ResponseEntity<String> setEdoReg1() {
        int token = 0;
        Boolean registrySet = registryService.setRegistry1Enrroll(token);
        if (registrySet != null && registrySet) {
            return ResponseEntity.ok("Registro establecido correctamente");
        } else {
            return ResponseEntity.status(500).body("Error al establecer el registro");
        }
    }

    @GetMapping("/setedoreg2")
    public ResponseEntity<String> setEdoReg2() {
        Boolean registrySet = registryService.setRegistry2proccess();
        if (registrySet != null && registrySet) {
            return ResponseEntity.ok("Registro establecido correctamente");
        } else {
            return ResponseEntity.status(500).body("Error al establecer el registro");
        }
    }

    @GetMapping("/setedoreg3")
    public ResponseEntity<String> setEdoReg3() {
        int token = 0;
        Boolean registrySet = registryService.setRegistry3Exit(token);
        if (registrySet != null && registrySet) {
            return ResponseEntity.ok("Registro establecido correctamente");
        } else {
            return ResponseEntity.status(500).body("Error al establecer el registro");
        }
    }

    @GetMapping("/setedoreg4")
    public ResponseEntity<String> setEdoReg4() {
        int token = registryService.getLastTokenRegistry();
        Boolean registrySet = registryService.setRegistry4Pension(token);
        if (registrySet != null && registrySet) {
            return ResponseEntity.ok("Registro establecido correctamente");
        } else {
            return ResponseEntity.status(500).body("Error al establecer el registro");
        }
    }

}
