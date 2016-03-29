/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Protocolo.Protocol;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author allen
 */
public class Servidor {
    

    public static void main(String[] args) throws IOException {
        
        DataOutputStream saidaSocket;
        DataInputStream entradaSocket;
         
        Scanner entrada = new Scanner(System.in);
        System.err.println("Informe a porta do servidor"); 
        int port = entrada.nextInt();
        ServerSocket server = new ServerSocket(port);
        System.err.println("O servidor está operando na porta: "+ port);
        Socket socket = new Socket("localhost", 11111);
        saidaSocket = new DataOutputStream(socket.getOutputStream());
        saidaSocket.writeInt(Protocol.SERVIDOR);
        
        while(true){
            System.err.println("Aguardando novas conexões");
            Socket s = server.accept();
        }
}
}
