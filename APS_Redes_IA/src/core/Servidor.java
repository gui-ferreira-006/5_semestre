package core;

import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;


public class Servidor {

    static final int PORTA = 65173;
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern ("dd/MM/yyyy HH:mm:ss");

    private static String agora() {
        return "[" + LocalDateTime.now().format(FORMATO) + "]";
    }

    public static void main (String [] args) throws IOException {

        //Abre uma "porta" para escutar as conexões dos clientes
        ServerSocket serverSocket = new ServerSocket (PORTA);
        System.out.println ("╔══════════════════════════════════════╗");
        System.out.println ("║         Servidor de Chat             ║");
        System.out.println ("║         Porta: " + PORTA + "                 ║");
        System.out.println ("╚══════════════════════════════════════╝");
        System.out.println (agora() + "Servidor iniciando! Aguardando conexões...");

        //Loop Infinito - o servidor fica sempre rodando, esperando clientes se conectarem
        while (true) {

            //Espera um cliente conectar
            Socket socket = serverSocket.accept();
            System.out.println (agora() + "Novo cliente conectado ao servidor: " + socket.getInetAddress());

            //Cria um handler (garçom) para esse cliente
            ClienteHandler handler = new ClienteHandler (socket);
            
            //Cria um Thread e passa o handler para ela 
            Thread thread = new Thread (handler);

            //Inicia a Thread, ela começa a rodar o método run() do handler
            thread.start();

        }

    }
    
}
