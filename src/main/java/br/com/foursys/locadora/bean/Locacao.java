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
public class Locacao implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer idLocacao;
    private String dataLocacao;
    private String dataDevolucao;
    private double valor;
    private String devolvido;
    private List<LocacaoJogo> locacaoJogoList;
    private Cliente clienteIdCliente;
    private FormaPagamento formaPagamentoIdFormaPagamento;
    private Funcionario funcionarioIdFuncionario;

    public Locacao() {
    }

    public Locacao(Integer idLocacao) {
        this.idLocacao = idLocacao;
    }

    public Locacao(Integer idLocacao, String dataLocacao, String dataDevolucao, double valor, String devolvido) {
        this.idLocacao = idLocacao;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
        this.valor = valor;
        this.devolvido = devolvido;
    }

    public Integer getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(Integer idLocacao) {
        this.idLocacao = idLocacao;
    }

    public String getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(String dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDevolvido() {
        return devolvido;
    }

    public void setDevolvido(String devolvido) {
        this.devolvido = devolvido;
    }

    public List<LocacaoJogo> getLocacaoJogoList() {
        return locacaoJogoList;
    }

    public void setLocacaoJogoList(List<LocacaoJogo> locacaoJogoList) {
        this.locacaoJogoList = locacaoJogoList;
    }

    public Cliente getClienteIdCliente() {
        return clienteIdCliente;
    }

    public void setClienteIdCliente(Cliente clienteIdCliente) {
        this.clienteIdCliente = clienteIdCliente;
    }

    public FormaPagamento getFormaPagamentoIdFormaPagamento() {
        return formaPagamentoIdFormaPagamento;
    }

    public void setFormaPagamentoIdFormaPagamento(FormaPagamento formaPagamentoIdFormaPagamento) {
        this.formaPagamentoIdFormaPagamento = formaPagamentoIdFormaPagamento;
    }

    public Funcionario getFuncionarioIdFuncionario() {
        return funcionarioIdFuncionario;
    }

    public void setFuncionarioIdFuncionario(Funcionario funcionarioIdFuncionario) {
        this.funcionarioIdFuncionario = funcionarioIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLocacao != null ? idLocacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locacao)) {
            return false;
        }
        Locacao other = (Locacao) object;
        if ((this.idLocacao == null && other.idLocacao != null) || (this.idLocacao != null && !this.idLocacao.equals(other.idLocacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.foursys.locadora.bean.Locacao[ idLocacao=" + idLocacao + " ]";
    }
    
}

