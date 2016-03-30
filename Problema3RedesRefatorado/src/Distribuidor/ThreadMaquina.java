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
public class ThreadMaquina implements Runnable {

    private Socket socket;
    private DataInputStream entradaSocket;
    private DataOutputStream saidaSocket;
    private DistribuidorController controller;
    
    public ThreadMaquina(DistribuidorController controller, Socket socket, DataInputStream entradaSocket, DataOutputStream saidaSocket) {
        
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
                // como thread de servidor será diferente de thread de cliente irei personalizar cada uma
                // cassio lembrando que essa thread irá morrer depois da criação
                case Protocol.SERVIDOR://diz que é Servidor
                    System.out.println("Novo Servidor se conectou");
                    TratamentoServidor server = new TratamentoServidor(controller, socket, entradaSocket , saidaSocket);
                    Thread s = new Thread(server);
                    //this.controller.enviarArquivoNovosSemaforos(); //Envia o arquivo com os novos semaforos para todos os servidores
                    s.start();
                    break;
                case Protocol.CLIENTE: // diz se é cliente
                    System.out.println("Novo Cliente se conectou");
                    TratamentoCliente cliente = new TratamentoCliente(controller, socket, entradaSocket , saidaSocket);
                    Thread c = new Thread(cliente);
                    c.start();
                    break;
            }

        } catch (IOException ex) {
            Logger.getLogger(ThreadMaquina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
