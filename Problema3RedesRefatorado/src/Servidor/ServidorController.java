/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Protocolo.Protocol;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author CASSIO
 */
public class ServidorController {
 
    private String ipPortaOutrosServidores;
    private ArrayList<Socket> conexaoServidores = new ArrayList<Socket>();
    private String ip;
    private int portaDeAcesso;
    
    public ServidorController(String ip, int portaDeAcesso){
        this.ip = ip;
        this.portaDeAcesso = portaDeAcesso;
    }
    
    
    public void conectarComDistribuidor(String ipDistribuidor, int port) throws IOException{
     
        DataOutputStream saidaSocket;
        DataInputStream entradaSocket;
         
        System.err.println("O servidor está operando na porta: "+ port);
        Socket socket = new Socket(ipDistribuidor, 11111);
        saidaSocket = new DataOutputStream(socket.getOutputStream());
        
        saidaSocket.writeInt(Protocol.SERVIDOR);//Código para dizer que é Servidor
        System.err.println("Enviou código para dizer que é servidor");

        saidaSocket.writeInt(Protocol.SERVIDOR_ENVIAR_IP_PORTA);//Código para dizer que vai enviar IP e PORTA
        saidaSocket.writeUTF(socket.getLocalAddress().getHostAddress());
        saidaSocket.writeInt(port);
        
        //Vamos ver essa parte aqui agora.
        //Eu preciso criar uma Thread no Servidor que fique disponível sempre para receber uma informação do distribuidor
        //Essa informação pode ser mandar a lista de IPS de servidores que ele precisa se conectar
        //Ou a nova relação de livros mais atualizada que tem no DIstribuidor.
        //Desse jeito que ta fazendo aqui ta certo? Falta colocar o que no distribuidor?
        // sim está certa, no distribuidor temos o tratamento servidor que será o outro lado da comunicação
        //ambos os lados estaram em um laço esperando uma entrada de protocolo a ser tratada
        //ou seja as mensagens serão trocadas entre  o tratamento distribuidor e o tratamento servidor certo?
        //Acho que não, o certo é as mensagem serem trocadas entre o controller de um e o tratamento do outro.
        //Ou seja, no controller do distribuidor tem que ter uma lista dos sockets dos servidores conectados.
        //E no controller do Servidor tem que ter o socket do DIstribuidor. Não seria isso?
        //não manjo de controller nao, maspelo que disse acho que está certo,
        //Ahh, Deixa os comentarios aqui, vai ficar mais fácil de fazer o relatório.
        
    
    }
    
    public void setListaDeIps(String outrosServidores){
        this.ipPortaOutrosServidores = outrosServidores;
    }

    public void conectarComServidores() throws IOException {
        
        conexaoServidores = new ArrayList<Socket>();
        String ipPorta[] = this.ipPortaOutrosServidores.split("/");
        for(int i = 0; i <ipPorta.length;i++){
            System.err.println("Estabelecendo conexao com servidor: "+ ipPorta[i]);
            String aux[] = ipPorta[i].split("-");
            Socket socket = new Socket(aux[0], Integer.parseInt(aux[1]));
            conexaoServidores.add(socket);
            DataOutputStream saidaSocket = new DataOutputStream(socket.getOutputStream());
    
            saidaSocket.writeInt(Protocol.SERVIDOR);//Código para dizer que é Servidor
            System.err.println("Enviou código para dizer que é servidor");

        
        }
    }
}
