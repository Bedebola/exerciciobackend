package com.backendapi.controllers;

import com.backendapi.dtos.ClienteDTO;
import com.backendapi.entities.Cliente;
import com.backendapi.exceptions.SenacException;
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
    ResponseEntity<List<Cliente>> listarClientes() throws SenacException {
        List<Cliente> lsitaRetorno = clienteService.listarClientes();

        if (lsitaRetorno.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lsitaRetorno);
    }

    @PostMapping("/novo")
    ResponseEntity<?> cadastrarCliente(
            @RequestBody Cliente cliente) {
        try {
            Cliente cadastrarCliente = clienteService.cadastrarCliente(cliente);
            return ResponseEntity.ok().body(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro");
        }
    }

    @PostMapping("/cadastrarClienteCompleto")
    ResponseEntity<?> criarClienteCompleto(
            @RequestBody ClienteDTO cliente
    ) {
        try {
            return ResponseEntity
                    .created(null)
                    .body(clienteService.cadastrarClienteCompleto(cliente));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    ResponseEntity<?> editarCliente(
            @PathVariable Long id,
            @RequestBody Cliente cliente
    ) {
        try {
            Cliente editarCliente = clienteService.editarCliente(id, cliente);
            return ResponseEntity.ok().body(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro");
        }
    }

    @DeleteMapping("/excluir/{id}")
    ResponseEntity<?> excluirCliente(
            @PathVariable Long id
    ) {
        try {
            clienteService.excluirCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro");
        }
    }

}