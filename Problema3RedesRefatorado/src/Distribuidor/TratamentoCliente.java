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
public class TratamentoCliente implements Runnable{

    private Socket socket;
    private DataInputStream entradaSocket;
    private DataOutputStream saidaSocket;
    private String ipServidor;
    private int portaServidor;
    private DistribuidorController controller;
    
    public TratamentoCliente(DistribuidorController controller,Socket socket, DataInputStream entradaSocket, DataOutputStream saidaSocket) {
        this.controller = controller;
        this.socket = socket;
        this.entradaSocket = entradaSocket;
        this.saidaSocket = saidaSocket;

    }
    @Override
    public void run() {
        try{
            while(true){
                int operacao = entradaSocket.readInt();
                switch(operacao){
                    case Protocol.CONECTAR_COM_SERVIDOR:
                        controller.balancearCliente(this); 
                        break;
                    case Protocol.INFORMAR_QUEDA_SERVIDOR:
                        //Fazer Operacoes de reoganizacao
                        String ipServidorCaiu = entradaSocket.readUTF();
                        int portaServidorCaiu = entradaSocket.readInt();
                        controller.removerServidor(ipServidorCaiu, portaServidorCaiu);
                        controller.reorganizarSemaforos();
                        break;
                }
            }
        } catch(IOException e){
            //e.printStackTrace();
        }
       
    }

    void conectarComServidor(String ipServidor, int portaDeAcesso) {
        this.ipServidor = ipServidor;
        this.portaServidor = portaDeAcesso;
        
        System.err.println("Enviando IP e Porta de Servidor ao qual o cliente deve se conectar");
        try {
            this.saidaSocket.writeUTF(this.ipServidor);
            this.saidaSocket.writeInt(this.portaServidor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    
    }
}
