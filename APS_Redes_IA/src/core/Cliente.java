package core;

import java.io.*;
import java.net.*;

public class Cliente {

    public static void main (String [] args) throws IOException {

        Socket socket = new Socket ("Localhost", 65173);
        System.out.println ("Conectado ao servidor com Sucesso!");

        PrintWriter saida = new PrintWriter (socket.getOutputStream (), true);

        BufferedReader entrada = new BufferedReader (new InputStreamReader (socket.getInputStream()));

        saida.println ("Olá, Servidor!");

        String resposta = entrada.readLine ();
        System.out.println ("Servidor respondeu: " + resposta);

        socket.close ();

    }
}
