package com.backendapi.services;

import com.backendapi.entities.Endereco;
import com.backendapi.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    public List<Endereco> listarEnderecos(){
        List<Endereco> listaRetorno = enderecoRepository.findAll();

        if (listaRetorno.isEmpty()){
            throw new RuntimeException("A tabela de endereços está vazia");
        }

        return listaRetorno;
    }





}
