package com.backendapi.services;

import com.backendapi.entities.Produto;
import com.backendapi.exceptions.SenacException;
import com.backendapi.repositories.PedidoItemRepository;
import com.backendapi.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    public List<Produto> listarProdutos() throws SenacException {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            throw new SenacException("Nenhum produto encontrado.");
        }
        return produtos;
    }

    public Produto buscarProdutoPorId(Long id) throws SenacException {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new SenacException("Produto não encontrado."));
    }

    public Produto cadastrarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        if (produto.getDescricao() == null || produto.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("Descrição do produto não pode ser vazia.");
        }

        return produtoRepository.save(produto);
    }

    public Produto editarProduto(Long id, Produto produtoAtualizado) throws SenacException {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new SenacException("Produto não encontrado para edição."));

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());

        return produtoRepository.save(produtoExistente);
    }

    public void excluirProduto(Long id) throws SenacException {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new SenacException("Produto não encontrado para exclusão."));

        boolean produtoEmUso = pedidoItemRepository.existsByProduto(produto);
        if (produtoEmUso) {
            throw new SenacException("Produto está associado a itens de pedido e não pode ser excluído.");
        }

        produtoRepository.delete(produto);
    }
}
