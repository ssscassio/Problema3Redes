/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;


import Protocolo.Protocol;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CASSIO
 */
public class ThreadMaquina implements Runnable{
    
     private Socket socket;
    private DataInputStream entradaSocket;
    private DataOutputStream saidaSocket;
    private ServidorController controller;
    
    public ThreadMaquina(ServidorController controller, Socket socket, DataInputStream entradaSocket, DataOutputStream saidaSocket) {
        
        this.controller = controller;
        this.socket = socket;
        this.entradaSocket = entradaSocket;
        this.saidaSocket = saidaSocket;

    }
    
    
    @Override
    public void run() {

     
        try {
                
            int opcao = entradaSocket.readInt();
            System.out.println("Opção: " + opcao);
            switch (opcao) {
                case Protocol.DISTRIBUIDOR:
                    System.out.println("Distribuidor se conectou");
                    TratamentoDistribuidor distribuidor = new TratamentoDistribuidor(controller, socket,entradaSocket,saidaSocket);
                    Thread d = new Thread(distribuidor);
                    d.start();
                    break;
                case Protocol.SERVIDOR:
                    System.out.println("Servidor se conectou");
                    TratamentoServidor servidor = new TratamentoServidor(controller, socket,entradaSocket,saidaSocket);
                    Thread s = new Thread(servidor);
                    s.start();
                    break;
                case Protocol.CLIENTE:
                    System.out.println("Cliente se conectou");
                    TratamentoCliente cliente = new TratamentoCliente(controller, socket, entradaSocket, saidaSocket);
                    Thread c = new Thread(cliente);
                    c.start();
                    break;
            }

        } catch (IOException ex) {
            Logger.getLogger(Distribuidor.ThreadMaquina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
