/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Model.Cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author allen
 */
public class LeituraCliente {
    private ArrayList<Cliente> Clientes;

    public LeituraCliente () {
        Clientes = new ArrayList<>();
    }
    
    

    public ArrayList<Cliente> leitura(String nomeTXT) {
        try {
            FileReader arq = new FileReader(nomeTXT);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            while (linha != null) {
                Clientes.add(refatorar(linha));
                linha = lerArq.readLine();
            }
            arq.close();
        } catch (IOException ex) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    ex.getMessage());
        }
        return Clientes;
    }

    private Cliente refatorar(String linha) {
        int a = linha.indexOf("Cliente:");
        int b = linha.indexOf("valor total de compra");
        
        String nome = linha.substring(9, b - 1);
        double valor = Double.parseDouble(linha.substring(b + 22, linha.length()));
     
        return new Cliente(nome, valor);
    }
    
    
    public boolean salvarLista(List clientes, String livroTXT) throws FileNotFoundException, IOException {
        new File(livroTXT).delete();
        BufferedWriter writer = new BufferedWriter(new FileWriter(livroTXT)); 
        Iterator it = clientes.iterator();
        while (it.hasNext()) {
            Cliente cliente = (Cliente) it.next();
            writer.write(cliente.toString()+"\r\n");
        }
        writer.close();
        return true;
    }
    

    public static void main(String[] args) {
        LeituraCliente leitura = new LeituraCliente();
        List clientes = leitura.leitura("Cliente.txt");
        Iterator it = clientes.iterator();
        while (it.hasNext()) {
            Cliente livro = (Cliente) it.next();
            System.out.println(livro.toString());
        }
    }
    
}
