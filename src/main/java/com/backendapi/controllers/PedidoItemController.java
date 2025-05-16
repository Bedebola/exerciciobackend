package com.backendapi.controllers;

import com.backendapi.entities.PedidoItem;
import com.backendapi.services.PedidoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/pedidoItem")
public class PedidoItemController {

    @Autowired
    private PedidoItemService pedidoItemService;

    @GetMapping("/listar/{pedidoId}")
    public ResponseEntity<?> listarItensPorPedido(@PathVariable Long pedidoId) {
        try {
            List<PedidoItem> itens = pedidoItemService.listarItensPorPedido(pedidoId);
            return ResponseEntity.ok(itens);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarItem(
            @RequestParam Long pedidoId,
            @RequestParam Long produtoId,
            @RequestBody PedidoItem item
    ) {
        try {
            PedidoItem novoItem = pedidoItemService.adicionarItem(pedidoId, produtoId, item);
            return ResponseEntity.ok(novoItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{itemId}")
    public ResponseEntity<?> atualizarItem(
            @PathVariable Long itemId,
            @RequestBody PedidoItem itemAtualizado
    ) {
        try {
            PedidoItem atualizado = pedidoItemService.atualizarQuantidadeOuValor(itemId, itemAtualizado);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/remover/{itemId}")
    public ResponseEntity<?> removerItem(@PathVariable Long itemId) {
        try {
            pedidoItemService.removerItem(itemId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}
