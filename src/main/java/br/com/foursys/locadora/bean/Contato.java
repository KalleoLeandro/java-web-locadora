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
public class Contato implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer idContato;
    private String telefone;
    private String celular;
    private String email;
    private List<Cliente> clienteList;
    private List<Funcionario> funcionarioList;

    public Contato() {
    }

    public Contato(Integer idContato) {
        this.idContato = idContato;
    }

    public Integer getIdContato() {
        return idContato;
    }

    public void setIdContato(Integer idContato) {
        this.idContato = idContato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public List<Funcionario> getFuncionarioList() {
        return funcionarioList;
    }

    public void setFuncionarioList(List<Funcionario> funcionarioList) {
        this.funcionarioList = funcionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContato != null ? idContato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contato)) {
            return false;
        }
        Contato other = (Contato) object;
        if ((this.idContato == null && other.idContato != null) || (this.idContato != null && !this.idContato.equals(other.idContato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.foursys.locadora.bean.Contato[ idContato=" + idContato + " ]";
    }
    
}
