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
    private ServidorController controller;
    TratamentoDistribuidor(ServidorController controller, Socket socket,DataInputStream entradaSocket, DataOutputStream saidaSocket) throws IOException {
        this.socket = socket;
        this.controller = controller;
        this.entradaSocket = entradaSocket;
        this.saidaSocket = saidaSocket;
    }

    @Override
    public void run() {

        try {
            while(true){
                int opcao = entradaSocket.readInt();
                System.err.println("Opcao = " + opcao);
                switch(opcao){
                    case Protocol.LISTA_DE_IPS:
                        String listaOutrosServidores;
                        listaOutrosServidores = entradaSocket.readUTF();
                        System.err.println("IPs recebidos do distribuidor:");
                        System.err.println(listaOutrosServidores);
                        controller.setListaDeIps(listaOutrosServidores);
                        controller.conectarComServidores();
                        break;
                    case Protocol.LISTA_LIVRO_COM_SEMAFORO:
                        String listaLivroComSemaforo;
                        listaLivroComSemaforo = entradaSocket.readUTF();
                        System.out.println(listaLivroComSemaforo);
                        controller.setLivros(listaLivroComSemaforo);
                        break;
                }
            }
        } catch (IOException ex) {

        }
    }
    
}
