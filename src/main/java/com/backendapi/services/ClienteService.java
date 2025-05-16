package com.backendapi.services;

import com.backendapi.dtos.ClienteDTO;
import com.backendapi.entities.Cliente;
import com.backendapi.entities.Endereco;
import com.backendapi.exceptions.SenacException;
import com.backendapi.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoService enderecoService;


    public List<Cliente> listarClientes() throws SenacException {

        List<Cliente> listaRetorno = clienteRepository.findAll();

        if (listaRetorno.isEmpty()) {
            throw new SenacException("A tabela de Clientes encontra-se vazia");
        }

        return listaRetorno;
    }

    public Cliente cadastrarCliente(Cliente cliente){

        if(cliente.getNome() == null || cliente.getNome().isEmpty()){
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (cliente.getSobrenome() == null || cliente.getSobrenome().isEmpty()){
            throw new IllegalArgumentException("Sobrenome não pode ser vazio");
        }

        if (cliente.getDocumento() == null || cliente.getDocumento().isEmpty()){
            throw new IllegalArgumentException("Documento não pode ser vazio");
        }

        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()){
            throw new IllegalArgumentException("Email não pode ser vazio");
        }

        return clienteRepository.save(cliente);
    }

    public Cliente cadastrarClienteCompleto(ClienteDTO cliente) throws  SenacException{
        Cliente clienteRecord = new Cliente();
        clienteRecord.setNome(cliente.getNome());
        clienteRecord.setSobrenome(cliente.getSobrenome());
        clienteRecord.setDataNascimento(cliente.getDataNascimento());
        clienteRecord.setEmail(cliente.getEmail());
        clienteRecord.setIdade(cliente.getIdade());
        clienteRecord.setDdd(cliente.getDdd());
        clienteRecord.setTelefone(cliente.getTelefone());
        clienteRecord.setSexo(cliente.getSexo());
        clienteRecord.setDocumento(cliente.getDocumento());

        Cliente clienteRetorno = this.cadastrarCliente(clienteRecord);

        if(cliente.getEnderecos() != null &&
                cliente.getEnderecos().isEmpty() == false) {
            List<Endereco> enderecosResult =
                    enderecoService.cadastrarEnderecos(clienteRetorno, cliente.getEnderecos());

            clienteRetorno.setEnderecos(enderecosResult);
        }

        return clienteRetorno;
    }


    public Cliente editarCliente(Long id, Cliente cliente) throws SenacException {

        Cliente clienteRecord = clienteRepository.findById(id)
                .orElseThrow(()->new SenacException("Não localizado!"));


        if (cliente.getNome() == null || cliente.getNome().isEmpty()){
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        clienteRecord.setNome(cliente.getNome());

        if (cliente.getSobrenome() == null || cliente.getSobrenome().isEmpty()){
            throw new IllegalArgumentException("Sobrenome não pode ser vazio");
        }

        clienteRecord.setSobrenome(cliente.getSobrenome());

        if (cliente.getDocumento() == null || cliente.getDocumento().isEmpty()){
            throw new IllegalArgumentException("Documento não pode ser vazio");
        }

        clienteRecord.setDocumento(cliente.getDocumento());

        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()){
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }

        clienteRecord.setEmail(cliente.getEmail());

        if (cliente.getIdade() != clienteRecord.getIdade()){
            clienteRecord.setIdade(cliente.getIdade());
        }

        if (!cliente.getSexo().equals(clienteRecord.getSexo())){
            clienteRecord.setSexo(cliente.getSexo());
        }

        if (cliente.getDataNascimento() != clienteRecord.getDataNascimento()){
            clienteRecord.setDataNascimento(cliente.getDataNascimento());
        }

        if (cliente.getDdd() != clienteRecord.getDdd()){
            clienteRecord.setDdd(cliente.getDdd());
        }

        if (cliente.getTelefone() != clienteRecord.getTelefone()){
            clienteRecord.setTelefone(cliente.getTelefone());
        }

        return clienteRepository.save(clienteRecord);
    }

    public void excluirCliente (Long id) throws SenacException {
        if (!clienteRepository.existsById(id)){
            throw new SenacException("Cliente não encontrado");
        }
        
         clienteRepository.deleteById(id);
    }

}