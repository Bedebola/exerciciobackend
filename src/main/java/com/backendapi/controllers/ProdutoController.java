package com.backendapi.controllers;

import com.backendapi.entities.Produto;
import com.backendapi.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listarProdutos() {
        try {
            List<Produto> produtos = produtoService.listarProdutos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarProdutoPorId(
            @PathVariable Long id
    ){
        try {
            Produto produto = produtoService.buscarProdutoPorId(id);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarProduto(
            @RequestBody Produto produto
    ){
        try {
            Produto novo = produtoService.cadastrarProduto(produto);
            return ResponseEntity.ok(novo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar produto.");
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProduto(
            @PathVariable Long id,
            @RequestBody Produto produto
    ) {
        try {
            Produto editado = produtoService.editarProduto(id, produto);
            return ResponseEntity.ok(editado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao editar produto.");
        }
    }

    @DeleteMapping("/excluir/{id}")
    ResponseEntity<?> excluirCliente(
            @PathVariable Long id
    ){
        try {
            produtoService.excluirProduto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro");
        }
    }
}
