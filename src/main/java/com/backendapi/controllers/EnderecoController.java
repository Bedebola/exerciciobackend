package com.backendapi.controllers;

import com.backendapi.entities.Endereco;
import com.backendapi.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;


    @GetMapping("/listar")
    ResponseEntity<List<Endereco>> listarEnderecos() {
        List<Endereco> listaRetorno = enderecoService.listarEnderecos();

        if (listaRetorno.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaRetorno);
    }

    @GetMapping("/buscar/{idCliente}")
    ResponseEntity<List<Endereco>> buscarEnderecoId(
            @PathVariable Long idCliente
    ) {
        List<Endereco> enderecosRetorno = enderecoService.buscarEnderecoPorId(idCliente);

        if (enderecosRetorno.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(enderecosRetorno);
    }

    @PostMapping("/cadastrar/{clienteId}")
    ResponseEntity<?> cadastrarEndereco(
            @PathVariable Long clienteId,
            @RequestBody Endereco endereco
    ) {
        try {
            Endereco cadastrarEndereco = enderecoService.cadastrarEndereco(clienteId, endereco);
            return ResponseEntity.ok(endereco);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro");
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarEndereco(
            @PathVariable Long id,
            @RequestParam Long clienteId,
            @RequestBody Endereco endereco
    ) {
        try {
            Endereco editarEndereco = enderecoService.editarEndereco(id, clienteId, endereco);
            return ResponseEntity.ok().body(editarEndereco);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro");
        }
    }

    @DeleteMapping("/excluir/{id}")
    ResponseEntity<?> excluirEndereco(
            @PathVariable Long id
    ) {
        try {
            enderecoService.excluirEndereco(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro");
        }
    }
}