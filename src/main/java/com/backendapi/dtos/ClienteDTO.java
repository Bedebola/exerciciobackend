package com.backendapi.dtos;

import java.time.LocalDate;
import java.util.List;

public class ClienteDTO {

    private String nome;
    private String sobrenome;
    private String documento;
    private String email;
    private int idade;
    private String sexo;
    private LocalDate DataNascimento;
    private int ddd;
    private int telefone;
    private List<EnderecoDTO> enderecos;

    public ClienteDTO() {
    }

    public ClienteDTO(String nome, String sobrenome, String documento, String email, int idade, String sexo, LocalDate dataNascimento, int ddd, int telefone, List<EnderecoDTO> enderecos) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.email = email;
        this.idade = idade;
        this.sexo = sexo;
        DataNascimento = dataNascimento;
        this.ddd = ddd;
        this.telefone = telefone;
        this.enderecos = enderecos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        DataNascimento = dataNascimento;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }
}
