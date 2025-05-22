package com.backendapi.controllers;

import com.backendapi.dtos.PedidoDTO;
import com.backendapi.entities.Pedido;
import com.backendapi.exceptions.SenacException;
import com.backendapi.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Pedido>> listarPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.listarPedidos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.buscarPedidoPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (SenacException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarPedido(
            @RequestParam Long clienteId,
            @RequestParam Long enderecoId,
            @RequestBody Pedido pedido
    ) {
        try {
            Pedido novoPedido = pedidoService.cadastrarPedido(clienteId, enderecoId, pedido);
            return ResponseEntity.ok(novoPedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro");
        }
    }

    @PostMapping("/cadastrar-com-itens")
    public ResponseEntity<?> cadastrarPedidoComItens(@RequestBody PedidoDTO pedidoDTO) {
        try {
            Pedido novoPedido = pedidoService.cadastrarPedidoComItens(pedidoDTO);
            return ResponseEntity.ok(novoPedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar pedido com itens.");
        }
    }


    @PutMapping("/atualizar-endereco/{pedidoId}")
    public ResponseEntity<?> atualizarEndereco(
            @PathVariable Long pedidoId,
            @RequestParam Long novoEnderecoId
    ) {
        try {
            Pedido atualizado = pedidoService.atualizarEnderecoEntrega(pedidoId, novoEnderecoId);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{id}")
    ResponseEntity<?> excluirCliente(
            @PathVariable Long id
    ){
        try {
            pedidoService.excluirPedido(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro");
        }
    }
}
