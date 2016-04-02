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
public class Livro implements java.io.Serializable{
    
    private  int id;
    private  String nome;
    private  double valor;
    private  int qtd;
    private  int semaforo;
    private  int port;
    private  String ip;

    public Livro(int id, String nome, double valor, int qtdDisponivel) {
        this.id = id;
        this.nome = nome;
        this.qtd = qtdDisponivel;
        this.valor = valor;
    }
    
    public Livro(int id, String nome, double valor, int qtdDisponivel, String ip, int porta, int semaforo) {
        this.id = id;
        this.nome = nome;
        this.qtd = qtdDisponivel;
        this.valor = valor;
        this.ip = ip;
        this.port = porta;
        this.semaforo = semaforo;
    }
    
    @Override
    public String toString() {
        return ("id " + getId() + " livro " + getNome() + " valor " + getValor() + " qtd "+ getQtd());
    }
    
    public String toStringComSemaforo() {
        return ("id " + getId() + " livro " + getNome() + " valor " + getValor() +
                " qtd "+ getQtd() + " ip " + getIp() + " porta " + getPort()+ " semáforo " + getSemaforo());
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

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    
}

    

