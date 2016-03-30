/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Protocolo.Protocol;
import java.io.*;
import java.net.Socket;

/**
 *
 * @author CASSIO
 */
public class TratamentoDistribuidor implements Runnable{

    private Socket socket;
    private DataInputStream entradaSocket;
    private DataOutputStream saidaSocket;
    TratamentoDistribuidor(ServidorController controller, Socket socket) throws IOException {
        this.socket = socket;
        this.entradaSocket = new DataInputStream(socket.getInputStream());
        this.saidaSocket = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {

        try {
            int opcao = entradaSocket.readInt();
            switch(opcao){
                case Protocol.LISTA_DE_IPS:
                    String listaOutrosServidores;
                    listaOutrosServidores = entradaSocket.readUTF();
                    System.err.println("IPs recebidos do distribuidor:");
                    System.err.println(listaOutrosServidores);
                    break;

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
