/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Model.Livro;
import Protocolo.Protocol;
import Util.LeituraLivro;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CASSIO
 */
public class ServidorController {
 
    private String ipPortaOutrosServidores;
    private ArrayList<Socket> conexaoServidores = new ArrayList<Socket>();
    private String ip;
    private int portaDeAcesso;
    private ArrayList<Livro> livros;//Local no qual são verificados semaforos e feitas alterações
    private ArrayList<String> ipsPortas = new ArrayList<String>();
    DataOutputStream dSaidaSocket;
    DataInputStream dEntradaSocket;
    
    public ServidorController(String ip, int portaDeAcesso){
        this.ip = ip;
        this.portaDeAcesso = portaDeAcesso;
    }
    
    
    public void conectarComDistribuidor(String ipDistribuidor, int port) throws IOException{
     
         
        System.err.println("O servidor está operando na porta: "+ port);
        Socket socket = new Socket(ipDistribuidor, 11111);
        dSaidaSocket = new DataOutputStream(socket.getOutputStream());
        dEntradaSocket = new DataInputStream(socket.getInputStream());
        dSaidaSocket.writeInt(Protocol.SERVIDOR);//Código para dizer que é Servidor
        System.err.println("Enviou código para dizer que é servidor");

        dSaidaSocket.writeInt(Protocol.SERVIDOR_ENVIAR_IP_PORTA);//Código para dizer que vai enviar IP e PORTA
        dSaidaSocket.writeUTF(socket.getLocalAddress().getHostAddress());
        dSaidaSocket.writeInt(port);
        
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
        ArrayList ips = new ArrayList();
        for(int i=0; i<ipPorta.length; i++){
            ips.add(ipPorta[i]);
            this.ipsPortas= ips;
        }
        
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

    public boolean comprarLivro(String nomeCliente, int id, int qtd) throws IOException {
       
       Livro livro = buscarLivro(id);
       String ipPorta = verificarPosseLivro(livro.getId());
       
       if(verificarPossibilidadeDeCompra(livro, qtd)){
           String aux[]= ipPorta.split("-");
           System.err.println(aux[0] + " " + this.ip + " / " + aux[1] + " "+this.portaDeAcesso);
           if(this.ip.equals(aux[0]) && this.portaDeAcesso == Integer.parseInt(aux[1])){ //Servidor local
              livro.setQtd(livro.getQtd() - qtd);
              
              LeituraLivro leituras = new LeituraLivro();
              String nomeTXT = ip + "-" + portaDeAcesso +".txt";
              leituras.salvarLista(livros, nomeTXT);
              double valor = livro.getValor()*qtd;
              enviarDadosCliente(nomeCliente,valor);
              try {
                distribuirOperacao(id,qtd);
                  enviarParaDistribuidor(id, qtd);
               } catch (Exception e) {
                   System.err.println("Não distribuiu");
               }
              
           }else{//Outro Servidor
               int i = 0;
               for(; i <ipsPortas.size() && !ipPorta.equals(ipsPortas.get(i)); i++);
               
               Socket socket = conexaoServidores.get(i);
               DataInputStream entrada = new DataInputStream(socket.getInputStream());
               DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
               saida.writeInt(Protocol.VERIFICAR_SEMAFORO);
               saida.writeUTF(nomeCliente);
               saida.writeInt(id);
               saida.writeInt(qtd);
           }
           
           
       }else{
           return false; //Não tem quantidade disponível
       }
 
       return true;
    }

    private boolean verificarPossibilidadeDeCompra(Livro livro,int qtd){
        return livro.getQtd() >= qtd; //True = Pode comprar, False = não pode comprar
    }
    

    private Livro buscarLivro(int id){
        for(int i = 0;i <livros.size(); i++){
            if(livros.get(i).getId() == id){
                return livros.get(i);
            }
        }
        return null;
    }
    private String verificarPosseLivro(int id) {
        String ip= "";
        int porta = 0;
        
        for(int i = 0;i <livros.size(); i++){
            if(livros.get(i).getId() == id){
                ip = livros.get(i).getIp();
                porta = livros.get(i).getPort();
                break;
            }
        }
        
        return (ip + "-" + porta);
    }

    public void setLivros(String listaLivroComSemaforo) {
        
        LeituraLivro leitura = new LeituraLivro();
        
        try {
            leitura.salvarArquivoServidor(listaLivroComSemaforo, this.ip +"-"+ this.portaDeAcesso+".txt");
        
            this.livros = leitura.gravarLivrosMemoriaServidor(listaLivroComSemaforo); 
        } catch (IOException ex) {
            Logger.getLogger(ServidorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }

    private void distribuirOperacao(int id, int qtd) throws IOException {
        for(int i = 0; i <conexaoServidores.size();i++){
            Socket socket = conexaoServidores.get(i);
            DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
            saida.writeInt(Protocol.ATUALIZAR_LINHA_LIVRO);
            saida.writeInt(id);
            saida.writeInt(qtd);    
        }

    }

    private void enviarParaDistribuidor(int id, int qtd) throws IOException {
            dSaidaSocket.writeInt(Protocol.ATUALIZAR_LINHA_LIVRO);
            dSaidaSocket.writeInt(id);
            dSaidaSocket.writeInt(qtd);    
    }
    void atualizarLinhaOutroServidor(int id, int qtd) throws IOException {
        for(int i = 0; i < livros.size(); i++){
            if(livros.get(i).getId() == id) {
                livros.get(i).setQtd(livros.get(i).getQtd() - qtd);
            }
        
        }
        
        LeituraLivro leituras = new LeituraLivro();
        String nomeTXT = ip + "-" + portaDeAcesso + ".txt";
        
        leituras.salvarLista(livros, nomeTXT);
    
    }

    private void enviarDadosCliente(String nomeCliente, double valor) throws IOException {
        dSaidaSocket.writeInt(Protocol.GUARDAR_DADOS_CLIENTE);
        dSaidaSocket.writeUTF(nomeCliente);
        dSaidaSocket.writeDouble(valor);
    }

    public String getStringlivros() {
        String aux = "";
        for(int i= 0; i < livros.size();i++){
            aux+= livros.get(i).toString() + "\n";
        }
        return aux;
    }

    

    

}
