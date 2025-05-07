package com.backendapi.repositories;

import com.backendapi.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository <Endereco, Long> {
}
