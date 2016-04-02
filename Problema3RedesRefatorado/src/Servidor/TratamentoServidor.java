/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Protocolo.Protocol;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CASSIO
 */
public class TratamentoServidor implements Runnable{
    
    private Socket socket;
    
    private ServidorController controller;

    private final DataOutputStream saidaSocket;
    private final DataInputStream entradaSocket;

    
    TratamentoServidor(ServidorController controller, Socket socket,DataInputStream entradaSocket, DataOutputStream saidaSocket) throws IOException {
        this.controller = controller;
        this.socket = socket;
        this.saidaSocket = saidaSocket;
        this.entradaSocket = entradaSocket;
     }
    

    @Override
    public void run() {
    
        try {
            while(true){
                int opcao = entradaSocket.readInt();
                switch(opcao){
                    case Protocol.ATUALIZAR_LINHA_LIVRO:
                        int id = entradaSocket.readInt();
                        int qtd = entradaSocket.readInt();
                        controller.atualizarLinhaOutroServidor(id, qtd);
                        break;
                    case Protocol.VERIFICAR_SEMAFORO:
                        String nome = entradaSocket.readUTF();
                        int id2 = entradaSocket.readInt();
                        int qtd2 = entradaSocket.readInt();
                        controller.comprarLivro(nome, id2, qtd2);
                }
                        
           
            }
        } catch (IOException ex) {
            System.out.println("Servidor saiu da rede");
            try {
                this.entradaSocket.close();
                this.saidaSocket.close();
                this.socket.close();
            } catch (IOException ex1) {
                  
            }
            
        }
        
        
    }
    
}
