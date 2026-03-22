/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.foursys.locadora.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author dmunhoz
 */
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer idCliente;
    private String nome;
    private String cpf;
    private String rg;
    private String dataNascimento;
    private int idade;
    private String sexo;
    private Contato contatoIdContato;
    private Endereco enderecoIdEndereco;
    private List<Locacao> locacaoList;

    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, String nome, String cpf, String rg, String dataNascimento, int idade, String sexo) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.sexo = sexo;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public Contato getContatoIdContato() {
        return contatoIdContato;
    }

    public void setContatoIdContato(Contato contatoIdContato) {
        this.contatoIdContato = contatoIdContato;
    }

    public Endereco getEnderecoIdEndereco() {
        return enderecoIdEndereco;
    }

    public void setEnderecoIdEndereco(Endereco enderecoIdEndereco) {
        this.enderecoIdEndereco = enderecoIdEndereco;
    }

    public List<Locacao> getLocacaoList() {
        return locacaoList;
    }

    public void setLocacaoList(List<Locacao> locacaoList) {
        this.locacaoList = locacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.foursys.locadora.bean.Cliente[ idCliente=" + idCliente + " ]";
    }
    
}
