package com.backendapi.services;

import com.backendapi.dtos.EnderecoDTO;
import com.backendapi.entities.Cliente;
import com.backendapi.entities.Endereco;
import com.backendapi.exceptions.SenacException;
import com.backendapi.repositories.ClienteRepository;
import com.backendapi.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    ClienteRepository clienteRepository;

    public List<Endereco> listarEnderecos(){
        List<Endereco> listaRetorno = enderecoRepository.findAll();

        if (listaRetorno.isEmpty()){
            throw new RuntimeException("A tabela de endereços está vazia");
        }

        return listaRetorno;
    }

    public List<Endereco> buscarEnderecoPorId(Long clienteId){
        List<Endereco> enderecosIdRetorno = enderecoRepository.findByIdCliente(clienteId);

        if (enderecosIdRetorno.isEmpty()){
            throw new RuntimeException("Nenhum endereço encontrado para o ID_Cliente informado");
        }

        return enderecosIdRetorno;
    }

    public Endereco cadastrarEndereco(Long clienteId, Endereco endereco) throws SenacException {

        Cliente clienteExisteRetorno = clienteRepository.findById(clienteId)
                .orElseThrow(()-> new SenacException("Cliente não encontrado na base de dados"));

        List<Endereco> enderecosIdRetorno = enderecoRepository.findByIdCliente(clienteId);
        if (enderecosIdRetorno.size() >= 3){
            throw new SenacException("Exclua um dos endereços para cadastrar um novo. Max de endereços por cliente: 3");
        }

        if (endereco.getCep() == null || endereco.getCep().length() != 8){
            throw new IllegalArgumentException("O CEP deve ter 8 dígitos");
        }

        if (endereco.getLogradouro() == null || endereco.getLogradouro().isEmpty()){
            throw new IllegalArgumentException("Logradouro não pode ser vazio!");
        }

        if (endereco.getBairro() == null || endereco.getBairro().isEmpty()){
            throw new IllegalArgumentException("Bairro não pode ser vazio!");
        }

        if (endereco.getCidade() == null || endereco.getCidade().isEmpty()){
            throw new IllegalArgumentException("Cidade não pode ser vazia!");
        }

        if (endereco.getEstado() == null || endereco.getEstado().isEmpty()){
            throw new IllegalArgumentException("Estado não pode ser vazio!");
        }

        endereco.setCliente(clienteExisteRetorno);
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> cadastrarEnderecos(Cliente cliente, List<EnderecoDTO> enderecos) throws SenacException{

        List<Endereco> enderecoRetorno = new ArrayList<>();

        for(EnderecoDTO endereco : enderecos) {
            Endereco enderecoRecord = new Endereco();
            enderecoRecord.setBairro(endereco.getBairro());
            enderecoRecord.setCep(endereco.getCep());
            enderecoRecord.setCidade(endereco.getCidade());
            enderecoRecord.setLogradouro(endereco.getLogradouro());
            enderecoRecord.setEstado(endereco.getEstado());
            enderecoRecord.setNumero(endereco.getNumero());
            enderecoRecord.setCliente(cliente);

            enderecoRetorno.add(this.cadastrarEndereco(cliente.getId(), enderecoRecord));
        }
            return enderecoRetorno;
    }


    public Endereco editarEndereco(Long id, Long clienteId, Endereco endereco) throws SenacException {

        Cliente clienteExisteRetorno = clienteRepository.findById(clienteId)
                .orElseThrow(()-> new SenacException("Cliente não encontrado na base de dados"));

        Endereco enderecoRecord = enderecoRepository.findById(id)
                .orElseThrow(()-> new SenacException("Endereço não encontrado na base de dados"));

        if (endereco.getCep() == null || endereco.getCep().length() != 8){
            throw new IllegalArgumentException("O CEP deve ter 8 dígitos");
        }

        enderecoRecord.setCep(endereco.getCep());

        if (endereco.getLogradouro() == null || endereco.getLogradouro().isEmpty()){
            throw new IllegalArgumentException("Logradouro não pode ser vazio!");
        }

        enderecoRecord.setLogradouro(endereco.getLogradouro());

        if (endereco.getBairro() == null || endereco.getBairro().isEmpty()){
            throw new IllegalArgumentException("Bairro não pode ser vazio!");
        }

        enderecoRecord.setBairro(endereco.getBairro());

        if (endereco.getCidade() == null || endereco.getCidade().isEmpty()){
            throw new IllegalArgumentException("Cidade não pode ser vazia!");
        }

        enderecoRecord.setCidade(endereco.getCidade());

        if (endereco.getEstado() == null || endereco.getEstado().isEmpty()){
            throw new IllegalArgumentException("Estado não pode ser vazio!");
        }

        enderecoRecord.setEstado(endereco.getEstado());

        return enderecoRepository.save(enderecoRecord);

    }

    public void excluirEndereco (Long id) throws SenacException {
        if (!enderecoRepository.existsById(id)){
            throw new SenacException("Endereço não encontrado!");
        }
        enderecoRepository.deleteById(id);
    }




}
