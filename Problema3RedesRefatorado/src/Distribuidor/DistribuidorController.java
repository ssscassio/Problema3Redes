/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuidor;

import Model.Cliente;
import Model.Livro;
import Protocolo.Protocol;
import Util.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CASSIO
 */
public class DistribuidorController {
    
    private ArrayList<String> ipsPortas; //Cada String no formato IP-PORTA
    private ArrayList<TratamentoServidor> servidoresConectados = new ArrayList<TratamentoServidor>();
    private ArrayList<Socket> conexoes = new ArrayList<Socket>();
    private int indiceServidorRR;
    private ArrayList<Livro> livros;
    
    
    private static LeituraLivro leituraLivro;
    private static LeituraCliente leituraCliente = new LeituraCliente();
    private ArrayList<Cliente> clientes;
    
    public DistribuidorController(){
    
    
    }

    public void adicionarServidor(TratamentoServidor server) throws IOException {
        this.servidoresConectados.add(server);
        //Estabelecer conexão com o servidor:
        System.err.println("Estabelecendo conexão com servidor: "+server.getIpServidor()+"-"+ server.getPortaDeAcesso());
        Socket socket = new Socket(server.getIpServidor(), server.getPortaDeAcesso());
        DataOutputStream saidaSocket = new DataOutputStream(socket.getOutputStream());
        
        saidaSocket.writeInt(Protocol.DISTRIBUIDOR);//Código para dizer que é DISTRIBUIDOR
        
        this.conexoes.add(socket);
        
        //Reinicia a lista de IPsPorta;
        this.ipsPortas = new ArrayList<String>();
        
        System.err.println("Gerando Lista de IPS");
        for(int i = 0; i < servidoresConectados.size(); i++){
            String aux = servidoresConectados.get(i).getIpServidor()+"-"+servidoresConectados.get(i).getPortaDeAcesso();
            this.ipsPortas.add(aux);
            System.err.println(aux);
        }
    }

    public void enviarIps() {//Chamar tanto quando servidor entra, como quando servidor sai
        
        
        //Concatenando Os IP-Porta dos servidores e enviando para os servidores
        for(int i = 0; i<servidoresConectados.size();i++){
            String aux=""; //String com IP-Porta Concatenados. No formato IP-PORTA/IP-PORTA/IP-PORTA/
            for(int j=0; j<ipsPortas.size(); j++){
                if(i != j){//Não adiciona o próprio servidor na lista
                    aux += ipsPortas.get(j) +"/";
                }
            }
            try {
                DataOutputStream saidaSocket = new DataOutputStream(conexoes.get(i).getOutputStream());
                saidaSocket.writeInt(Protocol.LISTA_DE_IPS);//Envia código de operação dizendo que vai enviar IPS
                saidaSocket.writeUTF(aux);
                System.err.println("Distribuidor Enviou Ips para servidor:"+ servidoresConectados.get(i).getIpServidor() + "-" + servidoresConectados.get(i).getPortaDeAcesso());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
        }
    }
    
    public int getNumServidoresConectados(){
        return this.conexoes.size();
    }

    void balancearCliente(TratamentoCliente cliente) {
        
        TratamentoServidor servidorParaConectar = servidoresConectados.get(indiceServidorRR++ %servidoresConectados.size());
        cliente.conectarComServidor(servidorParaConectar.getIpServidor(), servidorParaConectar.getPortaDeAcesso());
        
    }

    void removerServidor(String ipServidorCaiu, int portaServidorCaiu) {
        String busca = ipServidorCaiu + "-" + portaServidorCaiu;
        int index = 0;
        if(ipsPortas.contains(busca)){
            for(;index<ipsPortas.size() && ipsPortas.equals(busca);index ++);//For para parar no index do servidor que caiu;
            
            //Fazer remoção de todas as listas:
            ipsPortas.remove(index);
            servidoresConectados.remove(index);
            conexoes.remove(index);
            
        }   
    }

    public void lerLivrosArquivo(){
    
        leituraLivro = new LeituraLivro();
        this.livros = leituraLivro.leitura("Livros_Distribuidor.txt");
        
    }
    
    public void reorganizarSemaforos() {
        

        for (int i = 0; i < livros.size(); i++) {
            String ipPorta = ipsPortas.get(i % ipsPortas.size());
            
            String aux[] = ipPorta.split("-");
            
            livros.get(i).setIp(aux[0]);
            livros.get(i).setPort(Integer.parseInt(aux[1]));
            livros.get(i).setSemaforo(1);
        }
        System.out.println("Verificando Posse dos recursos: ");
        for (int i = 0; i < livros.size(); i++) {

            Livro l = (Livro) livros.get(i);
            
            System.out.println(i + " servidor: " + l.getIp() + " porta " + l.getPort() + " semáforo " + l.getSemaforo());
        }
        
    }
    

    public String getLivros() {
        Iterator it = livros.iterator();
        String txt = "";
        while (it.hasNext()) {
            Livro l = (Livro) it.next();
            txt += l.toString() + "\r\n";
        }
        return txt;
    
    }

    public void enviarPosseArquivos() {
        
        String aux=""; //String que armazenara os livros;
        
        Iterator it = livros.iterator();
        while(it.hasNext()){
            Livro l = (Livro)it.next();
            aux+=l.toStringComSemaforo()+"\n";
        }
        for(int i = 0; i<servidoresConectados.size();i++){
            try {
                DataOutputStream saidaSocket = new DataOutputStream(conexoes.get(i).getOutputStream());
                saidaSocket.writeInt(Protocol.LISTA_LIVRO_COM_SEMAFORO);//Envia código de operação dizendo que vai enviar IPS
                saidaSocket.writeUTF(aux);//Colocar Novo arquivo aqui
                System.err.println("Distribuidor Enviou Livro com Semaforos para servidor:"+ servidoresConectados.get(i).getIpServidor() + "-" + servidoresConectados.get(i).getPortaDeAcesso());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
        }
    }
    
    public void AtualizarListaCliente(String nome, double valor) throws IOException{
        for(int i=0; i <clientes.size(); i++){
            if(clientes.get(i).getNome().equals(nome)){
                clientes.get(i).setValor(clientes.get(i).getValor() + valor);
                leituraCliente.salvarLista(clientes, "Cliente.txt");
                return;
            }
        }
        
        Cliente cliente =  new Cliente(nome, valor);
        clientes.add(cliente);
        leituraCliente.salvarLista(clientes, "Cliente.txt");
        
    }

    void lerClientesArquivo() {
        this.clientes = leituraCliente.leitura("Cliente.txt");
    }
    
    public void atualizarLivros(int id, int qtd) throws IOException{
        for(int i = 0; i < livros.size(); i++){
            if(livros.get(i).getId() == id) {
                livros.get(i).setQtd(livros.get(i).getQtd() - qtd);
            }
        
        }
        
        LeituraLivro leituras = new LeituraLivro();
    
        leituras.salvarLista(livros, "Livros_Distribuidor.txt");
    }
}
