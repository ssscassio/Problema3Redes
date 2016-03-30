/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author allen
 */
public class Cliente {
    
    private String nome;
    private double valor;
    
    public Cliente(String nome,double valor){
        this.nome = nome;
        this.valor = valor;
    }

    @Override
    public String toString(){
     return ("cliente: " + nome + " valor total de compra " + valor);
    }
    
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    
    }
    
}
