/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author allen
 */
public class TratamentoCliente implements Runnable{

    private Socket socket;
    private DataInputStream entradaSocket;
    private DataOutputStream saidaSocket;

    public TratamentoCliente(Socket socket, DataInputStream entradaSocket, DataOutputStream saidaSocket) {
        this.socket = socket;
        this.entradaSocket = entradaSocket;
        this.saidaSocket = saidaSocket;

    }
    @Override
    public void run() {
        System.err.println("Inciando thread do cliente");
    }
}
