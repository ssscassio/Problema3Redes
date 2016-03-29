/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuidor;

import Protocolo.Protocol;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author allen
 */
public class ThreadMaquina implements Runnable{

    private Socket socket;
    private DataInputStream entradaSocket;
    private DataOutputStream saidaSocket;
    
    
    

    public ThreadMaquina(Socket socket, DataInputStream entradaSocket, DataOutputStream saidaSocket) {
        this.socket = socket;
        this.entradaSocket = entradaSocket;
        this.saidaSocket = saidaSocket;
    
    }
    @Override
    public void run() {
        
        try {
            int opcao = entradaSocket.readInt();
            System.out.println("Opção: " + opcao);
            switch (opcao){
                case 1: // diz se é cliente
                    break;
            }
                    
        } catch (IOException ex) {
            Logger.getLogger(ThreadMaquina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}