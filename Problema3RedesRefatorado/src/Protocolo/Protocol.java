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
    public final static int GUARDAR_DADOS_CLIENTE = 6;
    
    //Comunicacao Distribuidor-Cliente
    public final static int CONECTAR_COM_SERVIDOR =7;
    public final static int INFORMAR_QUEDA_SERVIDOR = 8;
    
    //Comunicação Cliente-Servidor
    public final static int RECEBER_LISTA_LIVROS = 9;
    public final static int EFETUAR_COMPRA = 10;
    public final static int ENVIAR_NOME_CLIENTE = 11;
            
    //Comunicação Servidor-Servidor
    public final static int VERIFICAR_SEMAFORO = 11;
    public final static int ATUALIZAR_LINHA_LIVRO = 12;
    
}