package core;

import java.io.*;
import java.net.*;


public class Servidor {

    public static void main (String [] args) throws IOException {

        //Abre uma "porta" para escutar as conexões dos clientes
        ServerSocket serverSocket = new ServerSocket (65173);
        System.out.println ("Aguardando conexão na porta 65173...");

        //Fica esperando até um cliente realizar a conexão com a porta aberta
        Socket socket = serverSocket.accept();
        System.out.println ("Conectado ao servidor com sucesso!");

        //Prepara para Ler mensagens do cliente
        BufferedReader entrada = new BufferedReader (new InputStreamReader (socket.getInputStream()));

        //Prepara para enviar mensagens ao cliente
        PrintWriter saida = new PrintWriter (socket.getOutputStream (), true);

        //Lê a mensagem que o cliente enviou
        String mensagem = entrada.readLine ();
        System.out.println ("You: " + mensagem);

        //Responde ao cliente confirmando que a mensagem foi recebida
        saida.println ("Sua mensagem foi recebida com sucesso!");

        //Fecha a conexão com o cliente e a porta do servidor
        socket.close ();
        serverSocket.close ();

    }
    
}
