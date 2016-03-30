/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.net.Socket;

/**
 *
 * @author CASSIO
 */
public class TratamentoCliente implements Runnable{
    private final ServidorController controller;
    private final Socket socket;

    TratamentoCliente(ServidorController controller, Socket socket) {
        this.controller = controller;
        this.socket = socket;
     }

    @Override
    public void run() {

    
    }
    
}
