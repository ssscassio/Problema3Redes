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
        aguardar(100);
        
        System.out.println("Informe seu nome:");
        String nome = leitor.next();
        controller.enviarNomeCliente(nome);
        
        while(true){
            controller.receberListaLivros();
            System.out.println(controller.getLivros());
            System.out.println("Informe a opção desejada");
            System.out.println("1 - Comprar um livro");
            
            int operacao = leitor.nextInt();
            leitor.nextLine();
            switch(operacao){
                case 1: //Comprar um livro
                    System.out.println("Informe o id do Livro");
                    int id = leitor.nextInt();
                    System.out.println("Informe a quantidade");
                    int qtd = leitor.nextInt();
                    controller.comprarLivro(id, qtd);
                    break;
            
            
            }
            
            
        }
        
        
    }

     private static void aguardar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}