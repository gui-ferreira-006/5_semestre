package core;

import java.io.*;
import java.net.*;


public class Servidor {

    static final int PORTA = 65173;

    public static void main (String [] args) throws IOException {

        //Abre uma "porta" para escutar as conexões dos clientes
        ServerSocket serverSocket = new ServerSocket (PORTA);
        System.out.println ("Servidor rodando na porta " + PORTA);
        System.out.println ("Aguardando conexão...");

        //Loop Infinitoo - o servidor fica sempre rodando, esperando clientes se conectarem
        while (true) {

            //Espera um cliente conectar
            Socket socket = serverSocket.accept();
            System.out.println ("Novo cliente conectado ao servidor: " + socket.getInetAddress());

            //Cria um handler (garçom) ppara esse cliente
            ClienteHandler handler = new ClienteHandler (socket);
            
            //Cria um Thread e passa o handler para ela 
            Thread thread = new Thread (handler);

            //Inicia a Thread, ela começa a rodar o método run() do handler
            thread.start();

        }

    }
    
}
