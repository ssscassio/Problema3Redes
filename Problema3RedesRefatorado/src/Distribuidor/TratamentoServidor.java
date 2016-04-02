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
public class TratamentoServidor implements Runnable {

    private Socket socket;
    private DataInputStream entradaSocket;
    private DataOutputStream saidaSocket;
    
    private String ipServidor;
    private int portaDeAcesso;
    private DistribuidorController controller;
    /**
     * Construtor da classe TratamentoServidor
     * Classe responsável por tratar os dados enviados por um Servidor no Distribuidor
     * @param socket
     * @param entradaSocket
     * @param saidaSocket 
     */
    public TratamentoServidor(DistribuidorController controller, Socket socket, DataInputStream entradaSocket, DataOutputStream saidaSocket) {
        this.socket = socket;
        this.entradaSocket = entradaSocket;
        this.saidaSocket = saidaSocket;
        this.controller = controller;

    }

    @Override
    public void run() {
        System.err.println("Inciando thread do servidor");
        try {
            while(true){
                //Inicio de Conexão do Servidor com o distribuidor
                //Servidor Envia Seu IP, e sua porta de acesso
                int opcao = entradaSocket.readInt();//Enviar Codigo de operacao
                switch(opcao){
                    case Protocol.SERVIDOR_ENVIAR_IP_PORTA:
                        this.ipServidor = entradaSocket.readUTF();
                        this.portaDeAcesso = entradaSocket.readInt();
                        System.err.println("Informações do Servidor: IP: "+ipServidor+" Porta de Acesso: "+portaDeAcesso);
                        controller.adicionarServidor(this);
                        if(controller.getNumServidoresConectados() > 1){//Envia apenas se tiver mais de 1 servidor conectado
                            controller.enviarIps();//Envia os Ips dos outros servidores para todos os servidores    
                        }
                        controller.reorganizarSemaforos();
                        controller.enviarPosseArquivos();
                        break;
                    case Protocol.GUARDAR_DADOS_CLIENTE:
                        String nomeCliente = entradaSocket.readUTF();
                        Double valor = entradaSocket.readDouble();
                        controller.AtualizarListaCliente(nomeCliente, valor);
                        break;
                    case Protocol.ATUALIZAR_LINHA_LIVRO:
                        int id = entradaSocket.readInt();
                        int qtd = entradaSocket.readInt();
                        controller.atualizarLivros(id, qtd);
                        break;
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public Socket getSocket(){
        return this.socket;
    }
    
    public DataOutputStream getOutput(){
        return this.saidaSocket;
    }
    
    public String getIpServidor(){
        return this.ipServidor;
    }

    public int getPortaDeAcesso(){
        return this.portaDeAcesso;
    }
}
