package com.backendapi.dtos;

import com.backendapi.entities.Cliente;
import com.backendapi.entities.Endereco;

import java.util.List;

public class PedidoDTO {

    private double valorTotal;
    private Cliente cliente;
    private Endereco endereco;
    private List<PedidoItemDTO> pedidoItens;

    public PedidoDTO() {
    }

    public PedidoDTO(double valorTotal, Cliente cliente, Endereco endereco, List<PedidoItemDTO> pedidoItens) {
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.endereco = endereco;
        this.pedidoItens = pedidoItens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<PedidoItemDTO> getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(List<PedidoItemDTO> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }
}
