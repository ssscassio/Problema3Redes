/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Protocolo.Protocol;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author allen
 */
public class Servidor {
    
    private static ServidorController controller;
    
    public static void main(String[] args) throws IOException {
       
        Scanner entrada = new Scanner(System.in);
        System.out.println("Informe a porta do servidor");
        int port = entrada.nextInt();
        System.out.println("Informe o IP do distribuidor");
        String ipDistribuidor = entrada.next();
       
        
        ServerSocket server = new ServerSocket(port);

        controller = new ServidorController("127.0.0.1",port);
        controller.conectarComDistribuidor(ipDistribuidor,port);
        
        while(true){
            System.err.println("Aguardando novas conexões");
            Socket socket = server.accept();
            DataInputStream entradaSocket = new DataInputStream(socket.getInputStream());
            DataOutputStream saidaSocket = new DataOutputStream(socket.getOutputStream());
            ThreadMaquina tratamento = new ThreadMaquina(controller, socket , entradaSocket , saidaSocket);
            Thread t = new Thread(tratamento);
            t.start();
        }
}
}
