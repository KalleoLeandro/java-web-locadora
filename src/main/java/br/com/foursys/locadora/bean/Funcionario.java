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
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer idFuncionario;
    private String nome;
    private String cpf;
    private String rg;
    private String dataNascimento;
    private int idade;
    private String sexo;
    private String login;
    private String senha;
    private String perfilAcesso;
    private Contato contatoIdContato;
    private Endereco enderecoIdEndereco;
    private List<Locacao> locacaoList;

    public Funcionario() {
    }

    public Funcionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Funcionario(Integer idFuncionario, String nome, String cpf, String rg, String dataNascimento, int idade, String sexo, String login, String senha, String perfilAcesso) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.sexo = sexo;
        this.login = login;
        this.senha = senha;
        this.perfilAcesso = perfilAcesso;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfilAcesso() {
        return perfilAcesso;
    }

    public void setPerfilAcesso(String perfilAcesso) {
        this.perfilAcesso = perfilAcesso;
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
        hash += (idFuncionario != null ? idFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.idFuncionario == null && other.idFuncionario != null) || (this.idFuncionario != null && !this.idFuncionario.equals(other.idFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.foursys.locadora.bean.Funcionario[ idFuncionario=" + idFuncionario + " ]";
    }
    
}
