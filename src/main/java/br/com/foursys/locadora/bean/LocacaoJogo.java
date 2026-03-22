/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.foursys.locadora.bean;

import java.io.Serializable;

/**
 *
 * @author dmunhoz
 */
public class LocacaoJogo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer idLocacaoJogo;
    private Jogo jogoIdJogo;
    private Locacao locacaoIdLocacao;

    public LocacaoJogo() {
    }

    public LocacaoJogo(Integer idLocacaoJogo) {
        this.idLocacaoJogo = idLocacaoJogo;
    }

    public Integer getIdLocacaoJogo() {
        return idLocacaoJogo;
    }

    public void setIdLocacaoJogo(Integer idLocacaoJogo) {
        this.idLocacaoJogo = idLocacaoJogo;
    }

    public Jogo getJogoIdJogo() {
        return jogoIdJogo;
    }

    public void setJogoIdJogo(Jogo jogoIdJogo) {
        this.jogoIdJogo = jogoIdJogo;
    }

    public Locacao getLocacaoIdLocacao() {
        return locacaoIdLocacao;
    }

    public void setLocacaoIdLocacao(Locacao locacaoIdLocacao) {
        this.locacaoIdLocacao = locacaoIdLocacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLocacaoJogo != null ? idLocacaoJogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocacaoJogo)) {
            return false;
        }
        LocacaoJogo other = (LocacaoJogo) object;
        if ((this.idLocacaoJogo == null && other.idLocacaoJogo != null) || (this.idLocacaoJogo != null && !this.idLocacaoJogo.equals(other.idLocacaoJogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.foursys.locadora.bean.LocacaoJogo[ idLocacaoJogo=" + idLocacaoJogo + " ]";
    }
    
}

