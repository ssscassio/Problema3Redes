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
       
        controller = new ServidorController();
        Scanner entrada = new Scanner(System.in);
        System.out.println("Informe a porta do servidor");
        int port = entrada.nextInt();
        System.out.println("Informe o IP do distribuidor");
        String ipDistribuidor = entrada.next();
        controller.conectarComDistribuidor(ipDistribuidor,port);
        
        ServerSocket server = new ServerSocket(port);

        while(true){
            System.err.println("Aguardando novas conexões");
            Socket s = server.accept();
        }
}
}
