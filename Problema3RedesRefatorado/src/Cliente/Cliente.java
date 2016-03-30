/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Protocolo.Protocol;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author allen
 */
public class Cliente {
    
    private static ClienteController controller;
    
    public static void main(String[] args) throws IOException {
        
        Scanner leitor = new Scanner(System.in);
        System.out.println("Informe o IP do distribuidor");
        String distribuidorIP = leitor.next();
        controller = new ClienteController(distribuidorIP);
        
        
        controller.iniciarSistema();
        
        
        
    }
}