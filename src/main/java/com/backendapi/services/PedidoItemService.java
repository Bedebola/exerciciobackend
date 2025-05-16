package com.backendapi.services;

import com.backendapi.entities.Pedido;
import com.backendapi.entities.PedidoItem;
import com.backendapi.entities.Produto;
import com.backendapi.exceptions.SenacException;
import com.backendapi.repositories.PedidoItemRepository;
import com.backendapi.repositories.PedidoRepository;
import com.backendapi.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoItemService {

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<PedidoItem> listarItensPorPedido(Long pedidoId) throws SenacException {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new SenacException("Pedido não encontrado."));

        List<PedidoItem> itens = pedidoItemRepository.findByPedido(pedido);
        if (itens.isEmpty()) {
            throw new SenacException("Nenhum item encontrado para este pedido.");
        }

        return itens;
    }

    public PedidoItem adicionarItem(Long pedidoId, Long produtoId, PedidoItem item) throws SenacException {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new SenacException("Pedido não encontrado."));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new SenacException("Produto não encontrado."));

        item.setPedido(pedido);
        item.setProduto(produto);

        return pedidoItemRepository.save(item);
    }

    public PedidoItem atualizarQuantidadeOuValor(Long itemId, PedidoItem itemAtualizado) throws SenacException {
        PedidoItem itemExistente = pedidoItemRepository.findById(itemId)
                .orElseThrow(() -> new SenacException("Item de pedido não encontrado."));

        if (!itemAtualizado.getProduto().getId().equals(itemExistente.getProduto().getId())) {
            throw new SenacException("Não é permitido trocar o produto de um item já existente.");
        }

        itemExistente.setQuantidade(itemAtualizado.getQuantidade());
        itemExistente.setValorUnitario(itemAtualizado.getValorUnitario());

        return pedidoItemRepository.save(itemExistente);
    }

    public void removerItem(Long itemId) {
        if (!pedidoItemRepository.existsById(itemId)) {
            throw new RuntimeException("Item de pedido não encontrado para exclusão.");
        }

        pedidoItemRepository.deleteById(itemId);
    }
}
