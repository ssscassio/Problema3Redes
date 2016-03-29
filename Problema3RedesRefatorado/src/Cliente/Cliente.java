/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Protocolo.Protocol;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author allen
 */
public class Cliente {
    public static void main(String[] args) throws IOException {
        
        DataOutputStream saidaSocket;
        DataInputStream entradaSocket;
        Socket cliente = new Socket("localhost", 11111);
        System.err.println("Cliente se conectou ao distribuidor");
        saidaSocket = new DataOutputStream(cliente.getOutputStream());
        saidaSocket.writeInt(Protocol.CLIENTE);
        
        
    }
}