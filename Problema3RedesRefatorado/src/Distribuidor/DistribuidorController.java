/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuidor;

import Protocolo.Protocol;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CASSIO
 */
public class DistribuidorController {
    
    private ArrayList<String> ipsPortas; //Cada String no formato IP-PORTA
    private ArrayList<TratamentoServidor> servidoresConectados = new ArrayList<TratamentoServidor>();
    
    public DistribuidorController(){
    
    
    }

    public void adicionarServidor(TratamentoServidor server) {
        this.servidoresConectados.add(server);
    }

    public void enviarIps() {
        //Reinicia a lista de IPsPorta;
        this.ipsPortas = new ArrayList<String>();
        
        System.err.println("Gerando Lista de IPS");
        for(int i = 0; i < servidoresConectados.size(); i++){
            String aux = servidoresConectados.get(i).getIpServidor()+"-"+servidoresConectados.get(i).getPortaDeAcesso();
            this.ipsPortas.add(aux);
            System.err.println(aux);
        }
        
        //Concatenando Os IP-Porta dos servidores e enviando para os servidores
        for(int i = 0; i<servidoresConectados.size();i++){
            String aux=""; //String com IP-Porta Concatenados. No formato IP-PORTA/IP-PORTA/IP-PORTA/
            for(int j=0; j<ipsPortas.size(); j++){
                if(i != j){//Não adiciona o próprio servidor na lista
                    aux += ipsPortas.get(j) +"/";
                }
            }
            try {
                servidoresConectados.get(i).getOutput().writeInt(Protocol.LISTA_DE_IPS);//Envia código de operação dizendo que vai enviar IPS
                servidoresConectados.get(i).getOutput().writeUTF(aux);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
        }
    }
}
