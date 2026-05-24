package core;

import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class ClienteHandler implements Runnable {

    private Socket socket;
    private String nomeCliente;
    private PrintWriter saida;
    private static LogServidor logger;

     //Construtor: recebe o socket do cliente que se conectou
    public ClienteHandler (Socket socket) {
        this.socket = socket;
    }

    public static void setLogger (LogServidor log) {
        logger = log;
    }

    private void log (String mensagem){
        if (logger != null) {
            logger.log (mensagem);
        } else {
            System.out.println (mensagem);
        }
    }

    //Lista compartilhada de todos os clientes conectados
    private static List <ClienteHandler> clientesConectados = new ArrayList<>();

    //Formatador de data e hora
    private static DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    //Retorna a hora atual formatada
    private static String agora() {
        return "[" + LocalDateTime.now().format(FORMATO) + "]";

    }

    @Override
    public void run () {
        try {
            BufferedReader entrada = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter (socket.getOutputStream(), true);

            // ==================================================================
            // Integração com a Interface Gráfica
            // ==================================================================
            String usuario = entrada.readLine();

            if (usuario == null || usuario.trim().isEmpty()) {
                socket.close();
                return;
            }

            nomeCliente = usuario;


            if (nomeJaExiste(nomeCliente)) {
                saida.println("[Sistema]: Erro - Este usuário já está conectado em outra sessão!");
                socket.close();
                return;
            }
          
            if (logger != null) logger.clienteLogado (socket.getInetAddress().toString(), nomeCliente);


            // Adiciona esse cliente na lista de conectados
            clientesConectados.add(this);

            // Integração: Usa o log do Mei em vez do System.out.println
            log(agora() + " " + " conectou-se via Terminal de Campo");
          
            // Mensagem de boas vindas limpa (A sua versão do Front-end)
            saida.println("-------------------------------------");
            saida.println("[Central]: Bem vindo à rede de monitoramento, " + nomeCliente + "!");
            saida.println("[Central]: Digite /lista para ver os comandos ou /arquivo para laudos.");
            saida.println("--------------------------------------");
          
            // Avisa os outros
            enviarParaTodos(agora() + " [Sistema]: " + nomeCliente + " entrou no chat operacional.");


            //Mostra os comandos disponíveis para o cliente utilizar
            saida.println ("──────────────────────────────────────────");
            saida.println ("Comandos Disponíveis: ");
            saida.println ("/lista                        -> Mostra a lista de clientes conectados");
            saida.println ("/p <nome> <mensagem>          -> mensagem privada para um cliente específico");
            saida.println ("<mensagem>                    -> envia para todos os clientes conectados");
            saida.println ("/arquivo <caminho>            -> envia um arquivo para todos");
            saida.println ("/sair                         -> Desconectar do chat");
            saida.println ("──────────────────────────────────────────");

            //Loop principal, lê mensagen enquanto cliente estiver conectados
            String mensagem;
            while ((mensagem = entrada.readLine ()) != null) {
                if (mensagem.equals ("/sair")) {
                    saida.println ("[Servidor] Você foi desconectado. Até logo, " + nomeCliente + "!");
                    listarClientes();

                }

                else if (mensagem.equals ("/lista")) {
                    listarClientes();
                }

                else if (mensagem.startsWith ("/p ")) {
                    enviarMensagemPrivada(mensagem);

                }

                else if (mensagem.startsWith("ARQUIVO:")) {
                    receberERepassarArquivo (mensagem);
                }

                else if (mensagem.startsWith ("/")) {
                    //Comando desconhecido
                    saida.println("[!] Comando não reconhecido! Digite /lista para ver os comandos.");
                }

                else {
                    log (agora() + " " + nomeCliente + ": " + mensagem);
                    enviarParaTodos (agora() + " [" + nomeCliente + "] " + mensagem);

                }
            }
        }

        catch  (IOException e) {

            if (nomeCliente != null) {

                log (agora() + " " + nomeCliente + " perdeu a conexão.");
                
            }

        }

        finally {

            clientesConectados.remove (this);
            if (nomeCliente != null) {
                enviarParaTodos (agora() + "[Servidor] " + nomeCliente + " saiu do chat!");
            }
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

        //Verifica se o comando foi digitado corretamente
        if (partes.length < 3) {
            saida.println ("[!] Formato correto: /p <nome> <mensagem>");
            return;

        }

        String nomeDestino = partes [1];
        String conteudo = partes [2];

        //Procura o cliente com esse nome na lista
        boolean encontrado = false;
        for (ClienteHandler cliente : clientesConectados) {
            if (cliente.nomeCliente.equals (nomeDestino)) {
                cliente.saida.println ("[Privado " + nomeCliente + "] " + conteudo);
                saida.println (agora() + "[Privado para " + nomeDestino + "] " + conteudo);
                encontrado = true;
                break;
                
            }
        }

        if (!encontrado) {
            saida.println ("[!] usuário '" + nomeDestino + "' não encontrado.");
        }
    }

    //Lista todos os clientes conectados
    private void listarClientes() {
        saida.println("──────────────────────────────");
        saida.println("[Servidor] usuários online: ");
        for (ClienteHandler cliente : clientesConectados) {
            if (cliente.nomeCliente.equals (nomeCliente)) {
                saida.println ("  -> " + cliente.nomeCliente + " (você)");
            }

            else {
                saida.println ("  -> " + cliente.nomeCliente);
            }
        }

        saida.println("──────────────────────────────");
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

    private void receberERepassarArquivo (String cabecalho) {
        try {

            //Formato: ARQUIVO:<nome>:<tamanho>
            String[] partes = cabecalho.split(":");
            String nomeArquivo = partes[1];
            long tamanho = Long.parseLong(partes[2]);

            log (agora() + "[Servidor] Recebendo arquivo de: " + nomeCliente + ": " + nomeArquivo + " (" + tamanho + " bytes)");

            DataInputStream entradaBytes = new DataInputStream (socket.getInputStream());

            //Lê todos os bytes do arquivo
            byte[] dados = new byte [(int) tamanho];
            entradaBytes.readFully(dados);

            log(agora() + "[Servidor] Arquivo recebido! Repassando para os outros clientes...");

            //Repassa para todos os outros clientes
            repassarArquivoParaTodos (nomeArquivo, dados);

        }

        catch (IOException e) {
            log ("[!] Erro ao receber arquivo: " + e.getMessage());

        }
    }

    //Repassa o arquivo para todos os clientes conectados
    private void repassarArquivoParaTodos (String nomeArquivo, byte[] dados) {
        for (ClienteHandler cliente : clientesConectados) {
            if (cliente != this) { //Não envia de volta para quem mandou
                try {

                    //Avisa o cliente que vem um arquivo
                    cliente.saida.println("ARQUIVO:" + nomeArquivo + ":" + dados.length);

                    //Envia os Bytes
                    DataOutputStream saidaBytes = new DataOutputStream (cliente.socket.getOutputStream());

                    saidaBytes.write(dados);
                    saidaBytes.flush();

                }

                catch (IOException e) {
                    log ("[!] Erro ao repassar arquivo: " + e.getMessage());
                }
            }
        }
    }
}
