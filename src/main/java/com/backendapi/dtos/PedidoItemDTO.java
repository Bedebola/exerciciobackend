package com.backendapi.dtos;

public class PedidoItemDTO {

    private Long produtoId;
    private int quantidade;
    private double valorUnitario;

    public PedidoItemDTO() {
    }

    public PedidoItemDTO(Long produtoId, int quantidade, double valorUnitario) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
