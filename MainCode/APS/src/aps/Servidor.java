package aps;

import java.io.*;
import java.net.*;


public class Servidor {

    public static void main (String [] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket (65173);
        System.out.println ("Aguardando conexão na porta 65173...");

        Socket socket = serverSocket.accept();
        System.out.println ("Conectado ao servidor com sucesso!");

        BufferedReader entrada = new BufferedReader (new InputStreamReader (socket.getInputStream()));

        PrintWriter saida = new PrintWriter (socket.getOutputStream (), true);

        String mensagem = entrada.readLine ();
        System.out.println ("You: " + mensagem);

        saida.println ("Sua mensagem foi recebida com sucesso!");

        socket.close ();
        serverSocket.close ();

    }
    
}
