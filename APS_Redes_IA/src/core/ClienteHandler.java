package core;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClienteHandler implements Runnable {

    private Socket socket;
    private String nomeCliente;
    private PrintWriter saida;

    //Lista compartilhada de todos os clientes conectados
    private static List <ClienteHandler> clientesConectados = new ArrayList<>();

    //Construtor: recebe o socket do cliente que se conectou
    public ClienteHandler (Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run () {
        try {
            BufferedReader entrada = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter (socket.getOutputStream(), true);

            //Pede o nome do cliente para verificar se já existem alguém com esse nome
            saida.println ("Digite seu nome: ");
            nomeCliente = entrada.readLine();

            while (nomeJaExiste(nomeCliente)) {
                saida.println ("Este nome já está em uso, por favor escolha outro: ");
                nomeCliente = entrada.readLine();
            }

            //Adiciona esse cliente na lista de conectados
            clientesConectados.add(this);
            System.out.println (nomeCliente + " entrou no chat!");
            enviarParaTodos("[Servidor] " + nomeCliente + " entrou no chat!");

            //Mostra os comandos disponíveis para o cliente utilizar
            saida.println ("Comandos Disponíveis: ");
            saida.println ("/lista                        -> Mostra a lista de clientes conectados");
            saida.println ("/p <nome> <mensagem>          -> mensagem privada para um cliente específico");
            saida.println ("<mensagem>                    -> envia para todos os clientes conectados");

            //Loop principal, lê mensagen enquanto cliente estiver conectados
            String mensagem;
            while ((mensagem = entrada.readLine ()) != null) {
                if (mensagem.equals ("/lista")) {
                    listarClientes();

                }

                else if (mensagem.startsWith ("/p ")) {
                    enviarMensagemPrivada(mensagem);

                }

                else {
                    System.out.println (nomeCliente + ": " + mensagem);
                    enviarParaTodos("[" + nomeCliente + "] " + mensagem);

                }
            }
        }

        catch  (IOException e) {

            System.out.println (nomeCliente + " saiu do chat.");

        }

        finally {

            clientesConectados.remove (this);
            enviarParaTodos("[Servidor] " + nomeCliente + " saiu do chat!");
            try { socket.close (); } catch (IOException e) {}
        }
    }

    //Envia mensagem para todos os clientes conectados
    private void enviarParaTodos (String mensagem) {
        for (ClienteHandler cliente : clientesConectados) {
            cliente.saida.println (mensagem);

        }
    }

    //Envia mensagem para um Cliente Expecífico
    private void enviarMensagemPrivada (String mensagem) {

        //Formato esperado : /p <nome> <mensagem>
        //Exemplo: /p Maria olá!
        String [] partes = mensagem.split (" ", 3);

        //Veerifica se o comando foi digitado corretamente
        if (partes.length < 3) {
            saida.println ("[Servidor] Formato correto: /p <nome> <mensagem>");
            return;

        }

        String nomeDestino = partes [1];
        String conteudo = partes [2];

        //Procura o cliente com esse nome na lista
        boolean encontrado = false;
        for (ClienteHandler cliente : clientesConectados) {
            if (cliente.nomeCliente.equals (nomeDestino)) {
                cliente.saida.println ("[Privado " + nomeCliente + "] " + conteudo);
                saida.println ("[Privado para " + nomeDestino + "] " + conteudo);
                encontrado = true;
                break;
                
            }
        }

        if (!encontrado) {
            saida.println ("[Servidor] usuário '" + nomeDestino + "' não encontrado.");
        }
    }

    private void listarClientes() {
        saida.println("[Servidor] usuários online: ");
        for (ClienteHandler cliente : clientesConectados) {
            if (cliente.nomeCliente.equals (nomeCliente)) {
                saida.println ("  -> " + cliente.nomeCliente + " (você)");
            }

            else {
                saida.println ("  -> " + cliente.nomeCliente);
            }
        }
    }

    //Verifica se um nome já está em uso
    private boolean nomeJaExiste(String nome) {

        for (ClienteHandler cliente : clientesConectados) {
            if (cliente.nomeCliente.equals (nome)) {
                return true;
            }
        }

        return false;
    }

}
