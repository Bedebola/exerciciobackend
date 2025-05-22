package com.backendapi.services;

import com.backendapi.dtos.PedidoDTO;
import com.backendapi.dtos.PedidoItemDTO;
import com.backendapi.entities.*;
import com.backendapi.exceptions.SenacException;
import com.backendapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Pedido> listarPedidos() throws SenacException {
        List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) {
            throw new SenacException("Não há pedidos cadastrados.");
        }
        return pedidos;
    }

    public Pedido buscarPedidoPorId(Long id) throws SenacException {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new SenacException("Pedido não encontrado com o ID informado."));
    }

    public Pedido cadastrarPedido(Long clienteId, Long enderecoId, Pedido pedido) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado na base de dados."));

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado na base de dados."));

        if (!endereco.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("O endereço informado não pertence ao cliente.");
        }

        pedido.setCliente(cliente);
        pedido.setEndereco(endereco);
        pedido.setDataCriacao(LocalDateTime.now());

        return pedidoRepository.save(pedido);
    }

    public Pedido cadastrarPedidoComItens(PedidoDTO pedido){

        Pedido pedidoRecord = new Pedido();
        pedidoRecord.setValorTotal(pedido.getValorTotal());
        pedidoRecord.setCliente(pedido.getCliente());
        pedidoRecord.setEndereco(pedido.getEndereco());
        pedidoRecord.setDataCriacao(LocalDateTime.now());

        Pedido pedidoSalvo = pedidoRepository.save(pedidoRecord);

        for (PedidoItemDTO itemDTO : pedido.getPedidoItens()){
            PedidoItem item = new PedidoItem();
            item.setPedido(pedidoSalvo);

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setValorUnitario(itemDTO.getValorUnitario());

            pedidoItemRepository.save(item);
        }

        return pedidoSalvo;

    }



    public Pedido atualizarEnderecoEntrega(Long pedidoId, Long novoEnderecoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        Endereco novoEndereco = enderecoRepository.findById(novoEnderecoId)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado."));

        if (!novoEndereco.getCliente().getId().equals(pedido.getCliente().getId())) {
            throw new RuntimeException("O novo endereço não pertence ao mesmo cliente do pedido.");
        }

        pedido.setEndereco(novoEndereco);
        return pedidoRepository.save(pedido);
    }

    public void excluirPedido(Long pedidoId) {
        if (!pedidoRepository.existsById(pedidoId)) {
            throw new RuntimeException("Pedido não encontrado para exclusão.");
        }

        pedidoRepository.deleteById(pedidoId);
    }
}
