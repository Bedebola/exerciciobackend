package com.backendapi.controllers;

import com.backendapi.entities.Cliente;
import com.backendapi.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {


 @Autowired
 ClienteService clienteService;


    @GetMapping("/listar")
    ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> clienteListRetorno = clienteService.listarClientes();

        if (clienteListRetorno.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clienteListRetorno);
    }

    @PostMapping("/novo")
    ResponseEntity<?> cadastrarCliente(
            @RequestBody Cliente cliente){
    try {
        Cliente cadastrarCliente = clienteService.cadastrarCliente(cliente);
        return ResponseEntity.ok().body(cliente);
    } catch (IllegalArgumentException e){
        return ResponseEntity.badRequest().body("Erro: "+ e.getMessage());
    } catch (Exception e){
        return ResponseEntity.badRequest().body("Erro");
    }
}

@PutMapping("/editar")
    ResponseEntity<?> editarCliente(
            @RequestParam Long id,
            @RequestBody Cliente cliente
){
    try {
        Cliente editarCliente = clienteService.editarCliente(id, cliente);
        return ResponseEntity.ok().body(cliente);
    } catch (IllegalArgumentException e){
        return ResponseEntity.badRequest().body("Erro: "+ e.getMessage());
    } catch (Exception e){
        return ResponseEntity.badRequest().body("Erro");
    }
}

@DeleteMapping("/excluir")
    ResponseEntity<?> excluirCliente(
            @RequestParam Long id
){
    try {
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();
    } catch (Exception e){
        return ResponseEntity.badRequest().body("Erro");
    }
}

}

















