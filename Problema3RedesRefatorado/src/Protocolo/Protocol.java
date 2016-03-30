/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocolo;

/**
 *
 * @author allen
 */
public class Protocol {
    
    //Identificador de maquinas
    public final static int SERVIDOR = 0;
    public final static int CLIENTE = 1;
    public final static int DISTRIBUIDOR = 2;
    
    //Comunicação Servidor-Distribuidor
    public final static int SERVIDOR_ENVIAR_IP_PORTA = 3;
    public final static int LISTA_DE_IPS = 4;
    
    //Comunicacao Distribuidor-Cliente
    public final static int CONECTAR_COM_SERVIDOR =5;
    public final static int INFORMAR_QUEDA_SERVIDOR = 6;
    
}