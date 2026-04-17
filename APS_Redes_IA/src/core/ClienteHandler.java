package core;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClienteHandler implements Runnable {
    
    private Socket socket;
    private String nomeCliente;


    private static List <ClienteHandler> clientesConectados = new ArrayList<>();
    private PrintWriter saida;

    public ClienteHandler (Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run () {
        try {
            BufferedReader entrada = new BufferedReader (new  InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter (socket.getOutputStream(), true);

            saida.println ("Digite seu nome: ");
            nomeCliente = entrada.readLine();
            System.out.println (nomeCliente + " entrou no chat!");

            clientesConectados.add(this);

            enviarParaTodos (nomeCliente + " entrou no chat!");

            String mensagem;   
            while ((mensagem = entrada.readLine ()) != null) {
                System.out.println (nomeCliente + ": " + mensagem);
                enviarParaTodos (nomeCliente + ": " + mensagem);

            }

        } catch (IOException e) {
            System.out.println (nomeCliente + " desconectou.");
            
        } finally {

            clientesConectados.remove(this);
            enviarParaTodos (nomeCliente + "saiu do chat.");
            try { socket.close (); } catch (IOException e) {}

        }
    }

    private void enviarParaTodos (String mensagem) {
        for (ClienteHandler cliente : clientesConectados) {
            cliente.saida.println(mensagem);
        }
    }

}
