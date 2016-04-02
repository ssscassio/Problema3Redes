/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuidor;

import Util.LeituraCliente;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author allen
 */
public class Distribuidor {
   
    
    private static DistribuidorController controller;
    
 
    public static void main(String[] args) throws IOException {
        
        
        //Iniciando a instancia do Controller
        controller = new DistribuidorController();
        
        ServerSocket server = new ServerSocket(11111);
        DataInputStream entradaSocket;
        DataOutputStream saidaSocket;
        System.err.println("O distribuidor está rodando na porta 11111");
        controller.lerLivrosArquivo();
        controller.lerClientesArquivo();
        System.out.println(controller.getLivros());
        while(true){
            System.err.println("Aguardando novas conexões");
            Socket socket = server.accept();
            entradaSocket = new DataInputStream(socket.getInputStream());
            saidaSocket = new DataOutputStream(socket.getOutputStream());
            ThreadMaquina tratamento = new ThreadMaquina(controller, socket , entradaSocket , saidaSocket);
            Thread t = new Thread(tratamento);
            t.start();
        }
                
    }
    
}