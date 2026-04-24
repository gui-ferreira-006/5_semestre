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

    static final String ENDERECO = "LocalHost";
    static final int PORTA = 65173;

    public static void main (String [] args) throws IOException {

        /*
        socket -> é a "porta" de comunicação entre o cliente e o servidor.
        */
        
        Socket socket = new Socket (ENDERECO, PORTA);
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

        BufferedReader teclado = new BufferedReader (new InputStreamReader (System.in));

        Thread threadReceber = new Thread (() -> {
            try {
                String mensagem;
                while ((mensagem = entrada.readLine ()) != null) {
                    System.out.println (mensagem);
                }
            }

            catch (IOException e) {
                System.out.println ("Conexão encerrad.");
            }
        });

        threadReceber.start();

        //Loop principal para enviar mensagens pelo teclado
        String texto;
        while ((texto = teclado.readLine ()) != null) {
            saida.println (texto);
        }
       
        socket.close ();

    }
}
