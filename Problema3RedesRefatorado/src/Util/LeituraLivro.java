/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Model.Livro;
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
public class LeituraLivro {

    private ArrayList<Livro> livros;

    public LeituraLivro() {
        livros = new ArrayList<>();
    }

    /**
     * O método ded leitura recebe o nome do arquivo, para analisar qualquer
     * tipo de arquivo de forma generica, o mesmo faz a leitura completa do
     * arquivo e o salva na memória, retornando a lista de livros.
     *
     * @param nomeTXT
     */
    public ArrayList<Livro> leitura(String nomeTXT) {
        try {
            FileReader arq = new FileReader(nomeTXT);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            while (linha != null) {
                livros.add(refatorar(linha));
                linha = lerArq.readLine();
            }
            arq.close();
        } catch (IOException ex) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    ex.getMessage());
        }
        return livros;
    }

    /**
     * O método refatorar recebe uma linha do arquivo e pega as informações
     * contidas nele, como por exemplo: id, livro, valor, quantidade
     *
     * @param linha
     * @return
     */
    private Livro refatorar(String linha) {
        int a = linha.indexOf("id");
        int b = linha.indexOf("livro");
        int c = linha.indexOf("valor");
        int d = linha.indexOf("qtd");
        int id = Integer.parseInt(linha.substring(3, b - 1));
        String livro = linha.substring(b + 6, c - 1);
        double valor = Double.parseDouble(linha.substring(c + 6, d - 1));
        int qtd = Integer.parseInt(linha.substring(d + 4, linha.length()));
        return new Livro(id, livro, valor, qtd);
    }

    /**
     * O método receberá uma lista de livros para salvar a mesma no arquivo ou
     * seja, irá atualiza-la.
     *
     * @param livros
     * @return
     */
    public boolean salvarLista(ArrayList<Livro> livros, String livroTXT) throws FileNotFoundException, IOException {
        new File(livroTXT).delete();
        BufferedWriter writer = new BufferedWriter(new FileWriter(livroTXT)); 
        Iterator it = livros.iterator();
        while (it.hasNext()) {
            Livro livro = (Livro) it.next();
            writer.write(livro.toString()+"\r\n");
        }
        writer.close();
        return true;
    }

    public static void main(String[] args) throws IOException {
       
       
        LeituraLivro leitura = new LeituraLivro();
        ArrayList<Livro> livros = leitura.leitura("Livros_Distribuidor.txt");
        Iterator it = livros.iterator();
        while (it.hasNext()) {
            Livro livro = (Livro) it.next();
            System.out.println(livro.toString());
            if(livro.getId()==4){
                livro.setQtd(180);
            }
        }
        leitura.salvarLista(livros, "Livros_Distribuidor.txt");
    }

    
    public void salvarArquivoServidor(String txt, String titulo) throws IOException{
        ArrayList<Livro> l = new ArrayList<>();
        String aux[] = txt.split("\n");
        for(String livro : aux){
            l.add(refatorarComSemaforo(livro));
        }
        salvarLista(l, titulo);
        System.out.println("Arquivo Salvo com sucesso");
    }

    public ArrayList<Livro> gravarLivrosMemoriaServidor(String listaLivroComSemaforo) {
        ArrayList<Livro> listaDeLivros = new ArrayList<Livro>();
        String aux[] = listaLivroComSemaforo.split("\n");
        for(String livro : aux){
            listaDeLivros.add(refatorarComSemaforo(livro));
        }
    
        return listaDeLivros;
    }
    private Livro refatorarComSemaforo(String linha) {
        int a = linha.indexOf("id");
        int b = linha.indexOf("livro");
        int c = linha.indexOf("valor");
        int d = linha.indexOf("qtd");
        int e = linha.indexOf("ip");
        int f = linha.indexOf("porta");
        int g = linha.indexOf("semáforo");
        int id = Integer.parseInt(linha.substring(3, b - 1));
        String livro = linha.substring(b + 6, c - 1);
        double valor = Double.parseDouble(linha.substring(c + 6, d - 1));
        int qtd = Integer.parseInt(linha.substring(d + 4, e-1));
        String ip = linha.substring(e+3, f-1);
        int porta = Integer.parseInt(linha.substring(f+ 6,g-1));
        int semaforo = 1;
        return new Livro(id, livro, valor, qtd, ip, porta, semaforo);
    }
}
