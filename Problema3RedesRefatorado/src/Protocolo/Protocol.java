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
    public final static int LISTA_LIVRO_COM_SEMAFORO = 5;
    
    //Comunicacao Distribuidor-Cliente
    public final static int CONECTAR_COM_SERVIDOR =6;
    public final static int INFORMAR_QUEDA_SERVIDOR = 7;
    
    //Comunicação Cliente-Servidor
    public final static int RECEBER_LISTA_LIVROS = 8;
    public final static int EFETUAR_COMPRA = 9;
            
    //Comunicação Servidor-Servidor
    public final static int VERIFICAR_SEMAFORO = 10;
    public final static int ATUALIZAR_LINHA_LIVRO = 11;
    
}