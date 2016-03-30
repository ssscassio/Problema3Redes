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
public class TratamentoServidor implements Runnable{
    
    private Socket socket;
    
    private ServidorController controller;

    TratamentoServidor(ServidorController controller, Socket socket) {
        this.controller = controller;
        this.socket = socket;
    
    }

    @Override
    public void run() {
    
        
    }
    
}
