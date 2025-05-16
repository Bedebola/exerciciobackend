package com.backendapi.repositories;

import com.backendapi.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository <Endereco, Long> {

    List<Endereco> findByIdCliente(Long clienteId);
}
