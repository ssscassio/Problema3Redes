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
public class Livro {
    
    private int id;
    private String nome;
    private double valor;
    private int qtd;
    private int semaforo;
    private int port;

    public Livro(int id, String nome, double valor, int qtdDisponivel) {
        this.id = id;
        this.nome = nome;
        this.qtd = qtdDisponivel;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return ("id " + getId() + " livro " + getNome() + " valor " + getValor() + " qtd "+ getQtd());
    }
    
    public void setPort(int port){
        this.port = port;
    }
    
    public int getPort(){
        return this.port;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * @return the qtd
     */
    public int getQtd() {
        return qtd;
    }

    /**
     * @param qtd the qtd to set
     */
    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    /**
     * @return the semaforo
     */
    public int getSemaforo() {
        return semaforo;
    }

    /**
     * @param semaforo the semaforo to set
     */
    public void setSemaforo(int semaforo) {
        this.semaforo = semaforo;
    }
}

    

