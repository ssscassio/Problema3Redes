/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Protocolo.Protocol;
import java.io.*;
import java.net.*;

/**
 *
 * @author CASSIO
 */
class ClienteController {
    
    //Referente ao distribuidor
    private final String ipDistribuidor;
    private Socket socketDistribuidor;
    private DataOutputStream dSaidaSocket;
    private DataInputStream dEntradaSocket;
    
    //Referente ao servidor
    private String ipServidor;
    private int portaServidor;
    private Socket socketServidor;
    private DataOutputStream sSaidaSocket;
    private DataInputStream sEntradaSocket;
    
    
    public ClienteController(String ipDistribuidor){
        this.ipDistribuidor = ipDistribuidor;
    }
    
    public void iniciarSistema(){
     boolean confirmacaoOperacao = false;
        try{
            //Estabelece conexão com o Distribuidor
            Socket cliente = new Socket(this.ipDistribuidor, 11111);
            System.err.println("Cliente se conectou ao distribuidor");
            dSaidaSocket = new DataOutputStream(cliente.getOutputStream());
            dEntradaSocket = new DataInputStream(cliente.getInputStream());
            dSaidaSocket.writeInt(Protocol.CLIENTE);//Envia codigo de operação 1 para se mostrar como cliente
            System.err.println("Enviou codigo para Distribuidor para sinalizar que é cliente");
            
            this.conectarComServidor();
            
        }catch(IOException e){
           e.printStackTrace();
        }
    }
    
    public void conectarComServidor() throws IOException{
            dSaidaSocket.writeInt(Protocol.CONECTAR_COM_SERVIDOR);
            //Recebe o IP do servidor que vai se conectar
            this.ipServidor = dEntradaSocket.readUTF();
            //Recebe a porta do servidor que vai se conectar
            this.portaServidor = dEntradaSocket.readInt();
            
            System.err.println("Cliente recebeu Servidor para conectar: "+ipServidor +"-"+portaServidor);
            //Estabelece conexão com o Distribuidor
            Socket cliente = new Socket(this.ipServidor, this.portaServidor);
            System.err.println("Cliente se conectou ao Servidor");
            sSaidaSocket = new DataOutputStream(cliente.getOutputStream());
            sEntradaSocket = new DataInputStream(cliente.getInputStream());
            sSaidaSocket.writeInt(Protocol.CLIENTE);//Envia codigo de operação 1 para se mostrar como cliente
            System.err.println("Enviou codigo para Servidor para sinalizar que é cliente");
            
    
    }
}
