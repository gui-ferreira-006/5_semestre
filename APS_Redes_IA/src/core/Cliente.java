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

    static final String ENDERECO = "localhost";
    static final int PORTA = 65173;

    public static void main (String [] args) throws IOException {

        /*
        socket -> é a "porta" de comunicação entre o cliente e o servidor.
        */
        
        Socket socket = new Socket (ENDERECO, PORTA);
       
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

        DataOutputStream saidaBytes = new DataOutputStream (socket.getOutputStream());

        DataInputStream entradaBytes = new DataInputStream (socket.getInputStream());

        BufferedReader teclado = new BufferedReader (new InputStreamReader (System.in));

        // Thread para Receber mensagens do servidor
        Thread threadReceber = new Thread (() -> {
            try {
                String mensagem;
                while ((mensagem = entrada.readLine ()) != null) {
                    
                    //Verifica se é um arquivo chegando
                    if (mensagem.startsWith ("ARQUIVO: ")) {

                        //Formato: ARQUIVO: <nome>:<tamanho>
                        String[] partes = mensagem.split (":");
                        String nomeArquivo = partes[1];
                        long tamanho = Long.parseLong (partes[2]);

                        System.out.println("[Servidor] Recebendo arquivo: " + nomeArquivo + " (" + tamanho + " bytes)");

                        //Cria o arquivo na pasta "recebidos"
                        File pasta = new File ("recebidos");
                        if (!pasta.exists()) pasta.mkdirs();

                        File arquivo = new File ("recebidos/" + nomeArquivo);
                        FileOutputStream fos = new FileOutputStream (arquivo);

                        //Lê os bytes e salva no arquivo

                        byte[] buffer = new byte [4096];
                        long totalLido = 0;
                        int lido;

                        while (totalLido < tamanho && (lido = entradaBytes.read(buffer, 0, 
                            (int) Math.min(buffer.length, tamanho - totalLido))) != -1) {
                            fos.write(buffer, 0, lido);
                            totalLido += lido;
                        }

                        fos.close ();
                        System.out.println ("[Servidor] Arquivo salvo em: recebidos/" + nomeArquivo);

                    }

                    else {

                        //Mensagem de texto normal
                        System.out.println (mensagem);

                    }
                }
            }

            catch (IOException e) {
                System.out.println ("Conexão encerrada.");
            }
        });

        threadReceber.start();

        //Loop principal para enviar mensagens pelo teclado
        String texto;
        while ((texto = teclado.readLine ()) != null) {

            //Verifica se o usuário quer enviar um arquivo
            if (texto.startsWith ("/arquivo ")) {

                //Formato: /arquivo <caminho do arquivo>>
                String caminho = texto.substring(9);
                File arquivo = new File (caminho);

                if (!arquivo.exists()) {
                    System.out.println ("Arquivo não encontrado: " + caminho);
                    continue;

                }

                //Avisa o servidor: nome e tamanho do arquivo
                saida.println ("ARQUIVO: " + arquivo.getName() + ":" + arquivo.length());

                //Envia os bytes do arquivo
                FileInputStream fis = new FileInputStream (arquivo);
                byte[] buffer = new byte[4096];
                int lido;

                while ((lido = fis.read(buffer)) != -1) {
                    saidaBytes.write(buffer, 0, lido);

                }

                saidaBytes.flush();
                fis.close();
                System.out.println("Arquivo enviado: " + arquivo.getName());

            }

            else {

                //Mensagem de texto normal
                saida.println (texto);
            }

        }
       
        socket.close ();

    }
}