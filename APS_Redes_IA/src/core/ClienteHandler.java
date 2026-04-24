package core;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClienteHandler implements Runnable {
    
    private Socket socket;
    private String nomeCliente;

    //Lista compartilhada de todos os clientes conectados
    private static List <ClienteHandler> clientesConectados = new ArrayList<>();
    private PrintWriter saida;

    //Construtor: recebe o socket do cliente que se conectou
    public ClienteHandler (Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run () {
        try {
            BufferedReader entrada = new BufferedReader (new  InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter (socket.getOutputStream(), true);

            //Pede o nome do cliente assim que ele conecta e o armazena para idenficar suas mensagens
            saida.println ("Digite seu nome: ");
            nomeCliente = entrada.readLine();
            System.out.println (nomeCliente + " entrou no chat!");

            //Adiciona esse cliente na lista de conectados
            clientesConectados.add(this);

            //Avisa todos que o cliente entrou no chat
            enviarParaTodos (nomeCliente + " entrou no chat!");

            //Fica em loop lendo mensagens enquanto o cliente estiver conectados
            String mensagem;   
            while ((mensagem = entrada.readLine ()) != null) {
                System.out.println (nomeCliente + ": " + mensagem);
                enviarParaTodos (nomeCliente + ": " + mensagem);

            }

        } catch (IOException e) {
            System.out.println (nomeCliente + " desconectou.");
            
        } finally {

            //Remove o cliente da lista quando ele sair e avisa os outros que ele saiu do chat
            clientesConectados.remove(this);
            enviarParaTodos (nomeCliente + "saiu do chat.");
            try { socket.close (); } catch (IOException e) {}

        }
    }

    //Envia uma mensagem para todos os clientes conectados
    private void enviarParaTodos (String mensagem) {
        for (ClienteHandler cliente : clientesConectados) {
            cliente.saida.println(mensagem);
        }
    }

}
