/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Protocolo.Protocol;
import java.io.*;
import java.net.*;

/**
 *
 * @author CASSIO
 */
public class TratamentoCliente implements Runnable{
    private final ServidorController controller;
    private final Socket socket;
    private final DataOutputStream saidaSocket;
    private final DataInputStream entradaSocket;

    private String nomeCliente;
    TratamentoCliente(ServidorController controller, Socket socket,DataInputStream entradaSocket, DataOutputStream saidaSocket) throws IOException {
        this.controller = controller;
        this.socket = socket;
        this.saidaSocket = saidaSocket;
        this.entradaSocket = entradaSocket;
     }

    @Override
    public void run() {
        System.err.println("Thread do Cliente iniciada");
        try{
            while(true){
                //Inicio de Conexão do Servidor com o distribuidor
                //Servidor Envia Seu IP, e sua porta de acesso
                int opcao = entradaSocket.readInt();//Enviar Codigo de operacao
                switch(opcao){
                    case Protocol.ENVIAR_NOME_CLIENTE:
                        this.nomeCliente = entradaSocket.readUTF();
                        System.err.println("Cliente conectado: "+ this.nomeCliente);
                        break;
                    case Protocol.EFETUAR_COMPRA:
                        int idLivro = entradaSocket.readInt();
                        int quantidadeCompra = entradaSocket.readInt();
                        boolean confirmacao = controller.comprarLivro(nomeCliente, idLivro,quantidadeCompra);
                        saidaSocket.writeBoolean(confirmacao);//True = sucesso//False = erro
                        break;
                    case Protocol.RECEBER_LISTA_LIVROS:
                        saidaSocket.writeUTF(controller.getStringlivros());
                        break;

                }
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
