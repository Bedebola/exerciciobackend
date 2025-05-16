package com.backendapi.repositories;

import com.backendapi.entities.Pedido;
import com.backendapi.entities.PedidoItem;
import com.backendapi.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    List<PedidoItem> findByPedido(Pedido pedido);

    boolean existsByProduto(Produto produto);
}
