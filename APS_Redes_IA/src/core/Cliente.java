package core;

/*

Importações das Bibliotecas: 

    import java.io.*; -> responsável por fazer o programa ler e escrever 
    dados, manipulando arvquivos, diretórios e fluxos de dados.

    import java.net.*; -> responsável pela comunicação entre computadores 
    diferentes através de redes, permite que seu programa se conecte a
    servidores, envie e receba dados pela internet, e crie conexões
    de rede.

*/

import java.io.*;
import java.net.*;

public class Cliente {

    /*
    throws IOException -> avisa que esse método pode gerar erros de
    entrada/saída e que não vai tratar eles aqui, só avisando que pode
    acontecer.
    */

    public static void main (String [] args) throws IOException {

        /*
        socket -> é a "porta" de comunicação entre o cliente e o servidor.
        */
        
        Socket socket = new Socket ("Localhost", 65173);
        System.out.println ("Conectado ao servidor com Sucesso!");

        /*
        PrintWriter -> Ferramenta de escrita de texto, ele pega qualquer
        dado e transforma em texto para enviar a algum destino.
        */

        PrintWriter saida = new PrintWriter (socket.getOutputStream (), true);

        /*
        BufferedReader -> Ferramenta de leitura de texto, recebe dados que
        chegam pela rede e os tranforma em texto legível para o programa.
        */

        BufferedReader entrada = new BufferedReader (new InputStreamReader (socket.getInputStream()));

        saida.println ("Olá, Servidor!");

        String resposta = entrada.readLine ();
        System.out.println ("Servidor respondeu: " + resposta);

        socket.close ();

    }
}
